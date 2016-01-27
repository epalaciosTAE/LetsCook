package com.tae.letscook.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.tae.letscook.R;
import com.tae.letscook.constants.ActionConstants;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.model.Chef;
import com.tae.letscook.model.CustomRecipe;
import com.tae.letscook.model.Event;
import com.tae.letscook.model.RecipeLocal;
import com.tae.letscook.service.LetsCookService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eduardo on 22/01/2016.
 */
public class ActivitySplash extends AppCompatActivity implements Animation.AnimationListener {

    private static final long ANIM_TITLE_TIME_OUT = 1700;
    private static final long DELAYED_TIME_OUT = 3500;
    @Bind(R.id.img_splash_image) protected ImageView imageView;
    @Bind(R.id.tv_splash) protected TextView tvTitle;
    private List<RecipeLocal> recipes;
    private List<Event> events;
    private List<CustomRecipe> customRecipes;
    private LoaderDataReceiver dataReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initAnimations();
        dataReceiver = new LoaderDataReceiver();
        startService(LetsCookService.makeIntent(ActivitySplash.this, ActionConstants.ACTION_LOAD_START_DATA));
        saveUserFirstTimeLogin();

    }

    private void saveUserFirstTimeLogin() {
        SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREFS_USER, MODE_PRIVATE);
        if (sp.getBoolean(Constants.SHARE_PREFS_FIRST_TIME, true)) {
            Log.i("SplashActivity", "onCreate: first time login, saving user");
            //TODO POST USER IN SERVER
            Chef chef = getIntent().getParcelableExtra(Constants.EXTRA_CHEF);
            chef.setId(UUID.randomUUID().toString());
            sp.edit().putBoolean(Constants.SHARE_PREFS_FIRST_TIME, false).apply();
            startService(LetsCookService.makeIntentChef(ActivitySplash.this, chef));
        }
    }

    private void initTimerTask() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ActivitySplash.this, ActivityDrawer.class)
                        .putExtra(Constants.EXTRA_CHEF, getIntent().getParcelableExtra(Constants.EXTRA_CHEF))
                        .putParcelableArrayListExtra(Constants.EXTRA_RECIPES, (ArrayList<? extends Parcelable>) recipes)
                        .putParcelableArrayListExtra(Constants.EXTRA_EVENTS, (ArrayList<? extends Parcelable>) events)
                        .putParcelableArrayListExtra(Constants.EXTRA_CUSTOM_RECIPES, (ArrayList<? extends Parcelable>) customRecipes)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                finish();
            }
        }, DELAYED_TIME_OUT);
    }

    private void initAnimations() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        animation.setAnimationListener(this);
        final Animation titleAnim = AnimationUtils.loadAnimation(ActivitySplash.this, R.anim.move);
        titleAnim.setAnimationListener(this);
        imageView.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.startAnimation(titleAnim);
            }
        }, ANIM_TITLE_TIME_OUT);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        imageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ActionConstants.ACTION_DOWNLOAD_EVENTS_SUCCESS);
        intentFilter.addAction(ActionConstants.ACTION_LOAD_SQLITE_FAVS_SUCCESS);
        intentFilter.addAction(ActionConstants.ACTION_DOWNLOAD_CUSTOM_RECIPE_SUCCESS);
        LocalBroadcastManager.getInstance(this).registerReceiver(dataReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(dataReceiver);
    }

    public class LoaderDataReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()) {
                case ActionConstants.ACTION_LOAD_SQLITE_FAVS_SUCCESS :
                    recipes = intent.getParcelableArrayListExtra(Constants.EXTRA_RECIPES);
                    break;
                case ActionConstants.ACTION_DOWNLOAD_EVENTS_SUCCESS :
                    events = intent.getParcelableArrayListExtra(Constants.EXTRA_EVENTS);
                    break;
                case ActionConstants.ACTION_DOWNLOAD_CUSTOM_RECIPE_SUCCESS :
                    customRecipes = intent.getParcelableArrayListExtra(Constants.EXTRA_CUSTOM_RECIPES);
                    break;
            }

            if (recipes != null && events != null && customRecipes != null) {
                initTimerTask();
            }
        }
    }
}
