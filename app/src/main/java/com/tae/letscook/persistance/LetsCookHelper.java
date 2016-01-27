package com.tae.letscook.persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.SparseArray;

import com.tae.letscook.constants.ConstantsContract;
import com.tae.letscook.constants.HelperConstans;

/**
 * Created by Eduardo on 17/01/2016.
 */
public class LetsCookHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LetsCook_Favourites.db";

    public LetsCookHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    @Override
//    public void onOpen(SQLiteDatabase db) {
//        super.onOpen(db);
//        if (!db.isReadOnly()) {
//            db.execSQL("PRAGMA foreign_keys=ON;"); // Enable foreign key constraints
//        }
//    }
//
//    @Override
//    public void onConfigure(SQLiteDatabase db) {
//        super.onConfigure(db);
//        db.setForeignKeyConstraintsEnabled(true);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(IngredientsContract.IngredientEntry.SQL_CREATE_INGREDIENTS_TABLE);
        db.execSQL(NutrientsContract.NutrientEntry.SQL_CREATE_NUTRIENTS_TABLE);
        db.execSQL(DietContract.DietEntry.SQL_CREATE_DIET_TABLE);
        db.execSQL(FavouritesContract.FavouritesEntry.SQL_CREATE_RECIPES_TABLE);
        db.execSQL(UserContract.UserEntry.SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ConstantsContract.DROP_TABLE + IngredientsContract.IngredientEntry.SQL_CREATE_INGREDIENTS_TABLE);
        db.execSQL(ConstantsContract.DROP_TABLE + NutrientsContract.NutrientEntry.SQL_CREATE_NUTRIENTS_TABLE);
        db.execSQL(ConstantsContract.DROP_TABLE + DietContract.DietEntry.SQL_CREATE_DIET_TABLE);
        db.execSQL(ConstantsContract.DROP_TABLE + FavouritesContract.FavouritesEntry.SQL_CREATE_RECIPES_TABLE);
        db.execSQL(ConstantsContract.DROP_TABLE + UserContract.UserEntry.SQL_CREATE_USER_TABLE);
        onCreate(db);
    }
}
