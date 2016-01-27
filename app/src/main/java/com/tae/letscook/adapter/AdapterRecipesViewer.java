 package com.tae.letscook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.tae.letscook.constants.Constants;
import com.tae.letscook.fragment.FragmentCategories;
import com.tae.letscook.fragment.FragmentCustomRecipes;
import com.tae.letscook.fragment.FragmentSuggestions;
import com.tae.letscook.model.ItemRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 18/01/2016.
 */
public class AdapterRecipesViewer extends FragmentPagerAdapter {

    private static final String TAG = "Material View Adapter";
    private static final int COUNT = 3;
    private List<ItemRecipe> suggestions, customRecipes;

    public AdapterRecipesViewer(FragmentManager fm, List<ItemRecipe> suggestions, List<ItemRecipe> customRecipes) {
        super(fm);
//        fragmentSuggestions = FragmentSuggestions.newInstance();
        this.suggestions = suggestions;
        this.customRecipes = customRecipes;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return FragmentCategories.newInstance();
        } else if (position == 1) {
            return FragmentSuggestions.newInstance((ArrayList<ItemRecipe>) suggestions);
        } else {
            return FragmentCustomRecipes.newInstance((ArrayList<ItemRecipe>) customRecipes);
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return Constants.VIEW_PAGER_WORLD_FOOD;
        } else if (position == 1) {
            return Constants.VIEW_PAGER_SUGGESTIONS;
        } else {
            return Constants.VIEW_PAGER_CUSTOM_RECIPES;
        }
    }

//    public void updateSuggestions(List<ItemRecipe> suggestionRecipes) {
//        fragmentSuggestions.updateSuggestions(suggestionRecipes);
//    }

}
