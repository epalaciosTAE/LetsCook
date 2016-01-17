package com.tae.letscook.Utils;

import android.support.annotation.NonNull;

import com.tae.letscook.api.apiModel.Digest;
import com.tae.letscook.api.apiModel.Hit;
import com.tae.letscook.model.ItemRecipe;
import com.tae.letscook.model.NutrientLocal;
import com.tae.letscook.model.RecipeLocal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 16/01/2016.
 */
public class ModelConverter {

    public static List<ItemRecipe> convertLocalRecipeToItemRecipe(List<RecipeLocal>recipes) {
        List<ItemRecipe> itemRecipes = new ArrayList<>(recipes.size());
        for (RecipeLocal recipe : recipes) {
            itemRecipes.add(new ItemRecipe(recipe.getLabel(),recipe.getImageUrl()));
        }
        return itemRecipes;
    }

    @NonNull
    public static ArrayList<RecipeLocal> convertApiRecipesToLocalRecipes(List<Hit> hits) {
        ArrayList<RecipeLocal> recipes = new ArrayList<RecipeLocal>(hits.size());
        for (Hit hit : hits) {
            List<NutrientLocal> nutrientLocals =new ArrayList<NutrientLocal>(hit.getRecipe().getDigest().size());
            for (Digest digest : hit.getRecipe().getDigest()) {
                nutrientLocals.add(new NutrientLocal(
                        digest.getLabel(),
                        digest.getTotal(),
                        digest.getUnit()));
            }
            recipes.add(new RecipeLocal(
                    hit.getRecipe().getLabel(),
                    hit.getRecipe().getImage(),
                    hit.getRecipe().getCalories(),
                    hit.getRecipe().getIngredientLines(),
                    nutrientLocals,
                    hit.getRecipe().getDietLabels()
            ));

        }
        return recipes;
    }
}
