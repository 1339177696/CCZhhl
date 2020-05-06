package com.hulian.oa.work.activity.expense;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.agency.fragment.UpcomFragment;
import com.hulian.oa.bean.People;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.FullyGridLayoutManager;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.views.AlertDialog;
import com.hulian.oa.work.activity.expense.l_adapter.ExpenseExamineAdapter;
import com.hulian.oa.work.activity.expense.l_adapter.ExpenseExamineAdapterS;
import com.hulian.oa.work.activity.expense.l_fragment.ExpenseApprovedFragment;
import com.hulian.oa.work.activity.expense.l_fragment.ExpensePendFragment;
import com.hulian.oa.work.activity.leave.SelDepartmentActivity_Leave;
import com.hulian.oa.work.activity.mypreview.PicturePreviewActivity;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 报销审批详情改版内容
 */
public class ExpenseExamineActivityS extends BaseActivity {
    @BindView(R.id.ci_title)
    ImageView ci_title;//申请人头像
    @BindView(R.id.tv_applicant)
    TextView tv_applicant;//申请人
    @BindView(R.id.tv_state)
    TextView tv_state;//申请状态
    @BindView(R.id.tv_reject_reson)
    TextView tv_reject_reson;//驳回原因
    @BindView(R.id.tv_reject_time)
    TextView tv_reject_time;//驳回原因
    @BindView(R.id.tv_expense_title)
    TextView tv_expense_title;//报销申请标题
    @BindView(R.id.tv_expense_time)
    TextView tv_expense_time;//报销申请时间
    @BindView(R.id.tv_department)
    TextView tv_department;//申请人所在部门
    @BindView(R.id.tv_bill_count)
    TextView tv_bill_count;//发票张数
    @BindView(R.id.tv_expense_money)
    TextView tv_expense_money;//报销金额
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycleview_expense)
    RecyclerView recycleview_expense;
    private int mNextRequestPage = 1;
    private static final int PAGE_SIZE = 6;
    ExpenseExamineAdapterS adapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.work_expense_result_s);
        ButterKnife.bind(this);
        initAdapter();
        initRefreshLayout();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void initAdapter() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recycleview_expense.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ExpenseExamineAdapterS();
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        },recycleview_expense);
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
