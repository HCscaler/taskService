package com.knowit.taskService.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.knowit.taskService.entities.EmailRequest;


@FeignClient(name = "notificationService")
public interface NotificationClient {

	@PostMapping("/email")
	public String EmailSender(@RequestBody EmailRequest emailRequest);
}
