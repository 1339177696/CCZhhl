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
import com.hulian.oa.work.activity.task.l_details_activity.TaskCopymeDetailsActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class L_CopymeTaskAdapter extends RecyclerView.Adapter <L_CopymeTaskAdapter.ViewHolder>{
    private Context mContext;
    private List<Notice_x> dataList = new ArrayList<>();


    public void addAllData(List<Notice_x> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public L_CopymeTaskAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_time;
        public TextView tv_deadline_time;
        public TextView tv_comp_task_person;
        public TextView qgl_zy_ysp_status;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_compy_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_comp_time);
            tv_deadline_time = (TextView) itemView.findViewById(R.id.tv_comp_deadline_time);
            tv_comp_task_person = (TextView) itemView.findViewById(R.id.tv_comp_task_person);
            qgl_zy_ysp_status = (TextView) itemView.findViewById(R.id.qgl_zy_ysp_status);
        }
    }

    @Override
    public L_CopymeTaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_item_copyme_task, parent, false);

        return new L_CopymeTaskAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(L_CopymeTaskAdapter.ViewHolder holder, final int position) {
        holder.tv_title.setText(dataList.get(position).getTitle());
        if (dataList.get(position).getCompletion().equals("2")){
            holder.qgl_zy_ysp_status.setVisibility(View.VISIBLE);
        }
//        开始时间
        String b = dataList.get(position).getStartTime();
//        String c = getDataaa(b);
        holder.tv_time.setText(b);

//        截止时间
        String a = dataList.get(position).getEndTime();
        holder.tv_deadline_time.setText(a);

//        holder.tv_time.setText(dataList.get(position).getStartTime());
//        holder.tv_deadline_time.setText(dataList.get(position).getEndTime());
        holder.tv_comp_task_person.setText(dataList.get(position).getCreateBy());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.putExtra("PORID",dataList.get(position).getProid());
                intent.putExtra("ID",dataList.get(position).getId());

                intent.setClass(mContext, TaskCopymeDetailsActivity.class);
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
