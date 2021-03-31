package com.example.conrep.ui.report;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.conrep.R;
import com.example.conrep.database.async.constructionSite.CreateConstructionSite;
import com.example.conrep.database.async.report.CreateReport;
import com.example.conrep.database.report.ReportEntity;
import com.example.conrep.database.site.ConstructionSiteEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.site.ViewConstructionSite;
import com.example.conrep.ui.util.OnAsyncEventListener;
import com.example.conrep.ui.viewmodel.constructionSite.ConstructionSiteViewModel;
import com.example.conrep.ui.viewmodel.report.ReportViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileReport extends BaseActivity {

    private int siteID;

    private static final String TAG = "FileReport";

    private ReportEntity report;
    private ReportViewModel viewModel;

    private EditText etReportName;
    private EditText etReportHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_file_report, frameLayout);

        siteID = getIntent().getIntExtra("siteID", 1);



        initiateView();

    }

    private void initiateView() {

        etReportName = findViewById(R.id.etReportName);
        etReportHours = findViewById(R.id.etReportHours);

        Button fileReportBtn = findViewById(R.id.btnSaveReport);

        fileReportBtn.setOnClickListener(view -> addReport());
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
        if(testBool){
            return;
        }
        report = new ReportEntity();
        report.setWorkerName(etReportName.getText().toString());
        report.setHours(Integer.parseInt(etReportHours.getText().toString()));
        LocalDateTime now = LocalDateTime.now();
        report.setTaskReport(1); // todo set to chosen tasks
        report.setDate(now.toString());
        report.setSiteReport(siteID);


        // todo call insert function for construction site //partially done


        CreateReport cr = new CreateReport(getApplication(), new OnAsyncEventListener()
        {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createReport: success");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createReport: failure", e);
            }
        });
        cr.execute(report);

        Intent intent = new Intent(this, ViewReport.class);
        intent.putExtra("reportID", (int)cr.reportID);// todo 0 has to be replaced with whatever ID will be generated by the DB //partially done
        startActivity(intent);
    }
}