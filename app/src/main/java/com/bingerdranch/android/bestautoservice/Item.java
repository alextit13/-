package com.bingerdranch.android.bestautoservice;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Item extends AppCompatActivity {

    private TextView tv_name;
    private TextView tv_marka;
    private TextView tv_model;
    private TextView tv_okrug;
    private TextView tv_rayon;
    private TextView tv_metro;
    private TextView tv_adress;
    private TextView tv_number;
    private TextView tv_vid_rabot;
    private TextView tv_otzivi;
    private Button button_open_the_map;

    private String name = "";
    private String marka = "";
    private String model = "";
    private String okrug = "";
    private String rayon = "";
    private String metro = "";
    private String adress = "";
    private String number = "";
    private String vid_rabot = "";
    private String otzivi = "";

    private Autoservice autoservice;

    private double X;
    private double Y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        tv_name = (TextView)findViewById(R.id.name);
        tv_marka = (TextView)findViewById(R.id.markas);
        tv_model = (TextView)findViewById(R.id.models);
        tv_okrug = (TextView)findViewById(R.id.okrug);
        tv_rayon = (TextView)findViewById(R.id.rayon);
        tv_metro = (TextView)findViewById(R.id.metro);
        tv_adress = (TextView)findViewById(R.id.adress);
        tv_number = (TextView)findViewById(R.id.nomer);
        tv_vid_rabot =(TextView)findViewById(R.id.vid_rabot);
        tv_otzivi = (TextView)findViewById(R.id.otzivi_item);
        button_open_the_map = (Button) findViewById(R.id.button_open_the_map);

        Intent intent = getIntent();
        autoservice = (Autoservice) intent.getSerializableExtra("list");

        Log.d(MainActivity.LOG_TAG,"list.get(item) = "+autoservice.getMarka());

        tv_name.setText(autoservice.getName());
        tv_marka.setText(autoservice.getMarka());
        tv_model.setText(autoservice.getModel());
        tv_okrug.setText(autoservice.getOkrug());
        tv_rayon.setText(autoservice.getRayon());
        tv_metro.setText(autoservice.getMetro());
        tv_adress.setText(autoservice.getAdress());
        tv_number.setText(autoservice.getNumber());
        tv_vid_rabot.setText(autoservice.getVid_rabot());

        otzivi = otzivi.replaceAll("null","");
        tv_otzivi.setText(otzivi);

        button_open_the_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Item.this,MapsActivity.class);
                intent1.putExtra("adress",autoservice.getAdress());
                intent1.putExtra("name",autoservice.getName());
                startActivity(intent1);
            }
        });
    }
}
