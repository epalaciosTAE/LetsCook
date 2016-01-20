package com.tae.letscook.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tae.letscook.R;
import com.tae.letscook.adapter.AdapterOneItem;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.listeners.OnNutrientsListener;
import com.tae.letscook.model.RecipeLocal;
import com.tae.letscook.service.SaveRecipeToFavsTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Eduardo on 16/01/2016.
 */
public class FragmentRecipeDetail extends Fragment implements RatingBar.OnRatingBarChangeListener{

    private static final String TAG = SaveRecipeToFavsTask.class.getSimpleName();
    @Bind(R.id.tv_recipe_detail_title) protected TextView tvTitle;
    @Bind(R.id.img_recipe_detail) protected ImageView img;
    @Bind(R.id.img_recipe_detail_kcal) protected ImageView imgKcal;
    @Bind(R.id.img_recipe_detail_protein) protected ImageView imgProtein;
    @Bind(R.id.img_recipe_detail_fibre) protected ImageView imgFiber;
    @Bind(R.id.img_recipe_detail_carb) protected ImageView imgCarb;
    @Bind(R.id.img_recipe_detail_sodium) protected ImageView imgSodium;
    @Bind(R.id.img_recipe_detail_fat) protected ImageView imgFat;
    @Bind(R.id.img_recipe_detail_balance) protected ImageView imgBalance;
    @Bind(R.id.recycler_view) protected RecyclerView recyclerView;
    @Bind(R.id.rb_recipe_detail) protected RatingBar rbFavourite;
//    private ProgressDialog progressDialog;
    private OnNutrientsListener onNutrientsListener;
    private RecipeLocal recipe;


    public static FragmentRecipeDetail newInstance (RecipeLocal recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_RECIPES, recipe);
        FragmentRecipeDetail fragment = new FragmentRecipeDetail();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipe = (RecipeLocal) getArguments().get(Constants.EXTRA_RECIPES);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);
        rbFavourite.setOnRatingBarChangeListener(this);
        onNutrientsListener = (OnNutrientsListener) getActivity();
        tvTitle.setText(recipe.getLabel());
        Picasso.with(getActivity()).load(recipe.getImageUrl()).resize(600, 300).centerCrop().into(img);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new AdapterOneItem(getActivity(), recipe.getIngredientLines()));
        Picasso.with(getActivity()).load(R.drawable.ic_kcal).into(imgKcal);
        displayDietIcons();
        return view;
    }

    private void displayDietIcons() {
        for (String label : recipe.getDietLabels()) {
            if (label != null && !label.isEmpty()) {
                switch (label) {
                    case Constants.PROTEIN:
                        imgProtein.setVisibility(View.VISIBLE);
                        Picasso.with(getActivity()).load(R.drawable.ic_protein).into(imgProtein);
                        break;
                    case Constants.FIBER:
                        imgFiber.setVisibility(View.VISIBLE);
                        Picasso.with(getActivity()).load(R.drawable.ic_fiber).into(imgFiber);
                        break;
                    case Constants.CARB:
                        imgCarb.setVisibility(View.VISIBLE);
                        Picasso.with(getActivity()).load(R.drawable.ic_carbs).into(imgCarb);
                        break;
                    case Constants.SODIUM:
                        imgSodium.setVisibility(View.VISIBLE);
                        Picasso.with(getActivity()).load(R.drawable.ic_sodium).into(imgSodium);
                        break;
                    case Constants.FAT:
                        imgFat.setVisibility(View.VISIBLE);
                        Picasso.with(getActivity()).load(R.drawable.ic_fat).into(imgFat);
                        break;
                    case Constants.BALANCED:
                        imgBalance.setVisibility(View.VISIBLE);
                        Picasso.with(getActivity()).load(R.drawable.ic_balance).into(imgBalance);
                        break;
                }
            }
        }
    }

    @OnClick({R.id.btn_recipe_detail_nutrients,
            R.id.img_recipe_detail_protein,
            R.id.img_recipe_detail_carb,
            R.id.img_recipe_detail_fibre,
            R.id.img_recipe_detail_sodium,
            R.id.img_recipe_detail_fat,
            R.id.img_recipe_detail_balance })
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_recipe_detail_protein :
                showSnackBar(view, Constants.PROTEIN);
                break;
            case R.id.img_recipe_detail_fibre :
                showSnackBar(view, Constants.FIBER);
                break;
            case R.id.img_recipe_detail_carb :
                showSnackBar(view, Constants.CARB);
                break;
            case R.id.img_recipe_detail_sodium :
                showSnackBar(view, Constants.SODIUM);
                break;
            case R.id.img_recipe_detail_fat :
                showSnackBar(view, Constants.FAT);
                break;
            case R.id.img_recipe_detail_balance :
                showSnackBar(view, Constants.BALANCED);
                break;
            default :
                onNutrientsListener.displayFragmentNutrients(recipe);
        }
    }



    private void showSnackBar(View view, String diet) {
        Snackbar.make(view, diet, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onNutrientsListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * Rating star starts an asynctask to save the recipe in data base
     * @param ratingBar
     * @param rating
     * @param fromUser
     */
    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        Log.i(TAG, "onRatingChanged: rating: " + rating);
        if (rating > 0) {
            SaveRecipeToFavsTask saveTask = new SaveRecipeToFavsTask(getActivity());
            saveTask.execute(recipe);
        }
    }
}
