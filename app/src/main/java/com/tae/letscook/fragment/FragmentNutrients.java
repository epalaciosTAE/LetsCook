package com.tae.letscook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tae.letscook.R;
import com.tae.letscook.activity.ActivityDrawer;
import com.tae.letscook.adapter.AdapterNutrients;
import com.tae.letscook.app.LetsCookApp;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.model.NutrientLocal;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eduardo on 17/01/2016.
 */
public class FragmentNutrients extends Fragment {

    @Bind(R.id.recycler_view)
    protected RecyclerView recyclerView;
//    private List<NutrientLocal> nutriens;

    public static FragmentNutrients newInstance(ArrayList<NutrientLocal> nutrients) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.EXTRA_NUTRIENTS, nutrients);
        FragmentNutrients fragmentNutrients = new FragmentNutrients();
        fragmentNutrients.setArguments(bundle);
        return fragmentNutrients;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LetsCookApp.getInstance().trackScreenView(getResources().getString(R.string.fragment_nutrients));
//        getActivity().setTitle(getActivity().getString(R.string.fragment_nutrients_title));
//        nutriens = (List<NutrientLocal>) getArguments().get(Constants.EXTRA_NUTRIENTS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        ButterKnife.bind(this,view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new AdapterNutrients(getActivity(),
                (List<NutrientLocal>) getArguments().get(Constants.EXTRA_NUTRIENTS)));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
