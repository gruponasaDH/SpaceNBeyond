package com.example.spacenbeyond.repository;

import com.example.spacenbeyond.model.TranslateBody;
import com.example.spacenbeyond.model.Translation;

import io.reactivex.Observable;
import retrofit2.http.Body;

import static com.example.spacenbeyond.data.remote.TranslateRetrofitService.getApiService;

public class TranslateRepository {

    public Observable<Translation> getPhotoOfDayTranslated(@Body TranslateBody body){
        return getApiService().getPhotoOfDayTranslated(body);
    }
}