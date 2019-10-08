package com.hulian.oa.address.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.hulian.oa.R;
import com.hulian.oa.address.bean.People;

import java.util.List;

public class PersonAdapter extends BaseAdapter {
    private String changeStr = "";
    //
    private List<People> mlistdatas;
    private LayoutInflater mInflater;

    public PersonAdapter(Context mcontext, List<People> mlistdatas) {
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

        view = mInflater.inflate(R.layout.item_person, null);
        TextView tv_name = view.findViewById(R.id.tv_name);
        tv_name.setText(mlistdatas.get(i).getName());
//        //处理关键字颜色变化
//        if (null != mlistdatas.get(i) && mlistdatas.get(i).contains(changeStr)) {
//            int index = mlistdatas.get(i).indexOf(changeStr);
//            int len = changeStr.length();
//            Spanned temp = Html.fromHtml(mlistdatas.get(i).substring(0, index)
//                    + "<font color=#ff0000>"
//                    + mlistdatas.get(i).substring(index, index + len) + "</font>"
//                    + mlistdatas.get(i).substring(index + len, mlistdatas.get(i).length()));
//            tv_name.setText(temp);
//        } else {
//            tv_name.setText(mlistdatas.get(i));
//        }

        return view;
    }


}
