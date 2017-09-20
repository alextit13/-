package com.bingerdranch.android.bestautoservice;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
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
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Autoservice> {

    private AlertDialog.Builder builder;
    private Autoservice autoservice;

    private FirebaseDatabase mFirebaseDatabase;
    private String old_otzivi = "";

    private ImageView rating_1;
    private ImageView rating_2;
    private ImageView rating_3;
    private ImageView rating_4;
    private ImageView rating_5;

    private String text;

    private int ITOG_RATING;

    private DatabaseReference dbr;

    private String rating = "0";
    private int numOfRating = 0;


    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_adapter,parent,false);
        }

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        TextView tv_name = (TextView)convertView.findViewById(R.id.text_name);
        TextView adress = (TextView)convertView.findViewById(R.id.adress);

        final TextView image_view_add_otziv = (TextView) convertView.findViewById(R.id.image_view_add_otziv);
        autoservice = getItem(position);
        tv_name.setText(autoservice.getName());
        adress.setText(autoservice.getAdress());
        image_view_add_otziv.setText("Добавить отзыв " + autoservice.getName()+"");

        DatabaseReference d = mFirebaseDatabase.getReference().child(getItem(position).getName()).child("rating");
        final View finalConvertView = convertView;
        d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rating = dataSnapshot.getValue().toString();
                settingsRating(rating, finalConvertView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d(MainActivity.LOG_TAG,"rootView 2 = " +rating);
        image_view_add_otziv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = image_view_add_otziv.getText().toString();
                text = text.substring(15);
                dbr = mFirebaseDatabase.getReference().child(text).child("otzivi");

                dbr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        old_otzivi = dataSnapshot.getValue().toString();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                clickAddOtziv(getItem(position));
            }
        });
        return convertView;
    }

    public void settingsRating(String rating_to_settings,View convert) {

        rating_1 = (ImageView) convert.findViewById(R.id.rating_1);
        rating_2 = (ImageView) convert.findViewById(R.id.rating_2);
        rating_3 = (ImageView) convert.findViewById(R.id.rating_3);
        rating_4 = (ImageView) convert.findViewById(R.id.rating_4);
        rating_5 = (ImageView) convert.findViewById(R.id.rating_5);

        if (ITOG_RATING!=0){
            rating_to_settings = ITOG_RATING+"";
        }

        switch (rating_to_settings){
            case "0":
                break;
            case "1":
                rating_1.setImageResource(R.drawable.rating_not_empty);
                break;
            case "2":
                rating_1.setImageResource(R.drawable.rating_not_empty);
                rating_2.setImageResource(R.drawable.rating_not_empty);
                break;
            case "3":
                rating_1.setImageResource(R.drawable.rating_not_empty);
                rating_2.setImageResource(R.drawable.rating_not_empty);
                rating_3.setImageResource(R.drawable.rating_not_empty);
                break;
            case "4":
                rating_1.setImageResource(R.drawable.rating_not_empty);
                rating_2.setImageResource(R.drawable.rating_not_empty);
                rating_3.setImageResource(R.drawable.rating_not_empty);
                rating_4.setImageResource(R.drawable.rating_not_empty);
                break;
            case "5":
                rating_1.setImageResource(R.drawable.rating_not_empty);
                rating_2.setImageResource(R.drawable.rating_not_empty);
                rating_3.setImageResource(R.drawable.rating_not_empty);
                rating_4.setImageResource(R.drawable.rating_not_empty);
                rating_5.setImageResource(R.drawable.rating_not_empty);
                break;
        }
    }

    private void clickAddOtziv(final Autoservice autoservice_from_adapter) {
        final LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
        builder = new AlertDialog.Builder(getContext());
        View dialogView = inflater.inflate(R.layout.dialog, null);
        final EditText edit_text_comment = (EditText) dialogView.findViewById(R.id.edit_text_comment);
        final ImageView zv_1 = (ImageView)dialogView.findViewById(R.id.zv_1);
        final ImageView zv_2 = (ImageView)dialogView.findViewById(R.id.zv_2);
        final ImageView zv_3 = (ImageView)dialogView.findViewById(R.id.zv_3);
        final ImageView zv_4 = (ImageView)dialogView.findViewById(R.id.zv_4);
        final ImageView zv_5 = (ImageView)dialogView.findViewById(R.id.zv_5);
        zv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zv_1.setImageResource(R.drawable.rating_not_empty);
                zv_2.setImageResource(R.drawable.rating_empty);
                zv_3.setImageResource(R.drawable.rating_empty);
                zv_4.setImageResource(R.drawable.rating_empty);
                zv_5.setImageResource(R.drawable.rating_empty);
                addRating(1);
            }
        });
        zv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zv_1.setImageResource(R.drawable.rating_not_empty);
                zv_2.setImageResource(R.drawable.rating_not_empty);
                zv_3.setImageResource(R.drawable.rating_empty);
                zv_4.setImageResource(R.drawable.rating_empty);
                zv_5.setImageResource(R.drawable.rating_empty);
                addRating(2);
            }
        });
        zv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zv_1.setImageResource(R.drawable.rating_not_empty);
                zv_2.setImageResource(R.drawable.rating_not_empty);
                zv_3.setImageResource(R.drawable.rating_not_empty);
                zv_4.setImageResource(R.drawable.rating_empty);
                zv_5.setImageResource(R.drawable.rating_empty);
                addRating(3);
            }
        });
        zv_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zv_1.setImageResource(R.drawable.rating_not_empty);
                zv_2.setImageResource(R.drawable.rating_not_empty);
                zv_3.setImageResource(R.drawable.rating_not_empty);
                zv_4.setImageResource(R.drawable.rating_not_empty);
                zv_5.setImageResource(R.drawable.rating_empty);
                addRating(4);
            }
        });
        zv_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zv_1.setImageResource(R.drawable.rating_not_empty);
                zv_2.setImageResource(R.drawable.rating_not_empty);
                zv_3.setImageResource(R.drawable.rating_not_empty);
                zv_4.setImageResource(R.drawable.rating_not_empty);
                zv_5.setImageResource(R.drawable.rating_not_empty);
                addRating(5);
            }
        });
        builder.setView(dialogView)
                .setTitle("Добавить отзыв")
                .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!edit_text_comment.getText().toString().equals("")){
                            dbr.setValue(old_otzivi + " \n " + edit_text_comment.getText().toString());
                        }
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setIcon(R.drawable.ic_person_add_black_24dp);
        builder.create().show();
    }

    private String addRating(final int numZv) {
        final int rating_int = Integer.parseInt(rating);
        String final_rating = "";
        DatabaseReference database = mFirebaseDatabase.getReference().child(text).child("numOfRating");
        Log.d(MainActivity.LOG_TAG,"numZv = "+numZv); // тут то что мы нажали - сколько рейтинга дали
        Log.d(MainActivity.LOG_TAG,"rating_int = " + rating_int); // тут сколькорейтинга есть - средний рейтинг
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int dataSnapshotInt = Integer.parseInt(dataSnapshot.getValue()+"");// тут количество нажатых рейтингов
                dataSnapshotInt = dataSnapshotInt + 1;

                ITOG_RATING = ((dataSnapshotInt*rating_int)+numZv)/(dataSnapshotInt+1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d(MainActivity.LOG_TAG,ITOG_RATING+"");
        return ITOG_RATING+"";
    }
}