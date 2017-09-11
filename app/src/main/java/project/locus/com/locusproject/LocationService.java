package project.locus.com.locusproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.concurrent.Callable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


/** Created by Dipesh on 10-09-2017. */
public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private final String TAG = "LocationService";
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private CompositeDisposable mCompositeDisposable;
    private long mLastLocationUpdateTime;
    private Handler mHandler;
    private LocationUpdateSettings mLocationUpdateSettings;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            checkForLocationUpdate();
        }
    };

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate()
    {
        Log.e(TAG, "onCreate");
        mHandler = new Handler();
        mLocationUpdateSettings = new LocationUpdateSettings();
        mCompositeDisposable = new CompositeDisposable();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(mLocationUpdateSettings.getLocationUpdateTimeInterval());
        mLocationRequest.setFastestInterval(mLocationUpdateSettings.getLocationUpdateMinTimeInterval());
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(mLocationUpdateSettings.getLocationUpdateMinDistance());
    }

    @Override
    public void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        mGoogleApiClient.disconnect();
        mHandler.removeCallbacks(mRunnable);
        mCompositeDisposable.dispose();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        try {
            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.
                    requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        catch (SecurityException e) {
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        mLastLocationUpdateTime = System.currentTimeMillis();
        LocationEntity entity = LocationEntity.getInstanceFromLocation(location);
        schedulePostDelayedRunnable();
        persistLocation(entity);
    }

    /** scheduling post delayed runnable for 2 mins as per current settings */
    private void schedulePostDelayedRunnable() {
        mHandler.removeCallbacks(mRunnable);
        mHandler.postDelayed(mRunnable, mLocationUpdateSettings.getLocationUpdateTimeInterval());
    }

    private void persistLocation(final LocationEntity entity) {
        mCompositeDisposable.add(Single.fromCallable(new Callable() {

            @Override
            public Object call() throws Exception {
                insert(entity);
                Utils.requestSync(MainApplication.sInstance);
                return new Object();
            }
        }).subscribeOn(Schedulers.io()).subscribe());
    }

    public void insert(LocationEntity entity) {
        MainApplication.sInstance.getDB().getLocationDao().insert(entity);
    }

    private void checkForLocationUpdate() {
        if (mLastLocation != null) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - mLastLocationUpdateTime >= mLocationUpdateSettings.getLocationUpdateTimeInterval()) {
                // save location data
                onLocationChanged(mLastLocation);
            }
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent){
        Intent restartServiceTask = new Intent(getApplicationContext(),this.getClass());
        restartServiceTask.setPackage(getPackageName());
        PendingIntent restartPendingIntent =PendingIntent.getService(getApplicationContext(), 1,restartServiceTask, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager myAlarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        myAlarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartPendingIntent);

        super.onTaskRemoved(rootIntent);
    }
}
