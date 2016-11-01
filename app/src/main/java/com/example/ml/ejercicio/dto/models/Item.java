package com.example.ml.ejercicio.dto.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.ml.ejercicio.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Item implements Parcelable {
    public final String id;
    public final String title;
    public final String details;
    public final String thumbnail;
    public String last_update;
    public Double price;
    public String stop_time;

    public List<String> images;


    public Item(String id, String title, String details, String thumbnail) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.thumbnail = thumbnail;
        images = new ArrayList<>();
    }

    protected Item(Parcel in) {
        id = in.readString();
        title = in.readString();
        details = in.readString();
        thumbnail = in.readString();
        images = in.createStringArrayList();
        last_update = in.readString();
        stop_time= in.readString();
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

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
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
        parcel.writeString(last_update);
        parcel.writeString(stop_time);
    }

    public static Item fromJSON(JSONObject o) {
        Item i = null;
        try {
            String title = o.getString("title");
            Double price = o.getDouble("price");
            String id = o.getString("id");
            String thumb = o.getString("thumbnail");
            String stop_time= o.getString("stop_time");
            String last_update = o.getString("last_updated");

            i = new Item(id, title, "Descripcion", thumb);
            i.price = price;
            i.last_update = last_update;
            i.stop_time= stop_time;

            //dump images
            JSONArray images = o.getJSONArray("pictures");

            int size = images.length();
            for (int it = 0; it < size; it++) {
                JSONObject image = images.getJSONObject(it);
                i.images.add(image.getString("url"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static List<Item> listFromJSON(JSONArray o) {
        Item i = null;
        List<Item> list = new ArrayList<>();

        try {
            int size = o.length();
            for (int it = 0; it < size; it++) {
                JSONObject json = o.getJSONObject(it);
                Item item = fromJSON(json);
                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}