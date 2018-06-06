package com.apptrovertscube.manojprabahar.myapplication;

import io.realm.RealmObject;

/**
 * Created by mna on 7/17/17.
 */

public class Selecttable extends RealmObject {
    private int id;
    private String items;
    private String price;
    private String total;
    private int entrydate;
    private int updatedate;
    private int version;
    private String qty;
    private String month;
    private String year;
    private int reserveint;
    private int reserveint2;
    private String reserveString;
    private String reserveString2;

    public Selecttable() {
    }

    public Selecttable(int id, String items, String price, String total, int entrydate, int updatedate, int version, String  qty, String month, String year,
                       int reserveint, int reserveint2, String reserveString, String reserveString2) {
        this.id = id;
        this.items = items;
        this.price = price;
        this.total = total;
        this.entrydate = entrydate;
        this.updatedate = updatedate;
        this.version = version;
        this.qty = qty;
        this.month = month;
        this.year = year;
        this.reserveint = reserveint;
        this.reserveint2 = reserveint2;
        this.reserveString = reserveString;
        this.reserveString2 = reserveString2;
    }

    public String getQty() {
        return qty;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public int getEntrydate() {
        return entrydate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setEntrydate(int entrydate) {
        this.entrydate = entrydate;
    }

    public int getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(int updatedate) {
        this.updatedate = updatedate;
    }

    public int getReserveint() {
        return reserveint;
    }

    public void setReserveint(int reserveint) {
        this.reserveint = reserveint;
    }

    public int getReserveint2() {
        return reserveint2;
    }

    public void setReserveint2(int reserveint2) {
        this.reserveint2 = reserveint2;
    }

    public String getReserveString() {
        return reserveString;
    }

    public void setReserveString(String reserveString) {
        this.reserveString = reserveString;
    }

    public String getReserveString2() {
        return reserveString2;
    }

    public void setReserveString2(String reserveString2) {
        this.reserveString2 = reserveString2;
    }
}
