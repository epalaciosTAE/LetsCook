package com.tae.letscook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tae.letscook.R;
import com.tae.letscook.model.Event;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eduardo on 26/01/2016.
 */
public class AdapterEvents extends RecyclerView.Adapter<AdapterEvents.ViewHolder> {

    private List<Event> events;
    private Context context;

    public AdapterEvents(Context context, List<Event> events) {
        this.events = events;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_events_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(events.get(position).getUserName());
        holder.address.setText(events.get(position).getAddress());
        holder.date.setText(events.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void update(Event event) {
        events.add(event);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv_event_title) protected TextView title;
        @Bind(R.id.tv_event_address) protected TextView address;
        @Bind(R.id.tv_event_date) protected TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
