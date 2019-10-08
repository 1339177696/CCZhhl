package com.hulian.oa.work.file.admin.activity.instruct;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.TimeUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//指令详情
public class InstructDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_name2)
    TextView tv_name2;
    @BindView(R.id.tv_feedback_content)
    TextView tv_feedback_content;
    @BindView(R.id.create_time)
    TextView create_time;
    @BindView(R.id.details_img)
    ImageView details_img;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.create_coontent)
    TextView createCoontent;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_instruct_datails);
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
                    JSONObject result = new JSONObject(responseObj.toString()).getJSONObject("data");
                    String imgUrl = result.getString("image");
                    Glide.with(InstructDetailsActivity.this).load(imgUrl).into(details_img);
//                    if(result.getString("content")!=null&&!result.getString("content").equals(""))
//                    createCoontent.setText(result.getString("content"));
//                    create_time.setText(TimeUtils.getDateToString(result.getString("createTime")));
//                    if (TextUtils.equals(result.getString("receive"), "1")) {
//                        tv_state.setText("已接收");
//                        tv_state.setTextColor(getResources().getColor(R.color.color_a_blue));
//                    } else {
//                        llBottom.setVisibility(View.GONE);
//                        tv_state.setText("未接收");
//                    }
//                    if (TextUtils.equals(result.getString("state"), "1")) {
//                        tv_state2.setText("已完成领导指令");
//                        tv_state2.setTextColor(getResources().getColor(R.color.colorAccent));
//                    } else {
//                        tv_state2.setText("未完成领导指令");
//                    }
//                    if(result.getString("receiveTime")!=null&&!result.getString("receiveTime").equals("null"))
//                    tv_time.setText(result.getString("receiveTime"));
//                    if(result.getString("feedbackTime")!=null&&!result.getString("feedbackTime").equals("null"))
//                    tv_time2.setText(result.getString("feedbackTime"));
//                    tv_name.setText(result.getString("receiver"));
//                    tv_name2.setText(result.getString("receiver"));
                    create_time.setText(TimeUtils.getDateToString(result.getString("createTime")));
                    createCoontent.setText(getIntent().getStringExtra("content"));
                    tv_name2.setText(result.getString("receiver"));
                    if(result.getString("feedback")!=null&&!result.getString("feedback").equals("null"))
                    tv_feedback_content.setText("反馈:"+result.getString("feedback"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }

    @OnClick(R.id.rl_title)
    public void onViewClicked() {
        finish();
    }
}
