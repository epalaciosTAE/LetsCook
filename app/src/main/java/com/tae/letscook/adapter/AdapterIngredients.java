package com.tae.letscook.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tae.letscook.R;
import com.tae.letscook.model.CustomIngredient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eduardo on 02/01/2016.
 */
public class AdapterIngredients extends RecyclerView.Adapter<AdapterIngredients.ViewHolder> {

    private static final String TAG = AdapterIngredients.class.getSimpleName();
    private Context context;
    private List<CustomIngredient> ingredients;

    public AdapterIngredients(Context context) {
        this.context = context;
        ingredients = new ArrayList<>();
    }

    public AdapterIngredients(Context context, @Nullable List<CustomIngredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
        if (ingredients != null) {
            Log.i(TAG, "AdapterIngredients: ingredients list size: " + ingredients.size());
        }
    }


    @Override
    public AdapterIngredients.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredients_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterIngredients.ViewHolder holder, int position) {
        holder.tvName.setText(ingredients.get(position).getIngredient());
        holder.tvAmount.setText(ingredients.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return ingredients != null ? ingredients.size() : 0;
    }

    public void addIngredient(CustomIngredient ingredient) {
        ingredients.add(ingredient);
        notifyDataSetChanged();
    }

    public void removeIngredient(int position) {
        ingredients.remove(position);
        notifyDataSetChanged();
    }

    public List<CustomIngredient> getIngredients() {
        return ingredients;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv_ingredient_name) protected TextView tvName;
        @Bind(R.id.tv_ingredient_amount) protected TextView tvAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
