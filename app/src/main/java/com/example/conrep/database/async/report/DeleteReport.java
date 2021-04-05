package com.example.conrep.database.async.report;

import android.app.Application;
import android.os.AsyncTask;

import com.example.conrep.database.report.ReportEntity;
import com.example.conrep.BaseApp;
import com.example.conrep.ui.util.OnAsyncEventListener;


public class DeleteReport extends AsyncTask<ReportEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteReport(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ReportEntity... params) {
        try {
            for (ReportEntity report : params)
                ((BaseApp) application).getDatabase().reportDao()
                        .deleteReport(report);
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