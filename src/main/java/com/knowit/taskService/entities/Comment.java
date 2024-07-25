package com.knowit.taskService.entities;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	    
	    private int id;
	    private int projectId;
	    private Integer taskId;
	    private String text;
	    private LocalDateTime timestamp;
	    private int userId;
	    
	}


