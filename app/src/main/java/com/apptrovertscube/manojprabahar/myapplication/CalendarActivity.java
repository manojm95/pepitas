package com.apptrovertscube.manojprabahar.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.apptrovertscube.manojprabahar.myapplication.model.TrackParseFile;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.razorpay.Checkout;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.DefaultDayViewAdapter;
import com.victor.loading.newton.NewtonCradleLoading;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    View v;
    NewtonCradleLoading ncl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        ncl = (NewtonCradleLoading) findViewById(R.id.newton_cradle_loading_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarcalendar);
        toolbar.setNavigationIcon(R.drawable.ic_leftarrow_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalendarActivity.this, MainActivity.class));
                finish();
            }
        });



        //setSupportActionBar(toolbar);
        Calendar mscal = Calendar.getInstance();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.MONTH, 1);

        CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date();

        long DAY_IN_MS = 1000 * 60 * 60 * 24;


        //calendar.setCustomDayView(new SampleDayViewAdapter());
        //calendar.setDecorators(Collections.<CalendarCellDecorator>emptyList());
        calendar.init(new Date(System.currentTimeMillis() - (97 * DAY_IN_MS)), nextYear.getTime())
                .withSelectedDate(today);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(CalendarActivity.this, DemoActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });


        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                if(LoginActivity.check)
                {
                SimpleDateFormat myFormat = new SimpleDateFormat("ddMMyyyy");
                String reformattedStr = myFormat.format(date);
                mscal.setTime(date);
                if(!date.after(today)){
                    //date<=today
                    calendar.setVisibility(View.INVISIBLE);
                    ncl.start();
                    ncl.setLoadingColor(Color.parseColor("#000000"));
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("SelectedItems");
                    query.whereEqualTo("id", Integer.parseInt(reformattedStr));
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if(e==null){
                                calendar.setVisibility(View.VISIBLE);
                                ncl.stop();
                                if(objects.size() > 0)
                                {
                                    Intent i = new Intent(CalendarActivity.this, DailyReceiptActivity.class);
                                    i.putExtra("dateval", reformattedStr);
                                    i.putExtra("sourceval","calendar");
                                    startActivity(i);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "We couldn't locate an order for the selected date. Please see Month View for list of orders placed.", Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                calendar.setVisibility(View.VISIBLE);
                                ncl.stop();
                            }
                        }
                    });
                    //Log.d("bvcxz past"," hhhhhhhhhhhh "+reformattedStr);

//                    Intent i = new Intent(CalendarActivity.this, DemoActivity.class);
//                    i.putExtra("dateval", reformattedStr);
//                    i.putExtra("timeinms",mscal.getTimeInMillis());
//                    startActivity(i);
//                    finish();
                }
                else {
                    if(LoginActivity.credit > LoginActivity.treshold) {
                        ncl.start();
                        ncl.setLoadingColor(Color.parseColor("#000000"));
                        calendar.setVisibility(View.INVISIBLE);
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("SelectedItems");
                        query.whereEqualTo("id", Integer.parseInt(reformattedStr));
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e == null) {
                                    calendar.setVisibility(View.VISIBLE);
                                    ncl.stop();
                                    if (objects.size() > 0) {
                                        Intent i = new Intent(CalendarActivity.this, DemoActivity.class);
                                        Bundle extras = new Bundle();
                                        extras.putString("dateval", reformattedStr);
                                        extras.putLong("timeinms", mscal.getTimeInMillis());
                                        extras.putString("category", "all");
                                        extras.putString("demo", "false");
                                        i.putExtras(extras);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Please complete the payment before proceeding as the credit limit is hit.", Toast.LENGTH_LONG).show();

                                    }
                                }
                                else
                                {   calendar.setVisibility(View.VISIBLE);
                                    ncl.stop();
                                    Toast.makeText(getApplicationContext(), "Please check your internet or try again after sometime..", Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                    }
                    else
                    {
                        //calendar.setVisibility(View.VISIBLE);
                        if(LoginActivity.treshold > 0) {
                            Intent i = new Intent(CalendarActivity.this, DemoActivity.class);
                            Bundle extras = new Bundle();
                            extras.putString("dateval", reformattedStr);
                            extras.putLong("timeinms", mscal.getTimeInMillis());
                            extras.putString("category", "all");
                            extras.putString("demo", "false");
                            i.putExtras(extras);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Please check your internet or try again after sometime..", Toast.LENGTH_LONG).show();
                        }
                    }
                    //Toast.makeText(getApplicationContext(), "The selected date is " + reformattedStr, Toast.LENGTH_SHORT).show();
                    //Intent i = new Intent(CalendarActivity.this, DailyReceiptActivity.class);

                }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please add a address before proceeding  or confirm internet connectivity and try in few seconds", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    public static void manoj(){
        Log.d("Manoj","Manoj");
    }

}
