package com.hulian.oa.me.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.socket.JWebSocketClientService;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeActivity extends BaseActivity {

    @BindView(R.id.mine_out_login)
    LinearLayout mine_out_login;
    @BindView(R.id.mine_phone)
    TextView mine_phone;
    //我的日程
    @BindView(R.id.rl_mine_program)
    RelativeLayout rl_mine_program;
    //个人文件柜
    @BindView(R.id.rl_mine_file)
    RelativeLayout rl_mine_file;
    //收藏
    @BindView(R.id.rl_mine_collection)
    RelativeLayout rl_mine_collection;
    //设置
    @BindView(R.id.rl_mine_set)
    RelativeLayout rl_mine_set;
    // 返回键
    @BindView(R.id.iv_back)
    RelativeLayout iv_back;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_dept)
    TextView tvDept;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.min_rela)
    RelativeLayout min_rela;
    private JWebSocketClientService.JWebSocketClientBinder binder;
    private JWebSocketClientService jWebSClientService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (JWebSocketClientService.JWebSocketClientBinder) iBinder;
            jWebSClientService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("MainActivity", "服务与活动成功断开");
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.activity_andmin_me);
        ButterKnife.bind(this);
        init();
        bindService();
    }

    public void init(){
        tv_type.setText(SPUtils.get(mContext, "nickname", "").toString().substring(SPUtils.get(mContext, "nickname", "").toString().length()-2,SPUtils.get(mContext, "nickname", "").toString().length()));
        tvName.setText(SPUtils.get(mContext, "nickname", "").toString());
        tvDept.setText(SPUtils.get(mContext, "deptname", "").toString());
        mine_phone.setText(SPUtils.get(mContext, "username", "").toString());
    }

    @OnClick({R.id.rl_mine_program, R.id.rl_mine_file, R.id.rl_mine_collection, R.id.rl_mine_set, R.id.iv_back, R.id.mine_out_login,R.id.main2_outlogin,R.id.rl_mine_banben,R.id.min_rela})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_mine_program:
                startActivity(new Intent(mContext, ScheduleActivity.class));
                break;
            case R.id.rl_mine_file:
                startActivity(new Intent(mContext, FileBoxActivity.class));
                break;
            case R.id.rl_mine_collection:
                startActivity(new Intent(mContext, CollectionActivity2.class));
                break;
            case R.id.rl_mine_set:
                startActivity(new Intent(mContext, L_SetActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.main2_outlogin:
                NIMClient.getService(AuthService.class).logout();
                SPUtils.clear(mContext);
                jWebSClientService.stopWebSocket();
                exitApp(mContext);
                break;
            case R.id.rl_mine_banben:
                startActivity(new Intent(mContext, LBanbenActivity.class));
                break;
            case R.id.min_rela:
                startActivityForResult(new Intent(mContext, MePersonalActivity.class),1);
                break;
        }
    }

    /**
     * 绑定服务
     */
    private void bindService() {
        Intent bindIntent = new Intent(mContext, JWebSocketClientService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    @OnClick(R.id.rl_mine_suggess)
    public void onViewClicked() {
        startActivity(new Intent(mContext, MeSuggestBackActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                init();
                break;
        }
    }
}
