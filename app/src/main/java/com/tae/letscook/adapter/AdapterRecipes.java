package com.tae.letscook.adapter;

import android.content.Context;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tae.letscook.R;
import com.tae.letscook.listeners.OnRecipeItemListener;
import com.tae.letscook.model.Item;
import com.tae.letscook.model.ItemRecipe;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Eduardo on 31/12/2015.
 * List of the recipes depending on the category
 */
public class AdapterRecipes extends RecyclerView.Adapter<AdapterRecipes.ViewHolder> {

    private List<ItemRecipe> recipes;
    private Context context;

    public AdapterRecipes(List<ItemRecipe> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_recipes_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvRecipe.setText(recipes.get(position).getLabel());
        Picasso.with(context)
                .load(recipes.get(position).getImageUrl())
                .placeholder(android.R.drawable.star_big_on)
                .into(holder.imgRecipe);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private static final String TAG = "ViewHolder";
        private CircleImageView imgRecipe;
        private TextView tvRecipe;
        private OnRecipeItemListener onRecipeItemListener;


        public ViewHolder(View itemView) {
            super(itemView);
            onRecipeItemListener = (OnRecipeItemListener) itemView.getContext();
            imgRecipe = (CircleImageView) itemView.findViewById(R.id.img_recipe);
            tvRecipe = (TextView) itemView.findViewById(R.id.tv_recipe);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: position" + getAdapterPosition());
            onRecipeItemListener.onItemClick(getAdapterPosition());
        }
    }
}
