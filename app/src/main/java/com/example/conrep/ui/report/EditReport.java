package com.example.conrep.ui.report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.conrep.R;
import com.example.conrep.database.async.report.CreateReport;
import com.example.conrep.database.report.ReportEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.util.OnAsyncEventListener;
import com.example.conrep.ui.viewmodel.report.ReportViewModel;

import java.time.LocalDateTime;

public class EditReport extends BaseActivity {

    private static final String TAG = "EditReport";

    private ReportEntity report;
    private ReportViewModel viewModel;

    private EditText etReportName;
    private EditText etReportHours;
    private EditText etReportSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_edit_report, frameLayout);

        int reportID = getIntent().getIntExtra("reportID", 1);

        initiateView();

        //Get report to edit by id
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

    private void initiateView() {

        etReportName = findViewById(R.id.etReportNameE);
        etReportHours = findViewById(R.id.etReportHoursE);
        etReportSite = findViewById(R.id.etReportSiteE);

        Button fileReportBtn = findViewById(R.id.btnSaveEditReport);

        fileReportBtn.setOnClickListener(view -> addReport());
    }

    private void updateContent() {
        etReportName.setText(report.getWorkerName());
        etReportHours.setText(String.valueOf(report.getHours()));
        etReportSite.setText(String.valueOf(report.getSiteReport()));
    }


    private void addReport() {
        boolean testBool = false;
        if(etReportName.getText().toString() == null){
            etReportName.setError(getString(R.string.error_empty_field));
            testBool = true;
        }
        if (etReportHours.getText().toString() == null){
            etReportHours.setError(getString(R.string.error_empty_field));
            testBool = true;
        }
        if (etReportSite.getText().toString() == null){
            etReportSite.setError(getString(R.string.error_empty_field));
            testBool = true;
        }
        if(testBool){
            return;
        }
        report.setWorkerName(etReportName.getText().toString());
        report.setHours(Integer.parseInt(etReportHours.getText().toString()));
        LocalDateTime now = LocalDateTime.now();
        report.setTaskReport(report.getTaskReport());
        report.setDate(now.toString());
        report.setSiteReport(report.getSiteReport());
        report.setReportID(report.getReportID());



        viewModel.updateReport(report, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateReport: success");
            }
            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "updateReport: failure", e);
            }
        });

        Intent intent = new Intent(this, ViewReport.class);
        intent.putExtra("reportID", report.getReportID());
        startActivity(intent);
    }
}