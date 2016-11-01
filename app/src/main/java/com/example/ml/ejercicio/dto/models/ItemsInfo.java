package com.example.ml.ejercicio.dto.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class ItemsInfo implements Parcelable {

    private List<ItemWrap> items;
    private Item search;
    public boolean isTrackeable;

    public ItemsInfo(List<ItemWrap> items){
        this.items= items;
    }

    protected ItemsInfo(Parcel in) {
        items= in.createTypedArrayList(ItemWrap.CREATOR);
        search= in.readParcelable(Item.class.getClassLoader());
        isTrackeable= in.readByte() != 0;
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
        parcel.writeByte((byte) (isTrackeable ? 1 : 0));
    }

    public List<ItemWrap> getItems() {
        return items;
    }

    public void setItems(List<ItemWrap> items) {
        this.items = items;
    }

    public Item getSearch() {
        return search;
    }

    public void setSearch(Item search) {
        this.search = search;
    }

    public boolean isTrackeable() {
        return isTrackeable;
    }

    public void setTrackeable(boolean trackeable) {
        isTrackeable = trackeable;
    }
}
