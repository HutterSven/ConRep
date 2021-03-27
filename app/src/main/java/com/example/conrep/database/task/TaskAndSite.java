package com.example.conrep.database.task;

import androidx.room.Embedded;

import com.example.conrep.database.site.ConstructionSite;
import com.example.conrep.database.task.Task;

public class TaskAndSite {

    @Embedded
    ConstructionSite site;

    @Embedded
    Task task;

}
