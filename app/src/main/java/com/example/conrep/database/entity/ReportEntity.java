package com.example.conrep.database.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ReportEntity {

    private String reportID;
    private int hours;
    private String workerName;
    private String taskReport;
    private String siteReport;
    private String date;

    public ReportEntity(){
    }

    @Exclude
    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
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

    public String getTaskReport() {
        return taskReport;
    }

    public void setTaskReport(String taskReport) {
        this.taskReport = taskReport;
    }

    public String getSiteReport() {
        return siteReport;
    }

    public void setSiteReport(String siteReport) {
        this.siteReport = siteReport;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("reportID", reportID);
        result.put("hours", hours);
        result.put("workerName", workerName);
        result.put("taskReport", taskReport);
        result.put("siteReport", siteReport);
        result.put("date", date);

        return result;
    }
}
