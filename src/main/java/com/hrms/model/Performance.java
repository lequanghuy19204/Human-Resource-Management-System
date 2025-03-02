package com.hrms.model;

public class Performance {
    private String _id; // ObjectId as String
    private String employee_id; // ObjectId reference to Employee
    private double performance_score; // Double
    private double complete_rate; // Double
    private double ontime_rate; // Double
    private int quality_score; // Integer

    public Performance() { }

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

    public double getPerformance_score() {
        return performance_score;
    }

    public void setPerformance_score(double performance_score) {
        this.performance_score = performance_score;
    }

    public double getComplete_rate() {
        return complete_rate;
    }

    public void setComplete_rate(double complete_rate) {
        this.complete_rate = complete_rate;
    }

    public double getOntime_rate() {
        return ontime_rate;
    }

    public void setOntime_rate(double ontime_rate) {
        this.ontime_rate = ontime_rate;
    }

    public int getQuality_score() {
        return quality_score;
    }

    public void setQuality_score(int quality_score) {
        this.quality_score = quality_score;
    }
}