package com.tae.letscook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tae.letscook.R;
import com.tae.letscook.Utils.DrawableUtils;
import com.tae.letscook.listeners.OnCategoryItemListener;
import com.tae.letscook.listeners.OnItemClickListener;
import com.tae.letscook.model.Item;

import java.util.List;

/**
 * Created by Eduardo on 30/12/2015.
 */
public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.ViewHolder> {

    private Context context;
    private List<Item> categories;

    public AdapterCategories(Context context) {
        this.context = context;
        categories = DrawableUtils.itemsFromResourcesFactory(context, R.array.category_titles, R.array.categories_images);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_categories_item, parent, false);
        return new ViewHolder(view, categories);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvCategory.setText(categories.get(position).getItem());
        Picasso.with(context)
                .load(categories.get(position)
                        .getImageId())
                .resize(150, 70).centerCrop()
                .into(holder.imgCategory);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvCategory;
        private ImageView imgCategory;
        private OnCategoryItemListener categoryClickListener;
        private List<Item> categories;

        public ViewHolder(View itemView, List<Item> categories) {
            super(itemView);
            itemView.setOnClickListener(this);
            categoryClickListener = (OnCategoryItemListener) itemView.getContext();
            tvCategory = (TextView) itemView.findViewById(R.id.tv_category);
            imgCategory = (ImageView) itemView.findViewById(R.id.img_category);
            this.categories = categories;
        }

        @Override
        public void onClick(View v) {
            categoryClickListener.onCategoryItemClick(v, getAdapterPosition(), categories.get(getAdapterPosition()).getItem(), true);
        }
    }
}
