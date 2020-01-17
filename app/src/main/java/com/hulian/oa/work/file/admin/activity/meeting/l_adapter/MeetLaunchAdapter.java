package com.hulian.oa.work.file.admin.activity.meeting.l_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Meeting;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.work.file.admin.activity.meeting.MeetingSigninActivity;

import java.util.ArrayList;
import java.util.List;

public class MeetLaunchAdapter extends RecyclerView.Adapter <MeetLaunchAdapter.ViewHolder>{
    private Context mContext;
    private List<Meeting> dataList = new ArrayList<>();


    public void addAllData(List<Meeting> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public MeetLaunchAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_time,tv_duration;
        public TextView tv_address;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_duration = (TextView) itemView.findViewById(R.id.tv_duration);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_time=(TextView) itemView.findViewById(R.id.tv_time);
        }
    }

    @Override
    public MeetLaunchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_item_meet_launch, parent, false);

        return new MeetLaunchAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MeetLaunchAdapter.ViewHolder holder, final int position) {
        holder.tv_title.setText(dataList.get(position).getMeetingTheme());
//        holder.tv_duration.setText(TimeUtils.getDateToString((dataList.get(position).getMeetingTimeBegin())));
        holder.tv_duration.setText((dataList.get(position).getMeetingTimeBegin()));
        holder.tv_address.setText(dataList.get(position).getMeetingRoomLocation());
        holder.tv_time.setText(dataList.get(position).getMeetingTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MeetingSigninActivity.class);
                intent.putExtra("id",dataList.get(position).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
