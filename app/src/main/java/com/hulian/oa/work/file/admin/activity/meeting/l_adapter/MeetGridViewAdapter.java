package com.hulian.oa.work.file.admin.activity.meeting.l_adapter;

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
import com.hulian.oa.bean.People;

import java.util.List;

public class MeetGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<People> list;
    LayoutInflater layoutInflater;
    private TextView bt_sel_peo;
    private FrameLayout fl_content;
    private  ImageView iv_add;
    public MeetGridViewAdapter(Context context, List<People> list) {
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
        bt_sel_peo.setText(list.get(position).getUserName());
//        if (position < list.size()) {
//            bt_sel_peo.setText(list.get(position).getDeptName());
//            fl_content.setVisibility(View.VISIBLE);
//            iv_add.setVisibility(View.GONE);
//
//        }else{
//            fl_content.setVisibility(View.GONE);
//            iv_add.setVisibility(View.VISIBLE);
//        }
        return convertView;
    }

}
