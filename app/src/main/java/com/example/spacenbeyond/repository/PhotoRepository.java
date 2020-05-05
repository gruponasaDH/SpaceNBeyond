package com.example.spacenbeyond.repository;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.spacenbeyond.data.remote.PhotoDatabase;
import com.example.spacenbeyond.model.PhotoEntity;
import com.example.spacenbeyond.model.PhotoResponse;
import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import static com.example.spacenbeyond.data.remote.PhotoRetrofitService.getApiService;

public class PhotoRepository {

    public Observable<PhotoResponse> getPhotoOfDay(String date, String apiKey){
        return getApiService().getPhotoOfDay(date, apiKey);
    }

    public Flowable<List<PhotoEntity>> retornaPhotosBD(Context context) {
        return PhotoDatabase.getDatabase(context).artistaDAO().recuperaPhotosDoBD();
    }

    public void inserePhotoBd(PhotoEntity photoEntity, Context context){
        PhotoDatabase.getDatabase(context).artistaDAO().insereListaBD(photoEntity);
    }

    public PhotoEntity getPhotoEntity(String title, Context context){
        return PhotoDatabase.getDatabase(context).artistaDAO().getPhotoEntity(title);
    }

    public void apagaOsDadosBD(PhotoEntity photoEntity, Context context){
        PhotoDatabase.getDatabase(context).artistaDAO().apagaDadosBd(photoEntity);
    }
}