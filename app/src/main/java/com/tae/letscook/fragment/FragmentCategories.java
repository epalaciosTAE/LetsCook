package com.tae.letscook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.tae.letscook.R;
import com.tae.letscook.adapter.AdapterCategories;
import com.tae.letscook.adapter.AdapterRecipesMaterial;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eduardo on 30/12/2015.
 */
public class FragmentCategories extends Fragment {

    private static final String TAG = FragmentCategories.class.getSimpleName();
    @Bind(R.id.recycler_view) protected RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private AdapterRecipesMaterial adapterRecipesMaterial;

    public static FragmentCategories newInstance () {
        return new FragmentCategories();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getActivity().getString(R.string.fragment_categories_title));
        Log.i(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        Log.i(TAG, "onCreateView: ");
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        adapterRecipesMaterial = new AdapterRecipesMaterial(getActivity());
        mAdapter = new RecyclerViewMaterialAdapter(adapterRecipesMaterial);
        recyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, null);
    }

    public void swipeBigCard() {
        adapterRecipesMaterial.reloadHeader();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        Log.i(TAG, "onDestroyView: ");
    }
}
