package com.hulian.oa.work.activity.attendancestatistics.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hulian.oa.R;
import com.hulian.oa.news.adapter.MyViewPageAdapter;
import com.othershe.calendarview.utils.CalendarUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者：qgl 时间： 2020/4/20 11:12
 * Describe:部门考勤
 */
public class DepartmentattendanceFragment extends Fragment {
    @BindView(R.id.current_time)
    TextView currentTime;
    @BindView(R.id.current_time_liner)
    LinearLayout currentTimeLiner;
    private View view;
    private Unbinder unbinder;
    @BindView(R.id.my_tablayout)
    TabLayout myTablayout;
    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    private ArrayList<String> titleDatas = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private List<TextView> numberList = new ArrayList<>();
    private String startTimeStr = "";
    private boolean btn = true;
    private int[] cDate = CalendarUtil.getCurrentDate();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.departmentattendancefragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        currentTime.setText("" + cDate[0] + "-" + cDate[1]);

        titleDatas.add("正常");
        titleDatas.add("外勤");
        titleDatas.add("迟到");
        titleDatas.add("缺卡");
        titleDatas.add("请假");
        titleDatas.add("早退");
        fragmentList.add(DepartListFragment.newInstance("0"));
        fragmentList.add(DepartListFragment.newInstance("1"));
        fragmentList.add(DepartListFragment.newInstance("2"));
        fragmentList.add(DepartListFragment.newInstance("3"));
        fragmentList.add(DepartListFragment.newInstance("4"));
        fragmentList.add(DepartListFragment.newInstance("5"));
        init();
        return view;

    }

    private void init() {
        // 在fragment中嵌套fragment使用getChildFragmentManager();
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getChildFragmentManager(), titleDatas, fragmentList);
        myTablayout.setSelectedTabIndicator(0);
        mainViewpager.setAdapter(myViewPageAdapter);
        mainViewpager.setOffscreenPageLimit(6);//ViewPager设置预加载页面的个数方法
        myTablayout.setupWithViewPager(mainViewpager);
        //替换tab中原有的样式
        for (int i = 0; i < titleDatas.size(); i++) {
            myTablayout.getTabAt(i).setCustomView(R.layout.item_kqtj_tab);
            TextView title = myTablayout.getTabAt(i).getCustomView().findViewById(R.id.tv_title);
            TextView number = myTablayout.getTabAt(i).getCustomView().findViewById(R.id.number);
            //存储后方便赋值
            numberList.add(number);
            title.setText(titleDatas.get(i));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    public void setListSize(int size, int position) {
        numberList.get(position).setText(size + "");
    }

    @OnClick(R.id.current_time_liner)
    public void onViewClicked() {
        selectStartTime();
    }

    private void selectStartTime() {
        TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                currentTime.setText(getTime(date));
                startTimeStr = currentTime.getText().toString();
                btn = true;
            }
        }).setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }
}
