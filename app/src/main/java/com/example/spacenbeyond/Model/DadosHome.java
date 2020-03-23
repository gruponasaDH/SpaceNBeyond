package com.example.spacenbeyond.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.ImageView;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class DadosHome implements Parcelable {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "imagem")
    private int imagem;
    @ColumnInfo(name = "nomeFoto")
    private String nomeFoto;
    @ColumnInfo(name = "nomeAutor")
    private String nomeAutor;
    @ColumnInfo(name = "descricao")
    private String descricao;
    @ColumnInfo(name = "favoritos")
    private int favoritos;
    @ColumnInfo(name = "compartilhar")
    private int compartilhar;
    @ColumnInfo(name = "dowlo")
    private int dowlo;

    public DadosHome(int id, int imagem, String nomeFoto, String nomeAutor, String descricao, int favoritos, int compartilhar, int dowlo) {
        this.id = id;
        this.imagem = imagem;
        this.nomeFoto = nomeFoto;
        this.nomeAutor = nomeAutor;
        this.descricao = descricao;
        this.favoritos = favoritos;
        this.compartilhar = compartilhar;
        this.dowlo = dowlo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
