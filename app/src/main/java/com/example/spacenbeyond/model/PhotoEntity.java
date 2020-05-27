package com.example.spacenbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite")
public class PhotoEntity implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    public PhotoEntity() {}

    public PhotoEntity(String copyright, String date, String explanation, String title, String url) {
        this.copyright = copyright;
        this.date = date;
        this.explanation = explanation;
        this.title = title;
        this.url = url;
    }

    private String copyright;

    private String date;

    private String explanation;

    private String title;

    private String url;

    protected PhotoEntity(Parcel in) {
        id = in.readLong();
        copyright = in.readString();
        date = in.readString();
        explanation = in.readString();
        title = in.readString();
        url = in.readString();
    }

    public static final Creator<PhotoEntity> CREATOR = new Creator<PhotoEntity>() {
        @Override
        public PhotoEntity createFromParcel(Parcel in) {
            return new PhotoEntity(in);
        }

        @Override
        public PhotoEntity[] newArray(int size) {
            return new PhotoEntity[size];
        }
    };

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(copyright);
        dest.writeString(date);
        dest.writeString(explanation);
        dest.writeString(title);
        dest.writeString(url);
    }
}