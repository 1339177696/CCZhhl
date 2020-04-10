package com.hulian.oa.work.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hulian.oa.R;

import java.util.List;

/**
 * Created by Mr.peng on 2017/3/8.
 */

public class Peopleadapter extends BaseAdapter {
    private String changeStr = "";
    //
    private List<String> mlistdatas;
    private LayoutInflater mInflater;

    public Peopleadapter(Context mcontext, List<String> mlistdatas) {
        this.mlistdatas = mlistdatas;
        mInflater = LayoutInflater.from(mcontext);
    }

    @Override
    public int getCount() {
        return mlistdatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mlistdatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = mInflater.inflate(R.layout.item_people, null);
        return view;
    }

}
