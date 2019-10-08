package com.hulian.oa.work.file.admin.activity.meeting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 会议修改
 */
public class MeetingAmendActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_meeting_amend);
        ButterKnife.bind(this);
    }

    @OnClick({})
    public void onViewClicked() {
        startActivity(new Intent(MeetingAmendActivity.this, MeetingSigninActivity.class));
    }
}
