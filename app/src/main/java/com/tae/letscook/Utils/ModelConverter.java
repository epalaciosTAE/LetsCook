package com.tae.letscook.Utils;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tae.letscook.api.apiGeocoding.Geocoding;
import com.tae.letscook.api.apiGeocoding.Result;
import com.tae.letscook.api.apiModel.Digest;
import com.tae.letscook.api.apiModel.Hit;
import com.tae.letscook.api.apiModel.Recipe;
import com.tae.letscook.model.ItemRecipe;
import com.tae.letscook.model.NutrientLocal;
import com.tae.letscook.model.RecipeLocal;
import com.tae.letscook.model.facebook.FacebookUser;
import com.tae.letscook.model.geocoding.GeocodingLatLng;

import org.json.JSONObject;

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

    public static FacebookUser convertFacebookModelToChef(JSONObject object) {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(object.toString());
        Gson gson = new Gson();
        return gson.fromJson(element, FacebookUser.class);
    }

    public static List<GeocodingLatLng> convertGeocodingApiToLocalGeocoding(Geocoding geocoding) {
        List<GeocodingLatLng> locations = new ArrayList<>(geocoding.getResults().size());
        for (Result result : geocoding.getResults()) {
            locations.add(new GeocodingLatLng(result.getGeometry().getLocation().getLat(), result.getGeometry().getLocation().getLng()));
        }
        return locations;
    }
}
