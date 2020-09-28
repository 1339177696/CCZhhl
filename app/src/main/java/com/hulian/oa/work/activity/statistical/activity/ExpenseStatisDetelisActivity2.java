package com.hulian.oa.work.activity.statistical.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.bean.Expen_Statis_Person_Bean;
import com.hulian.oa.bean.ExpenseStaBean;
import com.hulian.oa.bean.ExpenseStatisDeteBean;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.PieChartUtil;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.activity.statistical.adapter.ExpenStatisDetelisAdapter;
import com.hulian.oa.work.activity.statistical.adapter.ExpenseListAdapter;
import com.hulian.oa.work.activity.statistical.adapter.ExpenseListAdapter2;

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
 * 作者：qgl 时间： 2020/8/26 17:12
 * Describe: 报销统计详情
 */
public class ExpenseStatisDetelisActivity2 extends BaseActivity {
    @BindView(R.id.year)
    TextView year;
    @BindView(R.id.statistical_num)
    TextView statistical_num;
    @BindView(R.id.mouth)
    TextView mouth;
    @BindView(R.id.title_text)
    TextView titleText;
    private String tv_year = "";
    private String tv_month = "";

    @BindView(R.id.exlistview)
    ExpandableListView exlistview;
    //最外面一层 分组下面的详情
    private List<List<ExpenseStaBean>> childArray;
    //最外面一层 分组名
    private List<Expen_Statis_Person_Bean> groupArray;
    List<Expen_Statis_Person_Bean> departmentList = new ArrayList<>();
    List<ExpenseStaBean> memberList = new ArrayList<>();
    private ExpenseListAdapter2 expenseListAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.activity_expense_detelis2);
        ButterKnife.bind(this);
        tv_year = getIntent().getStringExtra("year");
        tv_month = getIntent().getStringExtra("month");
        titleText.setText(getIntent().getStringExtra("title"));
        year.setText("" + tv_year + "年");
        mouth.setText("" + tv_month + "月份");
        initList();
    }

    public void initList() {
        // 抽屉式
        exlistview.setGroupIndicator(null);
        groupArray = new ArrayList<>();
        childArray = new ArrayList<>();
        //创建适配器
        expenseListAdapter = new ExpenseListAdapter2(ExpenseStatisDetelisActivity2.this, groupArray, R.layout.group_layout_expenselist_single, childArray, R.layout.item_expense_statis2);
        exlistview.setAdapter(expenseListAdapter);
        post_data();

    }

    public void post_data() {
        RequestParams params = new RequestParams();
        // 查询人id
        params.put("id", SPUtils.get(mContext, "userId", "").toString());
        //类型，个人1,单位2,全部3
        params.put("dept", "2");
        params.put("type", getIntent().getStringExtra("type"));
        // 查询时间
        params.put("time", tv_year + "-" + tv_month);
        //代表页面的页码
        params.put("flag", "2");
        HttpRequest.get_Expense_List(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    departmentList.clear();
                    groupArray.clear();
                    JSONObject result = new JSONObject(responseObj.toString());
                    departmentList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<Expen_Statis_Person_Bean>>() {
                            }.getType());
                    statistical_num.setText("￥" + result.getString("msg"));
                    groupArray.addAll(departmentList);
                    for (int i = 0; i < departmentList.size(); i++) {
                        List<ExpenseStaBean> temPeople = new ArrayList<>();
                        childArray.add(temPeople);
                        initPeopleData(departmentList.get(i).getUserId(), tv_year + "-" + tv_month, i);
                    }
                    exlistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long id) {
                            //返回false表示系统自己处理展开和关闭事件 返回true表示调用者自己处理展开和关闭事件
                            return false;
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });

    }

    //请求报销类目
    private void initPeopleData(String partId, String time, int position) {
        RequestParams params = new RequestParams();
        params.put("id", partId);
        params.put("time", time);
        params.put("type", getIntent().getStringExtra("type"));
        HttpRequest.get_Expense_List_detelis(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    memberList.clear();
                    memberList.removeAll(memberList);
                    childArray.get(position).clear();
                    JSONObject result = new JSONObject(responseObj.toString());
                    memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<ExpenseStaBean>>() {
                            }.getType());
                    List<ExpenseStaBean> childModels = childArray.get(position);
                    childModels.addAll(memberList);
                    exlistview.collapseGroup(position);
                    expenseListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(ExpenseStatisDetelisActivity2.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.year, R.id.mouth, R.id.iv_back})
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
                onResult();
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
                onResult();
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

    public void onResult(){
        memberList.clear();
        post_data();
    }

}
