package com.example.leisureapp.models;

import android.content.ClipData;
import android.content.Context;

import java.lang.reflect.Constructor;

public class ItemModel {
    private String activity;
    private String type;
    private int participants;
    private double price;
    private String link;
    private String key;
    private double accessibility;
    private String imgURL;

    public ItemModel(String activity, String type, int participants, double price, String link, String key, double accessibility, String imgURL) {
        this.activity = activity;
        this.type = type;
        this.participants = participants;
        this.price = price;
        this.link = link;
        this.key = key;
        this.accessibility = accessibility;
        this.imgURL = imgURL;
    }

    public String getActivity() {
        return activity;
    }
    public String getType() {
        return type;
    }
    public int getParticipants() {
        return participants;
    }
    public double getPrice() {
        return price;
    }
    public String getLink() {
        return link;
    }
    public String getKey() {
        return key;
    }
    public double getAccessibility() {
        return accessibility;
    }
    public String getImgURL() {
        return imgURL;
    }
}
