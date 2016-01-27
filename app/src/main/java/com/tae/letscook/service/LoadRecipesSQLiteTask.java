package com.tae.letscook.service;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;

import com.tae.letscook.constants.ActionConstants;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.listeners.OnRecipesLoadedListener;
import com.tae.letscook.manager.RecipesManager;
import com.tae.letscook.model.ItemRecipe;
import com.tae.letscook.model.RecipeLocal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 24/01/2016.
 */
public class LoadRecipesSQLiteTask extends AsyncTask <Void, Void, List<RecipeLocal>>{

    private Context context;

    public LoadRecipesSQLiteTask( Context context) {
        this.context = context;
    }

    @Override
    protected List<RecipeLocal> doInBackground(Void... params) {
        return RecipesManager.getInstance(context).getRecipesFromSQLite();
    }

    @Override
    protected void onPostExecute(List<RecipeLocal> recipes) {
        super.onPostExecute(recipes);
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(ActionConstants.ACTION_UPDATE_SQLITE_RECIPES)
                .putParcelableArrayListExtra(Constants.EXTRA_SQLITE_RECIPES, (ArrayList<? extends Parcelable>) recipes));
    }
}
