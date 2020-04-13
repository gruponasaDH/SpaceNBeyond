package com.example.spacenbeyond.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Photo {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("date")
    public Integer mDate;

    @SerializedName("explanation")
    public String mExplanation;

    @SerializedName("media_type")
    public String mMediaType;

    @SerializedName("copyright")
    public String mCopyright;

    @SerializedName("title")
    public String mTitle;


    public Integer getmDate() { return mDate; }

    public void setmDate(Integer mDate) { this.mDate = mDate; }

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
