package com.knowit.taskService.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.knowit.taskService.entities.EmailRequest;
import com.knowit.taskService.entities.Task;
import com.knowit.taskService.entities.TaskStatus;
import com.knowit.taskService.entities.User;
import com.knowit.taskService.exception.TaskNotFoundException;
import com.knowit.taskService.kafkaConfig.KafkaService;
import com.knowit.taskService.services.NotificationClient;
import com.knowit.taskService.services.TaskService;
import com.knowit.taskService.services.UserClient;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;
   
    @Autowired
    private UserClient userClient;
    
    @Autowired
    private NotificationClient notificationClient;
    
    @Autowired
    private KafkaService kafkaService;
    @PostMapping
    public Task createTask(@RequestBody Task task) throws JsonProcessingException {
    	System.out.println("task api calling :"+task);
    	ResponseEntity<User> user = userClient.getUserById((long)task.getUserId());
    	User u = user.getBody();
    	Task t = taskService.createTask(task);
    	t.setUser(u);
    	EmailRequest emailRequest = new EmailRequest();
    	emailRequest.setTo(t.getUser().getEmail());
    	emailRequest.setSubject("Task is assigned to you");
    	emailRequest.setMassage(task.toString());
    	//notificationClient.EmailSender(emailRequest);
    	try {
			kafkaService.sendMail(emailRequest);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return t;
    }
    @GetMapping
    public List<Task> getAll() throws JsonProcessingException
    {
    	List<Task> tasks = taskService.getAllTask();
    	tasks = tasks.stream().map(t -> { t.setUser(userClient.getUserById((long)t.getUserId()).getBody()); return t;}).collect(Collectors.toList());
    	kafkaService.getAllTask(tasks);
    	return tasks;
    }
    @GetMapping("/sendReminder")
    public void sendReminder() throws JsonProcessingException
    {
    	List<Task> tasks = taskService.getAllTask();
    	tasks = tasks.stream().map(t -> { t.setUser(userClient.getUserById((long)t.getUserId()).getBody()); return t;}).collect(Collectors.toList());
    	List<EmailRequest> emailRequests = new ArrayList<EmailRequest>();
    	for(Task task : tasks)
    	{
    		if(task.getStatus().equals(TaskStatus.COMPLETED))
    		{
    			continue;
    		}
    		EmailRequest e = new EmailRequest();
    		e.setTo(task.getUser().getEmail());
    		e.setSubject("Task Status");
    		e.setMassage(task.toString());
    		emailRequests.add(e);
    	}
    	kafkaService.sendRemider(emailRequests);
    	
    }
    
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable int id) {
    	Task task = taskService.getTaskById(id);
    	if(task==null)
    	{
    		throw new TaskNotFoundException("Task is not found");
    	}
        return task;
    }
    
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable int id, @RequestBody Task task) throws JsonProcessingException {
    	User user = userClient.getUserById((long)task.getUserId()).getBody();
    	EmailRequest emailRequest = new EmailRequest(user.getEmail(),"status of task updated","current status of stask is :"+task.getStatus());
    	kafkaService.sendMail(emailRequest);
        return taskService.updateTask(id, task);
    }
    
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
    }
    
    @GetMapping("/user/{userId}")
    public List<Task> getTasksByUserId(@PathVariable int userId) {
    	List<Task> task= taskService.getTasksByUserId(userId);
    	if(task == null)
    	{
    		throw new TaskNotFoundException("Task is not found with the userId "+userId);
    	}
        return task;
    }
    @GetMapping("/project/{projectId}")
    public List<Task> getTaskByProjectId(@PathVariable int projectId)
    {
    	List<Task> task = taskService.getTasksByProjectId(projectId);
    	if(task == null)
    	{
    		throw new TaskNotFoundException("Task is not found with the projectId "+projectId);
    	}
    	return task;
    }
}
