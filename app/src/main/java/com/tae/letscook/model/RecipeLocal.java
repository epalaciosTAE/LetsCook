package com.tae.letscook.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.tae.letscook.api.apiModel.Ingredient;

import java.util.List;

/**
 * Created by Eduardo on 16/01/2016.
 */
public class RecipeLocal implements Parcelable {

    private String label;
    private String imageUrl;
    private double calories;
    private List<String> ingredientLines, dietLabels;
    private List<NutrientLocal> nutrients;

    public RecipeLocal(String label, String imageUrl, double calories, List<String> ingredientLines, List<NutrientLocal> nutrients, List<String> dietLabels) {
        this.label = label;
        this.imageUrl = imageUrl;
        this.calories = calories;
        this.ingredientLines = ingredientLines;
        this.nutrients = nutrients;
        this.dietLabels = dietLabels;
    }

    protected RecipeLocal(Parcel in) {
        label = in.readString();
        imageUrl = in.readString();
        calories = in.readDouble();
        ingredientLines = in.createStringArrayList();
        dietLabels = in.createStringArrayList();
        nutrients = in.createTypedArrayList(NutrientLocal.CREATOR);
    }

    public static final Creator<RecipeLocal> CREATOR = new Creator<RecipeLocal>() {
        @Override
        public RecipeLocal createFromParcel(Parcel in) {
            return new RecipeLocal(in);
        }

        @Override
        public RecipeLocal[] newArray(int size) {
            return new RecipeLocal[size];
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
        dest.writeDouble(calories);
        dest.writeStringList(ingredientLines);
        dest.writeStringList(dietLabels);
        dest.writeTypedList(nutrients);
    }

    public String getLabel() {
        return label;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getCalories() {
        return calories;
    }

    public List<NutrientLocal> getNutrients() {

        return nutrients;
    }

    public List<String> getIngredientLines() {
        return ingredientLines;
    }

    public List<String> getDietLabels() {
        return dietLabels;
    }
}
