package com.example.conrep.ui.viewmodel.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.conrep.BaseApp;
import com.example.conrep.database.repository.TaskRepository;
import com.example.conrep.database.entity.TaskEntity;
import com.example.conrep.ui.util.OnAsyncEventListener;

import java.util.List;

public class TaskListViewModel extends AndroidViewModel {

    private Application application;

    private TaskRepository repository;
    LiveData<List<TaskEntity>> task;


    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<TaskEntity>> observableTasks;

    public TaskListViewModel(@NonNull Application application, TaskRepository taskRepository) {
        super(application);

        this.application = application;

        repository = taskRepository;

        observableTasks = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableTasks.setValue(null);

        task = taskRepository.getTasks();

        // observe the changes of the entities from the database and forward them
        observableTasks.addSource(task, observableTasks::setValue);
    }



    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final TaskRepository taskRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            taskRepository = ((BaseApp) application).getTaskRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TaskListViewModel(application, taskRepository);
        }
    }

    /**
     * Expose the LiveData ClientAccounts query so the UI can observe it.
     */
    public LiveData<List<TaskEntity>> getTasks() {
        return observableTasks;
    }

    public LiveData<List<TaskEntity>> getTasksBySite(String siteID) {
        return repository.getTasksBySite(siteID);
    }
}
