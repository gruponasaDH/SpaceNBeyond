package com.example.spacenbeyond.model;

import com.google.gson.annotations.SerializedName;

public class PhotoResponse {

    @SerializedName("copyright")
    private String mCopyright;

    @SerializedName("date")
    private String mDate;

    @SerializedName("explanation")
    private String mExplanation;

    @SerializedName("hdurl")
    private String mHdurl;

    @SerializedName("media_type")
    private String mMediaType;

    @SerializedName("service_version")
    private String mServiceVersion;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("url")
    private String mUrl;

    public String getCopyright() {
        return mCopyright;
    }

    public String getDate() { return mDate; }


    public String getExplanation() {
        return mExplanation;
    }


    public String getTitle() {
        return mTitle;
    }


    public String getUrl() {
        return mUrl;
    }

    public void setmCopyright(String mCopyright) {
        this.mCopyright = mCopyright;
    }
}