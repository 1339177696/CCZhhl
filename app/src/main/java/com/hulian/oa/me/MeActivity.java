package com.hulian.oa.me;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hulian.oa.BaseActivity;
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

    private JWebSocketClientService jWebSClientService;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.activity_andmin_me);
        ButterKnife.bind(this);
        tv_type.setText(SPUtils.get(mContext, "nickname", "").toString().substring(SPUtils.get(mContext, "nickname", "").toString().length()-2,SPUtils.get(mContext, "nickname", "").toString().length()));
        tvName.setText(SPUtils.get(mContext, "nickname", "").toString());
        tvDept.setText(SPUtils.get(mContext, "deptname", "").toString());
        mine_phone.setText(SPUtils.get(mContext, "username", "").toString());

    }

    @OnClick({R.id.rl_mine_program, R.id.rl_mine_file, R.id.rl_mine_collection, R.id.rl_mine_set, R.id.iv_back, R.id.mine_out_login,R.id.main2_outlogin})
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
                exitApp(mContext);
                break;
        }
    }

    @OnClick(R.id.rl_mine_suggess)
    public void onViewClicked() {
        startActivity(new Intent(mContext, MeSuggestBackActivity.class));
    }
}
