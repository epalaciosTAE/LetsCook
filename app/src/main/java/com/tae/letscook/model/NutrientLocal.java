package com.tae.letscook.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eduardo on 16/01/2016.
 */
public class NutrientLocal implements Parcelable {

    private String label;
    private double total;
    private String unit;

    public NutrientLocal(String label, double total, String unit) {
        this.label = label;
        this.total = total;
        this.unit = unit;
    }


    protected NutrientLocal(Parcel in) {
        label = in.readString();
        total = in.readDouble();
        unit = in.readString();
    }

    public static final Creator<NutrientLocal> CREATOR = new Creator<NutrientLocal>() {
        @Override
        public NutrientLocal createFromParcel(Parcel in) {
            return new NutrientLocal(in);
        }

        @Override
        public NutrientLocal[] newArray(int size) {
            return new NutrientLocal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(label);
        dest.writeDouble(total);
        dest.writeString(unit);
    }

    public String getLabel() {
        return label;
    }

    public double getTotal() {
        return total;
    }

    public String getUnit() {
        return unit;
    }
}
