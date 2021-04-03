package com.example.conrep.ui.viewmodel.constructionSite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.conrep.BaseApp;
import com.example.conrep.database.repository.ConstructionSiteRepository;
import com.example.conrep.database.site.ConstructionSiteEntity;
import com.example.conrep.ui.util.OnAsyncEventListener;

import java.util.List;

public class ConstructionSiteListViewModel extends AndroidViewModel {

    private Application application;

    private ConstructionSiteRepository repository;
    LiveData<List<ConstructionSiteEntity>> sites;


    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<ConstructionSiteEntity>> observableSites;
    private final MediatorLiveData<List<ConstructionSiteEntity>> observableConstructionSites;

    public ConstructionSiteListViewModel(@NonNull Application application,
                                         final int ownerId,
                                         ConstructionSiteRepository siteRepository) {
        super(application);

        this.application = application;

        repository = siteRepository;

        observableConstructionSites = new MediatorLiveData<>();
        observableSites = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableConstructionSites.setValue(null);
        observableSites.setValue(null);

        sites = ConstructionSiteRepository.getConstructionSites(application);

        // observe the changes of the entities from the database and forward them
        observableSites.addSource(sites, observableSites::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private int siteId;

        private final ConstructionSiteRepository siteRepository;

        public Factory(@NonNull Application application, int siteId) {
            this.application = application;
            this.siteId = siteId;
            siteRepository = ((BaseApp) application).getConstructionSiteRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ConstructionSiteListViewModel(application, siteId, siteRepository);
        }
    }

    /**
     * Expose the LiveData ClientAccounts query so the UI can observe it.
     */
    public LiveData<List<ConstructionSiteEntity>> getSites() {
        return observableSites;
    }

    public synchronized List<ConstructionSiteEntity> getSitesNonLive() {
        return repository.getConstructionSitesNonLive(application);
    }

    /**
     * CHANGE!!!!!!!
     */
    public LiveData<List<ConstructionSiteEntity>> getOwnAccounts() {
        return observableSites;
    }

    public void deleteAccount(ConstructionSiteEntity account, OnAsyncEventListener callback) {
        repository.delete(account, callback, application);
    }
}
