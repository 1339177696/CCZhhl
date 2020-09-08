package com.hulian.oa.socket.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.me.activity.ScheduleActivity;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.push.bean.MeBean;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.work.activity.notice.NoticeParticularsActivity;

import java.util.List;

/**
 * 作者：qgl 时间： 2020/7/7 16:34
 * Describe: 通告
 */
public class NoticeTonggaoAdaoter extends BaseQuickAdapter<MeBean, BaseViewHolder> {

    public NoticeTonggaoAdaoter(List<MeBean> mData) {
        super(R.layout.item_soket_notice, mData);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeBean item) {
        helper.setText(R.id.socked_tv_time, item.getCreateTime().substring(0, item.getCreateTime().length() - 3));
        if (item.getStatus().equals("1")){
            helper.setVisible(R.id.iv_remind,false);
        }else {
            helper.setVisible(R.id.iv_remind,true);
        }
        helper.setOnClickListener(R.id.item_socket_liner, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 点击隐藏红点
                helper.setVisible(R.id.iv_remind,false);
                // 如果未读的时候发送接口改变成已读
                if (item.getStatus().equals("0")){
                    getStatus(item.getId());
                }
                if (item.getType().equals("5")){
                    // 通告
                    Intent intent=new Intent(mContext, NoticeParticularsActivity.class);
                    intent.putExtra("noticeId",item.getRelationId());
                    intent.putExtra("isCollect",item.getCollectionStatus());
                    mContext.startActivity(intent);
                }else if (item.getType().equals("1")){
                    // 邮件
                }else if (item.getType().equals("6")){
                    mContext.startActivity(new Intent(mContext, ScheduleActivity.class));
                    //日程没有详情
                }
            }
        });

        switch (item.getType()) {
            case "5":
                helper.setVisible((R.id.socked_tv_five), false);
                helper.setVisible(R.id.socked_tv4, false);
                helper.setVisible(R.id.socked_tv_four, false);
                helper.setImageResource(R.id.socked_img, R.mipmap.app_notice);
                helper.setText(R.id.socked_tv_title, "通告通知");
                helper.setText(R.id.socket_tv_title2, item.getTitle());
                helper.setText(R.id.socked_tv1, "通知内容");
                helper.setText(R.id.socked_tv2, "通知部门");
                helper.setText(R.id.socked_tv3, "发布者");
                helper.setText(R.id.socked_tv_one, item.getContent());
                helper.setText(R.id.socked_tv_two, item.getReleaseDept());
                helper.setText(R.id.socked_tv_three, item.getCreatePerson());
                break;
            case "6":
                helper.setVisible((R.id.socked_tv_five), false);
                helper.setVisible(R.id.socked_tv4, false);
                helper.setVisible(R.id.socked_tv_four, false);
                helper.setVisible(R.id.socked_tv3, false);
                helper.setVisible(R.id.socked_tv_three, false);
                helper.setVisible(R.id.socket_tv_title2, false);
                helper.setImageResource(R.id.socked_img, R.mipmap.app_notice);
                helper.setText(R.id.socked_tv_title, "日程通知");
                helper.setText(R.id.socked_tv1, "日程时间");
                helper.setText(R.id.socked_tv2, "日程内容");
                helper.setText(R.id.socked_tv_one, item.getStartDate());
                helper.setText(R.id.socked_tv_two, item.getContent());
                break;
        }
    }

    //修改已读状态
    private void getStatus(String id){
        RequestParams params = new RequestParams();
        params.put("id",id);
        params.put("userId", SPUtils.get(mContext, "userId", "").toString());
        HttpRequest.postNotice_staus(params, new ResponseCallback(){
            @Override
            public void onSuccess(Object responseObj) {
                Log.e("成功",responseObj.toString());
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Log.e("失败","失败");
            }
        });
    }
}