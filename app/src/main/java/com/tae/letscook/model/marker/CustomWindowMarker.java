package com.tae.letscook.model.marker;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.tae.letscook.R;

/**
 * Created by Eduardo on 07/01/2016.
 */
public class CustomWindowMarker implements GoogleMap.InfoWindowAdapter {

    private View infoWindowView;

    public CustomWindowMarker(Context context) {
        infoWindowView = ((AppCompatActivity)context).getLayoutInflater().inflate(R.layout.custom_info_window, null);
    }


    @Override
    public View getInfoWindow(Marker marker) {
        renderMarker(marker);
        return infoWindowView;
    }

    @Override
    public View getInfoContents(Marker marker) {
//        renderMarker(marker);
        return null;
    }

    private void renderMarker(Marker marker) {
        ((ImageView) infoWindowView.findViewById(R.id.badge)).setImageResource(R.drawable.ic_nav_add_recipes);
        TextView titleUi = ((TextView) infoWindowView.findViewById(R.id.title));
        titleUi.setText(marker.getTitle());
        TextView snippetUi = ((TextView) infoWindowView.findViewById(R.id.snippet));
        snippetUi.setText(marker.getSnippet());
    }
}
