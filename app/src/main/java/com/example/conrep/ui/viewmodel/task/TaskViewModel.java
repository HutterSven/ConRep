package com.example.conrep.ui.viewmodel.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.conrep.database.repository.TaskRepository;
import com.example.conrep.database.task.TaskEntity;
import com.example.conrep.ui.BaseApp;
import com.example.conrep.ui.util.OnAsyncEventListener;

public class TaskViewModel extends AndroidViewModel {

    private Application application;

    private TaskRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<TaskEntity> observableTask;

    public TaskViewModel(@NonNull Application application,
                         final int taskId, TaskRepository taskRepository) {
        super(application);

        this.application = application;

        repository = taskRepository;

        observableTask = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableTask.setValue(null);

        LiveData<TaskEntity> task = repository.getTask(taskId, application);

        // observe the changes of the task entity from the database and forward them
        observableTask.addSource(task, observableTask::setValue);
    }

    /**
     * A creator is used to inject the task id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final int taskId;

        private final TaskRepository repository;

        public Factory(@NonNull Application application, int taskId) {
            this.application = application;
            this.taskId = taskId;
            repository = ((BaseApp) application).getTaskRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TaskViewModel(application, taskId, repository);
        }
    }

    /**
     * Expose the LiveData TaskEntity query so the UI can observe it.
     */
    public LiveData<TaskEntity> getTask() {
        return observableTask;
    }

    public void createTask(TaskEntity task, OnAsyncEventListener callback) {
        repository.insert(task, callback, application);
    }

    public void updateTask(TaskEntity task, OnAsyncEventListener callback) {
        repository.update(task, callback, application);
    }


}
