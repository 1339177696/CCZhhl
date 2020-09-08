package com.hulian.oa.socket.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.Messagebean;
import com.hulian.oa.bean.Report;
import com.hulian.oa.push.bean.MeBean;

import java.util.List;

/**
 * 作者：qgl 时间： 2020/5/20 11:04
 * Describe:
 */
public class NoticeActivityAdapter extends BaseQuickAdapter<MeBean, BaseViewHolder> {
    public NoticeActivityAdapter(List<MeBean> mData) {
        super(R.layout.notticactivity_recy_item, mData);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeBean item) {
        helper.setText(R.id.not_rect_item_time,item.getCreateTime().substring(0,item.getCreateTime().length()-8));
        if (Integer.parseInt(item.getCount())<1)
        {
            helper.setVisible(R.id.not_rect_item_num,false);
        }else {
            helper.setVisible(R.id.not_rect_item_num,true);
            helper.setText(R.id.not_rect_item_num,item.getCount());

        }
        switch (item.getType()) {
            case "1":
                // 邮件
                helper.setImageResource(R.id.not_rect_item_img,R.mipmap.app_mail);
                helper.setText(R.id.not_rect_item_title,"邮件");
                helper.setText(R.id.not_rect_item_content,item.getContent());
                break;
                /**这三个都属于工作通知**/
            case "3":
                //请假
            case "7":
                //任务
            case "8":
                // 报销
                helper.setImageResource(R.id.not_rect_item_img,R.mipmap.app_takeleave);
                helper.setText(R.id.not_rect_item_title,"工作通知");
                if (item.getType().equals("3"))
                {
                    helper.setText(R.id.not_rect_item_content,"请假推送：收到" + item.getCreatePerson() + "的请假");
                }else if (item.getType().equals("7")){
                    helper.setText(R.id.not_rect_item_content,"任务推送：收到" + item.getCreatePerson() + "的任务");
                }else if (item.getType().equals("8")){
                    helper.setText(R.id.not_rect_item_content,"报销推送：收到" + item.getCreatePerson() + "的报销");
                }
                break;
            case "4":
                //会议
                helper.setImageResource(R.id.not_rect_item_img,R.mipmap.app_meeting);
                helper.setText(R.id.not_rect_item_title,"会议通知");
                helper.setText(R.id.not_rect_item_content,"会议安排：" + item.getContent());
                break;
            case "5":
                //通告
                helper.setImageResource(R.id.not_rect_item_img,R.mipmap.app_notice);
                helper.setText(R.id.not_rect_item_title,"通告");
                helper.setText(R.id.not_rect_item_content,item.getContent());
                break;
            case "6":
                // 日程
                helper.setImageResource(R.id.not_rect_item_img,R.mipmap.app_notice);
                helper.setText(R.id.not_rect_item_title,"日程");
                helper.setText(R.id.not_rect_item_content,item.getContent());
                break;
        }
    }
}
