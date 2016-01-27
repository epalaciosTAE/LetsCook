package com.tae.letscook.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Eduardo on 26/01/2016.
 */
public class CustomRecipe implements Parcelable{

    private long id;
    private String title, imagePath, budget, level, people, time, chefName, chefId, imageServerPath;
    private List<CustomIngredient> ingredients;

    public CustomRecipe(String title, String imagePath, String budget, String level, String people, String time, List<CustomIngredient> ingredients) {
        this.title = title;
        this.imagePath = imagePath;
        this.budget = budget;
        this.level = level;
        this.people = people;
        this.time = time;
        this.ingredients = ingredients;
    }

    public CustomRecipe(String title, String budget, String level, String people, String time, List<CustomIngredient> ingredients) {
        this.title = title;
        this.budget = budget;
        this.level = level;
        this.people = people;
        this.time = time;
        this.ingredients = ingredients;
    }


    public CustomRecipe(long id, String title, String imagePath, String budget, String level, String people,
                        String time, String chefName, String chefId, List<CustomIngredient> ingredients) {
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
        this.budget = budget;
        this.level = level;
        this.people = people;
        this.time = time;
        this.chefName = chefName;
        this.chefId = chefId;
        this.ingredients = ingredients;
    }

    public CustomRecipe(long id, String title, String imagePath, String budget, String level,
                        String people, String time, String chefName, String chefId,
                        String imageServerPath, List<CustomIngredient> ingredients) {
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
        this.budget = budget;
        this.level = level;
        this.people = people;
        this.time = time;
        this.chefName = chefName;
        this.chefId = chefId;
        this.imageServerPath = imageServerPath;
        this.ingredients = ingredients;
    }


    protected CustomRecipe(Parcel in) {
        id = in.readLong();
        title = in.readString();
        imagePath = in.readString();
        budget = in.readString();
        level = in.readString();
        people = in.readString();
        time = in.readString();
        chefName = in.readString();
        chefId = in.readString();
        imageServerPath = in.readString();
        ingredients = in.createTypedArrayList(CustomIngredient.CREATOR);
    }

    public static final Creator<CustomRecipe> CREATOR = new Creator<CustomRecipe>() {
        @Override
        public CustomRecipe createFromParcel(Parcel in) {
            return new CustomRecipe(in);
        }

        @Override
        public CustomRecipe[] newArray(int size) {
            return new CustomRecipe[size];
        }
    };

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }

    public void setChefId(String chefId) {
        this.chefId = chefId;
    }

    public String getChefName() {
        return chefName;
    }

    public String getChefId() {
        return chefId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBudget() {
        return budget;
    }

    public String getLevel() {
        return level;
    }

    public String getPeople() {
        return people;
    }

    public String getTime() {
        return time;
    }

    public String getImageServerPath() {
        return imageServerPath;
    }

    public void setImageServerPath(String imageServerPath) {
        this.imageServerPath = imageServerPath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<CustomIngredient> getIngredients() {
        return ingredients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(imagePath);
        dest.writeString(budget);
        dest.writeString(level);
        dest.writeString(people);
        dest.writeString(time);
        dest.writeString(chefName);
        dest.writeString(chefId);
        dest.writeString(imageServerPath);
        dest.writeTypedList(ingredients);
    }
}
