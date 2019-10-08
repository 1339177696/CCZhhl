package com.hulian.oa.work.file.admin.activity.file;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MasterAuthInfoActivity extends BaseActivity {
    private OptionsPickerView timePicker;//时间;
    @BindView(R.id.tv_sel_peo)
    TextView tvSelPeo;
    @BindView(R.id.tv_sel_time)
    TextView tvSelTime;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    List<String> list=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_auth_info);
        ButterKnife.bind(this);
        initTime();
    }

    private void initTime() {
        list.add("一天");
        list.add("一周");
        list.add("一月");
        timePicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvSelTime.setText(list.get(options1));
            }
        }).setTitleText("请选择时间").setContentTextSize(22).setTitleSize(22).setSubCalSize(21).build();
        timePicker.setPicker(list);
    }

    @OnClick({R.id.tv_sel_peo, R.id.tv_sel_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sel_peo:
                startActivity(new Intent(MasterAuthInfoActivity.this, AuthPeopleActivity.class));
                break;
            case R.id.tv_sel_time:
                timePicker.show();
                break;
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
