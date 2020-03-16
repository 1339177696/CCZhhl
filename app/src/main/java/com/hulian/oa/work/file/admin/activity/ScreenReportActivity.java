package com.hulian.oa.work.file.admin.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.views.MyDialog;
import com.hulian.oa.views.MyGridView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 陈泽宇 on 2020/3/13.
 * Describe: 汇报筛选
 */
public class ScreenReportActivity extends BaseActivity {


    @BindView(R.id.gv_test)
    MyGridView gvTest;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.stop_time)
    TextView stopTime;

    private String meetingTime = "";
    private String meetingTimeEnd = "";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.activity_screen_report);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.iv_back, R.id.originator, R.id.start_time, R.id.stop_time, R.id.cancel, R.id.ok})
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
                showDialog();
                break;
        }
    }

    private void selectStartTime() {
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //设置时间
                if (!meetingTimeEnd.equals("")) {
                    if (TimeUtils.differentDaysByMillisecond(getTime(date), meetingTimeEnd) > 0) {
                        ToastHelper.showToast(mContext, "请选择不小于开始时间的结束时间");
                        return;
                    }
                }
//                判断选择开始时间是否大于当前时间
                if (TimeUtils.timeCompare(TimeUtils.getNowTime1(), getTime(date)) == 1) {
                    ToastHelper.showToast(mContext, "请选择当前时间之后");
                } else {
                    startTime.setText(getTime(date));
                    meetingTime = startTime.getText().toString();
                }

            }
        }).setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    private void selectStopTime() {
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (!meetingTime.equals("")) {
                    if (TimeUtils.differentDaysByMillisecond(meetingTime, getTime(date)) < 0) {
                        ToastHelper.showToast(mContext, "请选择不小于开始时间的结束时间");
                        return;
                    }
                }
                //设置时间
                stopTime.setText(getTime(date));
                meetingTimeEnd = stopTime.getText().toString();

            }
        }).setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
                startActivity(new Intent(ScreenReportActivity.this,ScreenReportListActivity.class));
            }
        });
    }
}
