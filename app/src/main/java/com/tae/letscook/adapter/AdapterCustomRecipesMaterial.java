package com.tae.letscook.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tae.letscook.R;
import com.tae.letscook.Utils.DrawableUtils;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.listeners.OnCategoryItemListener;
import com.tae.letscook.model.ItemRecipe;

import java.util.List;

/**
 * Created by Eduardo on 26/01/2016.
 */
public class AdapterCustomRecipesMaterial extends RecyclerView.Adapter<AdapterCustomRecipesMaterial.ViewHolder>{

    private List<ItemRecipe> itemRecipes;
    private Context context;
    private ViewHolder mHolder;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CELL = 1;

    public AdapterCustomRecipesMaterial(Context context, List<ItemRecipe> itemRecipes) {
        this.context = context;
        this.itemRecipes = itemRecipes;
    }

    @Override
    public int getItemViewType(int position) {
        return position > 0 ? TYPE_CELL : TYPE_HEADER;
    }

    @Override
    public int getItemCount() {
        return itemRecipes == null ? 0 : itemRecipes.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_card_big, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_categories_item, parent, false);
        }
        return new ViewHolder(view, viewType, itemRecipes);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                holder.tvBigCard.setText(itemRecipes.get(position).getLabel());
                Log.i("CustomRecipesAdapter", "onBindViewHolder: path image: " + itemRecipes.get(position).getImageUrl());
                if (itemRecipes.get(position).getImageUrl() != null) {
                    holder.imgBigCard.setImageURI(Uri.parse(itemRecipes.get(position).getImageUrl()));
                }
//                Picasso.with(context).load(itemRecipes.get(position).getImageUrl()).into(holder.imgBigCard);
                mHolder = holder;
                break;
            case TYPE_CELL:
                holder.tvCategory.setText(itemRecipes.get(position).getLabel());
                if (itemRecipes.get(position).getImageUrl() != null) {
                    holder.imgCategory.setImageURI(Uri.parse(itemRecipes.get(position).getImageUrl()));
                }
//                Picasso.with(context).load(itemRecipes.get(position).getImageUrl()).into(holder.imgCategory);
                break;
        }
    }

    public void reloadHeader() {
        Picasso.with(context).load(DrawableUtils.randomDrawable()).into(mHolder.imgBigCard);
        notifyDataSetChanged();
    }

    public void updateSuggestions(List<ItemRecipe> itemRecipes) {
        Log.i("Suggestion Adapter", "updateSuggestions: list updated");
//        this.itemRecipes = itemRecipes;
//        for (ItemRecipe item : itemRecipes) {
//            this.itemRecipes.add(item);
//            notifyDataSetChanged();
//        }
        this.itemRecipes.clear();
        this.itemRecipes.addAll(itemRecipes);
        this.notifyDataSetChanged();
    }

//    private void generateColorLabelsInCards(final ViewHolder holder, int position) {
//        Bitmap photo = BitmapFactory.decodeResource(context.getResources(), itemRecipes.get(position).getImageUrl());
//        Palette.from(photo).generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                int bgColor;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                    bgColor = palette.getVibrantColor(context.getResources().getColor(R.color.colorPrimary, context.getTheme()));
//                } else {
//                    bgColor = palette.getVibrantColor(context.getResources().getColor(R.color.colorPrimary));
//                }
//                holder.tvBigCard.setBackgroundColor(palette.getVibrantColor(bgColor));
//
//            }
//        });
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvCategory, tvBigCard;
        private ImageView imgCategory, imgBigCard;
        private OnCategoryItemListener categoryClickListener;
        private List<ItemRecipe> suggestions;

        public ViewHolder(View itemView, int viewType, List<ItemRecipe> contents) {
            super(itemView);
            itemView.setOnClickListener(this);
            categoryClickListener = (OnCategoryItemListener) itemView.getContext();
            this.suggestions = contents;
            if (viewType == TYPE_HEADER) {
                imgBigCard = (ImageView) itemView.findViewById(R.id.img_card_view_big);
                tvBigCard = (TextView) itemView.findViewById(R.id.tv_card_view_big);
            } else {
                tvCategory = (TextView) itemView.findViewById(R.id.tv_category);
                imgCategory = (ImageView) itemView.findViewById(R.id.img_category);
                CardView viewGroup = (CardView) itemView.findViewById(R.id.card_view_categories_item);
                DrawableUtils.setRipple(viewGroup, DrawableUtils.drawableFactory(itemView.getContext(), R.drawable.ripple_drawable));
            }
        }

        @Override
        public void onClick(View v) {
            categoryClickListener.onCategoryItemClick(v, getAdapterPosition(), suggestions.get(getAdapterPosition() - 1).getLabel(), Constants.ADAPTER_CUSTOM_RECIPES_ID);
        }

    }
}
