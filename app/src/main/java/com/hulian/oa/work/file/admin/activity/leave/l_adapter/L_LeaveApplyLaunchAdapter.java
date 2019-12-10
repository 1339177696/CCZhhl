package com.hulian.oa.work.file.admin.activity.leave.l_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Leave;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.work.file.admin.activity.document.DocumentLotusActivity;
import com.hulian.oa.work.file.admin.activity.document.DocumentLotusInfoActivity;
import com.hulian.oa.work.file.admin.activity.leave.LeaveApplyResultActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class L_LeaveApplyLaunchAdapter extends RecyclerView.Adapter <L_LeaveApplyLaunchAdapter.ViewHolder>{
    private Context mContext;
    private List<Leave> dataList = new ArrayList<>();


    public void addAllData(List<Leave> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public L_LeaveApplyLaunchAdapter(Context context) {
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
        public ImageView tv_pend;

        public TextView l_item_time;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_leave_reason = (TextView) itemView.findViewById(R.id.tv_leave_reason);
            tv_duration = (TextView) itemView.findViewById(R.id.tv_duration);
            tv_pend = (ImageView) itemView.findViewById(R.id.tv_pend);
            l_item_time = (TextView) itemView.findViewById(R.id.l_item_time);
        }
    }

    @Override
    public L_LeaveApplyLaunchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_item_apply_launch_leave, parent, false);

        return new L_LeaveApplyLaunchAdapter.ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(L_LeaveApplyLaunchAdapter.ViewHolder holder, final int position) {
        holder.tv_title.setText(dataList.get(position).getRemark());

        Log.e("时间",dataList.get(position).getCreateTime());
     //   String a = getDataaa(dataList.get(position).getCreateTime());
//        holder.tv_time.setText(dataList.get(position).getCreateTime().split(" ")[0]);
        holder.tv_time.setText(TimeUtils.getDateToString(dataList.get(position).getCreateTime()));


        holder.tv_leave_reason.setText(dataList.get(position).getCause());
        holder.tv_duration.setText(dataList.get(position).getDuration()+"天");
        holder.l_item_time.setText("请假时间"+dataList.get(position).getStartTime()+"--"+dataList.get(position).getEndTime());

        if(dataList.get(position).getState().equals("0")){
            holder.tv_pend.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qj_daishenpi_icon_qgl));
        }
        else if (dataList.get(position).getState().equals("1")){
            holder.tv_pend.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qj_shenpi_tongguo_icon_qgl));
        }
        else if (dataList.get(position).getState().equals("2")){
            holder.tv_pend.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qj_bohui_icon_qgl));
        }
        else if (dataList.get(position).getState().equals("3")){

        }else if (dataList.get(position).getState().equals("4")){
            holder.tv_pend.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qj_shenpizhong_icon_qgl));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("id",dataList.get(position).getId());
                intent.setClass(mContext, LeaveApplyResultActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    private String getDataaa(String fromDate) {
        String strTime = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String toDate = df.format(curDate);
        try {
            //前的时间
            Date fd = df.parse(fromDate);
            //后的时间
            Date td = df.parse(toDate);
            //两时间差,精确到毫秒
            long diff = td.getTime() - fd.getTime();
            long day = diff / 86400000;                         //以天数为单位取整
            long hour = diff % 86400000 / 3600000;               //以小时为单位取整
            long min = diff % 86400000 % 3600000 / 60000;       //以分钟为单位取整
            long seconds = diff % 86400000 % 3600000 % 60000 / 1000;   //以秒为单位取整

            if (day<=0)
            {
                strTime = fromDate.substring(10,fromDate.length()-3);
            }
            else
            {
                System.out.println("两时间差---> " + day + "天" + hour + "小时" + min + "分" + seconds + "秒");
                strTime = fromDate.substring(0,fromDate.length()-8);

            }
            return strTime;
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
