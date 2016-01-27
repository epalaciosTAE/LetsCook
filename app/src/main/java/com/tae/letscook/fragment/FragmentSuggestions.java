package com.tae.letscook.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.tae.letscook.R;
import com.tae.letscook.activity.ActivityDrawer;
import com.tae.letscook.adapter.AdapterCategoriesMaterial;
import com.tae.letscook.adapter.AdapterSuggestionsMaterial;
import com.tae.letscook.app.LetsCookApp;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.model.ItemRecipe;
import com.tae.letscook.model.RecipeLocal;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class FragmentSuggestions extends Fragment {

//    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private AdapterSuggestionsMaterial adapterSuggestionsMaterial;
//    @Bind(R.id.swipe_container)
//    protected SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recycler_view)
    protected RecyclerView mRecyclerView;
//    private List<ItemRecipe>suggestions;

    public static FragmentSuggestions newInstance(ArrayList<ItemRecipe> suggestions) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.EXTRA_SUGGESTIONS, suggestions);
        FragmentSuggestions fragment = new FragmentSuggestions();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LetsCookApp.getInstance().trackScreenView(getResources().getString(R.string.fragment_suggestions));
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
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        adapterSuggestionsMaterial = new AdapterSuggestionsMaterial(getActivity(),
                (List<ItemRecipe>) getArguments().get(Constants.EXTRA_SUGGESTIONS));
        mAdapter = new RecyclerViewMaterialAdapter(adapterSuggestionsMaterial);
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
//        updateSuggestions(((ActivityDrawer) getActivity()).getSuggestionRecipes());
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setColorSchemeResources(
//                android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);

    }

    public void swipeBigCard() {
        adapterSuggestionsMaterial.reloadHeader();
    }

    public void updateSuggestions(List<ItemRecipe> suggestions) {
        adapterSuggestionsMaterial.updateSuggestions(suggestions);
    }

//    @Override
//    public void onRefresh() {
//        swipeRefreshLayout.setRefreshing(true);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                updateSuggestions(((ActivityDrawer) getActivity()).getSuggestionRecipes());
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        }, 5000);
//
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
