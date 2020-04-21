package com.example.spacenbeyond.util;

import android.content.Context;
import android.content.SharedPreferences;

public class AppUtil {


    public static String getIdUsuario(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("APP", Context.MODE_PRIVATE);
        return preferences.getString("UIID", "");
    }



}
