package com.example.spacenbeyond.data.remote;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.spacenbeyond.model.FirebasePhoto;
import com.example.spacenbeyond.model.PhotoEntity;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PhotoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insereListaBD(PhotoEntity photoEntity);

    @Query("SELECT * FROM favorite")
    Flowable<List<PhotoEntity>> recuperaPhotosDoBD();

    @Delete
    void apagaDadosBd(PhotoEntity photoEntity);
}
