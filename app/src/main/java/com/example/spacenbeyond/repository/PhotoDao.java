package com.example.spacenbeyond.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.spacenbeyond.model.Photo;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PhotoDao {

    @Insert
    void inserirPhotoBd(Photo photo);

    @Delete
    void apagaPhotoBd(Photo photo);

    @Query("SELECT * from photo")
    Flowable<List<Photo>> todosOsphotosBd();

    @Query("SELECT * FROM photo  WHERE title=:title")
    Photo getPhotoPorTitleBd(String title);
}
