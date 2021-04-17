package com.example.conrep.ui.report;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.conrep.R;
import com.example.conrep.database.entity.ReportEntity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_edit_report, frameLayout);

        String reportID = getIntent().getStringExtra("reportID");

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

        Button fileReportBtn = findViewById(R.id.btnSaveEditReport);

        fileReportBtn.setOnClickListener(view -> addReport());
    }

    private void updateContent() {
        etReportName.setText(report.getWorkerName());
        etReportHours.setText(String.valueOf(report.getHours()));
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

        report.setWorkerName(etReportName.getText().toString());
        report.setHours(Integer.parseInt(etReportHours.getText().toString()));


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