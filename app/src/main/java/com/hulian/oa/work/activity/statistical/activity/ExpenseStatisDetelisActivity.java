package com.hulian.oa.work.activity.statistical.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.bean.ExpenseStaBean;
import com.hulian.oa.bean.ExpenseStatisDeteBean;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.activity.ReadReportActivity;
import com.hulian.oa.work.activity.statistical.adapter.ExpenStatisDetelisAdapter;
import com.hulian.oa.work.adapter.WriteReportAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：qgl 时间： 2020/7/30 17:12
 * Describe: 报销统计详情
 */
public class ExpenseStatisDetelisActivity extends BaseActivity implements  SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.year)
    TextView year;
     @BindView(R.id.statistical_num)
    TextView statistical_num;
    @BindView(R.id.mouth)
    TextView mouth;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.ex_listview)
    RecyclerView ex_listview;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private String tv_year = "";
    private String tv_month = "";
    private List<ExpenseStatisDeteBean>mData = new ArrayList<>();
    private ExpenStatisDetelisAdapter mAdapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.activity_expense_detelis);
        ButterKnife.bind(this);
        tv_year = getIntent().getStringExtra("year");
        tv_month = getIntent().getStringExtra("month");
        titleText.setText(getIntent().getStringExtra("title"));
        year.setText("" + tv_year + "年");
        mouth.setText("" + tv_month + "月份");
        initList();
    }

    private void initList() {
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new ExpenStatisDetelisAdapter(mData);
        mAdapter.openLoadAnimation();
        mAdapter.setEnableLoadMore(true);
        mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.list_empty, null));
        ex_listview.setLayoutManager(new LinearLayoutManager(this));
        ex_listview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        post_data();

    }
    public void post_data() {
        RequestParams params = new RequestParams();
        // 查询人id
        params.put("id", SPUtils.get(mContext, "userId", "").toString());
        params.put("type", getIntent().getStringExtra("type"));
        // 查询时间
        params.put("time", tv_year + "-" + tv_month);
        HttpRequest.get_Expense_List_detelis(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                swipeRefreshLayout.setRefreshing(false);
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<ExpenseStatisDeteBean> memberList= gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<ExpenseStatisDeteBean>>() {
                            }.getType());
                    statistical_num.setText("￥" + result.getString("msg"));
                    mData.addAll(memberList);
                    mAdapter.loadMoreComplete();
                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }

    @OnClick({R.id.year, R.id.mouth,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.year:
                selectYear();
                break;
            case R.id.mouth:
                selectMoth();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        mData.clear();
        post_data();
    }

    /**
     * 选择年
     */
    private void selectYear() {
        String str="2020-01-01";
        String sto="2025-12-30";
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date stop = null;
        try {
            date = sdf.parse(str);
            stop = sdf.parse(sto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(stop);
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tv_year = getTime(date);
                year.setText(getTime(date) + "年");
                onRefresh();
            }
        }).setType(new boolean[]{true, false, false, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setRangDate(calendar,calendar1)
                .build();
        pvTime.show();
    }
    /**
     * 选择月
     */
    private void selectMoth() {
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mouth.setText(getTime2(date) + "月份");
                tv_month = getTime2(date);
                onRefresh();
            }
        }).setType(new boolean[]{false, true, false, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }
    private String getTime2(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return format.format(date);
    }


}
