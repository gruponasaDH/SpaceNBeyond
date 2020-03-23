package com.example.spacenbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DadosAjuda implements Parcelable {
    private int imageView_ajuda;
    private String title;
    private String subtitle;

    public DadosAjuda(int imageView_ajuda, String title, String subtitle) {
        this.imageView_ajuda = imageView_ajuda;
        this.title = title;
        this.subtitle = subtitle;
    }

    public DadosAjuda(Parcel in) {
        imageView_ajuda = in.readInt();
        title = in.readString();
        subtitle = in.readString();
    }

    public static final Creator<DadosAjuda> CREATOR = new Creator<DadosAjuda>() {
        @Override
        public DadosAjuda createFromParcel(Parcel in) {
            return new DadosAjuda(in);
        }

        @Override
        public DadosAjuda[] newArray(int size) {
            return new DadosAjuda[size];
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