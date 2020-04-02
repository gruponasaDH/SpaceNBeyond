package com.example.spacenbeyond.model;

import com.google.gson.annotations.SerializedName;

public class Translation {

    @SerializedName("translatedText")
    private String mTranslatedText;

    public String getTranslatedText() {
        return mTranslatedText;
    }

    public void setTranslatedText(String translatedText) {
        mTranslatedText = translatedText;
    }

}
