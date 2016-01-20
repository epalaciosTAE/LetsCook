package com.tae.letscook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tae.letscook.R;
import com.tae.letscook.model.ItemIngredient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eduardo on 02/01/2016.
 */
public class AdapterIngredients extends RecyclerView.Adapter<AdapterIngredients.ViewHolder> {

    private Context context;
    private List<ItemIngredient> ingredients;

    public AdapterIngredients(Context context) {
        this.context = context;
        ingredients = new ArrayList<>();
    }


    @Override
    public AdapterIngredients.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredients_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterIngredients.ViewHolder holder, int position) {
        holder.tvName.setText(ingredients.get(position).getName());
        holder.tvAmount.setText(ingredients.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void addIngredient(ItemIngredient ingredient) {
        ingredients.add(ingredient);
        notifyDataSetChanged();
    }

    public void removeIngredient(int position) {
        ingredients.remove(position);
        notifyDataSetChanged();
    }

    public List<ItemIngredient> getIngredients() {
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
