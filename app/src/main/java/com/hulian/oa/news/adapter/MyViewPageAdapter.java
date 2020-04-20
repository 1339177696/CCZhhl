package com.hulian.oa.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyViewPageAdapter extends FragmentPagerAdapter {
    private ArrayList<String> titleList;
    private ArrayList<Fragment> fragmentList;
    public MyViewPageAdapter(FragmentManager fm, ArrayList<String> titleList, ArrayList<Fragment> fragmentList) {
        super(fm);
        this.titleList = titleList;
        this.fragmentList = fragmentList;
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    @Override
    public int getCount() {
        return fragmentList.size();
    }
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return titleList.get(position);
//    }
}
