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
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.fragment.ReadReportFragment;
import com.hulian.oa.work.fragment.WriteReportFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 陈泽宇 on 2020/3/10.
 * Describe:工作汇报
 */
public class WorkReportActivity extends BaseActivity {

    @BindView(R.id.my_tablayout)
    TabLayout myTablayout;
    @BindView(R.id.my_viewpager)
    ViewPager myViewpager;
    Context mContext;
    @BindView(R.id.tv_apply)
    ImageView tv_apply;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    ArrayList<String> titleDatas   = new ArrayList<>();;
    ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.activity_work_report);
        ButterKnife.bind(this);
        mContext = this;
        //领导
        if (SPUtils.get(this, "roleKey", "").toString().contains("boss")) {//boss没有写日报权限
            tv_apply.setVisibility(View.VISIBLE);
            titleDatas.add("看汇报");
            fragmentList.add(new ReadReportFragment());
        }
        //员工
        else {
            titleDatas.add("写汇报");
            titleDatas.add("看汇报");
            fragmentList.add(new WriteReportFragment());
            fragmentList.add(new ReadReportFragment());
        }
        init();
    }
    private void init() {
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        myTablayout.setSelectedTabIndicator(0);
        myViewpager.setAdapter(myViewPageAdapter);
        myTablayout.setupWithViewPager(myViewpager);
        if (SPUtils.get(this, "roleKey", "").toString().contains("boss")) {//boss没有写日报权限
            //领导
            myTablayout.getTabAt(0).setCustomView(R.layout.item_bx_tab_f);
            TextView textView = myTablayout.getTabAt(0).getCustomView().findViewById(R.id.tv_title);
            ImageView imageView = myTablayout.getTabAt(0).getCustomView().findViewById(R.id.iv_pic);
            textView.setText("看汇报");
            imageView.setBackground(ContextCompat.getDrawable(this,R.drawable.huibao_pic_f));
        }else {
            //替换tab中原有的样式(职工)
            myTablayout.getTabAt(0).setCustomView(R.layout.item_bx_tab_f);
            myTablayout.getTabAt(1).setCustomView(R.layout.item_bx_tab_s);
            TextView textView = myTablayout.getTabAt(0).getCustomView().findViewById(R.id.tv_title);
            ImageView imageView = myTablayout.getTabAt(0).getCustomView().findViewById(R.id.iv_pic);
            TextView textView1 = myTablayout.getTabAt(1).getCustomView().findViewById(R.id.tv_title);
            ImageView imageView1 = myTablayout.getTabAt(1).getCustomView().findViewById(R.id.iv_pic);
            textView.setText("写汇报");
            textView1.setText("看汇报");
            //标题左边选中和未选中的图片效果
            imageView.setBackground(ContextCompat.getDrawable(this,R.drawable.huibao_pic_f));
            imageView1.setBackground(ContextCompat.getDrawable(this,R.drawable.huibao_pic_s));
        }

        //初始化替换后的文字和图片

        myTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tv_title).setSelected(true);
                myViewpager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                        tv_apply.setVisibility(View.INVISIBLE);
                        break;

                    case 1:
                        tv_apply.setVisibility(View.VISIBLE);
                        break;
                }
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
    @OnClick({R.id.tv_apply,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_apply://筛选
                startActivity(new Intent(WorkReportActivity.this,ScreenReportActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}