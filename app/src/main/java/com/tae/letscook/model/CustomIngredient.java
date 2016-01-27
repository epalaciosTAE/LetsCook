package com.tae.letscook.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eduardo on 26/01/2016.
 */
public class CustomIngredient implements Parcelable{

    private String ingredient, amount;

    public CustomIngredient(String ingredient, String amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }

    protected CustomIngredient(Parcel in) {
        ingredient = in.readString();
        amount = in.readString();
    }

    public static final Creator<CustomIngredient> CREATOR = new Creator<CustomIngredient>() {
        @Override
        public CustomIngredient createFromParcel(Parcel in) {
            return new CustomIngredient(in);
        }

        @Override
        public CustomIngredient[] newArray(int size) {
            return new CustomIngredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ingredient);
        dest.writeString(amount);
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getAmount() {
        return amount;
    }
}
