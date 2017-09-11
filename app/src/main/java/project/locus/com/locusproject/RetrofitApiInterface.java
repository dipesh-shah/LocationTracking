package project.locus.com.locusproject;

import retrofit2.http.POST;

/**
 * Created by Dipesh on 11-09-2017.
 */

public interface RetrofitApiInterface {
    @POST("user/candidate/location")
    LocationEntity postEntity(LocationEntity entity);
}
