package com.example.spacenbeyond.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.spacenbeyond.model.DadosHome;
import com.example.spacenbeyond.model.Imagem;

public class SpaceDatabase {

    @Database(entities = {DadosHome.class, Imagem.class}, version = 1, exportSchema = false)
    public abstract static class DadosHomeDataBase extends RoomDatabase {

        private static volatile DadosHomeDataBase INSTANCE;

        public abstract SpaceDao spaceDao();

        public static DadosHomeDataBase getDatabase(Context context) {
            if (INSTANCE == null) {
                synchronized (DadosHomeDataBase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context, DadosHomeDataBase.class, "musicas_db")
                                .fallbackToDestructiveMigration()
                                .build();
                    }
                }
            }
            return INSTANCE;
        }
    }
}
