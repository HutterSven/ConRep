package com.example.conrep.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.conrep.R;
import com.example.conrep.ui.site.AddConstructionSite;
import com.example.conrep.ui.site.ConstructionSiteList;

public class MainActivity extends BaseActivity {

    private Button addSite, map, viewReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addSite = findViewById(R.id.btnAddSite);
        viewReports = findViewById(R.id.btnSiteList);
        addSite.setOnClickListener(v -> openAddSite());
        viewReports.setOnClickListener(v -> openSiteList());
    }

    private void openAddSite() {
        Intent intent = new Intent(this, AddConstructionSite.class);
        startActivity(intent);
    }

    private void openSiteList() {
        Intent intent = new Intent(this, ConstructionSiteList.class);
        startActivity(intent);
    }



}