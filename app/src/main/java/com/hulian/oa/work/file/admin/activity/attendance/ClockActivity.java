package com.hulian.oa.work.file.admin.activity.attendance;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;

/**
 * 作者：qgl 时间： 2020/4/16 11:10
 * Describe:
 */
public class ClockActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clockactivity);
    }
}
