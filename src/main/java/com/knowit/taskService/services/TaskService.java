package com.knowit.taskService.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knowit.taskService.entities.Task;
import com.knowit.taskService.repositories.TaskRepository;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    
    public Task createTask(Task task) {
        return taskRepository.save(task);
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
        return taskRepository.findByUserId(userId);
    }
    public List<Task> getTasksByProjectId(int projectId)
    {
    	return taskRepository.findByProjectId(projectId);
    }
}
