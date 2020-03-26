package com.hulian.oa.work.file.admin.activity.attendance;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.People;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.CalendarViewAdapter;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.weiget.CalendarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hulian.oa.utils.TimeUtils.getMway;

/**
 * Created by qgl on 2020/3/20 15:42.
 * 下属打卡信息
 */
public class SubordActivity extends BaseActivity {
    @BindView(R.id.calendar)
    CalendarView calendarView;
    @BindView(R.id.kq_time)
    TextView kqTime;
    @BindView(R.id.cale_left)
    ImageView caleLeft;
    @BindView(R.id.cale_riht)
    ImageView caleRiht;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.clock_name)
    TextView clockName;
    @BindView(R.id.clock_department)
    TextView clockDepartment;
    @BindView(R.id.current_time)
    TextView currentTime;
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    private int[] cDate = CalendarUtil.getCurrentDate();
    List<People> mList = new ArrayList<People>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.subordactivity);
        ButterKnife.bind(this);
        List<People> mList1 = (List<People>) getIntent().getSerializableExtra("mList");
        //个人信息赋值
        tvType.setText(mList1.get(0).getUserName().substring(mList1.get(0).getUserName().length() - 2, mList1.get(0).getUserName().length()));
        clockName.setText(mList1.get(0).getUserName()+"");
        clockDepartment.setText(SPUtils.get(SubordActivity.this, "deptname", "").toString());

        currentTime.setText("" + cDate[0] + "-" + cDate[1] + "-" + cDate[2] + "   星期" + getMway());
        //适配日历数据
        HashMap<String, String> map = new HashMap<>();
        map.put("2020.3.15", "0");
        map.put("2020.3.16", "1");
        map.put("2020.3.17", "2");
        calendarView
                .setSpecifyMap(map)
                .setStartEndDate("2016.1", "2028.12")
                .setDisableStartEndDate("2016.10.10", "2028.10.10")
                .setInitDate(cDate[0] + "." + cDate[1])
                .setSingleDate(cDate[0] + "." + cDate[1] + "." + cDate[2])
                .setOnCalendarViewAdapter(R.layout.kq_ri_item_layout, new CalendarViewAdapter() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public TextView[] convertView(View view, DateBean date) {
                        TextView solarDay = (TextView) view.findViewById(R.id.solar_day);
                        TextView lunarDay = (TextView) view.findViewById(R.id.lunar_day);
                        TextView bg = (TextView) view.findViewById(R.id.bg);
                        LinearLayout liner = (LinearLayout) view.findViewById(R.id.liner_bg);
                        if (date.getKey() != null) {
                            if (date.getKey().equals("0")) {
                                bg.setVisibility(View.GONE);
                            } else if (date.getKey().equals("1")) {
                                bg.setVisibility(View.VISIBLE);
                                bg.setBackground(getResources().getDrawable(R.drawable.circle_bg2, null));
                            } else {
                                bg.setVisibility(View.VISIBLE);
                                bg.setBackground(getResources().getDrawable(R.drawable.circle_bg1, null));
                            }
                        }
                        return new TextView[]{solarDay, lunarDay};
                    }
                }).init();
        kqTime.setText(cDate[0] + "年" + cDate[1] + "月");
        Log.d("当前选中的日期1",cDate[0] + "年" + cDate[1] + "月" + cDate[2] + "日");
        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                kqTime.setText(date[0] + "年" + date[1] + "月");
            }
        });
        calendarView.setOnSingleChooseListener(new OnSingleChooseListener() {
            @Override
            public void onSingleChoose(View view, DateBean date) {
                kqTime.setText(date.getSolar()[0] + "年" + date.getSolar()[1] + "月");
                if (date.getType() == 1) {
                    Log.d("当前选中的日期2", date.getSolar()[0] + "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日");
                }
            }
        });
    }

    @OnClick({R.id.cale_left, R.id.cale_riht, R.id.clock_department,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cale_left:
//                前一个月
                calendarView.lastMonth();
                break;
            case R.id.cale_riht:
//                后一个月
                calendarView.nextMonth();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
