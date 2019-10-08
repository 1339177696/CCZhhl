package com.hulian.oa.address.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.MainActivity;
import com.hulian.oa.R;
import com.hulian.oa.work.file.admin.activity.instruct.InstructBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PeopleaddressInforActivity extends BaseActivity {

    @BindView(R.id.people_address)
    ImageView people;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_people_infor);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.people_address)
    public void onViewClicked() {
        Intent intent=new Intent(PeopleaddressInforActivity.this, MainActivity.class);
        intent.putExtra("isgoaddress", true);
        startActivity(intent);
        finish();
    }
}
