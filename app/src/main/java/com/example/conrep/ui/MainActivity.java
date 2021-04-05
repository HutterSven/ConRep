package com.example.conrep.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.conrep.R;
import com.example.conrep.ui.report.ReportList;
import com.example.conrep.ui.site.AddConstructionSite;
import com.example.conrep.ui.site.ConstructionSiteList;

public class MainActivity extends BaseActivity {

    private Button addSite, viewSites, viewReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        addSite = findViewById(R.id.btnAddSite);
        viewReports = findViewById(R.id.btnReportList);
        viewSites = findViewById(R.id.btnSiteList);
        addSite.setOnClickListener(v -> openAddSite());
        viewReports.setOnClickListener(v -> openReportList());
        viewSites.setOnClickListener(v -> openSiteList());
    }

    private void openAddSite() {
        Intent intent = new Intent(this, AddConstructionSite.class);
        startActivity(intent);
    }

    private void openSiteList() {
        Intent intent = new Intent(this, ConstructionSiteList.class);
        startActivity(intent);
    }

    private void openReportList() {
        Intent intent = new Intent(this, ReportList.class);
        startActivity(intent);
    }



}