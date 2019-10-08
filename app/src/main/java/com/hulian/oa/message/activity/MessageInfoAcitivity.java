package com.hulian.oa.message.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageInfoAcitivity extends BaseActivity {

    @BindView(R.id.iv_news)
    ImageView ivNews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_info_acitivity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_news)
    public void onViewClicked() {
        finish();
    }
}
