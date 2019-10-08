package com.hulian.oa.work.file.admin.activity.file;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.work.file.admin.adapter.Messageadapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Admin_messageActivity extends BaseActivity {
    private Messageadapter adapter;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.listview)
    ListView listview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_message);
        ButterKnife.bind(this);
        intRecycle();
    }
    private void intRecycle() {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(i + "111");
        }

        adapter = new Messageadapter(this, list);
        listview.setAdapter(adapter);
    }
    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
