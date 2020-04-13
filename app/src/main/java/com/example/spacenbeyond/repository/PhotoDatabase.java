package com.example.spacenbeyond.repository;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.spacenbeyond.model.Photo;


@androidx.room.Database(entities = {Photo.class}, version = 1, exportSchema = false )
@TypeConverters(PhotoConverter.class)
public abstract class PhotoDatabase extends RoomDatabase {

    public abstract PhotoDao photoDao();
    private static volatile PhotoDatabase INSTANCE;

    public static PhotoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PhotoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PhotoDatabase.class, "photo_bd")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
