package com.example.spacenbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Button;

public class DadosHome implements Parcelable {
    private Button eng;
    private Button pt;
    private int imagem;
    private String nomeFoto;
    private String nomeAutor;
    private String descricao;
    private int favoritos;
    private int compartilhar;
    private int dowlo;

    public Button getEng() { return eng; }

    public void setEng(Button eng) { this.eng = eng;}

    public Button getPt() { return pt; }

    public void setPt(Button pt) { this.pt = pt; }

    public int getImagem() { return imagem; }

    public void setImagem(int imagem) { this.imagem = imagem; }

    public String getNomeFoto() { return nomeFoto; }

    public void setNomeFoto(String nomeFoto) { this.nomeFoto = nomeFoto; }

    public String getNomeAutor() { return nomeAutor; }

    public void setNomeAutor(String nomeAutor) { this.nomeAutor = nomeAutor; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getFavoritos() { return favoritos; }

    public void setFavoritos(int favoritos) {this.favoritos = favoritos; }

    public int getCompartilhar() { return compartilhar; }

    public void setCompartilhar(int compartilhar) { this.compartilhar = compartilhar; }

    public int getDowlo() { return dowlo; }

    public void setDowlo(int dowlo) { this.dowlo = dowlo; }

    public static Creator<DadosHome> getCREATOR() { return CREATOR; }

    public DadosHome(Button eng, Button pt, int imagem, String nomeFoto,
                     String nomeAutor, String descricao, int favoritos, int compartilhar,
                     int dowlo) {
        this.eng = eng;
        this.pt = pt;
        this.imagem = imagem;
        this.nomeFoto = nomeFoto;
        this.nomeAutor = nomeAutor;
        this.descricao = descricao;
        this.favoritos = favoritos;
        this.compartilhar = compartilhar;
        this.dowlo = dowlo;
    }

    protected DadosHome(Parcel in) {
        nomeAutor = in.readString();
        nomeFoto = in.readString();
        imagem = in.readInt();
        favoritos= in.readInt();
        compartilhar= in.readInt();
        dowlo= in.readInt();
    }

    public static final Creator<DadosHome> CREATOR = new Creator<DadosHome>() {
        @Override
        public DadosHome createFromParcel(Parcel in) {
            return new DadosHome(in);
        }

        @Override
        public DadosHome[] newArray(int size) {
            return new DadosHome[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(imagem);
        parcel.writeInt(favoritos);
        parcel.writeInt(compartilhar);
        parcel.writeInt(dowlo);
        parcel.writeString(nomeAutor);
        parcel.writeString(nomeFoto);
        parcel.writeString(descricao);
    }
}
