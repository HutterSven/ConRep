package com.example.conrep.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.conrep.database.async.constructionSite.CreateConstructionSite;
import com.example.conrep.database.async.constructionSite.DeleteConstructionSite;
import com.example.conrep.database.async.constructionSite.UpdateConstructionSite;
import com.example.conrep.database.site.ConstructionSiteEntity;
import com.example.conrep.BaseApp;
import com.example.conrep.ui.util.OnAsyncEventListener;

import java.util.List;


public class ConstructionSiteRepository {

    private static ConstructionSiteRepository instance;

    private ConstructionSiteRepository() {

    }

    public static ConstructionSiteRepository getInstance() {
        if (instance == null) {
            synchronized (ConstructionSiteRepository.class) {
                if (instance == null) {
                    instance = new ConstructionSiteRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<ConstructionSiteEntity> getConstructionSite(final int constructionSiteId, Application application) {
        return ((BaseApp) application).getDatabase().constructionSiteDao().getById(constructionSiteId);
    }

    public LiveData<List<ConstructionSiteEntity>> getConstructionSites(Application application) {
        return ((BaseApp) application).getDatabase().constructionSiteDao().getAll();
    }

    public void insert(final ConstructionSiteEntity constructionSite, OnAsyncEventListener callback,
                       Application application) {
        new CreateConstructionSite(application, callback).execute( constructionSite);
    }

    public void update(final ConstructionSiteEntity constructionSite, OnAsyncEventListener callback,
                       Application application) {
        new UpdateConstructionSite(application, callback).execute( constructionSite);
    }

    public void delete(final ConstructionSiteEntity constructionSite, OnAsyncEventListener callback,
                       Application application) {
        new DeleteConstructionSite(application, callback).execute( constructionSite);
    }
}
