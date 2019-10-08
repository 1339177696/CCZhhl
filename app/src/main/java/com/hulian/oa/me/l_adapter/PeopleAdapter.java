package com.hulian.oa.me.l_adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Department;
import com.hulian.oa.bean.People;

import java.util.HashMap;
import java.util.List;

public class PeopleAdapter extends BaseAdapter {
    private List<People> listText;
    private Context context;
    // 用于记录每个RadioButton的状态，并保证只可选一个
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();

    public PeopleAdapter(List<People> listText, Context context) {
        this.listText = listText;
        this.context = context;
    }

    @Override
    public int getCount() {
        //return返回的是int类型，也就是页面要显示的数量。
        return listText.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            //通过一个打气筒 inflate 可以把一个布局转换成一个view对象
            view = View.inflate(context, R.layout.list_view_depart, null);
        } else {
            view = convertView;//复用历史缓存对象
        }
        //单选按钮的文字
        TextView radioText = (TextView) view.findViewById(R.id.tv_radio_text);
        radioText.setText(listText.get(position).getUserName());
        return view;
    }
}