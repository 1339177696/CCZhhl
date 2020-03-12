package com.hulian.oa.xcpsactivity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;

import butterknife.ButterKnife;

/**
 * Created by qgl on 2020/3/12 10:15.
 */
public class XCDetelisActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xcdetelisactivity);
        ButterKnife.bind(this);
    }



}
