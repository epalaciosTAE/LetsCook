package com.tae.letscook.persistance;

import android.provider.BaseColumns;

import com.tae.letscook.constants.ConstantsContract;

/**
 * Created by Eduardo on 17/01/2016.
 */
public class UserContract {

    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_USER = "table_user";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_USER_NAME = "title";
        public static final String COLUMN_USER_MAIL = "email";
        public static final String COLUMN_USER_IMAGE = "image";


//    public static final String TEXT = " TEXT";
//    public static final String INTEGER = " INTEGER";
//    public static final String CREATE = "CREATE TABLE ";
//    public static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
//    public static final String PK = " INTEGER PRIMARY KEY" ;
//    public static final String COMA = ", ";

    public static final String SQL_CREATE_USER_TABLE =
            ConstantsContract.CREATE + UserEntry.TABLE_USER + " (" +
                    UserEntry._ID + ConstantsContract.PRIMARY_KEY + ConstantsContract.COMA +
                    UserEntry.COLUMN_USER_ID + ConstantsContract.TEXT + ConstantsContract.COMA +
                    UserEntry.COLUMN_USER_NAME + ConstantsContract.TEXT + ConstantsContract.COMA +
                    UserEntry.COLUMN_USER_MAIL + ConstantsContract.TEXT + ConstantsContract.COMA +
                    UserEntry.COLUMN_USER_IMAGE + ")";

    public static final String SQL_DELETE_USER_TABLE =
            ConstantsContract.DROP_TABLE + UserEntry.TABLE_USER;
    }
}