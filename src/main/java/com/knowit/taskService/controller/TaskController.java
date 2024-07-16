package com.knowit.taskService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knowit.taskService.entities.Task;
import com.knowit.taskService.exception.TaskNotFoundException;
import com.knowit.taskService.services.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;
   
    
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
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
    public Task updateTask(@PathVariable int id, @RequestBody Task task) {
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
