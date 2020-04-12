package com.example.spacenbeyond.viewmodel;

import android.app.Application;
import android.util.Log;

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
    public final MutableLiveData<PhotoResponse> photo = new MutableLiveData<>();
    public final LiveData<PhotoResponse> liveData = photo;
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final PhotoRepository repository = new PhotoRepository();

    public PhotoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<PhotoResponse> getPhoto() {
        return this.photo;
    }

    public LiveData<Boolean> getLoading() {
        return this.loading;
    }

    public void getPhotoOfDay(String date, String apiKey) {
        disposable.add(
                repository.getPhotoOfDay(date, apiKey)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> loading.setValue(true))
                        .doOnTerminate(() -> loading.setValue(false))
                        .subscribe(photo::setValue,
                                throwable -> Log.i("LOG", "erro" + throwable.getMessage()))
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}