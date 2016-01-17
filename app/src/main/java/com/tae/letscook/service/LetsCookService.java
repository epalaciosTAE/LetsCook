package com.tae.letscook.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.tae.letscook.api.LetsCookRestAdapter;
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

    @Override
    protected void onHandleIntent(Intent intent) {
        LetsCookRestAdapter restAdapter = new LetsCookRestAdapter(getApplicationContext());
        switch (intent.getAction()) {
            case ActionConstants.ACTION_RECIPES_BY_CATEGORY:
                restAdapter.getRecipesByCategory(intent.getStringExtra(Constants.EXTRA_CATEGORY));
                break;
            case ActionConstants.ACTION_RECIPES_RANDOM:
                restAdapter.getRecipesRandom();
                break;
        }
    }
}
