package com.hulian.oa.work.file.admin.activity.mail.l_adapter;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.hulian.oa.R;
import com.hulian.oa.bean.Sche_Bean_x;
import com.hulian.oa.bean.SecondMail_bean_x;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.URLImageParser;
import com.hulian.oa.work.file.admin.activity.mail.MailParticularsActivity;
import com.hulian.oa.work.file.admin.activity.notice.NoticeParticularsActivity;

import java.util.ArrayList;
import java.util.List;

public class L_MailReciveAdapter extends RecyclerView.Adapter <L_MailReciveAdapter.ViewHolder> {
    private Context mContext;
    private List<SecondMail_bean_x> dataList = new ArrayList<>();

    public void addAllData(List<SecondMail_bean_x> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
        notifyDataSetChanged();
    }

    public L_MailReciveAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_pic_news,iv_fijian;
        public TextView tv_name;
        public TextView tv_time;
        public TextView tv_content,tv_type,tv_des;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_type=(TextView) itemView.findViewById(R.id.tv_type);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_des=(TextView) itemView.findViewById(R.id.tv_des);
            iv_fijian=itemView.findViewById(R.id.iv_fijian);
        }
    }

    @Override
    public L_MailReciveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_item_recive_mail, parent, false);
        return new L_MailReciveAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(L_MailReciveAdapter.ViewHolder holder, final int position) {
        holder.tv_type.setText(dataList.get(position).getSender().substring(0,1));
        holder.tv_name.setText(dataList.get(position).getSender().split("<")[0]+"");
        holder.tv_time.setText(TimeUtils.getDateToString(dataList.get(position).getSendDate()));

        if(dataList.get(position).isIsAttach()){
            holder.iv_fijian.setVisibility(View.VISIBLE);
        }
        else {
            holder.iv_fijian.setVisibility(View.INVISIBLE);
        }
        holder.tv_content.setText(dataList.get(position).getTitle());
        URLImageParser imageGetter = new URLImageParser( holder.tv_des);
        holder.tv_des.setText(Html.fromHtml(dataList.get(position).getContent(), imageGetter, null));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SecondMail_bean_x secondMail_bean_x = new SecondMail_bean_x();
                secondMail_bean_x = dataList.get(position);
                Intent intent=new Intent(mContext, MailParticularsActivity.class);
                intent.putExtra("key",secondMail_bean_x);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }



}

