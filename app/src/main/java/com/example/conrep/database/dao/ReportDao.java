package com.example.conrep.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.conrep.database.report.ReportEntity;

import java.util.List;

@Dao
public interface ReportDao {

    @Query("SELECT * FROM ReportEntity")
    LiveData<List<ReportEntity>> getAll();

    @Query("SELECT * FROM ReportEntity WHERE reportID = :reportID")
    LiveData<ReportEntity> getById(int reportID);

    @Query("SELECT * FROM ReportEntity WHERE siteReport = :siteID")
    LiveData<List<ReportEntity>> getBySite(int siteID);

    @Query("SELECT * FROM ReportEntity WHERE workerName = :name")
    LiveData<List<ReportEntity>> getByName(String name);

    @Query("SELECT * FROM ReportEntity WHERE date = :date")
    LiveData<List<ReportEntity>> getByDate(String date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReport(ReportEntity reportEntity);

    @Update
    void updateReport(ReportEntity reportEntity);

    @Delete
    void deleteReport(ReportEntity reportEntity);

    @Query("DELETE FROM ReportEntity")
    void deleteAll();

}
