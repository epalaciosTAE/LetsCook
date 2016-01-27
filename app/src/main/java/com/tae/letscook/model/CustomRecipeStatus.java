package com.tae.letscook.model;

/**
 * Created by Eduardo on 26/01/2016.
 */
public class CustomRecipeStatus {

    public enum RecipeState {
        READY,
        PROCESSING
    }


    private RecipeState state;

    public CustomRecipeStatus(RecipeState state) {
        super();
        this.state = state;
    }

    public RecipeState getState() {
        return state;
    }

    public void setState(RecipeState state) {
        this.state = state;
    }
}
