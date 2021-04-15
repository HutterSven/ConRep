package com.example.conrep.database.firebase;

import androidx.lifecycle.LiveData;

import com.example.conrep.database.entity.ReportEntity;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ReportListLiveData extends LiveData<List<ReportEntity>> {
    public ReportListLiveData(DatabaseReference reference) {
    }
}
