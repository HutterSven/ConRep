package com.example.conrep.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.conrep.database.entity.ReportEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReportListLiveData extends LiveData<List<ReportEntity>> {

    private static final String TAG = "ReportListLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public ReportListLiveData(DatabaseReference ref) {
        reference = ref;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toReportList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<ReportEntity> toReportList(DataSnapshot snapshot) {
        List<ReportEntity> clients = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            ReportEntity entity = childSnapshot.getValue(ReportEntity.class);
            entity.setReportID(childSnapshot.getKey());
            clients.add(entity);
        }
        return clients;
    }
}
