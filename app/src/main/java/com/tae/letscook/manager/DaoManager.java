package com.tae.letscook.manager;

import android.content.Context;
import android.util.Log;

import com.tae.letscook.constants.Constants;
import com.tae.letscook.model.DietLabelLocal;
import com.tae.letscook.model.IngredientLocal;
import com.tae.letscook.model.ItemRecipe;
import com.tae.letscook.model.NutrientLocal;
import com.tae.letscook.model.RecipeLocal;
import com.tae.letscook.persistance.DaoDiet;
import com.tae.letscook.persistance.DaoFavourites;
import com.tae.letscook.persistance.DaoIngredients;
import com.tae.letscook.persistance.DaoNutrients;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 23/01/2016.
 */
public class DaoManager {

    private static final String TAG = DaoManager.class.getSimpleName();
    private static DaoManager  instance;
    private Context context;

    private DaoManager (Context context) {
        this.context = context;
    }

    public static DaoManager getInstance(Context context) {
        if (instance == null) {
            instance = new DaoManager (context);
        }
        return instance;
    }

    public Long insert(RecipeLocal recipe) {
        Log.i(TAG, "doInBackground: saving recipe");
        long rowId = DaoFavourites.getInstance(context).insert(recipe);
        if (successInsert(rowId)) {
            Log.i(TAG, "doInBackground: diet");
            DaoDiet.getInstance(context).insert(recipe.getDietLabels(), rowId);
            Log.i(TAG, "doInBackground: ingredients");
            DaoIngredients.getInstance(context).insert(recipe.getIngredientLines(), rowId);
            Log.i(TAG, "doInBackground: nutrients");
            DaoNutrients.getInstance(context).insert(recipe.getNutrients(), rowId);
        }
        return rowId;
    }

    private boolean successInsert(long recipe) {
        return recipe > -1;
    }

    public List<RecipeLocal> getItemRecipes () {
        return createRecipeLocal(
                DaoFavourites.getInstance(context).getRecipes(),
                DaoIngredients.getInstance(context).getIngredients(),
                DaoNutrients.getInstance(context).getNutrients(),
                DaoDiet.getInstance(context).getRecipes());

    }

    private List<RecipeLocal> createRecipeLocal(List<RecipeLocal> recipes,
                                                List<IngredientLocal> ingredients,
                                                List<NutrientLocal> nutrients,
                                                List<DietLabelLocal> diets) {
        for (RecipeLocal recipe : recipes) {
            List<String> tempIngredients = new ArrayList<>(ingredients.size());
            for (IngredientLocal ingredient : ingredients) {
                if (recipe.getId() == ingredient.getFkRecipe()) {
                    tempIngredients.add(ingredient.getIngredient());
                }
            }
            List<String> tempDiets = new ArrayList<>(diets.size());
            for (DietLabelLocal diet : diets) {
                if (recipe.getId() == diet.getFkRecipe()) {
                    tempDiets.add(diet.getLabel());
                }
            }
            List<NutrientLocal> temNutrients = new ArrayList<>();
            for (NutrientLocal nutrient : nutrients) {
                if (recipe.getId() == nutrient.getFkRecipe()) {
                    temNutrients.add(nutrient);
                }
            }
            recipe.setNutrients(temNutrients);
            recipe.setIngredientLines(tempIngredients);
            recipe.setDietLabels(tempDiets);
        }
        return recipes;
    }
}
