package com.hulian.oa.work.activity.attendance;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.utils.ToastHelper;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by qgl on 2020/3/20 16:10.
 */
public class AttendrulesActivity extends BaseActivity {
    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.tv_modify)
    TextView tv_modify;
    @BindView(R.id.dk_tv_time)
    TextView dkTvTime;
    @BindView(R.id.dk_tv_work)
    TextView dkTvWork;
    @BindView(R.id.dk_tv_after_work)
    TextView dkTvAfterWork;
    @BindView(R.id.dk_tv_adress)
    TextView dkTvAdress;
    @BindView(R.id.dk_tv_card)
    TextView dkTvCard;

    private String id = "";
    private String registerContent = "";
    private String upTime = "";
    private String downTime = "";
    private String registerAddress = "";
    private String distance = "";
    private String rxhRule = "";
    private String jingwei = "";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.attendrulesactivity);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        if (SPUtils.get(this, "roleKey", "").toString().contains("synthesizeLead")){
            tv_modify.setVisibility(View.VISIBLE);
        }else {
            tv_modify.setVisibility(View.GONE);
        }
        postRule();
    }

    @OnClick({R.id.im_back, R.id.tv_modify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                finish();
                break;
                //修改
            case R.id.tv_modify:
                // 跳转修改规则
                Intent intent = new Intent(AttendrulesActivity.this,AttendrulesmodifyActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("registerContent",registerContent);
                intent.putExtra("upTime",upTime);
                intent.putExtra("downTime",downTime);
                intent.putExtra("registerAddress",registerAddress);
                intent.putExtra("distance",distance);
                intent.putExtra("rxhRule",rxhRule);
                intent.putExtra("jingwei",jingwei);
                startActivity(intent);
                break;
        }
    }

    /**
     * 请求打卡规则
     */
    public void postRule(){
        RequestParams params = new RequestParams();
        HttpRequest.PostClock_rules(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {

                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (result.optString("data") == "")
                    {
                        ToastHelper.showToast(AttendrulesActivity.this,"没有定制规则");
                    }else {
                        id = result.getJSONObject("data").getString("id");
                        registerContent = result.getJSONObject("data").getString("registerContent");
                        upTime = result.getJSONObject("data").getString("upTime");
                        downTime = result.getJSONObject("data").getString("downTime");
                        registerAddress = result.getJSONObject("data").getString("registerAddress");
                        distance = result.getJSONObject("data").getString("distance");
                        rxhRule = result.getJSONObject("data").getString("rxhRule");
                        jingwei = result.getJSONObject("data").getString("registerCoordinate");
                        dkTvTime.setText(registerContent+"");
                        dkTvWork.setText("在   "+upTime+"   之后打卡视为迟到");
                        dkTvAfterWork.setText("在   "+downTime+"   之前打卡视为早退");
                        dkTvAdress.setText(registerAddress+"附近"+distance+"米");
                        dkTvCard.setText(rxhRule+"");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                ToastHelper.showToast(mContext, "服务器请求失败");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    // 刷新
    public void onEventMainThread(AttendrulesActivity event) {
        postRule();
    }
}
