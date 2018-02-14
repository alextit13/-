package com.bingerdranch.android.bestautoservice;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private String adress;
    private String name;
    private double X;
    private double Y;

    private ArrayList<String>list;
    private MyTask mt;

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

        list = new ArrayList<>();


        list = (ArrayList<String>) intent.getSerializableExtra("list");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (name!=null&&adress!=null){

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
            //mMap = googleMap;
            List<Address> addresses;
            Log.d(MainActivity.LOG_TAG,"onPostExecute");
            for (int i = 0; i<list.size();i++){
                Geocoder geocoder = new Geocoder(MapsActivity.this);
                try {
                    addresses = geocoder.getFromLocationName(list.get(i), 1);
                    //Log.d(MainActivity.LOG_TAG,"adresses = " + addresses);
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
                mMap.addMarker(new MarkerOptions().position(object).title(list.get(i)));
                //mMap.addMarker(new MarkerOptions());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(object,10f));
            }
        }
    }

    class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //tvInfo.setText("Begin");
            Log.d(MainActivity.LOG_TAG,"onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(MainActivity.LOG_TAG,"doInBackground");
            /*try {
                //TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //tvInfo.setText("End");
        }
    }

}
