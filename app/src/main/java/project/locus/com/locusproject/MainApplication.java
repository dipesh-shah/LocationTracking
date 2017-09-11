package project.locus.com.locusproject;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dipesh on 10-09-2017.
 */

public class MainApplication extends Application {

    public final String DATABASE_NAME = "locus";
    private LocusDatabase mDatabase;
    public final String PREFERENCES = "locus_preference";
    public final String KEY_LAST_UPDATE_TIME = "last_update_time";
    public static MainApplication sInstance;
    private Retrofit mRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mDatabase = Room.databaseBuilder(getApplicationContext(), LocusDatabase.class, DATABASE_NAME)
                .build();
        Utils.checkAndCreateAccount(this, Constants.ACCOUNT_NAME, Constants.ACCOUNT_TYPE);
    }

    public LocusDatabase getDB() {
        return mDatabase;
    }

    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl("https://api.locus.sh/v1//client/test/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(createOkHttpClient())
                    .build();
        }
        return mRetrofit;
    }

    private OkHttpClient createOkHttpClient() {
        final OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(new BasicAuthIntercepter("test/candidate", "c00e-4764"));
        return httpClient.build();
    }
}
