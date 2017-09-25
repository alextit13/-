package com.bingerdranch.android.bestautoservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class FindActivity extends AppCompatActivity {

    private ArrayList<Autoservice> list;
    private Button find_button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        init();
        downloadData();
        updateAdapters();
        refreshAdapter();
    }

    private void init() {

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

        find_button = (Button) findViewById(R.id.button_find);
        find_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findLogic();
            }
        });

    }

    private void downloadData() {
        Intent intent = getIntent();
        list = (ArrayList<Autoservice>) intent.getSerializableExtra("list");

        for (int i = 0; i<list.size();i++){
            //Log.d(MainActivity.LOG_TAG,"list = "+ list.get(i).getName());
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

        Intent intent = new Intent(FindActivity.this,MainActivity.class);
        intent.putExtra("test","test");
        startActivity(intent);
    }
}
