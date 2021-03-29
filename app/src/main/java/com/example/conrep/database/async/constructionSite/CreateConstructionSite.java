package com.example.conrep.database.async.constructionSite;

import android.app.Application;
import android.os.AsyncTask;

import com.example.conrep.database.site.ConstructionSiteEntity;
import com.example.conrep.ui.BaseApp;
import com.example.conrep.ui.util.OnAsyncEventListener;


public class CreateConstructionSite extends AsyncTask<ConstructionSiteEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateConstructionSite(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ConstructionSiteEntity... params) {
        try {
            for (ConstructionSiteEntity site : params)
                ((BaseApp) application).getDatabase().constructionSiteDao()
                        .insertConstruction(site);
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