package com.tae.letscook.persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.tae.letscook.app.LetsCookApp;
import com.tae.letscook.constants.ConstantsContract;
import com.tae.letscook.model.DietLabelLocal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 17/01/2016.
 */
public class DaoDiet {

    private static final String TAG = DaoFavourites.class.getSimpleName();
    private static DaoDiet instance;
    private LetsCookHelper letsCookHelper;

    private DaoDiet(Context context) {
        openDataBase(context);
    }

    public static DaoDiet getInstance(Context context) {
        if (instance == null) {
            instance = new DaoDiet(context);
        }
        return instance;
    }

    public void openDataBase(Context context) {
        letsCookHelper = new LetsCookHelper(context);
    }

    public boolean insert(List<String> dietLabels, long recipeId) {
        SQLiteDatabase db = letsCookHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long rowId = 0;
        try {
            for (String label : dietLabels) {
                cv.put(DietContract.DietEntry.COLUMN_DIET, label);
                cv.put(DietContract.DietEntry.COLUMN_FK_DIET, recipeId);
                rowId = db.insert(DietContract.DietEntry.TABLE_DIET, DietContract.DietEntry.COLUMN_FK_DIET, cv);
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "insert: Error inserting diet label", e);
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
                    DietContract.DietEntry.TABLE_DIET,
                    DietContract.DietEntry.COLUMN_FK_DIET+ "=?",
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

    public List<DietLabelLocal> getRecipes() {
        SQLiteDatabase db = letsCookHelper.getReadableDatabase();
        String[] columns = {
                DietContract.DietEntry.COLUMN_FK_DIET,
                DietContract.DietEntry.COLUMN_DIET
        };
        String sortOrder =
                DietContract.DietEntry._ID + ConstantsContract.ASCENDANT;
        List<DietLabelLocal> dietLabelLocals = null;
        try {
            Cursor cursor = db.query(
                    DietContract.DietEntry.TABLE_DIET,
                    columns,
                    null,
                    null,
                    null,
                    null,
                    sortOrder);
            dietLabelLocals = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
                int fkRecipe = cursor.getInt(cursor.getColumnIndex(DietContract.DietEntry.COLUMN_FK_DIET));
                String label = cursor.getString(cursor.getColumnIndex(DietContract.DietEntry.COLUMN_DIET));
                dietLabelLocals.add(new DietLabelLocal(fkRecipe, label));
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(TAG, "getRecipes: Error reading table Diet label",e );
            LetsCookApp.getInstance().trackException(e);
        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }
        return dietLabelLocals;
    }
}
