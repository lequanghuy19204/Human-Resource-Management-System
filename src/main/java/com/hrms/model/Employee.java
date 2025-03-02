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
    private int overtime_hours;
    private int late_hours;
    private int absent_days;
    private String account_id;

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
}