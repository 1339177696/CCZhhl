package com.hulian.oa.work.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.People;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.views.AlertDialog;
import com.hulian.oa.views.MyDialog;
import com.hulian.oa.views.MyGridView;
import com.hulian.oa.work.activity.meeting.l_adapter.MeetGridViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 陈泽宇 on 2020/3/13.
 * Describe: 汇报筛选
 */
public class ScreenReportActivity extends BaseActivity {

    List<People> mList = new ArrayList<People>();
    private String participantId;
    MeetGridViewAdapter adapter;
    AlertDialog myDialog;

    @BindView(R.id.gv_test)
    MyGridView gvTest;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.stop_time)
    TextView stopTime;
    @BindView(R.id.tv_uname)
    TextView tvUname;
    @BindView(R.id.hb_img)
    ImageView hbImg;

    private String startTimeStr = "";
    private String stopTimeStr = "";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.activity_screen_report);
        ButterKnife.bind(this);
        String a = SPUtils.get(this, "roleKey", "").toString();
        if (SPUtils.get(this, "roleKey", "").toString().contains("eachLead") || SPUtils.get(this, "roleKey", "").toString().contains("boss")) {//
            //领导级别
            tvUname.setVisibility(View.GONE);
            hbImg.setVisibility(View.VISIBLE);
            gvTest.setVisibility(View.VISIBLE);

        } else {
            //普通员工
            tvUname.setVisibility(View.VISIBLE);
            tvUname.setText(SPUtils.get(mContext, "nickname", "").toString());
            hbImg.setVisibility(View.GONE);
            gvTest.setVisibility(View.GONE);
            participantId = SPUtils.get(mContext, "userId", "").toString();
        }
        myDialog = new AlertDialog(mContext).builder();
    }


    @OnClick({R.id.iv_back, R.id.originator, R.id.start_time, R.id.stop_time, R.id.cancel, R.id.ok, R.id.hb_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.cancel:
                finish();
                break;
            case R.id.originator:
                break;
            case R.id.start_time:
                selectStartTime();
                break;
            case R.id.stop_time:
                selectStopTime();
                break;
            case R.id.ok:
                if (TextUtils.isEmpty(startTimeStr)) {
                    ToastHelper.showToast(mContext, "请选择开始时间");
                    return;
                }
                if (TextUtils.isEmpty(stopTimeStr)) {
                    ToastHelper.showToast(mContext, "请选择结束时间");
                    return;
                }
                showDialog();
                break;
            case R.id.hb_img:
                startActivityForResult(new Intent(ScreenReportActivity.this, ReportSelDepartmentActivity.class), 0);
                break;
        }
    }

    private void selectStartTime() {
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //设置时间
                if (!stopTimeStr.equals("")) {
                    if (TimeUtils.differentDaysByMillisecond(getTime(date), stopTimeStr) < 0) {
                        ToastHelper.showToast(mContext, "请选择不大于结束时间的开始时间");
                        return;
                    }
                }
//                判断选择开始时间是否大于当前时间
                if (TimeUtils.timeCompare(TimeUtils.getNowTime1(), getTime(date)) == 3) {
                    ToastHelper.showToast(mContext, "请选择不大于当前时间的开始时间");
                } else {
                    startTime.setText(getTime(date));
                    startTimeStr = startTime.getText().toString();
                }

            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    private void selectStopTime() {
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (!startTimeStr.equals("")) {
                    if (TimeUtils.differentDaysByMillisecond(startTimeStr, getTime(date)) < 0) {
                        ToastHelper.showToast(mContext, "请选择不小于开始时间的结束时间");
                        return;
                    }
                }
                //设置时间
                stopTime.setText(getTime(date));
                stopTimeStr = stopTime.getText().toString();

            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void showDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_submit, null);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText("汇报筛选成功");
        TextView submit = view.findViewById(R.id.confirm);

        Dialog dialog = new MyDialog(ScreenReportActivity.this, true, true, (float) 0.7)
                .setNewView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交
                dialog.dismiss();
                Intent intent = new Intent(ScreenReportActivity.this, ScreenReportListActivity.class);
                intent.putExtra("createBy", getParticipantId());
                intent.putExtra("beginDate", startTimeStr);
                intent.putExtra("endDate", stopTimeStr);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && requestCode == 0 && data != null) {
            List<People> mList1 = (List<People>) data.getSerializableExtra("mList");
            mList.addAll(mList1);
            mList = TimeUtils.removeDuplicateWithOrder(mList);
            if (mList.size() > 0) {

                adapter = new MeetGridViewAdapter(ScreenReportActivity.this, mList);
                gvTest.setAdapter(adapter);
                gvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        myDialog.setGone().setTitle("提示").setMsg("确定删除么").setNegativeButton("取消", null).setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mList.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        }).show();
                    }
                });
            }
        }
    }


    private String getParticipantId() {
        participantId = "";
        for (People params1 : mList) {
            participantId += params1.getUserId() + ",";
        }
        if (!participantId.equals(""))
            participantId = participantId.substring(0, participantId.length() - 1);
        return participantId;
    }

}
