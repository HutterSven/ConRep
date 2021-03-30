package com.example.conrep.ui.task;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.conrep.R;
import com.example.conrep.database.task.TaskEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.viewmodel.task.TaskViewModel;

public class ViewTask extends BaseActivity {

    private static final String TAG = "ViewTaskActivity";

    private TaskEntity task;
    private TaskViewModel viewModel;
    private TextView tvName;
    private TextView tvDescription;
    private TextView tvStatus;
    private TextView tvDeadline;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_view_task, frameLayout);

        int taskID = getIntent().getIntExtra("taskID", 1);

        initiateView();

        TaskViewModel.Factory factory = new TaskViewModel.Factory(
                getApplication(), taskID);
        viewModel = ViewModelProviders.of(this, factory).get(TaskViewModel.class);
        viewModel.getTask().observe(this, taskEntity -> {
            if (taskEntity != null) {
                task = taskEntity;
                updateContent();
            }
        });
    }

    private void updateContent() {
        if (task != null) {
            setTitle("ConRep - Task " +  task.getTaskID() );
            tvName.setText(task.getName());
            tvDescription.setText(task.getDescription());
            if (task.isStatus()){
                tvStatus.setText("open");
            }else {
                tvStatus.setText("closed");
            }
            tvDeadline.setText(task.getDeadline());
            Log.i(TAG, "Activity populated.");
        }
    }

    private void initiateView() {
        tvName = findViewById(R.id.tvTaskName);
        tvDescription = findViewById(R.id.tvTaskDesc);
        tvStatus = findViewById(R.id.tvTaskStatus);
        tvDeadline = findViewById(R.id.tvTaskDeadline);


        Button EditBtn = findViewById(R.id.btnEditTask);
        EditBtn.setOnClickListener(view -> openEditTask());

        Button DeleteBtn = findViewById(R.id.btnDeleteTask);
        DeleteBtn.setOnClickListener(view -> openDeleteTask());

        Button BackBtn = findViewById(R.id.btnBackToTaskList);
        BackBtn.setOnClickListener(view -> openBackToTaskList());
    }

    private void openEditTask() {
        Intent intent = new Intent(this, EditTask.class);
        intent.putExtra("taskID", 1);
        startActivity(intent);
    }
    private void openDeleteTask() {
        // todo Add dialogue and toast for delete
    }
    private void openBackToTaskList() {
        Intent intent = new Intent(this, TaskList.class);
        startActivity(intent);
    }
}