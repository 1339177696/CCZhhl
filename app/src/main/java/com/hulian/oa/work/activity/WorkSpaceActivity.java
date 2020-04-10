package com.hulian.oa.work.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkSpaceActivity extends BaseActivity {

    @BindView(R.id.rl_title)
    RelativeLayout ivBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_space);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
