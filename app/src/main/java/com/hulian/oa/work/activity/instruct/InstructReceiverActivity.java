package com.hulian.oa.work.activity.instruct;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.agency.fragment.UpcomFragment;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.ToastHelper;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

//指令接收
public class InstructReceiverActivity extends BaseActivity {

    @BindView(R.id.tv_receiver_instruct)
    TextView tv_receiver_instruct;
    @BindView(R.id.details_img)
    ImageView details_img;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_time)
    TextView tvTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_instruct_receiver);
        ButterKnife.bind(this);
        getData();
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("id", getIntent().getStringExtra("id"));

        HttpRequest.postInstructDetailsApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    String imgUrl = result.getJSONObject("data").getString("image");
                    Glide.with(InstructReceiverActivity.this).load(imgUrl).into(details_img);
                    if(result.getJSONObject("data").getString("content").equals("null")){
                        tvContent.setText(getIntent().getStringExtra("content"));
                    }
                    else
                    tvContent.setText(result.getJSONObject("data").getString("content"));
                    tvTime.setText(TimeUtils.getDateToString(result.getJSONObject("data").getString("createTime")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }

    @OnClick({R.id.tv_receiver_instruct, R.id.rl_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_receiver_instruct:
                sendData();
                break;
            case R.id.rl_title:
                finish();
                break;

        }
    }

    private void sendData() {
        RequestParams params = new RequestParams();
        params.put("type", "0");
        params.put("id", getIntent().getStringExtra("id"));
        HttpRequest.postInstructOperation(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    String code = result.getString("code");
                    if (TextUtils.equals(code, "0")) {
                        EventBus.getDefault().post(new UpcomFragment());
                        finish();
                    }
                    ToastHelper.showToast(InstructReceiverActivity.this, result.getString("msg"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }
}
