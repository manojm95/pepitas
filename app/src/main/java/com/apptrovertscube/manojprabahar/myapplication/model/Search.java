package com.apptrovertscube.manojprabahar.myapplication.model;

/**
 * Created by mna on 8/6/17.
 */

public class Search {
    public Search() {
    }

    private String name, price, holder;

    public Search(String name, String price, String holder) {
        this.name = name;
        this.price = price;
        this.holder = holder;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }
}
