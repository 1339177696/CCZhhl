package com.hulian.oa.me.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：qgl 时间： 2020/6/4 13:50
 * Describe:
 */
public class MeForgetPassActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.tv_me_forget)
    TextView tvMeForget;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meforgetpassactivity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back,R.id.tv_me_forget})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_me_forget:
                countDown();
                Log.e("发送短信","Yes");
                break;
        }

    }


    /**
     * 倒计时显示
     */
    private void countDown() {
        CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvMeForget.setEnabled(false);
                tvMeForget.setText("已发送(" + millisUntilFinished / 1000 + ")");
            }

            @Override
            public void onFinish() {
                tvMeForget.setEnabled(true);
                tvMeForget.setText("重新获取验证码");
            }
        }.start();

    }

}
