package com.example.conrep.ui.viewmodel.report;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.conrep.database.entity.ReportEntity;
import com.example.conrep.database.repository.ReportRepository;
import com.example.conrep.BaseApp;
import com.example.conrep.ui.util.OnAsyncEventListener;

public class ReportViewModel extends AndroidViewModel {

    private Application application;

    private ReportRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<ReportEntity> observableReport;

    public ReportViewModel(@NonNull Application application,
                            final String reportId, ReportRepository reportRepository) {
        super(application);

        this.application = application;

        repository = reportRepository;

        observableReport = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableReport.setValue(null);

        LiveData<ReportEntity> report = repository.getReport(reportId);

        // observe the changes of the report entity from the database and forward them
        observableReport.addSource(report, observableReport::setValue);
    }

    /**
     * A creator is used to inject the report id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String reportId;

        private final ReportRepository repository;

        public Factory(@NonNull Application application, String reportId) {
            this.application = application;
            this.reportId = reportId;
            repository = ((BaseApp) application).getReportRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ReportViewModel(application, reportId, repository);
        }
    }

    /**
     * Expose the LiveData ReportEntity query so the UI can observe it.
     */
    public LiveData<ReportEntity> getReport(String reportID) {
        return repository.getReport(reportID);
    }


    public void updateReport(ReportEntity report, OnAsyncEventListener callback) {
        repository.update(report, callback);
    }

    public void deleteReport(ReportEntity report, OnAsyncEventListener callback) {
        repository.delete(report, callback);
    }


}
