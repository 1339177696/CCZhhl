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
import com.hulian.oa.work.activity.expense.ExpenseApplyForActivity;
import com.hulian.oa.work.activity.expense.l_fragment.ExpenseApprovedFragment;
import com.hulian.oa.work.activity.expense.l_fragment.ExpenseCopymeFragment;
import com.hulian.oa.work.activity.expense.l_fragment.ExpenseLaunchFragment;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 报销审批
 */
public class SecondExpenseActivity extends BaseActivity {


    @BindView(R.id.my_tablayout)
    TabLayout myTablayout;
    @BindView(R.id.my_viewpager)
    ViewPager myViewpager;
    Context mContext;
    @BindView(R.id.tv_apply)
    TextView tv_apply;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    ArrayList<String> titleDatas   = new ArrayList<>();;
    ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

    @BindView(R.id.tv_baoxiao)
    TextView tv_baoxiao;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.work_expense_list_apply);
        ButterKnife.bind(this);
        mContext = this;
        //领导
        String isLeader = (String) SPUtils.get(mContext, "isLead", "");
        isLeader = "0";
        if (isLeader.equals("0")) {
//            tv_apply.setVisibility(View.GONE);
            tv_apply.setVisibility(View.VISIBLE);
//            titleDatas.add("待审批");
//            titleDatas.add("已审批");
//            titleDatas.hb_add("我发起的");
//            titleDatas.hb_add("我审批的");
//            titleDatas.hb_add("抄送我的");
            fragmentList.add(new ExpenseLaunchFragment());
//            fragmentList.hb_add(new ExpensePendFragment());
            fragmentList.add(new ExpenseApprovedFragment());
        }
        //员工
        else {
//            titleDatas.add("我发起的");
//            titleDatas.add("抄送我的");
            tv_apply.setVisibility(View.VISIBLE);
            fragmentList.add(new ExpenseLaunchFragment());
            fragmentList.add(new ExpenseCopymeFragment());
        }
        init(isLeader);
    }
    private void init(String isLeader) {
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        myTablayout.setSelectedTabIndicator(0);
        myViewpager.setAdapter(myViewPageAdapter);
        myTablayout.setupWithViewPager(myViewpager);
        //替换tab中原有的样式(职工)
        myTablayout.getTabAt(0).setCustomView(R.layout.item_bx_tab_f);
        myTablayout.getTabAt(1).setCustomView(R.layout.item_bx_tab_s);
        //初始化替换后的文字和图片
        TextView textView = myTablayout.getTabAt(0).getCustomView().findViewById(R.id.tv_title);
        ImageView imageView = myTablayout.getTabAt(0).getCustomView().findViewById(R.id.iv_pic);
        TextView textView1 = myTablayout.getTabAt(1).getCustomView().findViewById(R.id.tv_title);
        ImageView imageView1 = myTablayout.getTabAt(1).getCustomView().findViewById(R.id.iv_pic);
        if (isLeader.equals("0")){//0 代表领导
            textView.setText("待审批");
            textView1.setText("已审批");
        }else{
            textView.setText("我发起的");
            textView1.setText("抄送我的");
        }
        //标题左边选中和未选中的图片效果
        imageView.setBackground(ContextCompat.getDrawable(this,R.drawable.baoxiao_pic_f));
        imageView1.setBackground(ContextCompat.getDrawable(this,R.drawable.baoxiao_pic_s));
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
    @OnClick({R.id.tv_apply,R.id.iv_back,R.id.tv_baoxiao})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_apply://报销申请
                startActivity(new Intent(mContext, ExpenseApplyForActivity.class));
//                startActivity(new Intent(mContext, ExpenseDetailsActivity.class));
                break;
//            case R.id.iv_back:
//                this.finish();
//                break;
            case R.id.tv_baoxiao://报销管理标题
//                startActivity(new Intent(mContext, ExpenseApplyForActivity.class));
                this.finish();
                break;
        }
    }
}
