package com.example.conrep.ui.viewmodel.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.conrep.BaseApp;
import com.example.conrep.database.repository.ConstructionSiteRepository;
import com.example.conrep.database.repository.TaskRepository;
import com.example.conrep.database.site.ConstructionSiteEntity;
import com.example.conrep.database.task.TaskEntity;
import com.example.conrep.ui.util.OnAsyncEventListener;

import java.util.List;

public class TaskListViewModel extends AndroidViewModel {

    private Application application;

    private TaskRepository repository;
    LiveData<List<TaskEntity>> task;


    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<TaskEntity>> observableTasks;

    public TaskListViewModel(@NonNull Application application,
                             final int ownerId,
                             TaskRepository taskRepository) {
        super(application);

        this.application = application;

        repository = taskRepository;

        observableTasks = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableTasks.setValue(null);

        task = taskRepository.getTasks(application);

        // observe the changes of the entities from the database and forward them
        observableTasks.addSource(task, observableTasks::setValue);
    }



    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private int siteId;

        private final TaskRepository taskRepository;

        public Factory(@NonNull Application application, int siteId) {
            this.application = application;
            this.siteId = siteId;
            taskRepository = ((BaseApp) application).getTaskRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TaskListViewModel(application, siteId, taskRepository);
        }
    }

    /**
     * Expose the LiveData ClientAccounts query so the UI can observe it.
     */
    public LiveData<List<TaskEntity>> getTasks() {
        return observableTasks;
    }

    public LiveData<List<TaskEntity>> getTasksBySite(int siteID) {
        return repository.getTasksBySite(getApplication(), siteID);
    }

    /**
     * CHANGE!!!!!!!
     */
    public LiveData<List<TaskEntity>> getOwnAccounts() {
        return observableTasks;
    }

    public void deleteAccount(TaskEntity account, OnAsyncEventListener callback) {
        repository.delete(account, callback, application);
    }
}
