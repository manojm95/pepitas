package com.apptrovertscube.manojprabahar.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.facebook.FacebookSdk;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.razorpay.Checkout;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.datatype.DatatypeConstants;

//https://stackoverflow.com/questions/27448684/android-facebook-sdk-generate-release-key-hash

public class LoginActivity extends AppCompatActivity {

    public static double treshold=0;
    public static double credit=0;
    ViewPager vp;

    static Activity c;
    static TabLayout y;
    static String defaultitem = "";
    String[] imagesresource;
    String[] nameresource;
    float[] priceresource;

    static boolean check = false;
    static String name = "";
    static String st1 = "";
    static String st2 = "";
    static String landmark = "";
    static String state = "";
    static String city = "";
    static String zip = "";
    static String phno = "";

    static int a=0;
    java.util.Calendar calendar = java.util.Calendar.getInstance();
    static String[] month = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct", "Nov","Dec"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.viewpager_layout);
        Checkout.preload(getApplicationContext());

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Address");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null) {
                    if(objects.size()>0) {
                        check=true;
                        name = objects.get(0).getString("name");
                        st1 = objects.get(0).getString("street1");
                        st2 = objects.get(0).getString("street2");
                        landmark = objects.get(0).getString("landmark");
                        state = objects.get(0).getString("state");
                        city = objects.get(0).getString("city");
                        phno = objects.get(0).getString("phone");
                        zip = objects.get(0).getString("zip");
                    }
                }
            }
        });

        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        String finaldate = formatter.format(calendar.getTime());
        Log.d("Manojjj Date Test", "The date is " + finaldate);
         a = Integer.parseInt(finaldate);

