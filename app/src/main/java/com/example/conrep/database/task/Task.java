package com.example.conrep.database.task;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.conrep.database.site.ConstructionSite;

import java.util.Date;

@Entity(primaryKeys = {"taskID"}, foreignKeys = {@ForeignKey(entity = ConstructionSite.class,
        parentColumns = "siteID",
        childColumns = "siteID",
        onDelete = ForeignKey.SET_NULL)
})
public class Task {
    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getSiteID() {
        return siteID;
    }

    public void setSiteID(int siteID) {
        this.siteID = siteID;
    }

    private int taskID;
    private String name;
    private String description;
    private Date deadline;
    private boolean status;
    private int siteID;

}
