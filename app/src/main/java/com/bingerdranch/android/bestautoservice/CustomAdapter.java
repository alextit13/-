package com.bingerdranch.android.bestautoservice;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Autoservice> {

    private Autoservice autoservice;
    private TextView text_name,number_phone,marka_t,adress_t,tv_details;
    private RatingBar ratingBar;
    private Context context;

    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects,Context ctx) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        autoservice = getItem(position);
        convertView = ((Activity)getContext())
                .getLayoutInflater().inflate(R.layout.test,parent,false);

        ((TextView)convertView.findViewById(R.id.text_name)).setText(autoservice.getName());
        ((TextView)convertView.findViewById(R.id.number_phone)).setText(autoservice.getNumber());
        ((TextView)convertView.findViewById(R.id.marka_t)).setText(autoservice.getMarka());
        ((TextView)convertView.findViewById(R.id.adress_t)).setText(autoservice.getAdress());

        Picasso.with(context).load("https://repairpal.com/images/managed/service_location/393/117393/images/26305_full.jpg")
                .into((ImageView)convertView.findViewById(R.id.image_autoservices));

        return convertView;
    }
}