package com.tae.letscook.persistance;

import android.provider.BaseColumns;

import com.tae.letscook.constants.ConstantsContract;

/**
 * Created by Eduardo on 17/01/2016.
 */
public class DietContract {
    public static abstract class DietEntry implements BaseColumns{
        public static final String TABLE_DIET = "table_diet";
        public static final String COLUMN_FK_DIET = ConstantsContract.RECIPE_FK;
        public static final String COLUMN_DIET = "diet";

        public static final String SQL_CREATE_DIET_TABLE =
                ConstantsContract.CREATE + TABLE_DIET + " (" +
                        _ID + ConstantsContract.PRIMARY_KEY + ConstantsContract.COMA +
                        COLUMN_DIET + ConstantsContract.TEXT + ConstantsContract.COMA +
                        COLUMN_FK_DIET + ConstantsContract.INTEGER + ConstantsContract.COMA +
                        ConstantsContract.FOREIGN_KEY + " (" + COLUMN_FK_DIET + ") " +
                        ConstantsContract.REFERENCES + " " +
                        FavouritesContract.FavouritesEntry.TABLE_RECIPES + " (" + FavouritesContract.FavouritesEntry._ID + ")" +
                        ConstantsContract.ON_DELETE_CASCADE + ")";

        public static final String SQL_DELETE_DIET_TABLE =
                ConstantsContract.DROP_TABLE + COLUMN_DIET;

    }
}
