package com.example.conrep.database.pojo;

import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.conrep.database.report.ReportEntity;
import com.example.conrep.database.site.ConstructionSiteEntity;

import java.util.List;

public class ReportAndSiteEntity {

    @Embedded
    public ConstructionSiteEntity site;

    @Relation(parentColumn = "reportSite", entityColumn = "reportID", entity = ReportEntity.class)
    public List<ReportEntity> reports;

}
