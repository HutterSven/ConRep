package com.example.conrep.ui.viewmodel.constructionSite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.conrep.database.repository.ConstructionSiteRepository;
import com.example.conrep.database.entity.ConstructionSiteEntity;
import com.example.conrep.BaseApp;
import com.example.conrep.ui.util.OnAsyncEventListener;

import java.util.List;

public class ConstructionSiteViewModel extends AndroidViewModel {

    private Application application;

    public ConstructionSiteRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<ConstructionSiteEntity> observableConstructionSite;

    public ConstructionSiteViewModel(@NonNull Application application,
                         final String constructionSiteId, ConstructionSiteRepository constructionSiteRepository) {
        super(application);

        this.application = application;

        repository = constructionSiteRepository;

        observableConstructionSite = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableConstructionSite.setValue(null);

        LiveData<ConstructionSiteEntity> constructionSite = repository.getConstructionSite(constructionSiteId);

        // observe the changes of the constructionSite entity from the database and forward them
        observableConstructionSite.addSource(constructionSite, observableConstructionSite::setValue);
    }

    /**
     * A creator is used to inject the constructionSite id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String constructionSiteId;

        private final ConstructionSiteRepository repository;

        public Factory(@NonNull Application application, String constructionSiteId) {
            this.application = application;
            this.constructionSiteId = constructionSiteId;
            repository = ((BaseApp) application).getConstructionSiteRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ConstructionSiteViewModel(application, constructionSiteId, repository);
        }
    }

    /**
     * Expose the LiveData ConstructionSiteEntity query so the UI can observe it.
     */

    public LiveData<ConstructionSiteEntity> getConstructionSite(String id) {
        return repository.getConstructionSite(id);
    }

    public LiveData<List<ConstructionSiteEntity>> getConstructionSites() {
        return repository.getConstructionSites();
    }

    public void createConstructionSite(ConstructionSiteEntity constructionSite, OnAsyncEventListener callback) {
        repository.insert(constructionSite, callback);
    }

    public void updateConstructionSite(ConstructionSiteEntity constructionSite, OnAsyncEventListener callback) {
        repository.update(constructionSite, callback);
    }

    public void deleteConstructionSite(ConstructionSiteEntity constructionSite, OnAsyncEventListener callback) {
        repository.delete(constructionSite, callback);
    }


}
