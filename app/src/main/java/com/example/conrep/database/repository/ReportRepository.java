package com.example.conrep.database.repository;

import androidx.lifecycle.LiveData;

import com.example.conrep.database.firebase.ReportListLiveData;
import com.example.conrep.database.firebase.ReportLiveData;
import com.example.conrep.database.entity.ReportEntity;
import com.example.conrep.ui.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class ReportRepository {

    private static ReportRepository instance;

    private ReportRepository() {

    }

    public static ReportRepository getInstance() {
        if (instance == null) {
            synchronized (ReportRepository.class) {
                if (instance == null) {
                    instance = new ReportRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<ReportEntity> getReport(final String reportId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("reports")
                .child(reportId);
        return new ReportLiveData(reference);
    }

    public LiveData<List<ReportEntity>> getReports() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("reports");
        return new ReportListLiveData(reference);
    }

    public void insert(final ReportEntity report, OnAsyncEventListener callback ) {
        String id = FirebaseDatabase.getInstance().getReference("reports").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("reports")
                .child(id)
                .setValue(report, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final ReportEntity report, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("reports")
                .child(report.getReportID())
                .updateChildren(report.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final ReportEntity report, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("reports")
                .child(report.getReportID())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}
