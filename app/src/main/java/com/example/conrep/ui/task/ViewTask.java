package com.example.conrep.ui.task;

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
import com.example.conrep.database.task.TaskEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.site.ConstructionSiteList;
import com.example.conrep.ui.site.ViewConstructionSite;
import com.example.conrep.ui.util.OnAsyncEventListener;
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
        viewModel.getTask(taskID).observe(this, taskEntity -> {
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
        generateDialog();
    }

    private void openBackToTaskList() {
        Intent intent = new Intent(this, TaskList.class);
        startActivity(intent);
    }

    private void generateDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.activity_delete_construction_site, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Delete Site");
        alertDialog.setCancelable(false);

        TextView tvDeleteNameTask = view.findViewById(R.id.tvDeleteNameTask);
        tvDeleteNameTask.setText(task.getName());
        TextView tvDeleteDescriptionTask = view.findViewById(R.id.tvDeleteDescriptionTask);
        tvDeleteDescriptionTask.setText(task.getDescription());
        TextView tvDeleteStatusTask = view.findViewById(R.id.tvDeleteStatusTask);
        if (task.isStatus()){
            tvDeleteStatusTask.setText("open");
        }else{
            tvDeleteStatusTask.setText("closed");
        }
        TextView tvDeleteDeadlineTask = view.findViewById(R.id.tvDeleteDeadlineTask);
        tvDeleteDeadlineTask.setText(task.getDeadline());
        TextView tvDeleteSiteTask = view.findViewById(R.id.tvDeleteSiteTask);
        tvDeleteSiteTask.setText(String.valueOf(task.getSiteTask()));

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.confirm_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                viewModel.deleteTask(task, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Toast toast = Toast.makeText(ViewTask.this, getString(R.string.delete_task_final), Toast.LENGTH_LONG);
                        toast.show();

                        Log.d(TAG, "deleteTask: success");
                        openBackToTaskList();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast toast = Toast.makeText(ViewTask.this, getString(R.string.delete_task_error), Toast.LENGTH_LONG);
                        toast.show();

                        Log.d(TAG, "deleteTask: failure", e);
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