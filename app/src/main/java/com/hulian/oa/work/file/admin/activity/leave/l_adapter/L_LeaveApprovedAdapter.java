package com.hulian.oa.work.file.admin.activity.leave.l_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Leave;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.work.file.admin.activity.leave.LeaveApplyResultActivity;
import com.hulian.oa.work.file.admin.activity.leave.LeaveExamineActivity;

import java.util.ArrayList;
import java.util.List;

public class L_LeaveApprovedAdapter extends RecyclerView.Adapter <L_LeaveApprovedAdapter.ViewHolder>{
    private Context mContext;
    private List<Leave> dataList = new ArrayList<>();


    public void addAllData(List<Leave> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public L_LeaveApprovedAdapter(Context context) {
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
        public ImageView tv_approved;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_leave_reason = (TextView) itemView.findViewById(R.id.tv_leave_reason);
            tv_duration = (TextView) itemView.findViewById(R.id.tv_duration);
            tv_approved = (ImageView) itemView.findViewById(R.id.tv_approved);
        }
    }

    @Override
    public L_LeaveApprovedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_item_approved_leave, parent, false);

        return new L_LeaveApprovedAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(L_LeaveApprovedAdapter.ViewHolder holder, final int position) {
        holder.tv_title.setText(dataList.get(position).getRemark());
        holder.tv_time.setText(TimeUtils.getDateToString(dataList.get(position).getCreateTime()));
        holder.tv_leave_reason.setText(dataList.get(position).getCause());
        holder.tv_duration.setText(dataList.get(position).getDuration()+"天");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("id",dataList.get(position).getId());
                intent.setClass(mContext, LeaveApplyResultActivity.class);
                mContext.startActivity(intent);
            }
        });
        if(dataList.get(position).getState().equals("0")){
//            holder.tv_approved.setBackgroundColor(mContext.getResources().getColor(R.color.tab_color_true));
//            holder.tv_approved.setBackground(mContext.getResources().getDrawable(R.drawable.test_yuan_qgl));
                holder.tv_approved.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qj_daishenpi_icon_qgl));


//            holder.tv_approved.setText("待审批");
        }
        else if (dataList.get(position).getState().equals("1")){
             holder.tv_approved.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qj_shenpi_tongguo_icon_qgl));

//            holder.tv_approved.setText("已审批");
////            holder.tv_approved.setBackgroundColor(mContext.getResources().getColor(R.color.color_a_green));
//            holder.tv_approved.setBackground(mContext.getResources().getDrawable(R.drawable.test_yuan_qgl2));

        }
        else {
                holder.tv_approved.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qj_bohui_icon_qgl));

//            holder.tv_approved.setText("驳回");
//            holder.tv_approved.setBackground(mContext.getResources().getDrawable(R.drawable.test_yuan_qgl3));

//            holder.tv_approved.setBackgroundColor(mContext.getResources().getColor(R.color.colorText));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
