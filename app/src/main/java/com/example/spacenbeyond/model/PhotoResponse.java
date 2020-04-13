package com.example.spacenbeyond.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class PhotoResponse {
    @Expose
    private List<Photo> photos;

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
