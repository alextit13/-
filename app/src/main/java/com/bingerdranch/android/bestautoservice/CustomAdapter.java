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
    private TextView number;
    private TextView marka;
    private TextView num_otziv_schet;

    private ImageView zv_adapter_1;
    private ImageView zv_adapter_2;
    private ImageView zv_adapter_3;
    private ImageView zv_adapter_4;
    private ImageView zv_adapter_5;

    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        autoservice = getItem(position);
        convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_adapter,parent,false);
        tv_name = (TextView)convertView.findViewById(R.id.text_name);
        adress = (TextView)convertView.findViewById(R.id.adress);

        zv_adapter_1 = (ImageView) convertView.findViewById(R.id.zv_adapter_1);
        zv_adapter_2 = (ImageView) convertView.findViewById(R.id.zv_adapter_2);
        zv_adapter_3 = (ImageView) convertView.findViewById(R.id.zv_adapter_3);
        zv_adapter_4 = (ImageView) convertView.findViewById(R.id.zv_adapter_4);
        zv_adapter_5 = (ImageView) convertView.findViewById(R.id.zv_adapter_5);

        number = (TextView)convertView.findViewById(R.id.number);
        marka = (TextView)convertView.findViewById(R.id.marka);
        num_otziv_schet = (TextView) convertView.findViewById(R.id.num_otziv_schet);

        setRating(autoservice.getRating());
        setNumberOfPhone(autoservice.getNumber());
        setMarka(autoservice.getMarka());
        setNumOfRatingSize(autoservice.getNumOfRating().length());


        tv_name.setText(autoservice.getName());
        adress.setText(autoservice.getAdress());
        return convertView;
    }

    private void setRating(int rating) {
        switch (rating){
            case 0:
                zv_adapter_1.setImageResource(R.drawable.rating_empty);
                zv_adapter_2.setImageResource(R.drawable.rating_empty);
                zv_adapter_3.setImageResource(R.drawable.rating_empty);
                zv_adapter_4.setImageResource(R.drawable.rating_empty);
                zv_adapter_5.setImageResource(R.drawable.rating_empty);
                break;
            case 1:
                zv_adapter_2.setImageResource(R.drawable.rating_empty);
                zv_adapter_3.setImageResource(R.drawable.rating_empty);
                zv_adapter_4.setImageResource(R.drawable.rating_empty);
                zv_adapter_5.setImageResource(R.drawable.rating_empty);
                break;
            case 2:
                zv_adapter_3.setImageResource(R.drawable.rating_empty);
                zv_adapter_4.setImageResource(R.drawable.rating_empty);
                zv_adapter_5.setImageResource(R.drawable.rating_empty);
                break;
            case 3:
                zv_adapter_4.setImageResource(R.drawable.rating_empty);
                zv_adapter_5.setImageResource(R.drawable.rating_empty);
                break;
            case 4:
                zv_adapter_5.setImageResource(R.drawable.rating_empty);
                break;
            case 5:
                break;
        }
    }

    private void setNumberOfPhone(String number_phone) {
        number.setText(number_phone);
    }

    private void setMarka(String marka_auto) {
        marka.setText(marka_auto);
    }

    private void setNumOfRatingSize(int length) {
        num_otziv_schet.setText(length+"");
    }
}