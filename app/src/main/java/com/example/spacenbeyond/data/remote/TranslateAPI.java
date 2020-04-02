package com.example.spacenbeyond.data.remote;

import com.example.spacenbeyond.model.TranslateBody;
import com.example.spacenbeyond.model.Translation;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TranslateAPI {
    @POST("language/translate/v2")
    Observable<Translation> getPhotoOfDayTranslated(@Body TranslateBody body);
}
