package com.tae.letscook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tae.letscook.R;

/**
 * Created by Eduardo on 30/12/2015.
 */
public class FragmentOtherChefs extends Fragment {

    public static FragmentOtherChefs newInstance () {
        return new FragmentOtherChefs();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_chefs, container, false);
        return view;
    }
}
