package com.bingerdranch.android.bestautoservice;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Item extends AppCompatActivity {

    private TextView tv_site;
    private TextView tv_name, tv_marka, tv_city, tv_model, tv_rayon, tv_metro, tv_adress, tv_number, tv_vid_rabot, tv_grafik_raboti, tv_otzivi;
    private ProgressBar progress_detail_view;
    private Autoservice autoservice;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        String custom_font = "font/oswald_bold.ttf";
        Typeface CF = Typeface.createFromAsset(getAssets(), custom_font);

        progress_detail_view = (ProgressBar) findViewById(R.id.progress_detail_view);
        progress_detail_view.setVisibility(View.INVISIBLE);

        ((TextView) findViewById(R.id.name)).setTypeface(CF);
        ((ImageView) findViewById(R.id.details_back))
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }
                );

        ((Button)findViewById(R.id.add_otziv))
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addOtziv();
                            }
                        }
                );
        Picasso.with(this).load("http://bobsmarket.ru/images/tehnicheskoe-obsluzhivanie.jpg")
                .into((ImageView) findViewById(R.id.details_back));
        tv_otzivi = (TextView) findViewById(R.id.tv_otzivi);
        tv_name = (TextView) findViewById(R.id.name);
        tv_marka = (TextView) findViewById(R.id.tv_marka);
        tv_model = (TextView) findViewById(R.id.tv_model);
        tv_rayon = (TextView) findViewById(R.id.tv_rayon);
        tv_metro = (TextView) findViewById(R.id.tv_metro);
        tv_adress = (TextView) findViewById(R.id.tv_adress);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_vid_rabot = (TextView) findViewById(R.id.tv_vid_rabot);
        tv_grafik_raboti = (TextView) findViewById(R.id.tv_grafik_raboti);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_site = (TextView) findViewById(R.id.tv_site);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        Intent intent = getIntent();
        autoservice = (Autoservice) intent.getSerializableExtra("list");

        if (autoservice.getSite()!=null
                &&!autoservice.getSite().equals("")){
            tv_site.setText(autoservice.getSite());
            tv_site.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToSite(tv_site.getText().toString());
                }
            });
        }

        tv_otzivi.setText(autoservice.getOtzivi());
        tv_adress.setText(autoservice.getAdress());
        tv_name.setText(autoservice.getName());
        tv_city.setText(autoservice.getOkrug());
        tv_marka.setText(autoservice.getMarka());
        tv_model.setText(autoservice.getModel());
        tv_rayon.setText(autoservice.getRayon());
        tv_metro.setText(autoservice.getMetro());
        tv_number.setText(autoservice.getNumber());
        tv_number.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CALL_PHONE);
                if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                            REQUEST_CODE_ASK_PERMISSIONS);
                    return;
                }
                Uri uri_number = Uri.parse("tel:" + autoservice.getNumber()+"");
                Intent intent_phone = new Intent(Intent.ACTION_CALL, uri_number);
                startActivity(intent_phone);
            }
        });
        tv_vid_rabot.setText(autoservice.getVid_rabot());
        tv_grafik_raboti.setText(autoservice.getGrafik_raboti());
    }

    private void goToSite(String s) {
        if (!s.startsWith("http://") && !s.startsWith("https://"))
            s = "http://" + s;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
        startActivity(browserIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    //insertDummyContact();
                    Uri uri_number = Uri.parse("tel:" + 154848454);
                    Intent intent_phone = new Intent(Intent.ACTION_CALL, uri_number);
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intent_phone);
                } else {
                    // Permission Denied
                    Toast.makeText(Item.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void addOtziv() {
        Intent intent = new Intent(Item.this, AddComment.class);
        intent.putExtra("autoservice", autoservice);
        startActivity(intent);
    }

    public void onClick(View view) {
        // do call with number
        switch (view.getId()) {
            case R.id.tv_adress:
                // map
                progress_detail_view.setVisibility(View.VISIBLE);
                Intent intent = new Intent(Item.this,MapsActivity.class);
                intent.putExtra("name",autoservice.getName());
                intent.putExtra("adress",autoservice.getAdress());
                intent.putExtra("autoservice",autoservice);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progress_detail_view.setVisibility(View.INVISIBLE);
    }
}
