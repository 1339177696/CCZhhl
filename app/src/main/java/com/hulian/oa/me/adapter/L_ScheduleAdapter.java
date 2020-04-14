package com.hulian.oa.me.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.ScheduleBean3;
import com.hulian.oa.me.activity.ScheduleActivity_Detelis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 我的日程列表
 */

public class L_ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private Context context;
    private String[] titles;
    private List<ScheduleBean3> dataList = new ArrayList<>();
    private List<ScheduleBean3> dataList2 = new ArrayList<>();
    private String timee;
    public static final int ITEMONE = 1;
    public static final int ITEMTWO = 2;

    public void addAllData(List<ScheduleBean3> dataList,List<ScheduleBean3> dataList2,String time) {
        this.dataList2=dataList2;
        this.dataList2.addAll(dataList);
        this.dataList = this.dataList2;
        timee = time;
        notifyDataSetChanged();
    }

    public L_ScheduleAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = null;
        RecyclerView.ViewHolder viewHolder = null;

        //根据i返回不同布局
        switch (viewType) {
            case ITEMONE:
                inflate = LayoutInflater.from(context).inflate(R.layout.l_item_schedule_qgl, parent, false);
                viewHolder = new ViewHolder_Top(inflate);
                break;
            case ITEMTWO:
                inflate = LayoutInflater.from(context).inflate(R.layout.l_item_schedule, parent, false);
                viewHolder = new ViewHolder_List(inflate);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder_Top) {
            //设置数据 事件
            ViewHolder_Top viewHolder_top = ((ViewHolder_Top) holder);

            if (position == 0){
                viewHolder_top.tv_my.setVisibility(View.VISIBLE);
            }else {
                viewHolder_top.tv_my.setVisibility(View.INVISIBLE);
            }
            if (dataList.get(position).getScheduleContent() != null) {

                viewHolder_top.tv_bw_content.setText(dataList.get(position).getScheduleContent());
            } else {
                viewHolder_top.tv_bw_content.setText("");
            }
            viewHolder_top.liner_bw.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    Log.e("点击了备忘录","备忘录");
                    Intent intent = new Intent(context, ScheduleActivity_Detelis.class);
                    intent.putExtra("type","0");
                    intent.putExtra("id",dataList.get(position).getId());
                    intent.putExtra("title",dataList.get(position).getScheduleContent());
                    intent.putExtra("qu",dataList.get(position).getQufen());
                    intent.putExtra("start",dataList.get(position).getScheduleTimeBegin());
                    intent.putExtra("end",dataList.get(position).getScheduleTimeEnd());
                    intent.putExtra("warnTime",dataList.get(position).getWarnTime());
                    context.startActivity(intent);
                }
            });

        } else if (holder instanceof ViewHolder_List) {
            ViewHolder_List viewHolderList = ((ViewHolder_List) holder);
            viewHolderList.tv_time.setText(dataList.get(position).getTimeTitle());
            if (dataList.get(position).getScheduleContent() != null)
                viewHolderList.tv_title.setText(dataList.get(position).getScheduleContent());
            else {
                viewHolderList.tv_title.setText("");
            }
            if (dataList.get(position).isHasContent()) {
                viewHolderList.fl_content.setVisibility(View.VISIBLE);
            } else {
                viewHolderList.fl_content.setVisibility(View.GONE);
            }
            if (dataList.get(position).isNow()) {
                viewHolderList.tv_now.setVisibility(View.VISIBLE);
                viewHolderList.tv_now_xian.setVisibility(View.VISIBLE);
                viewHolderList.tv_now_dian.setVisibility(View.VISIBLE);
                viewHolderList.tv_now.setText(dataList.get(position).getTimeNow());
            } else {
                viewHolderList.tv_now.setVisibility(View.GONE);
                viewHolderList.tv_now_xian.setVisibility(View.GONE);
                viewHolderList.tv_now_dian .setVisibility(View.GONE);
            }
            viewHolderList.liner_rc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("点击了日程","日程");
                    Intent intent = new Intent(context, ScheduleActivity_Detelis.class);
                    intent.putExtra("type","0");
                    intent.putExtra("id",dataList.get(position).getId());
                    intent.putExtra("title",dataList.get(position).getScheduleContent());
                    intent.putExtra("qu",dataList.get(position).getIsToday());
                    intent.putExtra("start",dataList.get(position).getScheduleTimeBegin());
                    intent.putExtra("end",dataList.get(position).getScheduleTimeEnd());
                    intent.putExtra("warnTime",dataList.get(position).getWarnTime());
                    context.startActivity(intent);
                }
            });
        }

    }


    public static class ViewHolder_Top extends RecyclerView.ViewHolder {
        TextView tv_bw_content;
        TextView tv_my;
        LinearLayout liner_bw;

        ViewHolder_Top(View view) {
            super(view);
            tv_my = (TextView) view.findViewById(R.id.tv_my);
            tv_bw_content = (TextView) view.findViewById(R.id.tv_bw_content);
            liner_bw = (LinearLayout) view.findViewById(R.id.liner_bw);
        }
    }

    public static class ViewHolder_List extends RecyclerView.ViewHolder {
        TextView tv_time;
        TextView tv_title;
        TextView tv_now, tv_now_xian,tv_now_dian;
        FrameLayout fl_content;
        LinearLayout liner_rc;

        ViewHolder_List(View view) {
            super(view);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            fl_content = view.findViewById(R.id.fl_content);
            tv_now = view.findViewById(R.id.tv_now);
            tv_now_xian = view.findViewById(R.id.tv_now_xian);
            tv_now_dian = view.findViewById(R.id.tv_now_dian);
            liner_rc = view.findViewById(R.id.liner_rc);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (("Y").equals(dataList.get(position).getQufen())){
            return ITEMONE;
        }else {
            return ITEMTWO;
        }
    }
}
