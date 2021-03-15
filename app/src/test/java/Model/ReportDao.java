package Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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
    void insertFavorite(Report report);

    @Update
    void updateFavorite(Report report);

    @Delete
    void deleteFavorite(Report report);

}
