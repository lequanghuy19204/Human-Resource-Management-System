package com.hrms.model;

import java.util.Date;
import java.util.List;

public class Salary {
    private String _id; // Sử dụng String
    private String employee_id; // Sử dụng String
    private int working_days;
    private Date payment_date;
    private int overtime_hours;
    private int late_hours;
    private int absent_days;
    private String account_id;
    private int approved_leave_days;
    private transient double base_salary;
    private transient double sumSalary;

    public Salary() { }

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

    public int getWorking_days() {
        return working_days;
    }

    public void setWorking_days(int working_days) {
        this.working_days = working_days;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }

    public int getOvertime_hours() {
        return overtime_hours;
    }

    public void setOvertime_hours(int overtime_hours) {
        this.overtime_hours = overtime_hours;
    }

    public int getLate_hours() {
        return late_hours;
    }

    public void setLate_hours(int late_hours) {
        this.late_hours = late_hours;
    }

    public int getAbsent_days() {
        return absent_days;
    }

    public void setAbsent_days(int absent_days) {
        this.absent_days = absent_days;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public int getApproved_leave_days() {
        return approved_leave_days;
    }

    public void setApproved_leave_days(int approved_leave_days) {
        this.approved_leave_days = approved_leave_days;
    }

    public double getBase_salary() {
        return base_salary;
    }

    public void setBase_salary(double base_salary) {
        this.base_salary = base_salary;
    }

    public double getSumSalary() {
        return sumSalary;
    }

    public void setSumSalary(double sumSalary) {
        this.sumSalary = sumSalary;
    }
}