package com.knowit.taskService.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.knowit.taskService.entities.User;


@FeignClient(name = "AuthService")
public interface UserClient {

	@GetMapping("/api/auth/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id);
}
