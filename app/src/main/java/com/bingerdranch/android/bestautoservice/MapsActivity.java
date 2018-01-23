package com.bingerdranch.android.bestautoservice;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private String adress;
    private String name;
    private double X;
    private double Y;
    private Autoservice autoservice;

    private ArrayList<Autoservice>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        adress = intent.getStringExtra("adress");
        name = intent.getStringExtra("name");
        autoservice = (Autoservice)intent.getSerializableExtra("autoservice");

        list = new ArrayList<>();


        list = (ArrayList<Autoservice>) intent.getSerializableExtra("list");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (name!=null&&adress!=null){
            mMap = googleMap;
            Geocoder geocoder = new Geocoder(MapsActivity.this);
            List<Address> addresses;
            try {
                addresses = geocoder.getFromLocationName(adress, 1);
                if (addresses.size() > 0) {
                    X = addresses.get(0).getLatitude();
                    Y = addresses.get(0).getLongitude();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            LatLng object = new LatLng(X, Y);

            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.addMarker(new MarkerOptions().position(object).title(name));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(object,17f));
        }else{
            mMap = googleMap;
            for (int i = 0; i<list.size();i++){

                Geocoder geocoder = new Geocoder(MapsActivity.this);
                List<Address> addresses;
                try {
                    addresses = geocoder.getFromLocationName(list.get(i).getAdress(), 1);
                    Log.d(MainActivity.LOG_TAG,"adresses = " + addresses);
                    if (addresses.size() > 0) {
                        X = addresses.get(0).getLatitude();
                        Y = addresses.get(0).getLongitude();
                    }
                } catch (IOException e) {
                    Log.d(MainActivity.LOG_TAG,"e = " + e);
                    e.printStackTrace();
                }
                LatLng object = new LatLng(X, Y);


                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.addMarker(new MarkerOptions().position(object).title(list.get(i).getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(object,10f));

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Intent intent = new Intent(MapsActivity.this,Item.class);
                        String titleID = marker.getId().substring(1);
                        int num_index_autoservice = Integer.parseInt(titleID);
                        intent.putExtra("list",list.get(num_index_autoservice));
                        startActivity(intent);

                    }
                });
            }
        }
    }
}
