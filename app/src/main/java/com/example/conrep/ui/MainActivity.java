package com.example.conrep.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;

import com.example.conrep.R;
import com.example.conrep.ui.report.ReportList;
import com.example.conrep.ui.site.AddConstructionSite;
import com.example.conrep.ui.site.Map;

public class MainActivity extends BaseActivity {

    private Button addSite, map, viewReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addSite = findViewById(R.id.btnAddSite);
        map = findViewById(R.id.btnMap);
        viewReports = findViewById(R.id.btnViewReports);
        addSite.setOnClickListener(v -> openAddSite());
        map.setOnClickListener(v -> openMap());
        viewReports.setOnClickListener(v -> openViewReports());
    }

    private void openAddSite() {
        Intent intent = new Intent(this, AddConstructionSite.class);
        startActivity(intent);
    }

    private void openMap() {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }

    private void openViewReports() {
        Intent intent = new Intent(this, ReportList.class);
        startActivity(intent);
    }



}