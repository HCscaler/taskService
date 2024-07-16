package com.knowit.taskService.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {

	private String msg;
	private LocalDateTime timeStamp;
	
}
