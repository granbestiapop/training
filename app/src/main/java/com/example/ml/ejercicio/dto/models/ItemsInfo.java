package com.example.ml.ejercicio.dto.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class ItemsInfo implements Parcelable {

    private List<Item> items;
    private Item search;

    public ItemsInfo(List<Item> items){
        this.items= items;
    }

    protected ItemsInfo(Parcel in) {
        items= in.createTypedArrayList(Item.CREATOR);
        search= in.readParcelable(Item.class.getClassLoader());
    }

    public static final Creator<ItemsInfo> CREATOR = new Creator<ItemsInfo>() {
        @Override
        public ItemsInfo createFromParcel(Parcel in) {
            return new ItemsInfo(in);
        }

        @Override
        public ItemsInfo[] newArray(int size) {
            return new ItemsInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(items);
        parcel.writeParcelable(search, i);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Item getSearch() {
        return search;
    }

    public void setSearch(Item search) {
        this.search = search;
    }
}
