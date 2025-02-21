package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "performance")
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String employeeId; // Tham chiếu đến Employee

    @Column(nullable = false)
    private double performanceScore; // Điểm hiệu suất

    @Column(nullable = false)
    private double completeRate; // Tỷ lệ hoàn thành công việc

    @Column(nullable = false)
    private double ontimeRate; // Tỷ lệ đúng hạn

    @Column(nullable = false)
    private int qualityScore; // Điểm chất lượng

    // Constructors
    public Performance() {}

    public Performance(String employeeId, double performanceScore, double completeRate, double ontimeRate, int qualityScore) {
        this.employeeId = employeeId;
        this.performanceScore = performanceScore;
        this.completeRate = completeRate;
        this.ontimeRate = ontimeRate;
        this.qualityScore = qualityScore;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public double getPerformanceScore() { return performanceScore; }
    public void setPerformanceScore(double performanceScore) { this.performanceScore = performanceScore; }

    public double getCompleteRate() { return completeRate; }
    public void setCompleteRate(double completeRate) { this.completeRate = completeRate; }

    public double getOntimeRate() { return ontimeRate; }
    public void setOntimeRate(double ontimeRate) { this.ontimeRate = ontimeRate; }

    public int getQualityScore() { return qualityScore; }
    public void setQualityScore(int qualityScore) { this.qualityScore = qualityScore; }
}
