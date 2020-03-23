package com.example.spacenbeyond.repository;

import androidx.room.TypeConverter;

import com.example.spacenbeyond.model.DadosHome;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SpaceConverter {
    @TypeConverter
    public List<DadosHome> fromString(String value) {
        Type listType = (Type) new TypeToken<List<String>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromList(List<DadosHome> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}

