package com.hulian.oa.me;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.ToastHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//我的-》日程-》添加日程
public class AddScheduleActivity extends BaseActivity {

    @BindView(R.id.tv_select_time)
    TextView tv_select_time;
    @BindView(R.id.tv_select_time2)
    TextView tv_select_time2;
    @BindView(R.id.btn_select_complete)
    Button btn_select_complete;
    @BindView(R.id.edit_content)
    EditText edit_content;
    String add_content; // 日程内容
    Context context;
    String time = "";
    String time2 = "";
    String time3 = "";
    @BindView(R.id.ama_iv_back)
    RelativeLayout iv_back;
    @BindView(R.id.tv_remind)
    TextView tvRemind;
    @BindView(R.id.rl_remind)
    RelativeLayout rlRemind;
    @BindView(R.id.sw_select)
    Switch swSelect;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_addschedule);
        ButterKnife.bind(this);
        context = this;
        time = getIntent().getStringExtra("schetime");
//        Toast.makeText(this,time,Toast.LENGTH_SHORT).show();
        swSelect.setChecked(false);
        swSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                swSelect.setChecked(isChecked);
                if(isChecked){
                    time2="00:00";
                    time3="23:59";
                    tv_select_time.setText(time2);
                    tv_select_time2.setText(time3);
                }
            }
        });

    }

    @OnClick({R.id.tv_select_time, R.id.tv_select_time2, R.id.btn_select_complete, R.id.ama_iv_back})
    public void onViewClicked(View view) {
        add_content = edit_content.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_select_time:
                selectTime();
                break;
            case R.id.btn_select_complete:
                Log.d("这是发布发送的", "点击了");
                if (time2.isEmpty()) {
                    //  tv_select_time.setError("请选择开始时间");
                    ToastHelper.showToast(mContext, "请选择开始时间");
                    return;
                } else if (time3.isEmpty()) {
                    ToastHelper.showToast(mContext, "请选择结束时间");
                    return;
                } else if (add_content.isEmpty()) {
                    ToastHelper.showToast(mContext, "请填写日程内容");
                    return;
                } else {
                    String Time = time + " " + time2;
                    getadd();
                }
                break;
            case R.id.ama_iv_back:
                finish();
                break;
            case R.id.tv_select_time2:
                selectTime2();
                break;
        }

    }

    private void selectTime() {
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //设置时间
                if(!time3.equals(""))
                {
                    if(TimeUtils.differentDaysByMillisecond2(getTime(date),time3)<0){
                        ToastHelper.showToast(mContext, "请选择不小于开始时间的结束时间");
                        return;
                    }
                }
                tv_select_time.setText(getTime(date));
                time2 = getTime(date);
                if(!time2.equals("00:00")){
                    swSelect.setChecked(false);
                }
                else if(time3.equals("23:59")){
                    swSelect.setChecked(true);
                }
            }
        }).setType(new boolean[]{false, false, false, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    private void selectTime2() {
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //设置时间
                if(!time2.equals(""))
                {
                    if(TimeUtils.differentDaysByMillisecond2(time2,getTime(date))<0){
                        ToastHelper.showToast(mContext, "请选择不小于开始时间的结束时间");
                        return;
                    }
                }
                tv_select_time2.setText(getTime(date));
                time3 = getTime(date);
                if(!time3.equals("23:59")){
                    swSelect.setChecked(false);
                }
                else if(time2.equals("00:00")){
                    swSelect.setChecked(true);
                }
            }
        }).setType(new boolean[]{false, false, false, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }


    private void getadd() {
        String userid = SPUtils.get(this, "userId", "-1").toString();
        RequestParams params = new RequestParams();
        params.put("createBy", userid);    //ID
        params.put("scheduleTimeBegin", time + " " + time2); // 时间
        params.put("scheduleTimeEnd", time + " " + time3);
        params.put("scheduleContent", add_content);
        if(swSelect.isChecked()){
            params.put("isToday", "Y");
        }
        else {
            params.put("isToday", "N");
        }
        params.put("warnTime", tvRemind.getText().toString());
        HttpRequest.postSche_Tianjia(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {

                //需要转化为实体对象
                try {

                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONObject obj = new JSONObject(result.toString());
                    String code = obj.getString("code");
                    String msg = obj.getString("msg");
                    if (code.equals("0")) {
                        setResult(1);
                        finish();
                    } else {
                        Toast.makeText(AddScheduleActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                Toast.makeText(AddScheduleActivity.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.rl_remind)
    public void onViewClicked() {
        Intent intent = new Intent(mContext, Me_Schedule_RemindActivity.class);
        startActivityForResult(intent, 110);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 110 && resultCode == 110) {
            tvRemind.setText(data.getStringExtra("tixing"));
        }
    }
}
