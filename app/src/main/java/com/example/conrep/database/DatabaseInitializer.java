package com.example.conrep.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.conrep.database.report.ReportEntity;
import com.example.conrep.database.site.ConstructionSiteEntity;
import com.example.conrep.database.task.TaskEntity;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addReport(final AppDatabase db, ReportEntity report){
        db.reportDao().insertReport(report);
    }

    private static void addConstructionSite(final AppDatabase db, ConstructionSiteEntity constructionSite){
        db.constructionSiteDao().insertConstruction(constructionSite);
    }

    private static void addTask(final AppDatabase db, TaskEntity task){
        db.taskDao().insertTask(task);
    }


    private static void populateWithTestData(AppDatabase db){
        Log.i(TAG, "populating.");

        // TestSite
        ConstructionSiteEntity constructionSite = new ConstructionSiteEntity();
        constructionSite.setAddress("Terbinerstrasse 51");
        constructionSite.setCity("Visp");
        constructionSite.setHours(2);
        constructionSite.setOverseer("ABC2");
        constructionSite.setSiteID(1);
        constructionSite.setSiteName("TestSite2");
        addConstructionSite(db,
                constructionSite);

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        //TestTask
        TaskEntity task = new TaskEntity();
        task.setTaskID(1);
        task.setName("Build");
        task.setDescription("Build house");
        task.setDeadline("31.12.2021");
        task.setStatus(true);
        task.setSiteTask(1);
        addTask(db,
                task);

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        //TestReport
        ReportEntity report = new ReportEntity();
        report.setReportID(1);
        report.setWorkerName("DEF");
        report.setHours(2);
        report.setDate("30.03.2021");
        report.setSiteReport(1);
        report.setTaskReport(1);
        addReport(db,
                report);

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

        public void execute() {
            populateWithTestData(database);
        }
    }

}
