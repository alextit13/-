package com.bingerdranch.android.bestautoservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "MyLogs";
    private ListView list_view;
    private List <Autoservice> list;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListeber;

    private CustomAdapter customAdapter;
    private Autoservice autoservice;

    private Button search_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_view = (ListView) findViewById(R.id.list_view);
        search_activity = (Button) findViewById(R.id.search_activity);

        search_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //открытие активити поиска
            }
        });

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        if (mChildEventListeber == null){
            mChildEventListeber = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    autoservice = dataSnapshot.getValue(Autoservice.class);
                    customAdapter.add(autoservice);
                    Log.d(LOG_TAG,dataSnapshot.getValue(Autoservice.class).getReiting()+"");
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
        list = new ArrayList<>();
        customAdapter = new CustomAdapter(this,R.layout.item_adapter,list);
        list_view.setAdapter(customAdapter);
        list_view.setEmptyView(findViewById(R.id.PB));
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
