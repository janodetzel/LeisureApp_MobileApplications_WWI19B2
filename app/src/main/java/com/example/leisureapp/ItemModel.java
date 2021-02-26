package com.example.leisureapp;

// Model for holding stack data item
public class ItemModel {
    // EXAMPLE DATA name
    private String name;

    public ItemModel() {
    }

    public ItemModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
