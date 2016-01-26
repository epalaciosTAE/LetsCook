package com.tae.letscook.api;

import android.telecom.Call;

import com.tae.letscook.api.apiGeocoding.Geocoding;
import com.tae.letscook.api.apiModel.Hit;
import com.tae.letscook.api.apiModel.Recipe;
import com.tae.letscook.constants.ServerConstants;
import com.tae.letscook.model.Chef;
import com.tae.letscook.model.Event;
import com.tae.letscook.model.geocoding.GeocodingLatLng;
import com.tae.letscook.model.google.GoogleUser;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Eduardo on 15/01/2016.
 */
public interface ILetsCookServer {

    @GET(ServerConstants.RECIPES_BY_CATEGORY_ENDPOINT)
    public void getRecipesByCategory(@Query("category") String category, Callback<List<Hit>> response);

    @GET(ServerConstants.RECIPES_RANDOM_ENDPOINT)
    public void getRandomRecipes(Callback<List<Recipe>> response);

    @GET(ServerConstants.GEOCODING)
    public void getGeoCode(@Query("geocoding") String query, Callback<Geocoding> response);

    @POST(ServerConstants.SIGN_IN)
    @FormUrlEncoded
    public void authorizeUser(@Field("authcode") String authCode, Callback<GoogleUser> response);

    @POST(ServerConstants.CHEF)
    public void saveChefInServer(@Body Chef chef, Callback<Chef> response);

    @GET(ServerConstants.CHEF)
    public void getChef(Callback<Chef> response);

    @POST(ServerConstants.EVENT)
    public void saveEventInServer(@Path(value = "id") String uuid, @Body Event event, Callback<Event> response);

    @GET(ServerConstants.EVENTS)
    public void getEvents(Callback<List<Event>> response);

//    @GET(ServerConstants.CHEFS)
//    public void getChefs(Callback<List<Chef>> response);

}

