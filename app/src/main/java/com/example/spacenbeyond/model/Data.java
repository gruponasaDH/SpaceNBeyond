package com.example.spacenbeyond.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("translations")
    private List<Translation> mTranslations;

    public List<Translation> getTranslations() {
        return mTranslations;
    }

    public void setTranslations(List<Translation> translations) {
        mTranslations = translations;
    }

}
