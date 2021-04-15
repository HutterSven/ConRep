package com.example.conrep.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.conrep.database.entity.ConstructionSiteEntity;

import java.util.List;

@Dao
public interface ConstructionSiteDao {

    @Query("SELECT * FROM ConstructionSiteEntity")
    LiveData<List<ConstructionSiteEntity>> getAll();

    @Query("SELECT * FROM ConstructionSiteEntity")
    List<ConstructionSiteEntity> getAllNonLive();

    @Query("SELECT * FROM ConstructionSiteEntity WHERE siteID = :siteID")
    LiveData<ConstructionSiteEntity> getById(int siteID);

    @Query("SELECT * FROM ConstructionSiteEntity WHERE siteName = :name")
    ConstructionSiteEntity getByIdNonLive(String name);

    @Query("SELECT * FROM ConstructionSiteEntity WHERE siteName = :name")
    LiveData<ConstructionSiteEntity> getByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertConstruction(ConstructionSiteEntity constructionSiteEntity) throws SQLiteConstraintException;

    @Update
    void updateConstruction(ConstructionSiteEntity constructionSiteEntity);

    @Delete
    void deleteConstruction(ConstructionSiteEntity constructionSiteEntity);

    @Query("DELETE FROM ConstructionSiteEntity")
    void deleteAll();

    //to add hours from newly created report of site
    @Query("UPDATE constructionsiteentity SET hours = hours+:hours WHERE siteID = :siteID")
    void updateHours(int siteID, int hours);
}
