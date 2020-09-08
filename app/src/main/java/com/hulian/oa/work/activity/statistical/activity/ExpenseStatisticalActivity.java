package com.hulian.oa.work.activity.statistical.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.bean.ClockDepartBean;
import com.hulian.oa.bean.Expen_Statis_Person_Bean;
import com.hulian.oa.bean.ExpenseBean;
import com.hulian.oa.bean.ExpenseStaBean;
import com.hulian.oa.bean.UnitLisFragmentBean;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.Identity;
import com.hulian.oa.utils.PieChartUtil;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.activity.statistical.adapter.ExpenseListAdapter;
import com.hulian.oa.work.activity.statistical.adapter.ExpenseStatisAdapter;
import com.hulian.oa.work.adapter.UnitListAdapter;
import com.hulian.oa.work.adapter.WriteReportAdapter;
import com.othershe.calendarview.utils.CalendarUtil;

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
 * Created by 陈泽宇 on 2020/4/27
 * Describe: 报销统计
 */
public class ExpenseStatisticalActivity extends BaseActivity {
    @BindView(R.id.pie_chart)
    PieChart mPieChart;
    @BindView(R.id.statis_listview)
    RecyclerView statisListview;
    @BindView(R.id.year)
    TextView year;
    @BindView(R.id.mouth)
    TextView mouth;
    @BindView(R.id.tv_me)
    TextView tvMe;
    @BindView(R.id.statistical_num)
    TextView statisticalNum;
    @BindView(R.id.tv_pie_tv)
    TextView tv_pie_tv;
    @BindView(R.id.expen_liner)
    LinearLayout expen_liner;
    @BindView(R.id.exlistview)
    ExpandableListView exlistview;
    //最外面一层 分组下面的详情
    private List<List<ExpenseStaBean>> childArray;
    //最外面一层 分组名
    private List<Expen_Statis_Person_Bean> groupArray;
    List<Expen_Statis_Person_Bean> departmentList = new ArrayList<>();
    List<ExpenseStaBean> memberList = new ArrayList<>();
    private ExpenseListAdapter expenseListAdapter;



    private ExpenseStatisAdapter expenseStatisAdapter;
    private List<ExpenseStaBean> mData = new ArrayList<>();
    private List<ExpenseStaBean> mData_b = new ArrayList<>();
    private List<ExpenseStaBean> mData_a = new ArrayList<>();
    List<String> reasonlist = new ArrayList<>();
    private OptionsPickerView reasonPicker;//时间;
    private String tv_year = "";
    private String tv_month = "";
    private int[] cDate = CalendarUtil.getCurrentDate();
    int color[];
    int res[];
    private String post_type = "1";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.activity_expense_statistical);
        ButterKnife.bind(this);
        if (cDate[1] < 10) {
            tv_year = "" + cDate[0];
            tv_month = "0" + cDate[1];
            year.setText("" + cDate[0] + "年");
            mouth.setText("0" + cDate[1] + "月份");
        } else {
            year.setText("" + cDate[0] + "年");
            mouth.setText("" + cDate[1] + "月份");
            tv_year = "" + cDate[0];
            tv_month = "" + cDate[1];
        }
        int iden = Identity.aa(SPUtils.get(this, "roleKey", "").toString());
        if (iden == 3 || iden == 4) {
            tvMe.setVisibility(View.VISIBLE);
        } else {
            tvMe.setVisibility(View.GONE);
        }
        initReason();
        initList();
    }

    private void initList() {
        // 抽屉式
        exlistview.setGroupIndicator(null);
        groupArray = new ArrayList<>();
        childArray = new ArrayList<>();
        //创建适配器
        expenseListAdapter = new ExpenseListAdapter(ExpenseStatisticalActivity.this, groupArray, R.layout.group_layout_expenselist_single, childArray, R.layout.item_expense_statis2);
        exlistview.setAdapter(expenseListAdapter);


        expenseStatisAdapter = new ExpenseStatisAdapter(mData);
        expenseStatisAdapter.openLoadAnimation();
        expenseStatisAdapter.setEnableLoadMore(true);
        expenseStatisAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.list_empty, null));
        statisListview.setLayoutManager(new LinearLayoutManager(this));
        statisListview.setAdapter(expenseStatisAdapter);
        expenseStatisAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (post_type.equals("1")){
                    Intent intent = new Intent(ExpenseStatisticalActivity.this, ExpenseStatisDetelisActivity.class);
                    intent.putExtra("type", mData.get(position).getType());
                    intent.putExtra("year", tv_year);
                    intent.putExtra("month", tv_month);
                    intent.putExtra("title", mData.get(position).getState());
                    startActivity(intent);
                }else if (post_type.equals("2")){
                    Intent intent = new Intent(ExpenseStatisticalActivity.this, ExpenseStatisDetelisActivity2.class);
                    intent.putExtra("type", mData.get(position).getType());
                    intent.putExtra("year", tv_year);
                    intent.putExtra("month", tv_month);
                    intent.putExtra("title", mData.get(position).getState());
                    startActivity(intent);
                }

            }
        });
        post_data();
    }

    /**
     * 初始化图表
     */
