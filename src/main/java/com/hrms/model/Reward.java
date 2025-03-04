package com.hrms.model;

public class Reward {
    private String _id; // ObjectId as String
    private String employee_id; // ObjectId reference to Employee
    private int completed_tasks; // Integer
    private double performance_score; // Double
    private double reward; // Double
    private String type; // Enum: MONEY, BONUS, etc.

    public Reward() { }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public int getCompleted_tasks() {
        return completed_tasks;
    }

    public void setCompleted_tasks(int completed_tasks) {
        this.completed_tasks = completed_tasks;
    }

    public double getPerformance_score() {
        return performance_score;
    }

    public void setPerformance_score(double performance_score) {
        this.performance_score = performance_score;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}