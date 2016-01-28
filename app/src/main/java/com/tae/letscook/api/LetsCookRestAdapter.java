package com.tae.letscook.api;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.tae.letscook.Utils.ModelConverter;
import com.tae.letscook.Utils.NetworkUtils;
import com.tae.letscook.api.apiGeocoding.Geocoding;
import com.tae.letscook.api.apiModel.Hit;
import com.tae.letscook.api.apiModel.Recipe;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.constants.ServerConstants;
import com.tae.letscook.constants.ActionConstants;
import com.tae.letscook.model.Chef;
import com.tae.letscook.model.CustomRecipe;
import com.tae.letscook.model.CustomRecipeStatus;
import com.tae.letscook.model.Event;
import com.tae.letscook.model.google.GoogleUser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by Eduardo on 15/01/2016.
 */
public class LetsCookRestAdapter {

    private static final String TAG = LetsCookRestAdapter.class.getSimpleName();
    private ILetsCookServer iLetsCookServer;
    private Context context;

    /**
     * RestAdapter constructor
     * @param context
     */
    public LetsCookRestAdapter(Context context) {
        this.context = context;
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ServerConstants.LETS_COOK_SERVER_BASE_URL)
                .build();
        iLetsCookServer = restAdapter.create(ILetsCookServer.class);
    }

    /**
     * Get recipes by category
     * @param category
     */
    public void getRecipesByCategory(String category) {
        iLetsCookServer.getRecipesByCategory(category, new Callback<List<Hit>>() {
            @Override
            public void success(List<Hit> hits, Response response) {
                Log.i(TAG, "getRecipesByCategory success: response " + response.getStatus());
                LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(new Intent(ActionConstants.ACTION_DOWNLOAD_RECIPES_BY_CATEGORY_SUCCESS)
                                .putParcelableArrayListExtra(Constants.EXTRA_RECIPES,
                                        ModelConverter.convertApiModelToLocalRecipes(hits)));
            }

            @Override
            public void failure(RetrofitError error) {
                NetworkUtils.handleRestAdapterFailure(context, error);
            }
        });
    }


    /**
     * Get random recipes
     */
    public void getRecipesRandom() {
        iLetsCookServer.getRandomRecipes(new Callback<List<Recipe>>() {
            @Override
            public void success(List<Recipe> recipes, Response response) {
                Log.i(TAG, "getRandomRecipes success: " + response.getStatus());
                LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(new Intent(ActionConstants.ACTION_DOWNLOAD_RECIPES_RANDOM_SUCCESS)
                                .putParcelableArrayListExtra(Constants.EXTRA_RECIPES_RANDOM,
                                        ModelConverter.convertRecipeApiToLocalRecipe(recipes)));
            }

            @Override
            public void failure(RetrofitError error) {
                NetworkUtils.handleRestAdapterFailure(context, error);
            }
        });
    }

    /**
     * Send authCode to Server side and authorize the user
     */
    public void signIn(String authCode) {
        Log.i(TAG, "signIn: authCode: " + authCode);
        iLetsCookServer.authorizeUser(authCode, new Callback<GoogleUser>() {
            @Override
            public void success(GoogleUser googleUser, Response response) {
                Log.i(TAG, "signIn: response " + response.getStatus());
                LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(new Intent(ActionConstants.ACTION_SIGN_IN_SUCCESS)
                                .putExtra(Constants.EXTRA_CHEF, new Chef(
                                        googleUser.getName(),
                                        googleUser.getEmail(),
                                        googleUser.getPictureUrl())));
            }

            @Override
            public void failure(RetrofitError error) {
                NetworkUtils.handleRestAdapterFailure(context, error);
            }
        });
    }


    public void getGeoCode(String query) {
        iLetsCookServer.getGeoCode(query, new Callback<Geocoding>() {
            @Override
            public void success(Geocoding geocoding, Response response) {
                Log.i(TAG, "getGeoCode: " + response.getStatus());
                LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(new Intent(ActionConstants.ACTION_DOWNLOAD_GEOCODING_SUCCESS)
                                .putParcelableArrayListExtra(Constants.EXTRA_GEOCODING,
                                        (ArrayList<? extends Parcelable>) ModelConverter.convertGeocodingApiToLocalGeocoding(geocoding)));
            }

            @Override
            public void failure(RetrofitError error) {
                NetworkUtils.handleRestAdapterFailure(context, error);
            }
        });
    }

    public void postChef(Chef chef) {
        iLetsCookServer.saveChefInServer(chef, new Callback<Chef>() {
            @Override
            public void success(Chef chef, Response response) {
                Log.i(TAG, "postChef: response" + response.getStatus());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i(TAG, "postChef: FAIL" + error.getMessage());
                NetworkUtils.handleRestAdapterFailure(context, error);
            }
        });
    }

    public void postEvent(String uuid, Event event) {
        iLetsCookServer.saveEventInServer(uuid, event, new Callback<Event>() {
            @Override
            public void success(Event event, Response response) {
                Log.i(TAG, "postEvent: response" + response.getStatus());
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(ActionConstants.ACTION_UPLOAD_EVENT_SUCCESS)
                        .putExtra(Constants.EXTRA_EVENT, event));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i(TAG, "postEvent: FAIL" + error.getMessage());
                NetworkUtils.handleRestAdapterFailure(context, error);
            }
        });
    }

    public void getEvents () {
        iLetsCookServer.getEvents(new Callback<List<Event>>() {
            @Override
            public void success(List<Event> events, Response response) {
                Log.i(TAG, "get events success: " + response.getStatus());
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(ActionConstants.ACTION_DOWNLOAD_EVENTS_SUCCESS)
                        .putParcelableArrayListExtra(Constants.EXTRA_EVENTS, (ArrayList<? extends Parcelable>) events));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i(TAG, "get Events: FAIL" + error.getMessage());
                NetworkUtils.handleRestAdapterFailure(context, error);
            }
        });
    }

    public void postCustomRecipe(CustomRecipe customRecipe) { // this method update the list after post a recipe
        iLetsCookServer.postCustomRecipe(customRecipe.getChefId(), customRecipe, new Callback<CustomRecipe>() {
            @Override
            public void success(final CustomRecipe customRecipe, Response response) {
                Log.i(TAG, "post custom recipe success: " + response.getStatus());
                if (customRecipe.getImagePath() != null) {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {

                            File imageFile = new File(customRecipe.getImagePath());
                            CustomRecipeStatus recipeStatus = iLetsCookServer.setCustomRecipeData(
                                    customRecipe.getId(), new TypedFile("image/*", imageFile));

                            if (recipeStatus.getState().equals(CustomRecipeStatus.RecipeState.READY)) {
                                LocalBroadcastManager.getInstance(context)
                                        .sendBroadcast(new Intent(ActionConstants.ACTION_UPLOAD_CUSTOM_RECIPE_SUCCESS)
                                                .putExtra(Constants.EXTRA_CUSTOM_RECIPE, customRecipe));
                            }
                            return null;
                        }
                    }.execute();

                } else {
                    LocalBroadcastManager.getInstance(context)
                            .sendBroadcast(new Intent(ActionConstants.ACTION_UPLOAD_CUSTOM_RECIPE_SUCCESS)
                                    .putExtra(Constants.EXTRA_CUSTOM_RECIPE, customRecipe));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i(TAG, "post custom recipe: FAIL " + error.getMessage());
                NetworkUtils.handleRestAdapterFailure(context, error);
            }
        });

    }

    public void getCustomRecipes() {
        iLetsCookServer.getCustomRecipes(new Callback<List<CustomRecipe>>() {
            @Override
            public void success(List<CustomRecipe> customRecipes, Response response) {
                Log.i(TAG, "get custom recipes: Succes " + response.getStatus());
                LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(new Intent(ActionConstants.ACTION_DOWNLOAD_CUSTOM_RECIPE_SUCCESS)
                                .putParcelableArrayListExtra(Constants.EXTRA_CUSTOM_RECIPES, (ArrayList<? extends Parcelable>) customRecipes));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i(TAG, "get custom recipes: FAIL " + error.getMessage());
                NetworkUtils.handleRestAdapterFailure(context, error);
            }
        });
    }

    public Response getCustomRecipeImage(long id) {
        return  iLetsCookServer.getCustomRecipeImage(id);
    }
}
