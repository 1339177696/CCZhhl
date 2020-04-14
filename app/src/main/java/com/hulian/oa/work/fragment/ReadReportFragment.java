package com.hulian.oa.work.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.bean.Report;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.work.adapter.WriteReportAdapter;
import com.hulian.oa.work.activity.ReadReportActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * Created by 陈泽宇 on 2020/3/10.
 * Describe: 汇报列表
 */
public class ReadReportFragment extends Fragment implements  BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.listview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private int mCount = 1;
    Unbinder unbinder;
    private WriteReportAdapter mAdapter;
    private List<Report> mData = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_read_report, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initList();
        return view;
    }

    private void initList() {
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new WriteReportAdapter(mData);
        mAdapter.openLoadAnimation();
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this,mRecyclerView);
        mAdapter.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.list_empty, null));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), ReadReportActivity.class);
                intent.putExtra("report", mData.get(position));
                startActivity(intent);
            }
        });
        getData();

    }


    // 刷新
    public void onEventMainThread(ReadReportFragment event) {
        onRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
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
        params.put("pageStart", mCount*10-10 + "");
        params.put("pageEnd", mCount * 10 + "");
        params.put("createBy", SPUtils.get(getActivity(), "userId", "").toString());
        params.put("receivePerson", SPUtils.get(getActivity(), "userId", "").toString());
        HttpRequest.getGetWorkReportList(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                swipeRefreshLayout.setRefreshing(false);
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
                    mAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(getActivity(), "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onLoadMoreRequested() {

        mCount = mCount + 1;
        getData();
    }
}
