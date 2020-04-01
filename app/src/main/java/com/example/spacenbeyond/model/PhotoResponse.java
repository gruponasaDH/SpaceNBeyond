
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

    public void setCopyright(String copyright) {
        mCopyright = copyright;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getExplanation() {
        return mExplanation;
    }

    public void setExplanation(String explanation) {
        mExplanation = explanation;
    }

    public String getHdurl() {
        return mHdurl;
    }

    public void setHdurl(String hdurl) {
        mHdurl = hdurl;
    }

    public String getMediaType() {
        return mMediaType;
    }

    public void setMediaType(String mediaType) {
        mMediaType = mediaType;
    }

    public String getServiceVersion() {
        return mServiceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        mServiceVersion = serviceVersion;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

}
