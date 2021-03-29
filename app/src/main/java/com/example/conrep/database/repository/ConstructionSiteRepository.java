package com.example.conrep.database.repository;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.example.conrep.database.site.ConstructionSiteEntity;
import com.example.conrep.ui.BaseApp;
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

    @SuppressWarnings("unchecked")
    public void transaction(final ConstructionSiteEntity sender, final ConstructionSiteEntity recipient,
                            OnAsyncEventListener callback, Application application) {
        new Transaction(application, callback).execute(new Pair<>(sender, recipient));
    }
}
