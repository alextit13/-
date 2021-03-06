package com.bingerdranch.android.bestautoservice;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class FindActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Autoservice> list;
    private Button find_button;

    private ProgressBar progress_bar;

    private Spinner sp_1;
    private Spinner sp_2;
    private Spinner sp_3;
    private Spinner sp_4;
    private Spinner sp_5;
    private Spinner sp_6;

    private ArrayList<String> list_marka;
    private ArrayList<String> list_model;
    private ArrayList<String> list_okrug;
    private ArrayList<String> list_rayon;
    private ArrayList<String> list_metro;
    private ArrayList<String> list_vid_rabot;
    private ArrayAdapter<String> adapter_marka;
    private ArrayAdapter<String> adapter_model;
    private ArrayAdapter<String> adapter_okrug;
    private ArrayAdapter<String> adapter_rayon;
    private ArrayAdapter<String> adapter_metro;
    private ArrayAdapter<String> adapter_vid_rabot;
    private String marka = "";
    private String model = "";
    private String okrug = "";
    private String rayon = "";
    private String metro = "";
    private String vid_rabot = "";
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private Autoservice autoservice;
    private ChildEventListener mChildEventListeber;
    private LinearLayout container;
    private long countListData = 0;
    private ArrayList<Autoservice> find_list_autoservices;
    private ImageView see_autoserv_s_on_the_map;
    private Toolbar toolbar;
    private ArrayList<String>listAdresses = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        init();
        downloadData();
    }

    private void init() {

        String custom_font = "font/oswald_regular.ttf";
        Typeface CF = Typeface.createFromAsset(getAssets(), custom_font);
        ((TextView)findViewById(R.id.marka_tv)).setTypeface(CF);
        ((TextView) findViewById(R.id.model_splash)).setTypeface(CF);
        ((TextView) findViewById(R.id.city_splash)).setTypeface(CF);
        ((TextView) findViewById(R.id.rayon_splash)).setTypeface(CF);
        ((TextView) findViewById(R.id.metro_splash)).setTypeface(CF);
        ((TextView) findViewById(R.id.vid_rabot_splash)).setTypeface(CF);

        ((Button) findViewById(R.id.button_cancel)).setTypeface(CF);
        ((Button) findViewById(R.id.button_cancel)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sp_1.setSelection(0);
                        sp_2.setSelection(0);
                        sp_3.setSelection(0);
                        sp_4.setSelection(0);
                        sp_5.setSelection(0);
                        sp_6.setSelection(0);
                    }
                }
        );


        ((Button) findViewById(R.id.button_find)).setTypeface(CF);

        String custom_font_amarante = "font/amarante_regular.ttf";
        Typeface CF_amarante = Typeface.createFromAsset(getAssets(), custom_font_amarante);
        ((TextView) findViewById(R.id.main_splash)).setTypeface(CF_amarante);


        see_autoserv_s_on_the_map = (ImageView) findViewById(R.id.see_autoserv_s_on_the_map);
        see_autoserv_s_on_the_map.setAlpha(.3f);
        see_autoserv_s_on_the_map.setClickable(false);
        see_autoserv_s_on_the_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllAutoserviceOnTheMap();
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar_swipe);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        list = new ArrayList<>();

        sp_1 = (Spinner) findViewById(R.id.sp_1);
        sp_2 = (Spinner) findViewById(R.id.sp_2);
        sp_3 = (Spinner) findViewById(R.id.sp_3);
        sp_4 = (Spinner) findViewById(R.id.sp_4);
        sp_5 = (Spinner) findViewById(R.id.sp_5);
        sp_6 = (Spinner) findViewById(R.id.sp_6);

        list_marka = new ArrayList<>();
        list_model = new ArrayList<>();
        list_okrug = new ArrayList<>();
        list_rayon = new ArrayList<>();
        list_metro = new ArrayList<>();
        list_vid_rabot = new ArrayList<>();

        container = (LinearLayout)findViewById(R.id.container);
        container.setAlpha(0.3f);

        find_button = (Button) findViewById(R.id.button_find);
        find_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findLogic();
            }
        });
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.VISIBLE);
        find_button.setEnabled(false);
    }

    private void downloadData() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d(MainActivity.LOG_TAG,"dataSnapshot.getChildrenCount() = "+ dataSnapshot.getChildrenCount());
                countListData = dataSnapshot.getChildrenCount();

                if (mChildEventListeber == null){
                    mChildEventListeber = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            autoservice = dataSnapshot.getValue(Autoservice.class);

                            //Log.d("LOG_TAG","s = " + s);
                            list.add(autoservice);
                            //Log.d(MainActivity.LOG_TAG,"countListData = "+ autoservice.getRating());
                            if (list.size()==countListData){
                                sortListData();
                                confirmList();
                                progress_bar.setVisibility(View.GONE);
                                container.setAlpha(1f);
                                find_button.setEnabled(true);
                                see_autoserv_s_on_the_map.setAlpha(1f);
                                see_autoserv_s_on_the_map.setClickable(true);
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    mDatabaseReference.addChildEventListener(mChildEventListeber);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sortListData() {
        for (int i = 0; i<list.size();i++){
            listAdresses.add(list.get(i).getAdress());
        }
    }

    public void confirmList(){
        for (int i = 0; i<list.size();i++){
            list_marka.add(list.get(i).getMarka());
            list_model.add(list.get(i).getModel());
            list_okrug.add(list.get(i).getOkrug());
            list_rayon.add(list.get(i).getRayon());
            list_metro.add(list.get(i).getMetro());
            list_vid_rabot.add(list.get(i).getVid_rabot());
        }

        HashSet<String> hash_okrug = new HashSet<>();
        hash_okrug.addAll(list_okrug);
        list_okrug.clear();
        list_okrug.addAll(hash_okrug);


        HashSet<String> hash_rayon = new HashSet<>();
        hash_rayon.addAll(list_rayon);
        list_rayon.clear();
        list_rayon.addAll(hash_rayon);

        HashSet<String> hash_metro = new HashSet<>();
        hash_metro.addAll(list_metro);
        list_metro.clear();
        list_metro.addAll(hash_metro);

        updateAdapters();
    }

    private ArrayList<String> sortList(ArrayList<String> list) {
        for (int i = 0; i<list.size();i++){
            if (list.get(i).contains("ВСЕ")){
                list.remove(i);
            }
        }
        Collections.sort(list);
        return list;
    }

    private void updateAdapters() {
        ArrayList<String>new_list_marka = new ArrayList<>();
        ArrayList<String>new_list_model = new ArrayList<>();
        ArrayList<String>new_list_vid_rabot = new ArrayList<>();
        String[] ary_marka = {};
        String[] ary_model = {};
        String[] ary_vid_rabot = {};
        for (int i = 0; i<list_marka.size();i++){
            ary_marka = null;
           if (list_marka.get(i).contains(",")){
               ary_marka = list_marka.get(i).split(",");
               for (int t = 0; t<ary_marka.length;t++){
                   new_list_marka.add(ary_marka[t]);
               }
           }else{
               new_list_marka.add(list_marka.get(i));
           }
        }

        for (int i = 0; i<list_model.size();i++){
            if (list_model.get(i).contains(",")){
                ary_model = list_model.get(i).split(",");
                for (int t = 0; t<ary_model.length;t++){
                    new_list_model.add(ary_model[t]);
                }
            }else{
                new_list_model.add(list_model.get(i));
            }
        }

        for (int i = 0; i<list_vid_rabot.size();i++){
            ary_vid_rabot = null;
            if (list_vid_rabot.get(i).contains(",")){
                ary_vid_rabot = list_vid_rabot.get(i).split(",");
                for (int t = 0; t<ary_vid_rabot.length;t++){
                    new_list_vid_rabot.add(ary_vid_rabot[t]);
                }
            }else{
                new_list_vid_rabot.add(list_vid_rabot.get(i));
            }
        }

        for (int i = 0; i<new_list_marka.size();i++){
            ary_model = null;
            if (new_list_marka.get(i).contains(" ")){
                String g = new_list_marka.get(i);
                g = g.replaceAll(" ", "");
                new_list_marka.set(i,g);
            }
            //Log.d(MainActivity.LOG_TAG,"new = " + new_list_marka.get(i));
        }
        list_marka = new_list_marka;
        HashSet<String> hash_marka = new HashSet<>();
        hash_marka.addAll(list_marka);
        list_marka.clear();
        list_marka.addAll(hash_marka);

        for (int i = 0; i<new_list_model.size();i++){
            if (new_list_model.get(i).contains(" ")){
                String g = new_list_model.get(i);
                g = g.replaceAll(" ", "");
                new_list_model.set(i,g);
            }
            //Log.d(MainActivity.LOG_TAG,"new = " + new_list_marka.get(i));
        }
        list_model = new_list_model;
        HashSet<String> hash_model = new HashSet<>();
        hash_model.addAll(list_model);
        list_model.clear();
        list_model.addAll(hash_model);

        for (int i = 0; i<new_list_vid_rabot.size();i++){
            if (new_list_vid_rabot.get(i).contains(" ")){
                String g = new_list_vid_rabot.get(i);
                g = g.replaceAll(" ", "");
                new_list_vid_rabot.set(i,g);
            }
            //Log.d(MainActivity.LOG_TAG,"new = " + new_list_marka.get(i));
        }
        list_vid_rabot = new_list_vid_rabot;
        HashSet<String> hash_vid_rabot = new HashSet<>();
        hash_vid_rabot.addAll(list_vid_rabot);
        list_vid_rabot.clear();
        list_vid_rabot.addAll(hash_vid_rabot);

        list_marka = sortList(list_marka);
        list_model = sortList(list_model);
        list_vid_rabot = sortList(list_vid_rabot);
        list_metro = sortList(list_metro);
        list_okrug = sortList(list_okrug);
        list_rayon = sortList(list_rayon);

        refreshAdapter();

    }

    private void refreshAdapter() {
        adapter_marka = new ArrayAdapter<>(FindActivity.this,android.R.layout.simple_list_item_1,list_marka);
        adapter_model = new ArrayAdapter<>(FindActivity.this,android.R.layout.simple_list_item_1,list_model);
        adapter_okrug = new ArrayAdapter<>(FindActivity.this,android.R.layout.simple_list_item_1,list_okrug);
        adapter_rayon = new ArrayAdapter<>(FindActivity.this,android.R.layout.simple_list_item_1,list_rayon);
        adapter_metro = new ArrayAdapter<>(FindActivity.this,android.R.layout.simple_list_item_1,list_metro);
        adapter_vid_rabot = new ArrayAdapter<>(FindActivity.this,android.R.layout.simple_list_item_1,list_vid_rabot);

        sp_1.setAdapter(adapter_marka);
        sp_2.setAdapter(adapter_model);
        sp_3.setAdapter(adapter_okrug);
        sp_4.setAdapter(adapter_rayon);
        sp_5.setAdapter(adapter_metro);
        sp_6.setAdapter(adapter_vid_rabot);
    }

    private void findLogic() {
        marka = sp_1.getSelectedItem().toString();
        model = sp_2.getSelectedItem().toString();
        okrug = sp_3.getSelectedItem().toString();
        rayon = sp_4.getSelectedItem().toString();
        metro = sp_5.getSelectedItem().toString();
        vid_rabot = sp_6.getSelectedItem().toString();
        generatorFindList();
    }

    private void generatorFindList() {

        find_list_autoservices = new ArrayList<>();


        for(int i = 0; i<list.size();i++){
            if (list.get(i).getMarka().contains(marka)||marka.contains("ВСЕ")){
                if (list.get(i).getModel().contains(model)||model.contains("ВСЕ")){
                    if (list.get(i).getOkrug().contains(okrug)||okrug.contains("Любой город")){
                        if (list.get(i).getRayon().contains(rayon)||rayon.contains("Любой район")){
                            if (list.get(i).getMetro().contains(metro)||metro.contains("се станции метро")){
                                if (list.get(i).getVid_rabot().contains(vid_rabot)||vid_rabot.contains("ВСЕ")){
                                    find_list_autoservices.add(list.get(i));
                                }
                            }
                        }
                    }
                }
            }
        }


        HashSet<Autoservice> hashAutoservices = new HashSet<>();
        hashAutoservices.addAll(find_list_autoservices);
        find_list_autoservices.clear();
        find_list_autoservices.addAll(hashAutoservices);

        Intent intent = new Intent(FindActivity.this,MainActivity.class);
        intent.putExtra("list",find_list_autoservices);
        startActivity(intent);
    }

    private void showAllAutoserviceOnTheMap() {
        Intent intent = new Intent(FindActivity.this,MapsActivity.class);
        intent.putExtra("list",list);
        intent.putExtra("listAdresses",listAdresses);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.idItem1){
            Intent intent = new Intent(FindActivity.this,AboutApplication.class);
            startActivity(intent);
        }else if (id == R.id.idItem2){
            Intent intent = new Intent(FindActivity.this,HowFinds.class);
            startActivity(intent);
        }else if (id == R.id.idItem0){
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT,"Скачивай и устанавливай приложение BestAutoservices - все автосервисы твоего города у тебя в кармане! https://yadi.sk/d/QiCyatbh3P32j6");
            sharingIntent.setPackage("com.viber.voip");
            startActivity(sharingIntent);
        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
