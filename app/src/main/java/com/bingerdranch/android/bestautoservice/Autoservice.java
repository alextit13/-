package com.bingerdranch.android.bestautoservice;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Autoservice {
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
    private String reiting;
    private double X;
    private double Y;
    private int numOfRating;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListeber;

    public Autoservice() {
    }

    public Autoservice(String name, String marka, String model, String number, String okrug, String rayon, String metro, String adress, String vid_rabot, String otzivi, String reiting, int numOfRating) {
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
        this.reiting = reiting;
        this.numOfRating = numOfRating;
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

    public String getReiting() {
        return reiting;
    }

    public void setReiting(String reiting) {
        this.reiting = reiting;
    }

    public int getNumOfRating() {
        return numOfRating;
    }

    public void setNumOfRating(int numOfRating) {
        this.numOfRating = numOfRating;
    }
}
