package com.hulian.oa.work.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hulian.oa.R;

import java.util.List;

public class MyGridViewAdapter extends BaseAdapter {
    //声明引用
    private Context mContext;   //这个Context类型的变量用于第三方图片加载时用到
    private LayoutInflater mlayoutInflater;
    private List<String> mlist;
    //创建一个构造函数
    public MyGridViewAdapter(Context context, List<String> mList){
        this.mContext=context;
        //利用LayoutInflate把控件所在的布局文件加载到当前类中
        mlayoutInflater= LayoutInflater.from(context);
        this.mlist=mList;
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    //写一个静态的class,把layout_grid_item的控件转移过来使用
    static class ViewHolder{
        public TextView bt_box;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            //填写ListView的图标和标题等控件的来源，来自于layout_list_item这个布局文件
            //把控件所在的布局文件加载到当前类中
            convertView = mlayoutInflater.inflate(R.layout.item_box,null);
            //生成一个ViewHolder的对象
            holder = new ViewHolder();
            holder.bt_box=convertView.findViewById(R.id.bt_num);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bt_box.setText(1+position+"");
        if(position%2==0){
            holder.bt_box.setBackgroundResource(R.drawable.bt_background);
        }
        else {
            holder.bt_box.setBackgroundResource(R.drawable.bt_background_sel);
        }
        return convertView;
    }
}
