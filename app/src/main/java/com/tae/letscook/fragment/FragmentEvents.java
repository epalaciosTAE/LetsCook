package com.tae.letscook.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tae.letscook.R;
import com.tae.letscook.adapter.AdapterEvents;
import com.tae.letscook.app.LetsCookApp;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.model.Event;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Eduardo on 30/12/2015.
 */
public class FragmentEvents extends Fragment {

    @Bind(R.id.recycler_view) protected RecyclerView recyclerView;
    private List<Event> events;
    private AdapterEvents adapterEvents;

    public static FragmentEvents newInstance (List<Event> events) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.EXTRA_EVENTS, (ArrayList<? extends Parcelable>) events);
        FragmentEvents fragmentEvents = new FragmentEvents();
        fragmentEvents.setArguments(bundle);
        return fragmentEvents;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LetsCookApp.getInstance().trackScreenView(getResources().getString(R.string.fragment_events));
        events = (List<Event>) getArguments().get(Constants.EXTRA_EVENTS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapterEvents = new AdapterEvents(getActivity(), events);
        recyclerView.setAdapter(adapterEvents);
        return view;
    }

    @OnClick(R.id.fab_add_event)
    protected void onClick(View view) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container, FragmentAddEvent.newInstance(), getResources().getString(R.string.fragment_add_event))
                .addToBackStack(getResources().getString(R.string.fragment_add_event)).commit();

    }

    public void updateEvents(Event event) {
        adapterEvents.update(event);
    }
}
