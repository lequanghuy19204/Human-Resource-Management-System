package com.hrms.model;

import java.util.List;

public class Employee {
    private String _id;
    private String name;
    private String position_name;
    private List<String> permissions;
    private String organization_id;
    private String company_id;
    private String manager_id;
    private String phone;
    private String account_id;
    private double base_salary;
    private int task_count;
    private int completed_tasks;
    private int ontime_tasks;
    private double quality_score;
    private double performance_score;

    // Constructor
    public Employee() {
    }

    // Getters and Setters
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

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getManager_id() {
        return manager_id;
    }

    public void setManager_id(String manager_id) {
        this.manager_id = manager_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public double getBase_salary() {
        return base_salary;
    }

    public void setBase_salary(double base_salary) {
        this.base_salary = base_salary;
    }

    public int getTask_count() {
        return task_count;
    }

    public void setTask_count(int task_count) {
        this.task_count = task_count;
    }

    public int getCompleted_tasks() {
        return completed_tasks;
    }

    public void setCompleted_tasks(int completed_tasks) {
        this.completed_tasks = completed_tasks;
    }

    public int getOntime_tasks() {
        return ontime_tasks;
    }

    public void setOntime_tasks(int ontime_tasks) {
        this.ontime_tasks = ontime_tasks;
    }

    public double getQuality_score() {
        return quality_score;
    }

    public void setQuality_score(double quality_score) {
        this.quality_score = quality_score;
    }

    public double getPerformance_score() {
        return performance_score;
    }

    public void setPerformance_score(double performance_score) {
        this.performance_score = performance_score;
    }
}