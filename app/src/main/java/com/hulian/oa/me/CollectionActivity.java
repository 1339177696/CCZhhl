package com.hulian.oa.me;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.me.l_fragment.L_CollectionNewsFragment;
import com.hulian.oa.me.l_fragment.L_CollectionNoticeFragment;
import com.hulian.oa.news.adapter.MyViewPageAdapter;
import com.hulian.oa.news.fragment.News_1_Fragment;
import com.hulian.oa.news.fragment.News_2_Fragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

//我的-》收藏
public class CollectionActivity extends BaseActivity {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Unbinder unbinder;
    @BindView(R.id.my_tablayout)
    TabLayout myTablayout;
    @BindView(R.id.my_viewpager)
    ViewPager myViewpager;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_collection);
        ButterKnife.bind(this);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }

    private void init() {
        ArrayList<String> titleDatas = new ArrayList<>();
        titleDatas.add("新闻");
        titleDatas.add("通告");
        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new L_CollectionNewsFragment());
        fragmentList.add(new L_CollectionNoticeFragment());
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        myTablayout.setSelectedTabIndicator(0);
        myViewpager.setAdapter(myViewPageAdapter);
        myTablayout.setupWithViewPager(myViewpager);
        //        myTablayout.setTabsFromPagerAdapter(myViewPageAdapter);
    }
}
