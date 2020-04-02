package com.example.spacenbeyond.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.spacenbeyond.model.TranslateBody;
import com.example.spacenbeyond.model.Translation;
import com.example.spacenbeyond.repository.TranslateRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Body;

public class TranslateViewModel extends AndroidViewModel {
    private MutableLiveData<Translation> translate = new MutableLiveData<>();
    public LiveData<Translation> liveData = translate;
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private TranslateRepository repository = new TranslateRepository();

    public TranslateViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Translation> get() {
        return this.translate;
    }

    public LiveData<Boolean> getLoading() {
        return this.loading;
    }

    public void getPhotoOfDayTranslated(@Body TranslateBody body) {
        disposable.add(
                repository.getPhotoOfDayTranslated(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> loading.setValue(true))
                        .doOnTerminate(() -> loading.setValue(false))
                        .subscribe(translateResult -> translate.setValue(translateResult),
                                throwable -> {
                                    Log.i("LOG", "erro" + throwable.getMessage());
                                })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}