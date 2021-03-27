package com.example.conrep.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.conrep.database.site.ConstructionSite;

import java.util.List;

@Dao
public interface ConstructionSiteDao {

    @Query("SELECT * FROM constructionsite")
    List<ConstructionSite> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertConstruction(ConstructionSite constructionSite);

    @Update
    void updateConstruction(ConstructionSite constructionSite);

    @Delete
    void deleteConstruction(ConstructionSite constructionSite);

    @Query("DELETE FROM constructionsite")
    void deleteAll();
}
