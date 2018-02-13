package com.bingerdranch.android.bestautoservice;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView list_view;
    private ArrayList<Autoservice>list;// тут находятся все автосервисы, которые отобрались по параметрам поиска
    private ArrayList<String> list_marka;
    private CustomAdapter adapter;
    public static final String LOG_TAG = "MyLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {

        ((ImageView)findViewById(R.id.back_main_list))
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }
                );

        String custom_font = "font/amarante_regular.ttf";
        Typeface CF = Typeface.createFromAsset(getAssets(), custom_font);

        ((TextView) findViewById(R.id.main_list_toolbar_text)).setTypeface(CF);

        Intent intent = getIntent();
        list = (ArrayList<Autoservice>) intent.getSerializableExtra("list");

        list_view = (ListView) findViewById(R.id.list_view);
        list_marka = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            list_marka.add(list.get(i).getMarka());
        }
        adapter = new CustomAdapter(this,R.layout.test,list,MainActivity.this);
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
        intent.putExtra("list",list.get(position));
        startActivity(intent);
    }
}
