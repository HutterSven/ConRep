package com.example.conrep.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.conrep.database.report.ReportEntity;
import com.example.conrep.database.site.ConstructionSiteEntity;
import com.example.conrep.database.task.TaskEntity;

import java.util.Date;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);    //PopulateDbAsync ist eine interne klasse die hier unten gemacht wird
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

        //resetten
        db.constructionSiteDao().deleteAll();

        // TestSite
        ConstructionSiteEntity constructionSite = new ConstructionSiteEntity();
        constructionSite.setAddress("testStreet");
        constructionSite.setCity("testCity");
        constructionSite.setHours(2);
        constructionSite.setOverseer("ABC");
        constructionSite.setSiteID(1);
        constructionSite.setSiteName("TestSite");
        addConstructionSite(db,
                constructionSite);

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        //resetten
        db.taskDao().deleteAll();

        //TestTask
        TaskEntity task = new TaskEntity();
        task.setTaskID(1);
        task.setName("Build");
        task.setDescription("Build house");
        task.setDeadline(new Date("2021-12-31"));
        task.setSiteTask(1);
        addTask(db,
                task);

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        //resetten
        db.reportDao().deleteAll();

        //TestReport
        ReportEntity report = new ReportEntity();
        report.setReportID(1);
        report.setWorkerName("DEF");
        report.setHours(2);
        report.setDate(new Date("2021-03-28"));
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
        }
    }

}
