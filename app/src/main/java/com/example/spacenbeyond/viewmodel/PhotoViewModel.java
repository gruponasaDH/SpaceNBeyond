package com.example.spacenbeyond.viewmodel;

import android.app.Application;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.spacenbeyond.model.FirebasePhoto;
import com.example.spacenbeyond.model.PhotoEntity;
import com.example.spacenbeyond.model.PhotoResponse;
import com.example.spacenbeyond.repository.PhotoRepository;
import com.example.spacenbeyond.util.AppUtil;
import com.example.spacenbeyond.view.FavoritosRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
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
    public MutableLiveData<PhotoEntity> favoriteAdded = new MutableLiveData<>();

    public MutableLiveData<List<PhotoEntity>> mutableLiveDataPhoto = new MutableLiveData<>();
    public LiveData<List<PhotoEntity>> liveDataPhoto = mutableLiveDataPhoto;

    public MutableLiveData<String> mutableLiveDataErro = new MutableLiveData<>();
    public LiveData<String> liveDataErro = mutableLiveDataErro;


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
                                throwable -> Toast.makeText(getApplication(), throwable.getMessage().toString(), Toast.LENGTH_LONG).show())
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

                    if (photoResponse.getCopyright() != null) { }
                    else {
                        photoResponse.setmCopyright(" ");
                    }

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
                PhotoEntity photoEntity = new PhotoEntity(photoResponse1.getCopyright(), photoResponse.getDate(), photoResponse.getExplanation(), photoResponse.getTitle(), photoResponse.getUrl());
                favoriteAdded.setValue(photoEntity);

                insereDadosBd(photoEntity);

                Toast.makeText(getApplication(), "Imagem " + photoEntity.getTitle() + " favoritada com sucesso.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplication(), databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void carregaFavoritos(FavoritosRecyclerViewAdapter adapter){

        new Thread(new Runnable() {
            @Override
            public void run() {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference(AppUtil.getIdUsuario(getApplication()) + "/favorites");
                reference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<PhotoEntity> listaFotos = new ArrayList<>();
                        for (DataSnapshot child : dataSnapshot.getChildren()){
                            FirebasePhoto photoResponse = new FirebasePhoto();
                            photoResponse = child.getValue(FirebasePhoto.class);
                            PhotoEntity photoEntity = new PhotoEntity(photoResponse.getCopyright(), photoResponse.getDate(), photoResponse.getExplanation(), photoResponse.getTitle(), photoResponse.getUrl());
                            listaFotos.add(photoEntity);
                        }

                        adapter.update(listaFotos);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplication(), databaseError.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }

    public void carregaDadosBD() {
        disposable.add(
                repository.retornaPhotosBD(getApplication())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(subscription -> loading.setValue(true))
                        .doAfterTerminate(() -> loading.setValue(false))
                        .subscribe(albumList ->
                                        mutableLiveDataPhoto.setValue(albumList),
                                throwable ->
                                        mutableLiveDataErro.setValue(throwable.getMessage() + "problema banco de dados"))
        );
    }

    public void insereDadosBd(PhotoEntity photoEntity) {

        new Thread(() -> {
            repository.inserePhotoBd(photoEntity, getApplication());
        }).start();
    }
}