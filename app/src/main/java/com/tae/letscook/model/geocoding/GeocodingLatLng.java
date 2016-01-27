package com.tae.letscook.model.geocoding;

import android.os.Parcel;
import android.os.Parcelable;

public class GeocodingLatLng implements Parcelable{

    private double lat;
    private double lng;

    public GeocodingLatLng(double lat, double lng) {
        super();
        this.lat = lat;
        this.lng = lng;
    }

    protected GeocodingLatLng(Parcel in) {
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<GeocodingLatLng> CREATOR = new Creator<GeocodingLatLng>() {
        @Override
        public GeocodingLatLng createFromParcel(Parcel in) {
            return new GeocodingLatLng(in);
        }

        @Override
        public GeocodingLatLng[] newArray(int size) {
            return new GeocodingLatLng[size];
        }
    };

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }
}
