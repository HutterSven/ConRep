package com.example.conrep.database.report;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.TypeConverters;

import com.example.conrep.database.DateTypeConverter;
import com.example.conrep.database.site.ConstructionSiteEntity;
import com.example.conrep.database.task.TaskEntity;

import java.util.Date;

@Entity(primaryKeys = {"reportID"}, foreignKeys = {@ForeignKey(entity = TaskEntity.class,
        parentColumns = "taskID",
        childColumns = "taskReport",
        onDelete = ForeignKey.SET_NULL), @ForeignKey(entity = ConstructionSiteEntity.class,
        parentColumns = "siteID",
        childColumns = "siteReport",
        onDelete = ForeignKey.SET_NULL)})
@TypeConverters(DateTypeConverter.class)
public class ReportEntity {

    private int reportID;
    private int hours;
    private String workerName;
    private int taskReport;
    private int siteReport;
    private String date;

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public int getTaskReport() {
        return taskReport;
    }

    public void setTaskReport(int taskReport) {
        this.taskReport = taskReport;
    }

    public int getSiteReport() {
        return siteReport;
    }

    public void setSiteReport(int siteReport) {
        this.siteReport = siteReport;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
