package com.example.spacenbeyond.data.remote;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.spacenbeyond.model.PhotoEntity;

@Database(entities = {PhotoEntity.class}, version = 1, exportSchema = false)
@TypeConverters(PhotoTypeConverter.class)
public abstract class PhotoDatabase extends RoomDatabase {
    private static volatile PhotoDatabase INSTANCE;

    public abstract PhotoDAO artistaDAO();

    public static PhotoDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (PhotoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, PhotoDatabase.class, "photos_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}