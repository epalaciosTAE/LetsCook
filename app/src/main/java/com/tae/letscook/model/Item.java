package com.tae.letscook.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eduardo on 06/12/2015.
 */
public class Item implements Parcelable {

    private String item;
    private int imageId;

    public Item(String item, int id) {
        this.item = item;
        this.imageId = id;
    }

    protected Item(Parcel in) {
        item = in.readString();
        imageId = in.readInt();
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

    public int getImageId() {
        return imageId;
    }

    public void setImageID(int id) {
        this.imageId = imageId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item);
        dest.writeInt(imageId);
    }
}
