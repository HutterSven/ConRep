package com.example.conrep.database.async.constructionSite;

import android.app.Application;
import android.os.AsyncTask;

import com.example.conrep.database.site.ConstructionSiteEntity;
import com.example.conrep.BaseApp;
import com.example.conrep.ui.util.OnAsyncEventListener;


public class UpdateConstructionSite extends AsyncTask<ConstructionSiteEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateConstructionSite(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ConstructionSiteEntity... params) {
        try {
            for (ConstructionSiteEntity site : params)
                ((BaseApp) application).getDatabase().constructionSiteDao()
                        .updateConstruction(site);
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