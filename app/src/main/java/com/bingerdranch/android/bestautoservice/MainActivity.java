package com.bingerdranch.android.bestautoservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "MyLogs";
    private ListView list_view;
    private ArrayList<Autoservice>list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list_marka;



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
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list_marka);
        list_view.setAdapter(adapter);
    }

    private void clickItem(int position) {

    }
}
