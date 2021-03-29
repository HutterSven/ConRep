package com.example.conrep.database.task;

import androidx.room.Embedded;

import com.example.conrep.database.site.ConstructionSiteEntity;

public class TaskAndSiteEntity {

    @Embedded
    public ConstructionSiteEntity site;

    @Embedded
    public TaskEntity taskEntity;

}
