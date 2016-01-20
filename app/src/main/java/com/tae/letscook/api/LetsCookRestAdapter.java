package com.tae.letscook.api;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.tae.letscook.Utils.ModelConverter;
import com.tae.letscook.Utils.ToastUtils;
import com.tae.letscook.api.apiModel.Hit;
import com.tae.letscook.api.apiModel.Recipe;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.constants.ServerConstants;
import com.tae.letscook.constants.ActionConstants;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
                handleFailure(error);
//                LocalBroadcastManager.getInstance(context)
//                        .sendBroadcast(new Intent(ActionConstants.ACTION_DOWNLOAD_RECIPES_BY_CATEGORY_SUCCESS));
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
                handleFailure(error);
                LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(new Intent(ActionConstants.ACTION_DOWNLOAD_RECIPES_RANDOM_SUCCESS));
            }
        });
    }

    private void handleFailure(RetrofitError error) {
        if (error.getKind().equals(RetrofitError.Kind.HTTP)) {
            ToastUtils.showToastErrorInRetrofit(context, "Http error: ", error);
        } else if (error.getKind().equals(RetrofitError.Kind.NETWORK)) {
            ToastUtils.showToastErrorInRetrofit(context, "Network error: ", error);
        } else if (error.getKind().equals(RetrofitError.Kind.CONVERSION)) {
            ToastUtils.showToastErrorInRetrofit(context, "Conversion error: ", error);
        } else {
            ToastUtils.showToastErrorInRetrofit(context, "Unknown error: ", error);
        }
    }

}
