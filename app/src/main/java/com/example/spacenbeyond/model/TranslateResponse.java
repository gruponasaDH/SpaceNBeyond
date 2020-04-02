package com.example.spacenbeyond.model;

import com.google.gson.annotations.SerializedName;

public class TranslateResponse {

    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

}
