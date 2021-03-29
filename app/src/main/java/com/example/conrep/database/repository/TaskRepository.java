package com.example.conrep.database.repository;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.example.conrep.database.async.task.CreateTask;
import com.example.conrep.database.async.task.DeleteTask;
import com.example.conrep.database.async.task.UpdateTask;
import com.example.conrep.database.task.TaskEntity;
import com.example.conrep.ui.BaseApp;
import com.example.conrep.ui.util.OnAsyncEventListener;

import java.util.List;

public class TaskRepository {
    private static TaskRepository instance;

    private TaskRepository() {

    }

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

    public LiveData<TaskEntity> getTask(final int taskId, Application application) {
        return ((BaseApp) application).getDatabase().taskDao().getById(taskId);
    }

    public LiveData<List<TaskEntity>> getTasks(Application application) {
        return ((BaseApp) application).getDatabase().taskDao().getAll();
    }

    public void insert(final TaskEntity task, OnAsyncEventListener callback,
                       Application application) {
        new CreateTask(application, callback).execute(task);
    }

    public void update(final TaskEntity task, OnAsyncEventListener callback,
                       Application application) {
        new UpdateTask(application, callback).execute(task);
    }

    public void delete(final TaskEntity task, OnAsyncEventListener callback,
                       Application application) {
        new DeleteTask(application, callback).execute(task);
    }


}
