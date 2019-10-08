package com.hulian.oa.work.file.admin.activity.task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.hulian.oa.MainActivity;
import com.hulian.oa.R;
import com.hulian.oa.work.file.admin.activity.document.DocumentLotusInfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskInfoActivity extends Activity {

    @BindView(R.id.work_coop)
    ImageView workcoop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_task_info);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.work_coop)
    public void onViewClicked() {
        Intent intent=new Intent(TaskInfoActivity.this, MainActivity.class);
        intent.putExtra("isgowork", true);
        startActivity(intent);
        finish();
    }
}