package com.knowit.taskService.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.knowit.taskService.entities.Task;
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
	@Query("select task from Task task where task.userId = :userId")
	List<Task> findByUserId(@Param("userId") int userId);
	
	@Query("select task from Task task where task.userId = :projectId")
	List<Task> findByProjectId(@Param("projectId") int projectId);
}
