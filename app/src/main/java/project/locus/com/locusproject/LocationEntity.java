package project.locus.com.locusproject;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;

/**
 * Created by Dipesh on 10-09-2017.
 */

@Entity (tableName = "locations")
public class LocationEntity {

    public static LocationEntity getInstanceFromLocation(Location location) {
        LocationEntity entity = new LocationEntity();
        entity.setSyncedToServer(false);
        entity.setRecordedTime(location.getTime());
        entity.setLongitude(location.getLongitude());
        entity.setLatitude(location.getLatitude());
        entity.setProvider(location.getProvider());
        return entity;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "id")
    private int uid;

    @ColumnInfo(name = "longitude")
    private double longitude;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "time")
    private long recordedTime;

    @ColumnInfo(name = "provider")
    private String provider;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getRecordedTime() {
        return recordedTime;
    }

    public void setRecordedTime(long recorded_time) {
        this.recordedTime = recorded_time;
    }

    public boolean isSyncedToServer() {
        return isSyncedToServer;
    }

    public void setSyncedToServer(boolean syncedToServer) {
        isSyncedToServer = syncedToServer;
    }

    @ColumnInfo(name = "synced_to_server")
    private boolean isSyncedToServer = false;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("latitude : " + latitude);
        sb.append("\nlongitude: " + longitude).append("\nprovider : " + provider).append("\nuid : " + uid);
        return sb.toString();
    }
}
