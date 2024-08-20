package com.knowit.taskService.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowit.taskService.entities.Task;
import com.knowit.taskService.entities.User;
import com.knowit.taskService.repositories.TaskRepository;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private CommentClient commentClient;
    
    @Autowired
	public KafkaTemplate<String, String> kafkaTemplate;
 
    @Autowired
    ObjectMapper objectMapper;
    
	public static final String TOPIC = "tasks";
 
	public void sendTaskToKafka(Task task) throws JsonProcessingException {
		String str = objectMapper.writeValueAsString(task);
		kafkaTemplate.send("tasks", str);
		System.out.println("Tasks :"+task);
	}
    
    public TaskService(TaskRepository taskReposirository , CommentClient commentClient)
    {
    	super();
    	this.taskRepository=taskReposirository;
    	this.commentClient=commentClient;
    }
    
    public Task createTask(Task task) throws JsonProcessingException {
    	
    	Task task1 = taskRepository.save(task);
    	sendTaskToKafka(task1);
        return task1;
    }
    public List<Task> getAllTask()
    {
    	List<Task> task = taskRepository.findAll();
    	List<Task> newTask = task.stream().map(t -> {t.setCommnts(commentClient.getComment(t.getProjectId(), t.getId())); return t;}).collect(Collectors.toList());
    	return newTask;
    }
    public Task getTaskById(int id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + id));
    }
    
    public Task updateTask(int id, Task updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + id));
        
        
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setUserId(updatedTask.getUserId());
        task.setDueDate(updatedTask.getDueDate());
        task.setStatus(updatedTask.getStatus());
        
        return taskRepository.save(task);
    }
    
    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }
    
    public List<Task> getTasksByUserId(int userId) {
       // return taskRepository.findByUserId(userId);
    	List<Task> task = taskRepository.findByUserId(userId);
    	List<Task> newTask = task.stream().map(t -> {t.setCommnts(commentClient.getComment(t.getProjectId(), t.getId())); return t;}).collect(Collectors.toList());
    	return newTask;
    }
    public List<Task> getTasksByProjectId(int projectId)
    {
    	List<Task> task = taskRepository.findByProjectId(projectId);
    	List<Task> tasks = task.stream().map(t -> {t.setCommnts(commentClient.getComment(t.getProjectId(), t.getId())); return t;}).collect(Collectors.toList());
    	return tasks;
    }
}
