package com.hulian.oa.work.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.Instruct;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.work.activity.instruct.l_adapter.L_InstructAdapter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//工作指令
public class SecondInstructActivity extends BaseActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    @BindView(R.id.recyclerview)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.emptyBg)
    RelativeLayout emptyBg;
    private RecyclerView mRecyclerView;
    L_InstructAdapter mRecyclerViewAdapter;
    private Context mContext;
    private int mCount = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_instruct);
        ButterKnife.bind(this);
        initList();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(mSearchTesk);
                handler.postDelayed(mSearchTesk, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    Handler handler = new Handler();
    SearchTask mSearchTesk = new SearchTask();

    /**
     * 搜索任务
     */
    class SearchTask implements Runnable {

        @Override
        public void run() {
            Log.e("---SearchTask---", "开始查询");
            mRecyclerViewAdapter.clearData();
            mCount = 1;
            getData();
        }
    }


    @OnClick({R.id.iv_back, R.id.rl_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_title:
                finish();
                break;
        }
    }

    private void initList() {

        //获取mRecyclerView对象
        mRecyclerView = mPullLoadMoreRecyclerView.getRecyclerView();
        //代码设置scrollbar无效？未解决！
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //设置下拉刷新是否可见
        //mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置是否可以下拉刷新
        //mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
        //设置是否可以上拉刷新
        //mPullLoadMoreRecyclerView.setPushRefreshEnable(false);
        //显示下拉刷新
        mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置上拉刷新文字
        mPullLoadMoreRecyclerView.setFooterViewText("loading");
        //设置上拉刷新文字颜色
        //mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);
        mPullLoadMoreRecyclerView.setLinearLayout();
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
        mRecyclerViewAdapter = new L_InstructAdapter(this);
        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
        getData();
    }


    @Override
    public void onRefresh() {
        Log.e("wxl", "onRefresh");
        setRefresh();
        getData();
    }

    @Override
    public void onLoadMore() {
        Log.e("wxl", "onLoadMore");
        mCount = mCount + 1;
        getData();
    }

    private void setRefresh() {
        mRecyclerViewAdapter.clearData();
        mCount = 1;
    }

    private void getData() {
        String userid = SPUtils.get(this, "userId", "-1").toString();
        RequestParams params = new RequestParams();
        if ("0".equals(SPUtils.get(this, "isLead", "-1").toString())) {
            params.put("createBy", userid);
        } else {
            params.put("receiver", userid);
        }
        params.put("pageStart", mCount * 10 - 9 + "");
        params.put("pageEnd", mCount * 10 + "");
        params.put("content", search.getText() + "");

        HttpRequest.postSecondInstructApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<Instruct> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<Instruct>>() {
                            }.getType());
                    if (mCount == 1 && memberList.size() == 0) {
                        emptyBg.setVisibility(View.VISIBLE);
                        mPullLoadMoreRecyclerView.setVisibility(View.GONE);
                    } else {
                        emptyBg.setVisibility(View.GONE);
                        mPullLoadMoreRecyclerView.setVisibility(View.VISIBLE);
                    }
                    mRecyclerViewAdapter.addAllData(memberList);
                    mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setRefresh();
        getData();
    }
}
