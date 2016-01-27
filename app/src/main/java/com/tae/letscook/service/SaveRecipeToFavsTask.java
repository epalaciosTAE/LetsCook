package com.tae.letscook.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tae.letscook.Utils.ToastUtils;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.listeners.OnTaskResponse;
import com.tae.letscook.manager.DaoManager;
import com.tae.letscook.model.RecipeLocal;
import com.tae.letscook.persistance.DaoDiet;
import com.tae.letscook.persistance.DaoFavourites;
import com.tae.letscook.persistance.DaoIngredients;
import com.tae.letscook.persistance.DaoNutrients;

/**
 * Created by Eduardo on 20/01/2016.
 */
public class SaveRecipeToFavsTask extends AsyncTask<RecipeLocal, Void, Long> {

    private static final String TAG = SaveRecipeToFavsTask.class.getSimpleName();
    private Context context;
    private OnTaskResponse taskResponse;

    public SaveRecipeToFavsTask(Context context, OnTaskResponse taskResponse) {
        this.context = context;
        this.taskResponse = taskResponse;
    }


    @Override
    protected Long doInBackground(RecipeLocal... recipes) {
        return DaoManager.getInstance(context).insert(recipes[0]);
    }

    private boolean successInsert(long recipe) {
        return recipe > -1;
    }

    @Override
    protected void onPostExecute(Long recipeId) {
        super.onPostExecute(recipeId);
        if (successInsert(recipeId)) {
            ToastUtils.showToast(context, "Recipe saved");
            Log.i(TAG, "onPostExecute: row id: " + recipeId) ;
            taskResponse.onResponse(recipeId);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
//        taskResponse = null;
    }
}
