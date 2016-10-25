package com.example.ml.ejercicio.dummy;

import org.json.JSONObject;

import java.io.Serializable;

public class Item implements Serializable{
    public final String id;
    public final String title;
    public final String details;
    public final String thumbnail;
    public Double price;


    public Item(String id, String title, String details, String thumbnail) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.thumbnail= thumbnail;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return title;
    }


}