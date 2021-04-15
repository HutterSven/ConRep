package com.example.conrep.database.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class TaskEntity {

    @Exclude
    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSiteTask() {
        return siteTask;
    }

    public void setSiteTask(String siteTask) {
        this.siteTask = siteTask;
    }

    private String taskID;
    private String name;
    private String description;
    private String deadline;
    private boolean status;
    private String siteTask;

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("taskID", taskID);
        result.put("name", name);
        result.put("description", description);
        result.put("deadline", deadline);
        result.put("status", status);
        result.put("siteTask", siteTask);
        return result;
    }

}