//    private void initPieChartView() {
//        setData();
//        //设置X轴的动画
//        mPieChart.animateX(1400);
//        //使用百分比
//        mPieChart.setUsePercentValues(true);
//        //设置图列可见
//        mPieChart.getLegend().setEnabled(true);
//        //设置图列标识的大小
////        mPieChart.getLegend().setFormSize(14);
//        //设置图列标识文字的大小
////        mPieChart.getLegend().setTextSize(14);
//        //设置图列的位置
////        mPieChart.getLegend().setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
//        //设置图列标识的形状
////        mPieChart.getLegend().setForm(Legend.LegendForm.CIRCLE);
//        //设置图表描述
////        mPieChart.setDescription("这是一个饼图");
//        //设置图表描述的字体
////        mPieChart.setDescriptionTextSize(14);
//        //设置图表描述的位置
////        mPieChart.setDescriptionPosition(950, 950);
//        //设置是否可转动
//        mPieChart.setRotationEnabled(false);
//
//        mPieChart.setDrawHoleEnabled(true);
////        mPieChart.setHoleRadius(100);
////        mPieChart.setTransparentCircleRadius(25f);
//        mPieChart.setTransparentCircleAlpha(0);
//        mPieChart.setHoleRadius(80f);
//        mPieChart.getDescription().setEnabled(false);
//        mPieChart.highlightValues(null);
//        mPieChart.setHighlightPerTapEnabled(false);
//
//    }

    /**
     * 图表设置数据
     */
