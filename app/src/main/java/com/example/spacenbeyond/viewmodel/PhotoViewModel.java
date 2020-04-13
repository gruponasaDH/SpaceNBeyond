package com.example.spacenbeyond.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.spacenbeyond.model.PhotoResponse;
import com.example.spacenbeyond.repository.PhotoRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PhotoViewModel extends AndroidViewModel {
    private PhotoRepository repository = new PhotoRepository();

    private MutableLiveData<PhotoResponse> mutablePhoto = new MutableLiveData<PhotoResponse>();

    public LiveData<PhotoResponse> liveDataPhoto = mutablePhoto;

    private CompositeDisposable disposable = new CompositeDisposable();

    private MutableLiveData<String> errorMutable = new MutableLiveData<>();
    public LiveData<String> erro = errorMutable;

    public PhotoViewModel(@NonNull Application application) {
        super(application);
    }

    public void getTodosPhotos(Context context, String date, String apiKey) {

        disposable.add(

                repository.getPhotoOfDay(date, apiKey)

                        .subscribeOn(Schedulers.newThread())

                        .observeOn(AndroidSchedulers.mainThread())

                        .subscribe(photos -> {

                                    mutablePhoto.setValue(photos);
                                },

                                throwable -> {
                                    errorMutable.setValue(throwable.getMessage());
                                })
        );


    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}






