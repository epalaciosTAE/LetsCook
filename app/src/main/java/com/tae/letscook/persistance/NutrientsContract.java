package com.tae.letscook.persistance;

import android.provider.BaseColumns;

import com.tae.letscook.constants.ConstantsContract;

/**
 * Created by Eduardo on 17/01/2016.
 */
public class NutrientsContract {
    public static abstract class NutrientEntry implements BaseColumns {

        public static final String TABLE_NUTRIENTS = "table_nutrients";
        public static final String COLUMN_FK_NUTRIENTS = ConstantsContract.RECIPE_FK;
        public static final String COLUMN_NUTRIENT = "nutrient";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_UNIT = "unit";

        public static final String SQL_CREATE_NUTRIENTS_TABLE =
                ConstantsContract.CREATE + TABLE_NUTRIENTS + " (" +
                        _ID + ConstantsContract.PRIMARY_KEY + ConstantsContract.COMA +
                        COLUMN_NUTRIENT + ConstantsContract.TEXT + ConstantsContract.COMA +
                        COLUMN_AMOUNT + ConstantsContract.INTEGER + ConstantsContract.COMA +
                        COLUMN_UNIT + ConstantsContract.TEXT + ConstantsContract.COMA +
                        COLUMN_FK_NUTRIENTS + ConstantsContract.INTEGER + ConstantsContract.COMA +
                        ConstantsContract.FOREIGN_KEY + " (" + COLUMN_FK_NUTRIENTS + ") " +
                        ConstantsContract.REFERENCES + " " +
                        FavouritesContract.FavouritesEntry.TABLE_RECIPES + " (" + FavouritesContract.FavouritesEntry._ID + ")" +
                        ConstantsContract.ON_DELETE_CASCADE + ")";

        public static final String SQL_DELETE_NUTRIENTS_TABLE =
                ConstantsContract.DROP_TABLE + TABLE_NUTRIENTS;



    }
}
