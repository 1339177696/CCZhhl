package com.hulian.oa.agency.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgencyInfoActivity extends BaseActivity {
    @BindView(R.id.info_agency)
    ImageView infoAgency;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_info_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.info_agency)
    public void onViewClicked() {
        finish();
    }
}
