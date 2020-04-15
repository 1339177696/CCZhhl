package com.hulian.oa.work.file.admin.activity;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.news.adapter.MyViewPageAdapter;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.file.admin.activity.leave.LeaveApplyforActivity;
import com.hulian.oa.work.file.admin.activity.leave.l_fragment.LeaveApprovedFragment;
import com.hulian.oa.work.file.admin.activity.leave.l_fragment.LeaveCopymeFragment;
import com.hulian.oa.work.file.admin.activity.leave.l_fragment.LeaveLaunchFragment;
import com.hulian.oa.work.file.admin.activity.leave.l_fragment.LeavePendFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 请假审批
 * 领导页面
 */
public class SecondLeaveActivity extends BaseActivity {
    @BindView(R.id.my_tablayout)
    TabLayout myTablayout;
    @BindView(R.id.my_viewpager)
    ViewPager myViewpager;
    Context mContext;
    @BindView(R.id.tv_apply)
    LinearLayout tv_apply;
    ArrayList<String> titleDatas   = new ArrayList<>();;
    ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.work_leave_list_applyfor);
        ButterKnife.bind(this);
        mContext = this;
        //领导
        if (SPUtils.get(mContext, "isLead", "").equals("0")) {
            tv_apply.setVisibility(View.GONE);
            titleDatas.add("待审批");
            titleDatas.add("已审批");
            fragmentList.add(new LeavePendFragment());
            fragmentList.add(new LeaveApprovedFragment());
        }
        //员工
        else {
            titleDatas.add("我发起的");
            titleDatas.add("抄送我的");
            tv_apply.setVisibility(View.VISIBLE);
            fragmentList.add(new LeaveLaunchFragment());
            fragmentList.add(new LeaveCopymeFragment());
        }
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
        if (SPUtils.get(mContext, "isLead", "").equals("0")) {
            textView.setText("待审批");
            textView1.setText("已审批");
            //标题左边选中和未选中的图片效果
            imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.leave_shenpi));
            imageView1.setBackground(ContextCompat.getDrawable(this, R.drawable.leave_yishenpi));
        }else {
            textView.setText("我发起的");
            textView1.setText("抄送我的");
            //标题左边选中和未选中的图片效果
            imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.leave_my_faqi));
            imageView1.setBackground(ContextCompat.getDrawable(this, R.drawable.leave_chaosong));
        }

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
    @OnClick({R.id.tv_apply,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_apply:
                startActivity(new Intent(mContext, LeaveApplyforActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
