package com.tae.letscook.Utils;

import android.support.annotation.NonNull;

import com.tae.letscook.api.apiModel.Digest;
import com.tae.letscook.api.apiModel.Hit;
import com.tae.letscook.api.apiModel.Recipe;
import com.tae.letscook.model.ItemRecipe;
import com.tae.letscook.model.NutrientLocal;
import com.tae.letscook.model.RecipeLocal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 16/01/2016.
 */
public class ModelConverter {

    public static ArrayList<ItemRecipe> convertLocalRecipeToItemRecipe(List<RecipeLocal>recipes) {
        ArrayList<ItemRecipe> itemRecipes = new ArrayList<>(recipes.size());
        for (RecipeLocal recipe : recipes) {
            itemRecipes.add(new ItemRecipe(recipe.getLabel(),recipe.getImageUrl()));
        }
        return itemRecipes;
    }

    @NonNull
    public static ArrayList<RecipeLocal> convertApiModelToLocalRecipes(List<Hit> hits) {
        ArrayList<Recipe> recipes = new ArrayList<>(hits.size());
        for (Hit hit : hits) {
            recipes.add(hit.getRecipe());
        }
        return convertRecipeApiToLocalRecipe(recipes);
    }

    public static ArrayList<RecipeLocal> convertRecipeApiToLocalRecipe(List<Recipe> recipes) {
        List<RecipeLocal> recipeLocals = new ArrayList<>(recipes.size());
        for (Recipe recipe : recipes) {
            List<NutrientLocal> nutrientLocals = new ArrayList<NutrientLocal>(recipe.getDigest().size());
            for (Digest digest : recipe.getDigest()) {
                nutrientLocals.add(new NutrientLocal(
                        digest.getLabel(),
                        digest.getTotal(),
                        digest.getUnit()));
            }
            recipeLocals.add(new RecipeLocal(
                    recipe.getLabel(),
                    recipe.getImage(),
                    recipe.getCalories(),
                    recipe.getIngredientLines(),
                    nutrientLocals,
                    recipe.getDietLabels()
            ));

        }
        return (ArrayList<RecipeLocal>) recipeLocals;
    }
}
