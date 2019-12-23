package com.hulian.oa.work.file.admin.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.People;
import com.hulian.oa.bean.People_x;
import com.hulian.oa.me.SelPeopleActivity;
import com.hulian.oa.me.SelPeopleActivity_x;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.work.file.admin.activity.document.l_fragment.L_PendFragment;
import com.hulian.oa.work.file.admin.activity.leave.SelPeopleActivity_Leave_people;
import com.luck.picture.lib.entity.LocalMedia;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.auth.LoginInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class PostOrderActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_sel_peo)
    TextView tvSelPeo;
    @BindView(R.id.rl_sel_people)
    RelativeLayout rlSelPeople;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.bt_post)
    Button btPost;
    String filePath;
    private AbortableFuture<LoginInfo> loginRequest;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_order);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        filePath=getIntent().getStringExtra("file");
        Glide.with(this).load(filePath).into(ivImage);
    }

    @OnClick({R.id.iv_back, R.id.tv_sel_peo, R.id.rl_sel_people, R.id.et_content, R.id.bt_post,R.id.rl_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_title:
                finish();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_sel_peo:
                break;
            case R.id.rl_sel_people:
                Intent intent=new Intent(PostOrderActivity.this, SelPeopleActivity_x.class);
                intent.putExtra("partId", SPUtils.get(mContext, "deptId", "").toString());
                startActivity(intent);
                break;
            case R.id.et_content:
                break;
            case R.id.bt_post:
                if(tvSelPeo.getTag().equals("")){
                    ToastHelper.showToast(mContext,"请选择接收人");
                    return;
                }
                if(TextUtils.isEmpty(etContent.getText().toString())){
                    ToastHelper.showToast(mContext,"请输入指令");
                    return;
                }
                RequestParams params = new RequestParams();
                params.put("content",etContent.getText().toString());
                params.put("createBy",  SPUtils.get(mContext, "userId", "").toString());
                params.put("receiver", tvSelPeo.getTag().toString());
                List<File> fils = new ArrayList<>();
                fils.add(new File(filePath));
                HttpRequest.postInstructSendApi(params, fils, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {

                        try {
                            JSONObject result = new JSONObject(responseObj.toString());
                            ToastHelper.showToast(mContext, result.getString("msg"));
                            finish();
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

    public void onEventMainThread(People_x event) {
        tvSelPeo.setText(event.getUserName());
        tvSelPeo.setTag(event.getUserId()+"");

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
