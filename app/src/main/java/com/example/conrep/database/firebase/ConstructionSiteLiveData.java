package com.example.conrep.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.conrep.database.entity.ConstructionSiteEntity;
import com.example.conrep.database.entity.ReportEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ConstructionSiteLiveData extends LiveData<ConstructionSiteEntity> {

    private static final String TAG = "ConstructionSiteLiveData";

    private final DatabaseReference reference;
    private final ConstructionSiteLiveData.MyValueEventListener listener = new ConstructionSiteLiveData.MyValueEventListener();

    public ConstructionSiteLiveData(DatabaseReference ref) {
        this.reference = ref;
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
            if (dataSnapshot.exists()) {
                ConstructionSiteEntity entity = dataSnapshot.getValue(ConstructionSiteEntity.class);
                entity.setSiteID(dataSnapshot.getKey());
                setValue(entity);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
