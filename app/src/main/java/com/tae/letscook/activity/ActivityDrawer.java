package com.tae.letscook.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import com.github.florent37.materialviewpager.Utils;
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
import com.tae.letscook.fragment.FragmentAddRecipe;
import com.tae.letscook.fragment.FragmentEvents;
import com.tae.letscook.fragment.FragmentFavourites;
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
import com.tae.letscook.model.Item;
import com.tae.letscook.model.ItemRecipe;
import com.tae.letscook.model.NutrientLocal;
import com.tae.letscook.model.RecipeLocal;
import com.tae.letscook.service.LetsCookService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityDrawer extends AppCompatActivity
        implements OnItemClickListener, OnRecipeTitleListener, OnCategoryItemListener, OnRecipeItemListener, OnNutrientsListener{

    private static final String TAG = ActivityDrawer.class.getSimpleName();
    @Bind(R.id.toolbar) protected Toolbar mToolbar;
    @Bind(R.id.drawer_layout) protected DrawerLayout mDrawerLayout;
    @Bind(R.id.rv_navigation_drawer) protected RecyclerView mRecyclerView;
    @Bind(R.id.tv_logout) protected TextView tvLogout;
    private SparseArray<Fragment> mDrawerFragments;
    private String[] mFragmentTags;
    private LetsCookReceiver receiver;
    private ProgressDialog progressDialog;
    private List<RecipeLocal>recipes, recipesOfTheDay;
    private List<ItemRecipe> suggestions;
    private boolean ofTheDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setRecyclerView();
        setDrawerToggle();
        setTvLogoutRipple();
        hasStoragePermission();
        initLetsCookReceiver();
        initProgressDialog();
        mToolbar.setNavigationIcon(android.R.drawable.star_big_on); // this changes the burguer

        mDrawerFragments = getDrawerFragments(); //drawer fragments
        mFragmentTags = getResources().getStringArray(R.array.nav_drawer_fragment_tags);


    }

    private void loadSuggestionRecipes() {
        startService(LetsCookService.makeIntentHomeView(ActivityDrawer.this));
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
     * Init the RecyclerView
     * Set the Adapter
     */
    private void setRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        List<Item> menuItems = generateDrawerItems();
        AdapterDrawer mAdapterDrawer = new AdapterDrawer(this, menuItems);
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
     * @param adapterId
     */
    @Override
    public void onItemClick(View view, int position, int adapterId) {
        Log.i("DrawerActivity", "onItemClick: " + position);
        if (adapterId == Constants.ADAPTER_DRAWER_ID) { //display fragment in drawer
            mDrawerLayout.closeDrawer(GravityCompat.START);
            if (position == Constants.FRAGMENT_ADD_RECIPE_POSITION) { // before display add recipe fragment display dialog to get title
                LetsCookApp.getInstance().trackEvent(AnalyticsConstants.EVENT_ADD_RECIPE, AnalyticsConstants.ACTION_SCREEN, AnalyticsConstants.ADD_RECIPE_LABEL);
                DialogFragmentRecipeTitle.newInstance().show(
                        getSupportFragmentManager(),
                        getResources().getString(R.string.fragment_add_recipe));
            } else if (position == Constants.FRAGMENT_RECIPE_PAGER_POSITION) {
                if (NetworkUtils.isConnectionAvailable(this)) {
                    loadSuggestionRecipes();
                }
                progressDialog.show();
            } else{
                Log.i(TAG, "onItemClick: show fragment: " + mDrawerFragments.get(position).toString() + " tag: " + mFragmentTags[position -1]);
                displayFragment(mDrawerFragments.get(position), mFragmentTags[position -1]);
            }
        }
    }

    /**
     * This method put all the fragments of the navigation drawer in one sparse array
     * @return
     */
    @NonNull
    private SparseArray<Fragment> getDrawerFragments() {
        mDrawerFragments = new SparseArray<>(Constants.DRAWER_ITEMS_SIZE);
//        mDrawerFragments.put(Constants.FRAGMENT_RECIPE_PAGER_POSITION, FragmentRecipesViewer.newInstance());
//        mDrawerFragments.put(Constants.FRAGMENT_RECIPE_PAGER_POSITION, FragmentCategories.newInstance());
//        mDrawerFragments.put(Constants.FRAGMENT_ADD_RECIPE_POSITION, FragmentAddRecipe.newInstance());
        mDrawerFragments.put(Constants.FRAGMENT_OTHER_CHEFS_POSITION, FragmentOtherChefs.newInstance());
        mDrawerFragments.put(Constants.FRAGMENT_FAVOURITES_POSITION, FragmentFavourites.newInstance());
        mDrawerFragments.put(Constants.FRAGMENT_EVENTS_POSITION, FragmentEvents.newInstance());
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

    /**
     * Handle click event of Logout in Navigation Drawer
     * @param view
     */
    @OnClick(R.id.tv_logout)
    protected void onLogoutClick(View view) {
        Log.i("DrawerActivity", "logout clicked: ");
        // TODO do logout
    }

    /**
     * Set title of the Recipe takes the user input and pass its values int
     * a new fragment instance to display the fragment with the data loaded
     * @param title
     * @param category
     */
    @Override
    public void showRecipes(String title, String category) {
        displayFragment(FragmentAddRecipe.newInstance(title, category), getResources().getString(R.string.fragment_add_recipe));
    }

    /**
     * Display a fragment with a list of recipes depending on the category
     * @param v
     * @param position
     * @param category
     */
    @Override
    public void onCategoryItemClick(View v, int position, String category, boolean isCategory) { //Click in categories: display fragment recipes when click any item in fragment categories
        if (isCategory) {
            Log.i("MAIN", "onItemClick: adapter categories  " + String.valueOf(position));
            downloadRecipesByCategory(category);
            progressDialog.setMessage("Loading recipes");
            progressDialog.show();
        } else if (category.equals(recipesOfTheDay.get(position - 1).getLabel())) { //is suggestion, is its a recipe--> detail
            displayFragment(FragmentRecipeDetail.newInstance(recipesOfTheDay.get(position - 1)), getResources().getString(R.string.fragment_recipes_detail));
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
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);


    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
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
            displayFragment(FragmentRecipeDetail.newInstance(recipesOfTheDay.get(position)),getString(R.string.fragment_recipe_detail));
        } else {
            displayFragment(FragmentRecipeDetail.newInstance(recipes.get(position)),getString(R.string.fragment_recipe_detail));
        }
    }

    @Override
    public void displayFragmentNutrients(RecipeLocal recipe) {
        displayFragment(FragmentNutrients.newInstance(
                        (ArrayList<NutrientLocal>) recipe.getNutrients()),
                getString(R.string.fragment_nutrients));
    }

    public List<ItemRecipe>  getSuggestionRecipes() {
        return suggestions;
    }

    public void setSuggestionRecipes(List<ItemRecipe> suggestions) {
        this.suggestions = suggestions;
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
                    displayFragment(FragmentRecipes.newInstance(ModelConverter.convertLocalRecipeToItemRecipe(recipes),false),
                            getResources().getString(R.string.fragment_recipes));
                    // TODO start activity do show list of recipes
                    break;
                case ActionConstants.ACTION_DOWNLOAD_RECIPES_RANDOM_SUCCESS :
                    Log.i(TAG, "onReceive: ACTION_DOWNLOAD_RECIPES_RANDOM_SUCCESS ");
                    ofTheDay = true;
                    ToastUtils.showToast(ActivityDrawer.this, Constants.CHECK_RANDOM_RECIPES);
                    recipesOfTheDay = intent.getParcelableArrayListExtra(Constants.EXTRA_RECIPES_RANDOM);
                    // FAKE LIST FOR TEST
//                    List<ItemRecipe> test = new ArrayList<>(1);
//                    for (int i = 0; i < 10; i++) {
//                        test.add(new ItemRecipe("dfdsdfsdfsd", "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg"));
//                    }
                    displayFragment(FragmentRecipesViewer.newInstance(ModelConverter.convertLocalRecipeToItemRecipe(recipesOfTheDay)), mFragmentTags[0]);
                    break;
            }
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }


    }
}
