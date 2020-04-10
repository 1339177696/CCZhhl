package com.hulian.oa.me.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class Me_Schedule_RemindActivity extends BaseActivity {
    @BindView(R.id.tv_save)
    TextView tv_save;
    //不提醒
//    @BindView(R.id.tv_noremind)
//    RadioButton rb_noremind;
    //提前15分钟
    @BindView(R.id.tv_remind1)
    RadioButton rb_remind1;
    //提前1小时
    @BindView(R.id.tv_remind2)
    RadioButton rb_remind2;
    //提前3小时
    @BindView(R.id.tv_remind3)
     RadioButton rb_remind13;
    //提前1天
    @BindView(R.id.tv_remind4)
    RadioButton rb_remind14;
    @BindView(R.id.rg_remind)
    RadioGroup rg_remind;
    public Context mContext;
    private int[] array = new int[]{R.id.tv_remind1,R.id.tv_remind2,R.id.tv_remind3,R.id.tv_remind4};
    private String tixing = "不提醒";
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_schedule_remind);
        ButterKnife.bind(this);
        mContext = this;

    }
    @OnClick({R.id.tv_save,R.id.iv_back})
    public void onViewClicked(View view)
    {
        switch (view.getId()){
            case R.id.tv_save:
                Intent inent=new Intent();
                inent.putExtra("tixing",tixing);
                setResult(110,inent);
                finish();
                break;
            case R.id.iv_back:
                finish();
                break;
        }

    }

    @OnCheckedChanged({R.id.tv_remind1,R.id.tv_remind2,R.id.tv_remind3,R.id.tv_remind4})
    public void onRadioCheck(CompoundButton view, boolean ischanged) {
        switch (view.getId()){
            case R.id.tv_remind1:
                if (ischanged) {
                    System.out.println("======2");
                    tixing = "不提醒";
                }
                break;
            case R.id.tv_remind2:
                if (ischanged) {
                    System.out.println("======3");
                    tixing = "5分钟";
                }
                break;
            case R.id.tv_remind3:
                if (ischanged) {
                    System.out.println("======4");
                    tixing = "10分钟";
                }
                break;
            case R.id.tv_remind4:
                if (ischanged) {
                    System.out.println("=====5");
                    tixing = "30分钟";
                }
                break;
        }
    }

}
