package com.example.conrep.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.conrep.database.entity.TaskEntity;
import com.example.conrep.database.firebase.TaskListLiveData;
import com.example.conrep.database.firebase.TaskLiveData;
import com.example.conrep.ui.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TaskRepository {
    private static TaskRepository instance;


    public static TaskRepository getInstance() {
        if (instance == null) {
            synchronized (TaskRepository.class) {
                if (instance == null) {
                    instance = new TaskRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<TaskEntity> getTask(final String taskId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Tasks")
                .child(taskId);
        return new TaskLiveData(reference);
    }

    public LiveData<List<TaskEntity>> getTasks() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("tasks");
        return new TaskListLiveData(reference);
    }

    public LiveData<List<TaskEntity>> getTasksBySite(String siteID) {

        // todo get reference of tasks with site id return new TaskListLiveData(reference);
        return null;
    }


    public void insert(final TaskEntity task, OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("tasks").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("tasks")
                .child(id)
                .setValue(task, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final TaskEntity task, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("tasks")
                .child(task.getTaskID())
                .updateChildren(task.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final TaskEntity task, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("tasks")
                .child(task.getTaskID())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void changeStatus(TaskEntity task, OnAsyncEventListener callback, Application application) {
        FirebaseDatabase.getInstance()
                .getReference("tasks")
                .child(task.getTaskID())
                .updateChildren(task.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
