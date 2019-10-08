package com.hulian.oa.me;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.utils.SPUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//我的-》设置
public class L_SetActivity extends BaseActivity {
    //新消息通知
    @BindView(R.id.sw_select)
    Switch sw_select;
    @BindView(R.id.log_out)
    Button log_out;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_activity_set);
        ButterKnife.bind(this);

        //测试
        Boolean bool = sw_select.isChecked();
        sw_select.setChecked((Boolean) SPUtils.get(mContext,"message",bool));
   //     Toast.makeText(this, "选择" + bool, Toast.LENGTH_SHORT).show();
        sw_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
     //           Toast.makeText(L_SetActivity.this, "改变" + isChecked, Toast.LENGTH_SHORT).show();
                SPUtils.put(mContext,"message",isChecked);
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Toast.makeText(L_SetActivity.this,"退出登录",Toast.LENGTH_SHORT).show();
                NIMClient.getService(AuthService.class).logout();
                SPUtils.clear(mContext);
                exitApp(mContext);
            }
        });
    }

    @OnClick(R.id.rl_title)
    public void onViewClicked() {
        finish();
    }
}
