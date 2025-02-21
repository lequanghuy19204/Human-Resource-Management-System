package com.example.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;
    private String description;

    @ElementCollection
    private List<String> assignedTo; // Danh sách ID nhân viên

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    // Getters và Setters
}
