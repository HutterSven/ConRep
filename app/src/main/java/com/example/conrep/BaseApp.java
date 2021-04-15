package com.example.conrep;

import android.app.Application;

import com.example.conrep.database.repository.ConstructionSiteRepository;
import com.example.conrep.database.repository.ReportRepository;
import com.example.conrep.database.repository.TaskRepository;
import com.google.firebase.database.FirebaseDatabase;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public FirebaseDatabase getDatabase() {
        return FirebaseDatabase.getInstance();
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
