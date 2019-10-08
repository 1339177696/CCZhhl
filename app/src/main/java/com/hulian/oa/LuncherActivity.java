package com.hulian.oa;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hulian.oa.bean.User;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.utils.preference.Preferences;
import com.hulian.oa.utils.preference.UserPreferences;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;

import static java.lang.Thread.sleep;

public class LuncherActivity extends BaseActivity {
    private AbortableFuture<LoginInfo> loginRequest;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luncher);
//        new Thread( new Runnable( ) {
//            @Override
//            public void run() {
//                //耗时任务，比如加载网络数据
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        // 这里可以睡几秒钟，如果要放广告的话
//                        try {
//                            sleep(3000);
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//        } ).start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(SPUtils.contains(mContext,"userId")) {
                    if(BuildConfig.IsPad){
                      //  PostKeyValueRequet(SPUtils.get(mContext,"username","-1").toString(),SPUtils.get(mContext,"password","-1").toString());
                        doLogin(SPUtils.get(mContext,"username","-1").toString(),SPUtils.get(mContext,"password","-1").toString());
                    }else {
                        doLogin(SPUtils.get(mContext,"username","-1").toString(),SPUtils.get(mContext,"password","-1").toString());
                    }
                }
                else {
                    Intent intent = new Intent(LuncherActivity.this, LoginActivity.class);
                    startActivity(intent);
                    LuncherActivity.this.finish();
                }
            }
        }, 2000);

    }
    private void doLogin(String account1,String token1) {
        //   onLoginDone();
//         云信只提供消息通道，并不包含用户资料逻辑。开发者需要在管理后台或通过服务器接口将用户帐号和token同步到云信服务器。
//         在这里直接使用同步到云信服务器的帐号和token登录。
//         这里为了简便起见，demo就直接使用了密码的md5作为token。
//         如果开发者直接使用这个demo，只更改appkey，然后就登入自己的账户体系的话，需要传入同步到云信服务器的token，而不是用户密码。
   //     loadDialog.show();
        final String account = account1;
        final String token =token1;
        // 登录
        loginRequest = NimUIKit.login(new LoginInfo(account, token), new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                loadDialog.dismiss();
                onLoginDone();
                DemoCache.setAccount(account);
                saveLoginInfo(account, token);
                PostKeyValueRequet(account1,token1);
            }
            @Override
            public void onFailed(int code) {
                loadDialog.dismiss();
                onLoginDone();
                if (code == 302 || code == 404) {
                    ToastHelper.showToast(LuncherActivity.this, "登录失败");
                } else {
                    ToastHelper.showToast(LuncherActivity.this, "登录失败: " + code);
                }
            }

            @Override
            public void onException(Throwable exception) {
                loadDialog.dismiss();
                ToastHelper.showToast(LuncherActivity.this, "登录失败");
                onLoginDone();
            }
        });

    }

    private void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
    }
    private void onLoginDone() {
        loginRequest = null;
        // DialogMaker.dismissProgressDialog();
    }
    private void initNotificationConfig() {
        // 初始化消息提醒
        NIMClient.toggleNotification(UserPreferences.getNotificationToggle());
        // 加载状态栏配置
        StatusBarNotificationConfig statusBarNotificationConfig = UserPreferences.getStatusConfig();
        if (statusBarNotificationConfig == null) {
            statusBarNotificationConfig = DemoCache.getNotificationConfig();
            UserPreferences.setStatusConfig(statusBarNotificationConfig);
        }
        // 更新配置
        NIMClient.updateStatusBarNotificationConfig(statusBarNotificationConfig);
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
                //    UserInfo userInfo = (UserInfo) responseObj;
                User user=(User) responseObj;
//                Toast.makeText(LoginActivity.this, "请求成功"+responseObj.toString(), Toast.LENGTH_SHORT).show();
                // 初始化消息提醒配置
                SPUtils.clear(mContext);
                SPUtils.put(mContext, "userId", user.getUserId());
                SPUtils.put(mContext, "username", account1);
                SPUtils.put(mContext,"nickname",user.getUserName());
                SPUtils.put(mContext,"isLead",user.getIsLead());
                SPUtils.put(mContext, "deptId", user.getDeptId());
                SPUtils.put(mContext,"deptname",user.getDept().getDeptName());
                SPUtils.put(mContext,"email",user.getEmail());
                SPUtils.put(mContext, "password", token1);
                initNotificationConfig();
                Intent intent=new Intent(LuncherActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            @Override
            public void onFailure(OkHttpException failuer) {
                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                Toast.makeText(LuncherActivity.this, "请求失败="+failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
