package com.example.conrep.ui.report;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conrep.BaseApp;
import com.example.conrep.R;
import com.example.conrep.adapter.TaskRecyclerAdapter;
import com.example.conrep.database.entity.ReportEntity;
import com.example.conrep.database.repository.ConstructionSiteRepository;
import com.example.conrep.database.repository.TaskRepository;
import com.example.conrep.database.entity.ConstructionSiteEntity;
import com.example.conrep.database.entity.TaskEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.site.ViewConstructionSite;
import com.example.conrep.ui.util.OnAsyncEventListener;
import com.example.conrep.ui.util.RecyclerViewItemClickListener;
import com.example.conrep.ui.viewmodel.report.ReportViewModel;
import com.example.conrep.ui.viewmodel.task.TaskListViewModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileReport extends BaseActivity {

    private String siteID;

    private static final String TAG = "FileReport";

    private ReportEntity report;
    private ReportViewModel viewModel;

    private EditText etReportName;
    private EditText etReportHours;

    private View vTemp;
    private int positionOld;
    private int hours;

    private List<TaskEntity> Tasks;
    private TaskRecyclerAdapter taskRecyclerAdapter;
    private TaskListViewModel viewModelTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_file_report, frameLayout);

        siteID = getIntent().getStringExtra("siteID");
        hours = getIntent().getIntExtra("hours", 0);

        initiateView();

    }

    private void initiateView() {

        etReportName = findViewById(R.id.etReportName);
        etReportHours = findViewById(R.id.etReportHours);
        report = new ReportEntity();
        positionOld = 0;

        Button fileReportBtn = findViewById(R.id.btnSaveReport);

        fileReportBtn.setOnClickListener(view -> addReport());

        RecyclerView recyclerView = findViewById(R.id.reportTaskRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        Tasks = new ArrayList<>();
        taskRecyclerAdapter = new TaskRecyclerAdapter(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                recyclerView.getChildAt(positionOld).setBackgroundColor(Color.parseColor("#0184C8"));
                positionOld = position;
                recyclerView.getChildAt(position).setBackgroundColor(Color.GRAY);
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + Tasks.get(position).toString());
                report.setTaskReport(Tasks.get(position).getTaskID());
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });

        TaskListViewModel.Factory factory = new TaskListViewModel.Factory(getApplication());
        viewModelTask = ViewModelProviders.of(this, factory).get(TaskListViewModel.class);
        viewModelTask.getTasks().observe(this, TaskEntities -> {
            if (TaskEntities != null) {
                Tasks = TaskEntities;
                taskRecyclerAdapter.setData(Tasks);
            }
        });


        recyclerView.setAdapter(taskRecyclerAdapter);
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

        TaskEntity task = new TaskEntity();
        ConstructionSiteEntity site = new ConstructionSiteEntity();

        report.setWorkerName(etReportName.getText().toString());
        report.setHours(Integer.parseInt(etReportHours.getText().toString()));
        LocalDateTime now = LocalDateTime.now();
        report.setDate(now.toString());
        report.setSiteReport(siteID);


        // inserting report

        ((BaseApp)getApplication()).getReportRepository().insert(report, new OnAsyncEventListener()
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

        task.setTaskID(report.getTaskReport());
        task.setStatus(!task.isStatus());

        //updating tasks reference to report
        TaskRepository.getInstance().changeStatus(task, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateTask: success");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "updateTask: failure", e);
            }
        });

        site.setHours(report.getHours()+hours);
        site.setSiteID(report.getSiteReport());
        //adding hours (update) site
        ConstructionSiteRepository.getInstance().updateHours(site, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateHours: success");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "updateHours: failure", e);
            }
        });

        Intent intent = new Intent(this, ViewConstructionSite.class);
        intent.putExtra("siteID", siteID);
        startActivity(intent);
    }
}