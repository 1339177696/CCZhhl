package com.hulian.oa.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
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
 * 作者：qgl 时间： 2020/6/4 10:59
 * Describe:修改个人信息
 */
public class MeSavePersonalActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.tv_me_old_password)
    EditText tvMeOldPassword;
    @BindView(R.id.tv_me_forget)
    TextView tvMeForget;
    @BindView(R.id.tv_former)
    EditText tvFormer;
    @BindView(R.id.tv_affirm)
    EditText tvAffirm;
    @BindView(R.id.btn_modification)
    Button btnModification;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mesavepersonalactivity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.btn_modification,R.id.tv_me_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_modification:
                postData();
                break;
            case R.id.tv_me_forget:
                startActivity(new Intent(this,MeForgetPassActivity.class));
                this.finish();
                break;
        }
    }

    public void postData(){
        if (TextUtils.isEmpty(tvMeOldPassword.getText().toString().trim())) {
            ToastHelper.showToast(mContext, "请输入旧密码");
            return;
        }
        if (tvFormer.getText().toString().trim().length()<6) {
            ToastHelper.showToast(mContext, "密码不能小于六位数");
            return;
        }
        if (TextUtils.isEmpty(tvAffirm.getText().toString().trim())) {
            ToastHelper.showToast(mContext, "请重新输入新密码");
            return;
        }

        if (!tvAffirm.getText().toString().trim().equals(tvFormer.getText().toString().trim()))
        {
            ToastHelper.showToast(mContext, "两次输入密码不一致");
            return;
        }
        loadDialog.show();
        RequestParams params = new RequestParams();
        params.put("userId", SPUtils.get(mContext, "userId", "").toString());
        params.put("oldPassword",tvMeOldPassword.getText().toString().trim());
        params.put("newPassword",tvFormer.getText().toString().trim());
        HttpRequest.getChange_PassWord(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                loadDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(responseObj.toString());
                    if (object.getString("code").equals("0")){
                        SPUtils.put(mContext,"password",tvFormer.getText().toString().trim());
                        Toast.makeText(mContext,"修改成功",Toast.LENGTH_LONG).show();
                        finish();
                    }else {
                        Toast.makeText(mContext,object.getString("msg"),Toast.LENGTH_LONG).show();
                    }

                }catch (JSONException e){

                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                loadDialog.dismiss();
                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
