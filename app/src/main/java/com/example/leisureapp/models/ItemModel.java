package com.example.leisureapp.models;

// Model for holding stack data item
public class ItemModel {
    // EXAMPLE DATA name
    private String name;
    private String activity;

    public ItemModel() {
    }

    public ItemModel(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getActivity() {return  this.activity;}

    public void setName(String name) {
        this.name = name;
    }

    public void setActivity(String activity) {this.activity = activity;}
}
