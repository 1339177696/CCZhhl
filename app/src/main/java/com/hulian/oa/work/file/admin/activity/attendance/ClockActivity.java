package com.hulian.oa.work.file.admin.activity.attendance;

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

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.news.adapter.MyViewPageAdapter;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.file.admin.activity.ScreenReportActivity;
import com.hulian.oa.work.file.admin.activity.WorkReportActivity;
import com.hulian.oa.work.fragment.CalendarFragment;
import com.hulian.oa.work.fragment.ClockFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by qgl on 2020/3/17 16:34.
 * 打卡总界面
 */
public class ClockActivity extends BaseActivity {
    @BindView(R.id.my_tablayout)
    TabLayout myTablayout;
    @BindView(R.id.my_viewpager)
    ViewPager myViewpager;
    @BindView(R.id.tv_close)
    TextView tVclose;

    ArrayList<String> titleDatas = new ArrayList<>();
    ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.clockactivity);
        ButterKnife.bind(this);
        if (SPUtils.get(mContext, "roleKey", "").equals("boos")) {
            tVclose.setVisibility(View.VISIBLE);
        } else {
            tVclose.setVisibility(View.GONE);
        }
        titleDatas.add("考勤打卡");
        titleDatas.add("打卡日历");
        fragmentList.add(new ClockFragment());
        fragmentList.add(new CalendarFragment());
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
        textView.setText("考勤打卡");
        textView1.setText("打卡日历");
        //标题左边选中和未选中的图片效果
        imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.huibao_pic_f));
        imageView1.setBackground(ContextCompat.getDrawable(this, R.drawable.huibao_pic_s));
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

    @OnClick({R.id.tv_close,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_close:// 下属打卡
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

}
