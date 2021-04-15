package com.example.conrep.database.firebase;

import androidx.lifecycle.LiveData;

import com.example.conrep.database.entity.TaskEntity;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class TaskListLiveData extends LiveData<List<TaskEntity>> {
    public TaskListLiveData(DatabaseReference reference) {
    }
}
