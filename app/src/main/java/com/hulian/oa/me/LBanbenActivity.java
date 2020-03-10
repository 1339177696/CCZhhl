package com.hulian.oa.me;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.utils.SPUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;

import butterknife.ButterKnife;

/**
 * 创建：  qgl
 * 时间： 2020/03/10
 * 描述： 版本升级页面
 */
public class LBanbenActivity extends BaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lbanbenactivity);
        ButterKnife.bind(this);

    }

}
