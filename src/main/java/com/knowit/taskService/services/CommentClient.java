package com.knowit.taskService.services;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.knowit.taskService.entities.Comment;

@FeignClient(name="commentService",value="commentService")
public interface CommentClient {

	@GetMapping("comment/projects/{projectId}/tasks/{taskId}/comments")
	List<Comment> getComment(@PathVariable int projectId ,@PathVariable int taskId);
	
}
