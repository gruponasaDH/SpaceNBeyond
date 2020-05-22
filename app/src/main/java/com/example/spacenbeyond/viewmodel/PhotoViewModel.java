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
import com.example.spacenbeyond.view.adapter.FavoritosRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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

    private final MutableLiveData<Throwable> resultLiveDataError = new MutableLiveData<>();
    private final MutableLiveData<PhotoEntity> favoriteAdded = new MutableLiveData<>();

    private final MutableLiveData<List<PhotoEntity>> mutableLiveDataPhoto = new MutableLiveData<>();
    public final LiveData<List<PhotoEntity>> liveDataPhoto = mutableLiveDataPhoto;

    private final MutableLiveData<String> mutableLiveDataErro = new MutableLiveData<>();
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

    public void deletarFavorito(PhotoResponse photoResponse) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(AppUtil.getIdUsuario(getApplication()));
        Query applesQuery = ref.child("favorites").orderByChild("title").equalTo(photoResponse.getTitle());
        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
                deletarPhotoEntity(photoResponse.getTitle());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { resultLiveDataError.setValue(new Throwable(databaseError.getMessage())); }
        });
    }

    public void salvarFavorito(PhotoResponse photoResponse){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(AppUtil.getIdUsuario(getApplication()) + "/favorites");

        reference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean existe = false;
                for (DataSnapshot resultSnapshot : dataSnapshot.getChildren()){
                    PhotoEntity firebaseResult = resultSnapshot.getValue(PhotoEntity.class);

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

                Toast.makeText(getApplication(), "Foto " + photoEntity.getTitle() + " inserida nos favoritos.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplication(), databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void carregaFavoritos(FavoritosRecyclerViewAdapter adapter){

        new Thread(() -> {

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
        }).start();
    }

    public void carregaDadosBD() {
        disposable.add(
                repository.retornaPhotosBD(getApplication())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(subscription -> loading.setValue(true))
                        .doAfterTerminate(() -> loading.setValue(false))
                        .subscribe(mutableLiveDataPhoto::setValue,
                                throwable ->
                                        mutableLiveDataErro.setValue(throwable.getMessage() + "problema banco de dados"))
        );
    }

    public void insereDadosBd(PhotoEntity photoEntity) {

        new Thread(() -> repository.inserePhotoBd(photoEntity, getApplication())).start();
    }

    public void deletarPhotoEntity(String title) {

        boolean valid = true;

        new Thread(() -> {
            PhotoEntity photoEntity = repository.getPhotoEntity(title, getApplication());
            repository.apagaOsDadosBD(photoEntity, getApplication());
        }).start();

        if (valid) {
            Toast.makeText(getApplication(), "Foto " + title + " retirada dos favoritos.", Toast.LENGTH_SHORT).show();
        }
    }
}
