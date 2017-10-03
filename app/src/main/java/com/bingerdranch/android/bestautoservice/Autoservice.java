package com.bingerdranch.android.bestautoservice;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class Autoservice implements Serializable{
    private String name;
    private String marka;
    private String model;
    private String number;
    private String okrug;
    private String rayon;
    private String metro;
    private String adress;
    private String vid_rabot;
    private String otzivi;
    private int rating;
    private String grafik_raboti;
    private double X;
    private double Y;
    private String numOfRating;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListeber;

    public Autoservice() {
    }

    public Autoservice(String name, String marka, String model, String number, String okrug, String rayon, String metro, String adress, String vid_rabot, String otzivi, int rating, String numOfRating, String grafik_raboti) {
        this.name = name;
        this.marka = marka;
        this.model = model;
        this.number = number;
        this.okrug = okrug;
        this.rayon = rayon;
        this.metro = metro;
        this.adress = adress;
        this.vid_rabot = vid_rabot;
        this.otzivi = otzivi;
        this.rating = rating;
        this.numOfRating = numOfRating;
        this.grafik_raboti = grafik_raboti;
    }

    protected Autoservice(Parcel in) {
        name = in.readString();
        marka = in.readString();
        model = in.readString();
        number = in.readString();
        okrug = in.readString();
        rayon = in.readString();
        metro = in.readString();
        adress = in.readString();
        vid_rabot = in.readString();
        otzivi = in.readString();
        rating = in.readInt();
        X = in.readDouble();
        Y = in.readDouble();
        numOfRating = in.readString();
        grafik_raboti = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOkrug() {
        return okrug;
    }

    public void setOkrug(String okrug) {
        this.okrug = okrug;
    }

    public String getRayon() {
        return rayon;
    }

    public void setRayon(String rayon) {
        this.rayon = rayon;
    }

    public String getMetro() {
        return metro;
    }

    public void setMetro(String metro) {
        this.metro = metro;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getVid_rabot() {
        return vid_rabot;
    }

    public void setVid_rabot(String vid_rabot) {
        this.vid_rabot = vid_rabot;
    }

    public String getOtzivi() {
        return otzivi;
    }

    public void setOtzivi(String otzivi) {
        this.otzivi = otzivi;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getNumOfRating() {
        return numOfRating;
    }

    public void setNumOfRating(String numOfRating) {
        this.numOfRating = numOfRating;
    }

    public String getGrafik_raboti() {
        return grafik_raboti;
    }

    public void setGrafik_raboti(String grafik_raboti) {
        this.grafik_raboti = grafik_raboti;
    }
}
