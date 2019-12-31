package com.hulian.oa.work.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.bean.ScheduleBean3;
import com.hulian.oa.me.AddScheduleActivity;
import com.hulian.oa.me.ScheduleActivity;
import com.hulian.oa.me.l_adapter.L_ScheduleAdapter;
import com.hulian.oa.me.l_calendar.CustomDayView;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.ldf.calendar.Utils;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by qgl on 2019/12/25 16:38.
 */
public class WorkFragemt_richeng extends Fragment {
    Unbinder unbinder;
    //顶部年
    @BindView(R.id.show_year_view)
    TextView tvYear;
    //顶部月
    @BindView(R.id.show_month_view)
    TextView tvMonth;
    //    //折叠控件
//    @BindView(R.id.content)
//    CoordinatorLayout content;
    //日历
    @BindView(R.id.calendar_view)
    MonthPager monthPager;
    //日程列表
    @BindView(R.id.list)
    //  CustListView rvToDoList;
            RecyclerView rvToDoList;
    //添加日程
    @BindView(R.id.tv_add_schedule)
    LinearLayout tv_add_schedule;


    String time = "";
    String Month;
    String Day;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private Context context;
    private CalendarDate currentDate;
    private boolean initiated = false;
    private L_ScheduleAdapter l_scheduleAdapter;
    private    String timeNow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_fragment_recheng_qgl, container, false);
        unbinder = ButterKnife.bind(this, view);
        ButterKnife.bind(getActivity());
        context = getActivity();
        init();
        initCurrentDate();
        initCalendarView();
        getDatatime(time);
        return view;
    }

    /**
     * 初始化当前的年月
     */
    private void initCurrentDate() {
        currentDate = new CalendarDate();
        tvYear.setText(currentDate.getYear() + "年");
        tvMonth.setText(currentDate.getMonth() + "");
        if (currentDate.getMonth() < 10) {
            Month = "0" + currentDate.getMonth();
        } else {
            Month = "" + currentDate.getMonth();
        }
        if (currentDate.getDay() < 10) {
            Day = "0" + currentDate.getDay();
        } else {
            Day = "" + currentDate.getDay();

        }
        time = currentDate.getYear() + "-" + Month + "-" + Day;
    }

    /**
     * 初始化CustomDayView，并作为CalendarViewAdapter的参数传入
     */
    private void initCalendarView() {
        initListener();
        CustomDayView customDayView = new CustomDayView(context, R.layout.l_custom_day);
        calendarAdapter = new CalendarViewAdapter(
                context,
                onSelectDateListener,
                CalendarAttr.WeekArrayType.Monday,
                customDayView);
        calendarAdapter.setOnCalendarTypeChangedListener(new CalendarViewAdapter.OnCalendarTypeChanged() {
            @Override
            public void onCalendarTypeChanged(CalendarAttr.CalendarType type) {
//                rvToDoList.scrollToPosition(0);

            }
        });
//        initMarkData();
        getMark_Data();
        initMonthPager();
    }

    /**
     * 初始化monthPager，MonthPager继承自ViewPager
     *
     * @return void
     */
    private void initMonthPager() {
        monthPager.setAdapter(calendarAdapter);
        monthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        monthPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        monthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) != null) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    tvYear.setText(date.getYear() + "年");
                    tvMonth.setText(date.getMonth() + "");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 初始化标记数据，HashMap的形式，可自定义
     * 如果存在异步的话，在使用setMarkData之后调用 calendarAdapter.notifyDataChanged();
     */

    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                refreshClickDate(date);
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                monthPager.selectOtherMonth(offset);
            }
        };
    }

    private void init() {
        //此处强行setViewHeight，毕竟你知道你的日历牌的高度
        monthPager.setViewHeight(Utils.dpi2px(context, 270));
//        rvToDoList.setNestedScrollingEnabled(false);
//        rvToDoList.setLayoutManager(new LinearLayoutManager(this));
//        rvToDoList.setAdapter(new L_ScheduleAdapter(this));
    }

    /**
     * onWindowFocusChanged回调时，将当前月的种子日期修改为今天
     *
     * @return void
     */

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        if (isInMultiWindowMode && !initiated) {
            refreshMonthPager();
            initiated = true;
        }
    }

    private void refreshMonthPager() {
        CalendarDate today = new CalendarDate();
        calendarAdapter.notifyDataChanged(today);
        tvYear.setText(today.getYear() + "年");
        tvMonth.setText(today.getMonth() + "");
    }

    private void refreshClickDate(CalendarDate date) {
        currentDate = date;
        tvYear.setText(date.getYear() + "年");
        tvMonth.setText(date.getMonth() + "");
        if (date.getMonth() < 10) {
            Month = "0" + date.getMonth();
        } else {
            Month = "" + date.getMonth();
        }
        if (date.getDay() < 10) {
            Day = "0" + date.getDay();
        } else {
            Day = "" + date.getDay();

        }
        time = date.getYear() + "-" + Month + "-" + Day;
//        Toast.makeText(ScheduleActivity.this,time,Toast.LENGTH_SHORT).show();
        getDatatime(time);
    }

    /**
     * 获取接口
     */
    private void getDatatime(String time) {
        String userid = SPUtils.get(getActivity(), "userId", "-1").toString();
        RequestParams params = new RequestParams();
        params.put("createBy", userid);    //ID
        params.put("scheduleTimeBegin", time); // 时间
        HttpRequest.postSche_schedule(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");//只有时分秒
                timeNow = sdf.format(date);
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<ScheduleBean3> memberList2 = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<ScheduleBean3>>() {
                            }.getType());
                    rvToDoList.setLayoutManager(new LinearLayoutManager(getActivity()));
                    l_scheduleAdapter = new L_ScheduleAdapter(getActivity());
                    rvToDoList.setAdapter(l_scheduleAdapter);
                    List<ScheduleBean3> memberList = new ArrayList<>();
                    List<ScheduleBean3> memberList3 = new ArrayList<>();
                    // 没有数据也显示时间
//                    if (memberList2.size() > 0) {
                    //时间这是为8点----17点
//                        for (int i = 0; i < 24; i++) {
                        for (int i = 8; i < 18; i++) {
                            ScheduleBean3 bean3 = new ScheduleBean3();
                            String str = String.format("%02d", i);
                            bean3.setTimeTitle("" + str + ":00");
                            bean3.setHasContent(false);
                            bean3.setNow(false);
                            if (timeNow.split(":")[0].toString().equals(str)) {
                            if (TimeUtils.differentDaysByMillisecond2(timeNow,"17:00")<0)
                            {
                                bean3.setNow(false);
                            }else {
                                bean3.setNow(true);
                            }

                                if (timeNow.split(":")[1].equals("00")) {
                                    bean3.setTimeNow(timeNow.split(":")[0] + ":01");
                                } else
                                    bean3.setTimeNow(timeNow);
                            }
                            memberList.add(bean3);
                        }

                        for (int i = 0; i < memberList2.size(); i++) {
                            if (memberList2.get(i).getIsToday().equals("Y")) {
                                ScheduleBean3 memberList4 = new ScheduleBean3();
                                memberList4.setQufen("Y");
                                memberList4.setScheduleContent(memberList2.get(i).getScheduleContent());
                                memberList3.add(memberList4);
                            }else {
                                String startTime = TimeUtils.getDateToString5(memberList2.get(i).getScheduleTimeBegin());
                                String endTime = TimeUtils.getDateToString5(memberList2.get(i).getScheduleTimeEnd());
                                for (int j = Integer.parseInt(startTime)-8; j < Integer.parseInt(endTime)-8; j++) {
                                    //如果是全天就不显示在列表上，设置为备忘录
                                    memberList.get(j).setHasContent(true);
                                    if (j == Integer.parseInt(startTime)-8)
                                        memberList.get(j).setScheduleContent(memberList2.get(i).getScheduleContent());
                                }
                            }

                    }
                        l_scheduleAdapter.addAllData(memberList, memberList3,time);
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

    // 获取有日程安排的天
    private void getMark_Data() {
        String userid = SPUtils.get(getActivity(), "userId", "-1").toString();
        RequestParams params = new RequestParams();
        params.put("createBy", userid);    //ID
        HttpRequest.postSche_selectDates(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<String> list = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<String>>() {
                            }.getType());
                    // 标记有日程的日期
                    HashMap<String, String> markData = new HashMap<>();
                    for (int i = 0; i <= list.size() - 1; i++) {
                        markData.put(list.get(i), "0");
                    }
                    calendarAdapter.setMarkData(markData);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("返回回來的", "返回回來的");
        if (requestCode == 1) {
            getDatatime(time);
            // initCalendarView();
            getMark_Data();
        }
    }

    @OnClick({R.id.tv_add_schedule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_schedule:
                if (time != "" && !time.equals("")) {
                    Intent intent = new Intent(getActivity(), AddScheduleActivity.class);
                    intent.putExtra("schetime", time);
//                    startActivity(intent);
                    startActivityForResult(intent, 1);

//                    startActivity(new Intent(this,AddScheduleActivity.class));
                } else {
                    time = "" + currentDate.getYear() + currentDate.getMonth() + currentDate.getDay();
                    Intent intent = new Intent(getActivity(), AddScheduleActivity.class);
                    intent.putExtra("schetime", time);
//                    startActivity(intent);
                    startActivityForResult(intent, 1);
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
