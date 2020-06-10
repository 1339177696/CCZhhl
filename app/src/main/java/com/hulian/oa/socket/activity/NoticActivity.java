package com.hulian.oa.socket.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.bean.Report;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.socket.adapter.NoticeActivityAdapter;
import com.hulian.oa.socket.adapter.NoticeMeetingAdaoter;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：qgl 时间： 2020/5/20 08:58
 * Describe: 消息通知
 */
public class NoticActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private NoticeActivityAdapter noticeActivityAdapter;
    private List<Report> mData = new ArrayList<>();
    private int mCount = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.notticactivity);
        ButterKnife.bind(this);
        initList();
    }

    private void initList() {
        swipeRefreshLayout.setOnRefreshListener(this);
        noticeActivityAdapter = new NoticeActivityAdapter(mData);
        noticeActivityAdapter.openLoadAnimation();
        noticeActivityAdapter.setEnableLoadMore(true);
        noticeActivityAdapter.setOnLoadMoreListener(this, listview);
        noticeActivityAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.list_empty, null));
        listview.setLayoutManager(new LinearLayoutManager(this));
        listview.setAdapter(noticeActivityAdapter);
        noticeActivityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(NoticActivity.this,NoticeMeetingActivity.class));
            }
        });
        getData();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
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
    @Override
    public void onLoadMoreRequested() {
        mCount = mCount + 1;
        getData();
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("pageStart", mCount * 10 - 10 + "");
        params.put("pageEnd", mCount * 10 + "");
        params.put("createBy", SPUtils.get(this, "userId", "").toString());
        params.put("receivePerson", SPUtils.get(this, "userId", "").toString());
        HttpRequest.getGetWorkReportList(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                swipeRefreshLayout.setRefreshing(false);
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<Report> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<Report>>() {}.getType());
                    mData.addAll(memberList);
                    if (memberList.size() < 10) {
                        noticeActivityAdapter.loadMoreEnd();
                    } else {
                        noticeActivityAdapter.loadMoreComplete();
                    }
                    noticeActivityAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(NoticActivity.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
