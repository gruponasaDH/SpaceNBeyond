package com.example.spacenbeyond.repository;

import com.example.spacenbeyond.model.PhotoResponse;

import io.reactivex.Observable;

import static com.example.spacenbeyond.data.remote.PhotoRetrofitService.getApiService;

public class PhotoRepository {

    public Observable<PhotoResponse> getPhotoOfDay(String date, String apiKey){
        return getApiService().getPhotoOfDay(date, apiKey);
    }
}
