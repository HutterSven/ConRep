package com.example.conrep.ui.site;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.conrep.R;
import com.example.conrep.database.site.ConstructionSiteEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.report.FileReport;
import com.example.conrep.ui.viewmodel.constructionSite.ConstructionSiteViewModel;

public class ViewConstructionSite extends BaseActivity {

    private static final String TAG = "ViewConstructionSiteActivity";

    private ConstructionSiteEntity conSite;
    private ConstructionSiteViewModel viewModel;
    private TextView tvName;
    private TextView tvAddress;
    private TextView tvCity;
    private TextView tvOverseer;
    private TextView tvHours;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_view_construction_site, frameLayout);

        int siteID = getIntent().getIntExtra("siteID", 1);

        initiateView();

        ConstructionSiteViewModel.Factory factory = new ConstructionSiteViewModel.Factory(
                getApplication(), siteID);
        viewModel = ViewModelProviders.of(this, factory).get(ConstructionSiteViewModel.class);
        viewModel.getConstructionSite().observe(this, constructionSiteEntity -> {
            if (constructionSiteEntity != null) {
                conSite = constructionSiteEntity;

            }
        });

        updateContent();
    }

    private void updateContent() {
        if (conSite != null) {
            setTitle(conSite.getSiteName());
            tvName.setText(conSite.getSiteName());
            tvAddress.setText(conSite.getAddress());
            tvCity.setText(conSite.getCity());
            tvOverseer.setText(conSite.getOverseer());
            tvHours.setText(conSite.getHours());
            Log.i(TAG, "Activity populated.");
        }
    }

    private void initiateView() {
        tvName = findViewById(R.id.textView17);
        tvAddress = findViewById(R.id.textView18);
        tvCity = findViewById(R.id.textView19);
        tvOverseer = findViewById(R.id.textView15);
        tvHours = findViewById(R.id.textView16);


        Button FileBtn = findViewById(R.id.btnFileReport);
        FileBtn.setOnClickListener(view -> openFileReport());

        Button AddTaskBtn = findViewById(R.id.btnAddTask);
        AddTaskBtn.setOnClickListener(view -> openAddTask());

        Button DeleteBtn = findViewById(R.id.btnDeleteSite);
        DeleteBtn.setOnClickListener(view -> openDeleteSite());

        Button EditBtn = findViewById(R.id.btnEditSite);
        EditBtn.setOnClickListener(view -> openEditSite());

        Button ViewTaskBtn = findViewById(R.id.btnViewTasks);
        ViewTaskBtn.setOnClickListener(view -> openViewTasks());
    }

    private void openFileReport() {
        Intent intent = new Intent(this, FileReport.class);
        startActivity(intent);
    }
    private void openAddTask() {
        Intent intent = new Intent(this, FileReport.class);
        startActivity(intent);
    }
    private void openDeleteSite() {
        Intent intent = new Intent(this, FileReport.class);
        startActivity(intent);
    }
    private void openEditSite() {
        Intent intent = new Intent(this, FileReport.class);
        startActivity(intent);
    }
    private void openViewTasks() {
        Intent intent = new Intent(this, FileReport.class);
        startActivity(intent);
    }
}