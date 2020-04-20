package com.hulian.oa.work.activity.video;

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

import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.news.adapter.MyViewPageAdapter;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.activity.ScreenReportActivity;
import com.hulian.oa.work.activity.WorkReportActivity;
import com.hulian.oa.work.fragment.ReadReportFragment;
import com.hulian.oa.work.fragment.WriteReportFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 陈泽宇 on 2020/4/20
 * Describe:视频会议
 */
public class VideoConferenceActivity extends BaseActivity {


    @BindView(R.id.my_tablayout)
    TabLayout myTablayout;
    @BindView(R.id.my_viewpager)
    ViewPager myViewpager;
    private Context mContext;

    private ArrayList<String> titleDatas = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    private ArrayList<Integer> imgList = new ArrayList<Integer>();
    private 


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.activity_work_report);
        ButterKnife.bind(this);
        mContext = this;
        titleDatas.add("写汇报");
        titleDatas.add("看汇报");
        fragmentList.add(new WriteReportFragment());
        fragmentList.add(new ReadReportFragment());

        imgList.add(R.drawable.vc_pic_1);
        imgList.add(R.drawable.vc_pic_2);
        init();
    }

    private void init() {
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        myTablayout.setSelectedTabIndicator(0);
        myViewpager.setAdapter(myViewPageAdapter);
        myTablayout.setupWithViewPager(myViewpager);
        //替换tab中原有的样式
        for (int i = 0; i < titleDatas.size(); i++) {
            myTablayout.getTabAt(i).setCustomView(R.layout.item_sphy_tab);
            TextView title = myTablayout.getTabAt(i).getCustomView().findViewById(R.id.tv_title);
            ImageView imageView = myTablayout.getTabAt(i).getCustomView().findViewById(R.id.iv_pic);
            TextView number = myTablayout.getTabAt(i).getCustomView().findViewById(R.id.number);
            title.setText(titleDatas.get(i));
            //标题左边选中和未选中的图片效果
            imageView.setBackground(ContextCompat.getDrawable(this, imgList.get(i)));

        }



//        myTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                tab.getCustomView().findViewById(R.id.tv_title).setSelected(true);
//                myViewpager.setCurrentItem(tab.getPosition());
//                switch (tab.getPosition()) {
//                    case 0:
//                        tv_apply.setVisibility(View.INVISIBLE);
//                        break;
//
//                    case 1:
//                        tv_apply.setVisibility(View.VISIBLE);
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                tab.getCustomView().findViewById(R.id.tv_title).setSelected(false);
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

    }

    @OnClick({R.id.tv_apply, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
