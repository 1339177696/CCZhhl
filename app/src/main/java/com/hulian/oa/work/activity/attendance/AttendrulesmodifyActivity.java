package com.hulian.oa.work.activity.attendance;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.work.fragment.CalendarFragment;
import com.hulian.oa.work.fragment.ClockFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by qgl on 2020/3/23 11:05.
 */
public class AttendrulesmodifyActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.dk_tv_time)
    EditText dkTvTime;
    @BindView(R.id.dk_tv_work)
    TextView dkTvWork;
    @BindView(R.id.dk_tv_after_work)
    TextView dkTvAfterWork;
    @BindView(R.id.dk_tv_adress)
    TextView dkTvAdress;
    @BindView(R.id.dk_tv_distance)
    EditText dkTvDistance;
    @BindView(R.id.dk_tv_card)
    EditText dkTvCard;
    @BindView(R.id.btn_select_complete)
    Button btnSelectComplete;
    @BindView(R.id.liner_m1)
    LinearLayout linerM1;
    @BindView(R.id.liner_m2)
    LinearLayout linerM2;
    private String id = "";
    private String jingwei = "";
    private String adress = "";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.attendrulesmodifyactivity);
        ButterKnife.bind(this);
        // 从上一个页面传过来的赋值
        dkTvTime.setText(getIntent().getStringExtra("registerContent")+"");
        dkTvWork.setText(getIntent().getStringExtra("upTime")+"");
        dkTvAfterWork.setText(getIntent().getStringExtra("downTime")+"");
        dkTvAdress.setText(getIntent().getStringExtra("registerAddress")+"");
        dkTvDistance.setText(getIntent().getStringExtra("distance")+"");
        dkTvCard.setText(getIntent().getStringExtra("rxhRule")+"");
        jingwei = getIntent().getStringExtra("jingwei")+"";
        adress = getIntent().getStringExtra("registerAddress")+"";
    }

    @OnClick({R.id.iv_back, R.id.dk_tv_adress, R.id.btn_select_complete, R.id.liner_m1, R.id.liner_m2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.dk_tv_adress:
                startActivityForResult(new Intent(AttendrulesmodifyActivity.this, MapActivity.class), 1);
                break;
            case R.id.btn_select_complete:
                SubData();
                break;
            case R.id.liner_m1:
                selectTime(dkTvWork);
                break;
            case R.id.liner_m2:
                selectendTime(dkTvAfterWork);
                break;
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return format.format(date);
    }

    /**
     * 上班时间选择
     *
     * @param textView
     */
    private void selectTime(TextView textView) {
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //判断选择开始时间是否大于当前时间
                if (TimeUtils.timeCompare(TimeUtils.getNowTime1(), getTime(date)) == 1) {
                    ToastHelper.showToast(mContext, "请选择当前时间之后");
                } else {
                    //设置时间
                    textView.setText(getTime(date));
                }
            }
        }).setType(new boolean[]{false, false, false, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    /**
     * 下班时间选择
     *
     * @param textView
     */
    private void selectendTime(TextView textView) {
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //判断选择开始时间是否大于当前时间
                if (TimeUtils.timeCompare(TimeUtils.getNowTime1(), getTime(date)) == 1) {
                    ToastHelper.showToast(mContext, "请选择当前时间之后");
                } else {
                    //设置时间
                    textView.setText(getTime(date));
                }
            }
        }).setType(new boolean[]{false, false, false, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    public void SubData() {
        if (!getIntent().getStringExtra("id").equals(""))
        {
            id = getIntent().getStringExtra("id");
        }
        if (TextUtils.isEmpty(dkTvTime.getText().toString().trim())) {
            ToastHelper.showToast(mContext, "请输入打卡时间");
            return;
        }
        if (TextUtils.isEmpty(dkTvWork.getText().toString().trim())) {
            ToastHelper.showToast(mContext, "请选择上班时间");
            return;
        }
        if (TextUtils.isEmpty(dkTvAfterWork.getText().toString().trim())) {
            ToastHelper.showToast(mContext, "请选择下班时间");
            return;
        }
        if (adress.equals("")) {
            ToastHelper.showToast(mContext, "请选择打卡地址");
            return;
        }
        if (TextUtils.isEmpty(dkTvDistance.getText().toString().trim())) {
            ToastHelper.showToast(mContext, "请输入打卡范围");
            return;
        }
        if (TextUtils.isEmpty(dkTvCard.getText().toString().trim())) {
            ToastHelper.showToast(mContext, "请输入规则");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("registerContent", dkTvTime.getText().toString());
        params.put("upTime", dkTvWork.getText().toString());
        params.put("downTime", dkTvAfterWork.getText().toString());
        params.put("registerAddress", adress);
        params.put("registerCoordinate", jingwei);
        params.put("distance", dkTvDistance.getText().toString());
        params.put("rxhRule", dkTvCard.getText().toString());
        HttpRequest.AddClock_rules(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    ToastHelper.showToast(mContext, result.getString("msg"));
                    if (result.getString("code").equals("0")) {
                        EventBus.getDefault().post(new AttendrulesActivity());
                        EventBus.getDefault().post(new ClockFragment());
                        EventBus.getDefault().post(new CalendarFragment());
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(AttendrulesmodifyActivity.this, "服务器请求失败", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            adress = data.getStringExtra("addname");
            jingwei = data.getStringExtra("jingdu")+","+data.getStringExtra("weidu");
            dkTvAdress.setText(adress);
        }
    }

}
