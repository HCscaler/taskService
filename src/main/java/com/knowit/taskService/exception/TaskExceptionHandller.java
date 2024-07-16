package com.knowit.taskService.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TaskExceptionHandller extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception
	{
		System.out.println("In TaskExceptionHandller");
		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(),LocalDateTime.now());
		ResponseEntity<Object> responseEntity = new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
		return responseEntity;
	}
	@ExceptionHandler(TaskNotFoundException.class)
	public final ResponseEntity<Object> handleTaskNotFoundException(Exception ex, WebRequest request) throws Exception
	{
		System.out.println("In TaskExceptionHandller");
		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(),LocalDateTime.now());
		ResponseEntity<Object> responseEntity = new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
		return responseEntity;
	}

}
