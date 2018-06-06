package com.apptrovertscube.manojprabahar.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.apptrovertscube.manojprabahar.myapplication.model.TrackParseFile;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPickerListener;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.special.ResideMenu.ResideMenu;
import com.squareup.picasso.Picasso;
import com.victor.loading.newton.NewtonCradleLoading;
import com.yalantis.multiselection.lib.MultiSelect;
import com.yalantis.multiselection.lib.MultiSelectBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

/**
 * Created by Artem Kholodnyi on 9/3/16.
 */
public class DemoActivity extends AppCompatActivity implements PaymentResultListener {


    private MultiSelect<TrackParseFile> mMultiSelect;
    Calendar calendar = Calendar.getInstance();
    public List<TrackParseFile> TRACKSPARSEFILE = new ArrayList<>();
    public List<TrackParseFile> TRACKSPARSEFILECOMPLETE = new ArrayList<>();
    int intenetdate = 0;
    private PopupWindow popupwdw;
    private String iteminstring;
    FrameLayout layout_MainMenu;
    FrameLayout mounton;
    NewtonCradleLoading newtonCradleLoading;
    List<ParseFile> pf = null;
    List<String> pfn = null;
    List<String> pfp = null;
    static HashMap<String, Integer> hm;
    HashMap<String, Integer> defaultitemmap;
    static HashMap<String, Integer> buildlayout;
    static HashMap<String, Integer> trackmap;
    HashMap<String, Integer> qtymap;
    HashMap<String, String> pricemap;
    List<String> itemtrack = new ArrayList<>();
    HashMap<String, Integer> tempmap;
    List<TrackParseFile> TRACKSBKUP = new ArrayList<>();
    List<String> deflist;
    String[] month = {""};
    String year = "";
    String date = "";
    int a = 0;
    boolean existflag = false;
    String tempcontent = "";
    String tempqty = "";
    String tempprice = "";
    String temptotal = "0";
    FloatingActionButton fab;
    static Context con;
    private boolean nodataflag = false;
    private long ms = 0;
    String category = "all";
    String tqty = "";
    String it = "";
    FrameLayout root;
    String tprice = "";
    double ttotal = 0;
    private boolean cartedit = false;
    private String recordexistflag = "";
    private int b = 0;
    private String demo = "";
    double credittemp = 0;
    List<TrackParseFile> search = null;
    private String leftadapterflag = "t";
    private PopupWindow pw;
    String q = "";
    String p = "";
    double t = 0;
    String c = "";
    private boolean updateflagforcredit = false;
    private boolean updateflagforpyment = false;

    private String updateContent = "";
    private String updatePrice = "";
    private boolean updateQtyind = false;
    private double updateTotal = 0;
    private ParseObject pob = null;
    private int updateCount = 0;
    private String updateQty = "";
    private int updateFinalb = 0;
    private String creditflag="false";
    private double differenceinmount=0;
    private boolean updateflagforpaymentlessamt=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_select);

        root = (FrameLayout) findViewById(R.id.demofl);
        fab = (FloatingActionButton) findViewById(R.id.fabcart);
        setUpToolbar((Toolbar) findViewById(R.id.toolbar));
        nodataflag = false;
        hm = new HashMap<>();
        defaultitemmap = new HashMap<>();
        tempmap = new HashMap<>();
        qtymap = new HashMap<>();
        pricemap = new HashMap<>();
        buildlayout = new HashMap<>();
        trackmap = new HashMap<>();
        con = DemoActivity.this;


        layout_MainMenu = (FrameLayout) findViewById(R.id.demofl);
        mounton = (FrameLayout) findViewById(R.id.mount_point);
        newtonCradleLoading = (NewtonCradleLoading) findViewById(R.id.newton_cradle_loading);
        layout_MainMenu.getForeground().setAlpha(0);
        Realm realm = Realm.getDefaultInstance();


        Bundle ext = getIntent().getExtras();
        if (ext.getString("dateval") != null) {
            if (ext.getString("dateval").equals("tomo")) {
                category = "all";
                SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
                calendar.setTime(new Date());
                calendar.add(Calendar.DATE, 1);
                ms = calendar.getTimeInMillis();
                demo = ext.getString("demo");
                String finaldate = formatter.format(calendar.getTime());
                a = Integer.parseInt(finaldate);
                intenetdate = a;

                Selecttable stcheck = realm.where(Selecttable.class)
                        .equalTo("id", a)
                        .findFirst();
                if (stcheck != null) {
                    realm.beginTransaction();
                    stcheck.removeFromRealm();
                    realm.commitTransaction();
                }
            } else {
                intenetdate = Integer.parseInt(ext.getString("dateval"));
                a = intenetdate;
                ms = ext.getLong("timeinms", 0);
                category = ext.getString("category");
                demo = ext.getString("demo");
                if (category.equals("all") && demo.equals("false")) {
                    Selecttable stcheck = realm.where(Selecttable.class)
                            .equalTo("id", a)
                            .findFirst();
                    if (stcheck != null) {
                        realm.beginTransaction();
                        stcheck.removeFromRealm();
                        realm.commitTransaction();
                    }
                } else {
                    Selecttable stcheck = realm.where(Selecttable.class)
                            .equalTo("id", a)
                            .findFirst();
                    if (stcheck != null) {
                        existflag = true;
                        tempcontent = stcheck.getItems();
                        if (!tempcontent.equals("")) {
                            int id = getResources().getIdentifier("ic_shopping", "drawable", getPackageName());
                            fab.setImageResource(id);
                        }
                        tempprice = stcheck.getPrice();
                        tempqty = stcheck.getQty();
                        String[] qsplit = tempqty.split("y1t5qm7az9d0q");
                        String[] csplit = tempcontent.split("y1t5qm7az9d0q");
                        String[] psplit = tempprice.split("y1t5qm7az9d0q");
                        Log.d("jkjkjkjkjk", Arrays.toString(qsplit));
                        Log.d("jkjkjkjkjk1", Arrays.toString(csplit));
                        Log.d("jkjkjkjkjk2", Arrays.toString(psplit));

                        if (!tempqty.equals("")) {
                            for (String qty : qsplit) {
                                String[] b = qty.split("y4t1qm3az8d1q");
                                trackmap.put(b[0], Integer.parseInt(b[1]));
                                qtymap.put(b[0], Integer.parseInt(b[1]));
                                itemtrack.add(b[0]);
                                Log.d("hhhhhjjjjjsize", itemtrack.size() + " " + itemtrack.get(0));
                            }
                        }
                        int y = 0;
                        while (y < qsplit.length) {
                            pricemap.put(csplit[y].split("y4t1qm3az8d1q")[0], psplit[y].split("y4t1qm3az8d1q")[0]);
                            y++;
                        }
                        temptotal = stcheck.getTotal();
                    }

                }
            }
        }
        realm.close();

        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        String finaldate = formatter.format(calendar.getTime());


