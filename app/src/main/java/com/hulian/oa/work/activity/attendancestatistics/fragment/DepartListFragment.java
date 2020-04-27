package com.hulian.oa.work.activity.attendancestatistics.fragment;

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
import com.hulian.oa.work.activity.attendancestatistics.activity.ClockDetailsActivity;
import com.hulian.oa.work.adapter.AttenListAdapter;
import com.hulian.oa.work.adapter.DepartListAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：qgl 时间： 2020/4/22 09:56
 * Describe: 部门统计列表
 */
public class DepartListFragment extends Fragment implements  BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.listview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private int mCount = 1;
    Unbinder unbinder;
    private DepartListAdapter mAdapter;
    private List<Report> mData = new ArrayList<>();
    private int cd = 0;
    // TODO: Rename and change types and number of parameters
    public  static DepartListFragment newInstance(String code) {
        DepartListFragment fragment = new DepartListFragment();
        Bundle args = new Bundle();
        args.putString("param", code);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.attenlistfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        String mParam1 = getArguments().getString("param");
        cd = Integer.parseInt(mParam1);
        initList();
        return view;
    }

    private void initList() {
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new DepartListAdapter(mData);
        mAdapter.openLoadAnimation();
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.list_empty, null));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), ClockDetailsActivity.class));
            }
        });
        mData.clear();
        getData();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
        params.put("pageStart", mCount * 10 - 10 + "");
        params.put("pageEnd", mCount * 10 + "");
        params.put("createBy", SPUtils.get(getActivity(), "userId", "").toString());
        params.put("receivePerson", SPUtils.get(getActivity(), "userId", "").toString());
        params.put("ok",String.valueOf(cd));
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
                    if (memberList.size() < 10) {
                        mAdapter.loadMoreEnd();
                    } else {
                        mAdapter.loadMoreComplete();
                    }
                    mAdapter.notifyDataSetChanged();
                    ((DepartmentattendanceFragment) (DepartListFragment.this.getParentFragment())).setListSize(mData.size(),cd);

                } catch (Exception e) {
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
