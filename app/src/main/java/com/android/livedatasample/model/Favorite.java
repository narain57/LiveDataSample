package com.android.livedatasample.model;

public class Favorite {
    private String name;
    private String color;

    public Favorite() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Favorite(Favorite favourites) {
        name = favourites.name;
        color = favourites.color;
    }
}
