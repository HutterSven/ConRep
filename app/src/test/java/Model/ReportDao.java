package Model;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReportDao {

    @Query("SELECT * FROM ConstructionSite")
    List<ConstructionSite> getAll();

}
