package project.locus.com.locusproject;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Dipesh on 10-09-2017.
 */

@Database(entities = {LocationEntity.class}, version = 1, exportSchema = false)
public abstract class LocusDatabase extends RoomDatabase {
    public abstract LocationDao getLocationDao();
}
