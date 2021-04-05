package com.example.conrep.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.conrep.database.task.TaskEntity;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM TaskEntity")
    LiveData<List<TaskEntity>> getAll();

    @Query("SELECT * FROM TaskEntity WHERE taskID = :taskID")
    LiveData<TaskEntity> getById(int taskID);

    @Query("SELECT * FROM TaskEntity WHERE siteTask = :siteID")
    LiveData<List<TaskEntity>> getBySite(int siteID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTask(TaskEntity taskEntity);

    @Update
    void updateTask(TaskEntity taskEntity);

    @Delete
    void deleteTask(TaskEntity taskEntity);

    @Query("DELETE FROM TaskEntity")
    void deleteAll();

    //To change status of task when it is referenced by a report
    @Query("UPDATE TaskEntity SET STATUS = :status WHERE taskID = :taskID")
    void ChangeStatus(int taskID, boolean status);
}
