package com.hrms.model;

import java.util.List;

public class Task {
    private String _id; // ObjectId as String
    private String name;
    private String description;
    private List<String> assigned_to; // List of ObjectId references to Employee
    private String status; // Enum: IN_PROGRESS, COMPLETED, etc.

    public Task() { }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAssigned_to() {
        return assigned_to;
    }

    public void setAssigned_to(List<String> assigned_to) {
        this.assigned_to = assigned_to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}