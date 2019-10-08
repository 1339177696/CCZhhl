package com.hulian.oa.work.file.admin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
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
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;

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
        myViewpager.setAdapter(myViewPageAdapter);
        myTablayout.setupWithViewPager(myViewpager);
        myTablayout.setTabsFromPagerAdapter(myViewPageAdapter);
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
