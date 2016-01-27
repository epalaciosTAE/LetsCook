package com.tae.letscook.manager;

import android.content.Context;

import com.tae.letscook.Utils.ModelConverter;
import com.tae.letscook.model.ItemRecipe;
import com.tae.letscook.model.RecipeLocal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 23/01/2016.
 */
public class RecipesManager {

    private static RecipesManager instance;
    private Context context;

    private RecipesManager (Context context) {
        this.context = context;
    }

    public static RecipesManager getInstance(Context context) {
        if (instance == null) {
            instance = new RecipesManager(context);
        }
        return instance;
    }

    public List<RecipeLocal> getRecipesFromSQLite () {
        return DaoManager.getInstance(context).getItemRecipes();
    }

    public List<ItemRecipe> getRecipesForList () {
        return ModelConverter.convertLocalRecipeToItemRecipe(DaoManager.getInstance(context).getItemRecipes());
    }
}
