package com.tae.letscook.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eduardo on 16/01/2016.
 */
public class ItemRecipe implements Parcelable {

    private String label, imageUrl;

    public ItemRecipe(String label, String imageUrl) {
        this.label = label;
        this.imageUrl = imageUrl;
    }

    protected ItemRecipe(Parcel in) {
        label = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<ItemRecipe> CREATOR = new Creator<ItemRecipe>() {
        @Override
        public ItemRecipe createFromParcel(Parcel in) {
            return new ItemRecipe(in);
        }

        @Override
        public ItemRecipe[] newArray(int size) {
            return new ItemRecipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(label);
        dest.writeString(imageUrl);
    }

    public String getLabel() {
        return label;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
