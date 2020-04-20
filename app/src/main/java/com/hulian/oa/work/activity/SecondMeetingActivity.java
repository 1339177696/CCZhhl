package com.hulian.oa.work.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    private ArrayList<String>titleDatas = new ArrayList<>();
    private ArrayList<Fragment>fragmentList = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.work_meting);
        ButterKnife.bind(this);
        mContext = this;

        titleDatas.add("我发起的");
        titleDatas.add("我参与的");
        fragmentList.add(new MeetLaunchFragment());
        fragmentList.add(new MeetReceiverFragment());
        init();
    }
    private void init() {
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        myTablayout.setSelectedTabIndicator(0);
        myViewpager.setAdapter(myViewPageAdapter);
        myTablayout.setupWithViewPager(myViewpager);
        myTablayout.getTabAt(0).setCustomView(R.layout.item_bx_tab_f);
        myTablayout.getTabAt(1).setCustomView(R.layout.item_bx_tab_s);
        TextView textView = myTablayout.getTabAt(0).getCustomView().findViewById(R.id.tv_title);
        ImageView imageView = myTablayout.getTabAt(0).getCustomView().findViewById(R.id.iv_pic);
        TextView textView1 = myTablayout.getTabAt(1).getCustomView().findViewById(R.id.tv_title);
        ImageView imageView1 = myTablayout.getTabAt(1).getCustomView().findViewById(R.id.iv_pic);
        textView.setText("我发起的");
        textView1.setText("我参与的");
        //标题左边选中和未选中的图片效果
        imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.leave_my_faqi));
        imageView1.setBackground(ContextCompat.getDrawable(this, R.drawable.meeting_tybe));
        //初始化替换后的文字和图片
        myTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tv_title).setSelected(true);
                myViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tv_title).setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
