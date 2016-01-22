package com.tae.letscook.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Eduardo on 29/12/2015.
 */
public class Chef implements Parcelable{

    private String name;
    private String email;
    private String picture;

    public Chef(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
    }


    protected Chef(Parcel in) {
        name = in.readString();
        email = in.readString();
        picture = in.readString();
    }

    public static final Creator<Chef> CREATOR = new Creator<Chef>() {
        @Override
        public Chef createFromParcel(Parcel in) {
            return new Chef(in);
        }

        @Override
        public Chef[] newArray(int size) {
            return new Chef[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(picture);
    }
}
