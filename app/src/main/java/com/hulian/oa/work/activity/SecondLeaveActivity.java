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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.news.adapter.MyViewPageAdapter;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.activity.leave.LeaveApplyforActivity;
import com.hulian.oa.work.activity.leave.l_fragment.LeaveApprovedFragment;
import com.hulian.oa.work.activity.leave.l_fragment.LeaveCopymeFragment;
import com.hulian.oa.work.activity.leave.l_fragment.LeaveLaunchFragment;
import com.hulian.oa.work.activity.leave.l_fragment.LeavePendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 请假总界面
 */
public class SecondLeaveActivity extends BaseActivity {
    @BindView(R.id.my_tablayout)
    TabLayout myTablayout;
    @BindView(R.id.my_viewpager)
    ViewPager myViewpager;
    Context mContext;
    @BindView(R.id.tv_apply)
    LinearLayout tv_apply;
    @BindView(R.id.leave_fragment)
    FrameLayout leave_fragment;
    private boolean perSenType = false;
    @BindView(R.id.view_zw)
    View view_zw;
    ArrayList<String> titleDatas   = new ArrayList<>();;
    ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    private ArrayList<Integer> imgList = new ArrayList<>();
    private List<TextView> numberList = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.work_leave_list_applyfor);
        ButterKnife.bind(this);
        mContext = this;
        //如果身份是普通员工不显示二级滑动
        if (SPUtils.get(this, "roleKey", "").toString().equals("common")){
            tv_apply.setVisibility(View.VISIBLE);
            titleDatas.add("我发起");
            fragmentList.add(new LeaveLaunchFragment());
            imgList.add(R.drawable.leave_my_faqi);
            leave_fragment.setVisibility(View.GONE);
            view_zw.setVisibility(View.GONE);
        }else {
            leave_fragment.setVisibility(View.VISIBLE);
            view_zw.setVisibility(View.VISIBLE);
            // 如果王婉娇的话有抄送我的
            if (SPUtils.get(this, "userId", "").toString().equals("184")){
                tv_apply.setVisibility(View.VISIBLE);
                titleDatas.add("我发起");
                fragmentList.add(new LeaveLaunchFragment());
                imgList.add(R.drawable.leave_my_faqi);
                titleDatas.add("抄送我的");
                fragmentList.add(new LeaveCopymeFragment());
                imgList.add(R.drawable.leave_chaosong);
            }
            if (SPUtils.get(this, "roleKey", "").toString().equals("boss")){
                tv_apply.setVisibility(View.GONE);
                perSenType = true;
                titleDatas.add("待审批");
                titleDatas.add("已审批");
                fragmentList.add(new LeavePendFragment());
                fragmentList.add(new LeaveApprovedFragment());
                imgList.add(R.drawable.leave_shenpi);
                imgList.add(R.drawable.leave_yishenpi);
            }else {
                tv_apply.setVisibility(View.VISIBLE);
                titleDatas.add("我发起");
                fragmentList.add(new LeaveLaunchFragment());
                imgList.add(R.drawable.leave_my_faqi);
                titleDatas.add("待审批");
                titleDatas.add("已审批");
                fragmentList.add(new LeavePendFragment());
                fragmentList.add(new LeaveApprovedFragment());
                imgList.add(R.drawable.leave_shenpi);
                imgList.add(R.drawable.leave_yishenpi);
            }
        }
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
            //存储后方便赋值
            numberList.add(number);
            title.setText(titleDatas.get(i));
            //标题左边选中和未选中的图片效果
            imageView.setBackground(ContextCompat.getDrawable(this, imgList.get(i)));

        }
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

    public void setListSize(int size, int position) {
        if (perSenType){
            position = position - 1;
        }
        numberList.get(position).setText(size + "");
    }
}
