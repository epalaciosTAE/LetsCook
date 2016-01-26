package com.tae.letscook.constants;

/**
 * Created by Eduardo on 15/01/2016.
 */
public class ServerConstants {

    public static final String LETS_COOK_SERVER_BASE_URL = "http://192.168.1.201:8080"; //192.168.1.247
    public static final String RECIPES_BY_CATEGORY_ENDPOINT = "/find-recipe";
    public static final String RECIPES_RANDOM_ENDPOINT = "/recipes";
    public static final String SIGN_IN = "/sign-in/authcode";
    public static final String GEOCODING = "/geocoding";
    public static final String CHEF = "/chef";
    public static final String EVENT = CHEF + "/{id}/event";
    public static final String EVENTS = CHEF + "/events";
    public static final String CHEFS = "/chefs";
    public static final String USER_RECIPES = CHEF + "/{id}/recipes";
    public static final String USER_RECIPES_DATA = USER_RECIPES + "/data";
    public static final String DATA_IMAGE = "data_image";
    public static final String USER_RECIPES_IMAGES = USER_RECIPES + "/images";
}
