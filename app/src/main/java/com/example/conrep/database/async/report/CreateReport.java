package com.example.conrep.database.async.report;

import android.app.Application;
import android.os.AsyncTask;

import com.example.conrep.database.entity.ReportEntity;
import com.example.conrep.BaseApp;
import com.example.conrep.ui.util.OnAsyncEventListener;


public class CreateReport extends AsyncTask<ReportEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public long reportID;

    public CreateReport(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ReportEntity... params) {
        try {
            for (ReportEntity report : params)
                reportID = ((BaseApp) application).getDatabase().reportDao()
                        .insertReport(report);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}