package com.tae.letscook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tae.letscook.R;
import com.tae.letscook.adapter.AdapterRecipes;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.model.ItemRecipe;
import com.tae.letscook.model.RecipeLocal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eduardo on 30/12/2015.
 */
public class FragmentRecipes extends Fragment {

    @Bind(R.id.recycler_view) protected RecyclerView recyclerView;
    List<ItemRecipe> recipes;

    public static FragmentRecipes newInstance (ArrayList<ItemRecipe> recipes) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.EXTRA_ITEM_RECIPES, recipes);
        FragmentRecipes fragment = new FragmentRecipes();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipes = (List<ItemRecipe>) getArguments().get(Constants.EXTRA_ITEM_RECIPES);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        AdapterRecipes adapter = new AdapterRecipes(recipes, getActivity()); // TODO pass the list to adapter
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
