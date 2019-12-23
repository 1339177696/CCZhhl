package com.hulian.oa.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hulian.oa.R;
import com.hulian.oa.bean.News;
import com.hulian.oa.net.Urls;
import com.hulian.oa.news.activity.NewsActivityInfo;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.URLImageParser;

import java.util.ArrayList;
import java.util.List;

public class NewsViewAdapter extends RecyclerView.Adapter <NewsViewAdapter.ViewHolder>{
    private Context mContext;
    private List<News> dataList = new ArrayList<>();


    public void addAllData(List<News> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public NewsViewAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView tv_content,tv_time,tv_dianzanCount;
        public SimpleDraweeView iv_head;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_name);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_head = (SimpleDraweeView) itemView.findViewById(R.id.iv_head);
            tv_dianzanCount=(TextView) itemView.findViewById(R.id.tv_dianzanCount);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_1, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(dataList.get(position).getJournalismTitle());
        holder.tv_content.setText(Html.fromHtml(dataList.get(position).getJournalismContent()));
        holder.tv_time.setText(TimeUtils.getDateToString(dataList.get(position).getCreateTime()));
        holder.iv_head.setImageURI(dataList.get(position).getJournalismImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent= new Intent(mContext, NewsActivityInfo.class);
               intent.putExtra("getIsCollect",dataList.get(position).getIsCollect());
               intent.putExtra("getJournalismId",dataList.get(position).getJournalismId());
               mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
