package com.example.conrep.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.conrep.database.report.ReportEntity;
import com.example.conrep.database.task.TaskEntity;
import com.example.conrep.database.task.TaskAndSiteEntity;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("Select * FROM TaskEntity, ConstructionSiteEntity WHERE TaskEntity.siteTask = ConstructionSiteEntity.siteID")
    LiveData<List<TaskAndSiteEntity>> getAllTaskAndSite();

    @Query("SELECT * FROM TaskEntity WHERE taskID = :taskID")
    LiveData<TaskEntity> getById(int taskID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(TaskEntity taskEntity);

    @Update
    void updateTask(TaskEntity taskEntity);

    @Delete
    void deleteTask(TaskEntity taskEntity);

    @Query("DELETE FROM TaskEntity")
    void deleteAll();


}
