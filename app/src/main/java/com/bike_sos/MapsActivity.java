package com.bike_sos;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Nicolas on 9/30/2016.
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double gpsLat = 45.5088400;
        double gpsLong = -73.5878100;

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            gpsLat = Double.parseDouble(extras.getString("gpsLat"));
            gpsLong = Double.parseDouble(extras.getString("gpsLong"));
        }

        LatLng position = new LatLng(gpsLat, gpsLong);
        mMap.addMarker(new MarkerOptions().position(position).title("Marker in Montreal"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position , 14.0f));
    }
}