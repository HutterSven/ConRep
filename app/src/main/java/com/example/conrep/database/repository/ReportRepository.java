package com.example.conrep.database.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.conrep.database.GmailSender;
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

    public void insert(final ReportEntity report, String receiver, String siteName, OnAsyncEventListener callback ) {
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

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    GmailSender sender = new GmailSender("conrep.app@gmail.com",
                            "Con.Rep123");
                    try {
                        sender.sendMail("Change on your Construction Site",
                                "Hello, \n\nA report has just been created on the Site: " + siteName + "\n\n"+ report.getWorkerName()+ " worked for " + report.getHours() + " hours\n\nSincerely, \nYour ConRep Team",
                                "conrep.app@gmail.com",
                                receiver);
                        Log.i("SendMail", "E-Mail sent");
                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }
                } catch (Exception e) {
                    Log.e("SendMailThread", e.getMessage(), e);
                }
            }
        });

        thread.start();
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
