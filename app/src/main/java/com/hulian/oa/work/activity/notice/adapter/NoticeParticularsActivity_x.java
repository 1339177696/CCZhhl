package com.hulian.oa.work.activity.notice.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.ToastHelper;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 通知详情
 */
public class NoticeParticularsActivity_x extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    String noticeId;
    @BindView(R.id.bt_store)
    ImageView btStore;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_notice_particulars);
        noticeId = getIntent().getStringExtra("noticeId");
        ButterKnife.bind(this);
        getData();
    }

    private void getData() {
        RequestParams params = new RequestParams();
        Log.d("noticeId",noticeId);
        params.put("noticeId", noticeId);
        HttpRequest.postNoticeInfoApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    tvTitle.setText(result.getJSONObject("data").getString("noticeTitle"));
                    tvContent.setText(result.getJSONObject("data").getString("noticeContent"));
                    tvAuthor.setText(result.getJSONObject("data").getString("createBy") + "  " + result.getJSONObject("data").getString("createTime"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.bt_store})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_store:
                RequestParams params1 = new RequestParams();
                params1.put("collectTypeId", noticeId);
                params1.put("collectType","1");
                params1.put("collectUserId", SPUtils.get(mContext, "userId", "").toString());
                HttpRequest.postStoreSenCommApi(params1, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        try {
                            JSONObject result = new JSONObject(responseObj.toString());
                            ToastHelper.showToast(mContext, result.getString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(OkHttpException failuer) {
                    }
                });
                break;
        }
    }
}
