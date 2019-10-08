package com.hulian.oa.message.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.hulian.oa.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeaaageAdressActivity  extends Activity {

    @BindView(R.id.message_address)
    ImageView address;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_address);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.message_address)
    public void onViewClicked() {
        finish();
    }
}
