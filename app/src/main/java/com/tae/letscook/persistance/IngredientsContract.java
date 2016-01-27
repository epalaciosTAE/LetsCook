package com.tae.letscook.persistance;

import android.provider.BaseColumns;

import com.tae.letscook.constants.ConstantsContract;

/**
 * Created by Eduardo on 17/01/2016.
 */
public class IngredientsContract {

    public static abstract class IngredientEntry implements BaseColumns {

        public static final String TABLE_INGREDIENTS = "table_ingredients";
        public static final String COLUMN_FK_INGREDIENTS = ConstantsContract.RECIPE_FK;
        public static final String COLUMN_INGREDIENT = "ingredient";

        public static final String SQL_CREATE_INGREDIENTS_TABLE =
                ConstantsContract.CREATE + TABLE_INGREDIENTS + " (" +
                        _ID + ConstantsContract.PRIMARY_KEY + ConstantsContract.COMA +
                        COLUMN_INGREDIENT + ConstantsContract.TEXT + ConstantsContract.COMA +
                        COLUMN_FK_INGREDIENTS + ConstantsContract.INTEGER + ConstantsContract.COMA +
                        ConstantsContract.FOREIGN_KEY + " (" + COLUMN_FK_INGREDIENTS + ") " +
                        ConstantsContract.REFERENCES + " " +
                        FavouritesContract.FavouritesEntry.TABLE_RECIPES + " (" + FavouritesContract.FavouritesEntry._ID + ")" +
                        ConstantsContract.ON_DELETE_CASCADE + ")";

        public static final String SQL_DELETE_INGREDIENTS_TABLE =
                ConstantsContract.DROP_TABLE + TABLE_INGREDIENTS;
    }
}
