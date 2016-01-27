package com.tae.letscook.listeners;

import com.tae.letscook.model.ItemRecipe;
import com.tae.letscook.model.RecipeLocal;

import java.util.List;

/**
 * Created by Eduardo on 24/01/2016.
 */
public interface OnRecipesLoadedListener {

    public void recipesLoaded(List<RecipeLocal> recipes);
}
