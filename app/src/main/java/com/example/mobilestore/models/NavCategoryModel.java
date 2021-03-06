package com.example.mobilestore.models;

public class NavCategoryModel {
    String name, description, rating, img_url, type;
    int price;

    public NavCategoryModel() {
    }

    public NavCategoryModel(String name, String description, String rating, String img_url, String type, int price) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.img_url = img_url;
        this.type = type;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getRating() {
        return rating;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }
}
