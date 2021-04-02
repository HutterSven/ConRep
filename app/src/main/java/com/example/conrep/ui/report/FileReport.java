package com.example.conrep.ui.report;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conrep.R;
import com.example.conrep.adapter.TaskRecyclerAdapter;
import com.example.conrep.database.async.report.CreateReport;
import com.example.conrep.database.report.ReportEntity;
import com.example.conrep.database.task.TaskEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.task.TaskList;
import com.example.conrep.ui.task.ViewTask;
import com.example.conrep.ui.util.OnAsyncEventListener;
import com.example.conrep.ui.util.RecyclerViewItemClickListener;
import com.example.conrep.ui.viewmodel.report.ReportViewModel;
import com.example.conrep.ui.viewmodel.task.TaskListViewModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileReport extends BaseActivity {

    private int siteID;
    private int reportID;

    private static final String TAG = "FileReport";

    private ReportEntity report;
    private ReportViewModel viewModel;

    private EditText etReportName;
    private EditText etReportHours;

    private List<TaskEntity> Tasks;
    private TaskRecyclerAdapter taskRecyclerAdapter;
    private TaskListViewModel viewModelTask;

    private Bundle savedInstanceState;

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
        report = new ReportEntity();

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
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + Tasks.get(position).toString());
                report.setTaskReport(Tasks.get(position).getTaskID());
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });

        TaskListViewModel.Factory factory = new TaskListViewModel.Factory(getApplication(), 0);
        viewModelTask = ViewModelProviders.of(this, factory).get(TaskListViewModel.class);
        viewModelTask.getTasksBySite(siteID).observe(this, TaskEntities -> {
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

        report.setWorkerName(etReportName.getText().toString());
        report.setHours(Integer.parseInt(etReportHours.getText().toString()));
        LocalDateTime now = LocalDateTime.now();
        report.setTaskReport(1); // todo set to chosen tasks
        report.setDate(now.toString());
        report.setSiteReport(siteID);


        // todo call insert function for construction site //probably incorrect, if possible replace with viewModel createReport call


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

        Intent intent = getIntent();
        intent.putExtra("reportID", (int)cr.reportID);// todo 0 has to be replaced with whatever ID will be generated by the DB //partially done
        startActivity(intent);
    }
}