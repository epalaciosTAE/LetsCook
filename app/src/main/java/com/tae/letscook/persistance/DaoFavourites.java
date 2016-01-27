package com.tae.letscook.persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tae.letscook.app.LetsCookApp;
import com.tae.letscook.constants.ConstantsContract;
import com.tae.letscook.model.RecipeLocal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 17/01/2016.
 */
public class DaoFavourites  {

    private static final String TAG = DaoFavourites.class.getSimpleName();
    private static DaoFavourites instance;
    private LetsCookHelper letsCookHelper;
    private Context context;

    private DaoFavourites(Context context) {
        openDataBase(context);
    }

    public static DaoFavourites getInstance(Context context) {
        if (instance == null) {
            instance = new DaoFavourites(context);
        }
        return instance;
    }

    private void openDataBase(Context context) {
        letsCookHelper = new LetsCookHelper(context);
    }



    public long insert(RecipeLocal recipe) {
        SQLiteDatabase db = letsCookHelper.getWritableDatabase();
        ContentValues cv = getContentValues(recipe);
        long rowId = 0;
        try {
            rowId = db.insert(FavouritesContract.FavouritesEntry.TABLE_RECIPES, FavouritesContract.FavouritesEntry.COLUMN_LABEL, cv);
        } catch (SQLiteException e) {
            Log.e(TAG, "insert: Error inserting recipe", e);
            LetsCookApp.getInstance().trackException(e);
        } finally {
            db.close();
        }
        return rowId;
    }

    public int delete(long recipeId) {
        SQLiteDatabase db = letsCookHelper.getWritableDatabase();
        int rows = 0;
        try {
            rows = db.delete(
                    FavouritesContract.FavouritesEntry.TABLE_RECIPES,
                    FavouritesContract.FavouritesEntry._ID + "=?",
                    new String[]{String.valueOf(recipeId)});
        } catch (SQLiteException e) {
            Log.e(TAG, "delete: Error deleting label: " + recipeId , e );
            LetsCookApp.getInstance().trackException(e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return rows;
    }

    @NonNull
    private ContentValues getContentValues(RecipeLocal recipe) {
        ContentValues cv = new ContentValues();
        cv.put(FavouritesContract.FavouritesEntry.COLUMN_LABEL, recipe.getLabel());
        cv.put(FavouritesContract.FavouritesEntry.COLUMN_IMAGE_URL, recipe.getImageUrl());
        cv.put(FavouritesContract.FavouritesEntry.COLUMN_CALORIES, recipe.getCalories());
        return cv;
    }

    public List<RecipeLocal> getRecipes() {
        SQLiteDatabase db = letsCookHelper.getReadableDatabase();
        String[] columns = {
                FavouritesContract.FavouritesEntry._ID,
                FavouritesContract.FavouritesEntry.COLUMN_LABEL,
                FavouritesContract.FavouritesEntry.COLUMN_IMAGE_URL,
                FavouritesContract.FavouritesEntry.COLUMN_CALORIES
        };
        String sortOrder =
                FavouritesContract.FavouritesEntry._ID + ConstantsContract.ASCENDANT;
        List<RecipeLocal> recipes = null;
        try {
//            Cursor cursor = db.rawQuery("" +
//                    "SELECT *" +
//                    "FROM " + FavouritesContract.FavouritesEntry.TABLE_RECIPES + ConstantsContract.COMA +
//                    DietContract.DietEntry.TABLE_DIET + ConstantsContract.COMA +
//                    IngredientsContract.IngredientEntry.TABLE_INGREDIENTS + ConstantsContract.COMA +
//                    NutrientsContract.NutrientEntry.TABLE_NUTRIENTS
//                    "WHERE"
//                    , new String[] {"label", "image_url", "calories"});
////
            Cursor cursor = db.query(
                    FavouritesContract.FavouritesEntry.TABLE_RECIPES,
                    columns,
                    null,
                    null,
                    null,
                    null,
                    sortOrder);
            recipes = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(FavouritesContract.FavouritesEntry._ID));
                String label = cursor.getString(cursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_LABEL));
                String imageUrl = cursor.getString(cursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_IMAGE_URL));
                double calories = cursor.getDouble(cursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_CALORIES));
                recipes.add(new RecipeLocal(id, label, imageUrl, calories));
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(TAG, "getRecipes: Error reading table Recipes",e );
            LetsCookApp.getInstance().trackException(e);
        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }
        return recipes;
    }
}
