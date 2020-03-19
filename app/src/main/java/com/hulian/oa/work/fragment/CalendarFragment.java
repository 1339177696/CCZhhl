package com.hulian.oa.work.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hulian.oa.R;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.CalendarViewAdapter;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.weiget.CalendarView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by qgl on 2020/3/17 17:00.
 * 打卡日历
 */
public class CalendarFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.calendar)
    CalendarView calendarView;
    @BindView(R.id.kq_time)
    TextView kqTime;
    @BindView(R.id.cale_left)
    ImageView caleLeft;
    @BindView(R.id.cale_riht)
    ImageView caleRiht;


    private int[] cDate = CalendarUtil.getCurrentDate();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.calendarfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
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


                        return new TextView[]{solarDay, lunarDay, bg};
                    }
                }).init();

        kqTime.setText(cDate[0] + "年" + cDate[1] + "月");
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
                }
            }
        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.cale_left, R.id.cale_riht})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cale_left:
                calendarView.lastMonth();
                break;
            case R.id.cale_riht:
                calendarView.nextMonth();
                break;
        }
    }
}
