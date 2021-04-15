package com.example.conrep.ui.site;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import com.example.conrep.R;
import com.example.conrep.database.entity.ConstructionSiteEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.report.FileReport;
import com.example.conrep.ui.report.ReportListSite;
import com.example.conrep.ui.task.AddTask;
import com.example.conrep.ui.task.TaskList;
import com.example.conrep.ui.util.OnAsyncEventListener;
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
                getApplication(), 0);
        viewModel = ViewModelProviders.of(this, factory).get(ConstructionSiteViewModel.class);
        viewModel.getConstructionSite(siteID).observe(this, constructionSiteEntity -> {
            if (constructionSiteEntity != null) {
                conSite = constructionSiteEntity;
                updateContent();
            }
        });
    }

    private void updateContent() {
        if (conSite != null) {
            setTitle("ConRep - " + conSite.getSiteName());
            tvName.setText(conSite.getSiteName());
            tvAddress.setText(conSite.getAddress());
            tvCity.setText(conSite.getCity());
            tvOverseer.setText(conSite.getOverseer());
            tvHours.setText(conSite.getHours() + " hours");
            Log.i(TAG, "Activity populated.");
        }
    }

    private void initiateView() {
        tvName = findViewById(R.id.tvSiteNameVD);
        tvAddress = findViewById(R.id.tvAddressVD);
        tvCity = findViewById(R.id.tvCityVD);
        tvOverseer = findViewById(R.id.tvOverSeerVD);
        tvHours = findViewById(R.id.tvHoursVD);


        Button FileBtn = findViewById(R.id.btnFileReport);
        FileBtn.setOnClickListener(view -> openFileReport());

        Button ReportListBtn = findViewById(R.id.btnReportListSite);
        ReportListBtn.setOnClickListener(view -> openReportList());

        Button AddTaskBtn = findViewById(R.id.btnAddTask);
        AddTaskBtn.setOnClickListener(view -> openAddTask());

        Button DeleteBtn = findViewById(R.id.btnDeleteSite);
        DeleteBtn.setOnClickListener(view -> openDeleteSite());

        Button EditBtn = findViewById(R.id.btnEditSite);
        EditBtn.setOnClickListener(view -> openEditSite());

        Button ViewTaskBtn = findViewById(R.id.btnViewTasks);
        ViewTaskBtn.setOnClickListener(view -> openViewTasks());

        Button ShowMapBtn = findViewById(R.id.btnShowOnMap);
        ShowMapBtn.setOnClickListener(view -> openMap());
    }

    private void openMap() {
        Intent intent = new Intent(this, Map.class);
        intent.putExtra("site", conSite);
        startActivity(intent);
    }

    private void openReportList() {
        Intent intent = new Intent(this, ReportListSite.class);
        intent.putExtra("siteID", conSite.getSiteID());
        startActivity(intent);
    }

    private void openFileReport() {
        Intent intent = new Intent(this, FileReport.class);
        intent.putExtra("siteID", conSite.getSiteID());
        startActivity(intent);
    }

    private void openAddTask() {
        Intent intent = new Intent(this, AddTask.class);
        intent.putExtra("siteID", conSite.getSiteID());
        startActivity(intent);
    }

    private void openDeleteSite() {
        generateDialog();
    }

    private void openEditSite() {
        Intent intent = new Intent(this, EditConstructionSite.class);
        intent.putExtra("siteID", conSite.getSiteID());
        startActivity(intent);
    }

    private void openViewTasks() {
        Intent intent = new Intent(this, TaskList.class);
        intent.putExtra("siteID", conSite.getSiteID());
        startActivity(intent);
    }

    private void openConstructionSiteList() {
        Intent intent = new Intent(this, ConstructionSiteList.class);
        startActivity(intent);
    }

    private void generateDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.activity_delete_construction_site, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Delete Site");
        alertDialog.setCancelable(false);

        TextView tvDeleteNameSite = view.findViewById(R.id.tvDeleteNameSite);
        tvDeleteNameSite.setText(conSite.getSiteName());
        TextView tvDeleteAddressSite = view.findViewById(R.id.tvDeleteAddressSite);
        tvDeleteAddressSite.setText(conSite.getAddress());
        TextView tvDeleteCitySite = view.findViewById(R.id.tvDeleteCitySite);
        tvDeleteCitySite.setText(conSite.getCity());
        TextView tvDeleteOverseerSite = view.findViewById(R.id.tvDeleteOverseerSite);
        tvDeleteOverseerSite.setText(conSite.getOverseer());
        TextView tvDeleteHoursSite = view.findViewById(R.id.tvDeleteHoursSite);
        if (conSite.getHours() > 0) tvDeleteHoursSite.setText(Integer.toString(conSite.getHours()));
        else tvDeleteHoursSite.setText(Integer.toString(0));

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.confirm_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                viewModel.deleteConstructionSite(conSite, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Toast toast = Toast.makeText(ViewConstructionSite.this, getString(R.string.delete_site_final), Toast.LENGTH_LONG);
                        toast.show();

                        Log.d(TAG, "deleteSite: success");
                        openConstructionSiteList();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast toast = Toast.makeText(ViewConstructionSite.this, getString(R.string.delete_site_error), Toast.LENGTH_LONG);
                        toast.show();

                        Log.d(TAG, "deleteSite: failure", e);
                    }
                });
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel),
                (dialog, which) -> alertDialog.dismiss());
        alertDialog.setView(view);
        alertDialog.show();
    }
}