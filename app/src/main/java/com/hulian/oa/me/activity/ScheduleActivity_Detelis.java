package com.hulian.oa.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.ToastHelper;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.picker.TimePicker;

/**
 * Created by qgl on 2020/1/17 13:49.
 */
public class ScheduleActivity_Detelis extends BaseActivity {
    @BindView(R.id.tv_bianji)
    TextView tvBianji;
    @BindView(R.id.sw_select)
    Switch swSelect;
    @BindView(R.id.tv_select_time)
    TextView tv_select_time;
    @BindView(R.id.tv_select_time2)
    TextView tv_select_time2;
    @BindView(R.id.edit_content)
    EditText edit_content;
    @BindView(R.id.tv_remind)
    TextView tv_remind;
    @BindView(R.id.img_start)
    ImageView imgStart;
    @BindView(R.id.img_end)
    ImageView imgEnd;
    @BindView(R.id.img_remind)
    ImageView imgRemind;
    @BindView(R.id.btn_select_complete)
    Button btnSelectComplete;
    private String type = "";
    private String title = "";
    private String qu = "";
    private String start = "";
    private String end = "";
    private String warnTime = "";
    private String id = "";
    String time = "";
    String time2 = "";
    String time3 = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduleactivity_detelis);
        ButterKnife.bind(this);
        swSelect.setClickable(false);
        imgStart.setVisibility(View.INVISIBLE);
        imgEnd.setVisibility(View.INVISIBLE);
        imgRemind.setVisibility(View.INVISIBLE);
        edit_content.setEnabled(false);
        tv_select_time.setClickable(false);
        tv_select_time2.setClickable(false);
        tv_remind.setClickable(false);
        ini();
    }

    public void ini() {
        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        qu = getIntent().getStringExtra("qu");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        warnTime = getIntent().getStringExtra("warnTime");
        id = getIntent().getStringExtra("id");
        if (("Y").equals(qu)) {
            swSelect.setChecked(true);
        } else {
            swSelect.setChecked(false);
        }
        tv_select_time.setText(TimeUtils.getDateToString1(start));
        tv_select_time2.setText(TimeUtils.getDateToString1(end));
        edit_content.setText(title);
        tv_remind.setText(warnTime);
        time = TimeUtils.getDateToString6(start);

    }

    @OnClick({R.id.tv_bianji,R.id.btn_select_complete,R.id.tv_select_time,R.id.tv_select_time2,R.id.ama_iv_back,R.id.tv_remind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bianji:
                swSelect.setClickable(true);
                imgStart.setVisibility(View.VISIBLE);
                imgEnd.setVisibility(View.VISIBLE);
                imgRemind.setVisibility(View.VISIBLE);
                btnSelectComplete.setBackgroundResource(R.drawable.l_leave_submit_bg2);
                btnSelectComplete.setText("保存");
                tvBianji.setVisibility(View.GONE);
                edit_content.setEnabled(true);
                tv_select_time.setClickable(true);
                tv_select_time2.setClickable(true);
                tv_remind.setClickable(true);
                swSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        swSelect.setChecked(isChecked);
                        if(isChecked){
                            time2="08:00";
                            time3="16:59";
                            tv_select_time.setText(time2);
                            tv_select_time2.setText(time3);
                        }
                    }
                });
                break;
            case R.id.btn_select_complete:
                if (btnSelectComplete.getText().toString().equals("删除日程")){
                    getDele();
                }else {
                    getadd();
                }
                break;
            case R.id.tv_select_time:
                onTimePicker(view);
                break;
            case R.id.tv_select_time2:
                onTimePicker2(view);
                break;
            case R.id.ama_iv_back:
                finish();
                break;
            case R.id.tv_remind:
                Intent intent = new Intent(mContext, Me_Schedule_RemindActivity.class);
                startActivityForResult(intent, 110);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 110 && resultCode == 110) {
            tv_remind.setText(data.getStringExtra("tixing"));
        }
    }
    //选择开始时间
    public void onTimePicker(View view) {
        TimePicker picker = new TimePicker(this, TimePicker.HOUR_24);
        picker.setRangeStart(8, 0);//09:00
        picker.setRangeEnd(16, 0);//18:30
        picker.setTopLineVisible(false);
        picker.setLineVisible(false);
        picker.setWheelModeEnable(false);
        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {

                if(!time3.equals(""))
                {
                    if(TimeUtils.differentDaysByMillisecond2(hour+":"+minute,time3)<0){
                        ToastHelper.showToast(mContext, "请选择不小于开始时间的结束时间");
                        return;
                    }
                }
                tv_select_time.setText(hour+":"+minute);
                time2 =hour+":"+minute;
                if(!time2.equals("08:00")){
                    swSelect.setChecked(false);
                }
                else if(time3.equals("16:59")){
                    swSelect.setChecked(true);
                }
            }
        });
        picker.show();
    }
    // 选择结束时间
    public void onTimePicker2(View view) {
        TimePicker picker = new TimePicker(this, TimePicker.HOUR_24);
        picker.setRangeStart(8, 0);//09:00
        picker.setRangeEnd(16, 0);//18:30
        picker.setTopLineVisible(false);
        picker.setLineVisible(false);
        picker.setWheelModeEnable(false);
        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                //设置时间
                if(!time2.equals(""))
                {
                    if(TimeUtils.differentDaysByMillisecond2(time2,hour+":"+minute)<0){
                        ToastHelper.showToast(mContext, "请选择不小于开始时间的结束时间");
                        return;
                    }
                }
                tv_select_time2.setText(hour+":"+minute);
                time3 =hour+":"+minute;
                if(!time3.equals("16:59")){
                    swSelect.setChecked(false);
                }
                else if(time2.equals("08:00")){
                    swSelect.setChecked(true);
                }
            }
        });
        picker.show();
    }

    private void getadd() {
        RequestParams params = new RequestParams();
        params.put("id", id);    //ID
        params.put("scheduleTimeBegin", time + " " + tv_select_time.getText().toString()); // 时间
        params.put("scheduleTimeEnd", time + " " +  tv_select_time2.getText().toString());
        params.put("scheduleContent", edit_content.getText().toString());
        if(swSelect.isChecked()){
            params.put("isToday", "Y");
        }
        else {
            params.put("isToday", "N");
        }
        params.put("warnTime", tv_remind.getText().toString());
        HttpRequest.postSche_Xiugai(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONObject obj = new JSONObject(result.toString());
                    String code = obj.getString("code");
                    String msg = obj.getString("msg");
                    if (code.equals("0")) {
                        Toast.makeText(ScheduleActivity_Detelis.this, msg, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ScheduleActivity_Detelis.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                Toast.makeText(ScheduleActivity_Detelis.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDele(){
        RequestParams params = new RequestParams();
        params.put("ids", id);    //ID
        HttpRequest.postSche_Dlete(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONObject obj = new JSONObject(result.toString());
                    String code = obj.getString("code");
                    String msg = obj.getString("msg");
                    if (code.equals("0")) {
                        Toast.makeText(ScheduleActivity_Detelis.this, msg, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ScheduleActivity_Detelis.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(ScheduleActivity_Detelis.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }





}
