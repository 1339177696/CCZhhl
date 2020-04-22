package com.hulian.oa.work.activity.attendance;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.ClueBean;
import com.hulian.oa.bean.People;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.utils.TimeUtils;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.CalendarViewAdapter;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.weiget.CalendarView;

import org.json.JSONException;
import org.json.JSONObject;

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
    @BindView(R.id.s_dk_time)
    TextView sDKtime;
    @BindView(R.id.x_dk_time)
    TextView xDKtime;
    @BindView(R.id.s_sb_time)
    TextView sSbtime;
    @BindView(R.id.x_sb_time)
    TextView xSbtime;
    @BindView(R.id.s_dk_adress)
    TextView sDkadress;
    @BindView(R.id.x_dk_adress)
    TextView xDkadress;
    @BindView(R.id.rela_cale_jilu)
    RelativeLayout Relacalejilu;
    @BindView(R.id.x_liner)
    LinearLayout Xliner;
    @BindView(R.id.s_liner)
    LinearLayout Sliner;
    @BindView(R.id.sb_dk_chidao)
    TextView sbDkchidao;
    @BindView(R.id.sb_dk_waiqin)
    TextView sbDkwaiqin;
    @BindView(R.id.xb_dk_waiqin)
    TextView xbDkwaiqin;
    @BindView(R.id.xb_dk_chidao)
    TextView xbDkchidao;
    private int[] cDate = CalendarUtil.getCurrentDate();
    private String createTime = "";
    HashMap<String, String> markData = new HashMap<>();
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
        createTime = cDate[0] + "-" + cDate[1] + "-" + cDate[2];
        PostStateMonth();
        ClockType_from();
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


    // 请求月打卡记录
    public void PostStateMonth(){
        RequestParams params = new RequestParams();
        params.put("createBy",getIntent().getStringExtra("userid"));
        params.put("createTime",createTime);
        HttpRequest.OnClock_month(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (!result.getJSONArray("data").toString().equals("[]")){
                        List<ClueBean.DataBean> list = gson.fromJson(result.getJSONArray("data").toString(), new TypeToken<List<ClueBean.DataBean>>() {}.getType());
                        // 标记有日程的日期
                        for (int i = 0; i <= list.size() - 1; i++) {
                            markData.put(TimeUtils.dateClearZero(list.get(i).getYMD()), list.get(i).getSTATE());
                        }
                    }else {
                        markData = null;
                    }
                    //日历赋值
                    calederData(markData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });

    }

    // 日历控件初始化
    public void  calederData( HashMap<String, String> markData){
        calendarView
                .setSpecifyMap(markData)
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
                            if (date.getKey() != null) {
                                if (date.getKey().equals("Y")) {
                                    bg.setVisibility(View.VISIBLE);
                                    bg.setBackground(getResources().getDrawable(R.drawable.circle_bg2, null));
                                } else if (date.getKey().equals("N")){
                                    bg.setVisibility(View.VISIBLE);
                                    bg.setBackground(getResources().getDrawable(R.drawable.circle_bg1, null));
                                } else {
                                    bg.setVisibility(View.GONE);
                                }
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
                    // 用户选择的日期
                    createTime = date.getSolar()[0] + "-" + date.getSolar()[1] + "-" + date.getSolar()[2];
                    //请求选择的日期数据
                    ClockType_from();
                }

            }
        });
    }

    // 请求某天打卡记录
    public void ClockType_from(){
        RequestParams params = new RequestParams();
        params.put("createBy",getIntent().getStringExtra("userid"));
        params.put("createTime", createTime);
        HttpRequest.OnClock_Type(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (result.optString("data") != ""){
                        // 有打卡记录，
                        sDKtime.setText("打卡时间   "+result.getJSONObject("data").getString("registerUpTime"));
                        sDkadress.setText(result.getJSONObject("data").getString("registerUpAddress"));
                        Sliner.setVisibility(View.VISIBLE);
                        if (result.getJSONObject("data").getString("registerUpState").equals("0"))
                        {
                            sbDkchidao.setVisibility(View.GONE);
                            if (result.getJSONObject("data").getString("regisgerUpType").equals("0")){
                                sbDkwaiqin.setVisibility(View.VISIBLE);
                                sbDkwaiqin.setBackgroundResource(R.drawable.kqrl_tv_bg_blue);
                                sbDkwaiqin.setText("正常");
                            }else {
                                sbDkwaiqin.setVisibility(View.VISIBLE);
                                sbDkwaiqin.setBackgroundResource(R.drawable.dk_tv_bg_lv);
                                sbDkwaiqin.setText("外勤");
                            }
                        }else {
                            sbDkchidao.setVisibility(View.VISIBLE);
                            if (result.getJSONObject("data").getString("regisgerUpType").equals("0")){
                                sbDkwaiqin.setVisibility(View.GONE);
                            }else {
                                sbDkwaiqin.setVisibility(View.VISIBLE);
                                sbDkwaiqin.setBackgroundResource(R.drawable.dk_tv_bg_lv);
                                sbDkwaiqin.setText("外勤");
                            }
                        }
                        if (result.getJSONObject("data").getString("registerDownTime").equals("null"))
                        {
                            xDKtime.setText("暂无打卡记录");
                            Xliner.setVisibility(View.GONE);
                            xbDkchidao.setVisibility(View.GONE);
                            xbDkwaiqin.setVisibility(View.GONE);
                        }
                        else {
                            Xliner.setVisibility(View.VISIBLE);
                            xDKtime.setText("打卡时间   "+result.getJSONObject("data").getString("registerDownTime"));
                            xDkadress.setText(result.getJSONObject("data").getString("registerDownAddress"));
                            if (result.getJSONObject("data").getString("registerDownState").equals("0"))
                            {
                                xbDkchidao.setVisibility(View.GONE);
                                if (result.getJSONObject("data").getString("regisgerDownType").equals("0")){
                                    xbDkwaiqin.setVisibility(View.VISIBLE);
                                    xbDkwaiqin.setText("正常");
                                    xbDkwaiqin.setBackgroundResource(R.drawable.kqrl_tv_bg_blue);
                                }else {
                                    xbDkwaiqin.setText("外勤");
                                    xbDkwaiqin.setBackgroundResource(R.drawable.dk_tv_bg_lv);
                                    xbDkwaiqin.setVisibility(View.VISIBLE);
                                }
                            }else {
                                xbDkchidao.setVisibility(View.VISIBLE);
                                xbDkchidao.setText("早退");
                                if (result.getJSONObject("data").getString("regisgerDownType").equals("0")){
                                    xbDkwaiqin.setVisibility(View.GONE);
                                }else {
                                    xbDkwaiqin.setText("外勤");
                                    xbDkwaiqin.setBackgroundResource(R.drawable.dk_tv_bg_lv);
                                    xbDkwaiqin.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                    }else {
                        // 未打卡
                        sDKtime.setText("暂无打卡记录");
                        xDKtime.setText("暂无打卡记录");
                        Xliner.setVisibility(View.GONE);
                        Sliner.setVisibility(View.GONE);
                        sbDkchidao.setVisibility(View.GONE);
                        xbDkchidao.setVisibility(View.GONE);
                        sbDkwaiqin.setVisibility(View.GONE);
                        xbDkwaiqin.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });

    }
}
