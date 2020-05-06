package com.hulian.oa.socket.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.Report;

import java.util.List;

/**
 * 作者：qgl 时间： 2020/4/27 10:58
 * Describe: 通知adapter
 */
public class NoticeMeetingAdaoter extends BaseQuickAdapter<Report, BaseViewHolder> {
    public NoticeMeetingAdaoter(List<Report> mData){
        super(R.layout.item_soket_notice,mData);
    }
    @Override
    protected void convert(BaseViewHolder helper, Report item) {
        helper.setVisible((R.id.socked_tv_five),false);
        switch (item.getType()){
            case "1":
                helper.setImageResource(R.id.socked_img,R.mipmap.socked_app_meeting);
                helper.setText(R.id.socked_tv_title,"会议安排");
                helper.setText(R.id.socked_tv1,"会议时间");
                helper.setText(R.id.socked_tv2,"会议地点");
                helper.setText(R.id.socked_tv2,"联系人");
                helper.setText(R.id.socked_tv2,"联系电话");
                helper.setText(R.id.socked_tv_one,"");
                helper.setText(R.id.socked_tv_two,"");
                helper.setText(R.id.socked_tv_three,"");
                helper.setText(R.id.socked_tv_four,"");
                break;
            case "2":
                helper.setImageResource(R.id.socked_img,R.mipmap.socked_app_video);
                helper.setText(R.id.socked_tv_title,"视频会议");
                helper.setText(R.id.socked_tv1,"会议标题");
                helper.setText(R.id.socked_tv2,"发起人");
                helper.setText(R.id.socked_tv2,"视频时间");
                helper.setText(R.id.socked_tv2,"参与人");
                helper.setText(R.id.socked_tv_one,"");
                helper.setText(R.id.socked_tv_two,"");
                helper.setText(R.id.socked_tv_three,"");
                helper.setText(R.id.socked_tv_four,"");
                break;
            case "3":
                helper.setImageResource(R.id.socked_img,R.mipmap.socked_app_voice);
                helper.setText(R.id.socked_tv_title,"语音会议");
                helper.setText(R.id.socked_tv1,"会议标题");
                helper.setText(R.id.socked_tv2,"发起人");
                helper.setText(R.id.socked_tv2,"视频时间");
                helper.setText(R.id.socked_tv2,"参与人");
                helper.setText(R.id.socked_tv_one,"");
                helper.setText(R.id.socked_tv_two,"");
                helper.setText(R.id.socked_tv_three,"");
                helper.setText(R.id.socked_tv_four,"");
                break;
        }
    }
}
