package com.example.conrep.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.conrep.database.task.TaskAndReportEntity;
import com.example.conrep.database.task.TaskEntity;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM TaskEntity")
    LiveData<List<TaskEntity>> getAll();

    @Query("Select * FROM TaskEntity, ConstructionSiteEntity WHERE TaskEntity.siteTask = ConstructionSiteEntity.siteID")
    LiveData<List<TaskAndReportEntity>> getAllTaskAndSite();

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
