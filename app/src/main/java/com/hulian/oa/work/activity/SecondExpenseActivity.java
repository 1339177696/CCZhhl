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
import com.hulian.oa.work.activity.expense.l_fragment.ExpensePendFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 报销管理
 */
public class SecondExpenseActivity extends BaseActivity {
    @BindView(R.id.my_tablayout)
    TabLayout myTablayout;
    @BindView(R.id.my_viewpager)
    ViewPager myViewpager;
    Context mContext;
    @BindView(R.id.tv_apply)
    TextView tv_apply;
    ArrayList<String> titleDatas   = new ArrayList<>();;
    ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    private ArrayList<Integer> imgList = new ArrayList<>();
    private List<TextView> numberList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.work_expense_list_apply);
        ButterKnife.bind(this);
        mContext = this;
        //领导
        String isLeader = "";
        if (SPUtils.get(this, "roleKey", "").toString().contains("boss")|| SPUtils.get(this, "roleKey", "").toString().contains("synthesizeLead")) {
            isLeader = "0";
        }else {
            isLeader = "1";
        }

        if (isLeader.equals("0")) {
            titleDatas.add("我发起的");
            titleDatas.add("待审批");
            titleDatas.add("已审批");
            fragmentList.add(new ExpenseLaunchFragment());
            fragmentList.add(new ExpensePendFragment());
            fragmentList.add(new ExpenseApprovedFragment());
            imgList.add(R.drawable.baoxiao_pic_f);
            imgList.add(R.drawable.baoxiao_pic_s);
            imgList.add(R.drawable.baoxiao_pic_s);
        }
        //员工
        else {
            titleDatas.add("我发起的");
            titleDatas.add("抄送我的");
            tv_apply.setVisibility(View.VISIBLE);
            fragmentList.add(new ExpenseLaunchFragment());
            fragmentList.add(new ExpenseCopymeFragment());
            imgList.add(R.drawable.baoxiao_pic_f);
            imgList.add(R.drawable.baoxiao_pic_s);
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
            case R.id.tv_apply://报销申请
                startActivity(new Intent(mContext, ExpenseApplyForActivity.class));
                break;
            case R.id.iv_back:
                this.finish();
                break;

        }
    }


    public void setListSize(int size, int position) {
        numberList.get(position).setText(size + "");
    }
}
