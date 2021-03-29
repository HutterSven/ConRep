package com.example.conrep.database.pojo;

import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.conrep.database.report.ReportEntity;
import com.example.conrep.database.task.TaskEntity;

import java.util.List;

public class TaskAndReportEntity {

    @Embedded
    public ReportEntity report;

    @Relation(parentColumn = "taskReport", entityColumn = "taskID", entity = TaskEntity.class)
    public List<TaskEntity> tasks;

}
