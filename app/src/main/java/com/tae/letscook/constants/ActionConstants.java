package com.tae.letscook.constants;

import android.content.Intent;

/**
 * Created by Eduardo on 15/01/2016.
 */
public class ActionConstants {

    public static final String ACTION_RECIPES_BY_CATEGORY = "com.tae.letscook.DOWNLOAD_RECIPES_BY_CATEGORY";
    public static final String ACTION_RECIPES_RANDOM = "com.tae.letscook.DOWNLOAD_RECIPES_RANDOM";
    public static final String ACTION_DOWNLOAD_RECIPES_BY_CATEGORY_SUCCESS = "com.tae.letscook.DOWNLOAD_BY_CATEGORY_SUCCESS";
    public static final String ACTION_DOWNLOAD_RECIPES_RANDOM_SUCCESS = "com.tae.letscook.DOWNLOAD_RECIPES_RANDOM_SUCCESS";

    public static final String ACTION_LOGIN = "com.tae.letscook.LOGIN";
    public static final String ACTION_SIGN_IN_SUCCESS = "com.tae.letscook.SIGN_IN_SUCCESS";
    public static final String ACTION_UPDATE_SQLITE_RECIPES = "com.tae.letscook.UPDATE_SQLITE_RECIPES";
    public static final String ACTION_GEOCODING = "com.tae.letscook.GEOCODING";
    public static final String ACTION_DOWNLOAD_GEOCODING_SUCCESS = "com.tae.letscook.ACTION_DOWNLOAD_GEOCODING_SUCCESS";
    public static final String ACTION_POST_CHEF = "com.tae.letscook.POST_CHEF";
    public static final String ACTION_POST_EVENT = "com.tae.letscook.POST_EVENT";
    public static final String ACTION_LOAD_START_DATA = "com.tae.letscook.ACTION_DOWNLOAD_EVENTS_SUCCESS";
    public static final String ACTION_DOWNLOAD_EVENTS_SUCCESS = "com.tae.letscook.ACTION_DOWNLOAD_EVENTS_SUCCESS";
    public static final String ACTION_LOAD_SQLITE_FAVS_SUCCESS = "com.tae.letscook.ACTION_LOAD_SQLITE_FAVS_SUCCESS";
    public static final String ACTION_DOWNLOAD_CHEFS_SUCCESS = "com.tae.letscook.ACTION_DOWNLOAD_ALL_CHEFS_SUCCESS";
    public static final String ACTION_UPLOAD_EVENT_SUCCESS = "com.tae.letscook.ACTION_UPDATE_EVENT_SUCCESS";
    public static final String ACTION_POST_CUSTOM_RECIPE = "com.tae.letscook.ACTION_POST_CUSTOM_RECIPE";
    public static final String ACTION_UPLOAD_CUSTOM_RECIPE_SUCCESS = "com.tae.letscook.ACTION_POST_CUSTOM_RECIPE_SUCCES";
    public static final String ACTION_DOWNLOAD_CUSTOM_RECIPE_SUCCESS = "com.tae.letscook.ACTION_DOWNLOAD_CUSTOM_RECIPE_SUCCES";
    public static final String ACTION_DOWNLOAD_CUSTOM_RECIPE_IMAGES_SUCCESS = "com.tae.letscook.ACTION_DOWNLOAD_CUSTOM_RECIPE_IMAGE_SUCCES";
}
