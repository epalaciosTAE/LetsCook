package com.tae.letscook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tae.letscook.R;
import com.tae.letscook.model.NutrientLocal;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eduardo on 17/01/2016.
 */
public class AdapterNutrients extends RecyclerView.Adapter <AdapterNutrients.ViewHolder> {

    private Context context;
    private List<NutrientLocal>nutrients;

    public AdapterNutrients(Context context, List<NutrientLocal> nutrients) {
        this.context = context;
        this.nutrients = nutrients;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_recipe_detail_nutrient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.label.setText(nutrients.get(position).getLabel());
        StringBuilder builder = new StringBuilder();
        builder.append(String.valueOf((int) nutrients.get(position).getTotal()));
        builder.append(" ");
        builder.append(nutrients.get(position).getUnit());
        holder.amount.setText(builder);
    }

    @Override
    public int getItemCount() {
        return nutrients.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv_nutrient_label) protected TextView label;
        @Bind(R.id.tv_nutrient_amount) protected TextView amount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
