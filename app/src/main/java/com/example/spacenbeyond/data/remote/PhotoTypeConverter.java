package com.example.spacenbeyond.data.remote;

import androidx.room.TypeConverter;
import com.example.spacenbeyond.model.PhotoEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PhotoTypeConverter {

    @TypeConverter
    public Object fromObject(String value) {
        Type listType = (Type) new TypeToken<Object>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromJsonObject(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    @TypeConverter
    public List<PhotoEntity> fromListPhoto(String value) {
        Type listType = (Type) new TypeToken<List<PhotoEntity>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromListPhotoObject(List<PhotoEntity> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}