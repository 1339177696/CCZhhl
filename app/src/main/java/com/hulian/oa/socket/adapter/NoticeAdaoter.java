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
public class NoticeAdaoter extends BaseQuickAdapter<Report, BaseViewHolder> {
    public NoticeAdaoter(List<Report> mData){
        super(R.layout.item_soket_notice,mData);
    }
    @Override
    protected void convert(BaseViewHolder helper, Report item) {
        switch (item.getType()){
            case "1":
                helper.setImageResource(R.id.socked_img,R.mipmap.socked_app_takeleave);
                helper.setText(R.id.socked_tv_title,"请假推送");
                break;
            case "2":
                helper.setImageResource(R.id.socked_img,R.mipmap.socked_app_document);
                helper.setText(R.id.socked_tv_title,"公文推送");
                break;
            case "3":
                helper.setImageResource(R.id.socked_img,R.mipmap.socked_app_reimburse);
                helper.setText(R.id.socked_tv_title,"报销推送");
                break;
            case "4":
                helper.setImageResource(R.id.socked_img,R.mipmap.socked_app_task);
                helper.setText(R.id.socked_tv_title,"任务推送");
                break;
        }
    }
}
