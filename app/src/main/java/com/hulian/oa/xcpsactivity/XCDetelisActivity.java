package com.hulian.oa.xcpsactivity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.views.DragFloatActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qgl on 2020/3/12 10:15.
 */
public class XCDetelisActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.li_1)
    LinearLayout li1;
    @BindView(R.id.li_2)
    LinearLayout li2;
    @BindView(R.id.li_3)
    LinearLayout li3;
    @BindView(R.id.li_4)
    LinearLayout li4;
    @BindView(R.id.li_5)
    LinearLayout li5;
    @BindView(R.id.li_6)
    LinearLayout li6;
    @BindView(R.id.li_7)
    LinearLayout li7;
    @BindView(R.id.li_8)
    LinearLayout li8;
    @BindView(R.id.li_9)
    LinearLayout li9;
    @BindView(R.id.li_10)
    LinearLayout li10;
    @BindView(R.id.li_11)
    LinearLayout li11;
    @BindView(R.id.li_12)
    LinearLayout li12;
    @BindView(R.id.floatbutton)
    DragFloatActionButton floatbutton;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xcdetelisactivity);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.rl_title, R.id.li_1, R.id.li_2, R.id.li_3, R.id.li_4, R.id.li_5, R.id.li_6, R.id.li_7, R.id.li_8, R.id.li_9, R.id.li_10, R.id.li_11, R.id.li_12})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_title:
                finish();
                break;
            case R.id.li_1:
                break;
            case R.id.li_2:
                break;
            case R.id.li_3:
                break;
            case R.id.li_4:
                break;
            case R.id.li_5:
                break;
            case R.id.li_6:
                break;
            case R.id.li_7:
                break;
            case R.id.li_8:
                break;
            case R.id.li_9:
                break;
            case R.id.li_10:
                break;
            case R.id.li_11:
                break;
            case R.id.li_12:
                break;
        }
    }
}
