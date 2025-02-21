package com.example.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String employeeId; // Tham chiếu đến Employee

    @Column(nullable = false)
    private int workingDays; // Số ngày làm việc

    @Column(nullable = false)
    private double salary; // Lương

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date paymentDate; // Ngày thanh toán

    // Constructors
    public Salary() {}

    public Salary(String employeeId, int workingDays, double salary, Date paymentDate) {
        this.employeeId = employeeId;
        this.workingDays = workingDays;
        this.salary = salary;
        this.paymentDate = paymentDate;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public int getWorkingDays() { return workingDays; }
    public void setWorkingDays(int workingDays) { this.workingDays = workingDays; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }
}
