package com.hulian.oa.me.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.utils.SPUtils;


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
        sw_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtils.put(mContext,"message",isChecked);
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
