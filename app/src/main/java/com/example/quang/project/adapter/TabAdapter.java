package com.example.quang.project.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by quang on 10/31/2017.
 */

public class TabAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> arrFragment = new ArrayList<>();
    ArrayList<String> arrTitle = new ArrayList<>();

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    public void  addFragment(Fragment fragment,String title) {

       arrFragment.add(fragment);
       arrTitle.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return arrFragment.get(position);
    }

    @Override
    public int getCount() {
        return arrFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return arrTitle.get(position);
    }
}
