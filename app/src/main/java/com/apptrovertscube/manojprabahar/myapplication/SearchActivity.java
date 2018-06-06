package com.apptrovertscube.manojprabahar.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apptrovertscube.manojprabahar.myapplication.model.Search;
import com.apptrovertscube.manojprabahar.myapplication.model.TrackParseFile;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.victor.loading.newton.NewtonCradleLoading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;

public class SearchActivity extends AppCompatActivity {

    TextView exit;
    ImageView search;
    RelativeLayout searchbarrv;
    SearchView sv;
    RecyclerView rv;
    private RecycAdapterSearch mAdapter;

    private List<Search> searchList = new ArrayList<>();;
    List<String> pfn = null;
    List<String> pfp = null;
    private NewtonCradleLoading ncl=null;
    private int intenetdate=0;
    private long ms=0;
    private String category="";
    private String mm="";
    private String year="";
    String itemsincart = "";
    private HashMap<String, Integer> cartitemmap = new HashMap<>();


    private List<Search> filter(List<Search> l, String text)
    {
        text = text.toLowerCase();
        final List<Search> filterList = new ArrayList<>();
        String t = "";
        for (Search pf : l)
        {
            t = pf.getName().toLowerCase();
            if(t.contains(text))
            {
                filterList.add(pf);
            }
        }
        return filterList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Bundle ext = getIntent().getExtras();
        intenetdate = Integer.parseInt(ext.getString("dateval"));
        itemsincart = ext.getString("itemsincart");
        Log.d("nbvcxz",itemsincart+" kkkk");
        String[] asplit = itemsincart.split("y1t5qm7az9d0q");
        if (!itemsincart.equals("")) {
            for (String qty : asplit) {
                String[] b = qty.split("y4t1qm3az8d1q");
                cartitemmap.put(b[0], Integer.parseInt(b[1]));
                Log.d("nbvcxz",b[0]+"    "+ Integer.parseInt(b[1]));
            }
        }
        String date = String.valueOf(intenetdate);
        if(date.length() == 7 )
        {
            mm = date.substring(1,3);
            year = date.substring(3,7);
            //date = date.substring(0,1)+"/"+ month[0] +"/"+year;

        }
        else
        {
            mm = date.substring(2,4);
            year = date.substring(4,8);
            //date = date.substring(0,2)+"/"+ month[0] +"/"+year;
        }
        ms = ext.getLong("timeinms",0);
        category = ext.getString("category");
        exit = (TextView)findViewById(R.id.searchgoback);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchActivity.this, DemoActivity.class);
                Bundle extras = new Bundle();
                extras.putString("dateval",String.valueOf(intenetdate));
                extras.putLong("timeinms",ms);
                extras.putString("category",category);
                extras.putString("demo","true");
                i.putExtras(extras);
                startActivity(i);
                finish();
            }
        });
        //extras.putString("demo","true");


        searchbarrv = (RelativeLayout)findViewById(R.id.searchactivityrv);

        sv = (SearchView)findViewById(R.id.search_view_search);
        sv.setFocusable(true);
        sv.setIconified(false);
        sv.requestFocusFromTouch();

        ncl = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loading_search);

        rv = (RecyclerView) findViewById(R.id.recycler_view_search);

        mAdapter = new RecycAdapterSearch(searchList);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Search> s = filter(searchList,newText);
                mAdapter.setFilter(s);
                return true;
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(mLayoutManager);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(this,rv,new RecyclerItemClickListener.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                TextView tv = (TextView)view.findViewById(R.id.recycfirstletter);
                if(!itemsincart.contains(tv.getTag().toString().toLowerCase())) {
                    TextView price = (TextView) view.findViewById(R.id.recycitemprice);
                    Toast.makeText(getApplicationContext(), tv.getTag() + " is added to shopping cart.", Toast.LENGTH_SHORT).show();

                    String tn = "";
                    String tp = "";
                    double tt = 0;

                    tn = tv.getTag() + "y4t1qm3az8d1q" + "1" + "y1t5qm7az9d0q";

                    tp = price.getTag() + "y4t1qm3az8d1q" + "1" + "y1t5qm7az9d0q";

                    tt = Double.parseDouble(price.getTag().toString());


                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();


                    Selecttable stcheck = realm.where(Selecttable.class)
                            .equalTo("id", intenetdate)
                            .findFirst();
                    if (stcheck == null) {
                        Selecttable selecttable = new Selecttable(intenetdate, tn, tp, String.valueOf(tt), intenetdate, intenetdate, 1, tn, mm, year, 0, 0, "", "");
                        realm.copyToRealm(selecttable);
                    } else {

                        stcheck.setItems(stcheck.getItems() + tn);
                        stcheck.setQty(stcheck.getQty() + tn);
                        stcheck.setPrice(stcheck.getPrice() + tp);
                        stcheck.setTotal(String.valueOf(Double.parseDouble(stcheck.getTotal()) + tt));
                    }

                    realm.commitTransaction();
                    realm.close();


                    Intent i = new Intent(SearchActivity.this, DemoActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("dateval", String.valueOf(intenetdate));
                    extras.putLong("timeinms", ms);
                    extras.putString("category", category);
                    extras.putString("demo", "true");
                    i.putExtras(extras);
                    startActivity(i);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), tv.getTag() + " is already available in shopping cart for the selected date.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(mAdapter);
        ncl.start();
        ncl.setLoadingColor(Color.parseColor("#707B7C"));
        prepareMovieData();

    }

    private void prepareMovieData() {



        ParseQuery<ParseObject> query = ParseQuery.getQuery("ImageRepo");
        query.whereEqualTo("type","all");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        pfn = objects.get(0).getList("imgfilename");
                        pfp = objects.get(0).getList("pricelist");
                    }


                    int g=0;
                    while (g< pfn.size())
                    {
                        searchList.add(new Search(pfn.get(g).substring(0,1).toUpperCase()+pfn.get(g).substring(1,pfn.get(g).length()),pfp.get(g),""));
                        g++;
                    }
                    ncl.stop();
                    ncl.setVisibility(View.INVISIBLE);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
