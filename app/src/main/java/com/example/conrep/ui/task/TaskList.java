package com.example.conrep.ui.task;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.conrep.R;
import com.example.conrep.adapter.TaskRecyclerAdapter;
import com.example.conrep.database.entity.TaskEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.util.RecyclerViewItemClickListener;
import com.example.conrep.ui.viewmodel.task.TaskListViewModel;

import java.util.ArrayList;
import java.util.List;

public class TaskList extends BaseActivity {

    private static final String TAG = "TaskList";

    private List<TaskEntity> Tasks;
    private TaskRecyclerAdapter taskRecyclerAdapter;
    private TaskListViewModel viewModel;

    private int siteID;

    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.savedInstanceState = savedInstanceState;
        getLayoutInflater().inflate(R.layout.activity_task_list, frameLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        siteID = getIntent().getIntExtra("siteID", 1);

        setTitle("Task List");

        RecyclerView recyclerView = findViewById(R.id.taskRecyclerView);

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

                Intent intent = new Intent(TaskList.this, ViewTask.class);
                intent.putExtra("taskID", Tasks.get(position).getTaskID());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });

        TaskListViewModel.Factory factory = new TaskListViewModel.Factory(getApplication(), 0);
        viewModel = ViewModelProviders.of(this, factory).get(TaskListViewModel.class);
        viewModel.getTasksBySite(siteID).observe(this, TaskEntities -> {
            if (TaskEntities != null) {
                Tasks = TaskEntities;
                taskRecyclerAdapter.setData(Tasks);
            }
        });


        recyclerView.setAdapter(taskRecyclerAdapter);
    }
}