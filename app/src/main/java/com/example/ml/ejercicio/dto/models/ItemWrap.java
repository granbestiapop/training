package com.example.ml.ejercicio.dto.models;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ItemWrap implements Parcelable {
    public final String id;
    public final String title;
    public final String details;
    public final String thumbnail;
    public Double price;


    public ItemWrap(String id, String title, String details, String thumbnail) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.thumbnail = thumbnail;
    }

    protected ItemWrap(Parcel in) {
        id = in.readString();
        title = in.readString();
        details = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<ItemWrap> CREATOR = new Creator<ItemWrap>() {
        @Override
        public ItemWrap createFromParcel(Parcel in) {
            return new ItemWrap(in);
        }

        @Override
        public ItemWrap[] newArray(int size) {
            return new ItemWrap[size];
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
    }

    public static ItemWrap fromJSON(JSONObject o) {
        ItemWrap i = null;
        try {
            String title = o.getString("title");
            Double price = o.getDouble("price");
            String id = o.getString("id");
            String thumb = o.getString("thumbnail");
            i = new ItemWrap(id, title, "Descripcion", thumb);
            i.price = price;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static List<ItemWrap> listFromJSON(JSONArray o) {
        Item i = null;
        List<ItemWrap> list = new ArrayList<>();

        try {
            int size = o.length();
            for (int it = 0; it < size; it++) {
                JSONObject json = o.getJSONObject(it);
                ItemWrap item = fromJSON(json);
                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
