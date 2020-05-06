package com.hulian.oa.work.activity.statistical;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.utils.PieChartUtil;
import com.hulian.oa.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 陈泽宇 on 2020/4/27
 * Describe: 报销统计
 */
public class ExpenseStatisticalActivity extends BaseActivity {

    @BindView(R.id.pie_chart)
    PieChart mPieChart;


    private List<Float> mPieChartData = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.activity_expense_statistical);
        ButterKnife.bind(this);

        getData();
//        initPieChartView();

        HashMap dataMap=new HashMap();
        dataMap.put("采购费","862100");
        dataMap.put("加班餐费","730900");
        dataMap.put("加班打车费","624600");
        dataMap.put("外出打车费","402000");
        dataMap.put("办公用品","382900");
        dataMap.put("差旅费","368100");
        dataMap.put("员工福利","288300");
//        dataMap.put("招待费","0");

        int color [] = new int[]{
                Color.rgb(251, 114, 14),
                Color.rgb(55, 162, 218),
                Color.rgb(159, 230, 184),
                Color.rgb(255, 219, 92),
                Color.rgb(224, 98, 174),
                Color.rgb(230, 144, 209),
                Color.rgb(255, 159, 127),
                Color.rgb(231, 188, 243)};



        PieChartUtil.getPitChart().setPieChart(mPieChart,dataMap,"",false,color);

    }

    private void getData(){
        mPieChartData.add(Float.valueOf("862100"));
        mPieChartData.add(Float.valueOf("730900"));
        mPieChartData.add(Float.valueOf("624600"));
        mPieChartData.add(Float.valueOf("402000"));
        mPieChartData.add(Float.valueOf("382900"));
        mPieChartData.add(Float.valueOf("368100"));
        mPieChartData.add(Float.valueOf("288300"));
        mPieChartData.add(Float.valueOf("165900"));
    }


    /**
     * 初始化图表
     */
    private void initPieChartView() {
        setData();
        //设置X轴的动画
        mPieChart.animateX(1400);
        //使用百分比
        mPieChart.setUsePercentValues(true);
        //设置图列可见
        mPieChart.getLegend().setEnabled(true);
        //设置图列标识的大小
//        mPieChart.getLegend().setFormSize(14);
        //设置图列标识文字的大小
//        mPieChart.getLegend().setTextSize(14);
        //设置图列的位置
//        mPieChart.getLegend().setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        //设置图列标识的形状
//        mPieChart.getLegend().setForm(Legend.LegendForm.CIRCLE);
        //设置图表描述
//        mPieChart.setDescription("这是一个饼图");
        //设置图表描述的字体
//        mPieChart.setDescriptionTextSize(14);
        //设置图表描述的位置
//        mPieChart.setDescriptionPosition(950, 950);
        //设置是否可转动
        mPieChart.setRotationEnabled(false);

        mPieChart.setDrawHoleEnabled(true);
//        mPieChart.setHoleRadius(100);
//        mPieChart.setTransparentCircleRadius(25f);
        mPieChart.setTransparentCircleAlpha(0);
        mPieChart.setHoleRadius(80f);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.highlightValues(null);

        mPieChart.setHighlightPerTapEnabled(false);
    }

    /**
     * 图表设置数据
     */
    private void setData() {
        boolean allZero = true;//是否全是0
        for (int i = 0; i < mPieChartData.size(); i++ ){
            if (mPieChartData.get(i) != 0){
                allZero = false;
            }
        }
        //设置标题
        List<PieEntry> entrys = new ArrayList<>();
        int color [];
        if (!allZero){
            for (int i = 0; i < mPieChartData.size(); i++) {
                entrys.add(new PieEntry(mPieChartData.get(i), i));
            }
            color = new int[]{
                    Color.rgb(251, 114, 14),
                    Color.rgb(55, 162, 218),
                    Color.rgb(159, 230, 184),
                    Color.rgb(255, 219, 92),
                    Color.rgb(224, 98, 174),
                    Color.rgb(230, 144, 209),
                    Color.rgb(255, 159, 127),
                    Color.rgb(231, 188, 243)};
        }else {
            entrys.add(new PieEntry(1, 0));
            color = new int[]{Color.rgb(196,196,196)};
        }


        //饼图数据集
        PieDataSet dataset = new PieDataSet(entrys,null);
        //设置饼状图Item之间的间隙
//        dataset.setSliceSpace(3f);
        //饼图Item被选中时变化的距离
//        dataset.setSelectionShift(10f);
        //颜色填充
        dataset.setColors(color);
        //数据填充
        PieData piedata = new PieData(dataset);
        //设置饼图上显示数据的字体大小
        piedata.setValueTextSize(0);
        mPieChart.setData(piedata);
    }
}
