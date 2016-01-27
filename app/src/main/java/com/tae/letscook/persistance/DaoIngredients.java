package com.tae.letscook.persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.tae.letscook.app.LetsCookApp;
import com.tae.letscook.constants.ConstantsContract;
import com.tae.letscook.model.IngredientLocal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 17/01/2016.
 */
public class DaoIngredients {

    private static final String TAG = DaoIngredients.class.getSimpleName();
    private static DaoIngredients  instance;
    private LetsCookHelper letsCookHelper;

    private DaoIngredients (Context context) {
        openDataBase(context);
    }

    public static DaoIngredients  getInstance(Context context) {
        if (instance == null) {
            instance = new DaoIngredients(context);
        }
        return instance;
    }

    public void openDataBase(Context context) {
        letsCookHelper = new LetsCookHelper(context);
    }

    public boolean insert(List<String> ingredientLines, long recipeId) {
        SQLiteDatabase db = letsCookHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long rowId = 0;
        try {
            for (String ingredient : ingredientLines) {
                cv.put(IngredientsContract.IngredientEntry.COLUMN_INGREDIENT, ingredient);
                cv.put(IngredientsContract.IngredientEntry.COLUMN_FK_INGREDIENTS, recipeId);
                rowId = db.insert(IngredientsContract.IngredientEntry.TABLE_INGREDIENTS, IngredientsContract.IngredientEntry.COLUMN_FK_INGREDIENTS, cv);

            }
        } catch (SQLiteException e) {
            Log.e(TAG, "insert: Error inserting ingredients", e);
            LetsCookApp.getInstance().trackException(e);
        } finally {
            db.close();
        }
        return rowId > -1;
    }

    public int delete(long recipeId) {
        Log.i(TAG, "delete: recipe id: " + recipeId);
        SQLiteDatabase db = letsCookHelper.getWritableDatabase();
        int rows = 0;
        try {
            rows = db.delete(
                    IngredientsContract.IngredientEntry.TABLE_INGREDIENTS,
                    IngredientsContract.IngredientEntry.COLUMN_FK_INGREDIENTS + "=?",
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


    public List<IngredientLocal> getIngredients() {
        SQLiteDatabase db = letsCookHelper.getReadableDatabase();
        String[] columns = {
                IngredientsContract.IngredientEntry.COLUMN_INGREDIENT,
                IngredientsContract.IngredientEntry.COLUMN_FK_INGREDIENTS
        };
        String sortOrder =
                IngredientsContract.IngredientEntry._ID + ConstantsContract.ASCENDANT;
        List<IngredientLocal> ingredients = null;
        try {
            Cursor cursor = db.query(
                    IngredientsContract.IngredientEntry.TABLE_INGREDIENTS,
                    columns,
                    null,
                    null,
                    null,
                    null,
                    sortOrder);
            ingredients = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
                String ingredient = cursor.getString(cursor.getColumnIndexOrThrow(IngredientsContract.IngredientEntry.COLUMN_INGREDIENT));
                int fkRecipe = cursor.getInt(cursor.getColumnIndexOrThrow(IngredientsContract.IngredientEntry.COLUMN_FK_INGREDIENTS));
                ingredients.add(new IngredientLocal(fkRecipe, ingredient));
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(TAG, "getRecipes: Error reading table Ingredients",e );
            LetsCookApp.getInstance().trackException(e);
        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }
        return ingredients;
    }
}
