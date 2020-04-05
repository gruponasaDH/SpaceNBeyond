package com.example.spacenbeyond.repository;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.spacenbeyond.model.Photo;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PhotoDao {

    @Query("SELECT * from photos")
    Flowable<List<Photo>> todosOsFotosBd();
}
