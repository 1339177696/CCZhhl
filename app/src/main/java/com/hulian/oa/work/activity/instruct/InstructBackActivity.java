package com.hulian.oa.work.activity.instruct;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

//指令反馈
public class InstructBackActivity extends BaseActivity {


    @BindView(R.id.tv_back_instruct)
    TextView tv_back_instruct;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.create_time)
    TextView create_time;
    @BindView(R.id.create_coontent)
    TextView create_coontent;
    @BindView(R.id.details_img)
    ImageView details_img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_instruct_back);
        ButterKnife.bind(this);
        getData();
    }

    @OnClick({R.id.tv_back_instruct,R.id.rl_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_instruct:
                sendData();
                break;
            case R.id.rl_title:
                finish();
                break;
        }
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("id", getIntent().getStringExtra("id"));

        HttpRequest.postInstructDetailsApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    String  imgUrl = result.getJSONObject("data").getString("image");
                    Glide.with(InstructBackActivity.this).load(imgUrl).into(details_img);
                    create_time.setText(TimeUtils.getDateToString(result.getJSONObject("data").getString("createTime")));
                    if(result.getJSONObject("data").getString("content").equals("null")){
                        create_coontent.setText(getIntent().getStringExtra("content"));
                    }
                    else
                    create_coontent.setText(result.getJSONObject("data").getString("content"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }

    private void sendData(){
        String content = et_content.getText() + "";
        if (TextUtils.isEmpty(content)){
            ToastHelper.showToast(InstructBackActivity.this,"请输入反馈内容!");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("type","1");
        params.put("id",getIntent().getStringExtra("id"));
        params.put("feedback",content);
        HttpRequest.postInstructOperation(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    String code = result.getString("code");
                    if (TextUtils.equals(code,"0")){
                        EventBus.getDefault().post(new UpcomFragment());
                        finish();
                    }
                    ToastHelper.showToast(InstructBackActivity.this,result.getString("msg"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }
}
