package com.example.spacenbeyond.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ajuda implements Parcelable {

    private String title;
    private String subtitle;

    public Ajuda(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    protected Ajuda(Parcel in) {
        title = in.readString();
        subtitle = in.readString();
    }

    public static final Creator<Ajuda> CREATOR = new Creator<Ajuda>() {
        @Override
        public Ajuda createFromParcel(Parcel in) {
            return new Ajuda(in);
        }

        @Override
        public Ajuda[] newArray(int size) {
            return new Ajuda[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(subtitle);
    }

}