package com.example.spacenbeyond.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.spacenbeyond.Model.DadosHome;
import com.example.spacenbeyond.Model.Imagem;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface SpaceDao {
    @Insert void insereListaBD(List<DadosHome> dadosHomeList) ;

    @Query("SELECT * FROM dadosHome")
    Flowable<Imagem> recuperadadosHomeBD();

    @Delete
    void apagaProdutoBd(DadosHome dadosHome);
}

