package com.hulian.oa.socket.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.push.bean.MeBean;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.work.activity.expense.ExpenseExamineActivityQ;
import com.hulian.oa.work.activity.expense.ExpenseExamineActivityS;
import com.hulian.oa.work.activity.leave.LeaveApplyResultActivity;
import com.hulian.oa.work.activity.leave.LeaveExamineActivity;
import com.hulian.oa.work.activity.task.l_details_activity.TaskCompletedDetailsActivity;
import com.hulian.oa.work.activity.task.l_details_activity.TaskLaunchDetailsActivity;
import com.hulian.oa.work.activity.task.l_details_activity.TaskUndoneDetailsActivity;

import java.util.List;

/**
 * 作者：qgl 时间： 2020/4/27 10:58
 * Describe: 通知adapter
 */
public class NoticeWorkAdaoter extends BaseQuickAdapter<MeBean, BaseViewHolder> {
    public NoticeWorkAdaoter(List<MeBean> mData) {
        super(R.layout.item_soket_notice, mData);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeBean item) {
        helper.setText(R.id.socked_tv_time, item.getCreateTime().substring(0, item.getCreateTime().length() - 3));
        if (item.getStatus().equals("1")) {
            helper.setVisible(R.id.iv_remind, false);
        } else {
            helper.setVisible(R.id.iv_remind, true);
        }

        helper.setOnClickListener(R.id.item_socket_liner, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击隐藏红点
                helper.setVisible(R.id.iv_remind, false);
                // 如果未读的时候发送接口改变成已读
                if (item.getStatus().equals("0")) {
                    getStatus(item.getId());
                }
                if (item.getType().equals("3")) {
//                    if (item.getApprovalStatus().equals("0")){
                    if (item.getFlagState().equals("1")) {
                        Intent intent = new Intent();
                        intent.putExtra("id", item.getRelationId());
                        intent.setClass(mContext, LeaveApplyResultActivity.class);
                        mContext.startActivity(intent);
                    } else {
                        //审批
                        Intent intent = new Intent();
                        intent.putExtra("id", item.getRelationId());
                        intent.putExtra("createByid", SPUtils.get(mContext, "userId", "").toString());
                        intent.setClass(mContext, LeaveExamineActivity.class);
                        mContext.startActivity(intent);
                    }
                } else if (item.getType().equals("7")) {
                    if (item.getFlagState().equals("1")) {
                        Intent intent = new Intent();
                        intent.putExtra("PORID", "porid");
                        intent.putExtra("ID", item.getRelationId());
                        intent.setClass(mContext, TaskCompletedDetailsActivity.class);
                        mContext.startActivity(intent);
                    } else {
                        if (item.getCreateBy().equals(SPUtils.get(mContext, "userId", "").toString())) {
                            Intent intent = new Intent();
                            intent.putExtra("PORID","porid");
                            intent.putExtra("ID",item.getRelationId());
                            intent.setClass(mContext, TaskLaunchDetailsActivity.class);
                            mContext.startActivity(intent);
                        }else {
                            Intent intent = new Intent();
                            intent.putExtra("PORID","porid");
                            intent.putExtra("ID",item.getRelationId());
                            intent.setClass(mContext, TaskUndoneDetailsActivity.class);
                            mContext.startActivity(intent);
                        }
                    }
//
//                    Intent intent = new Intent();
//                    intent.putExtra("PORID", "1");
//                    intent.putExtra("ID", item.getRelationId());
//                    intent.setClass(mContext, TaskLaunchDetailsActivity.class);
//                    mContext.startActivity(intent);

                } else if (item.getType().equals("8")) {
                    // 如果是抄送的话-->只能查看,我发起的-->只能查看，已审批-->查看，待审批——>审批
                    if (item.getFlagState().equals("1")) {
                        Intent intent = new Intent();
                        intent.putExtra("id", item.getRelationId());
                        intent.setClass(mContext, ExpenseExamineActivityS.class);
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("id", item.getRelationId());
                        intent.putExtra("createByid", SPUtils.get(mContext, "userId", "").toString());
                        intent.setClass(mContext, ExpenseExamineActivityQ.class);
                        mContext.startActivity(intent);
                    }
                }

            }
        });
        switch (item.getType()) {
            case "3":
                helper.setVisible(R.id.socked_tv4, true);
                helper.setVisible(R.id.socked_tv_four, true);
                helper.setImageResource(R.id.socked_img, R.mipmap.socked_app_takeleave);
                helper.setText(R.id.socked_tv_title, "请假推送");
                helper.setText(R.id.socket_tv_title2, item.getCreatePerson() + "提交的请假申请");
                helper.setText(R.id.socked_tv1, "请假类型");
                helper.setText(R.id.socked_tv2, "请假原因");
                helper.setText(R.id.socked_tv3, "请假时间");
                helper.setText(R.id.socked_tv4, "所在部门");
                helper.setText(R.id.socked_tv_one, item.getCause());
                helper.setText(R.id.socked_tv_two, item.getContent());
                helper.setText(R.id.socked_tv_three, item.getDuration() + "天");
                helper.setText(R.id.socked_tv_four, item.getDeptName());
                helper.setBackgroundRes(R.id.socked_tv_five, R.drawable.item_socked_tv_bg1);
                helper.setTextColor(R.id.socked_tv_five, android.graphics.Color.parseColor("#FEA800"));
                helper.setText(R.id.socked_tv_five, item.getCompletion());
                break;
            case "7":
                helper.setVisible(R.id.socked_tv4, true);
                helper.setVisible(R.id.socked_tv_four, true);
                helper.setImageResource(R.id.socked_img, R.mipmap.socked_app_task);
                helper.setText(R.id.socked_tv_title, "任务推送");
                helper.setText(R.id.socket_tv_title2, item.getContent());
                helper.setText(R.id.socked_tv1, "截止时间");
                helper.setText(R.id.socked_tv2, "召集人");
                helper.setText(R.id.socked_tv3, "执行人");
                helper.setText(R.id.socked_tv4, "抄送人");
                helper.setText(R.id.socked_tv_one, item.getEndDate());
                helper.setText(R.id.socked_tv_two, item.getCreatePerson());
                helper.setText(R.id.socked_tv_three, item.getExecutor());
                helper.setText(R.id.socked_tv_four, item.getCopier());
                helper.setBackgroundRes(R.id.socked_tv_five, R.drawable.item_socked_tv_bg1);
                helper.setTextColor(R.id.socked_tv_five, android.graphics.Color.parseColor("#10C7A7"));
                helper.setText(R.id.socked_tv_five, item.getCompletion());
                break;
            case "8":
                helper.setVisible(R.id.socked_tv4, false);
                helper.setVisible(R.id.socked_tv_four, false);
                helper.setImageResource(R.id.socked_img, R.mipmap.socked_app_reimburse);
                helper.setText(R.id.socked_tv_title, "报销推送");
                helper.setText(R.id.socket_tv_title2, item.getCreatePerson() + "提交的报销申请");
                helper.setText(R.id.socked_tv1, "所在部门");
                helper.setText(R.id.socked_tv2, "发票张数");
                helper.setText(R.id.socked_tv3, "报销(金额)");
//                helper.setText(R.id.socked_tv4,"报销事由");
                helper.setText(R.id.socked_tv_one, item.getDeptName());
                helper.setText(R.id.socked_tv_two, item.getReceiptSum() + "张");
                helper.setText(R.id.socked_tv_three, item.getMoney() + "元");
//                helper.setText(R.id.socked_tv_four,"");
                helper.setBackgroundRes(R.id.socked_tv_five, R.drawable.item_socked_tv_bg3);
                helper.setTextColor(R.id.socked_tv_five, android.graphics.Color.parseColor("#10C7A7"));
                helper.setText(R.id.socked_tv_five, item.getCompletion());
                break;
        }

    }

    //修改已读状态
    private void getStatus(String id) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("userId", SPUtils.get(mContext, "userId", "").toString());
        HttpRequest.postNotice_staus(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.e("成功", responseObj.toString());
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }


}
