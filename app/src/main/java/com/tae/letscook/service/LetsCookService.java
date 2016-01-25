package com.tae.letscook.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.tae.letscook.api.LetsCookRestAdapter;
import com.tae.letscook.app.LetsCookApp;
import com.tae.letscook.constants.AnalyticsConstants;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.constants.ActionConstants;

/**
 * Created by Eduardo on 15/01/2016.
 */
public class LetsCookService extends IntentService {

    private static final String TAG = LetsCookService.class.getSimpleName();

    public LetsCookService() {
        super(TAG);
    }

    public static Intent makeIntentWithCategory(Context context, String category) {
        Intent intent = new Intent(context, LetsCookService.class);
        intent.setAction(ActionConstants.ACTION_RECIPES_BY_CATEGORY);
        intent.putExtra(Constants.EXTRA_CATEGORY, category);
        return intent;
    }

    public static Intent makeIntentHomeView(Context context) {
        Intent intent = new Intent(context, LetsCookService.class);
        intent.setAction(ActionConstants.ACTION_RECIPES_RANDOM);
        return intent;
    }

    public static Intent makeIntentGEocoding(Context context, String query) {
        Intent intent = new Intent(context, LetsCookService.class);
        intent.setAction(ActionConstants.ACTION_GEOCODING);
        intent.putExtra(Constants.EXTRA_GEOCODING_QUERY, query);
        return intent;
    }

    public static Intent makeIntentLogin(Context context, String authCode) {
        Intent intent = new Intent(context, LetsCookService.class);
        intent.setAction(ActionConstants.ACTION_LOGIN);
        intent.putExtra(Constants.EXTRA_AUTHCODE, authCode);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LetsCookRestAdapter restAdapter = new LetsCookRestAdapter(getApplicationContext());
        switch (intent.getAction()) {
            case ActionConstants.ACTION_RECIPES_BY_CATEGORY:
                LetsCookApp.getInstance().trackEvent(AnalyticsConstants.EVENT_CATEGORY, AnalyticsConstants.ACTION_DOWNLOAD, AnalyticsConstants.CONNECTING);
                restAdapter.getRecipesByCategory(intent.getStringExtra(Constants.EXTRA_CATEGORY));
                break;
            case ActionConstants.ACTION_RECIPES_RANDOM:
                LetsCookApp.getInstance().trackEvent(AnalyticsConstants.EVENT_RANDOM, AnalyticsConstants.ACTION_DOWNLOAD, AnalyticsConstants.CONNECTING);
                restAdapter.getRecipesRandom();
                break;
            case ActionConstants.ACTION_LOGIN :
                LetsCookApp.getInstance().trackEvent(AnalyticsConstants.EVENT_LOGIN_GOOGLE, AnalyticsConstants.ACTION_LOGIN_GOOGLE, AnalyticsConstants.CONNECTING);
                restAdapter.signIn(intent.getStringExtra(Constants.EXTRA_AUTHCODE));
                break;
            case ActionConstants.ACTION_GEOCODING :
                restAdapter.getGeoCode(intent.getStringExtra(Constants.EXTRA_GEOCODING_QUERY));
                break;
        }
    }
}
