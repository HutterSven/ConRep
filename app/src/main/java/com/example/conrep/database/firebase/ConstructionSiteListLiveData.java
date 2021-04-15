package com.example.conrep.database.firebase;

import androidx.lifecycle.LiveData;

import com.example.conrep.database.entity.ConstructionSiteEntity;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ConstructionSiteListLiveData extends LiveData<List<ConstructionSiteEntity>> {
    public ConstructionSiteListLiveData(DatabaseReference reference) {
    }
}
