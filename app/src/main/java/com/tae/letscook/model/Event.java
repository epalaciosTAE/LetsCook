package com.tae.letscook.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eduardo on 25/01/2016.
 */
public class Event implements Parcelable{

    private String title, address, chefId, userName, date;
//    private String address;
//    private String chefId;
    private double lat, lng;

    public Event(String title, String address, double lat, double lng, String date) {
        this.title = title;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.date = date;
    }

    public Event(String title, String address, String chefId, double lat, double lng) {
        this.title = title;
        this.address = address;
        this.chefId = chefId;
        this.lat = lat;
        this.lng = lng;
    }

    public Event(String title, String address, String chefId, String userName, String date, double lat, double lng) {
        this.title = title;
        this.address = address;
        this.chefId = chefId;
        this.userName = userName;
        this.date = date;
        this.lat = lat;
        this.lng = lng;
    }

    protected Event(Parcel in) {
        title = in.readString();
        address = in.readString();
        chefId = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        userName = in.readString();
        date = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(address);
        dest.writeString(chefId);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeString(userName);
        dest.writeString(date);
    }

    public void setChefId(String chefId) {
        this.chefId = chefId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getChefId() {
        return chefId;
    }

    public String getUserName() {
        return userName;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getDate() {
        return date;
    }
}
