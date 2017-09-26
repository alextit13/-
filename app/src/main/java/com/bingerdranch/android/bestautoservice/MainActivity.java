package com.bingerdranch.android.bestautoservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "MyLogs";
    private ListView list_view;

    private ArrayList<Autoservice>list;// тут находятся все автосервисы, которые отобрались по параметрам поиска

    private ArrayList<String> list_marka;

    private CustomAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        Intent intent = getIntent();
        list = (ArrayList<Autoservice>) intent.getSerializableExtra("list");

        list_view = (ListView) findViewById(R.id.list_view);
        list_marka = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            list_marka.add(list.get(i).getMarka());
        }
        adapter = new CustomAdapter(this,R.layout.item_adapter,list);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickItem(position);
            }
        });
    }

    private void clickItem(int position) {
        Intent intent = new Intent(MainActivity.this,Item.class);
        intent.putExtra("name",list.get(position).getName());
        intent.putExtra("marka",list.get(position).getMarka());
        intent.putExtra("model",list.get(position).getModel());
        intent.putExtra("okrug",list.get(position).getOkrug());
        intent.putExtra("rayon",list.get(position).getRayon());
        intent.putExtra("metro",list.get(position).getMetro());
        intent.putExtra("adress",list.get(position).getAdress());
        intent.putExtra("number",list.get(position).getNumber());
        intent.putExtra("vid_rabot",list.get(position).getVid_rabot());
        intent.putExtra("otzivi",list.get(position).getOtzivi());
        startActivity(intent);
    }
}
