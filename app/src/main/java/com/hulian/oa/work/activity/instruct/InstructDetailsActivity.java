package com.hulian.oa.work.activity.instruct;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hulian.oa.activity.BaseActivity;
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
