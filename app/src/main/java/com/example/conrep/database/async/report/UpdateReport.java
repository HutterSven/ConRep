package com.example.conrep.database.async.report;

import android.app.Application;
import android.os.AsyncTask;

import com.example.conrep.database.report.ReportEntity;
import com.example.conrep.ui.BaseApp;
import com.example.conrep.ui.util.OnAsyncEventListener;


public class UpdateReport extends AsyncTask<ReportEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateReport(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ReportEntity... params) {
        try {
            for (ReportEntity report : params)
                ((BaseApp) application).getDatabase().reportDao()
                        .updateReport(report);
        } catch (Exception e) {
            this.exception = e;
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