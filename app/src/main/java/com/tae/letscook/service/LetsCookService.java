package com.tae.letscook.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.tae.letscook.activity.ActivitySplash;
import com.tae.letscook.api.LetsCookRestAdapter;
import com.tae.letscook.app.LetsCookApp;
import com.tae.letscook.constants.AnalyticsConstants;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.constants.ActionConstants;
import com.tae.letscook.manager.RecipesManager;
import com.tae.letscook.model.Chef;
import com.tae.letscook.model.Event;
import com.tae.letscook.model.RecipeLocal;
import com.tae.letscook.persistance.DaoUser;

import java.util.ArrayList;
import java.util.List;

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

    public static Intent makeIntent(Context context, String action) {
        Intent intent = new Intent(context, LetsCookService.class);
        intent.setAction(action);
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


    public static Intent makeIntentChef(Context context, Chef chef) {
        Intent intent = new Intent(context, LetsCookService.class);
        intent.setAction(ActionConstants.ACTION_POST_CHEF);
        intent.putExtra(Constants.EXTRA_CHEF, chef);
        return intent;
    }

    public static Intent makeIntentEvent(Context context, Event event) {
        Intent intent = new Intent(context, LetsCookService.class);
        intent.setAction(ActionConstants.ACTION_POST_EVENT);
        intent.putExtra(Constants.EXTRA_EVENT, event);
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
            case ActionConstants.ACTION_POST_CHEF :
                final Chef chef = intent.getParcelableExtra(Constants.EXTRA_CHEF);
                restAdapter.postChef(chef);
                DaoUser.getInstance(getApplicationContext()).insert(chef);
                break;
            case ActionConstants.ACTION_POST_EVENT :
                final Chef chefisimo = DaoUser.getInstance(getApplicationContext()).getUser();
                Event event = intent.getParcelableExtra(Constants.EXTRA_EVENT);
                event.setChefId(chefisimo.getId());
                event.setUserName(chefisimo.getName());
                restAdapter.postEvent(chefisimo.getId(), event);
                break;
            case ActionConstants.ACTION_GET_EVENTS :
                Log.i(TAG, "onHandleIntent: loading recipes from sqlite");
                List<RecipeLocal> recipes = RecipesManager.getInstance(getApplicationContext()).getRecipesFromSQLite();
                Log.i(TAG, "onHandleIntent: downloading events from server");
                restAdapter.getEvents();
                // TODO load recipes added by the user
                if (recipes != null) {
                    LocalBroadcastManager.getInstance(getApplicationContext())
                            .sendBroadcast(new Intent(ActionConstants.ACTION_LOAD_SQLITE_FAVS_SUCCESS)
                                    .putParcelableArrayListExtra(Constants.EXTRA_RECIPES, (ArrayList<? extends Parcelable>) recipes));
                }
                break;

        }
    }
}