//        if (intenetdate == 0) {
//            a = Integer.parseInt(finaldate);
//        } else {
//            a = intenetdate;
//        }

        date = String.valueOf(a);
        if (date.length() == 7) {
            month[0] = date.substring(1, 3);
            year = date.substring(3, 7);
            date = date.substring(0, 1) + "/" + month[0] + "/" + year;

        } else {
            month[0] = date.substring(2, 4);
            year = date.substring(4, 8);
            date = date.substring(0, 2) + "/" + month[0] + "/" + year;
        }


        newtonCradleLoading.start();
        newtonCradleLoading.setLoadingColor(Color.parseColor("#000000"));
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ImageRepo");
        query.whereEqualTo("type", category);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        pfn = objects.get(0).getList("imgfilename");
                        pfp = objects.get(0).getList("pricelist");
                        pf = objects.get(0).getList("imgfiles");
                    }
                    Log.d("nbnbnbnbn", "----->" + objects.size());


                    String[] defarr = LoginActivity.defaultitem.split("1zzqwertyzz1");
                    deflist = Arrays.asList(defarr);


                    int g = 0;
                    if (category.equals("all")) {
                        while (g < pfn.size()) {
                            TRACKSPARSEFILECOMPLETE.add(new TrackParseFile(pfn.get(g), pfp.get(g), pf.get(g)));
                            if (deflist.contains(pfn.get(g))) {
                                if (demo.equals("false")) {
                                    TRACKSBKUP.add(new TrackParseFile(pfn.get(g), pfp.get(g), pf.get(g)));
                                    defaultitemmap.put(pfn.get(g), 1);
                                    hm.put(pfn.get(g), 1);
                                }

                            } else
                                TRACKSPARSEFILE.add(new TrackParseFile(pfn.get(g), pfp.get(g), pf.get(g)));
                            g++;
                        }
                    } else {
                        if (objects.size() > 0) {
                            while (g < pfn.size()) {
                                TRACKSPARSEFILECOMPLETE.add(new TrackParseFile(pfn.get(g), pfp.get(g), pf.get(g)));
                                TRACKSPARSEFILE.add(new TrackParseFile(pfn.get(g), pfp.get(g), pf.get(g)));
                                g++;
                            }
                        }
                    }
                    MultiSelectBuilder<TrackParseFile> builder = new MultiSelectBuilder<>(TrackParseFile.class)
                            .withContext(DemoActivity.con)
                            .mountOn((ViewGroup) findViewById(R.id.mount_point))
                            .withSidebarWidth(46 + 8 * 2); // ImageView width with paddings
                    newtonCradleLoading.stop();
                    newtonCradleLoading.setVisibility(View.GONE);
                    setUpAdapters(builder);
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shoppingCart(false, null);
            }
        });

    }

    private void setUpDecoration() {
        TracksItemDecorator itemDecorator = new TracksItemDecorator(
                getResources().getDimensionPixelSize(R.dimen.decoration_size));
        mMultiSelect.getRecyclerLeft().addItemDecoration(itemDecorator);
        mMultiSelect.getRecyclerRight().addItemDecoration(itemDecorator);
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
        }

        @Override
        public void closeMenu() {

        }
    };


    private List<TrackParseFile> filter(List<TrackParseFile> l, String text) {
        text = text.toLowerCase();
        final List<TrackParseFile> filterList = new ArrayList<>();
        String t = "";
        for (TrackParseFile pf : l) {
            t = pf.getTrackName().toLowerCase();
            if (t.contains(text)) {
                filterList.add(pf);
            }
        }
        return filterList;
    }


    private void setUpAdapters(MultiSelectBuilder<TrackParseFile> builder) {
        LeftAdapterKotlin leftAdapter = new LeftAdapterKotlin(position -> mMultiSelect.select(position));
        RightAdapterKotlin rightAdapter = new RightAdapterKotlin(position -> mMultiSelect.deselect(position));
        List<TrackParseFile> TRACKSFilter = new ArrayList<TrackParseFile>();
        List<TrackParseFile> TRACKSSelected = new ArrayList<TrackParseFile>();

        ArrayList<String> gh = new ArrayList<>();
        if (category.equals("all") && demo.equals("false")) {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("SelectedItems");
            query.whereEqualTo("id", a);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (e == null) {
                        Log.d("manojkl4", objects.size() + "");
                        if (objects.size() > 0) {
                            recordexistflag = "true";
                            b = objects.get(0).getInt("version");
                            String s = String.valueOf(objects.get(0).get("items"));

                            //TO REMOVE DAFAULT ITEMS FROM THE hm map which causes abend in shopping cart
                            Log.d("hhhhhjjjjjuuu1", TRACKSBKUP.size() + "");
                            if (TRACKSBKUP.size() > 0) {
                                for (TrackParseFile tpf : TRACKSBKUP) {
                                    Log.d("hhhhhjjjjjuuu2", TRACKSBKUP.size() + "" + tpf.getTrackName());
                                    Log.d("hhhhhjjjjjuuu2.5", TRACKSBKUP.size() + "" + hm.get(tpf.getTrackName()) + " " + hm.size());
                                    hm.remove(tpf.getTrackName());
                                    Log.d("hhhhhjjjjjuuu3", TRACKSBKUP.size() + "" + tpf.getTrackName() + " " + hm.size());
                                }
                            }

                            if (s.equals("nodata")) {
                                nodataflag = true;
                                for (TrackParseFile tpf : TRACKSBKUP) {
                                    TRACKSPARSEFILE.add(tpf);
                                }
                                Log.d("jkjkjkjkjk<--->", TRACKSBKUP.size() + "   ");
                                TRACKSBKUP.clear();
                            }
                            String q = String.valueOf(objects.get(0).get("quantity"));
                            int g = 0;
                            String[] asplit = s.split("y1t5qm7az9d0q");
                            String[] qsplit = q.split("y1t5qm7az9d0q");
                            if (!q.equals("")) {
                                for (String qty : qsplit) {
                                    String[] b = qty.split("y4t1qm3az8d1q");
                                    buildlayout.put(b[0], Integer.parseInt(b[1]));
                                    hm.put(b[0], Integer.parseInt(b[1]));
                                    Log.d("ytrewqb", hm.size() + "");
                                    Log.d("jjjjjhhhhh", hm.size() + " " + hm.get(b[0]) + " " + b[0]);
                                }
                            }
                            String[] check = asplit[0].split("y4t1qm3az8d1q");
                            List<String> ll = new ArrayList<>();
                            if (check.length > 1) {
                                for (String v : asplit) {
                                    String[] b = v.split("y4t1qm3az8d1q");
                                    ll.add(b[0]);
                                }
                            }

                            for (String f : pfn) {
                                if (check.length > 1) {
                                    //Toast.makeText(getApplicationContext(),"Because of Edit")
                                    if (ll.contains(f)) {
                                        TRACKSSelected.add(TRACKSPARSEFILECOMPLETE.get(g));
                                    } else {
                                        TRACKSFilter.add(TRACKSPARSEFILECOMPLETE.get(g));
                                    }
                                } else {
                                    if (Arrays.asList(asplit).contains(f)) {
                                        TRACKSSelected.add(TRACKSPARSEFILECOMPLETE.get(g));
                                    } else {
                                        TRACKSFilter.add(TRACKSPARSEFILECOMPLETE.get(g));
                                    }
                                }
                                g++;
                            }


                        } else {
                            recordexistflag = "false";
                        }

                        Log.d("jkjkjkjkjknodataflag", nodataflag + "   ");
                        if (TRACKSFilter.size() > 0 || TRACKSSelected.size() > 0) {
                            leftadapterflag = "f";
                            leftAdapter.addAll(TRACKSFilter);
                            rightAdapter.addAll(TRACKSSelected);
                        } else {

                            leftAdapter.addAll(TRACKSPARSEFILE);
                            //here thedefault item is added. Tracksbkup will have defaultitem
                            if (!nodataflag) {
                                rightAdapter.addAll(TRACKSBKUP);
                            }
                        }

                        builder.withLeftAdapter(leftAdapter)
                                .withRightAdapter(rightAdapter);
                        mMultiSelect = builder.build();

                        setUpDecoration();
                    } else {

                    }

                }
            });
        } else {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("SelectedItems");
            query.whereEqualTo("id", a);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            recordexistflag = "true";
                            b = objects.get(0).getInt("version");
                        } else recordexistflag = "false";
                    }
                }
            });
            leftAdapter.addAll(TRACKSPARSEFILE);
            builder.withLeftAdapter(leftAdapter)
                    .withRightAdapter(rightAdapter);
            mMultiSelect = builder.build();

            setUpDecoration();
        }
    }

    private void setUpToolbar(final Toolbar toolbar) {

        RelativeLayout rv = (RelativeLayout) findViewById(R.id.searchrelativeview);
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setNavigationIcon(R.drawable.ic_leftarrow_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<TrackParseFile> items = mMultiSelect.getSelectedItems();
                final int selectedCount = items.size();
                if (selectedCount == 0 && tempqty.equals("")) {
                    startActivity(new Intent(DemoActivity.this, MainActivity.class));
                    finish();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(DemoActivity.this);
                    dialog.setTitle("Confirm  Exit")
                            .setIcon(R.drawable.alert)
                            .setMessage("Are you sure you want to exit this page with items added?")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    dialoginterface.cancel();
                                }
                            })
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    startActivity(new Intent(DemoActivity.this, MainActivity.class));
                                    finish();
                                }
                            }).show();
                }
            }
        });
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.select) {
                saveselected(toolbar);
                return true;
            }
            if (item.getItemId() == R.id.search) {
                //flag for search activity
                saveTemp(true);
                //initiatePopupWindow(root);

                return true;
            }
            if (item.getItemId() == R.id.quantity) {
                layout_MainMenu.getForeground().setAlpha(220);
                LayoutInflater layoutInflater = LayoutInflater.from(DemoActivity.this);
                View popupview = layoutInflater.inflate(R.layout.popupqty, null);
                LinearLayout ll = (LinearLayout) popupview.findViewById(R.id.dynamicllqty);
                ll.setOrientation(LinearLayout.VERTICAL);
                ImageView goback = (ImageView) popupview.findViewById(R.id.gobackpopupwdw);
                goback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupwdw.dismiss();
                        layout_MainMenu.getForeground().setAlpha(0);
                    }
                });

                List<TrackParseFile> items = mMultiSelect.getSelectedItems();
                final int selectedCount = items.size();


                ParseQuery<ParseObject> query = ParseQuery.getQuery("CategoryRepo");
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(final List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            if (objects.size() > 0) {

                                List<String> pfn = objects.get(0).getList("imgfilename");
                                List<ParseFile> pfp = objects.get(0).getList("imgfiles");
                                int cnt = 0;
                                while (cnt < pfn.size()) {
                                    LayoutInflater popupiteminflater = LayoutInflater.from(DemoActivity.this);
                                    View popupitem = popupiteminflater.inflate(R.layout.categorypopupitem, ll, false);
                                    LinearLayout root = (LinearLayout) popupitem.findViewById(R.id.categoryitemroot);
                                    TextView item = (TextView) popupitem.findViewById(R.id.categoryitemtv);
                                    item.setText(pfn.get(cnt));
                                    root.setTag(pfn.get(cnt));
                                    root.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String h = String.valueOf(view.getTag());
                                            ParseQuery<ParseObject> query = ParseQuery.getQuery("ImageRepo");
                                            query.whereEqualTo("type", h);
                                            query.findInBackground(new FindCallback<ParseObject>() {
                                                @Override
                                                public void done(List<ParseObject> objects, ParseException e) {
                                                    if (objects.size() > 0) {
                                                        saveTemp(false);
                                                        popupwdw.dismiss();
                                                        Intent i = new Intent(DemoActivity.this, DemoActivity.class);
                                                        Bundle extras = new Bundle();
                                                        extras.putString("dateval", String.valueOf(intenetdate));
                                                        extras.putLong("timeinms", ms);
                                                        extras.putString("category", h);
                                                        extras.putString("demo", "true");
                                                        i.putExtras(extras);

                                                        startActivity(i);

                                                        finish();
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "There are no items in this category for today. " +
                                                                "Sorrry please visit later.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                        }
                                    });
                                    ImageView iv = (ImageView) popupitem.findViewById(R.id.categoryitemiv);
                                    Uri imageuri = Uri.parse(pfp.get(cnt).getUrl());
                                    Picasso.with(DemoActivity.con).load(imageuri.toString()).into(iv);
                                    ll.addView(root);
                                    cnt++;
                                }
                            }
                        }
                    }
                });


                popupwdw = new PopupWindow(popupview, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popupwdw.setAnimationStyle(android.R.style.Animation_Dialog);
                popupwdw.setOutsideTouchable(true);
                popupwdw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        layout_MainMenu.getForeground().setAlpha(0);
                    }
                });
                popupwdw.showAtLocation(popupview, Gravity.LEFT, 0, 0);
                return true;

            } else {
                return false;
            }
        });
    }

    private void saveselected(Toolbar t) {

        if (recordexistflag.equals("true") && mMultiSelect.getSelectedItems().size() == 0 && tempcontent.equals("")) {
            Toast.makeText(getApplicationContext(), "Item(s) in your order cancelled. Hope to serve you again soon.", Toast.LENGTH_SHORT).show();
            savetodatabase("nodata", 0, t, false, "", 0, "", "");
        } else shoppingCart(true, t);
    }

    void savetodatabase(String content, int count, Toolbar t, boolean qtyind, String price, double total, String qty, String paymentorcredit) {

        String newline = System.getProperty("line.separator");
        newtonCradleLoading.setVisibility(View.VISIBLE);
        //the first time a record is oaded for particular date
        if (!recordexistflag.equals("")) {
            if (recordexistflag == "false") {

                if (content.equals("nodata")) {
                    newtonCradleLoading.start();
                    newtonCradleLoading.setLoadingColor(Color.parseColor("#000000"));
                    ParseObject SelectItems = new ParseObject("SelectedItems");
                    SelectItems.put("id", a);
                    SelectItems.put("items", "nodata");
                    SelectItems.put("price", "");
                    SelectItems.put("total", "");
                    SelectItems.put("entrydate", a);
                    SelectItems.put("updatedate", a);
                    SelectItems.put("version", 1);
                    SelectItems.put("quantity", "");
                    SelectItems.put("month", month[0]);
                    SelectItems.put("year", year);
                    SelectItems.put("reserveint", 0);
                    SelectItems.put("reserveint2", 0);
                    SelectItems.put("reserveString", "");
                    SelectItems.put("reserveString2", "");
                    SelectItems.put("creditflag", paymentorcredit);
                    String finalMonth = month[0];
                    SelectItems.put("actualdate", ms);
                    SelectItems.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                SelectItems.saveEventually();
                            } else {
                                ParseQuery<ParseObject> query = ParseQuery.getQuery("MonthTotal");
                                query.whereEqualTo("month", month[0]);
                                query.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(final List<ParseObject> objects, ParseException e) {
                                        if (e == null) {
                                            if (objects.size() == 0) {
                                                String mm;
                                                ArrayList<Integer> date = new ArrayList<>();
                                                ArrayList<String> total1 = new ArrayList<>();
                                                ArrayList<String> dateplustotal = new ArrayList<>();
                                                ParseObject SelectItems1 = new ParseObject("MonthTotal");
                                                mm = finalMonth;
                                                date.add(a);
                                                total1.add("nodata");
                                                dateplustotal.add("nodata");
                                                SelectItems1.put("month", mm);
                                                SelectItems1.put("datelist", date);
                                                SelectItems1.put("totallist", total1);
                                                SelectItems1.put("datetotal", dateplustotal);
                                                SelectItems1.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {
                                                        if (e != null) {
                                                            SelectItems1.saveEventually();
                                                        } else {
                                                            startActivity(new Intent(DemoActivity.this, MainActivity.class));
                                                            newtonCradleLoading.stop();
                                                            newtonCradleLoading.setVisibility(View.GONE);
                                                        }
                                                    }
                                                });
                                            } else if (objects.size() > 0) {
                                                String mm = objects.get(0).getString("month");
                                                List<Integer> date = objects.get(0).getList("datelist");
                                                List<String> total1 = objects.get(0).getList("totallist");
                                                List<String> dateplustotal = objects.get(0).getList("datetotal");
                                                int idx = date.indexOf(a);
                                                if (idx == -1) {
                                                    date.add(a);
                                                    total1.add("nodata");
                                                    dateplustotal.add("nodata");
                                                    objects.get(0).put("datelist", date);
                                                    objects.get(0).put("totallist", total1);
                                                    objects.get(0).put("datetotal", dateplustotal);
                                                    objects.get(0).saveInBackground(new SaveCallback() {
                                                        @Override
                                                        public void done(ParseException e) {
                                                            if (e != null) {
                                                                objects.get(0).saveEventually();
                                                            } else {
                                                                startActivity(new Intent(DemoActivity.this, MainActivity.class));
                                                                finish();
                                                                newtonCradleLoading.stop();
                                                                newtonCradleLoading.setVisibility(View.GONE);
                                                            }
                                                        }
                                                    });
                                                }

                                            }
                                        } else {
                                        }

                                    }
                                });
                            }
                        }
                    });
                    //TODO seperate mailstep for noitems needed
                } else {

                    //Parse Edit
                    Log.d("nbnbnb",paymentorcredit+"");
                    ParseObject SelectItems = new ParseObject("SelectedItems");
                    SelectItems.put("id", a);
                    SelectItems.put("items", content);
                    SelectItems.put("price", price);
                    SelectItems.put("total", String.valueOf(total));
                    SelectItems.put("entrydate", a);
                    SelectItems.put("updatedate", a);
                    SelectItems.put("version", 1);
                    SelectItems.put("quantity", qty);
                    SelectItems.put("month", month[0]);
                    SelectItems.put("year", year);
                    SelectItems.put("creditflag", paymentorcredit);
                    SelectItems.put("reserveint", 0);
                    SelectItems.put("reserveint2", 0);
                    SelectItems.put("reserveString", "");
                    SelectItems.put("reserveString2", "");
                    SelectItems.put("actualdate", ms);
                    SelectItems.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                SelectItems.saveEventually();
                            } else {
                                ParseQuery<ParseObject> query = ParseQuery.getQuery("MonthTotal");
                                query.whereEqualTo("month", month[0]);
                                query.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(final List<ParseObject> objects, ParseException e) {
                                        if (e == null) {
                                            if (objects.size() == 0) {
                                                String mm;
                                                ArrayList<Integer> date = new ArrayList<>();
                                                ArrayList<String> total1 = new ArrayList<>();
                                                ArrayList<String> dateplustotal = new ArrayList<>();
                                                ParseObject SelectItems1 = new ParseObject("MonthTotal");
                                                mm = month[0];
                                                date.add(a);
                                                total1.add(String.valueOf(total));
                                                dateplustotal.add(a + "$" + String.valueOf(total));
                                                Log.d("hgfdsa", dateplustotal.get(0));
                                                SelectItems1.put("month", mm);
                                                SelectItems1.put("datelist", date);
                                                SelectItems1.put("totallist", total1);
                                                SelectItems1.put("datetotal", dateplustotal);
                                                SelectItems1.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {
                                                        if (e != null) {
                                                            SelectItems1.saveEventually();
                                                        } else {
                                                            newtonCradleLoading.stop();
                                                            newtonCradleLoading.setVisibility(View.GONE);

                                                        }
                                                    }
                                                });
                                            } else if (objects.size() > 0) {
                                                String mm = objects.get(0).getString("month");
                                                List<Integer> date = objects.get(0).getList("datelist");
                                                List<String> total1 = objects.get(0).getList("totallist");
                                                List<String> dateplustotal = objects.get(0).getList("datetotal");
                                                int idx = date.indexOf(a);
                                                if (idx == -1) {
                                                    date.add(a);
                                                    total1.add(String.valueOf(total));
                                                    dateplustotal.add(a + "$" + String.valueOf(total));
                                                    objects.get(0).put("datelist", date);
                                                    objects.get(0).put("totallist", total1);
                                                    objects.get(0).put("datetotal", dateplustotal);
                                                    objects.get(0).saveInBackground(new SaveCallback() {
                                                        @Override
                                                        public void done(ParseException e) {
                                                            if (e != null) {
                                                                objects.get(0).saveEventually();
                                                            } else {
                                                                newtonCradleLoading.stop();
                                                                newtonCradleLoading.setVisibility(View.GONE);

                                                            }

                                                        }
                                                    });
                                                }

                                            }

                                            if (paymentorcredit.equals("true")) {
                                                ParseQuery<ParseObject> query = ParseQuery.getQuery("DefaultItem");
                                                query.findInBackground(new FindCallback<ParseObject>() {
                                                    @Override
                                                    public void done(List<ParseObject> objects, ParseException e) {
                                                        if (e == null) {
                                                            if (objects.size() > 0) {
                                                                double cre = objects.get(0).getDouble("credit");
                                                                cre = cre + total;
                                                                LoginActivity.credit = cre;
                                                                objects.get(0).put("credit", cre);
                                                                objects.get(0).saveInBackground(new SaveCallback() {
                                                                    @Override
                                                                    public void done(ParseException e) {
                                                                        if (e != null) {
                                                                            objects.get(0).saveEventually();
                                                                        } else {
                                                                            startActivity(new Intent(DemoActivity.this, MainActivity.class));
                                                                            finish();
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    }
                                                });
                                            } else {
                                                startActivity(new Intent(DemoActivity.this, MainActivity.class));
                                                finish();
                                            }

                                        } else {
                                        }

                                    }
                                });
                                sendEmail(content, price, qtyind, total);
                            }
                        }
                    });

                }
            } else {
                //the record is updated for particular date
                if (content.equals("nodata")) {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("SelectedItems");
                    query.whereEqualTo("id", a);
                    int finalB = b;

                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(final List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                if (objects.size() > 0) {
                                    if(objects.get(0).get("items").equals("nodata")) credittemp = 0;
                                    else credittemp = Double.parseDouble(objects.get(0).get("total").toString());
                                    Log.d("cree0",credittemp+" <---");
                                    objects.get(0).put("items", "nodata");
                                    objects.get(0).put("updatedate", a);
                                    objects.get(0).put("price", "");
                                    objects.get(0).put("total", "0");
                                    objects.get(0).put("quantity", "");
                                    objects.get(0).put("version", finalB + 1);
                                    creditflag = objects.get(0).get("creditflag").toString();
                                    if(creditflag.equals("true")){
                                        updateOrdernodata(objects.get(0),finalB);
                                    }
                                    else
                                    {
                                        AlertDialog.Builder dialog = new AlertDialog.Builder(DemoActivity.this);
                                        dialog.setTitle("Alert")
                                                .setIcon(R.drawable.alert)
                                                .setMessage("This order is placed with Debit/Credit/NetBanking and we can refund as only " +
                                                        "credits at this point which can be used for future orders. Click Ok to proceed")
                                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialoginterface, int i) {
                                                        dialoginterface.cancel();
                                                    }
                                                })
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialoginterface, int i) {
                                                        updateOrdernodata(objects.get(0),finalB);
                                                    }
                                                }).show();
                                    }

                                }
                            } else {
                            }

                        }
                    });


                } else {
                    //******REGULAR UPDATE ORDER******

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("SelectedItems");
                    query.whereEqualTo("id", a);
                    int finalB = b;
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(final List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                if (objects.size() > 0) {
                                    updateContent = content;
                                    updatePrice = price;
                                    updateQtyind = qtyind;
                                    updateTotal = total;
                                    pob = objects.get(0);
                                    updateCount = count;
                                    updateQty = qty;
                                    updateFinalb = finalB;
                                    credittemp = Double.parseDouble(objects.get(0).get("total").toString());
                                    creditflag = objects.get(0).get("creditflag").toString();
                                    Log.d("Tesst1",total +"  "+credittemp);
                                    if (total > credittemp)

                                    {
                                        if (objects.get(0).get("creditflag").equals("true")) {
                                            Log.d("Tesst2",total +"  "+objects.get(0).get("creditflag"));
                                            updateflagforcredit = true;
                                            if (!((LoginActivity.treshold - LoginActivity.credit) > (total - credittemp))) {
                                                //200 - 167=33 > 156 - 140=16
                                                AlertDialog.Builder dialog = new AlertDialog.Builder(DemoActivity.this);
                                                dialog.setTitle("Alert")
                                                        .setIcon(R.drawable.alert)
                                                        .setMessage("Sorry, You don't have enough credits to confirm the order. " +
                                                                "Do you want to pay now the total amount to proceed with this order? Your credits will be added back to your account.")
                                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialoginterface, int i) {
                                                                dialoginterface.cancel();
                                                            }
                                                        })
                                                        .setPositiveButton("Pay Now", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialoginterface, int i) {
                                                                makePayment(total);
                                                            }
                                                        }).show();
                                            }
                                            else
                                            {
                                                Log.d("Tesst3",total +"  alter");
                                                updateorder(pob, updateContent, updateCount, updateQtyind, updatePrice, updateTotal, updateQty, updateFinalb,true);
                                            }

                                        }
                                        else
                                        {
                                            updateflagforpyment = true;
                                            differenceinmount = LoginActivity.round(total-credittemp,2);
                                            AlertDialog.Builder dialog = new AlertDialog.Builder(DemoActivity.this);
                                            dialog.setTitle("Alert")
                                                    .setIcon(R.drawable.alert)
                                                    .setMessage("This order was placed using credit/debit/Net Banking. We would now " +
                                                            "charge you for the difference "+differenceinmount)
                                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialoginterface, int i) {
                                                            dialoginterface.cancel();
                                                        }
                                                    })
                                                    .setPositiveButton("Pay Now", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialoginterface, int i) {
                                                            makePayment(differenceinmount);
                                                        }
                                                    }).show();
                                        }
                                    } else if (total < credittemp) {
                                        if (objects.get(0).get("creditflag").equals("true")) {
                                            updateflagforcredit = true;

                                                //200 - 167=33 > 156 - 140=16
                                                AlertDialog.Builder dialog = new AlertDialog.Builder(DemoActivity.this);
                                                dialog.setTitle("Alert")
                                                        .setIcon(R.drawable.alert)
                                                        .setMessage("It looks like you have modified the order. The difference in total will be added back to your account. Click Ok to proceed")
                                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialoginterface, int i) {
                                                                dialoginterface.cancel();
                                                            }
                                                        })
                                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialoginterface, int i) {
                                                                updateorder(pob, updateContent, updateCount, updateQtyind, updatePrice, updateTotal, updateQty, updateFinalb,true);
                                                            }
                                                        }).show();

                                        }
                                        else
                                        {
                                            updateflagforpaymentlessamt = true;
                                            differenceinmount = LoginActivity.round(credittemp-total,2);
                                            AlertDialog.Builder dialog = new AlertDialog.Builder(DemoActivity.this);
                                            dialog.setTitle("Alert")
                                                    .setIcon(R.drawable.alert)
                                                    .setMessage("This order was placed using credit/debit/Net Banking. " +
                                                            "We can only add credits to your account for the difference "+differenceinmount+", at this time which can be used for future orders. " +
                                                            "Please click ok to continue.")
                                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialoginterface, int i) {
                                                            dialoginterface.cancel();
                                                        }
                                                    })
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialoginterface, int i) {
                                                            updateorder(pob, updateContent, updateCount, updateQtyind, updatePrice, updateTotal, updateQty, updateFinalb,true);
                                                        }
                                                    }).show();
                                        }
                                    }
                                }
                            } else {
                            }

                        }
                    });

                }
            }
            mMultiSelect.showSelectedPage();

        } else {
            Toast.makeText(getApplicationContext(), "Please check your internet connect and try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void makePayment(double total) {
        total = total * 100;
        double t = LoginActivity.round(total,2);
        Log.d("nbnbnb",t+"$$$");
        Log.d("nbnbnb",total+"$$$"+t);
        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.cloudsanelogohome);
        final Activity activity = DemoActivity.this;
        JSONObject options = new JSONObject();
        try {
            options.put("name", "Pepitas");
            options.put("description", "Order #123456");
            options.put("currency", "INR");
            JSONObject theme = new JSONObject();
            theme.put("color", "#000000");
            options.put("theme", theme);
            options.put("amount", String.valueOf(t));
            checkout.open(activity, options);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveTemp(boolean searchflag) {

        List<TrackParseFile> items = mMultiSelect.getSelectedItems();
        final int selectedCount = items.size();
        List<String> l1 = new ArrayList<>();

        String qty = "";
        //the below is for passing items to SearchActivity for stop adding duplicate items
        String qtyforSearch = "";
        for (Map.Entry<String, Integer> h : hm.entrySet()) {
            qty = qty + h.getKey() + "y4t1qm3az8d1q" + h.getValue() + "y1t5qm7az9d0q";
            l1.add(h.getKey());
        }
        Log.d("ytrewq000", qty);
        iteminstring = "";
        String price = "";
        double total = 0;
        if (selectedCount == 0) {
            qtyforSearch = tempqty;
        } else {

            for (TrackParseFile s : items) {

                int t = 1;

                if (DemoActivity.hm.get(s.getTrackName()) != null) {
                    t = DemoActivity.hm.get(s.getTrackName());
                }

                iteminstring = iteminstring + s.getTrackName() + "y4t1qm3az8d1q" + t + "y1t5qm7az9d0q";

                price = price + s.getArtist() + "y4t1qm3az8d1q" + t + "y1t5qm7az9d0q";

                total = total + (Double.parseDouble(s.getArtist()) * t);

            }

            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            if (tempcontent.equals("")) {
                qtyforSearch = qty;
                Log.d("nbvcxz1", qtyforSearch + " kkkk");
                Selecttable selecttable = new Selecttable(a, iteminstring, price, String.valueOf(total), a, a, 1, qty, month[0], year, 0, 0, "", "");
                realm.copyToRealm(selecttable);
            } else {
                Selecttable stcheck = realm.where(Selecttable.class)
                        .equalTo("id", a)
                        .findFirst();
                stcheck.setItems(tempcontent + iteminstring);
                stcheck.setQty(tempqty + qty);
                qtyforSearch = tempqty + qty;
                Log.d("nbvcxz2", qtyforSearch + " kkkk");
                stcheck.setPrice(tempprice + price);
                stcheck.setTotal(String.valueOf(Double.parseDouble(temptotal) + total));
            }
            realm.commitTransaction();
            realm.close();

        }
        Log.d("nbvcxz3", qtyforSearch + " kkkk");
        if (searchflag == true) {
            Intent i = new Intent(DemoActivity.this, SearchActivity.class);
            Bundle extras = new Bundle();
            extras.putString("dateval", String.valueOf(intenetdate));
            extras.putLong("timeinms", ms);
            extras.putString("itemsincart", qtyforSearch);
            extras.putString("category", category);
            i.putExtras(extras);

            startActivity(i);

            finish();
        }

    }

    void shoppingCart(boolean submit, Toolbar t1) {
        List<TrackParseFile> items = mMultiSelect.getSelectedItems();
        final int selectedCount = items.size();
        if (selectedCount == 0 && tempcontent.equals("")) {
            if (submit == false)
                Toast.makeText(getApplicationContext(), "No Items Added to Cart.", Toast.LENGTH_SHORT).show();

        } else {
            layout_MainMenu.getForeground().setAlpha(220);
            LayoutInflater layoutInflater = LayoutInflater.from(DemoActivity.this);
            View popupview = layoutInflater.inflate(R.layout.cartlayout, null);
            LinearLayout ll = (LinearLayout) popupview.findViewById(R.id.cartll);
            ll.setOrientation(LinearLayout.VERTICAL);
            TextView tv = (TextView) popupview.findViewById(R.id.cartcheckout);
            TextView sctotal = (TextView) popupview.findViewById(R.id.sctotalprice);
            //sctotal.setText
            if (submit == true) tv.setText("Submit");
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tv.getText().equals("Checkout")) {
                        LayoutInflater popupiteminflater = LayoutInflater.from(DemoActivity.this);
                        View popupitem = popupiteminflater.inflate(R.layout.address_view_cart, ll, false);
                        TextView add = (TextView) popupitem.findViewById(R.id.shoppingcartaddresstv);
                        TextView date = (TextView) popupitem.findViewById(R.id.shoppingCartdatetv);
                        CardView addroot = (CardView) popupitem.findViewById(R.id.shoppingcardaddressroot);
                        String tempdate = String.valueOf(a);
                        if (tempdate.length() == 7)
                            date.setText("Order Date: " + tempdate.substring(0, 1) + "/" + tempdate.substring(1, 3) + "/" + tempdate.substring(3, 7));
                        else
                            date.setText("Order Date: " + tempdate.substring(0, 2) + "/" + tempdate.substring(2, 4) + "/" + tempdate.substring(4, 8));

                        add.setText("Address \n" + LoginActivity.name + "\n" + LoginActivity.st1 + "\n" + LoginActivity.st2 + "\n" + "Chennai, TN-" + LoginActivity.zip);
                        ll.addView(addroot, 0);
                        tv.setText("Submit");
                    } else {
                        String q = "";
                        String p = "";
                        double t = 0;
                        String c = "";
                        //if things are chnaged in carti
                        if (cartedit) {
                            int cnt = 0;


                            while (cnt < qtymap.size()) {

                                c = c + itemtrack.get(cnt) + "y4t1qm3az8d1q" + qtymap.get(itemtrack.get(cnt)) + "y1t5qm7az9d0q";


                                p = p + pricemap.get(itemtrack.get(cnt)) + "y4t1qm3az8d1q" + qtymap.get(itemtrack.get(cnt)) + "y1t5qm7az9d0q";


                                t = t + (Double.parseDouble(pricemap.get(itemtrack.get(cnt))) * qtymap.get(itemtrack.get(cnt)));

                                q = q + itemtrack.get(cnt) + "y4t1qm3az8d1q" + qtymap.get(itemtrack.get(cnt)) + "y1t5qm7az9d0q";
                                cnt++;
                            }
                        }
                        popupview.setAlpha(0.3f);
                        //UNCOMMENT AND FIX BELOW LOGIC
                        //**********************************
                        //***********************************
                        //***********************************



                        if (!existflag) {
                            if(recordexistflag.equals("false")) {
                                if (cartedit) {
                                    //savetodatabase(con, qtymap.size(), null, true, p, t, q);
                                    initiatePopupWindow(root, popupview, t);
                                } else {
                                    //savetodatabase(it, 0, null, true, tprice, ttotal, tqty);
                                    initiatePopupWindow(root, popupview, ttotal);
                                }
                            }else
                            {
                                if (cartedit) {
                                    savetodatabase(c, qtymap.size(), null, true, p, t, q, "true");
                                } else {
                                    savetodatabase(tempcontent, 0, null, true, tempprice, Double.parseDouble(temptotal), tempqty, "true");

                                }
                            }

                        } else {

//                            if (cartedit) {
//                                //savetodatabase(con, qtymap.size(), null, true, p, t, q);
//                                initiatePopupWindow(root, popupview, t);
//                            } else {
//                                //savetodatabase(tempcontent, 0, null, true, tempprice, Double.parseDouble(temptotal), tempqty);
//                                initiatePopupWindow(root, popupview, Double.parseDouble(temptotal));
//
//                            }
                            //TODO HAVE TO CHECK HRE
                            //
                            if (cartedit) {
                                savetodatabase(c, qtymap.size(), null, true, p, t, q, "true");
                            } else {
                                savetodatabase(tempcontent, 0, null, true, tempprice, Double.parseDouble(temptotal), tempqty, "true");

                            }
//                            ParseQuery<ParseObject> query = ParseQuery.getQuery("SelectedItems");
//                            query.whereEqualTo("id", a);
//                            String finalP = p;
//                            double finalT = t;
//                            String finalQ = q;
//                            String finalC = c;
//                            double finalT1 = t;
//                            query.findInBackground(new FindCallback<ParseObject>() {
//                                @Override
//                                public void done(List<ParseObject> objects, ParseException e) {
//                                    if(e==null)
//                                    {
//                                        double h = Double.parseDouble(objects.get(0).get("total").toString());
//                                        if (cartedit) {
//                                            if(h > finalT) savetodatabase(finalC, qtymap.size(), null, true, finalP, finalT, finalQ, "true");
//                                            else initiatePopupWindow(root, popupview, finalT1);
//                                        } else {
//                                            if(h > Double.parseDouble(temptotal))
//                                                savetodatabase(tempcontent, 0, null, true, tempprice, Double.parseDouble(temptotal), tempqty, "true");
//                                            else initiatePopupWindow(root, popupview, Double.parseDouble(temptotal));
//                                        }
//                                    }
//                                }
//                            });
                        }
                    }

                }
            });
            TextView goback = (TextView) popupview.findViewById(R.id.cartgoback);
            goback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupwdw.dismiss();
                    layout_MainMenu.getForeground().setAlpha(0);
                }
            });

            if (submit) {
                LayoutInflater popupiteminflater = LayoutInflater.from(DemoActivity.this);
                View popupitem = popupiteminflater.inflate(R.layout.address_view_cart, ll, false);
                TextView add = (TextView) popupitem.findViewById(R.id.shoppingcartaddresstv);
                TextView date = (TextView) popupitem.findViewById(R.id.shoppingCartdatetv);
                CardView addroot = (CardView) popupitem.findViewById(R.id.shoppingcardaddressroot);
                String tempdate = String.valueOf(a);
                if (tempdate.length() == 7)
                    date.setText("Order Date: " + tempdate.substring(0, 1) + "/" + tempdate.substring(1, 3) + "/" + tempdate.substring(3, 7));
                else
                    date.setText("Order Date: " + tempdate.substring(0, 2) + "/" + tempdate.substring(2, 4) + "/" + tempdate.substring(4, 8));

                add.setText("Address \n" + LoginActivity.name + "\n" + LoginActivity.st1 + "\n" + LoginActivity.st2 + "\n" + "Chennai, TN-" + LoginActivity.zip);
                ll.addView(addroot);
            }


            for (Map.Entry<String, Integer> h : hm.entrySet()) {
                tqty = tqty + h.getKey() + "y4t1qm3az8d1q" + h.getValue() + "y1t5qm7az9d0q";
                qtymap.put(h.getKey(), h.getValue());
                itemtrack.add(h.getKey());
                Log.d("hhhhhjjjjjsize1", itemtrack.size() + " " + h.getKey());
            }


            if (selectedCount == 0) {
                sctotal.setText(temptotal);
            } else {

                for (TrackParseFile s : items) {

                    int te = 1;

                    if (DemoActivity.hm.get(s.getTrackName()) != null) {
                        te = DemoActivity.hm.get(s.getTrackName());
                    }

                    it = it + s.getTrackName() + "y4t1qm3az8d1q" + te + "y1t5qm7az9d0q";


                    tprice = tprice + s.getArtist() + "y4t1qm3az8d1q" + te + "y1t5qm7az9d0q";
                    pricemap.put(s.getTrackName(), s.getArtist());
                    ttotal = ttotal + (Double.parseDouble(s.getArtist()) * te);

                }
                tempcontent = tempcontent + it;
                tempprice = tempprice + tprice;
                tempqty = tempqty + tqty;
                temptotal = String.format("%.2f", Double.parseDouble(temptotal) + ttotal);
                sctotal.setText(temptotal);

            }

            int cnt = 0;
            String[] asplit = tempcontent.split("y1t5qm7az9d0q");
            if (asplit.length == 0) {
                //TODO pass a string that says no items in shopping cart
            } else {
                String[] psplit = tempprice.split("y1t5qm7az9d0q");
                for (String i : asplit) {
                    String[] ia = i.split("y4t1qm3az8d1q");
                    String[] ip = psplit[cnt].split("y4t1qm3az8d1q");
                    LayoutInflater popupiteminflater = LayoutInflater.from(DemoActivity.this);
                    View popupitem = popupiteminflater.inflate(R.layout.item_view_cart, ll, false);
                    CardView c = (CardView) popupitem.findViewById(R.id.cartcardroot);
                    TextView desc = (TextView) popupitem.findViewById(R.id.cartdesc);
                    TextView itemtotal = (TextView) popupitem.findViewById(R.id.cartitemTotal);
                    ScrollableNumberPicker np = (ScrollableNumberPicker) popupitem.findViewById(R.id.cart_no_picker);
                    desc.setText(ia[0]);
                    TextView cartqty = (TextView) popupitem.findViewById(R.id.cartqty);
                    cartqty.setText("Price " + ip[0] + " INR");
                    double lintotal = Double.parseDouble(ip[0]) * (Integer.parseInt(ip[1]));
                    itemtotal.setText(String.format("%.2f", lintotal));
                    np.setValue(Integer.parseInt(ip[1]));
                    np.setListener(new ScrollableNumberPickerListener() {
                        @Override
                        public void onNumberPicked(int value) {
                            if (value == 0) {
                                qtymap.put(desc.getText().toString(), value);
                                cartedit = true;
                                AlertDialog.Builder dialog = new AlertDialog.Builder(DemoActivity.this);
                                dialog.setTitle("Confirm")
                                        .setIcon(R.drawable.alert)
                                        .setMessage("Are you sure you want to remove this item from cart?")
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialoginterface, int i) {
                                                np.setValue(1);
                                                dialoginterface.cancel();
                                            }
                                        })
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialoginterface, int i) {
                                                cartedit = true;
                                                ll.removeView(((CardView) np.getParent().getParent()));
                                                pricemap.remove(desc.getText());
                                                qtymap.remove(desc.getText());
                                                hm.remove(desc.getText());
                                                Log.d("jkjkjkjkjkhm1", hm.size() + "");
                                                itemtrack.remove(itemtrack.indexOf(desc.getText().toString()));
                                            }
                                        }).show();
                            } else {
                                double dt = Double.parseDouble(sctotal.getText().toString()) - Double.parseDouble(itemtotal.getText().toString());
                                qtymap.put(desc.getText().toString(), value);
                                itemtotal.setText(String.format("%.2f", Double.parseDouble(ip[0]) * value));
                                dt = dt + Double.parseDouble(ip[0]) * value;
                                sctotal.setText(String.format("%.2f", dt));
                                cartedit = true;
                            }
                        }
                    });
                    cnt++;
                    ll.addView(c);
                }


            }

            popupwdw = new PopupWindow(popupview, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            popupwdw.setAnimationStyle(android.R.style.Animation_Dialog);
            popupwdw.setOutsideTouchable(true);
            popupwdw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();

                    //if things are chnaged in cart
                    if (cartedit) {
                        int cnt = 0;

                        while (cnt < qtymap.size()) {

                            //TODO build the stringggggg and sub
                            c = c + itemtrack.get(cnt) + "y4t1qm3az8d1q" + qtymap.get(itemtrack.get(cnt)) + "y1t5qm7az9d0q";


                            p = p + pricemap.get(itemtrack.get(cnt)) + "y4t1qm3az8d1q" + qtymap.get(itemtrack.get(cnt)) + "y1t5qm7az9d0q";

                            t = t + (Double.parseDouble(pricemap.get(itemtrack.get(cnt))) * qtymap.get(itemtrack.get(cnt)));

                            q = q + itemtrack.get(cnt) + "y4t1qm3az8d1q" + qtymap.get(itemtrack.get(cnt)) + "y1t5qm7az9d0q";
                            Log.d("jkjkjkjkjqqq", q);
                            cnt++;
                        }
                    }
                    if (!existflag) {
                        if (cartedit) {
                            Selecttable selecttable = new Selecttable(a, c, p, String.valueOf(t), a, a, 1, q,
                                    month[0], year, 0, 0, "", "");
                            Log.d("jkjkjkjkjqqq1", q);
                            realm.copyToRealm(selecttable);
                        } else {
                            Selecttable selecttable = new Selecttable(a, it, tprice, String.valueOf(ttotal), a, a, 1, tqty,
                                    month[0], year, 0, 0, "", "");
                            realm.copyToRealm(selecttable);
                        }

                    } else {

                        if (cartedit) {
                            Selecttable stcheck = realm.where(Selecttable.class)
                                    .equalTo("id", a)
                                    .findFirst();
                            stcheck.setItems(c);
                            stcheck.setQty(q);
                            stcheck.setPrice(p);
                            stcheck.setTotal(String.valueOf(t));
                        } else {

                            Selecttable stcheck = realm.where(Selecttable.class)
                                    .equalTo("id", a)
                                    .findFirst();
                            stcheck.setItems(tempcontent);
                            stcheck.setQty(tempqty);
                            stcheck.setPrice(tempprice);
                            stcheck.setTotal(temptotal);
                        }
                    }
                    realm.commitTransaction();
                    realm.close();

                    popupwdw.dismiss();
                    Intent i = new Intent(DemoActivity.this, DemoActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("dateval", String.valueOf(a));
                    extras.putLong("timeinms", ms);
                    extras.putString("category", category);
                    extras.putString("demo", "true");
                    i.putExtras(extras);

                    startActivity(i);

                    finish();

                }
            });
            popupwdw.showAtLocation(root, Gravity.LEFT, 0, 0);

        }
    }

    public void sendEmail(String content, String price, boolean qtyind, double total) {
        String[] items = content.split("y1t5qm7az9d0q");
        String[] pri = price.split("y1t5qm7az9d0q");

        String[] check = null;
        if (items.length > 0)
            check = items[0].split("y4t1qm3az8d1q");
        if (check != null) {
            if (check.length > 1) qtyind = true;
        }

        String v = "<table border=1 bordercolor='#000000' cellpadding='9' cellspacing='5' width='500' style='text-align:center;'>";
        v = v + "<tr><td align='center' border=1 background-color='#4CAF50'>S No</td><td border=1 align='left'>Item</td>" +
                "<td border=1>Price</td><td border=1>Qty</td><td>Line Total</td></tr>";
        int cnt = 1;
        if (qtyind) {
            for (String h : items) {
                String[] k = h.split("y4t1qm3az8d1q");
                String[] p = pri[cnt - 1].split("y4t1qm3az8d1q");
                v = v + "<tr><td align='center' color='#FFFFFF'>" + cnt + "</td><td align='left' paddingLeft='5'>" + k[0] + "</td>" +
                        "<td>" + p[0] + "</td><td>" + k[1] + "</td><td>" + Integer.parseInt(k[1]) * Double.parseDouble(p[0]) + "</td></tr>";
                cnt++;
            }
            String space = " ";
            String tot = "Grand Total";
            v = v + "<tr><td>" + space + "</td><td>" + space + "</td><td>" + space + "</td>" +
                    "<td align='center'>" + tot + "</td><td>" + total + "</td></tr>";
            v = v + "</table>";
        } else {
            for (String h : items) {
                v = v + cnt + ". " + h;
                cnt++;
            }
        }


        Map<String, String> params = new HashMap<>();
        params.put("toEmail", ParseUser.getCurrentUser().getEmail());
        b = b + 1;
        params.put("subject", "Order Confirmed for Delivery On " + date + "_" + b);
        params.put("body", "The order is modified successfully and its processing.</br></br>Items In your Order</br> </br>" + v);
        ParseCloud.callFunctionInBackground("sendEmail", params, new FunctionCallback<Object>() {
            @Override
            public void done(Object response, ParseException exc) {
                if (exc == null) {
                } else {
                }
            }
        });
    }


    private void initiatePopupWindow(View v, View pv, final double temp) {
        Log.d("jhjhjghhh11", temp + "");

        try {
            LayoutInflater layoutInflater = LayoutInflater.from(DemoActivity.this);
            View popupview = layoutInflater.inflate(R.layout.popuppayment, null);
            pw = new PopupWindow(popupview, MyApplication.width - 60, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);
            pw.setOutsideTouchable(true);
            pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    pv.setAlpha(1f);
                }
            });

            Button wallet = (Button) popupview.findViewById(R.id.wallet);
            wallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!existflag) {
                        if (cartedit) {
                            savetodatabase(c, qtymap.size(), null, true, p, t, q, "true");
                        } else {
                            savetodatabase(it, 0, null, true, tprice, ttotal, tqty, "true");
                        }

                    } else {

                        if (cartedit) {
                            savetodatabase(c, qtymap.size(), null, true, p, t, q, "true");
                        } else {
                            savetodatabase(tempcontent, 0, null, true, tempprice, Double.parseDouble(temptotal), tempqty, "true");

                        }
                    }
                }
            });
            Button other = (Button) popupview.findViewById(R.id.paynow);
            other.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("jhjhjghhh1", temp + "");
                    double temp1 = temp * 100;
                    Log.d("jhjhjghhh2", temp1 + "");
                    double t =  LoginActivity.round(temp1,2);
                    Log.d("jhjhjghhh3", t + "");
                    Checkout checkout = new Checkout();
                    checkout.setImage(R.drawable.cloudsanelogohome);
                    final Activity activity = DemoActivity.this;
                    JSONObject options = new JSONObject();
                    try {
                        options.put("name", "Pepitas");
                        options.put("description", "Order #123456");
                        options.put("currency", "INR");
                        JSONObject theme = new JSONObject();
                        theme.put("color", "#000000");
                        options.put("theme", theme);
                        Log.d("jhjhjghhh", temp + "");
                        options.put("amount", String.valueOf(t));
                        checkout.open(activity, options);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateorder(ParseObject object, String content, int count, boolean qtyind, String price, double total, String qty,
                            int finalB, boolean enoughcredit) {
        credittemp = Double.parseDouble(object.get("total").toString());
        object.put("items", content);
        object.put("updatedate", a);
        object.put("price", price);
        object.put("total", String.valueOf(total));
        object.put("quantity", qty);
        object.put("version", finalB + 1);
        //to reset the payment from credit to money when credit is less than the new difference
        if(!enoughcredit) object.put("creditflag", "false");
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    object.saveEventually();
                } else {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("MonthTotal");
                    query.whereEqualTo("month", month[0]);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(final List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                if (objects.size() > 0) {
                                    List<Integer> date = objects.get(0).getList("datelist");
                                    List<String> total1 = objects.get(0).getList("totallist");
                                    List<String> dateplustotal = objects.get(0).getList("datetotal");
                                    int idx = date.indexOf(a);
                                    if (idx != -1) {
                                        total1.remove(idx);
                                        dateplustotal.remove(idx);
                                        total1.add(idx, String.valueOf(total));
                                        dateplustotal.add(idx, a + "$" + String.valueOf(total));
                                        objects.get(0).put("datelist", date);
                                        objects.get(0).put("totallist", total1);
                                        objects.get(0).put("datetotal", dateplustotal);
                                        objects.get(0).saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e != null) {
                                                    objects.get(0).saveEventually();
                                                } else {
                                                    newtonCradleLoading.stop();
                                                    newtonCradleLoading.setVisibility(View.GONE);
                                                }

                                            }
                                        });
                                    }

                                }
                            } else {
                                objects.get(0).saveEventually();
                            }

                        }
                    });
                    if(!updateflagforpyment) {
                        //***update credit back***
                        ParseQuery<ParseObject> q = ParseQuery.getQuery("DefaultItem");
                        q.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e == null) {
                                    if (objects.size() > 0) {
                                        //double cre = (double)((Math.round(objects.get(0).getDouble("credit"))*100)/100);
                                        double cre = LoginActivity.round(objects.get(0).getDouble("credit"),2);
                                        //based on enough credit available
                                        if (enoughcredit == true) {
                                            Log.d("Tesst cre1",total +"  "+cre+" "+credittemp);
                                            cre = cre + total;
                                            Log.d("Tesst cre2",total +"  "+cre+" "+total);
                                            cre = LoginActivity.round(cre - credittemp,2);
                                            Log.d("Tesst cre3",total +"  "+cre);
                                        } else if (updateflagforpaymentlessamt)
                                        {
                                            cre = cre - differenceinmount;
                                        } else {
                                            cre = cre - credittemp;
                                        }
                                        LoginActivity.credit = cre;
                                        Log.d("Tesst4",total +"  "+LoginActivity.credit);
                                        objects.get(0).put("credit", cre);
                                        objects.get(0).saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e != null) {
                                                    objects.get(0).saveEventually();
                                                } else {

                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                        sendEmail(content, price, qtyind, total);
                        startActivity(new Intent(DemoActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            }
        });
    }

    public void updateOrdernodata(ParseObject object, int finalB){

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    object.saveEventually();
                } else {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("MonthTotal");
                    query.whereEqualTo("month", month[0]);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(final List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                if (objects.size() > 0) {
                                    List<Integer> date1 = objects.get(0).getList("datelist");
                                    List<String> total1 = objects.get(0).getList("totallist");
                                    List<String> dateplustotal = objects.get(0).getList("datetotal");
                                    int idx = date1.indexOf(a);
                                    if (idx != -1) {
                                        total1.remove(idx);
                                        dateplustotal.remove(idx);
                                        total1.add(idx, "nodata");
                                        dateplustotal.add(idx, "nodata");
                                        objects.get(0).put("datelist", date1);
                                        objects.get(0).put("totallist", total1);
                                        objects.get(0).put("datetotal", dateplustotal);
                                        objects.get(0).saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e != null) {
                                                    objects.get(0).saveEventually();
                                                } else {
                                                    newtonCradleLoading.stop();
                                                    newtonCradleLoading.setVisibility(View.GONE);

                                                    Map<String, String> params = new HashMap<>();

                                                    params.put("toEmail", ParseUser.getCurrentUser().getEmail());
                                                    int y = finalB + 1;
                                                    params.put("subject", "Order Cancelled for " + date + "_" + y);
                                                    params.put("body", "The order is cancelled. Hoping to serve you soon.");
                                                    ParseCloud.callFunctionInBackground("sendEmail", params, new FunctionCallback<Object>() {
                                                        @Override
                                                        public void done(Object response, ParseException exc) {
                                                            if (exc == null) {
                                                            } else {
                                                            }
                                                        }
                                                    });

                                                }

                                            }
                                        });
                                    }

                                }
                            } else {
                                objects.get(0).saveEventually();
                            }

                        }
                    });
                    //to update total

                        ParseQuery<ParseObject> q = ParseQuery.getQuery("DefaultItem");
                        q.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e == null) {
                                    if (objects.size() > 0) {
                                        double cre = objects.get(0).getDouble("credit");
                                        Log.d("creee",cre+" "+credittemp);
                                        cre = cre - credittemp;
                                        Log.d("creee1",cre+"");
                                        LoginActivity.credit = cre;
                                        objects.get(0).put("credit", cre);
                                        objects.get(0).saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e != null) {
                                                    objects.get(0).saveEventually();
                                                } else {
                                                    startActivity(new Intent(DemoActivity.this, MainActivity.class));
                                                    finish();
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });


                }
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(getApplicationContext(), "Payment Successful.", Toast.LENGTH_SHORT).show();
        if (updateflagforcredit) {
            //updateflagforcredit = false;
            updateorder(pob, updateContent, updateCount, updateQtyind, updatePrice, updateTotal, updateQty, updateFinalb,false);
            sendEmail(updateContent, updatePrice, updateQtyind, updateTotal);
            startActivity(new Intent(DemoActivity.this, MainActivity.class));
            finish();
        }
        else if(updateflagforpyment){
            //updateflagforpyment = false;
            updateorder(pob, updateContent, updateCount, updateQtyind, updatePrice, updateTotal, updateQty, updateFinalb,false);
            sendEmail(updateContent, updatePrice, updateQtyind, updateTotal);
            startActivity(new Intent(DemoActivity.this, MainActivity.class));
            finish();
        } else {
            if (!existflag) {
                if (cartedit) {
                    savetodatabase(c, qtymap.size(), null, true, p, t, q, "false");
                } else {
                    savetodatabase(it, 0, null, true, tprice, ttotal, tqty, "false");
                }

            } else {

                if (cartedit) {
                    savetodatabase(c, qtymap.size(), null, true, p, t, q, "false");
                } else {
                    savetodatabase(tempcontent, 0, null, true, tempprice, Double.parseDouble(temptotal), tempqty, "false");

                }
            }
        }
    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(getApplicationContext(), "Payment Not Successful. Please check your payment method and internet before proceeding", Toast.LENGTH_SHORT).show();

    }
}



