package com.example.conrep.database.firebase;

import androidx.lifecycle.LiveData;

import com.example.conrep.database.entity.ConstructionSiteEntity;
import com.google.firebase.database.DatabaseReference;

public class ConstructionSiteLiveData extends LiveData<ConstructionSiteEntity> {
    public ConstructionSiteLiveData(DatabaseReference reference) {
    }
}
