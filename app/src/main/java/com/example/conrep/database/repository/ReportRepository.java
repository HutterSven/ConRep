package com.example.conrep.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.conrep.database.async.report.CreateReport;
import com.example.conrep.database.async.report.DeleteReport;
import com.example.conrep.database.async.report.UpdateReport;
import com.example.conrep.database.report.ReportEntity;
import com.example.conrep.BaseApp;
import com.example.conrep.ui.util.OnAsyncEventListener;

import java.util.List;


public class ReportRepository {

    private static ReportRepository instance;

    private ReportRepository() {

    }

    public static ReportRepository getInstance() {
        if (instance == null) {
            synchronized (ReportRepository.class) {
                if (instance == null) {
                    instance = new ReportRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<ReportEntity> getReport(final int reportId, Application application) {
        return ((BaseApp) application).getDatabase().reportDao().getById(reportId);
    }

    public LiveData<List<ReportEntity>> getReports(Application application) {
        return ((BaseApp) application).getDatabase().reportDao().getAll();
    }


    public void insert(final ReportEntity report, OnAsyncEventListener callback,
                       Application application) {
        new CreateReport(application, callback).execute(report);
    }

    public void update(final ReportEntity report, OnAsyncEventListener callback,
                       Application application) {
        new UpdateReport(application, callback).execute(report);
    }

    public void delete(final ReportEntity report, OnAsyncEventListener callback,
                       Application application) {
        new DeleteReport(application, callback).execute(report);
    }

}
