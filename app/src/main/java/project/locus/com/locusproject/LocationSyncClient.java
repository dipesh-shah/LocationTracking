package project.locus.com.locusproject;

import android.util.Log;
import java.util.List;

/**
 * Created by Dipesh on 11-09-2017.
 */

public class LocationSyncClient {
    public static final String TAG = LocationSyncClient.class.getSimpleName();
    public static boolean synclocation() {
        RetrofitApiInterface retrofitApiInterface = MainApplication.sInstance.getRetrofit().create(RetrofitApiInterface.class);
        List<LocationEntity> unsyncedList = MainApplication.sInstance.getDB().getLocationDao().getAllUnsynced();
        for (LocationEntity entity: unsyncedList) {
            LocationEntity locationEntity = retrofitApiInterface.postEntity(entity);
            /** if there is any error from the api. stop further post */
            if (locationEntity == null) {
                Log.d(TAG, "Entity could not be synced: " + entity);
                return false;
            }
            locationEntity.setUid(entity.getUid());
            locationEntity.setSyncedToServer(true);
            MainApplication.sInstance.getDB().getLocationDao().update(locationEntity);
            // update the entry in the table to mark it synced
            Log.e(TAG, "" + locationEntity.getLatitude());
        }
        return true;
    }
}
