package com.hulian.oa.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.Toast;
import com.hulian.oa.R;
import com.hulian.oa.bean.User;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;

public class LuncherActivity extends BaseActivity {
    private Intent login;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.transparencyBar(this);
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }
        setContentView(R.layout.activity_luncher);
        login=new Intent(LuncherActivity.this, LoginActivity_ceshi.class);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(SPUtils.contains(mContext,"userId")) {
                    PostKeyValueRequet(SPUtils.get(mContext,"username","-1").toString(),SPUtils.get(mContext,"password","-1").toString());
                }
                else {
                    Intent intent = new Intent(LuncherActivity.this, LoginActivity_ceshi.class);
                    startActivity(intent);
                    LuncherActivity.this.finish();
                }
            }
        }, 2000);
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
                Intent intent=new Intent(LuncherActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(LuncherActivity.this, "请求失败="+failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                // 跳转登录页
                startActivity(login);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
