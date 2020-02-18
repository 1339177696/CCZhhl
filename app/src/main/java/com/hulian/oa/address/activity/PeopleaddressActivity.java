package com.hulian.oa.address.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.work.file.admin.activity.instruct.InstructBackActivity;
import com.hulian.oa.work.file.admin.activity.instruct.InstructReceiverActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PeopleaddressActivity extends BaseActivity {

    @BindView(R.id.people_address)
    ImageView people;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_address);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.people_address)
    public void onViewClicked() {
        startActivity(new Intent(PeopleaddressActivity.this, PeopleaddressInforActivity.class));
    }

}
