package com.bingerdranch.android.bestautoservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class AddComment extends AppCompatActivity {

    private Button button_add_otziv_ok;
    private Button button_add_otziv_cancel;

    private EditText edit_text_otziv;

    private Spinner spinner_rating;
    private ArrayList <String> spinner_array;

    private Autoservice autoservice;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private String old_comment;
    private String old_numOfRating = "";

    private ArrayList<Integer>array_num_of_rating; // тут весь рейтинг за все время
    private int num_of_rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        autoservice = (Autoservice) intent.getSerializableExtra("autoservice");

        edit_text_otziv = (EditText) findViewById(R.id.edit_text_otziv);
        spinner_rating = (Spinner) findViewById(R.id.spinner_rating);
        spinner_array = new ArrayList<>();
        for (int i = 0; i<=5;i++){
            spinner_array.add(i+"");
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,spinner_array);
        spinner_rating.setAdapter(adapter);

        button_add_otziv_ok = (Button)findViewById(R.id.button_add_otziv_ok);
        button_add_otziv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                butonOK(edit_text_otziv.getText().toString());
            }
        });

        button_add_otziv_cancel = (Button) findViewById(R.id.button_add_otziv_cancel);
        button_add_otziv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCancel();
            }
        });
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        reference.child(autoservice.getName()).child("otzivi").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                old_comment = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        reference.child(autoservice.getName()).child("numOfRating").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                old_numOfRating = dataSnapshot.getValue().toString();

                char [] arr = new char[old_numOfRating.length()];
                array_num_of_rating = new ArrayList<>();
                arr = old_numOfRating.toCharArray();
                for (int i = 0; i<arr.length;i++){
                    int k = Integer.parseInt(""+arr[i]);
                    array_num_of_rating.add(k);
                    //Log.d(MainActivity.LOG_TAG," rating = " + old_numOfRating);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void butonOK(String edit_text_string) {
            reference.child(autoservice.getName()).child("otzivi").setValue(old_comment + " \n " + edit_text_string);
            // тут рейтинг берется
            int select_rating = Integer.parseInt(spinner_rating.getSelectedItem().toString());
            array_num_of_rating.add(select_rating);
            int k = 0;
            String new_numOfRating = "";
            for (int i = 0; i<array_num_of_rating.size();i++){
                k = k + array_num_of_rating.get(i);
                new_numOfRating = new_numOfRating + array_num_of_rating.get(i);
            }
            int k1 = k / array_num_of_rating.size();
            reference.child(autoservice.getName()).child("numOfRating").setValue(new_numOfRating);
            reference.child(autoservice.getName()).child("rating").setValue(k1);
            // тут будет устанавливаться новый рейтинг
            reference.push();
            finish();
    }

    private void buttonCancel() {
        finish();
    }
}
