package com.tae.letscook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.tae.letscook.R;
import com.tae.letscook.Utils.ModelConverter;
import com.tae.letscook.adapter.AdapterRecipesViewer;
import com.tae.letscook.app.LetsCookApp;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.model.CustomRecipe;
import com.tae.letscook.model.ItemRecipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eduardo on 18/01/2016.
 */
public class FragmentRecipesViewer extends Fragment {

    private static final String TAG = FragmentRecipesViewer.class.getSimpleName();
    @Bind(R.id.materialViewPager)
    protected MaterialViewPager materialViewPager;
    private AdapterRecipesViewer adapter;

    public static FragmentRecipesViewer newInstance (ArrayList<ItemRecipe> suggestions, ArrayList<ItemRecipe> customRecipes) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.EXTRA_SUGGESTIONS, suggestions);
        bundle.putParcelableArrayList(Constants.EXTRA_CUSTOM_RECIPES, customRecipes);
        FragmentRecipesViewer fragment = new FragmentRecipesViewer();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        setRetainInstance(true);
        LetsCookApp.getInstance().trackScreenView(getResources().getString(R.string.fragment_recipes_viewer));
    }

    //    public void updateSuggestions(List<ItemRecipe> suggestionRecipes) {
//        adapter.updateSuggestions(suggestionRecipes);
    public void updateCustomRecipesInFragment(List<CustomRecipe> customRecipes) {
        FragmentCustomRecipes fragment = (FragmentCustomRecipes) getFragmentFromChildFragmentManager(true);
        fragment.updateSuggestions(ModelConverter.convertCustomRecipeToItemRecipe(customRecipes));
    }

    private Fragment getFragmentFromChildFragmentManager(boolean isFragmentCustomRecipes) {
        FragmentCategories fragmentCat = null;
        FragmentSuggestions fragmentSuggestions = null;
        FragmentCustomRecipes fragmentCustomRecipes = null;
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            if (fragment instanceof FragmentCategories) {
                fragmentCat = (FragmentCategories) fragment;
            } else if (fragment instanceof FragmentSuggestions) {
                fragmentSuggestions = (FragmentSuggestions) fragment;
            } else {
                fragmentCustomRecipes = (FragmentCustomRecipes) fragment;
            }
        }
        if (isFragmentCustomRecipes) {
            return fragmentCustomRecipes;
        } else {
            return fragmentSuggestions;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_viewer, container, false);
        ButterKnife.bind(this, view);
        adapter = new AdapterRecipesViewer(
                getChildFragmentManager(),
                (List<ItemRecipe>) getArguments().get(Constants.EXTRA_SUGGESTIONS),
                (List<ItemRecipe>) getArguments().get(Constants.EXTRA_CUSTOM_RECIPES));
        materialViewPager.getViewPager().setAdapter(adapter);
        materialViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {

                switch (page) {
                    case 0:
//                        FragmentCategories fragmentCategories = (FragmentCategories) getFragmentFromChildFragmentManager(true);
//                        fragmentCategories.swipeBigCard();
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");
                    case 1:
//                        FragmentSuggestions fragmentSuggestions = (FragmentSuggestions) getFragmentFromChildFragmentManager(false);
//                        fragmentSuggestions.updateSuggestions();
//                        fragmentSuggestions.swipeBigCard();
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });



        materialViewPager.getViewPager().setOffscreenPageLimit(materialViewPager.getViewPager().getAdapter().getCount());
        materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());
        return view;
    }


//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: ");
        ButterKnife.unbind(this);
    }
}
