package com.example.leisureapp;

import android.content.Context;

class FavCard {

    public int activityID;
    public String activity;
    public String costs;
    public String persons;
    public String topic;
    public String picURL;
    public Context context;

    public FavCard(int id, String activity, String costs, String persons, String topic, String picURL, Context context) {
        this.activityID = id;
        this.activity = activity;
        this.costs = costs;
        this.persons = persons;
        this.topic = topic;
        this.picURL = picURL;
        this.context = context;
    }

}
