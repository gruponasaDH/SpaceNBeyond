package com.example.spacenbeyond.data.remote;

import com.example.spacenbeyond.model.PhotoResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotoAPI {

    @GET("planetary/apod")
    Observable<PhotoResponse> getPhotoOfDay(
            @Query("date") String date,
            @Query("api_key") String apiKey
    );
}