package com.hulian.oa.work.activity.leave.l_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Leave;
import com.hulian.oa.work.activity.leave.LeaveExamineActivity;

import java.util.ArrayList;
import java.util.List;

public class L_LeaveApplyCopymeAdapter extends RecyclerView.Adapter <L_LeaveApplyCopymeAdapter.ViewHolder>{
    private Context mContext;
    private List<Leave> dataList = new ArrayList<>();


    public void addAllData(List<Leave> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public L_LeaveApplyCopymeAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //请假标题
        public TextView tv_title;
        //申请时间
        public TextView tv_time;
        //请假事由
        public TextView tv_leave_reason;
        //请假时长
        public TextView tv_duration;
        //已审批
        public TextView tv_pend;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_leave_reason = (TextView) itemView.findViewById(R.id.tv_leave_reason);
            tv_duration = (TextView) itemView.findViewById(R.id.tv_duration);
            tv_pend = (TextView) itemView.findViewById(R.id.tv_pend);
        }
    }

    @Override
    public L_LeaveApplyCopymeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_item_apply_copyme_leave, parent, false);

        return new L_LeaveApplyCopymeAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(L_LeaveApplyCopymeAdapter.ViewHolder holder, final int position) {
        holder.tv_title.setText(dataList.get(position).getCreateTime());
        holder.tv_time.setText(dataList.get(position).getCreateTime());
        holder.tv_pend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //测试查看界面
                mContext.startActivity(new Intent(mContext, LeaveExamineActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
