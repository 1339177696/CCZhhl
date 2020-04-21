package com.hulian.oa.work.activity.attendancestatistics;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.news.adapter.MyViewPageAdapter;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.fragment.DepartmentattendanceFragment;
import com.hulian.oa.work.fragment.MaintenanceFragment;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：qgl 时间： 2020/4/20 10:35
 * Describe: 考勤统计总界面
 */
public class AnaestheticsActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.my_tablayout)
    TabLayout myTablayout;
    @BindView(R.id.my_viewpager)
    ViewPager myViewpager;
    @BindView(R.id.attendan_frame)
    FrameLayout attendanFrame;
    @BindView(R.id.viewpager_frame)
    FrameLayout viewpagerFrame;
    @BindView(R.id.view_zw)
    View view_zw;
    private boolean type ;
    ArrayList<String> titleDatas = new ArrayList<>();
    ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.attendancestatisticsactivity);
        ButterKnife.bind(this);
        //如果身份是普通员工不显示二级滑动
        if (SPUtils.get(this, "roleKey", "").toString().contains("common")){
            view_zw.setVisibility(View.GONE);
            attendanFrame.setVisibility(View.GONE);
            type = true;
        }else {
            view_zw.setVisibility(View.VISIBLE);
            attendanFrame.setVisibility(View.VISIBLE);
            type = false;
        }
        titleDatas.add("我的考勤");
        fragmentList.add(new MaintenanceFragment());
        if (SPUtils.get(this, "roleKey", "").toString().contains("boss")||
                SPUtils.get(this, "roleKey", "").toString().contains("synthesizeLead")){
            titleDatas.add("单位考勤");
            fragmentList.add(new DepartmentattendanceFragment());
        }else if (SPUtils.get(this, "roleKey", "").toString().contains("eachLead")){
            titleDatas.add("单位考勤");
            fragmentList.add(new DepartmentattendanceFragment());
        }
        init();
    }

    private void init() {
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        myTablayout.setSelectedTabIndicator(0);
        myViewpager.setAdapter(myViewPageAdapter);
        myTablayout.setupWithViewPager(myViewpager);
        if (!type) {
            myTablayout.getTabAt(0).setCustomView(R.layout.item_bx_tab_f);
            myTablayout.getTabAt(1).setCustomView(R.layout.item_bx_tab_s);
            TextView textView = myTablayout.getTabAt(0).getCustomView().findViewById(R.id.tv_title);
            ImageView imageView = myTablayout.getTabAt(0).getCustomView().findViewById(R.id.iv_pic);
            TextView textView1 = myTablayout.getTabAt(1).getCustomView().findViewById(R.id.tv_title);
            ImageView imageView1 = myTablayout.getTabAt(1).getCustomView().findViewById(R.id.iv_pic);
            textView.setText("我的考勤");
            if (SPUtils.get(this, "roleKey", "").toString().contains("boss")||
                    SPUtils.get(this, "roleKey", "").toString().contains("synthesizeLead")){
                textView1.setText("单位考勤");
            }else if (SPUtils.get(this, "roleKey", "").toString().contains("eachLead")){
                textView1.setText("单位考勤");
            }
            //标题左边选中和未选中的图片效果
            imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.kaoqin_tj_me));
            if (SPUtils.get(this, "roleKey", "").toString().contains("boss")||
                    SPUtils.get(this, "roleKey", "").toString().contains("synthesizeLead")){
                imageView1.setBackground(ContextCompat.getDrawable(this, R.drawable.kaoqin_tj_boss));
            }else if (SPUtils.get(this, "roleKey", "").toString().contains("eachLead")){
                imageView1.setBackground(ContextCompat.getDrawable(this, R.drawable.kaoqin_tj_eachlead));
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
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
