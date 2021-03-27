package com.example.conrep.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.conrep.database.report.Report;
import com.example.conrep.database.site.ConstructionSite;
import com.example.conrep.database.task.Task;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);    //PopulateDbAsync ist eine interne klasse die hier unten gemacht wird
        task.execute();
    }

    private static void addReport(final AppDatabase db, int id, int wid){

        Report report = new Report();
        db.reportDao().insertReport(report);
    }

    private static void addConstructionSite(final AppDatabase db, final int id, final String constructionSitename, final String info){

        ConstructionSite constructionSite = new ConstructionSite();
        db.constructionSiteDao().insertConstruction(constructionSite);
    }

    private static void addTask(final AppDatabase db, int id, String name, String taskry){

        Task task = new Task();
        db.taskDao().insertTask(task);
    }


    private static void populateWithTestData(AppDatabase db){
        //resetten
        db.reportDao().deleteAll();
        /* Hier kommen die anfänglichen Favoriten rein falls es welche gibt.
         */
        //Testfavorite
        addReport(db,
                1,1 );

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        //resetten
        db.constructionSiteDao().deleteAll();

        addConstructionSite(db,
                1, "Visperterminen", "Europas höchster Weinberg.");
        addConstructionSite(db,
                2, "Varen","Die Weininsel im Wallis.");
        addConstructionSite(db,
                3, "Salgesch","Will wier ine räbe läbe.");
        addConstructionSite(db,
                4, "Siders","Wasser predigen, Wein trinken.");

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        //resetten
        db.taskDao().deleteAll();
        /* Hier kommen die Weine rein, via addTask die am Anfang da sein sollen
                */
        //Testwein
        addTask(db,
                1,"Geile Wein","Taskry Jean-pierre");

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
