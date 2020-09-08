package com.hulian.oa.work.activity.expense;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.bean.ExpenisBean;
import com.hulian.oa.bean.ExpenseBean;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.activity.expense.l_adapter.L_ExpenseHistoryAdapterS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//报销历史
public class ExpenseHistoryActivityS extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private int mCount = 1;
    @BindView(R.id.recycleview_history)
    RecyclerView recycleview_history;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    L_ExpenseHistoryAdapterS adapter;
    private List<ExpenisBean> mData = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.work_expense_historys);
        ButterKnife.bind(this);
        initList();
    }

    private void initList() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        adapter = new L_ExpenseHistoryAdapterS(mData);
        adapter.openLoadAnimation();
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(this, recycleview_history);
        adapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.list_empty, null));
        recycleview_history.setLayoutManager(new LinearLayoutManager(this));
        recycleview_history.setAdapter(adapter);
        getData();

    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        setRefresh();
        getData();

    }


    private void setRefresh() {
        mData.clear();
        mCount = 1;
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("pageStart", mCount * 10 - 10 + "");
        params.put("pageEnd", mCount * 10 + "");
        params.put("createBy", getIntent().getStringExtra("ID"));
        HttpRequest.get_WorkExpense_history1(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                mSwipeRefreshLayout.setRefreshing(false);
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();

                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<ExpenisBean> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<ExpenisBean>>() {
                            }.getType());
                    mData.addAll(memberList);
                    if (memberList.size() < 10) {
                        adapter.loadMoreEnd();
                    } else {
                        adapter.loadMoreComplete();
                    }
                    adapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(ExpenseHistoryActivityS.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onLoadMoreRequested() {
        mCount = mCount + 1;
        getData();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {

        finish();
    }
}
