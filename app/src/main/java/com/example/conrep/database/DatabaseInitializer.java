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
        ConstructionSiteEntity constructionSite1 = new ConstructionSiteEntity();
        constructionSite1.setAddress("Terbinerstrasse 51");
        constructionSite1.setCity("Visp");
        constructionSite1.setHours(0);
        constructionSite1.setOverseer("ABC");
        constructionSite1.setSiteID(1);
        constructionSite1.setSiteName("TestSite1");
        addConstructionSite(db,
                constructionSite1);

        ConstructionSiteEntity constructionSite2 = new ConstructionSiteEntity();
        constructionSite2.setAddress("Kleegärtenstrase 5");
        constructionSite2.setCity("Visp");
        constructionSite2.setHours(0);
        constructionSite2.setOverseer("ABC");
        constructionSite2.setSiteID(2);
        constructionSite2.setSiteName("TestSite2");
        addConstructionSite(db,
                constructionSite2);

        ConstructionSiteEntity constructionSite3 = new ConstructionSiteEntity();
        constructionSite3.setAddress("Gliserallee 51");
        constructionSite3.setCity("Brig-Glis");
        constructionSite3.setHours(0);
        constructionSite3.setOverseer("ABC");
        constructionSite3.setSiteID(3);
        constructionSite3.setSiteName("TestSite3");
        addConstructionSite(db,
                constructionSite3);

        ConstructionSiteEntity constructionSit4 = new ConstructionSiteEntity();
        constructionSit4.setAddress("Case postale 80");
        constructionSit4.setCity("Sierre");
        constructionSit4.setHours(0);
        constructionSit4.setOverseer("ABC");
        constructionSit4.setSiteID(4);
        constructionSit4.setSiteName("TestSite4");
        addConstructionSite(db,
                constructionSit4);

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        //TestTask
        TaskEntity task1 = new TaskEntity();
        task1.setTaskID(1);
        task1.setName("Build");
        task1.setDescription("Build house");
        task1.setDeadline("31.12.2021");
        task1.setStatus(true);
        task1.setSiteTask(1);
        addTask(db,
                task1);

        TaskEntity task2 = new TaskEntity();
        task2.setTaskID(1);
        task2.setName("Review");
        task2.setDescription("check for mistakes");
        task2.setDeadline("31.12.2021");
        task2.setStatus(true);
        task2.setSiteTask(1);
        addTask(db,
                task2);

        TaskEntity task3 = new TaskEntity();
        task3.setTaskID(1);
        task3.setName("Build");
        task3.setDescription("Build house");
        task3.setDeadline("31.12.2021");
        task3.setStatus(true);
        task3.setSiteTask(2);
        addTask(db,
                task3);

        TaskEntity task4 = new TaskEntity();
        task4.setTaskID(1);
        task4.setName("Build");
        task4.setDescription("Build house");
        task4.setDeadline("31.12.2021");
        task4.setStatus(true);
        task4.setSiteTask(3);
        addTask(db,
                task4);

        TaskEntity task5 = new TaskEntity();
        task5.setTaskID(1);
        task5.setName("Build");
        task5.setDescription("Build house");
        task5.setDeadline("31.12.2021");
        task5.setStatus(true);
        task5.setSiteTask(4);
        addTask(db,
                task5);

        TaskEntity task6 = new TaskEntity();
        task6.setTaskID(1);
        task6.setName("Review");
        task6.setDescription("check for mistakes");
        task6.setDeadline("31.12.2021");
        task6.setStatus(true);
        task6.setSiteTask(4);
        addTask(db,
                task4);


        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        //TestReport
        ReportEntity report1 = new ReportEntity();
        report1.setReportID(1);
        report1.setWorkerName("DEF");
        report1.setHours(2);
        report1.setDate("30.03.2021");
        report1.setSiteReport(1);
        report1.setTaskReport(1);
        addReport(db,
                report1);

        ReportEntity report2 = new ReportEntity();
        report2.setReportID(2);
        report2.setWorkerName("DEF");
        report2.setHours(5);
        report2.setDate("22.04.2021");
        report2.setSiteReport(2);
        report2.setTaskReport(3);
        addReport(db,
                report2);

        ReportEntity report3 = new ReportEntity();
        report3.setReportID(3);
        report3.setWorkerName("DEF");
        report3.setHours(10);
        report3.setDate("21.03.2021");
        report3.setSiteReport(3);
        report3.setTaskReport(4);
        addReport(db,
                report3);

        ReportEntity report4 = new ReportEntity();
        report4.setReportID(4);
        report4.setWorkerName("DEF");
        report4.setHours(3);
        report4.setDate("01.04.2021");
        report4.setSiteReport(4);
        report4.setTaskReport(5);
        addReport(db,
                report4);

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
