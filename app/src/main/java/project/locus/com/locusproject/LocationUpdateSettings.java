package project.locus.com.locusproject;

/**
 * Created by Dipesh on 11-09-2017.
 */

public class LocationUpdateSettings {
    private long locationUpdateTimeInterval = 120 * 1000;

    public long getLocationUpdateTimeInterval() {
        return locationUpdateTimeInterval;
    }

    public void setLocationUpdateTimeInterval(long locationUpdateTimeInterval) {
        this.locationUpdateTimeInterval = locationUpdateTimeInterval;
    }

    public float getLocationUpdateMinDistance() {
        return locationUpdateMinDistance;
    }

    public void setLocationUpdateMinDistance(float locationUpdateMinDistance) {
        this.locationUpdateMinDistance = locationUpdateMinDistance;
    }

    private float locationUpdateMinDistance = 100f;

    public long getLocationUpdateMinTimeInterval() {
        return locationUpdateMinTimeInterval;
    }

    public void setLocationUpdateMinTimeInterval(long locationUpdateMinTimeInterval) {
        this.locationUpdateMinTimeInterval = locationUpdateMinTimeInterval;
    }

    private long locationUpdateMinTimeInterval = 5 * 1000;
}
