package com.example.spacenbeyond.model;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("media_type")
    private String mMediaType;

    public String getmMediaType() {
        return mMediaType;
    }

    public void setmMediaType(String mMediaType) {
        this.mMediaType = mMediaType;
    }
}
