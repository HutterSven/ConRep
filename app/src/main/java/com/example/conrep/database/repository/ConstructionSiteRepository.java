package com.example.conrep.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.conrep.database.async.constructionSite.CreateConstructionSite;
import com.example.conrep.database.async.constructionSite.DeleteConstructionSite;
import com.example.conrep.database.async.constructionSite.UpdateConstructionSite;
import com.example.conrep.database.async.constructionSite.UpdateHours;
import com.example.conrep.database.entity.ConstructionSiteEntity;
import com.example.conrep.BaseApp;
import com.example.conrep.database.firebase.ConstructionSiteListLiveData;
import com.example.conrep.database.firebase.ConstructionSiteLiveData;
import com.example.conrep.ui.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class ConstructionSiteRepository {

    private static ConstructionSiteRepository instance;
    public CreateConstructionSite ccs;

    public ConstructionSiteRepository() {

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

    public LiveData<ConstructionSiteEntity> getConstructionSite(final String constructionSiteId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("constructionSites")
                .child(constructionSiteId);
        return new ConstructionSiteLiveData(reference);
    }

    // todo ?????
    public ConstructionSiteEntity getConstructionSiteNameNonLive(final String name) {
        return null;//.getDatabase().constructionSiteDao().getByIdNonLive(name);
    }

    //todo how
    public LiveData<ConstructionSiteEntity> getConstructionSiteByName(String name) {
        return null;//((BaseApp) application).getDatabase().constructionSiteDao().getByName(name);
    }

    public static LiveData<List<ConstructionSiteEntity>> getConstructionSites() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("constructionSites");
        return new ConstructionSiteListLiveData(reference);
    }

    // todo ?????
    public List<ConstructionSiteEntity> getConstructionSitesNonLive() {
        return null;//((BaseApp) application).getDatabase().constructionSiteDao().getAllNonLive();
    }

    public void insert(final ConstructionSiteEntity constructionSite, OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("constructionSites").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("constructionSites")
                .child(id)
                .setValue(constructionSite, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final ConstructionSiteEntity constructionSite, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("constructionSites")
                .child(constructionSite.getSiteID())
                .updateChildren(constructionSite.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final ConstructionSiteEntity constructionSite, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("constructionSites")
                .child(constructionSite.getSiteID())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
