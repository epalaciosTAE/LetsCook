package com.tae.letscook.persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.tae.letscook.app.LetsCookApp;
import com.tae.letscook.constants.ConstantsContract;
import com.tae.letscook.model.Chef;
import com.tae.letscook.model.DietLabelLocal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 25/01/2016.
 */
public class DaoUser {

    private static final String TAG = DaoFavourites.class.getSimpleName();
    private static DaoUser instance;
    private LetsCookHelper letsCookHelper;

    private DaoUser(Context context) {
        openDataBase(context);
    }

    public static DaoUser getInstance(Context context) {
        if (instance == null) {
            instance = new DaoUser(context);
        }
        return instance;
    }

    public void openDataBase(Context context) {
        letsCookHelper = new LetsCookHelper(context);
    }

    public boolean insert(Chef chef) {
        SQLiteDatabase db = letsCookHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long rowId = 0;
        try {
            cv.put(UserContract.UserEntry.COLUMN_USER_ID, chef.getId());
            cv.put(UserContract.UserEntry.COLUMN_USER_NAME, chef.getName());
            cv.put(UserContract.UserEntry.COLUMN_USER_MAIL, chef.getEmail());
            cv.put(UserContract.UserEntry.COLUMN_USER_IMAGE, chef.getPicture());
            rowId = db.insert(UserContract.UserEntry.TABLE_USER, UserContract.UserEntry.COLUMN_USER_MAIL, cv);

        } catch (SQLiteException e) {
            Log.e(TAG, "insert: Error inserting user ", e);
            LetsCookApp.getInstance().trackException(e);
        } finally {
            db.close();
        }
        return rowId > -1;
    }

//    public int delete(long recipeId) {
//        Log.i(TAG, "delete: user id: " + recipeId);
//        SQLiteDatabase db = letsCookHelper.getWritableDatabase();
//        int rows = 0;
//        try {
//            rows = db.delete(
//                    DietContract.DietEntry.TABLE_DIET,
//                    DietContract.DietEntry.COLUMN_FK_DIET+ "=?",
//                    new String[]{String.valueOf(recipeId)});
//        } catch (SQLiteException e) {
//            Log.e(TAG, "delete: Error deleting label: " + recipeId , e );
//            LetsCookApp.getInstance().trackException(e);
//        } finally {
//            if (db != null) {
//                db.close();
//            }
//        }
//        return rows;
//    }

    public Chef getUser() {
        SQLiteDatabase db = letsCookHelper.getReadableDatabase();
        String[] columns = {
                UserContract.UserEntry.COLUMN_USER_ID,
                UserContract.UserEntry.COLUMN_USER_NAME,
                UserContract.UserEntry.COLUMN_USER_MAIL,
                UserContract.UserEntry.COLUMN_USER_IMAGE
        };
        String sortOrder =
                UserContract.UserEntry._ID + ConstantsContract.ASCENDANT;
        Chef chef = null;
        try {
            Cursor cursor = db.query(
                    UserContract.UserEntry.TABLE_USER,
                    columns,
                    null,
                    null,
                    null,
                    null,
                    sortOrder);
            while (cursor.moveToNext()) {
                String uuid = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_ID));
                String name = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_NAME));
                String mail = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_MAIL));
                String image = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_IMAGE));
                chef = new Chef(uuid, name, mail, image);
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
        return chef;
    }

}
