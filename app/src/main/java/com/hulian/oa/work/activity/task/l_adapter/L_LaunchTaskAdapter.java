package com.hulian.oa.work.activity.task.l_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Notice_x;
import com.hulian.oa.work.activity.task.l_details_activity.TaskLaunchDetailsActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class L_LaunchTaskAdapter extends RecyclerView.Adapter <L_LaunchTaskAdapter.ViewHolder>{
    private Context mContext;
    private List<Notice_x> dataList = new ArrayList<>();

    public void addAllData(List<Notice_x> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public L_LaunchTaskAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_time;
        public TextView tv_deadline_time;
        public TextView tv_launch_task_person;
        private TextView qgl_zy_ysp_status;
        private TextView tv_wancheng_number;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_laun_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_laun_time);
            tv_deadline_time = (TextView) itemView.findViewById(R.id.tv_laun_deadline_time);
            tv_launch_task_person = (TextView) itemView.findViewById(R.id.tv_launch_task_person);
            qgl_zy_ysp_status = (TextView) itemView.findViewById(R.id.qgl_zy_ysp_status);
            tv_wancheng_number = (TextView) itemView.findViewById(R.id.tv_wancheng_number);
        }
    }

    @Override
    public L_LaunchTaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_item_launch_task, parent, false);
        return new L_LaunchTaskAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(L_LaunchTaskAdapter.ViewHolder holder, final int position) {
        holder.tv_title.setText(dataList.get(position).getTitle());
        //        开始时间
        String b = dataList.get(position).getStartTime();
        holder.tv_time.setText(b);
        //        截止时间
        String a = dataList.get(position).getEndTime();
        holder.tv_deadline_time.setText(a);
        holder.tv_launch_task_person.setText(dataList.get(position).getCreateBy());
        if (dataList.get(position).getCompletion().equals("0"))
        {
            holder.qgl_zy_ysp_status.setBackgroundResource(R.drawable.ggl_ysp_btn_bg1);
            holder.qgl_zy_ysp_status.setText("未完成");

        }
        else if (dataList.get(position).getCompletion().equals("1"))
        {
            holder.qgl_zy_ysp_status.setBackgroundResource(R.drawable.ggl_ysp_btn_bg2);
            holder.qgl_zy_ysp_status.setText("已完成");
        }else if (dataList.get(position).getCompletion().equals("2")){
            holder.qgl_zy_ysp_status.setBackgroundResource(R.drawable.ggl_ysp_btn_bg3);
            holder.qgl_zy_ysp_status.setText("已关闭");

        }
        holder.tv_wancheng_number.setText(dataList.get(position).getSum()+"人已完成");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.putExtra("PORID",dataList.get(position).getProid());
                intent.putExtra("ID",dataList.get(position).getId());
                intent.setClass(mContext, TaskLaunchDetailsActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
