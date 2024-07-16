package com.knowit.taskService.entities;

import lombok.Data;

import jakarta.persistence.*;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.OPEN; // Default status is OPEN

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "due_date")
    private LocalDate dueDate;
    
    @Column(name="user_id")
    private int userId;

    @Column(name = "project_id")
    private Integer projectId;

    
}


