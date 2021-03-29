package com.example.conrep.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.conrep.database.report.ReportEntity;
import com.example.conrep.database.site.ConstructionSiteEntity;

import java.util.List;

@Dao
public interface ConstructionSiteDao {

    @Query("SELECT * FROM ConstructionSiteEntity")
    LiveData<List<ConstructionSiteEntity>> getAll();

    @Query("SELECT * FROM ConstructionSiteEntity WHERE siteID = :siteID")
    LiveData<ConstructionSiteEntity> getById(int reportID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertConstruction(ConstructionSiteEntity constructionSiteEntity);

    @Update
    void updateConstruction(ConstructionSiteEntity constructionSiteEntity);

    @Delete
    void deleteConstruction(ConstructionSiteEntity constructionSiteEntity);

    @Query("DELETE FROM ConstructionSiteEntity")
    void deleteAll();
}
