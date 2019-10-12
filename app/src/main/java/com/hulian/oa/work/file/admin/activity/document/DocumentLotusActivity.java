package com.hulian.oa.work.file.admin.activity.document;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.agency.l_fragment.L_UpcomFragment;
import com.hulian.oa.bean.DocumentImage;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.work.file.admin.activity.document.l_fragment.L_ApprovedFragment;
import com.hulian.oa.work.file.admin.activity.document.l_fragment.L_PendFragment;
import com.hulian.oa.work.file.admin.activity.document.l_gallery_adapter.CustomGalleryAdapter;
import com.hulian.oa.work.file.admin.activity.document.l_gallery_adapter.CustomPagerSnapHelper;
import com.hulian.oa.work.file.admin.activity.document.l_gallery_adapter.GalleryOnScrollListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

//公文流转-》审批//领导端
public class DocumentLotusActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.tv_agree)
    RadioButton tv_agree;
    @BindView(R.id.tv_disagree)
    RadioButton tv_disagree;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    private String offId;
    List<DocumentImage> memberList = new ArrayList<>();
    private CustomGalleryAdapter adapter;

    @BindView(R.id.decument_proname)
    TextView decument_proname;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_lotus);
        ButterKnife.bind(this);
        offId = getIntent().getStringExtra("offId");
        getNetData();
    }

    private void getNetData()
    {
        RequestParams params = new RequestParams();
        params.put("offId", offId);
        params.put("uId", SPUtils.get(mContext, "userId", "").toString());
//        params.put("flag", "1");
//        params.put("state", "0");
        HttpRequest.postDocumentInfoApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    memberList = gson.fromJson(result.getJSONObject("data").getJSONArray("fileList").toString(),
                            new TypeToken<List<DocumentImage>>() {
                            }.getType());

                    init(memberList);

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

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init(List<DocumentImage> memberList) {
        //公文横向滑动
        //设置横滑
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        //填充数据
        adapter = new CustomGalleryAdapter(memberList, this);
        recyclerView.setAdapter(adapter);
        //分页滑动效果
        recyclerView.setOnFlingListener(null);
        new CustomPagerSnapHelper().attachToRecyclerView(recyclerView);
        //滑动动画
        recyclerView.addOnScrollListener(new GalleryOnScrollListener());

    }

    @OnClick({R.id.tv_agree, R.id.tv_disagree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_agree: //同意
                String content = etContent.getText().toString();
                RequestParams params = new RequestParams();
                params.put("offid", offId);
                params.put("uid", SPUtils.get(mContext, "userId", "").toString());
                params.put("content", content);
                params.put("result", "1");
                HttpRequest.postDocumentApproveApi(params, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        try {
                            JSONObject result = new JSONObject(responseObj.toString());
                            ToastHelper.showToast(mContext, result.getString("msg"));
                            if (result.getString("code").equals("0")) {
                                EventBus.getDefault().post(new L_PendFragment());
                                EventBus.getDefault().post(new L_ApprovedFragment());
                                EventBus.getDefault().post(new L_UpcomFragment());
                                finish();
                            }
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
                break;
            case R.id.tv_disagree:
                //  startActivity(new Intent(DocumentLotusActivity.this, DocumentLotusInfoActivity.class));
                String content2 = etContent.getText().toString();
                RequestParams params1 = new RequestParams();
                params1.put("offid", offId);
                params1.put("uid", SPUtils.get(mContext, "userId", "").toString());
                params1.put("content", content2);
                params1.put("result", "2");
                HttpRequest.postDocumentApproveApi(params1, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        try {
                            JSONObject result = new JSONObject(responseObj.toString());
                            ToastHelper.showToast(mContext, result.getString("msg"));
                            if (result.getString("code").equals("0")) {
                                finish();
                                EventBus.getDefault().post(new L_PendFragment());
                                EventBus.getDefault().post(new L_ApprovedFragment());
                                EventBus.getDefault().post(new L_UpcomFragment());
                            }
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
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}