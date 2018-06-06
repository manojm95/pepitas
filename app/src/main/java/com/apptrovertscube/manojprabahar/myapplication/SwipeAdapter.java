package com.apptrovertscube.manojprabahar.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by ManojPrabahar on 6/16/2016.
 */
public class SwipeAdapter extends FragmentStatePagerAdapter {
    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int i) {
        Log.d("ManojPos is "+i," ");
        if(i==0) {
            Fragment fragment = new LoginFragment();
//            Bundle bundle = new Bundle();
//            bundle.putInt("count", i + 1);
//            fragment.setArguments(bundle);
            return fragment;
        }
        else
        {
            Fragment fragment = new SignupFragment();
            return fragment;

    }
}}
