package com.tae.letscook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.tae.letscook.R;
import com.tae.letscook.adapter.AdapterCategoriesMaterial;
import com.tae.letscook.adapter.AdapterRecipes;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.model.ItemRecipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eduardo on 30/12/2015.
 */
public class FragmentRecipes extends Fragment {

    @Bind(R.id.recycler_view) protected RecyclerView recyclerView;
    List<ItemRecipe> recipes;

//    public static FragmentRecipes newInstance () {
//        return new FragmentRecipes();
//    }

    public static FragmentRecipes newInstance (ArrayList<ItemRecipe> recipes, boolean title) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.EXTRA_ITEM_RECIPES, recipes);
        bundle.putBoolean(Constants.EXTRA_TITLE, title);
        FragmentRecipes fragment = new FragmentRecipes();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipes = (List<ItemRecipe>) getArguments().get(Constants.EXTRA_ITEM_RECIPES);
        boolean withTitle = (boolean) getArguments().get(Constants.EXTRA_TITLE);
//        if (withTitle) {
//            getActivity().setTitle(getActivity().getString(R.string.recipes_of_the_day));
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        AdapterRecipes adapter = new AdapterRecipes(recipes, getActivity()); // TODO pass the list to adapter
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
}
