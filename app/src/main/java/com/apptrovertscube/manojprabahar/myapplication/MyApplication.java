package com.apptrovertscube.manojprabahar.myapplication;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by mna on 7/17/17.
 */

public class MyApplication extends Application {
    static int width;
    static  int height;
    @Override
    public void onCreate() {
        super.onCreate();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
         width = size.x;
         height = size.y;
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);


        // Add your initialization code here

        Log.d("Credentials--->",Credentials.id +"  "+Credentials.pwd);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(Credentials.id)
                .clientKey(Credentials.pwd)
                .server("https://parseapi.back4app.com/")
                .enableLocalDataStore()
                .build()
        );


        //ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        ParseFacebookUtils.initialize(getApplicationContext());
    }
}
