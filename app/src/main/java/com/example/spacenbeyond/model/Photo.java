package com.example.spacenbeyond.model;

import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("date")
    private String mDate;

    @SerializedName("explanation")
    private String mExplanation;

    @SerializedName("media_type")
    private String mMediaType;

    @SerializedName("copyright")
    private String mCopyright;

    @SerializedName("title")
    private String mTitle;

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmExplanation() {
        return mExplanation;
    }

    public void setmExplanation(String mExplanation) {
        this.mExplanation = mExplanation;
    }

    public String getmMediaType() {
        return mMediaType;
    }

    public void setmMediaType(String mMediaType) {
        this.mMediaType = mMediaType;
    }

    public String getmCopyright() {
        return mCopyright;
    }

    public void setmCopyright(String mCopyright) {
        this.mCopyright = mCopyright;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
