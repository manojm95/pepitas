package com.apptrovertscube.manojprabahar.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.victor.loading.newton.NewtonCradleLoading;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MonthView extends AppCompatActivity {


    private RecyclerView rv;
    int m;
    int yy;
    long curdate = 0;
    String whatmonth="";

    private  TextView mmmm;
    private  TextView mmyy;

    private List<MonthForRecyc> monthList = new ArrayList<>();

    private RecycAdapterMonth mAdapter;

    private NewtonCradleLoading ncl;

    String curmonth;
    String pastmonth="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_view);
        m = Calendar.getInstance().get(Calendar.MONTH);
        yy = Calendar.getInstance().get(Calendar.YEAR);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        curdate = c.getTimeInMillis();
        mmmm = (TextView)findViewById(R.id.mmmm);
        mmyy = (TextView)findViewById(R.id.mmyy);
        ncl = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loadingmonth);
        mmyy.setText(String.valueOf(yy));
        findViewById(R.id.monthgoback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MonthView.this,MainActivity.class));
                finish();
            }
        });

        Log.d("vcxzas","mmmmmmm------>"+m);

        m=m+1;
        if(String.valueOf(m).length()==1)
        {
            curmonth = 0+ String.valueOf(m);
        }
        else
        {
            curmonth = String.valueOf(m);
        }
        rv = (RecyclerView)findViewById(R.id.recycler_view);

        if(getIntent().getStringExtra("whatmonth")!=null) {
            whatmonth = getIntent().getStringExtra("whatmonth");
            if(whatmonth.equals("pastmonth")){
                pastmonth = getIntent().getStringExtra("mmyy");
            }
        }
        mAdapter = new RecycAdapterMonth(monthList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(mLayoutManager);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(this,rv,new RecyclerItemClickListener.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                TextView tv = (TextView)view.findViewById(R.id.recycfirstletter);
                TextView total = (TextView)view.findViewById(R.id.recyctotal);
                Toast.makeText(getApplicationContext(),"The value is "+tv.getTag(),Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MonthView.this, DailyReceiptActivity.class);
                i.putExtra("dateval", String.valueOf(tv.getTag()));
                i.putExtra("sourceval", String.valueOf(total.getTag()));
                startActivity(i);
                finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(mAdapter);

        prepareMovieData();
    }

    private void prepareMovieData() {

        if(whatmonth.equals("thismonth"))
        {
            mmmm.setText(LoginActivity.month[m-1]);

            ncl.start();
            ncl.setLoadingColor(Color.parseColor("#707B7C"));
            ParseQuery<ParseObject> query = ParseQuery.getQuery("SelectedItems");
            query.whereEqualTo("month",curmonth);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e==null)
                    {
                        String h;
                        for (ParseObject o : objects)
                        {
                            String j = String.valueOf(o.get("id"));
                            if(j.length()==7) h = 0 + j;
                            else h = j;
                            //Log.d("MonthViewManoj",h);
                            monthList.add(new MonthForRecyc(h.substring(0,2)+"-"+LoginActivity.month[Integer.parseInt(h.substring(2,4))-1],
                                    String.valueOf(o.get("total")), h,"pastmonth"));
                        }
                        ncl.stop();
                        ncl.setVisibility(View.INVISIBLE);
                        mAdapter.notifyDataSetChanged();
                    }
                    else
                        ncl.stop();
                    ncl.setVisibility(View.INVISIBLE);
                }
            });

//            Realm realm = Realm.getDefaultInstance();
//
//            RealmResults<Selecttable> st = realm.where(Selecttable.class)
//                    .equalTo("month",curmonth)
//                    .findAll();
//            String h;
//            for (Selecttable s : st) {
//                String j = String.valueOf(s.getId());
//                if(j.length()==7) h = 0 + j;
//                else h = j;
//                Log.d("thismonth",s.getId()+"pppppppp"+(Integer.parseInt(h.substring(2,4))-1)+"");
//
//                monthList.add(new MonthForRecyc(h.substring(0,2)+"-"+LoginActivity.month[Integer.parseInt(h.substring(2,4))-1], s.getTotal(), h,"thismonth"));
//            }
//            realm.close();
            mAdapter.notifyDataSetChanged();

        }else if(whatmonth.equals("futuremonths"))
        {
            ncl.start();
            ncl.setLoadingColor(Color.parseColor("#707B7C"));
            mmmm.setText("Forthcoming");
            mmyy.setText("Orders");
            ParseQuery<ParseObject> query = ParseQuery.getQuery("SelectedItems");
            query.whereGreaterThan("actualdate",curdate);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e==null)
                    {
                        String h;
                        for (ParseObject o : objects)
                        {
                            String j = String.valueOf(o.get("id"));
                            if(j.length()==7) h = 0 + j;
                            else h = j;
                            Log.d("MonthViewManoj",h);
                            monthList.add(new MonthForRecyc(h.substring(0,2)+"-"+LoginActivity.month[Integer.parseInt(h.substring(2,4))-1],
                                    String.valueOf(o.get("total")), h,"futuremonths"));
                        }
                        ncl.stop();
                        ncl.setVisibility(View.INVISIBLE);
                        mAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        ncl.stop();
                        ncl.setVisibility(View.INVISIBLE);
                    }
                }
            });
        } else if(whatmonth.equals("pastmonth"))
        {
            String mmm="0";
            String yyy="0";
            if(pastmonth.length()==5)
            {
                mmm = "0"+pastmonth.substring(0,1);
                yyy = pastmonth.substring(1,5);
            }

            else if(pastmonth.length()==6)
            {
                mmm = pastmonth.substring(0,2);
                yyy = pastmonth.substring(2,6);
            }

            ncl.start();
            ncl.setLoadingColor(Color.parseColor("#707B7C"));

            mmmm.setText(LoginActivity.month[Integer.parseInt(mmm)-1]);

            ParseQuery<ParseObject> query = ParseQuery.getQuery("SelectedItems");
            query.whereEqualTo("month",mmm);
            query.whereEqualTo("year",yyy);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e==null)
                    {
                        String h;
                        for (ParseObject o : objects)
                        {
                            String j = String.valueOf(o.get("id"));
                            if(j.length()==7) h = 0 + j;
                            else h = j;
                            //Log.d("MonthViewManoj",h);
                            monthList.add(new MonthForRecyc(h.substring(0,2)+"-"+LoginActivity.month[Integer.parseInt(h.substring(2,4))-1],
                                    String.valueOf(o.get("total")), h,"pastmonth"));
                        }
                        ncl.stop();
                        ncl.setVisibility(View.INVISIBLE);
                        mAdapter.notifyDataSetChanged();
                    }
                    else
                        ncl.stop();
                    ncl.setVisibility(View.INVISIBLE);
                }
            });

        }
//        else {
//
//            monthList.add(new MonthForRecyc("11-July", "100.20", "07112017",""));
//            monthList.add(new MonthForRecyc("12-July", "200.40", "07122017",));
//            monthList.add(new MonthForRecyc("13-July", "400.50", "07132017"));
//            mAdapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MonthView.this,MainActivity.class));
        finish();
    }
}
