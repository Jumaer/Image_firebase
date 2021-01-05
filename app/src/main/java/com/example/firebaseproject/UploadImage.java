package com.example.firebaseproject;

import com.google.firebase.database.Exclude;
import com.google.gson.annotations.Expose;

public class UploadImage {
    private String imageName, ImageUrl,key;

   @Exclude
    public String getKey() {
        return key;
    }
    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public UploadImage(String imageName, String imageUrl) {
        this.imageName = imageName;
        ImageUrl = imageUrl;
    }

    public UploadImage() {
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
