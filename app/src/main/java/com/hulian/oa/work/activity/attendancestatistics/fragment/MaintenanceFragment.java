package com.hulian.oa.work.activity.attendancestatistics.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hulian.oa.R;
import com.hulian.oa.news.adapter.MyViewPageAdapter;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.ToastHelper;
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
 * 作者：qgl 时间： 2020/4/20 11:11
 * Describe:我的考勤
 * 在fragment中嵌套fragment使用getChildFragmentManager();
 */
public class MaintenanceFragment extends Fragment {
    @BindView(R.id.my_tablayout)
    TabLayout myTablayout;
    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.clock_name)
    TextView clockName;
    @BindView(R.id.clock_department)
    TextView clockDepartment;
    @BindView(R.id.current_time)
    TextView currentTime;
    @BindView(R.id.current_time_img)
    ImageView currentTimeImg;
    @BindView(R.id.current_time_liner)
    LinearLayout currentTimeLiner;
    private Unbinder unbinder;
    private View view;
    private ArrayList<String> titleDatas = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private List<TextView> numberList = new ArrayList<>();
    private int[] cDate = CalendarUtil.getCurrentDate();
    private String startTimeStr = "";
    private boolean btn = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maintenancefragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        //个人信息赋值
        tvType.setText(SPUtils.get(getActivity(), "nickname", "").toString().substring(SPUtils.get(getActivity(), "nickname", "").toString().length() - 2, SPUtils.get(getActivity(), "nickname", "").toString().length()));
        clockName.setText(SPUtils.get(getActivity(), "nickname", "").toString());
        clockDepartment.setText(SPUtils.get(getActivity(), "deptname", "").toString());
        currentTime.setText("" + cDate[0] + "-" + cDate[1]);

        titleDatas.add("正常");
        titleDatas.add("外勤");
        titleDatas.add("迟到");
        titleDatas.add("缺卡");
        titleDatas.add("请假");
        titleDatas.add("早退");
        fragmentList.add(AttenListFragment.newInstance("0"));
        fragmentList.add(AttenListFragment.newInstance("1"));
        fragmentList.add(AttenListFragment.newInstance("2"));
        fragmentList.add(AttenListFragment.newInstance("3"));
        fragmentList.add(AttenListFragment.newInstance("4"));
        fragmentList.add(AttenListFragment.newInstance("5"));
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
        Log.d("啊啊啊啊", position + "***" + size + "");
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
