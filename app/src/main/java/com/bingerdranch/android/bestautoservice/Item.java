package com.bingerdranch.android.bestautoservice;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

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

    private double X;
    private double Y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        tv_name = (TextView)findViewById(R.id.name);
        tv_marka = (TextView)findViewById(R.id.marka);
        tv_model = (TextView)findViewById(R.id.model);
        tv_okrug = (TextView)findViewById(R.id.okrug);
        tv_rayon = (TextView)findViewById(R.id.rayon);
        tv_metro = (TextView)findViewById(R.id.metro);
        tv_adress = (TextView)findViewById(R.id.adress);
        tv_number = (TextView)findViewById(R.id.nomer);
        tv_vid_rabot =(TextView)findViewById(R.id.vid_rabot);
        tv_otzivi = (TextView)findViewById(R.id.otzivi_item);
        button_open_the_map = (Button) findViewById(R.id.button_open_the_map);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        marka = intent.getStringExtra("marka");
        model = intent.getStringExtra("model");
        okrug = intent.getStringExtra("okrug");
        rayon = intent.getStringExtra("rayon");
        metro = intent.getStringExtra("metro");
        adress = intent.getStringExtra("adress");
        number = intent.getStringExtra("number");
        vid_rabot = intent.getStringExtra("vid_rabot");
        otzivi = intent.getStringExtra("otzivi");

        tv_name.setText(name);
        tv_marka.setText(marka);
        tv_model.setText(model);
        tv_okrug.setText(okrug);
        tv_rayon.setText(rayon);
        tv_metro.setText(metro);
        tv_adress.setText(adress);
        tv_number.setText(number);
        tv_vid_rabot.setText(vid_rabot);

        otzivi = otzivi.replaceAll("null","");
        tv_otzivi.setText(otzivi);

        button_open_the_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Item.this,MapsActivity.class);
                intent1.putExtra("adress",adress);
                intent1.putExtra("name",name);
                startActivity(intent1);
            }
        });
    }
}
