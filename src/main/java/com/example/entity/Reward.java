package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reward")
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String employeeId; // Tham chiếu đến Employee

    @Column(nullable = false)
    private int completedTasks; // Số nhiệm vụ hoàn thành

    @Column(nullable = false)
    private double performanceScore; // Điểm hiệu suất

    @Column(nullable = false)
    private double reward; // Tiền thưởng

    @Column(nullable = false)
    private String type; // Loại thưởng (MONEY, BONUS, ...)

    // Constructors
    public Reward() {}

    public Reward(String employeeId, int completedTasks, double performanceScore, double reward, String type) {
        this.employeeId = employeeId;
        this.completedTasks = completedTasks;
        this.performanceScore = performanceScore;
        this.reward = reward;
        this.type = type;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public int getCompletedTasks() { return completedTasks; }
    public void setCompletedTasks(int completedTasks) { this.completedTasks = completedTasks; }

    public double getPerformanceScore() { return performanceScore; }
    public void setPerformanceScore(double performanceScore) { this.performanceScore = performanceScore; }

    public double getReward() { return reward; }
    public void setReward(double reward) { this.reward = reward; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
