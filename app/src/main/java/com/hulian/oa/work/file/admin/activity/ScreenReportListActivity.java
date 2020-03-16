package com.hulian.oa.work.file.admin.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.Report;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.adapter.WriteReportAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 陈泽宇 on 2020/3/13.
 * Describe: 汇报筛选列表
 */
public class ScreenReportListActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    private int mCount = 1;

    private WriteReportAdapter mAdapter;
    private List<Report> mData = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.activity_report_list);
        ButterKnife.bind(this);
        initList();
    }

    private void initList() {
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new WriteReportAdapter(mData);
        mAdapter.openLoadAnimation();
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, listview);
        listview.setLayoutManager(new LinearLayoutManager(ScreenReportListActivity.this));
        listview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ScreenReportListActivity.this, ReadReportActivity.class);
                intent.putExtra("report", mData.get(position));
                startActivity(intent);
            }
        });

        getData();

    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        setRefresh();
        getData();
    }


    private void setRefresh() {
        mData.clear();
        mCount = 1;
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("createBy", getIntent().getStringExtra("createBy"));
        params.put("beginDate", getIntent().getStringExtra("params.beginDate"));
        params.put("endDate", getIntent().getStringExtra("params.endDate"));
        params.put("pageStart", mCount * 10 - 9 + "");
        params.put("pageEnd", mCount * 10 + "");
        HttpRequest.getGetWorkReportList(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<Report> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<Report>>() {
                            }.getType());
                    mData.addAll(memberList);
                    if (memberList.size()<10){
                        mAdapter.loadMoreEnd();
                    }else {
                        mAdapter.loadMoreComplete();
                    }
//                   mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                Toast.makeText(ScreenReportListActivity.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });


//        Report report1 = new Report();
//        report1.setName("王俊杰");
//        report1.setFinishWork("设计oa报销页面");
//        report1.setUnFinishWork("设计飞机场");
//        report1.setTime("2020-02-28 09:52");
//        report1.setType("1");
//
//        mData.add(report1);
//        Report report2 = new Report();
//        report2.setName("王俊杰1");
//        report2.setFinishWork("设计oa报销页面");
//        report2.setUnFinishWork("设计飞机场");
//        report2.setTime("2020-02-28 09:52");
//        report2.setType("1");
//        mData.add(report2);
//        Report report3 = new Report();
//        report3.setName("王俊杰2");
//        report3.setFinishWork("设计oa报销页面");
//        report3.setUnFinishWork("设计飞机场");
//        report3.setTime("2020-02-28 09:52");
//        report3.setType("2");
//        mData.add(report3);
//        Report report4 = new Report();
//        report4.setName("王俊杰3");
//        report4.setFinishWork("设计oa报销页面");
//        report4.setUnFinishWork("设计飞机场");
//        report4.setTime("2020-02-28 09:52");
//        report4.setType("3");
//        mData.add(report4);
//        mAdapter.notifyDataSetChanged();
//        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onLoadMoreRequested() {
        mCount = mCount + 1;
        getData();
        mAdapter.loadMoreEnd();
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
