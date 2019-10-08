package com.hulian.oa.work.file.admin.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.Fab;
import com.hulian.oa.bean.Fab2;
import com.hulian.oa.news.adapter.MyViewPageAdapter;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.file.admin.activity.document.LauncherDocumentActivity;
import com.hulian.oa.work.file.admin.activity.document.l_fragment.L_ApprovedFragment;
import com.hulian.oa.work.file.admin.activity.document.l_fragment.L_PendFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

//任务-》公文流转
public class SecondDocumentActivity extends BaseActivity {

    Unbinder unbinder;
    //发起
    @BindView(R.id.tv_launch)
    LinearLayout tv_launch;
    @BindView(R.id.my_tablayout)
    TabLayout myTablayout;
    @BindView(R.id.my_viewpager)
    ViewPager myViewpager;
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.tv_mengban)
    TextView tvMengban;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    ArrayList<String> titleDatas = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        EventBus.getDefault().register(this);
        setContentView(R.layout.work_document);
        ButterKnife.bind(this);
        //领导
        if (SPUtils.get(mContext, "isLead", "").equals("0"))
        {
            tv_launch.setVisibility(View.GONE);
            titleDatas.add("待审批");
            titleDatas.add("已审批");
        }
        //员工
        else {
            titleDatas.add("审批中");
            titleDatas.add("审批完成");
            tv_launch.setVisibility(View.VISIBLE);
        }
        init();
    }

    private void init() {
        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new L_PendFragment());
        fragmentList.add(new L_ApprovedFragment());
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
//        myTablayout.setSelectedTabIndicator(0);
        myViewpager.setAdapter(myViewPageAdapter);
        myTablayout.setupWithViewPager(myViewpager);
    }

    @OnClick(R.id.tv_launch)
    public void onViewClicked() {
//            startActivity(new Intent(SecondDocumentActivity.this, LauncherDocumentActivity.class));
        startActivity(new Intent(SecondDocumentActivity.this, LauncherDocumentActivity.class));
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked1() {
        finish();
    }
    public void onEventMainThread(Fab event) {
        if (event.getTag().equals("0")) {
            tvMengban.setVisibility(View.GONE);
        } else {
            tvMengban.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.tv_mengban)
    public void onViewClicked2() {
        Fab2 fab2 = new Fab2();
        fab2.setTag("0");
        EventBus.getDefault().post(fab2);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}