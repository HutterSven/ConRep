package com.example.conrep.ui.viewmodel.constructionSite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.conrep.database.repository.ConstructionSiteRepository;
import com.example.conrep.database.site.ConstructionSiteEntity;
import com.example.conrep.BaseApp;
import com.example.conrep.ui.util.OnAsyncEventListener;

import java.util.List;

public class ConstructionSiteViewModel extends AndroidViewModel {

    private Application application;

    public ConstructionSiteRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<ConstructionSiteEntity> observableConstructionSite;

    public ConstructionSiteViewModel(@NonNull Application application,
                         final int constructionSiteId, ConstructionSiteRepository constructionSiteRepository) {
        super(application);

        this.application = application;

        repository = constructionSiteRepository;

        observableConstructionSite = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableConstructionSite.setValue(null);

        LiveData<ConstructionSiteEntity> constructionSite = repository.getConstructionSite(constructionSiteId, application);

        // observe the changes of the constructionSite entity from the database and forward them
        observableConstructionSite.addSource(constructionSite, observableConstructionSite::setValue);
    }

    public ConstructionSiteViewModel(Application application, ConstructionSiteRepository constructionSiteRepository) {
        super(application);

        this.application = application;

        repository = constructionSiteRepository;

        observableConstructionSite = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableConstructionSite.setValue(null);

        LiveData<ConstructionSiteEntity> constructionSite;
    }

    /**
     * A creator is used to inject the constructionSite id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private String constructionSiteName;
        private final int constructionSiteId;

        private final ConstructionSiteRepository repository;

        public Factory(@NonNull Application application, int constructionSiteId) {
            this.application = application;
            this.constructionSiteId = constructionSiteId;
            repository = ((BaseApp) application).getConstructionSiteRepository();
        }

        public Factory(@NonNull Application application, String name) {
            this.application = application;
            this.constructionSiteName = name;
            repository = ((BaseApp) application).getConstructionSiteRepository();
            constructionSiteId = repository.getConstructionSiteNameNonLive(name, application).getSiteID();
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
    public LiveData<ConstructionSiteEntity> getConstructionSite(String name) {
        return repository.getConstructionSiteByName(name, getApplication());
    }

    public LiveData<ConstructionSiteEntity> getConstructionSite(int id) {
        return repository.getConstructionSite(id, getApplication());
    }

    public LiveData<List<ConstructionSiteEntity>> getConstructionSites() {
        return repository.getConstructionSites(getApplication());
    }

    public int getID() {
        return repository.ccs.siteID;
    }

    public void createConstructionSite(ConstructionSiteEntity constructionSite, OnAsyncEventListener callback) {
        repository.insert(constructionSite, callback, application);
    }

    public void updateConstructionSite(ConstructionSiteEntity constructionSite, OnAsyncEventListener callback) {
        repository.update(constructionSite, callback, application);
    }

    public void deleteConstructionSite(ConstructionSiteEntity constructionSite, OnAsyncEventListener callback) {
        repository.delete(constructionSite, callback, application);
    }


}
