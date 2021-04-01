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
import com.example.conrep.database.site.ConstructionSiteEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.report.FileReport;
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
                getApplication(), siteID);
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
            tvHours.setText(conSite.getHours()+" hours");
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
        tvDeleteAddressSite.setText(conSite.getSiteName());
        TextView tvDeleteCitySite = view.findViewById(R.id.tvDeleteCitySite);
        tvDeleteCitySite.setText(conSite.getSiteName());
        TextView tvDeleteOverseerSite = view.findViewById(R.id.tvDeleteOverseerSite);
        tvDeleteOverseerSite.setText(conSite.getSiteName());
        TextView tvDeleteHoursSite = view.findViewById(R.id.tvDeleteHoursSite);
        tvDeleteHoursSite.setText(conSite.getSiteName());

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