package com.example.conrep.ui;

import android.app.Application;

import com.example.conrep.database.AppDatabase;
import com.example.conrep.database.repository.ConstructionSiteRepository;
import com.example.conrep.database.repository.ReportRepository;
import com.example.conrep.database.repository.TaskRepository;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public ConstructionSiteRepository getConstructionSiteRepository() {
        return  ConstructionSiteRepository.getInstance();
    }

    public ReportRepository getReportRepository() {
        return ReportRepository.getInstance();
    }


    public TaskRepository getTaskRepository() {
        return TaskRepository.getInstance();
    }
}
