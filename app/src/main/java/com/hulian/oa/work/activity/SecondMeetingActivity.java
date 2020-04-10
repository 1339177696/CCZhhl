package com.hulian.oa.work.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.news.adapter.MyViewPageAdapter;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.activity.meeting.MeetingSponsorActivity;
import com.hulian.oa.work.activity.meeting.l_fragment.MeetLaunchFragment;
import com.hulian.oa.work.activity.meeting.l_fragment.MeetReceiverFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SecondMeetingActivity extends BaseActivity {

    Unbinder unbinder;
    //发起
    @BindView(R.id.my_tablayout)
    TabLayout myTablayout;
    @BindView(R.id.my_viewpager)
    ViewPager myViewpager;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    public Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.work_meting);
        ButterKnife.bind(this);
        mContext = this;
        init();
    }
    private void init() {
        ArrayList<String> titleDatas = new ArrayList<>();
        titleDatas.add("我发起的");
        titleDatas.add("我接收的");
        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new MeetLaunchFragment());
        fragmentList.add(new MeetReceiverFragment());
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        myViewpager.setAdapter(myViewPageAdapter);
        myTablayout.setupWithViewPager(myViewpager);
    }
    @OnClick({R.id.tv_launch,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_launch:
                startActivity(new Intent(mContext, MeetingSponsorActivity.class));
                break;
            case R.id.iv_back:
              finish();
                break;
        }
    }
}
