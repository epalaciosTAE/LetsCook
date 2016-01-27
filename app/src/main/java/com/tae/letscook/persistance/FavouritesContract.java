package com.tae.letscook.persistance;

import android.provider.BaseColumns;

import com.tae.letscook.constants.ConstantsContract;

/**
 * Created by Eduardo on 17/01/2016.
 */
public class FavouritesContract {

    public static abstract class FavouritesEntry implements BaseColumns {
        public static final String TABLE_RECIPES = "table_recipes";
//        public static final String COLUMN_FK_USER = UserContract.UserEntry.COLUMN_USER_ID;
        public static final String COLUMN_LABEL = "label";
        public static final String COLUMN_IMAGE_URL = "image_url";
        public static final String COLUMN_CALORIES = "calories";
//        public static final String COLUMN_INGREDIENTS_ID = "ingredients_id";
//        public static final String COLUMN_NUTRIENTS_ID = "nutrients_id";
//        public static final String COLUMN_DIET_ID = "diet_id";


        public static final String TABLE_OTHER_CHEFS = "table_other_chefs";
        public static final String COLUMN_CHEF_ID = "chef_id";


    public static final String SQL_CREATE_RECIPES_TABLE =
            ConstantsContract.CREATE + FavouritesEntry.TABLE_RECIPES + " (" +
                    FavouritesEntry._ID + ConstantsContract.PRIMARY_KEY + ConstantsContract.COMA +
                    FavouritesEntry.COLUMN_LABEL + ConstantsContract.TEXT + ConstantsContract.COMA +
                    FavouritesEntry.COLUMN_IMAGE_URL + ConstantsContract.TEXT + ConstantsContract.COMA +
                    FavouritesEntry.COLUMN_CALORIES + ConstantsContract.INTEGER + ")";
//    FavouritesEntry.COLUMN_INGREDIENTS_ID + ConstantsContract.INTEGER + ConstantsContract.COMA +
//    FavouritesEntry.COLUMN_NUTRIENTS_ID + ConstantsContract.INTEGER + ConstantsContract.COMA +
//    FavouritesEntry.COLUMN_DIET_ID + ConstantsContract.INTEGER
//
    public static final String SQL_DELETE_RECIPES_TABLE =
            ConstantsContract.DROP_TABLE + FavouritesEntry.TABLE_RECIPES;

    }
}

