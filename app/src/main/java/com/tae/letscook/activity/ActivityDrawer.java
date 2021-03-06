package com.tae.letscook.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;
import com.tae.letscook.Utils.DetectTabletUtils;
import com.tae.letscook.Utils.ModelConverter;
import com.tae.letscook.Utils.NetworkUtils;
import com.tae.letscook.Utils.ToastUtils;
import com.tae.letscook.app.LetsCookApp;
import com.tae.letscook.constants.AnalyticsConstants;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.Utils.DrawableUtils;
import com.tae.letscook.R;
import com.tae.letscook.adapter.AdapterDrawer;
import com.tae.letscook.constants.ActionConstants;
import com.tae.letscook.dialog.DialogFragmentRecipeTitle;
import com.tae.letscook.fragment.FragmentAddEvent;
import com.tae.letscook.fragment.FragmentAddRecipe;
import com.tae.letscook.fragment.FragmentCustomRecipeDetail;
import com.tae.letscook.fragment.FragmentCustomRecipes;
import com.tae.letscook.fragment.FragmentEvents;
import com.tae.letscook.fragment.FragmentHome;
import com.tae.letscook.fragment.FragmentNutrients;
import com.tae.letscook.fragment.FragmentOtherChefs;
import com.tae.letscook.fragment.FragmentRecipeDetail;
import com.tae.letscook.fragment.FragmentRecipes;
import com.tae.letscook.fragment.FragmentRecipesViewer;
import com.tae.letscook.listeners.OnCategoryItemListener;
import com.tae.letscook.listeners.OnItemClickListener;
import com.tae.letscook.listeners.OnNutrientsListener;
import com.tae.letscook.listeners.OnRecipeItemListener;
import com.tae.letscook.listeners.OnRecipeTitleListener;
import com.tae.letscook.listeners.OnTaskResponse;
import com.tae.letscook.model.Chef;
import com.tae.letscook.model.CustomRecipe;
import com.tae.letscook.model.Event;
import com.tae.letscook.model.Item;
import com.tae.letscook.model.ItemRecipe;
import com.tae.letscook.model.NutrientLocal;
import com.tae.letscook.model.RecipeLocal;
import com.tae.letscook.model.geocoding.GeocodingLatLng;
import com.tae.letscook.service.LetsCookService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityDrawer extends AppCompatActivity
        implements OnItemClickListener, OnRecipeTitleListener, OnCategoryItemListener,
        OnRecipeItemListener, OnNutrientsListener, OnTaskResponse{

    private static final String TAG = ActivityDrawer.class.getSimpleName();
    @Bind(R.id.toolbar) protected Toolbar mToolbar;
    @Bind(R.id.drawer_layout) protected DrawerLayout mDrawerLayout;
    @Bind(R.id.rv_navigation_drawer) protected RecyclerView mRecyclerView;
    @Bind(R.id.tv_logout) protected TextView tvLogout;
    private SparseArray<Fragment> mDrawerFragments;
    private String[] mFragmentTags;
    private LetsCookReceiver receiver;
    private ProgressDialog progressDialog;
    private List<RecipeLocal>recipes, suggestionsOfTheDay, recipesSQLite; // receive their data from broadcast receiver
    private List<ItemRecipe> suggestions;
    private List<Event> events;
    private List<CustomRecipe> customRecipes;
    private boolean ofTheDay, isFragmentFavourites, isTwoPane;
    private Chef chef;
    private Fragment currentFragment;
    private String currentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);

        isTwoPane = DetectTabletUtils.isTablet(this);
        ToastUtils.showToast(this, "Is two pane?: " + isTwoPane);

        initMemberFields();
        setSupportActionBar(mToolbar);
        setRecyclerView();
        setDrawerToggle();
        setTvLogoutRipple();
        hasStoragePermission();
        initLetsCookReceiver();
        initProgressDialog();
        mToolbar.setNavigationIcon(R.drawable.ic_chicken_burger); // this changes the burguer

        mDrawerFragments = getDrawerFragments(); //drawer fragments
        mFragmentTags = getResources().getStringArray(R.array.nav_drawer_fragment_tags);


        if (savedInstanceState != null) {
            currentFragment = getSupportFragmentManager().getFragment(savedInstanceState, Constants.ROTATION_CURRENT_FRAGMENT);
            currentTag = (String) savedInstanceState.get(Constants.ROTATION_CURRENT_FRAGMENT_TAG);
            displayFragment(currentFragment, currentTag);
        } else {
            setCurrentFragment(FragmentHome.newInstance());
            setCurrentTag(getResources().getString(R.string.fragment_home));
            displayFragment(currentFragment,currentTag);
        }

    }

    private void setCurrentTag(String fragmentTag) {
        currentTag = fragmentTag;
    }

    private void setCurrentFragment(Fragment fragment) {
        currentFragment = fragment;
    }

    private void initMemberFields() {
        chef = getIntent().getParcelableExtra(Constants.EXTRA_CHEF); // TODO are we doing something with the user?¿
        Log.i(TAG, "onCreate: Chef data from splash activity" + getIntent().getParcelableExtra(Constants.EXTRA_CHEF));
        events = getIntent().getParcelableArrayListExtra(Constants.EXTRA_EVENTS);
        Log.i(TAG, "onCreate: retrieving events from splash activity " + events.size());
        recipesSQLite = getIntent().getParcelableArrayListExtra(Constants.EXTRA_RECIPES);
        Log.i(TAG, "onCreate: retrieving recipes sqlite (favs) from splash activity " + recipesSQLite.size());
        customRecipes = getIntent().getParcelableArrayListExtra(Constants.EXTRA_CUSTOM_RECIPES);
        Log.i(TAG, "onCreate: retrieving custom recipes from splash activity " + customRecipes.size());
    }

    private void loadSuggestionRecipes() {
        startService(LetsCookService.makeIntentSuggestions(
                ActivityDrawer.this,
                ActionConstants.ACTION_RECIPES_RANDOM,
                customRecipes));
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(ActivityDrawer.this);
    }

    private void initLetsCookReceiver() {
        receiver = new LetsCookReceiver();
    }

    /**
     * Enable ripple effect in the logout option in the navigation drawer
     */
    private void setTvLogoutRipple() {
        tvLogout.setSelected(true); // this line allows the selector xml
        DrawableUtils.setRipple(tvLogout, DrawableUtils.drawableFactory(this, R.drawable.ripple_drawable));
    }

    /**
     * Ask the user to allow permission for storage
     * @return
     */
    private boolean hasStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.PERMISSIONS_REQUEST_WRITE_STORAGE);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * Init the Navigation's Drawer RecyclerView
     * Set the Adapter
     */
    private void setRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        List<Item> menuItems = generateDrawerItems();
        AdapterDrawer mAdapterDrawer = new AdapterDrawer(this, menuItems, chef);
        mRecyclerView.setAdapter(mAdapterDrawer);
    }

    /**
     * In order to get the xml resources, if we stored in the xml an int []
     * we need to use a TypedArray to get the resources and then get the resourceId
     * (Check DrawableUtils class)
     * @return List<DrawerItem>
     */
    @NonNull
    private List<Item> generateDrawerItems() {
        return DrawableUtils.itemsFromResourcesFactory(this, R.array.nav_drawer_titles, R.array.nav_drawer_icons);
    }

    /**
     * Init the Action bar drawer toggle to open and close the navigation drawer.
     * Set the drawer layour with the toggle object.
     */
    private void setDrawerToggle() {
        final ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.open_drawer,
                R.string.close_drawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset < Constants.DRAWER_ALPHA_PERCENT) {
                    mToolbar.setAlpha(Constants.DRAWER_ALPHA - slideOffset);
                }
            }
        };
        mDrawerLayout.setDrawerListener(mToggle);
        mToggle.syncState();
    }

    /**
     * This interface implementation handles the navigation drawer items click event
     * The footer logout button is handle in onLogoutClick
     * Position points to a fragment to display
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        Log.i("DrawerActivity", "onItemClick: " + position);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (position) {
            case Constants.FRAGMENT_ADD_RECIPE_POSITION :
                LetsCookApp.getInstance().trackEvent(AnalyticsConstants.EVENT_ADD_RECIPE,
                        AnalyticsConstants.ACTION_SCREEN, AnalyticsConstants.ADD_RECIPE_LABEL);
                DialogFragmentRecipeTitle.newInstance().show(
                        getSupportFragmentManager(),
                        getResources().getString(R.string.fragment_add_recipe));
                break;
            case Constants.FRAGMENT_RECIPE_PAGER_POSITION :
                if (NetworkUtils.isConnectionAvailable(this)) {
                    loadSuggestionRecipes();
                }
                progressDialog.show();
                break;
            case Constants.FRAGMENT_FAVOURITES_POSITION :
                setCurrentFragment(mDrawerFragments.get(position));
                setCurrentTag(mFragmentTags[position - 1]);
                displayFragment(currentFragment, currentTag);
                isFragmentFavourites = true;
                break;
            case Constants.FRAGMENT_HEADER_POSITION :
                Log.i(TAG, "onItemClick: header click");
//                displayFragment(FragmentHeader.newInstance(), getResources().getString(R.string.fragment_header));
                break;
            case Constants.FRAGMENT_EVENTS_POSITION :
                Log.i(TAG, "onItemClick: event fragment is calling");
                if (!isTwoPane) {
                    setCurrentFragment(mDrawerFragments.get(position));
                    setCurrentTag(mFragmentTags[position - 1]);
                    displayFragment(currentFragment, currentTag);
                } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    setCurrentFragment(mDrawerFragments.get(position));
                    setCurrentTag(mFragmentTags[position - 1]);
                    displayFragment(currentFragment, currentTag);
                } else {
                    setCurrentFragment(FragmentAddEvent.newInstance());
                    setCurrentTag(getResources().getString(R.string.fragment_add_event));
                    displayFragmentInDualMode(
                            FragmentEvents.newInstance(events),
                            getResources().getString(R.string.fragment_events),
                            currentFragment,
                            currentTag
                    );
                }
                break;
            default :
                Log.i(TAG, "onItemClick: show fragment: " + mDrawerFragments.get(position).toString() + " tag: " + mFragmentTags[position - 1]);
                setCurrentFragment(mDrawerFragments.get(position));
                setCurrentTag(mFragmentTags[position - 1]);
                displayFragment(currentFragment, currentTag);
        }

    }

    /**
     * This method put all the fragments of the navigation drawer in one sparse array
     * @return
     */
    @NonNull
    private SparseArray<Fragment> getDrawerFragments() {
        List<ItemRecipe> itemRecipes = ModelConverter.convertLocalRecipeToItemRecipe(recipesSQLite);
        mDrawerFragments = new SparseArray<>(Constants.DRAWER_ITEMS_SIZE);
//        mDrawerFragments.put(Constants.FRAGMENT_RECIPE_PAGER_POSITION, FragmentRecipesViewer.newInstance());
//        mDrawerFragments.put(Constants.FRAGMENT_RECIPE_PAGER_POSITION, FragmentCategories.newInstance());
//        mDrawerFragments.put(Constants.FRAGMENT_ADD_RECIPE_POSITION, FragmentAddRecipe.newInstance());
        mDrawerFragments.put(Constants.FRAGMENT_OTHER_CHEFS_POSITION, FragmentOtherChefs.newInstance());
        mDrawerFragments.put(Constants.FRAGMENT_FAVOURITES_POSITION, FragmentRecipes.newInstance((ArrayList<ItemRecipe>) itemRecipes, true));
        mDrawerFragments.put(Constants.FRAGMENT_EVENTS_POSITION, FragmentEvents.newInstance(events));
        return mDrawerFragments;
    }

    /**
     * Display de current fragment
     * @param currentFragment
     * @param currentFragmentTag
     */
    private void displayFragment(Fragment currentFragment, String currentFragmentTag) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container, currentFragment, currentFragmentTag).addToBackStack(currentFragmentTag).commit();
    }

    private void displayDetailFragmentPanelTwo(Fragment currentFragment, String currentFragmentTag) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container_detail, currentFragment, currentFragmentTag).addToBackStack(currentFragmentTag).commit();
    }



    private void displayFragmentInDualMode(Fragment main, String mainTag, Fragment detail, String detailTag) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container, main, mainTag).addToBackStack(mainTag).commit();
        fm.beginTransaction().replace(R.id.container_detail, detail, detailTag).addToBackStack(detailTag).commit();
    }

    /**
     * Handle click event of Logout in Navigation Drawer
     * @param view
     */
    @OnClick(R.id.tv_logout)
    protected void onLogoutClick(View view) {
        Log.i("DrawerActivity", "logout clicked: ");
        // TODO do logout
      finish();
    }

    /**
     * Set title of the Recipe takes the user input and pass its values int
     * a new fragment instance to display the fragment with the data loaded
     * @param title
     * @param category
     */
    @Override
    public void launchAddRecipeFragment(String title, String category) {
        if (!isTwoPane) {
            setCurrentFragment(FragmentAddRecipe.newInstance(title, category));
            setCurrentTag(getResources().getString(R.string.fragment_add_recipe));
            displayFragment(currentFragment, currentTag);
        } else {
            setCurrentFragment(FragmentCustomRecipes.newInstance(ModelConverter.convertCustomRecipeToItemRecipe(customRecipes)));
            setCurrentTag(getResources().getString(R.string.fragment_custom_recipes));
            displayFragmentInDualMode(
                    FragmentAddRecipe.newInstance(title, category),
                    getResources().getString(R.string.fragment_add_recipe),
                    currentFragment,
                    currentTag
            );
        }
    }

    /**
     * Display a fragment with a list of recipes depending on the category
     * @param v
     * @param position
     * @param category
     */
    @Override
    public void onCategoryItemClick(View v, int position, String category, int adapterId) { //Click in categories: display fragment recipes when click any item in fragment categories
        switch (adapterId) {
            case Constants.ADAPTER_CATEGORIES_ID :
                Log.i("MAIN", "onItemClick: adapter categories  " + String.valueOf(position));
                downloadRecipesByCategory(category);
                progressDialog.setMessage("Loading recipes");
                progressDialog.show();
                break;
            case Constants.ADAPTER_SUGGESTIONS_ID :
                if (category.equals(suggestionsOfTheDay.get(position - 1).getLabel())) { //is suggestion, is its a recipe--> detail
                    if (!isTwoPane) {
                        setCurrentFragment(FragmentRecipeDetail.newInstance(suggestionsOfTheDay.get(position - 1), Constants.FRAGMENT_PAGER));
                        setCurrentTag(getResources().getString(R.string.fragment_recipes_detail));
                        displayFragment(currentFragment , currentTag);
                    } else {
                        displayDetailFragmentPanelTwo(FragmentRecipeDetail.newInstance(suggestionsOfTheDay.get(position - 1), Constants.FRAGMENT_PAGER),
                                getResources().getString(R.string.fragment_recipes_detail));
                    }
                }
                break;
            case Constants.ADAPTER_CUSTOM_RECIPES_ID :
                if (!isTwoPane) {
                    setCurrentFragment(FragmentCustomRecipeDetail.newInstance(customRecipes.get(position - 1)));
                    setCurrentTag(getResources().getString(R.string.fragment_custom_recipe_detail));
                    displayFragment(currentFragment,
                            currentTag);
                } else {
                    displayDetailFragmentPanelTwo(FragmentCustomRecipeDetail.newInstance(customRecipes.get(position - 1)),
                            getResources().getString(R.string.fragment_custom_recipe_detail));
                }
                break;
        }

    }

    private void downloadRecipesByCategory(String category) {
        Log.i(TAG, "downloadRecipesByCategory: starting service");
        startService(LetsCookService.makeIntentWithCategory(getApplicationContext(), category));
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ActionConstants.ACTION_DOWNLOAD_RECIPES_RANDOM_SUCCESS);
        intentFilter.addAction(ActionConstants.ACTION_DOWNLOAD_RECIPES_BY_CATEGORY_SUCCESS);
        intentFilter.addAction(ActionConstants.ACTION_UPDATE_SQLITE_RECIPES);
        intentFilter.addAction(ActionConstants.ACTION_DOWNLOAD_GEOCODING_SUCCESS);
        intentFilter.addAction(ActionConstants.ACTION_UPLOAD_EVENT_SUCCESS);
        intentFilter.addAction(ActionConstants.ACTION_UPLOAD_CUSTOM_RECIPE_SUCCESS);
        intentFilter.addAction(ActionConstants.ACTION_DOWNLOAD_CUSTOM_RECIPE_IMAGES_SUCCESS);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
        initFacebookAppEventsLogger();


    }

    /**
     * Logs 'install' and 'app activate' App Events.
     * data reflected in your app's Insights dashboard.
     */
    private void initFacebookAppEventsLogger() {
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        stopFacebookEventLogger();
    }

    /**
     * Stop Facebook event logger
     */
    private void stopFacebookEventLogger() {
        AppEventsLogger.deactivateApp(this);
    }

    /**
     * Implements OnRecipeItemListener
     * Display fragment recipe detail
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Log.i(TAG, "onItemClick: position " + position);
        if (ofTheDay) {
            setCurrentFragment(FragmentRecipeDetail.newInstance(suggestionsOfTheDay.get(position),Constants.FRAGMENT_PAGER));
            setCurrentTag(getString(R.string.fragment_recipes_detail));
            displayFragment(currentFragment, currentTag);
        } else if (isFragmentFavourites) {
            setCurrentFragment(FragmentRecipeDetail.newInstance(recipesSQLite.get(position), Constants.FRAGMENT_FAVOURITES));
            setCurrentTag(getString(R.string.fragment_recipes_detail));
            displayFragment(currentFragment, currentTag);
        } else {
            setCurrentFragment(FragmentRecipeDetail.newInstance(recipes.get(position), Constants.FRAGMENT_PAGER));
            setCurrentTag(getString(R.string.fragment_recipes_detail));
            displayFragment(currentFragment, currentTag);
        }
    }

    @Override
    public void displayFragmentNutrients(RecipeLocal recipe) {
        setCurrentFragment(FragmentNutrients.newInstance(
                (ArrayList<NutrientLocal>) recipe.getNutrients()));
        setCurrentTag(getString(R.string.fragment_nutrients));
        displayFragment(currentFragment, currentTag);
    }

    public List<ItemRecipe>  getSuggestionRecipes() {
        return suggestions;
    }

    public void setSuggestionRecipes(List<ItemRecipe> suggestions) {
        this.suggestions = suggestions;
    }

    @Override
    public void onResponse(long rowId) {
        ofTheDay = false;
        FragmentRecipeDetail fragmentRecipeDetail = (FragmentRecipeDetail) getSupportFragmentManager()
                .findFragmentByTag(getResources().getString(R.string.fragment_recipes_detail));
        fragmentRecipeDetail.setRowId(rowId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.ROTATION_CURRENT_FRAGMENT_TAG, currentTag);
        getSupportFragmentManager().putFragment(outState, Constants.ROTATION_CURRENT_FRAGMENT, currentFragment);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Inner class to handle broadcast receivers (take it out if possible)
     */
    public class LetsCookReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case ActionConstants.ACTION_DOWNLOAD_RECIPES_BY_CATEGORY_SUCCESS :
                    Log.i(TAG, "onReceive: ACTION_DOWNLOAD_RECIPES_BY_CATEGORY_SUCCESS ");
                    ofTheDay = false;
                    recipes = intent.getParcelableArrayListExtra(Constants.EXTRA_RECIPES);
                    if (!isTwoPane) {
                        setCurrentFragment(FragmentRecipes.newInstance(ModelConverter.convertLocalRecipeToItemRecipe(recipes), false));
                        setCurrentTag(getResources().getString(R.string.fragment_recipes));
                        displayFragment(currentFragment, currentTag);
                    } else {
                        displayDetailFragmentPanelTwo(FragmentRecipes.newInstance(ModelConverter.convertLocalRecipeToItemRecipe(recipes), false),
                                getResources().getString(R.string.fragment_recipes));
                    }
                    // TODO start activity do show list of recipes
                    break;
                case ActionConstants.ACTION_DOWNLOAD_RECIPES_RANDOM_SUCCESS :
                    Log.i(TAG, "onReceive: ACTION_DOWNLOAD_RECIPES_RANDOM_SUCCESS ");
                    ofTheDay = true;
                    ToastUtils.showToast(ActivityDrawer.this, Constants.CHECK_RANDOM_RECIPES);
                    suggestionsOfTheDay = intent.getParcelableArrayListExtra(Constants.EXTRA_RECIPES_RANDOM);
                    setCurrentFragment(FragmentRecipesViewer.newInstance(
                            ModelConverter.convertLocalRecipeToItemRecipe(suggestionsOfTheDay),
                            ModelConverter.convertCustomRecipeToItemRecipe(customRecipes)));
                    setCurrentTag(mFragmentTags[0]);
                    displayFragment(currentFragment, currentTag);
                    break;
                case ActionConstants.ACTION_UPDATE_SQLITE_RECIPES :
                    recipesSQLite = intent.getParcelableArrayListExtra(Constants.EXTRA_SQLITE_RECIPES);
                    FragmentRecipes fragmentRecipes = (FragmentRecipes) getSupportFragmentManager()
                            .findFragmentByTag(getResources().getString(R.string.fragment_favourites));
                    fragmentRecipes.setRecipes(ModelConverter.convertLocalRecipeToItemRecipe(recipesSQLite));
                    fragmentRecipes.updateRecipes(ModelConverter.convertLocalRecipeToItemRecipe(recipesSQLite));
                    fragmentRecipes.stopSwipeRefresh();
                    break;
                case ActionConstants.ACTION_DOWNLOAD_GEOCODING_SUCCESS :
                    List<GeocodingLatLng> locations = intent.getParcelableArrayListExtra(Constants.EXTRA_GEOCODING);
                    FragmentAddEvent fragmentAddEvent = (FragmentAddEvent) getSupportFragmentManager()
                            .findFragmentByTag(getResources().getString(R.string.fragment_add_event));
                    if (locations != null && !locations.isEmpty()) {
                        fragmentAddEvent.showMarkerWithLocations(locations);
                    } else {
                        ToastUtils.showToast(getApplicationContext(), "There is no address for your search");
                    }
                    break;
                case ActionConstants.ACTION_UPLOAD_EVENT_SUCCESS :
                    FragmentEvents fragmentEvents = (FragmentEvents) getSupportFragmentManager()
                            .findFragmentByTag(getResources().getString(R.string.fragment_events));
                    fragmentEvents.updateEvents((Event) intent.getParcelableExtra(Constants.EXTRA_EVENT));
                    break;
                case ActionConstants.ACTION_UPLOAD_CUSTOM_RECIPE_SUCCESS :
                    // TODO update custom recipes list
                    Log.i(TAG, "onReceive: custom recipe saved");
                    ToastUtils.showToast(ActivityDrawer.this, "Recipe saved!");
                    CustomRecipe customRecipe = intent.getParcelableExtra(Constants.EXTRA_CUSTOM_RECIPE);
                    customRecipes.add(customRecipe);
                    FragmentRecipesViewer fragmentRecipesViewer = (FragmentRecipesViewer) getSupportFragmentManager()
                            .findFragmentByTag(getResources().getString(R.string.fragment_recipes_viewer));
                    if (fragmentRecipesViewer != null) {
                        fragmentRecipesViewer.updateCustomRecipesInFragment(customRecipes);
                    }
                    if (isTwoPane) {
                        FragmentCustomRecipes fragmentCustomRecipes = (FragmentCustomRecipes) getSupportFragmentManager()
                                .findFragmentByTag(getResources().getString(R.string.fragment_custom_recipes));
                        fragmentCustomRecipes.updateSuggestions(ModelConverter.convertCustomRecipeToItemRecipe(customRecipes));
                    }
                    // TODO update list in fragment and in adapter
                    break;
                case ActionConstants.ACTION_DOWNLOAD_CUSTOM_RECIPE_IMAGES_SUCCESS: // This can be a race with random recipes
//                    ArrayList<String> imagesPaths = intent.getStringArrayListExtra(Constants.EXTRA_PATH_IMAGES);
//                    for (CustomRecipe recipe : customRecipes) {
//                        for (String path : imagesPaths) {
//                            if (recipe.getTitle().equals(path)) {
//                                recipe.setImagePath(path);
//                            }
//                        }
//                    }
                    break;
            }
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }


    }


}
