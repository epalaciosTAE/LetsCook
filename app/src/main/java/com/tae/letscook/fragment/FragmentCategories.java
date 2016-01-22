package com.tae.letscook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.tae.letscook.R;
import com.tae.letscook.Utils.Animations;
import com.tae.letscook.adapter.AdapterCategoriesMaterial;
import com.tae.letscook.app.LetsCookApp;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.listeners.HidingScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eduardo on 30/12/2015.
 */
public class FragmentCategories extends Fragment {

    private static final String TAG = FragmentCategories.class.getSimpleName();
    @Bind(R.id.recycler_view) protected RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private AdapterCategoriesMaterial adapterCategoriesMaterial;
//    @Bind(R.id.toolbar) protected Toolbar mToolbar;
    private Toolbar mToolbar;
    private List<Animation> animations;

    public static FragmentCategories newInstance () {
        return new FragmentCategories();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getActivity().getString(R.string.fragment_categories_title));
        Log.i(TAG, "onCreate: ");
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        loadAnimations();
        LetsCookApp.getInstance().trackScreenView(getResources().getString(R.string.fragment_categories_title));
    }

    private void loadAnimations() {
        animations = new ArrayList<>(2);
        animations.add(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in));
        animations.add(AnimationUtils.loadAnimation(getActivity(),R.anim.fade_out));
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
        adapterCategoriesMaterial = new AdapterCategoriesMaterial(getActivity());
        mAdapter = new RecyclerViewMaterialAdapter(adapterCategoriesMaterial);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                Animations.hideViews(mToolbar);
                mToolbar.startAnimation(animations.get(Constants.FADE_OUT));
//                mToolbar.setVisibility(View.GONE);
            }

            @Override
            public void onShow() {
                Animations.showViews(mToolbar);
                mToolbar.startAnimation(animations.get(Constants.FADE_IN));
            }
        });
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, null);
    }

    public void swipeBigCard() {
        adapterCategoriesMaterial.reloadHeader();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        Log.i(TAG, "onDestroyView: ");
    }

    @Override
    public void onPause() {
        super.onPause();
//        mToolbar.setVisibility(View.VISIBLE);
    }
}
