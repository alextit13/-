package com.bingerdranch.android.bestautoservice;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Autoservice> {

    private Autoservice autoservice;
    private TextView tv_name;
    private TextView adress;

    private TextView text_name;

    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            tv_name = (TextView)convertView.findViewById(R.id.text_name);
            adress = (TextView)convertView.findViewById(R.id.adress);
            autoservice = getItem(position);
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_adapter,parent,false);
            tv_name.setText(autoservice.getName());
            adress.setText(autoservice.getAdress());
        }
        return convertView;
    }
}