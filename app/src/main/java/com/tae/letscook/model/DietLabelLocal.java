package com.tae.letscook.model;

/**
 * Created by Eduardo on 17/01/2016.
 */
public class DietLabelLocal {
    private int fkRecipe;
    private String label;

    public DietLabelLocal(int fkRecipe, String label) {
        this.fkRecipe = fkRecipe;
        this.label = label;
    }

    public int getFkRecipe() {
        return fkRecipe;
    }

    public String getLabel() {
        return label;
    }
}
