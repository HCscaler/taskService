package com.knowit.taskService.services;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.knowit.taskService.entities.Comment;

@FeignClient(url = "http://localhost:5000",value="commentService")
public interface CommentClient {

	@GetMapping("/projects/{projectId}/tasks/{taskId}/comments")
	List<Comment> getComment(@PathVariable int projectId ,@PathVariable int taskId);
	
	@GetMapping("/project/{projectId}")
	List<Comment> getAllCommentOfProject(@PathVariable int projectId);
}
