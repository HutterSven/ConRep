package Model;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ConstructionSiteDao {

    @Query("SELECT * FROM constructionsite")
    List<ConstructionSite> getAll();

}
