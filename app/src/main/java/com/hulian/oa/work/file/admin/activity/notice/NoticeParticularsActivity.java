package com.hulian.oa.work.file.admin.activity.notice;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.Fab;
import com.hulian.oa.bean.Fab2;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.utils.URLImageParser;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 通知详情
 */
public class NoticeParticularsActivity extends BaseActivity {
    @BindView(R.id.tv_mengban)
    TextView tvMengban;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_author)
    TextView tvAuthor;

    String noticeId;
    String isCollect;
    @BindView(R.id.bt_store)
    ImageView btStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_notice_particulars);
        noticeId = getIntent().getStringExtra("noticeId");
        isCollect = getIntent().getStringExtra("isCollect");
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        //新改的判断收藏qgl
        if (isCollect.equals("0")) {
            btStore.setBackgroundResource(R.mipmap.ic_store);
        } else if (isCollect.equals("1")) {
            btStore.setBackgroundResource(R.mipmap.ic_store_sel);
        }
        getData();
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("noticeId", noticeId);
        HttpRequest.postNoticeInfoApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    tvTitle.setText(result.getJSONObject("data").getString("noticeTitle"));

                    URLImageParser imageGetter = new URLImageParser(tvContent);
                    tvContent.setText(Html.fromHtml(result.getJSONObject("data").getString("noticeContent"), imageGetter, null));

                    tvAuthor.setText("发布人: " + result.getJSONObject("data").getString("createBy") + "      " + result.getJSONObject("data").getString("createTime"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.bt_store, R.id.rl_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_title:
                finish();
                break;
            case R.id.bt_store:
                if (isCollect.equals("0"))
                {
                    RequestParams params1 = new RequestParams();
                    params1.put("collectTypeId", noticeId);
                    params1.put("collectType", "1");
                    params1.put("collectUserId", SPUtils.get(mContext, "userId", "").toString());
                    HttpRequest.postStoreSenCommApi(params1, new ResponseCallback() {
                        @Override
                        public void onSuccess(Object responseObj) {
                            try {
                                JSONObject result = new JSONObject(responseObj.toString());
                                ToastHelper.showToast(mContext, result.getString("msg"));
                                btStore.setBackgroundResource(R.mipmap.ic_store_sel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(OkHttpException failuer) {
                        }
                    });
                }else if (isCollect.equals("1")){

                }

                break;
        }
    }

    public void onEventMainThread(Fab event) {
        if (event.getTag().equals("0")) {
            tvMengban.setVisibility(View.GONE);
        } else {
            tvMengban.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.tv_mengban)
    public void onViewClicked2() {
        Fab2 fab2 = new Fab2();
        fab2.setTag("0");
        EventBus.getDefault().post(fab2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
