package com.hulian.oa.work.activity.attendancestatistics.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hulian.oa.R;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.news.adapter.MyViewPageAdapter;
import com.othershe.calendarview.utils.CalendarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者：qgl 时间： 2020/4/22 09:03
 * Describe:单位考勤
 */
public class UnitAttendanceFragment extends Fragment {
    @BindView(R.id.unitatt_month_avg)
    TextView unitattMonthAvg;
    private View view;
    private Unbinder unbinder;
    @BindView(R.id.my_tablayout)
    TabLayout myTablayout;
    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    @BindView(R.id.unitAtten_number)
    TextView unitAtten_number;

    private ArrayList<String> titleDatas = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private List<TextView> numberList = new ArrayList<>();

    @BindView(R.id.current_time_liner)
    LinearLayout currentTimeLiner;
    @BindView(R.id.current_time)
    TextView currentTime;
    private String startTimeStr = "";
    private boolean btn = true;
    private int[] cDate = CalendarUtil.getCurrentDate();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.unitattendancefragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (cDate[1]<10)
        {
            currentTime.setText("" + cDate[0] + "-" + "0"+cDate[1]);
        }else {
            currentTime.setText("" + cDate[0] + "-" + cDate[1]);
        }
        init();
        getData();
        return view;

    }

    private void init() {
        titleDatas.clear();
        fragmentList.clear();
        numberList.clear();
        titleDatas.add("正常");
        titleDatas.add("迟到");
        titleDatas.add("早退");
        titleDatas.add("加班");
        titleDatas.add("请假");
        titleDatas.add("缺勤");
        titleDatas.add("外勤");
        fragmentList.add(UnitListFragment.newInstance("0",currentTime.getText().toString()));
        fragmentList.add(UnitListFragment.newInstance("1",currentTime.getText().toString()));
        fragmentList.add(UnitListFragment.newInstance("2",currentTime.getText().toString()));
        fragmentList.add(UnitListFragment.newInstance("3",currentTime.getText().toString()));
        fragmentList.add(UnitListFragment.newInstance("4",currentTime.getText().toString()));
        fragmentList.add(UnitListFragment.newInstance("5",currentTime.getText().toString()));
        fragmentList.add(UnitListFragment.newInstance("6",currentTime.getText().toString()));
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

    public void setUnit_text(int number){
        unitAtten_number.setText("考勤部门 "+number+" 个");
    }

    private void selectStartTime() {
        TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                currentTime.setText(getTime(date));
                startTimeStr = currentTime.getText().toString();
                btn = true;
                init();
                getData();
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

    @OnClick(R.id.current_time_liner)
    public void onViewClicked() {
        selectStartTime();
    }


    private void getData() {
        RequestParams params = new RequestParams();
        params.put("createTime",currentTime.getText().toString());
        HttpRequest.getClock_Timer(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (!TextUtils.equals(result.getJSONObject("data").getString("avg"), "")) {
                        unitattMonthAvg.setText(result.getJSONObject("data").getString("avg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                Toast.makeText(getActivity(), "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
