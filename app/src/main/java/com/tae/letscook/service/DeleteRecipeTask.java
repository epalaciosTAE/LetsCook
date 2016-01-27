package com.tae.letscook.service;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tae.letscook.Utils.ToastUtils;
import com.tae.letscook.persistance.DaoDiet;
import com.tae.letscook.persistance.DaoFavourites;
import com.tae.letscook.persistance.DaoIngredients;
import com.tae.letscook.persistance.DaoNutrients;

/**
 * Created by Eduardo on 21/01/2016.
 */
public class DeleteRecipeTask extends AsyncTask<Long, Void, Boolean> {

    private static final String TAG = "DeleteRecipeTask";
    private Context context;

    public DeleteRecipeTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Long... params) {
        int rows = DaoFavourites.getInstance(context).delete(params[0]);
        Log.i(TAG, "onPostExecute: rows deleted in recipe: " + rows);
        int dietRows = DaoDiet.getInstance(context).delete(params[0]);
        Log.i(TAG, "onPostExecute: rows deleted in diet: " + dietRows);
        int nutrientRows = DaoNutrients.getInstance(context).delete(params[0]);
        Log.i(TAG, "onPostExecute: rows deleted in nutrients: " + nutrientRows);
        int ingredientRows = DaoIngredients.getInstance(context).delete(params[0]);
        Log.i(TAG, "onPostExecute: rows deleted in ingredients: " + ingredientRows);
        return rows > 0;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) { // TODO delete this.
        super.onPostExecute(aBoolean);
        if (aBoolean) {
            ToastUtils.showToast(context, "Recipe deleted");
        }
    }
}
