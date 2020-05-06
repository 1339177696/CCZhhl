package com.hulian.oa.work.activity.expense;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.activity.expense.l_adapter.ExpenseExamineAdapterS;
import com.hulian.oa.work.activity.expense.l_adapter.L_ExpenseHistoryAdapterS;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpenseHistoryActivityS extends BaseActivity {

    @BindView(R.id.recycleview_history)
    RecyclerView recycleview_history;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private int mNextRequestPage = 1;
    private static final int PAGE_SIZE = 6;
    L_ExpenseHistoryAdapterS adapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.work_expense_historys);
        ButterKnife.bind(this);
        initAdapter();
        initRefreshLayout();
        mSwipeRefreshLayout.setRefreshing(true);
    }
    private void initAdapter() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recycleview_history.setLayoutManager(new LinearLayoutManager(this));

        adapter = new L_ExpenseHistoryAdapterS();
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        },recycleview_history);
    }

    //加载更多
    private void loadMore() {

    }

    //初始化刷新
    private void initRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    //获取第一页的数据
    private void refresh() {
        mNextRequestPage = 1;
        adapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载

    }


    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            adapter.setNewData(data);
        } else {
            if (size > 0) {
                adapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            adapter.loadMoreEnd(isRefresh);
            Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            adapter.loadMoreComplete();
        }
    }
}
