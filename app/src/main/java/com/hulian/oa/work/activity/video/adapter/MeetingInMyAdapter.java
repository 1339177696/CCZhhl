package com.hulian.oa.work.activity.video.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.VideoMeeting;

import java.util.List;

/**
 * Created by 陈泽宇 on 2020/4/21
 * Describe:
 */
public class MeetingInMyAdapter extends BaseQuickAdapter<VideoMeeting, BaseViewHolder> {

    public MeetingInMyAdapter(List<VideoMeeting> mData) {
        super(R.layout.item_meeting_in_my, mData);
    }

    @Override
    protected void convert(BaseViewHolder holder, VideoMeeting videoMeeting) {

        holder.setText(R.id.day, videoMeeting.getDay());
        holder.setText(R.id.month, videoMeeting.getMonth()+"月");
        holder.setText(R.id.title, videoMeeting.getTitle()+"发起会议");
        holder.setText(R.id.meeting_num, videoMeeting.getNumber());
        String endTime = "";
        if (!videoMeeting.getStopTime().equals("")){
            endTime = "-" + videoMeeting.getStopTime();
        }else {
            endTime = "";
        }
        holder.setText(R.id.meeting_time, videoMeeting.getStartTime() + endTime);

        switch (videoMeeting.getState()) {
            case "0":
                holder.getView(R.id.meeting_state_1).setVisibility(View.VISIBLE);
                holder.getView(R.id.meeting_state_2).setVisibility(View.GONE);
                holder.getView(R.id.ll).setVisibility(View.VISIBLE);
                break;

            case "1":
                holder.getView(R.id.meeting_state_2).setVisibility(View.VISIBLE);
                holder.getView(R.id.meeting_state_1).setVisibility(View.GONE);
                holder.getView(R.id.ll).setVisibility(View.GONE);
                break;
        }

    }
}
