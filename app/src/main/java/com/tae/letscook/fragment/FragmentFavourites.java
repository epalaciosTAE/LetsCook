package com.tae.letscook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tae.letscook.R;
import com.tae.letscook.manager.DaoManager;
import com.tae.letscook.manager.RecipesManager;
import com.tae.letscook.model.RecipeLocal;

import java.util.List;

/**
 * Created by Eduardo on 30/12/2015.
 */
public class FragmentFavourites extends Fragment {

    public static FragmentFavourites newInstance () {
        return new FragmentFavourites();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<RecipeLocal> recipeLocals = RecipesManager.getInstance(getActivity()).getRecipesFromSQLite();
        Log.i("FAVOUTRITES", "onCreate: " + recipeLocals.size());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        return view;
    }
}
