package com.hulian.oa.work.activity.notice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Department;

import java.util.List;

public class GridViewAdapter  extends BaseAdapter {
    private Context context;
    private List<Department> list;
    LayoutInflater layoutInflater;
    private TextView bt_sel_peo;
    private FrameLayout fl_content;
    private  ImageView iv_add;
    public GridViewAdapter(Context context, List<Department> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();//注意此处
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.item_notice_people, null);
        bt_sel_peo = (TextView) convertView.findViewById(R.id.bt_sel_peo);
        fl_content=convertView.findViewById(R.id.fl_content);
        iv_add=convertView.findViewById(R.id.iv_add);
        bt_sel_peo.setText(list.get(position).getDeptName());
        return convertView;
    }

}