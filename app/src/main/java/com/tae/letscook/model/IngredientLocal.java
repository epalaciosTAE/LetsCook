package com.tae.letscook.model;

/**
 * Created by Eduardo on 17/01/2016.
 */
public class IngredientLocal {

    private int fkRecipe;
    private String ingredient;

    public IngredientLocal(int fkRecipe, String ingredient) {
        this.fkRecipe = fkRecipe;
        this.ingredient = ingredient;
    }

    public int getFkRecipe() {
        return fkRecipe;
    }

    public String getIngredient() {
        return ingredient;
    }
}
