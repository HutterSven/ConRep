package com.example.conrep.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.conrep.database.report.Report;

import java.util.List;

@Dao
public interface ReportDao {

    @Query("SELECT * FROM Report")
    List<Report> getAll();

    @Query("SELECT * FROM Report WHERE siteID = :siteID")
    List<Report> getBySite(int siteID);

    @Query("SELECT * FROM Report WHERE workerName = :name")
    List<Report> getByName(String name);

    @Query("SELECT * FROM Report WHERE date = :date")
    List<Report> getByDate(String date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReport(Report report);

    @Update
    void updateReport(Report report);

    @Delete
    void deleteReport(Report report);

    @Query("DELETE FROM report")
    void deleteAll();
}
