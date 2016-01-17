package com.tae.letscook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tae.letscook.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eduardo on 17/01/2016.
 */
public class AdapterOneItem extends RecyclerView.Adapter<AdapterOneItem.ViewHolder> {

    private Context context;
    private List<String>ingredients;

    public AdapterOneItem(Context context, List<String> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @Override
    public AdapterOneItem.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_recipe_detail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterOneItem.ViewHolder holder, int position) {
        holder.tvItem.setText(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_recipe_detail_item) protected TextView tvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}
