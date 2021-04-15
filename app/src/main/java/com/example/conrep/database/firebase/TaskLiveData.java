package com.example.conrep.database.firebase;

import androidx.lifecycle.LiveData;

import com.example.conrep.database.entity.TaskEntity;
import com.google.firebase.database.DatabaseReference;

public class TaskLiveData extends LiveData<TaskEntity> {
    public TaskLiveData(DatabaseReference reference) {
    }
}
