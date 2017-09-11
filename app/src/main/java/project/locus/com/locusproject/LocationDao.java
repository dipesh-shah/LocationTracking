package project.locus.com.locusproject;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Dipesh on 10-09-2017.
 */

@Dao
public interface LocationDao {
    @Query("SELECT * FROM locations")
    List<LocationEntity> getAll();

    @Query("SELECT * FROM locations where synced_to_server < 1")
    List<LocationEntity> getAllUnsynced();

    @Insert
    void insertAll(List<LocationEntity> locations);

    @Insert
    long insert(LocationEntity location);

    @Update
    int updateAll(List<LocationEntity> locations);

    @Update
    int update(LocationEntity location);

    @Delete
    int delete(LocationEntity location);
}
