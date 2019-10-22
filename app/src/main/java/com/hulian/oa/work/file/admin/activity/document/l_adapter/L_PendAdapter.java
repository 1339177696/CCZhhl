package com.hulian.oa.work.file.admin.activity.document.l_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hulian.oa.BuildConfig;
import com.hulian.oa.R;
import com.hulian.oa.bean.Document;
import com.hulian.oa.me.l_adapter.L_CollectionNoticeAdapter;
import com.hulian.oa.pad.PAD_gongwen_SP;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.views.fabVIew.FabAttributes;
import com.hulian.oa.work.file.admin.activity.document.DocumentLotusActivity;
import com.hulian.oa.work.file.admin.activity.document.DocumentLotusInfoActivity;
import com.netease.nim.uikit.common.framework.NimTaskExecutor;

import java.util.ArrayList;
import java.util.List;

public class L_PendAdapter extends RecyclerView.Adapter<L_PendAdapter.ViewHolder> {
    //qgl的分支
    private Context mContext;
    private List<Document> dataList = new ArrayList<>();


    public void addAllData(List<Document> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public L_PendAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ld_stuas;
        private TextView Leader_tv_title;
        private TextView Leader_tv_time;
        private TextView Leader_tv_wenhao;



        public ViewHolder(View itemView) {
            super(itemView);

            ld_stuas = (TextView) itemView.findViewById(R.id.ld_stuas);
            Leader_tv_title = (TextView) itemView.findViewById(R.id.Leader_tv_title);
            Leader_tv_time = (TextView) itemView.findViewById(R.id.Leader_tv_time);
            Leader_tv_wenhao = (TextView) itemView.findViewById(R.id.Leader_tv_wenhao);
        }
    }

    @Override
    public L_PendAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_item_pend, parent, false);
        return new L_PendAdapter.ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(L_PendAdapter.ViewHolder holder, final int position) {
        holder.Leader_tv_wenhao.setText(dataList.get(position).getSymbol());
        holder.Leader_tv_title.setText(dataList.get(position).getTitle());
        holder.Leader_tv_time.setText(dataList.get(position).getCreateTime());

        if ("1".equals(dataList.get(position).getInitiationType())){
            holder.ld_stuas.setText("会签");
            holder.ld_stuas.setBackgroundResource(R.drawable.qgl_qp_btn_bg1);
        }
        else {
            holder.ld_stuas.setText("签批");
            holder.ld_stuas.setBackgroundResource(R.drawable.qgl_qp_btn_bg2);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("offId", dataList.get(position).getId());
                if (SPUtils.get(mContext, "isLead", "").equals("0")) {
                    if (BuildConfig.IsPad) {
                        intent.setClass(mContext, PAD_gongwen_SP.class);
                    } else {
                        intent.setClass(mContext, DocumentLotusActivity.class);
                    }
                } else {
                    intent.setClass(mContext, DocumentLotusInfoActivity.class);
                }
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
