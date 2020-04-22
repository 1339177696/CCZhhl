package com.hulian.oa.work.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.news.adapter.MyViewPageAdapter;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.activity.task.TaskLauncherActivity;
import com.hulian.oa.work.activity.task.l_fragment.CompletedTaskFragment;
import com.hulian.oa.work.activity.task.l_fragment.CopymeTaskFragment;
import com.hulian.oa.work.activity.task.l_fragment.LaunchTaskFragment;
import com.hulian.oa.work.activity.task.l_fragment.UndoneTaskFragment;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//任务协同
public class SecondTaskCoopActivity extends BaseActivity {
    //发起
    @BindView(R.id.tv_launch)
    LinearLayout tv_launch;
    @BindView(R.id.my_tablayout)
    TabLayout myTablayout;
    @BindView(R.id.my_viewpager)
    ViewPager myViewpager;
    @BindView(R.id.iv_back)
    RelativeLayout iv_back;
    Context mContext;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.work_coop);
        ButterKnife.bind(this);
        mContext = this;
        init();
    }
    private void init() {
        ArrayList<String> titleDatas = new ArrayList<>();
        titleDatas.add("我发起的");
        titleDatas.add("未完成的");
        titleDatas.add("已完成的");
        titleDatas.add("抄送我的");
        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new LaunchTaskFragment());
        fragmentList.add(new UndoneTaskFragment());
        fragmentList.add(new CompletedTaskFragment());
        fragmentList.add(new CopymeTaskFragment());
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        myViewpager.setAdapter(myViewPageAdapter);
        myTablayout.setupWithViewPager(myViewpager);
        myTablayout.setTabsFromPagerAdapter(myViewPageAdapter);
    }
    @OnClick({R.id.tv_launch,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_launch:
                startActivity(new Intent(mContext, TaskLauncherActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}