package com.tae.letscook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.tae.letscook.R;
import com.tae.letscook.adapter.AdapterCustomRecipesMaterial;
import com.tae.letscook.app.LetsCookApp;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.model.ItemRecipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eduardo on 26/01/2016.
 */
public class FragmentCustomRecipes extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private AdapterCustomRecipesMaterial adapterSuggestionsMaterial;

    @Bind(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    public static FragmentCustomRecipes newInstance(ArrayList<ItemRecipe> customRecipes) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.EXTRA_CUSTOM_RECIPES, customRecipes);
        FragmentCustomRecipes fragment = new FragmentCustomRecipes();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LetsCookApp.getInstance().trackScreenView(getResources().getString(R.string.fragment_custom_recipes));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view , container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        adapterSuggestionsMaterial = new AdapterCustomRecipesMaterial(getActivity(),
                (List<ItemRecipe>) getArguments().get(Constants.EXTRA_CUSTOM_RECIPES));
        mAdapter = new RecyclerViewMaterialAdapter(adapterSuggestionsMaterial);
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);


    }

    public void swipeBigCard() {
        adapterSuggestionsMaterial.reloadHeader();
    }

    public void updateSuggestions(List<ItemRecipe> suggestions) {
        adapterSuggestionsMaterial.updateSuggestions(suggestions);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
