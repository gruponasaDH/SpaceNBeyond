package com.example.spacenbeyond.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ajuda implements Parcelable {
    private int imageView_ajuda;
    private String title;
    private String subtitle;

    public Ajuda(int imageView_ajuda, String title, String subtitle) {
        this.imageView_ajuda = imageView_ajuda;
        this.title = title;
        this.subtitle = subtitle;
    }

    public Ajuda(Parcel in) {
        imageView_ajuda = in.readInt();
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

    public int getImageView_ajuda() {
        return imageView_ajuda;
    }

    public void setImageView_ajuda(int imageView_ajuda) {
        this.imageView_ajuda = imageView_ajuda;
    }

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
        dest.writeInt(imageView_ajuda);
        dest.writeString(title);
        dest.writeString(subtitle);
    }
}