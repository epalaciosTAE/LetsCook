package com.tae.letscook.persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.tae.letscook.app.LetsCookApp;
import com.tae.letscook.constants.ConstantsContract;
import com.tae.letscook.model.NutrientLocal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 17/01/2016.
 */
public class DaoNutrients {

    private static final String TAG = DaoNutrients.class.getSimpleName();
    private static DaoNutrients  instance;
    private LetsCookHelper letsCookHelper;

    private DaoNutrients (Context context) {
        openDataBase(context);
    }

    public static DaoNutrients  getInstance(Context context) {
        if (instance == null) {
            instance = new DaoNutrients(context);
        }
        return instance;
    }

    public void openDataBase(Context context) {
        letsCookHelper = new LetsCookHelper(context);
    }

    public boolean insert(List<NutrientLocal> nutrientLocals, long recipeId) {
        SQLiteDatabase db = letsCookHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long rowId = 0;
        try {
            for (NutrientLocal nutrient : nutrientLocals) {
                cv.put(NutrientsContract.NutrientEntry.COLUMN_NUTRIENT, nutrient.getLabel());
                cv.put(NutrientsContract.NutrientEntry.COLUMN_AMOUNT, nutrient.getTotal());
                cv.put(NutrientsContract.NutrientEntry.COLUMN_UNIT, nutrient.getUnit());
                cv.put(NutrientsContract.NutrientEntry.COLUMN_FK_NUTRIENTS, recipeId);
                rowId = db.insert(NutrientsContract.NutrientEntry.TABLE_NUTRIENTS, NutrientsContract.NutrientEntry.COLUMN_FK_NUTRIENTS, cv);
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "insert: Error inserting nutrients", e);
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
                    NutrientsContract.NutrientEntry.TABLE_NUTRIENTS,
                    NutrientsContract.NutrientEntry.COLUMN_FK_NUTRIENTS + "=?",
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

    public List<NutrientLocal> getNutrients() {
        SQLiteDatabase db = letsCookHelper.getReadableDatabase();
        String[] columns = {
                NutrientsContract.NutrientEntry.COLUMN_FK_NUTRIENTS,
                NutrientsContract.NutrientEntry.COLUMN_NUTRIENT,
                NutrientsContract.NutrientEntry.COLUMN_AMOUNT,
                NutrientsContract.NutrientEntry.COLUMN_UNIT
        };
        String sortOrder =
                NutrientsContract.NutrientEntry._ID + ConstantsContract.ASCENDANT;
        List<NutrientLocal> nutrients = null;
        try {
            Cursor cursor = db.query(
                    NutrientsContract.NutrientEntry.TABLE_NUTRIENTS,
                    columns,
                    null,
                    null,
                    null,
                    null,
                    sortOrder);
            nutrients = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
                int fkRecipe = cursor.getInt(cursor.getColumnIndex(NutrientsContract.NutrientEntry.COLUMN_FK_NUTRIENTS));
                String nutrient = cursor.getString(cursor.getColumnIndex(NutrientsContract.NutrientEntry.COLUMN_NUTRIENT));
                double amount = cursor.getDouble(cursor.getColumnIndex(NutrientsContract.NutrientEntry.COLUMN_AMOUNT));
                String unit = cursor.getString(cursor.getColumnIndex(NutrientsContract.NutrientEntry.COLUMN_UNIT));
                nutrients.add(new NutrientLocal(fkRecipe, nutrient, amount, unit));
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(TAG, "getRecipes: Error reading table Nutrients",e );
            LetsCookApp.getInstance().trackException(e);
        } finally {

            if (db.isOpen()) {
                db.close();
            }
        }
        return nutrients;
    }
}
