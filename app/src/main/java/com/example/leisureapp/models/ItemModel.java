package com.example.leisureapp.models;

// Model for holding stack data item
public class ItemModel {
    // EXAMPLE DATA name
    private String name;
    private String key;
    private String activity;

    public ItemModel() {
    }

    public ItemModel(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return this.name;
    }

    public String getActivity() {return  this.activity;}

    public String getKey() {return  this.key;}


    public void setName(String name) {
        this.name = name;
    }

    public void setActivity(String activity) {this.activity = activity;}

    public void setKey(String key) {
        this.key = key;
    }

}
