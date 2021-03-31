package com.example.conrep.ui.viewmodel.report;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.conrep.BaseApp;
import com.example.conrep.database.report.ReportEntity;
import com.example.conrep.database.repository.ConstructionSiteRepository;
import com.example.conrep.database.repository.ReportRepository;
import com.example.conrep.database.site.ConstructionSiteEntity;
import com.example.conrep.ui.util.OnAsyncEventListener;

import java.util.List;

public class ReportListViewModel extends AndroidViewModel {

    private Application application;

    private ReportRepository repository;
    LiveData<List<ReportEntity>> reports;


    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<ReportEntity>> observableReports;
    private final MediatorLiveData<List<ConstructionSiteEntity>> observableConstructionSites;

    public ReportListViewModel(@NonNull Application application,
                               final int ownerId,
                               ReportRepository reportRepository,
                               ConstructionSiteRepository siteRepository) {
        super(application);

        this.application = application;

        repository = reportRepository;

        observableConstructionSites = new MediatorLiveData<>();
        observableReports = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableConstructionSites.setValue(null);
        observableReports.setValue(null);

        reports = reportRepository.getReports(application);

        // observe the changes of the entities from the database and forward them
        observableReports.addSource(reports, observableReports::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private int siteId;

        private final ConstructionSiteRepository siteRepository;

        private final ReportRepository reportRepository;

        public Factory(@NonNull Application application, int siteId) {
            this.application = application;
            this.siteId = siteId;
            siteRepository = ((BaseApp) application).getConstructionSiteRepository();
            reportRepository = ((BaseApp) application).getReportRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ReportListViewModel(application, siteId, reportRepository, siteRepository);
        }
    }

    /**
     * Expose the LiveData ClientAccounts query so the UI can observe it.
     */
    public LiveData<List<ReportEntity>> getReports() {
        return observableReports;
    }

    public LiveData<List<ReportEntity>> getReportsBySite(int siteID) {
        reports = repository.getReportsBySite(getApplication(), siteID);
        return observableReports;
    }

    public LiveData<List<ReportEntity>> getReportsByName(String name) {
        reports = repository.getReportsByName(getApplication(), name);
        return observableReports;
    }

    /**
     * CHANGE!!!!!!!
     */
    public LiveData<List<ReportEntity>> getOwnAccounts() {
        return observableReports;
    }

    public void deleteAccount(ReportEntity account, OnAsyncEventListener callback) {
        repository.delete(account, callback, application);
    }
}
