package com.tae.letscook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tae.letscook.Utils.DrawableUtils;
import com.tae.letscook.R;
import com.tae.letscook.listeners.OnItemClickListener;
import com.tae.letscook.model.Item;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Eduardo on 06/12/2015.
 */
public class AdapterDrawer extends RecyclerView.Adapter<AdapterDrawer.ViewHolder> {

    private List<Item> items;
    private Context context;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public AdapterDrawer(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_ITEM) {
            view = inflater.inflate(R.layout.drawer_item, parent, false);
        } else {
            view = inflater.inflate(R.layout.drawer_header, parent, false);
        }
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.type == TYPE_ITEM) {
            holder.tvItemDrawer.setText(items.get(position - 1).getItem());
            Picasso.with(context).load(items.get(position - 1).getImageId()).placeholder(android.R.drawable.star_big_on).into(holder.imgItemDrawer); // TODO add a
        } else {
            holder.tvHeaderUser.setText("User name goes here"); // TODO Use a Chef model to set this value and the user image
            Picasso.with(context).load(android.R.drawable.star_big_off).placeholder(android.R.drawable.star_big_on).resize(100, 100).centerCrop().into(holder.circleImgHeader); // TODO add a
        }
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position > 0 ? TYPE_ITEM : TYPE_HEADER ;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvItemDrawer, tvHeaderUser;
        private CircleImageView circleImgHeader;
        private ImageView imgItemDrawer;
        private int type;
        private OnItemClickListener itemClickListener;


        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setSelected(true); // this enables selection
            itemClickListener = (OnItemClickListener) itemView.getContext();
            if (viewType == TYPE_ITEM) {
                tvItemDrawer = (TextView) itemView.findViewById(R.id.tv_drawer_item);
                imgItemDrawer = (ImageView) itemView.findViewById(R.id.img_drawer_item);
                LinearLayout viewGroup = (LinearLayout) itemView.findViewById(R.id.drawer_item_layout);
                DrawableUtils.setRipple(viewGroup, DrawableUtils.drawableFactory(itemView.getContext(), R.drawable.ripple_drawable));
            } else {
                tvHeaderUser = (TextView) itemView.findViewById(R.id.header_user_name);
                circleImgHeader = (CircleImageView) itemView.findViewById(R.id.header_image);
            }
            type = viewType;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition(), 0);
        }
    }
}
