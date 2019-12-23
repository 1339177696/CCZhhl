package com.hulian.oa.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Notice;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.URLImageParser;
import com.hulian.oa.work.file.admin.activity.notice.NoticeParticularsActivity;

import java.util.ArrayList;
import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter <NoticeAdapter.ViewHolder>{
    private Context mContext;
    private List<Notice> dataList = new ArrayList<>();


    public void addAllData(List<Notice> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public NoticeAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView tv_time,tv_time2;
        public TextView tv_author;
        public TextView tv_content;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
            tv_time2=(TextView) itemView.findViewById(R.id.tv_time2);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author);
            tv_content= (TextView) itemView.findViewById(R.id.tv_content);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_2, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(dataList.get(position).getNoticeTitle());

        URLImageParser imageGetter = new URLImageParser(holder.tv_content);
        holder.tv_content.setText(Html.fromHtml(dataList.get(position).getNoticeContent(), imageGetter, null));
    //    holder.tv_content.setText(dataList.get(position).getNoticeContent());
        if(dataList.get(position).getCreateTime()!=null) {
            holder.tv_time.setText(TimeUtils.getDateToString3(dataList.get(position).getCreateTime()));
            holder.tv_time2.setText(TimeUtils.getDateToString4(dataList.get(position).getCreateTime()));
        }
//        "发布人："+
        holder.tv_author.setText(dataList.get(position).getCreateBy());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, NoticeParticularsActivity.class);
                intent.putExtra("noticeId",dataList.get(position).getNoticeId());
                //新改的传收藏状态qgl
                intent.putExtra("isCollect",dataList.get(position).getIsCollect());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
