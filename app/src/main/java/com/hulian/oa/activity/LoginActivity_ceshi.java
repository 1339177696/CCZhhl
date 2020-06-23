package com.hulian.oa.activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hulian.oa.R;
import com.hulian.oa.bean.User;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity_ceshi extends BaseActivity {
    @BindView(R.id.et_userName)
    EditText etUserName;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.bt_login)
    Button btLogin;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if(SPUtils.contains(mContext,"userId")) {
            PostKeyValueRequet(SPUtils.get(mContext,"username","-1").toString(),SPUtils.get(mContext,"password","-1").toString());
        }
    }

    @OnClick(R.id.bt_login)
    public void onViewClicked() {
        if(TextUtils.isEmpty(etUserName.getText().toString().trim())){
            Toast.makeText(mContext,"请输入账号",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(etPass.getText().toString().trim())){
            Toast.makeText(mContext,"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }
            PostKeyValueRequet(etUserName.getText().toString(), etPass.getText().toString());
        }

    /**
     * POST请求
     */
    public void PostKeyValueRequet(String account1,String token1) {
        RequestParams params = new RequestParams();
        params.put("username", account1);
        params.put("password", token1);
        HttpRequest.postLoginApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                // 网易通讯
                User user=(User) responseObj;
                // 初始化消息提醒配置
                SPUtils.clear(mContext);
                SPUtils.put(mContext, "userId", user.getUserId());
                SPUtils.put(mContext, "username", account1);
                SPUtils.put(mContext, "loginName", user.getLoginName());
                SPUtils.put(mContext, "phonenumber", user.getPhonenumber());
                SPUtils.put(mContext, "sex", user.getSex());
                SPUtils.put(mContext,"nickname",user.getUserName());
                SPUtils.put(mContext,"isLead",user.getIsLead());
                SPUtils.put(mContext, "deptId", user.getDeptId());
                SPUtils.put(mContext,"deptname",user.getDept().getDeptName());
                SPUtils.put(mContext,"email",user.getEmail());
                SPUtils.put(mContext, "password", token1);
                SPUtils.put(mContext, "roleKey", user.getRolesStr());
                Intent intent=new Intent(LoginActivity_ceshi.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(LoginActivity_ceshi.this, "请求失败="+failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });

    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
