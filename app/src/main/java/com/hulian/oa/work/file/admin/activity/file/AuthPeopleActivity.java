package com.hulian.oa.work.file.admin.activity.file;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.work.file.admin.adapter.Peopleadapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthPeopleActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private Peopleadapter adapter;
    @BindView(R.id.listview)
    ListView listview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_people);
        ButterKnife.bind(this);
        intRecycle();
    }

    private void intRecycle() {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(i + "111");
        }

        adapter = new Peopleadapter(AuthPeopleActivity.this, list);
        listview.setAdapter(adapter);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