//        ArrayList<ParseFile> pFileList = new ArrayList<ParseFile>();
//
//        ArrayList<String> namelist = new ArrayList<>();
//        ArrayList<String> typelist = new ArrayList<>();
//
//        List<String> pricelist = Arrays.asList("1.1",
//                "2.1",
//                "3.1",
//                "4.1",
//                "5.1",
//                "6.1",
//                "7.1",
//                "8.1",
//                "9.1",
//                "10.1",
//                "11.1",
//                "1.1",
//                "2.1",
//                "3.1",
//                "4.1",
//                "5.1",
//                "6.1",
//                "7.1",
//                "8.1",
//                "9.1",
//                "10.1",
//                "11.1",
//                "1.1",
//                "2.1",
//                "3.1",
//                "4.1",
//                "5.1",
//                "6.1",
//                "7.1",
//                "8.1",
//                "9.1",
//                "10.1",
//                "11.1",
//                "9.1",
//                "10.1",
//                "11.1",
//                "4.1",
//                "5.1",
//                "6.1",
//                "7.1",
//                "8.1");
//
//        imagesresource = getResources().getStringArray(R.array.imagesforlistfinal);
//        nameresource = getResources().getStringArray(R.array.namesforlistfinal);
//
//        Collections.addAll(namelist,nameresource);
//
//
//        for(String imageresource: imagesresource)
//        {
//            Log.d("ggggghhhhh",imageresource);
//            //namelist.add(imageresource);
//            typelist.add("all");
//            int img=-1;
//            try {
//               img = getResources().getIdentifier(imageresource, "drawable", getPackageName());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            Bitmap bm = BitmapFactory.decodeResource(getResources(), img );
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            byte[] bitmapdata = stream.toByteArray();
//                if(bm!=null)
//                {
//                    //Log.d("hahaha","onDesstroybmnotnull");
//                    bm.recycle();
//                    bm=null;
//                }
//            final ParseFile pFile = new ParseFile(imageresource+".png", bitmapdata);
//            pFile.saveInBackground(new SaveCallback() {
//                @Override
//                public void done(ParseException e) {
//                    if (e != null) {
//                        Log.d("saveerror", "saveerror");
//                    }
//                    else {
//                        Log.d("hghghg",pFile.getUrl());
//                    }
//                }
//            });
//            pFileList.add(pFile);
//        }
//
//        final ParseObject manoj = new ParseObject("ImageRepo");
//        manoj.addAll("imgfiles", pFileList);
//        manoj.put("type", "all");
//        manoj.put("imgfilename",namelist);
//        manoj.put("pricelist",pricelist);
//        manoj.put("version", 1);
//        manoj.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e!=null)
//                {
//                    manoj.saveEventually();
//                    Log.d("hghghg",""+e.getMessage());
//                }
//                else
//                {
//                    Log.d("hghghg","Created good");
//                }
//            }
//        });
//
//
//        //createcategory("Grocs");
//        //To create categories only
//        createmaincategoris();

        c = LoginActivity.this;

        final ParseUser usercheck = ParseUser.getCurrentUser();



        if (usercheck != null) {
            boolean linkedWithFacebook = ParseFacebookUtils.isLinked(usercheck);
            if (linkedWithFacebook) {
                setdefault();
                Log.d("PARSELOGINN", "EMAIL NOT VERIFIED but facebook");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Log.d("PARSELOGINN", "NOT facebook");
                if (usercheck.getBoolean("emailVerified")) {
                    setdefault();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else
                {
                    ParseUser.logOut();
                }
            }
        } else {
            Log.d("ParseUserCheck", "User is Null");
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        y = tabLayout;
        tabLayout.addTab(tabLayout.newTab().setText("Login"));

        tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        vp = (ViewPager) findViewById(R.id.pager);

        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        vp.setAdapter(swipeAdapter);
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //getKeyHash();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private void getKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.apptrovertscube.manojprabahar.myapplication", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            //something
        } catch (NoSuchAlgorithmException e) {
            //something
        }
    }

    void setdefault()
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DefaultItem");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() == 0) {
                        LoginActivity.defaultitem = "milk";
                        ParseObject SelectItems = new ParseObject("DefaultItem");
                        SelectItems.put("id", LoginActivity.a);
                        SelectItems.put("items", "pasta");
                        SelectItems.put("treshold", 200.0);
                        SelectItems.put("credit", 0);
                        SelectItems.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e!=null)
                                {
                                    SelectItems.saveInBackground();
                                }
                            }
                        });
                    }
                    else if(objects.size() > 0 )
                    {
                        LoginActivity.defaultitem = objects.get(0).getString("items");
                        LoginActivity.treshold = objects.get(0).getDouble("treshold");
                        LoginActivity.credit = objects.get(0).getDouble("credit");
                    }
                } else {
                    Log.d("ImageViewScrollTag", "Check error in retrieving trashorarchive");
                }

            }
        });
    }

    private void createcategory(String type) {
        ArrayList<ParseFile> pFileList = new ArrayList<ParseFile>();

        ArrayList<String> namelist = new ArrayList<>();
        //ArrayList<String> typelist = new ArrayList<>();

        List<String> pricelist = Arrays.asList("11.50","22.50", "12.00", "34.00","23.00");

        imagesresource = getResources().getStringArray(R.array.dairy);


        for(String imageresource: imagesresource)
        {

            namelist.add(imageresource);
            int img=-1;
            try {
                img = getResources().getIdentifier(imageresource, "drawable", getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Bitmap bm = BitmapFactory.decodeResource(getResources(), img );
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();
            if(bm!=null)
            {
                //Log.d("hahaha","onDesstroybmnotnull");
                bm.recycle();
                bm=null;
            }
            final ParseFile pFile = new ParseFile("mediaFiles.png", bitmapdata);
            pFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Log.d("saveerror", "saveerror");
                    }
                    else {
                    }
                }
            });
            pFileList.add(pFile);
        }

        final ParseObject manoj = new ParseObject("ImageRepo");
        manoj.addAll("imgfiles", pFileList);
        manoj.put("imgfilename",namelist);
        manoj.put("pricelist",pricelist);
        manoj.put("type",type);
        manoj.put("version", 1);

        manoj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null)
                {
                    manoj.saveEventually();
                }
            }
        });
    }

    private void createmaincategoris() {
        ArrayList<ParseFile> pFileList = new ArrayList<ParseFile>();

        ArrayList<String> namelist = new ArrayList<>();


        String[] catsimages = getResources().getStringArray(R.array.categories_images);


        for(String imageresource: catsimages)
        {

            namelist.add(imageresource.substring(3,4).toUpperCase()+ imageresource.substring(4,imageresource.length()));
            int img=-1;
            try {
                img = getResources().getIdentifier(imageresource, "drawable", getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Bitmap bm = BitmapFactory.decodeResource(getResources(), img );
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();
            if(bm!=null)
            {
                //Log.d("hahaha","onDesstroybmnotnull");
                bm.recycle();
                bm=null;
            }
            final ParseFile pFile = new ParseFile("mediaFiles.png", bitmapdata);
            pFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Log.d("saveerror", "saveerror");
                    }
                    else {
                        Log.d("hghghg",pFile.getUrl());
                    }
                }
            });
            pFileList.add(pFile);
        }

        final ParseObject manoj = new ParseObject("CategoryRepo");
        manoj.addAll("imgfiles", pFileList);
        manoj.put("imgfilename",namelist);
        manoj.put("version", 1);

        manoj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null)
                {
                    manoj.saveEventually();
                }
            }
        });
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
