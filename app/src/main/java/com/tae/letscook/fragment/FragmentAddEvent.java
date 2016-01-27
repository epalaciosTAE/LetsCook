package com.tae.letscook.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tae.letscook.R;
import com.tae.letscook.Utils.DetectTabletUtils;
import com.tae.letscook.Utils.DrawableUtils;
import com.tae.letscook.Utils.ToastUtils;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.dialog.DatePickerDialogFragment;
import com.tae.letscook.listeners.OnGetDatePickerListener;
import com.tae.letscook.model.Event;
import com.tae.letscook.model.geocoding.GeocodingLatLng;
import com.tae.letscook.model.marker.CustomWindowMarker;
import com.tae.letscook.service.LetsCookService;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Eduardo on 24/01/2016.
 */
public class FragmentAddEvent extends Fragment implements OnGetDatePickerListener, OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = FragmentAddEvent.class.getSimpleName();
    @Bind(R.id.et_event_title) protected EditText etAddTitle;
    @Bind(R.id.et_event_address) protected EditText etAddAddress;
//    @Bind(R.id.actv_event_address) protected EditText autoComplete;
    @Bind(R.id.tv_event_date) protected TextView tvDate;
    @Bind(R.id.btn_event_add) protected Button btnAddEvent;
    @Bind(R.id.btn_event_date) protected Button btnAddDate;
    private List<GeocodingLatLng> locations;
    private GoogleMap mMap;



    public static FragmentAddEvent newInstance() {
        return new FragmentAddEvent();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ButterKnife.bind(this, view);
        DrawableUtils.setRipple(btnAddDate, DrawableUtils.drawableFactory(getActivity(), R.drawable.ripple_drawable));
        DrawableUtils.setRipple(btnAddEvent, DrawableUtils.drawableFactory(getActivity(), R.drawable.ripple_drawable));
        return view;
    }

    @OnClick({R.id.fab_event_show_map, R.id.btn_event_date, R.id.btn_event_add})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_event_show_map:
                if (!etAddAddress.getText().toString().isEmpty()) {
                    getActivity().startService(LetsCookService.makeIntentGEocoding(getActivity(), etAddAddress.getText().toString()));
                } else {
                    ToastUtils.showToast(getActivity(), getResources().getString(R.string.event_enter_address));
                }
                break;
            case R.id.btn_event_date:
                DatePickerDialogFragment datePicker = DatePickerDialogFragment.newInstance();
                datePicker.show(getActivity().getSupportFragmentManager(),DatePickerDialogFragment.FRAGMENT_TAG);
                break;
            default:
               // TODO create an event
                if (locations != null && !locations.isEmpty()) {
                    getActivity().startService(LetsCookService.makeIntentEvent(getActivity(),
                                    new Event( // FIXME location is hardcoded --> should calculate the closest distance to the user
                                            etAddTitle.getText().toString(),
                                            etAddAddress.getText().toString(),
                                            locations.get(0).getLat(),
                                            locations.get(0).getLng(),
                                            tvDate.getText().toString()))
                    );
                    if (!DetectTabletUtils.isTablet(getActivity())) {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                } else {
                    ToastUtils.showToast(getActivity(), "Look your address in the map");
                }


        }

    }

    @Override
    public void getDate(String date) {
        Log.i(TAG, "getDate: "+ date);
        tvDate.setText(date);
    }


    public void showMarkerWithLocations (List<GeocodingLatLng> locations) {
        for (GeocodingLatLng location : locations) {
            this.locations = locations;
            Marker marker = mMap.addMarker(new MarkerOptions() // here we dont need the marker, deleted if its not going to have any use
                    .position(new LatLng(location.getLat(), location.getLng()))
                    .title(etAddTitle.getText().toString())
                    .snippet(etAddAddress.getText().toString() + " (" + tvDate.getText().toString() + ")")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_nav_events)));

        }
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionInMap(new LatLng(locations.get(0).getLat(),locations.get(0).getLng()))));
    }
    @NonNull
    private CameraPosition getCameraPositionInMap(LatLng latLng) {
        return new CameraPosition.Builder()
                .target(latLng)
                .zoom(14)
                .bearing(0)  // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera
                .build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setInfoWindowAdapter(new CustomWindowMarker(getActivity()));
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        marker.showInfoWindow();
    }
}
