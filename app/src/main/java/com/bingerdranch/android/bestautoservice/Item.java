package com.bingerdranch.android.bestautoservice;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Item extends AppCompatActivity {

    private TextView site_text_view;
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
    private TextView tv_grafik_raboti;
    private Button button_open_the_map;
    private TextView text_view_add_otziv;
    private String otzivi = "";
    private Autoservice autoservice;
    private LinearLayout item_container;

    private String rating = "";

    private ImageView zv_1;
    private ImageView zv_2;
    private ImageView zv_3;
    private ImageView zv_4;
    private ImageView zv_5;

    private double X;
    private double Y;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        site_text_view = (TextView) findViewById(R.id.site_text_view);
        tv_name = (TextView)findViewById(R.id.name);
        tv_marka = (TextView)findViewById(R.id.markas);
        tv_model = (TextView)findViewById(R.id.models);
        tv_okrug = (TextView)findViewById(R.id.okrug);
        tv_rayon = (TextView)findViewById(R.id.rayon);
        tv_metro = (TextView)findViewById(R.id.metro);
        tv_adress = (TextView)findViewById(R.id.number_phone);
        tv_number = (TextView)findViewById(R.id.nomer);
        tv_vid_rabot =(TextView)findViewById(R.id.vid_rabot);
        tv_otzivi = (TextView)findViewById(R.id.otzivi_item);
        tv_grafik_raboti = (TextView)findViewById(R.id.grafik_raboti);

        button_open_the_map = (Button) findViewById(R.id.button_open_the_map);
        text_view_add_otziv = (TextView) findViewById(R.id.text_view_add_otziv);
        item_container = (LinearLayout)findViewById(R.id.item_container);

        zv_1 = (ImageView) findViewById(R.id.zv_1);
        zv_2 = (ImageView) findViewById(R.id.zv_2);
        zv_3 = (ImageView) findViewById(R.id.zv_3);
        zv_4 = (ImageView) findViewById(R.id.zv_4);
        zv_5 = (ImageView) findViewById(R.id.zv_5);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        Intent intent = getIntent();
        autoservice = (Autoservice) intent.getSerializableExtra("list");

        setRatingZv(autoservice.getRating());

        tv_name.setText(autoservice.getName());
        tv_marka.setText(autoservice.getMarka());
        tv_model.setText(autoservice.getModel());
        tv_okrug.setText(autoservice.getOkrug());
        tv_rayon.setText(autoservice.getRayon());
        tv_metro.setText(autoservice.getMetro());
        tv_adress.setText(autoservice.getAdress());
        tv_number.setText(autoservice.getNumber());
        tv_vid_rabot.setText(autoservice.getVid_rabot());
        tv_grafik_raboti.setText(autoservice.getGrafik_raboti());
        if (autoservice.getSite()!=null
                &&!autoservice.getSite().equals("")){
            site_text_view.setText(autoservice.getSite());
            site_text_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = site_text_view.getText().toString();
                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                        url = "http://" + url;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
            });
        }else{
            site_text_view.setVisibility(View.INVISIBLE);
        }

        tv_otzivi.setText(autoservice.getOtzivi());

        button_open_the_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button_open_the_map.setEnabled(false);

                item_container.setAlpha(0.3f);
                Intent intent1 = new Intent(Item.this,MapsActivity.class);
                intent1.putExtra("autoservice",autoservice);
                intent1.putExtra("adress",autoservice.getAdress());
                intent1.putExtra("name",autoservice.getName());
                startActivity(intent1);
            }
        });
        text_view_add_otziv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOtziv();
            }
        });

        tv_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnPhoneNumber();
            }
        });
    }

    private void clickOnPhoneNumber() {
        ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label",tv_number.getText().toString());
        manager.setPrimaryClip(clip);
        Toast.makeText(Item.this,"Номер скопирован!",Toast.LENGTH_SHORT).show();
    }

    private void setRatingZv(int rating) {
        //Log.d(MainActivity.LOG_TAG,"rating 3 = " + rating);
        switch (rating){
            case 0:
                zv_1.setImageResource(R.drawable.rating_empty);
                zv_2.setImageResource(R.drawable.rating_empty);
                zv_3.setImageResource(R.drawable.rating_empty);
                zv_4.setImageResource(R.drawable.rating_empty);
                zv_5.setImageResource(R.drawable.rating_empty);
                break;
            case 1:
                zv_2.setImageResource(R.drawable.rating_empty);
                zv_3.setImageResource(R.drawable.rating_empty);
                zv_4.setImageResource(R.drawable.rating_empty);
                zv_5.setImageResource(R.drawable.rating_empty);
                break;
            case 2:
                zv_3.setImageResource(R.drawable.rating_empty);
                zv_4.setImageResource(R.drawable.rating_empty);
                zv_5.setImageResource(R.drawable.rating_empty);
                break;
            case 3:
                zv_4.setImageResource(R.drawable.rating_empty);
                zv_5.setImageResource(R.drawable.rating_empty);
                break;
            case 4:
                zv_5.setImageResource(R.drawable.rating_empty);
                break;
            case 5:
                break;
        }
    }

    private void addOtziv() {
        Intent intent = new Intent(Item.this,AddComment.class);
        intent.putExtra("autoservice",autoservice);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        button_open_the_map.setEnabled(true);
        item_container.setAlpha(1f);
        super.onStart();
    }
}
