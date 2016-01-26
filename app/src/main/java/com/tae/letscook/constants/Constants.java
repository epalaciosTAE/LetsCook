package com.tae.letscook.constants;

/**
 * Created by Eduardo on 30/12/2015.
 *
 * Posible API for recipes http://www.recipepuppy.com/about/api/
 * Possible API for restaurants https://www.zomato.com/es/api/documentation
 */
public class Constants {

    public static final float DRAWER_ALPHA = 1f;
    public static final float DRAWER_ALPHA_PERCENT = 0.6f;
    public static final int DRAWER_ITEMS_SIZE = 5;
    public static final int FRAGMENT_RECIPE_PAGER_POSITION = 1;
    public static final int FRAGMENT_ADD_RECIPE_POSITION = 2;
    public static final int FRAGMENT_OTHER_CHEFS_POSITION = 3;
    public static final int FRAGMENT_FAVOURITES_POSITION = 4;
    public static final int FRAGMENT_EVENTS_POSITION = 5;

    public static final int ADAPTER_DRAWER_ID = 0;
    public static final int ADAPTER_CATEGORIES_ID = 1;

    public static final int CATEGORY_MEAT_ID = 0;
    public static final int CATEGORY_FISH_ID = 1;
    public static final int CATEGORY_PASTA_ID = 2;
    public static final int CATEGORY_RICE_ID = 3;
    public static final int CATEGORY_SALAD_ID = 4;
    public static final int CATEGORY_SOUP_ID = 5;
    public static final int CATEGORY_DESERT_ID = 6;

    public static final String EXTRA_CATEGORY = "extra_category";
    public static final String EXTRA_RECIPES = "extra_recipes";
    public static final String EXTRA_ITEM_RECIPES = "extra_item_recipes";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_RECIPES_RANDOM = "extra_recipes_random";
    public static final String EXTRA_SUGGESTIONS = "extra_suggestions";
    public static final String EXTRA_CHEF = "extra_chef";

    public static final String EXTRA_AUTHCODE = "extra_auth_code";
    public static final String EXTRA_SQLITE_RECIPES = "extra_recipes_sqlite";

    public static final String EXTRA_NUTRIENTS = "extra_nutrients";
    public static final String EXTRA_FRAGMENT_ID = "extra_fragment_id";
    public static final String EXTRA_GEOCODING_QUERY = "extra_geocoding_query";
    public static final String EXTRA_GEOCODING = "extra_geocoding";
    public static final String EXTRA_EVENTS = "extra_events";

    public static final String EXTRA_EVENT = "extra_event";
    public static final String ARGS_CATEGORY_ID = "args_category_id";

    public static final String ARGS_RECIPE_TITLE= "args_recipe_title";
    public static final String ARGS_RECIPE_CATEGORY= "args_recipe_category";
    public static final String RECIPE = "Recipe: ";
    public static final int PERMISSIONS_REQUEST_CAMERA = 101;
    public static final int PERMISSIONS_REQUEST_WRITE_STORAGE = 102;
    public static final String CAMERA_IMAGES_FOLDER = "LetsCook";
    public static final int REQUEST_CAMERA_IMAGE = 201;
    public static final int CARD_VIEW_KCAL = 1;
    public static final String KCAL = "calories";
    public static final String PROTEIN = "High-Protein";
    public static final String FIBER = "High-Fiber";

    public static final String CARB = "Low-Carb";
    public static final String FAT = "Low-Fat";
    public static final String SODIUM = "Low-Sodium";
    public static final String BALANCED = "Balanced";
    public static final CharSequence VIEW_PAGER_WORLD_FOOD = "World Food";
    public static final CharSequence VIEW_PAGER_SUGGESTIONS = "Suggestions";
    public static final int FADE_IN = 0;
    public static final int FADE_OUT = 1;
    public static final String CHECK_RANDOM_RECIPES = "Don't miss the suggestions for today!";
    public static final CharSequence ADD_TO_FAVOURITES = "Adding to favourites";
    public static final int FRAGMENT_FAVOURITES = 0;
    public static final int FRAGMENT_PAGER = 1;
    public static final int REQUEST_DATE = 112;
    public static final String SHARED_PREFS_USER = "shared_prefs_user";
    public static final String SHARE_PREFS_FIRST_TIME = "shared_prefs_first_time_running";
    public static final String EXTRA_CHEFS = "extra_all_chefs";


//    "http://s1.1zoom.net/big3/862/340361-svetik.jpg"
//    "http://www.nutritionbymi.com/wordpress/wp-content/uploads/2013/07/shutterstock_131743067.jpg"
}
