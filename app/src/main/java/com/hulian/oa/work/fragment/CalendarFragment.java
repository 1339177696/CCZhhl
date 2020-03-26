package com.hulian.oa.work.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hulian.oa.R;
import com.hulian.oa.bean.Res;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.NullStringToEmptyAdapterFactory;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.work.file.admin.activity.attendance.AttendrulesActivity;
import com.hulian.oa.work.file.admin.activity.attendance.AttendrulesmodifyActivity;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.CalendarViewAdapter;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.weiget.CalendarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

import static com.hulian.oa.utils.TimeUtils.getMway;

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
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.clock_name)
    TextView clockName;
    @BindView(R.id.clock_department)
    TextView clockDepartment;
    @BindView(R.id.current_time)
    TextView currentTime;

    @BindView(R.id.permissions_dar_no)
    RelativeLayout permissionsDarno;
    @BindView(R.id.clock_fg_img)
    ImageView clockFgimg;
    @BindView(R.id.clock_fg_tv)
    TextView clockFg_tv;
    @BindView(R.id.permissions_dar_yes)
    LinearLayout permissionsDar_yes;

    private int[] cDate = CalendarUtil.getCurrentDate();

    private boolean permi; //权限

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.calendarfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        // 权限判断
        permissions();
        // 规则制定查询
        postRule();
//        个人信息赋值
        tvType.setText(SPUtils.get(getActivity(), "nickname", "").toString().substring(SPUtils.get(getActivity(), "nickname", "").toString().length() - 2, SPUtils.get(getActivity(), "nickname", "").toString().length()));
        clockName.setText(SPUtils.get(getActivity(), "nickname", "").toString());
        clockDepartment.setText(SPUtils.get(getActivity(), "deptname", "").toString() + "   考勤(查看规则)");
        currentTime.setText("" + cDate[0] + "-" + cDate[1] + "-" + cDate[2] + "   星期" + getMway());
//        适配日历数据
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

    @OnClick({R.id.cale_left, R.id.cale_riht, R.id.clock_department, R.id.clock_fg_img})
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
            case R.id.clock_department:
                // 考勤规则
                startActivity(new Intent(getActivity(), AttendrulesActivity.class));
                break;
            case R.id.clock_fg_img:
                if (permi) {
                    // 跳转修改规则，值为空(领导)
                    Intent intent = new Intent(getActivity(), AttendrulesmodifyActivity.class);
                    intent.putExtra("id", "");
                    intent.putExtra("registerContent", "");
                    intent.putExtra("upTime", "");
                    intent.putExtra("downTime", "");
                    intent.putExtra("registerAddress", "");
                    intent.putExtra("distance", "");
                    intent.putExtra("rxhRule", "");
                    intent.putExtra("jingwei", "");
                    startActivity(intent);
                } else {
                    ToastHelper.showToast(getActivity(), "请联系管理员");
                }
                break;
        }
    }


    /**
     * 请求打卡规则
     */
    public void postRule() {
        RequestParams params = new RequestParams();
        HttpRequest.PostClock_rules(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (result.optString("data") == "") {
                        if (permi) {
                            clockFgimg.setImageResource(R.mipmap.kq_rule);
                            clockFg_tv.setText("请设置打卡规则");
                        } else {
                            clockFgimg.setImageResource(R.mipmap.kq_administrator);
                            clockFg_tv.setText("未设置打卡规则,请联系管理员");
                        }
                        permissionsDarno.setVisibility(View.VISIBLE);
                        permissionsDar_yes.setVisibility(View.GONE);
                    } else {
                        permissionsDar_yes.setVisibility(View.VISIBLE);
                        permissionsDarno.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(OkHttpException failuer) {
                ToastHelper.showToast(getActivity(), "服务器请求失败");
            }
        });
    }

    public void permissions() {
        if (SPUtils.get(getActivity(), "roleKey", "").toString().contains("synthesizeLead")) {
            permi = true;
        } else if (SPUtils.get(getActivity(), "roleKey", "").toString().contains("eachLead")) {
            permi = true;
        } else {
            permi = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    // 刷新
    public void onEventMainThread(CalendarFragment event) {
        postRule();
    }

}
