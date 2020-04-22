package com.example.spacenbeyond.viewmodel;

import android.app.Application;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.spacenbeyond.model.PhotoResponse;
import com.example.spacenbeyond.repository.PhotoRepository;
import com.example.spacenbeyond.util.AppUtil;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PhotoViewModel extends AndroidViewModel {
    public final MutableLiveData<PhotoResponse> photo = new MutableLiveData<>();
    public final LiveData<PhotoResponse> liveData = photo;
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final PhotoRepository repository = new PhotoRepository();
    public MutableLiveData<Throwable> resultLiveDataError = new MutableLiveData<>();
    public MutableLiveData<PhotoResponse> favoriteAdded = new MutableLiveData<>();

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

    public void salvarFavorito(PhotoResponse photoResponse){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(AppUtil.getIdUsuario(getApplication()) + "/favorites");

        reference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean existe = false;
                for (DataSnapshot resultSnapshot : dataSnapshot.getChildren()){
                    PhotoResponse firebaseResult = resultSnapshot.getValue(PhotoResponse.class);

                    if (firebaseResult != null && firebaseResult.getDate() != null && firebaseResult.getDate().equals(photoResponse.getDate())) {
                        existe = true;
                    }
                }

                if (existe) {
                    resultLiveDataError.setValue(new Throwable("A foto do dia: " + photoResponse.getDate() + "já está nos seus favoritos."));
                } else {
                    salvarFavoritoVerificado(reference, photoResponse);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplication(), databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void salvarFavoritoVerificado(DatabaseReference reference, PhotoResponse photoResponse){
        String key = reference.push().getKey();
        reference.child(key).setValue(photoResponse);

        reference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                PhotoResponse photoResponse1 = dataSnapshot.getValue(PhotoResponse.class);
                favoriteAdded.setValue(photoResponse1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}