package com.hulian.oa.work.file.admin.activity.document.l_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Document;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.work.file.admin.activity.document.DocumentLotusInfoActivity;

import java.util.ArrayList;
import java.util.List;

public class L_WoFaqiAdapter extends RecyclerView.Adapter <L_WoFaqiAdapter.ViewHolder> {
    private Context mContext;
    private List<Document> dataList = new ArrayList<>();
    private String type;

    public void addAllData(List<Document> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public L_WoFaqiAdapter(Context context, String type) {
        mContext = context;
        this.type=type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_time;
        public TextView ld_stuas;
        public TextView qgl_zy_ysp_jieshi;
        private ImageView tv_status;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            ld_stuas = (TextView) itemView.findViewById(R.id.ld_stuas);
            qgl_zy_ysp_jieshi = (TextView) itemView.findViewById(R.id.qgl_zy_ysp_jieshi);
            tv_status=(ImageView) itemView.findViewById(R.id.tv_status);
        }
    }

    @Override
    public L_WoFaqiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_item_wofaqi, parent, false);
        return new L_WoFaqiAdapter.ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(L_WoFaqiAdapter.ViewHolder holder, final int position) {
        holder.tv_title.setText(dataList.get(position).getSymbol());
        holder.tv_time.setText(dataList.get(position).getCreateTime().substring(0,dataList.get(position).getCreateTime().length()-3));
        holder.qgl_zy_ysp_jieshi.setText(dataList.get(position).getTitle());
        if ("1".equals(dataList.get(position).getInitiationType())){
            holder.ld_stuas.setText("会签");
            holder.ld_stuas.setBackgroundResource(R.drawable.qgl_qp_btn_bg1);
        }
        else {
            holder.ld_stuas.setText("签批");
            holder.ld_stuas.setBackgroundResource(R.drawable.qgl_qp_btn_bg2);
        }

            holder.tv_status.setVisibility(View.VISIBLE);
            if("0".equals(dataList.get(position).getState()))
            {
                holder.tv_status.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qj_daishenpi_icon_qgl));
            }
            else if("1".equals(dataList.get(position).getState())) {
                holder.tv_status.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qj_shenpi_tongguo_icon_qgl));

            }
            else if("2".equals(dataList.get(position).getState())) {
                holder.tv_status.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qj_bohui_icon_qgl));
            }
            else
            {
                holder.tv_status.setVisibility(View.GONE);
            }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("offId",dataList.get(position).getId());
                intent.setClass(mContext, DocumentLotusInfoActivity.class);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
