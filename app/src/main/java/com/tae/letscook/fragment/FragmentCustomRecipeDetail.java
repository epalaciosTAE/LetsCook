package com.tae.letscook.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tae.letscook.R;
import com.tae.letscook.adapter.AdapterIngredients;
import com.tae.letscook.adapter.AdapterOneItem;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.model.CustomRecipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eduardo on 26/01/2016.
 */
public class FragmentCustomRecipeDetail extends Fragment {

    @Bind(R.id.img_custom_recipe_detail) protected ImageView imgRecipe;
//    @Bind(R.id.fab__custom_recipe_detail) protected FloatingActionButton fabFavourites;
    @Bind(R.id.tv_custom_recipe_budget) protected TextView tvBudget;
    @Bind(R.id.tv_custom_recipe_time) protected TextView tvTime;
    @Bind(R.id.tv_custom_recipe_people) protected TextView tvPeople;
    @Bind(R.id.tv_custom_recipe_level) protected TextView tvLevel;
    @Bind(R.id.tv_custom_recipe_detail_title) protected TextView tvTitle;
    @Bind(R.id.recycler_view) protected RecyclerView recyclerView;

    public static FragmentCustomRecipeDetail newInstance(CustomRecipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_CUSTOM_RECIPE, recipe);
        FragmentCustomRecipeDetail fragment = new FragmentCustomRecipeDetail();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_recipe_detail, container, false);
        ButterKnife.bind(this, view);
        CustomRecipe recipe = (CustomRecipe) getArguments().get(Constants.EXTRA_CUSTOM_RECIPE);
        if (recipe.getImagePath() != null) {
            imgRecipe.setImageURI(Uri.parse(recipe.getImagePath()));
        }
        tvTitle.setText(recipe.getTitle());
        tvLevel.setText(recipe.getLevel());
        tvPeople.setText(recipe.getPeople());
        tvTime.setText(recipe.getTime());
        tvBudget.setText(recipe.getBudget());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        AdapterIngredients adapter = new AdapterIngredients(getActivity(), recipe.getIngredients());
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
