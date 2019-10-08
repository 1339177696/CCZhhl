package com.hulian.oa.me.l_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hulian.oa.MainActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.Bean_x;
import com.hulian.oa.bean.Sche_Bean_x;
import com.hulian.oa.bean.ScheduleBean3;
import com.hulian.oa.me.L_SetActivity;
import com.hulian.oa.me.ScheduleActivity;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.TimeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 我的日程列表
 */

public class L_ScheduleAdapter extends RecyclerView.Adapter <L_ScheduleAdapter.ViewHolder>{

    private final LayoutInflater layoutInflater;
    private  Context context;
    private String[] titles;
    private List<ScheduleBean3> dataList = new ArrayList<>();
    private String timee;


    public void addAllData(List<ScheduleBean3> dataList, String time) {
        this.dataList=dataList;
        timee = time;
        notifyDataSetChanged();
    }

    public L_ScheduleAdapter(Context context) {
    //    titles = context.getResources().getStringArray(R.array.titles);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public L_ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     //   return new ViewHolder(layoutInflater.inflate(R.layout.l_item_schedule, null));
        return new ViewHolder(layoutInflater.inflate(R.layout.l_item_schedule,  parent,false));
    }
////
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

      /*  String aa = dataList.get(position).getScheduleDate();
        holder.tv_time.setText( com.hulian.oa.utils.TimeUtils.getDateToString2(aa));
        holder.tv_title.setText(dataList.get(position).getScheduleContent());
        holder.fl_content.setVisibility(View.GONE);*/
        holder.tv_time.setText(dataList.get(position).getTimeTitle());
        if(dataList.get(position).getScheduleContent()!=null)
        holder.tv_title.setText(dataList.get(position).getScheduleContent());
        else {
            holder.tv_title.setText("");
        }
        if(dataList.get(position).isHasContent()){
            holder.fl_content.setVisibility(View.VISIBLE);
        }else {
            holder.fl_content.setVisibility(View.GONE);
        }
        if(dataList.get(position).isNow()){
            holder.tv_now.setVisibility(View.VISIBLE);
           /* RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)   holder.tv_now_xian.getLayoutParams();

            lp.setMargins(int left, int top, int right, int bottom)*/

            holder.tv_now_xian.setVisibility(View.VISIBLE);
            holder.tv_now.setText(dataList.get(position).getTimeNow());
        }
        else {
            holder.tv_now.setVisibility(View.GONE);
            holder.tv_now_xian.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_time;
        TextView tv_title;
        TextView tv_now,tv_now_xian;
        FrameLayout fl_content;
        ViewHolder(View view) {
            super(view);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            fl_content=view.findViewById(R.id.fl_content);
            tv_now=view.findViewById(R.id.tv_now);
            tv_now_xian=view.findViewById(R.id.tv_now_xian);
        }
    }
}
