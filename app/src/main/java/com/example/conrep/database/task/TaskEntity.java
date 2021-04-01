package com.example.conrep.database.task;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.conrep.database.site.ConstructionSiteEntity;

import java.util.Date;

@Entity(foreignKeys = {@ForeignKey(entity = ConstructionSiteEntity.class,
        parentColumns = "siteID",
        childColumns = "siteTask",
        onDelete = ForeignKey.CASCADE)
})
public class TaskEntity {
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

    public int getSiteTask() {
        return siteTask;
    }

    public void setSiteTask(int siteTask) {
        this.siteTask = siteTask;
    }

    @PrimaryKey(autoGenerate = true)
    private int taskID;
    private String name;
    private String description;
    private String deadline;
    private boolean status;
    private int siteTask;

}
