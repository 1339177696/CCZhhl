package com.hulian.oa.work.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.work.activity.file.Admin_boxActivity;
import com.hulian.oa.work.activity.file.Admin_fileActivity;
import com.hulian.oa.work.activity.file.Admin_logsActivity;
import com.hulian.oa.work.activity.file.Admin_messageActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondFileActicity extends BaseActivity {
    /**
     * 文件
     */
    @BindView(R.id.tv_file)
    TextView tvFile;
    @BindView(R.id.tv_file_box)
    TextView tvFileBox;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_logs)
    TextView tvLogs;
    @BindView(R.id.iv_back)
    ImageView ivBack;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fra_admin);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.tv_file, R.id.tv_file_box, R.id.tv_message, R.id.tv_logs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_file:
                startActivity(new Intent(SecondFileActicity.this, Admin_fileActivity.class));
                break;
            case R.id.tv_file_box:
                startActivity(new Intent(SecondFileActicity.this, Admin_boxActivity.class));
                break;
            case R.id.tv_message:
                startActivity(new Intent(SecondFileActicity.this, Admin_messageActivity.class));
                break;
            case R.id.tv_logs:
                startActivity(new Intent(SecondFileActicity.this, Admin_logsActivity.class));
                break;
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
