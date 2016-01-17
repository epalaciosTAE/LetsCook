package com.tae.letscook.api;

import com.tae.letscook.api.apiModel.Hit;
import com.tae.letscook.api.apiModel.Recipe;
import com.tae.letscook.constants.ServerConstants;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Eduardo on 15/01/2016.
 */
public interface ILetsCookServer {

    @GET(ServerConstants.RECIPES_BY_CATEGORY_ENDPOINT)
    public void getRecipesByCategory(@Query("category") String category, Callback<List<Hit>> response);

    @GET(ServerConstants.RECIPES_RANDOM_ENDPOINT)
    public void getRandomRecipes(Callback<List<Recipe>> response);
}

