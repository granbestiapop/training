package com.example.ml.ejercicio.dto.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ml.ejercicio.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Item implements Parcelable{
    public final String id;
    public final String title;
    public final String details;
    public final String thumbnail;
    public Double price;

    public List<String> images;


    public Item(String id, String title, String details, String thumbnail) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.thumbnail= thumbnail;
        images= new ArrayList<>();
    }

    protected Item(Parcel in) {
        id = in.readString();
        title = in.readString();
        details = in.readString();
        thumbnail = in.readString();
        images= in.createStringArrayList();
        //images = in.readStringL;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(details);
        parcel.writeString(thumbnail);
        parcel.writeStringList(images);
    }

    public static Item fromJSON(JSONObject o){
        Item i= null;
        try{
            String title= o.getString("title");
            Double price= o.getDouble("price");
            String id= o.getString("id");
            String thumb= o.getString("thumbnail");
            JSONArray images= o.getJSONArray("pictures");

            i= new Item(id,title,"Descripcion HARDCODED",thumb);
            i.price= price;
            //dump images
            int size= images.length();
            for(int it=0; it<size;it++){
                JSONObject image= images.getJSONObject(it);
                i.images.add(image.getString("url"));
            }
        }catch (Exception e){

        }
        return i;
    }
}