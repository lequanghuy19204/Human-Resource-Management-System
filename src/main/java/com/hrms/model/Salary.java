package com.hrms.model;

import java.util.Date;
import java.util.List;

public class Salary {
    private String _id; // Sử dụng String
    private String employee_id; // Sử dụng String
    private int working_days;
    private int salary;
    private Date payment_date;

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

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }
}