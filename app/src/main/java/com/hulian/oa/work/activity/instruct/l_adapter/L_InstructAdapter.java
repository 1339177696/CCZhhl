package com.hulian.oa.work.activity.instruct.l_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Instruct;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.work.activity.instruct.InstructBackActivity;
import com.hulian.oa.work.activity.instruct.InstructDetailsActivity;
import com.hulian.oa.work.activity.instruct.InstructReceiverActivity;

import java.util.ArrayList;
import java.util.List;

public class L_InstructAdapter extends RecyclerView.Adapter <L_InstructAdapter.ViewHolder> {
    private Context mContext;
    private List<Instruct> dataList = new ArrayList<>();

    public void addAllData(List<Instruct> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public L_InstructAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name,tv_posert,tv_receiver;
        public TextView tv_time,tv_time2;
        public ImageView tv_operate;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_posert= (TextView) itemView.findViewById(R.id.tv_posert);
            tv_receiver= (TextView) itemView.findViewById(R.id.tv_receiver);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_operate = (ImageView) itemView.findViewById(R.id.tv_operate);
            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
            tv_time2=(TextView) itemView.findViewById(R.id.tv_time2);
        }
    }

    @Override
    public L_InstructAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_item_instructl, parent, false);
        return new L_InstructAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(L_InstructAdapter.ViewHolder holder, final int position) {

        try {
            if(dataList.get(position).getContent()==null||dataList.get(position).getContent().equals("")){
            }
            else{
            }
        }
       catch (Exception e){

       }
        holder.tv_posert.setText(dataList.get(position).getCreateBy());
        if (dataList.get(position).getReceiver()!=null) {
            holder.tv_receiver.setVisibility(View.VISIBLE);
            holder.tv_receiver.setText(dataList.get(position).getReceiver().toString());
        }
else {
            holder.tv_receiver.setVisibility(View.INVISIBLE);
        }
        if(dataList.get(position).getCreateTime()!=null) {
            holder.tv_time.setText(TimeUtils.getDateToString3(dataList.get(position).getCreateTime()));
            holder.tv_time2.setText(TimeUtils.getDateToString4(dataList.get(position).getCreateTime()));
            String time = dataList.get(position).getCreateTime();
            holder.tv_name.setText(time.substring(time.length()-8,time.length()-3));
        }
        String operate = "";
        Intent intent;
        if(SPUtils.get(mContext,"isLead","").equals("0")){
            operate = "详情";
            intent = new Intent(mContext, InstructDetailsActivity.class);
            if (TextUtils.equals(dataList.get(position).getReceive(), "1")) {
                if (TextUtils.equals(dataList.get(position).getState(), "0")) {
                    operate = "反馈";
                    holder.tv_operate.setBackgroundResource(R.mipmap.ins_receiver);
                } else {
                    operate = "详情";
                    holder.tv_operate.setBackgroundResource(R.mipmap.ins_receive_back);
                }

            } else {
                holder.tv_operate.setBackgroundResource(R.mipmap.ins_unreceiver);
            }
        }
        else {
            if (TextUtils.equals(dataList.get(position).getReceive(), "1")) {
                if (TextUtils.equals(dataList.get(position).getState(), "0")) {
                    operate = "反馈";
                    holder.tv_operate.setBackgroundResource(R.mipmap.ins_receiver);
                    intent = new Intent(mContext, InstructBackActivity.class);
                } else {
                    operate = "详情";
                    holder.tv_operate.setBackgroundResource(R.mipmap.ins_receive_back);
                    intent = new Intent(mContext, InstructDetailsActivity.class);
                }

            } else {
                holder.tv_operate.setBackgroundResource(R.mipmap.ins_unreceiver);
                intent = new Intent(mContext, InstructReceiverActivity.class);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  //测试数据end
                intent.putExtra("id",dataList.get(position).getId());
                intent.putExtra("content",dataList.get(position).getContent());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

