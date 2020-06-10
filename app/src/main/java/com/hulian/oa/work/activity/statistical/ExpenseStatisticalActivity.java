package com.hulian.oa.work.activity.statistical;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.bean.ExpenseStaBean;
import com.hulian.oa.utils.PieChartUtil;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.activity.statistical.adapter.ExpenseStatisAdapter;

import java.util.ArrayList;
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
    @BindView(R.id.back)
    ImageView back;

    private ExpenseStatisAdapter expenseStatisAdapter;
    private List<ExpenseStaBean> mData = new ArrayList<>();

//    private List<Float> mPieChartData = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.activity_expense_statistical);
        ButterKnife.bind(this);
        initList();
        //initPieChartView();
//        HashMap dataMap=new HashMap();
//        dataMap.put("采购费","120");
//        dataMap.put("加班餐费","100");
//        dataMap.put("加班打车费","50");
//        dataMap.put("外出打车费","40");
//        dataMap.put("办公用品","60");
//        dataMap.put("差旅费","40");
//        dataMap.put("员工福利","80");
//        dataMap.put("招待费","50");
        mData.clear();
        for (int i = 0; i < 8; i++) {
            ExpenseStaBean expenseStaBean = new ExpenseStaBean();
            expenseStaBean.setState(i + "");
            expenseStaBean.setNum(50 + "");
            expenseStaBean.setNum_per("30%");
            mData.add(expenseStaBean);
        }


        int color[] = new int[]{
                Color.rgb(251, 114, 147),
                Color.rgb(55, 162, 218),
                Color.rgb(159, 230, 184),
                Color.rgb(255, 219, 92),
                Color.rgb(224, 98, 174),
                Color.rgb(230, 144, 209),
                Color.rgb(255, 159, 127),
                Color.rgb(231, 188, 243)
        };
        PieChartUtil.getPitChart().setPieChart(mPieChart, mData, "", false, color);
    }


    private void initList() {
        expenseStatisAdapter = new ExpenseStatisAdapter(mData);
        expenseStatisAdapter.openLoadAnimation();
        expenseStatisAdapter.setEnableLoadMore(false);
        expenseStatisAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.list_empty, null));
        statisListview.setLayoutManager(new LinearLayoutManager(this));
        statisListview.setAdapter(expenseStatisAdapter);
        expenseStatisAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

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


    @OnClick(R.id.back)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
