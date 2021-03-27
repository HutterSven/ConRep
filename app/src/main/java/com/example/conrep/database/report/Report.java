package com.example.conrep.database.report;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.conrep.database.task.Task;
import com.example.conrep.database.site.ConstructionSite;

import java.util.Date;

@Entity(primaryKeys = {"reportID"}, foreignKeys = {@ForeignKey(entity = Task.class,
        parentColumns = "taskID",
        childColumns = "taskID",
        onDelete = ForeignKey.SET_NULL), @ForeignKey(entity = ConstructionSite.class,
        parentColumns = "siteID",
        childColumns = "siteID",
        onDelete = ForeignKey.SET_NULL)})
public class Report {

    private int reportID;
    private int hours;
    private String workerName;
    private int taskID;
    private int siteID;
    private Date date;

}
