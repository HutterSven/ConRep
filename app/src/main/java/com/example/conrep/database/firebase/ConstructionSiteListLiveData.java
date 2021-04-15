package com.example.conrep.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.conrep.database.entity.ConstructionSiteEntity;
import com.example.conrep.database.entity.ConstructionSiteEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConstructionSiteListLiveData extends LiveData<List<ConstructionSiteEntity>> {
    private static final String TAG = "ConstructionSiteListLiveData";

    private final DatabaseReference reference;
    private final ConstructionSiteListLiveData.MyValueEventListener listener = new ConstructionSiteListLiveData.MyValueEventListener();

    public ConstructionSiteListLiveData(DatabaseReference ref) {
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
            setValue(toConstructionSiteList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<ConstructionSiteEntity> toConstructionSiteList(DataSnapshot snapshot) {
        List<ConstructionSiteEntity> clients = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            ConstructionSiteEntity entity = childSnapshot.getValue(ConstructionSiteEntity.class);
            entity.setSiteID(childSnapshot.getKey());
            clients.add(entity);
        }
        return clients;
    }
}