//    private void setData() {
//        boolean allZero = true;//是否全是0
//        for (int i = 0; i < mPieChartData.size(); i++ ){
//            if (mPieChartData.get(i) != 0){
//                allZero = false;
//            }
//        }
//        //设置标题
//        List<PieEntry> entrys = new ArrayList<>();
//        int color [];
//        if (!allZero){
//            for (int i = 0; i < mPieChartData.size(); i++) {
//                entrys.add(new PieEntry(mPieChartData.get(i), i));
//            }
//            color = new int[]{
//                    Color.rgb(251, 114, 14),
//                    Color.rgb(55, 162, 218),
//                    Color.rgb(159, 230, 184),
//                    Color.rgb(255, 219, 92),
//                    Color.rgb(224, 98, 174),
//                    Color.rgb(230, 144, 209),
//                    Color.rgb(255, 159, 127),
//                    Color.rgb(231, 188, 243)};
//        }else {
//            entrys.add(new PieEntry(1, 0));
//            color = new int[]{Color.rgb(196,196,196)};
//        }
//
//
//        //饼图数据集
//        PieDataSet dataset = new PieDataSet(entrys,null);
//        //设置饼状图Item之间的间隙
////        dataset.setSliceSpace(3f);
//        //饼图Item被选中时变化的距离
////        dataset.setSelectionShift(10f);
//        //颜色填充
//        dataset.setColors(color);
//        //数据填充
//        PieData piedata = new PieData(dataset);
//        //设置饼图上显示数据的字体大小
//        piedata.setValueTextSize(0);
//        mPieChart.setData(piedata);
//    }
    @OnClick({R.id.iv_back, R.id.year, R.id.mouth, R.id.tv_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.year:
                selectYear();
                break;
            case R.id.mouth:
                selectMoth();
                break;
            case R.id.tv_me:
                reasonPicker.show();
                break;
        }
    }

    // 请求个人数据
    public void post_data() {
        RequestParams params = new RequestParams();
        // 查询人id
        params.put("id", SPUtils.get(mContext, "userId", "").toString());
        //类型，个人1,单位2,全部3
        params.put("dept", post_type);
        // 查询时间
        params.put("time", tv_year + "-" + tv_month);
        //代表页面的页码
        params.put("flag", "1");
        HttpRequest.get_Expense_List(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                if (!post_type.equals("3")) {
                    expen_liner.setVisibility(View.VISIBLE);
                    exlistview.setVisibility(View.GONE);
                    try {
                        res = new int[]{};
                        mData_b = new ArrayList<>();
                        mData_a = new ArrayList<>();
                        color = new int[]{Color.rgb(
                                251, 114, 147),
                                Color.rgb(55, 162, 218),
                                Color.rgb(159, 230, 184),
                                Color.rgb(255, 219, 92),
                                Color.rgb(224, 98, 174),
                                Color.rgb(230, 144, 209),
                                Color.rgb(255, 159, 127),
                                Color.rgb(231, 188, 243),
                                Color.rgb(148, 0, 211)};

                        JSONObject result = new JSONObject(responseObj.toString());
                        mData_a = gson.fromJson(result.getJSONArray("data").toString(),
                                new TypeToken<List<ExpenseStaBean>>() {
                                }.getType());
                        statisticalNum.setText("￥" + result.getString("msg"));
                        mData.addAll(mData_a);
                        expenseStatisAdapter.loadMoreComplete();
                        expenseStatisAdapter.notifyDataSetChanged();
                        // 去除没有数据的
                        mData_b = mData_a;
                        for (int i = mData_b.size() - 1; i >= 0; i--) {
                            if (mData_b.get(i).getNum().equals("0")) {
                                mData_b.remove(i);
                                res = delete(i, color);
                            }else {
                             res= color;
                            }
                        }
                        if (result.getString("msg").equals("0")) {
                            mPieChart.setVisibility(View.INVISIBLE);
                            tv_pie_tv.setVisibility(View.VISIBLE);
                        } else {
                            mPieChart.setVisibility(View.VISIBLE);
                            tv_pie_tv.setVisibility(View.GONE);
                            PieChartUtil.getPitChart().setPieChart(mPieChart, mData_b, "", false, res);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    expen_liner.setVisibility(View.GONE);
                    exlistview.setVisibility(View.VISIBLE);
                    try {
                        departmentList.clear();
                        groupArray.clear();
                        JSONObject result = new JSONObject(responseObj.toString());
                        departmentList = gson.fromJson(result.getJSONArray("data").toString(),
                                new TypeToken<List<Expen_Statis_Person_Bean>>() {
                                }.getType());
                        statisticalNum.setText("￥" + result.getString("msg"));
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
                Toast.makeText(ExpenseStatisticalActivity.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
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
                clear();
            }
        }).setType(new boolean[]{true, false, false, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setRangDate(calendar,calendar1)
                .build();


        pvTime.show();
    }

    public void startDate(){
        String str="2020-01-01";
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
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
                clear();
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

    private void initReason() {
        reasonlist.add("我的   ");
        reasonlist.add("单位   ");
        reasonlist.add("全部   ");
        reasonPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvMe.setText(reasonlist.get(options1));
                post_type = String.valueOf(options1 + 1);
                clear();
            }
        }).setTitleText("请选择").setContentTextSize(22).setTitleSize(22).setSubCalSize(21).build();
        reasonPicker.setPicker(reasonlist);
    }

    /**
     * 数组删除
     *
     * @param index
     * @param array
     * @return
     */
    public int[] delete(int index, int array[]) {
        //数组的删除其实就是覆盖前一位
        int[] arrNew = new int[array.length - 1];
        for (int i = index; i < array.length - 1; i++) {
            array[i] = array[i + 1];
        }
        System.arraycopy(array, 0, arrNew, 0, arrNew.length);
        return arrNew;
    }

    // 重新加载
    private void clear() {
        mData.clear();
        post_data();
    }

}
