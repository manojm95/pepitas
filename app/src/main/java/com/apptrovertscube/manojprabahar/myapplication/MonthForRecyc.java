package com.apptrovertscube.manojprabahar.myapplication;

/**
 * Created by mna on 8/6/17.
 */

public class MonthForRecyc {
    public MonthForRecyc() {
    }

    private String day, total, date, source;

    public MonthForRecyc(String day, String total, String date, String source) {
        this.day = day;
        this.total = total;
        this.date = date;
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
