package com.example.conrep.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.conrep.database.site.ConstructionSite;
import com.example.conrep.database.task.Task;
import com.example.conrep.database.task.TaskAndSite;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    List<ConstructionSite> getAll();

    @Query("Select * FROM Task, ConstructionSite WHERE Task.siteID = ConstructionSite.siteID")
    List<TaskAndSite> getAllTaskAndSite();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("DELETE FROM task")
    void deleteAll();


}
