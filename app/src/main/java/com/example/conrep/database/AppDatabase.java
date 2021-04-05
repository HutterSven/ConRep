package com.example.conrep.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.conrep.database.dao.ConstructionSiteDao;
import com.example.conrep.database.dao.ReportDao;
import com.example.conrep.database.dao.TaskDao;
import com.example.conrep.database.report.ReportEntity;
import com.example.conrep.database.site.ConstructionSiteEntity;
import com.example.conrep.database.task.TaskEntity;

import java.util.concurrent.Executors;


@Database(entities = {ReportEntity.class, ConstructionSiteEntity.class, TaskEntity.class}, version = 1)
@TypeConverters(DateTypeConverter.class)

    public abstract class AppDatabase extends RoomDatabase {

        private static final String TAG = "AppDatabase";

        private static AppDatabase instance;

        private static final String DATABASE_NAME = "taskIndex-database";

        public abstract ReportDao reportDao();

        public abstract ConstructionSiteDao constructionSiteDao();

        public abstract TaskDao taskDao();

        private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

        public static AppDatabase getInstance(final Context context) {
            if (instance == null) {
                synchronized (AppDatabase.class) {
                    if (instance == null) {
                        instance = buildDatabase(context.getApplicationContext());
                        instance.updateDatabaseCreated(context.getApplicationContext());
                    }
                }
            }
            return instance;
        }

        private static AppDatabase buildDatabase(final Context appContext) {
            Log.i(TAG, "Database will be initialized.");
            return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadExecutor().execute(() -> {
                                AppDatabase database = AppDatabase.getInstance(appContext);
                                initializeDemoData(database);
                                // notify that the database was created and it's ready to be used
                                database.setDatabaseCreated();
                            });
                        }
                    }).build();
        }


        public static void initializeDemoData(final AppDatabase database) {
            Executors.newSingleThreadExecutor().execute(() -> {
                database.runInTransaction(() -> {
                    Log.i(TAG, "Wipe database.");
                    database.reportDao().deleteAll();
                    database.constructionSiteDao().deleteAll();
                    database.taskDao().deleteAll();

                    DatabaseInitializer.populateDatabase(database);
                });
            });
        }

        private void updateDatabaseCreated(final Context context) {
            if (context.getDatabasePath(DATABASE_NAME).exists()) {
                Log.i(TAG, "Database initialized.");
                setDatabaseCreated();
            }
        }

        private void setDatabaseCreated() {
            mIsDatabaseCreated.postValue(true);
        }

        public LiveData<Boolean> getDatabaseCreated() {
            return mIsDatabaseCreated;
        }

}
