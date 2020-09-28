package com.hulian.oa.work.activity.expense.l_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.bean.Expense;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.gallery.DisplayUtils;
import com.hulian.oa.views.CustomDecoration;
import com.hulian.oa.work.activity.expense.l_adapter.L_ExpenseApplyLaunchAdapter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

//  抄送我的
public class ExpenseCopymeFragment extends Fragment implements PullLoadMoreRecyclerView.PullLoadMoreListener {
    @BindView(R.id.recyclerView)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private int mCount = 1;
    private RecyclerView mRecyclerView;
    L_ExpenseApplyLaunchAdapter mRecyclerViewAdapter;
    Unbinder unbinder;
//    qgl修改的
    @BindView(R.id.emptyBg)
    RelativeLayout emptyBg;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.l_fra_copyme_leave,null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initList();
        return view;
    }
    public void onEventMainThread(ExpenseCopymeFragment event) {
        onRefresh();
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
//        mPullLoadMoreRecyclerView.addItemDecoration(new CustomDecoration(getContext(),CustomDecoration.VERTICAL_LIST,R.drawable.devide_line, DisplayUtils.dip2px(getContext(),15)));
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
        mRecyclerViewAdapter = new L_ExpenseApplyLaunchAdapter(getActivity());
        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
        getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onRefresh() {
        Log.e("报销管理抄送我的", "onRefresh");
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
        Log.e("报销管理抄送我的", "getData");
        RequestParams params = new RequestParams();
        params.put("pageStart", mCount*10-9 + "");
        params.put("pageEnd", mCount * 10 + "");
        params.put("copier", SPUtils.get(getActivity(), "userId", "").toString());
        HttpRequest.get_listWorkExpense(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<Expense> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<Expense>>() {
                            }.getType());
                    mRecyclerViewAdapter.addAllData(memberList);
                    if (mCount == 1 && memberList.size() == 0) {
                        emptyBg.setVisibility(View.VISIBLE);
                    } else {
                        emptyBg.setVisibility(View.GONE);
                    }
                    mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
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


}
