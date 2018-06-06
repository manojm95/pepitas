package com.apptrovertscube.manojprabahar.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.victor.loading.newton.NewtonCradleLoading;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;

public class DailyReceiptActivity extends AppCompatActivity {

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
    String finaldate = formatter.format(calendar.getTime());
    int intenetdate = 0;
    TextView mon;
    TextView day;
    private boolean checknull=false;
    LinearLayout ll;
    String param;
    ImageView iv;
    private NewtonCradleLoading ncl;
    String source="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_receipt);
        iv  = (ImageView)findViewById(R.id.dailyback);
        ll = (LinearLayout) findViewById(R.id.placeholder);
        mon = (TextView)findViewById(R.id.month);
        ncl = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loadingdaily);
        day = (TextView)findViewById(R.id.day);
        if (getIntent().getStringExtra("dateval") != null) {
            param = getIntent().getStringExtra("dateval");
            source = getIntent().getStringExtra("sourceval");
            intenetdate = Integer.parseInt(param);
        }


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (source)
                {
                    case "calendar":
                        Intent i = new Intent(DailyReceiptActivity.this, CalendarActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case "thismonth":
                        Intent i1 = new Intent(DailyReceiptActivity.this, MonthView.class);
                        i1.putExtra("whatmonth","thismonth");
                        startActivity(i1);
                        finish();
                        break;
                    case "futuremonths":
                        Intent i2 = new Intent(DailyReceiptActivity.this, MonthView.class);
                        i2.putExtra("whatmonth","futuremonths");
                        startActivity(i2);
                        finish();
                        break;
                    case "pastmonth":
                        Intent i3 = new Intent(DailyReceiptActivity.this, MonthView.class);
                        i3.putExtra("whatmonth","pastmonth");
                        i3.putExtra("mmyy",param.substring(2,8));
                        startActivity(i3);
                        finish();
                        break;
                    default:
                        Intent id = new Intent(DailyReceiptActivity.this, MainActivity.class);
                        startActivity(id);
                        finish();
                        break;
                }
            }
        });


        Date date=null;
        SimpleDateFormat myFormat = new SimpleDateFormat("ddMMyyyy");
        try {
             date = myFormat.parse(param);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Log.d("hjhjhj", date.toString());

//        Calendar con = Calendar.getInstance();
//        con.setTime(date);
//        int dayOfWeek = con.get(Calendar.DAY_OF_WEEK);

        //Log.d("hjhjhj", date.toString()+"   "+dayOfWeek);

        mon.setText(param.substring(0,2)+"-"+LoginActivity.month[Integer.parseInt(param.substring(2,4))-1]);

        switch (date.toString().substring(0,3)) {
            case "Mon":
                day.setText("Monday");
                break;
            case "Tue":
                day.setText("Tuesday");
                break;
            case "Wed":
                day.setText("Wednesday");
                break;
            case "Thu":
                day.setText("Thursday");
                break;
            case "Fri":
                day.setText("Friday");
                break;
            case "Sat":
                day.setText("Saturday");
                break;
            case "Sun":
                day.setText("Sunday");
                break;
            default:
                day.setText(date.toString().substring(0, 3));

        }

        Realm realm = Realm.getDefaultInstance();

        int a;
        if (intenetdate == 0) {
            a = Integer.parseInt(finaldate);
        } else {
            a = intenetdate;
        }

        ncl.start();
        ncl.setLoadingColor(Color.parseColor("#707B7C"));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("SelectedItems");
        query.whereEqualTo("id",a);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                if(e==null)
                {
                    if(objects.size()>0) {
                        String s = String.valueOf(objects.get(0).get("items"));
                        String p = String.valueOf(objects.get(0).get("price"));
                        //String p = stcheck.getPrice();
                        int g = 0;
                        String[] asplit = s.split("y1t5qm7az9d0q");
                        String[] pricesplit = p.split("y1t5qm7az9d0q");
                        String[] check = asplit[0].split("y4t1qm3az8d1q");
                        int total_amt = 0;
                        LayoutInflater popupiteminflater = LayoutInflater.from(DailyReceiptActivity.this);
                        ncl.stop();
                        ncl.setVisibility(View.INVISIBLE);
                        for (String f : asplit) {


                            String[] psplit = pricesplit[g].split("y4t1qm3az8d1q");

                            double d = Double.parseDouble(psplit[0]);
                            if (check.length > 1) {
                                View popupitem = popupiteminflater.inflate(R.layout.paymentitem, ll, false);
                                String[] itemsplit = f.split("y4t1qm3az8d1q");
                                //CardView cv = (CardView) popupitem.findViewById(R.id.cardll);
                                CardView llitem = (CardView) popupitem.findViewById(R.id.cardpaymentitem);
                                TextView tvletter = (TextView) popupitem.findViewById(R.id.paymentitemfirstletter);
                                tvletter.setText(itemsplit[0].substring(0, 1).toUpperCase());
                                TextView desc = (TextView) popupitem.findViewById(R.id.paymentitemdesc);
                                TextView qty = (TextView) popupitem.findViewById(R.id.paymentitemqty);
                                TextView total = (TextView) popupitem.findViewById(R.id.paymentitemtotal);
                                desc.setText(itemsplit[0]);

                                qty.setText("Price: " + d + ". Qty: " + itemsplit[1]);
                                total.setText(String.format("%.2f",d * Integer.parseInt(itemsplit[1])));
                                ll.addView(llitem);
                            } else {
                                View popupitem = popupiteminflater.inflate(R.layout.paymentitem, ll, false);
                                CardView llitem = (CardView) popupitem.findViewById(R.id.cardpaymentitem);
                                TextView tvletter = (TextView) popupitem.findViewById(R.id.paymentitemfirstletter);
                                tvletter.setText(f.substring(0, 1).toUpperCase());
                                TextView desc = (TextView) popupitem.findViewById(R.id.paymentitemdesc);
                                TextView qty = (TextView) popupitem.findViewById(R.id.paymentitemqty);
                                TextView total = (TextView) popupitem.findViewById(R.id.paymentitemtotal);
                                desc.setText(f);
                                //desc.setText(f);
                                qty.setText("Price: " + d + ". Qty: 1");
                                total.setText(String.format("%.2f",d));
                                ll.addView(llitem);

                            }

                            g++;

                        }
                        if (asplit.length > 0) {
                            View popupitem = popupiteminflater.inflate(R.layout.paymentitemtotal, ll, false);
                            CardView llitem = (CardView) popupitem.findViewById(R.id.cardll);
                            TextView tvletter = (TextView) popupitem.findViewById(R.id.paymentitemtotalprice);
                            tvletter.setText(String.valueOf(objects.get(0).get("total")));
                            ll.addView(llitem);
                        }
                    }
                    else
                    {
                        checknull = true;
                        ncl.stop();
                        ncl.setVisibility(View.INVISIBLE);
                    }
                }
                else
                {
                    //checknull = true;
                    ncl.stop();
                    ncl.setVisibility(View.INVISIBLE);
                }
            }
        });

