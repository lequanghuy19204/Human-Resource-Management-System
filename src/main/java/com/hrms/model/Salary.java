package com.hrms.model;

import org.bson.types.ObjectId; // Thêm import này
import java.util.Date;

public class Salary {
    private ObjectId _id; // Sử dụng ObjectId
    private ObjectId employee_id; // Sử dụng ObjectId
    private int working_days;
    private double salary;
    private Date payment_date;

    public Salary() { }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(ObjectId employee_id) {
        this.employee_id = employee_id;
    }

    public int getWorking_days() {
        return working_days;
    }

    public void setWorking_days(int working_days) {
        this.working_days = working_days;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }
}