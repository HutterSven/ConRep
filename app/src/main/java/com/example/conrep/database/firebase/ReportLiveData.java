package com.example.conrep.database.firebase;

import androidx.lifecycle.LiveData;

import com.example.conrep.database.entity.ReportEntity;
import com.google.firebase.database.DatabaseReference;

public class ReportLiveData extends LiveData<ReportEntity> {
    public ReportLiveData(DatabaseReference reference) {
    }
}
