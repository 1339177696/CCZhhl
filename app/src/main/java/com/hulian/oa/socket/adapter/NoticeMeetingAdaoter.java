package com.hulian.oa.socket.adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.Report;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.push.bean.MeBean;
import com.hulian.oa.utils.SPUtils;

import java.util.List;

/**
 * 作者：qgl 时间： 2020/4/27 10:58
 * Describe: 通知adapter
 */
public class NoticeMeetingAdaoter extends BaseQuickAdapter<MeBean, BaseViewHolder> {
    public NoticeMeetingAdaoter(List<MeBean> mData){
        super(R.layout.item_soket_notice,mData);
    }
    @Override
    protected void convert(BaseViewHolder helper, MeBean item) {
        helper.setVisible((R.id.socked_tv_five),false);
        helper.setText(R.id.socked_tv_time,item.getCreateTime().substring(0,item.getCreateTime().length()-3));
        if (item.getStatus().equals("1")){
            helper.setVisible(R.id.iv_remind,false);
        }else {
            helper.setVisible(R.id.iv_remind,true);
        }

        // 点击隐藏红点
        helper.setVisible(R.id.iv_remind,false);
        // 如果未读的时候发送接口改变成已读
        if (item.getStatus().equals("0")){
            getStatus(item.getId());
        }
        switch (item.getType()){
            case "4":
                helper.setImageResource(R.id.socked_img,R.mipmap.socked_app_meeting);
                helper.setText(R.id.socked_tv_title,"会议安排");
                helper.setText(R.id.socket_tv_title2,item.getContent());
                helper.setText(R.id.socked_tv1,"会议时间");
                helper.setText(R.id.socked_tv2,"会议地点");
                helper.setText(R.id.socked_tv3,"联系人");
                helper.setText(R.id.socked_tv4,"联系电话");
                helper.setText(R.id.socked_tv_one,item.getStartDate().substring(0,item.getStartDate().length()-3));
                helper.setText(R.id.socked_tv_two,item.getMeetingLocation());
                helper.setText(R.id.socked_tv_three,item.getCreatePerson());
                helper.setText(R.id.socked_tv_four,item.getPhone());
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

            }
        });
    }
}
