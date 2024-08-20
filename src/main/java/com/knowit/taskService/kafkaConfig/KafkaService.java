package com.knowit.taskService.kafkaConfig;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowit.taskService.entities.EmailRequest;
import com.knowit.taskService.entities.Task;

@Service

public class KafkaService {
	
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	public boolean sendMail(EmailRequest emailRequest) throws JsonProcessingException
	{
		String email = objectMapper.writeValueAsString(emailRequest);
		this.kafkaTemplate.send(Constant.sendMail,email);
		System.out.println("emailRequest Produce :"+email);
		return true;
	}
	public boolean getAllTask(List<Task> tasks) throws JsonProcessingException
	{
		String task = objectMapper.writeValueAsString(tasks);
		this.kafkaTemplate.send(Constant.getAllTask,task);
		System.out.println(task);
		return true;
	}
	public boolean sendRemider(List<EmailRequest> emailRequests) throws JsonProcessingException
	{
		String sendReminder = objectMapper.writeValueAsString(emailRequests);
		this.kafkaTemplate.send(Constant.sendReminder,sendReminder);
		return true;
	}
}
