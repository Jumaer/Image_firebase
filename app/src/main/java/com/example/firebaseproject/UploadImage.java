package com.example.firebaseproject;

public class UploadImage {
    private String imageName, ImageUrl;

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
