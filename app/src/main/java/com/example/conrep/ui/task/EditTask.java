package com.example.conrep.ui.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.conrep.R;
import com.example.conrep.database.task.TaskEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.util.OnAsyncEventListener;
import com.example.conrep.ui.viewmodel.task.TaskViewModel;

public class EditTask extends BaseActivity {

    private static final String TAG = "EditConstructionSite";

    private TaskEntity task;
    private TaskViewModel viewModel;

    private EditText etTaskName;
    private EditText etTaskDesc;
    private EditText etTaskStat;
    private int taskID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_edit_task, frameLayout);

        taskID = getIntent().getIntExtra("taskID", 1);

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
        etTaskName.setText(task.getName());
        etTaskDesc.setText(task.getDescription());
        if (task.isStatus()){
            etTaskStat.setText("open");
        }else{
            etTaskStat.setText("closed");
        }
    }

    private void initiateView() {

        etTaskName = findViewById(R.id.etTaskNameE);
        etTaskDesc = findViewById(R.id.etTaskDescE);
        etTaskStat = findViewById(R.id.etTaskStatE);

        Button fileReportBtn = findViewById(R.id.SaveEditTask);

        fileReportBtn.setOnClickListener(view -> editTask());
    }

    private void editTask() {
        boolean testBool = false;
        if(etTaskName.getText().toString() == null){
            etTaskName.setError(getString(R.string.error_empty_field));
            testBool = true;
        }
        if (etTaskDesc.getText().toString() == null){
            etTaskDesc.setError(getString(R.string.error_empty_field));
            testBool = true;
        }
        if (etTaskStat.getText().toString() == null){
            etTaskStat.setError(getString(R.string.error_empty_field));
            testBool = true;
        }
        if(testBool){
            return;
        }
        task = new TaskEntity();
        task.setName(etTaskName.getText().toString());
        task.setDescription(etTaskDesc.getText().toString());
        if (etTaskStat.getText().equals("open")){
            task.setStatus(true);
        }else{
            task.setStatus(false);
        }
        task.setSiteTask(task.getSiteTask());
        task.setTaskID(task.getTaskID());

        viewModel.updateTask(task, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateTask: success");
            }
            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "updateTask: failure", e);
            }
        });

        Intent intent = new Intent(this, ViewTask.class);
        intent.putExtra("taskID", taskID);
        startActivity(intent);
    }

}