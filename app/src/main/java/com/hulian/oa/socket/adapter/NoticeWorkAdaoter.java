package com.hulian.oa.socket.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.Report;

import java.util.List;

/**
 * 作者：qgl 时间： 2020/4/27 10:58
 * Describe: 通知adapter
 */
public class NoticeWorkAdaoter extends BaseQuickAdapter<Report, BaseViewHolder> {
    public NoticeWorkAdaoter(List<Report> mData){
        super(R.layout.item_soket_notice,mData);
    }
    @Override
    protected void convert(BaseViewHolder helper, Report item) {
        switch (item.getType()){
            case "1":
                helper.setImageResource(R.id.socked_img,R.mipmap.socked_app_takeleave);
                helper.setText(R.id.socked_tv_title,"请假推送");
                helper.setText(R.id.socked_tv1,"补卡原因");
                helper.setText(R.id.socked_tv2,"补卡班次");
                helper.setText(R.id.socked_tv2,"补卡时间");
                helper.setText(R.id.socked_tv2,"所在部门");
                helper.setText(R.id.socked_tv_one,"");
                helper.setText(R.id.socked_tv_two,"");
                helper.setText(R.id.socked_tv_three,"");
                helper.setText(R.id.socked_tv_four,"");
                helper.setBackgroundRes(R.id.socked_tv_five,R.drawable.item_socked_tv_bg1);
                helper.setTextColor(R.id.socked_tv_five, android.graphics.Color.parseColor("#FEA800"));
                helper.setText(R.id.socked_tv_five,"待审批");
                break;
            case "2":
                helper.setImageResource(R.id.socked_img,R.mipmap.socked_app_document);
                helper.setText(R.id.socked_tv_title,"公文推送");
                helper.setText(R.id.socked_tv1,"公文标题");
                helper.setText(R.id.socked_tv2,"发起人");
                helper.setText(R.id.socked_tv2,"发起时间");
                helper.setText(R.id.socked_tv2,"审批人");
                helper.setText(R.id.socked_tv_one,"");
                helper.setText(R.id.socked_tv_two,"");
                helper.setText(R.id.socked_tv_three,"");
                helper.setText(R.id.socked_tv_four,"");
                helper.setBackgroundRes(R.id.socked_tv_five,R.drawable.item_socked_tv_bg2);
                helper.setTextColor(R.id.socked_tv_five, android.graphics.Color.parseColor("#D51616"));
                helper.setText(R.id.socked_tv_five,"已驳回");
                break;
            case "3":
                helper.setImageResource(R.id.socked_img,R.mipmap.socked_app_reimburse);
                helper.setText(R.id.socked_tv_title,"报销推送");
                helper.setText(R.id.socked_tv1,"所在部门");
                helper.setText(R.id.socked_tv2,"发票张数");
                helper.setText(R.id.socked_tv2,"报销(金额)");
                helper.setText(R.id.socked_tv2,"报销事由");
                helper.setText(R.id.socked_tv_one,"");
                helper.setText(R.id.socked_tv_two,"");
                helper.setText(R.id.socked_tv_three,"");
                helper.setText(R.id.socked_tv_four,"");
                helper.setBackgroundRes(R.id.socked_tv_five,R.drawable.item_socked_tv_bg3);
                helper.setTextColor(R.id.socked_tv_five,android.graphics.Color.parseColor("#10C7A7"));
                helper.setText(R.id.socked_tv_five,"已审批");
                break;
            case "4":
                helper.setImageResource(R.id.socked_img,R.mipmap.socked_app_task);
                helper.setText(R.id.socked_tv_title,"任务推送");
                helper.setText(R.id.socked_tv1,"截止时间");
                helper.setText(R.id.socked_tv2,"召集人");
                helper.setText(R.id.socked_tv2,"发起人");
                helper.setText(R.id.socked_tv2,"抄送人");
                helper.setText(R.id.socked_tv_one,"");
                helper.setText(R.id.socked_tv_two,"");
                helper.setText(R.id.socked_tv_three,"");
                helper.setText(R.id.socked_tv_four,"");
                helper.setBackgroundRes(R.id.socked_tv_five,R.drawable.item_socked_tv_bg1);
                helper.setTextColor(R.id.socked_tv_five, android.graphics.Color.parseColor("#10C7A7"));
                helper.setText(R.id.socked_tv_five,"待审批");
                break;
        }

    }
}
