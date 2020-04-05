package com.example.spacenbeyond.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Explanation {

    @SerializedName("explanation")
    private String mExplanation;

    public String getmExplanation() {
        return mExplanation;
    }

    public void setmExplanation(String mExplanation) {
        this.mExplanation = mExplanation;
    }
}
