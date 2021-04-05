package com.example.conrep.ui.task;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.conrep.R;
import com.example.conrep.database.task.TaskEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.viewmodel.task.TaskViewModel;

public class DeleteTask extends BaseActivity {
    private static final String TAG = "DeleteTaskActivity";

    private TaskEntity task;
    private TaskViewModel viewModel;
    private TextView tvName;
    private TextView tvDescription;
    private TextView tvStatus;
    private TextView tvDeadline;
    private TextView tvSite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_delete_task, frameLayout);

        int taskID = getIntent().getIntExtra("taskID", 1);

        initiateView();

        TaskViewModel.Factory factory = new TaskViewModel.Factory(
                getApplication(), taskID);
        viewModel = ViewModelProviders.of(this, factory).get(TaskViewModel.class);
        viewModel.getTask(taskID).observe(this, taskEntity -> {
            if (taskEntity != null) {
                task = taskEntity;
                updateContent();
            }
        });
    }

    private void updateContent() {
        if (task != null) {
            tvName.setText(task.getName());
            tvDescription.setText(task.getDescription());
            if (task.isStatus()){
                tvStatus.setText("open");
            }else{
                tvStatus.setText("closed");
            }
            tvDeadline.setText(task.getDeadline());
            tvSite.setText(String.valueOf(task.getSiteTask()));
            Log.i(TAG, "Activity populated.");
        }
    }

    private void initiateView() {
        tvName = findViewById(R.id.tvDeleteNameTask);
        tvDescription = findViewById(R.id.tvDeleteDescriptionTask);
        tvStatus = findViewById(R.id.tvDeleteStatusTask);
        tvDeadline = findViewById(R.id.tvDeleteDeadlineTask);
        tvSite = findViewById(R.id.tvDeleteSiteTask);
    }
}