package com.knowit.taskService.kafkaConfig;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

	@Bean
	public NewTopic topic() {
		return TopicBuilder.name(Constant.sendMail).build();
	}
	@Bean
	public NewTopic getAllTask()
	{
		return TopicBuilder.name(Constant.getAllTask).build();
	}
	@Bean
	public NewTopic sendReminder()
	{
		return TopicBuilder.name(Constant.sendReminder).build();
	}
	@Bean
	public NewTopic tasks()
	{
		return TopicBuilder.name("tasks").build();
	}
	
}
