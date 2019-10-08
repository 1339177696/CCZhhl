package com.hulian.oa.message.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.hulian.oa.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageRemindActivity extends Activity {

    @BindView(R.id.message_remind)
    ImageView remind;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_remind);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.message_remind)
    public void onViewClicked() {
        finish();
    }
}
