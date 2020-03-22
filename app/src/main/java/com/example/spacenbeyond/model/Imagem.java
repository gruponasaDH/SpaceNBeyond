package com.example.spacenbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Imagem implements Parcelable {
    private int imagem;

    public Imagem(int imagem) {
        this.imagem = imagem;
    }

    protected Imagem(Parcel in) {
        imagem = in.readInt();
    }

    public static final Creator<Imagem> CREATOR = new Creator<Imagem>() {
        @Override
        public Imagem createFromParcel(Parcel in) {
            return new Imagem( in );
        }

        @Override
        public Imagem[] newArray(int size) {
            return new Imagem[size];
        }
    };

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt( imagem );
    }
}