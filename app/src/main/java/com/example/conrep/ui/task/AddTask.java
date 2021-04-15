package com.example.conrep.ui.task;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.conrep.R;
import com.example.conrep.database.repository.TaskRepository;
import com.example.conrep.database.entity.TaskEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.site.ViewConstructionSite;
import com.example.conrep.ui.util.OnAsyncEventListener;
import com.example.conrep.ui.viewmodel.task.TaskViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTask extends BaseActivity  {

    private int siteID;
    private String dateDeadLine;

    private static final String TAG = "AddTask";

    private TaskEntity task;
    private TaskViewModel viewModel;

    private EditText etTaskName;
    private EditText etTaskDescription;
    private CalendarView cvTaskDeadLine;
    private EditText status;
    private EditText etSiteTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_add_task, frameLayout);

        siteID = getIntent().getIntExtra("siteID", 1);

        initiateView();

    }

    private void initiateView() {

        cvTaskDeadLine = findViewById(R.id.cvDeadline);

        etTaskName = findViewById(R.id.etTaskName);
        etTaskDescription = findViewById(R.id.etTaskDescription);


        Button addTaskBtn = findViewById(R.id.btnSaveTask);

        cvTaskDeadLine.setOnDateChangeListener(new myCalendarListener());

        addTaskBtn.setOnClickListener(view -> addTask());
    }

    public class myCalendarListener implements CalendarView.OnDateChangeListener {

        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {

            // add one because month starts at 0
            month = month + 1;
            // output to log cat **not sure how to format year to two places here**
            dateDeadLine = day+"-"+month+"-"+year;
            Log.d("NEW_DATE", dateDeadLine);
        }
    }

    private void addTask() {
        boolean testBool = false;
        if(etTaskName.getText().toString() == null){
            etTaskName.setError(getString(R.string.error_empty_field));
            testBool = true;
        }
        if(etTaskName.getText().toString() == null){
            etTaskDescription.setError(getString(R.string.error_empty_field));
            testBool = true;
        }
        if(testBool){
            return;
        }
        task = new TaskEntity();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String selectedDate = sdf.format(new Date(cvTaskDeadLine.getDate()));

        task.setDeadline(dateDeadLine);
        task.setName(etTaskName.getText().toString());
        task.setSiteTask(siteID);
        task.setStatus(true);
        task.setDescription(etTaskDescription.getText().toString());

        TaskRepository.getInstance().insert(task, new OnAsyncEventListener()
        {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createTask: success");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createTask: failure", e);
            }
        }, getApplication());

        Intent intent = new Intent(this, ViewConstructionSite.class);
        intent.putExtra("siteID", siteID);
        startActivity(intent);
    }
}