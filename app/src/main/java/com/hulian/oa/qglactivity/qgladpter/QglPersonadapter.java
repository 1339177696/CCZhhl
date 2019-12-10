package com.hulian.oa.qglactivity.qgladpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.qglactivity.qglbean.PersonBean;

import java.util.List;

/**
 * Created by Mr.peng on 2017/3/8.
 */

public class QglPersonadapter extends BaseAdapter {
    private List<PersonBean> mlistdatas;
    private LayoutInflater mInflater;

    public QglPersonadapter(Context mcontext, List<PersonBean> mlistdatas) {
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
        view = mInflater.inflate(R.layout.qglperson_list_item, null);
        TextView tv_name = view.findViewById(R.id.tv_name);
        tv_name.setText(mlistdatas.get(i).getUserName());
        return view;
    }



}
