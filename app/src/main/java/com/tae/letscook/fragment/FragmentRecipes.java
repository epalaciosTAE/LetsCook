package com.tae.letscook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.tae.letscook.R;
import com.tae.letscook.Utils.ToastUtils;
import com.tae.letscook.activity.ActivitySplash;
import com.tae.letscook.adapter.AdapterCategoriesMaterial;
import com.tae.letscook.adapter.AdapterRecipes;
import com.tae.letscook.app.LetsCookApp;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.listeners.OnRecipesLoadedListener;
import com.tae.letscook.manager.DaoManager;
import com.tae.letscook.manager.RecipesManager;
import com.tae.letscook.model.ItemRecipe;
import com.tae.letscook.service.LoadRecipesSQLiteTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eduardo on 30/12/2015.
 */
public class FragmentRecipes extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnRecipesLoadedListener {

    @Bind(R.id.recycler_view) protected RecyclerView recyclerView;
    @Bind(R.id.swipe_container) protected SwipeRefreshLayout swipeRefreshLayout;
    private List<ItemRecipe> recipes;
    private AdapterRecipes adapter;

//    public static FragmentRecipes newInstance () {
//        return new FragmentRecipes();
//    }

    public static FragmentRecipes newInstance (ArrayList<ItemRecipe> recipes, boolean isFragmentFavs) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.EXTRA_ITEM_RECIPES, recipes);
        bundle.putBoolean(Constants.EXTRA_TITLE, isFragmentFavs);
        FragmentRecipes fragment = new FragmentRecipes();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LetsCookApp.getInstance().trackScreenView(getResources().getString(R.string.fragment_recipes));
        recipes = (List<ItemRecipe>) getArguments().get(Constants.EXTRA_ITEM_RECIPES);
//        boolean withTitle = (boolean) getArguments().get(Constants.EXTRA_TITLE);
//        if (withTitle) {
//            getActivity().setTitle(getActivity().getString(R.string.recipes_of_the_day));
//        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestions, container, false);
        ButterKnife.bind(this, view);
        if ((boolean) getArguments().get(Constants.EXTRA_TITLE)) {
//            swipeRefreshLayout.setOnRefreshListener(this);
//            final SwipeRefreshLayout s = new SwipeRefreshLayout(getActivity());
        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.post(new Runnable() {
//                @Override
//                public void run() {
//                    swipeRefreshLayout.setRefreshing(true);
//                }
//            });
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new AdapterRecipes(recipes, getActivity()); // TODO pass the list to adapter
        Log.i("Fragment REcipes", "onCreateView: recipes size " + recipes.size());
//        AdapterCategoriesMaterial adapter = new AdapterCategoriesMaterial(getActivity());
        recyclerView.setAdapter(adapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, null);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        ToastUtils.showToast(getActivity(), "On refresh");
        new LoadRecipesSQLiteTask(this, getActivity()).execute();
    }

    @Override
    public void recipesLoaded(List<ItemRecipe> recipes) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        adapter.update(recipes);
    }
}
