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
public class MeetingOngoingAdapter extends BaseQuickAdapter<VideoMeeting, BaseViewHolder> {

    public MeetingOngoingAdapter(List<VideoMeeting> mData){
        super(R.layout.item_meeting_ongoing,mData);
    }
    @Override
    protected void convert(BaseViewHolder holder, VideoMeeting videoMeeting) {

        holder.setText(R.id.day,videoMeeting.getDay())
                .setText(R.id.month,videoMeeting.getMonth())
                .setText(R.id.title,videoMeeting.getTitle())
                .setText(R.id.meeting_num,videoMeeting.getNumber())
                .setText(R.id.meeting_time,videoMeeting.getStartTime());



    }
}
