package com.example.conrep.database.async.task;

import android.app.Application;
import android.os.AsyncTask;

import com.example.conrep.database.task.TaskEntity;
import com.example.conrep.ui.BaseApp;
import com.example.conrep.ui.util.OnAsyncEventListener;


public class DeleteTask extends AsyncTask<TaskEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteTask(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(TaskEntity... params) {
        try {
            for (TaskEntity task : params)
                ((BaseApp) application).getDatabase().taskDao()
                        .deleteTask(task);
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