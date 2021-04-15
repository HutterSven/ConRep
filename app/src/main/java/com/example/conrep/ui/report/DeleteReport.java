package com.example.conrep.ui.report;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.conrep.R;
import com.example.conrep.database.entity.ReportEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.viewmodel.report.ReportViewModel;

public class DeleteReport extends BaseActivity {

    private static final String TAG = "DeleteReportActivity";

    private ReportEntity report;
    private ReportViewModel viewModel;
    private TextView tvName;
    private TextView tvHours;
    private TextView tvDate;
    private TextView tvSite;
    private TextView tvTasks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_delete_construction_site, frameLayout);

        int reportID = getIntent().getIntExtra("reportID", 1);

        initiateView();
        //Get report to delete by id, id gotten from viewReport intent
        ReportViewModel.Factory factory = new ReportViewModel.Factory(
                getApplication(), reportID);
        viewModel = ViewModelProviders.of(this, factory).get(ReportViewModel.class);
        viewModel.getReport(reportID).observe(this, reportEntity -> {
            if (reportEntity != null) {
                report = reportEntity;
                updateContent();
            }
        });
    }

    private void updateContent() {
        if (report != null) {
            tvName.setText(report.getWorkerName());
            tvHours.setText(report.getHours()+" hours");
            tvDate.setText(report.getDate());
            tvSite.setText(report.getSiteReport());
            tvTasks.setText(report.getTaskReport());
            Log.i(TAG, "Activity populated.");
        }
    }

    private void initiateView() {
        tvName = findViewById(R.id.tvDeleteNameReport);
        tvHours = findViewById(R.id.tvDeleteHoursReport);
        tvDate = findViewById(R.id.tvDeleteDateReport);
        tvSite = findViewById(R.id.tvDeleteSiteReport);
        tvTasks = findViewById(R.id.tvDeleteTasksReport);
    }
}