//        Selecttable stcheck = realm.where(Selecttable.class)
//                .equalTo("id", a)
//                .findFirst();
////        Log.d("Manojjghggh", "" + stcheck.getItems());
//        if (stcheck == null) {
//            checknull = true;
//        } else {
//            String s = stcheck.getItems();
//            String p = stcheck.getPrice();
//            int g = 0;
//            String[] asplit = s.split("y1t5qm7az9d0q");
//            String[] pricesplit = p.split("y1t5qm7az9d0q");
//            String[] check = asplit[0].split("y4t1qm3az8d1q");
//            int total_amt = 0;
//            LayoutInflater popupiteminflater = LayoutInflater.from(DailyReceiptActivity.this);
//            for (String f : asplit) {
//
//
//
//                String[] psplit = pricesplit[g].split("y4t1qm3az8d1q");
//
//                double d = Double.parseDouble(psplit[0]);
//                if(check.length > 1)
//                {
//                    View popupitem = popupiteminflater.inflate(R.layout.paymentitem, ll, false);
//                    String[] itemsplit = f.split("y4t1qm3az8d1q");
//                    //CardView cv = (CardView) popupitem.findViewById(R.id.cardll);
//                    CardView llitem = (CardView) popupitem.findViewById(R.id.cardpaymentitem);
//                    TextView tvletter = (TextView) popupitem.findViewById(R.id.paymentitemfirstletter);
//                    tvletter.setText(itemsplit[0].substring(0,1).toUpperCase());
//                    TextView desc = (TextView)popupitem.findViewById(R.id.paymentitemdesc);
//                    TextView qty = (TextView)popupitem.findViewById(R.id.paymentitemqty);
//                    TextView total = (TextView)popupitem.findViewById(R.id.paymentitemtotal);
//                    desc.setText(itemsplit[0]);
//
//                    qty.setText("Price: "+ d +". Qty: "+itemsplit[1]);
//                    total.setText((d*Integer.parseInt(itemsplit[1]))+"");
//                    ll.addView(llitem);
//                }
//                else {
//                    View popupitem = popupiteminflater.inflate(R.layout.paymentitem, ll, false);
//                    CardView llitem = (CardView) popupitem.findViewById(R.id.cardpaymentitem);
//                    TextView tvletter = (TextView) popupitem.findViewById(R.id.paymentitemfirstletter);
//                    tvletter.setText(f.substring(0,1).toUpperCase());
//                    TextView desc = (TextView)popupitem.findViewById(R.id.paymentitemdesc);
//                    TextView qty = (TextView)popupitem.findViewById(R.id.paymentitemqty);
//                    TextView total = (TextView)popupitem.findViewById(R.id.paymentitemtotal);
//                    desc.setText(f);
//                    //desc.setText(f);
//                    qty.setText("Price: "+ d +". Qty: 1");
//                    total.setText(d+"");
//                    ll.addView(llitem);
//
//                }
//
//                g++;
//
//            }
//            if(asplit.length > 0){
//                View popupitem = popupiteminflater.inflate(R.layout.paymentitemtotal, ll, false);
//                CardView llitem = (CardView) popupitem.findViewById(R.id.cardll);
//                TextView tvletter = (TextView) popupitem.findViewById(R.id.paymentitemtotalprice);
//                tvletter.setText(stcheck.getTotal());
//                ll.addView(llitem);
//            }
//        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        switch (source)
        {
            case "calendar":
                Intent i = new Intent(DailyReceiptActivity.this, CalendarActivity.class);
                startActivity(i);
                finish();
                break;
            case "thismonth":
                Intent i1 = new Intent(DailyReceiptActivity.this, MonthView.class);
                i1.putExtra("whatmonth","thismonth");
                startActivity(i1);
                finish();
                break;
            case "futuremonths":
                Intent i2 = new Intent(DailyReceiptActivity.this, MonthView.class);
                i2.putExtra("whatmonth","futuremonths");
                startActivity(i2);
                finish();
                break;
            case "pastmonth":
                Intent i3 = new Intent(DailyReceiptActivity.this, MonthView.class);
                i3.putExtra("whatmonth","pastmonth");
                i3.putExtra("mmyy",param.substring(2,8));
                startActivity(i3);
                finish();
                break;
            default:
                Intent id = new Intent(DailyReceiptActivity.this, MainActivity.class);
                startActivity(id);
                finish();
                break;
        }
    }
}
