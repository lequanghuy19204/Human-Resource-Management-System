package com.hrms.model;

import java.util.Date;
import java.util.List;

public class TrainingProgram {
    private String _id;
    private String name;
    private String description;
    private Date start;
    private Date end;
    private String location;
    private String trainer_id;
    private List<String> participant_ids;
    private String company_id;

    // Constructor
    public TrainingProgram() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(String trainer_id) {
        this.trainer_id = trainer_id;
    }

    public List<String> getParticipant_ids() {
        return participant_ids;
    }

    public void setParticipant_ids(List<String> participant_ids) {
        this.participant_ids = participant_ids;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }
}