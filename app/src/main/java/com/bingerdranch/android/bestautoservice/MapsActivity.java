package com.bingerdranch.android.bestautoservice;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private String adress;
    private String name;
    private double X;
    private double Y;
    AlertDialog.Builder alertDialog;

    private ArrayList<String> listAdresses;
    private ArrayList<Autoservice> list;
    private MyTask mt;
    ProgressDialog progressDialog;

    private FusedLocationProviderClient mFusedLocationClient;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;


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
        listAdresses = new ArrayList<>();
        listAdresses = (ArrayList<String>) intent.getSerializableExtra("listAdresses");
        list = (ArrayList<Autoservice>) intent.getSerializableExtra("list");
        takeCoordinates();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }else{
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                //Log.d(VARIABLES_CLASS.LOG_TAG,"location = " + location);
                                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(
                                            location.getLatitude(),
                                            location.getLongitude(),

                                            // In this sample, get just a single address.
                                            1);
                                    LatLng object_I = new LatLng(location.getLatitude(), location.getLongitude());
                                    Log.d(MainActivity.LOG_TAG,"location: " + location.getLatitude()
                                            + ", " + location.getLongitude());
                                    Marker IMarker = mMap.addMarker(new MarkerOptions()
                                            .position(object_I)
                                    .title("Мое местоположение")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_location)));
                                } catch (IOException e) {

                                }
                                /*if (addresses!=null){
                                    Log.d(MainActivity.LOG_TAG,"location: " + addresses.get(0).getLatitude()
                                    + ", " + addresses.get(0).getLongitude());
                                    *//*Marker IMarker = mMap.addMarker(new MarkerOptions()
                                    .title("Мое местоположение")
                                    .icon());*//*
                                   *//* e_et_from.setText(addresses.get(0).getLocality());
                                    e_et_pointer_adress_1.setText(addresses.get(0).getAddressLine(0));*//*
                                    //e_et_from.setText(addresses.get(0).getAddressLine(0)+"");

                                }*/
                            }
                        }
                    });
        }
    }

    private void takeCoordinates() {
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission, Manifest.permission.READ_PHONE_STATE},
                        REQUEST_CODE_PERMISSION);
            }else{
                //read location
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    System.out.println("permission denied!");
                }
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
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
            progressDialog = new ProgressDialog(MapsActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(true);
            progressDialog.show();

            mt = new MyTask();
            mt.execute(listAdresses);
            mMap.setOnInfoWindowClickListener(
                    new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Intent intent = new Intent(MapsActivity.this,Item.class);
                            intent.putExtra("list",list.get(Integer.parseInt(marker.getTag().toString())));
                            startActivity(intent);
                        }
                    }
            );
        }
    }

    @Override
    protected void onPause() {
        mt = null;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mt = null;
        super.onDestroy();
    }

    class MyTask extends AsyncTask<ArrayList<String>, MarkerOptions, Void> {

        int num = 0;

        @Override
        protected Void doInBackground(ArrayList<String>...listAdresses) {
            List<Address> addresses;
            for (int i = 0; i<listAdresses[0].size();i++){
                Geocoder geocoder = new Geocoder(MapsActivity.this);
                try {
                    addresses = geocoder.getFromLocationName(listAdresses[0].get(i), 1);
                    if (addresses.size() > 0) {
                        X = addresses.get(0).getLatitude();
                        Y = addresses.get(0).getLongitude();
                    }
                } catch (IOException e) {
                    Log.d(MainActivity.LOG_TAG,"e = " + e);
                    e.printStackTrace();
                }
                LatLng object = new LatLng(X, Y);
                publishProgress(new MarkerOptions().position(object).title(list.get(i).getName() + ", адрес: " + listAdresses[0].get(i)));
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(MarkerOptions...markers) {
            Marker marker = mMap.addMarker(markers[0]);
            marker.setTag(num);
            num++;
            progressDialog.setMessage("Загрузка автосервисов: " + num + " / " + listAdresses.size());
            if (num== listAdresses.size()){
                progressDialog.dismiss();
            }
        }
    }

}
