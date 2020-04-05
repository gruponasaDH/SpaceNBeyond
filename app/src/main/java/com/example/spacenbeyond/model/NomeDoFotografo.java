package com.example.spacenbeyond.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NomeDoFotografo {

    @SerializedName("copyright")
    private String mCopyright;

    public String getmCopyright() {
        return mCopyright;
    }

    public void setmCopyright(String mCopyright) {
        this.mCopyright = mCopyright;
    }
}
