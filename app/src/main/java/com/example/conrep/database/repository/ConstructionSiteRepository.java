package com.example.conrep.database.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.conrep.database.entity.ConstructionSiteEntity;
import com.example.conrep.BaseApp;
import com.example.conrep.database.firebase.ConstructionSiteListLiveData;
import com.example.conrep.database.firebase.ConstructionSiteLiveData;
import com.example.conrep.ui.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class ConstructionSiteRepository {

    private static ConstructionSiteRepository instance;

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

    public static LiveData<List<ConstructionSiteEntity>> getConstructionSites() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("constructionSites");
        return new ConstructionSiteListLiveData(reference);
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

    public void updateHours(final ConstructionSiteEntity constructionSite, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("constructionSites")
                .child(constructionSite.getSiteID()).child("hours").setValue(constructionSite.getHours());
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
