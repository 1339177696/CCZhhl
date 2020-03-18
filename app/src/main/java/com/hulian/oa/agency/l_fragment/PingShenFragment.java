package com.hulian.oa.agency.l_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hulian.oa.R;
import com.hulian.oa.agency.l_adapter.ShenqingAdapter_qgl;
import com.hulian.oa.agency.l_adapter.UpcomAdapter_qgl;
import com.hulian.oa.bean.Daiban_xin_qgl1;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * 创建：  qgl
 * 时间： 2020/03/13
 * 描述： 现场评审列表
 */
public class PingShenFragment extends Fragment implements PullLoadMoreRecyclerView.PullLoadMoreListener{
    Unbinder unbinder;
    @BindView(R.id.lv_pingshen)
    PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    private RecyclerView mRecyclerView;
    ShenqingAdapter_qgl shenqingAdapter_qgl;
    private List<Daiban_xin_qgl1.DataBean> memberList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pingshenfragment, null);
        unbinder = ButterKnife.bind(this, view);
        initList();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initList() {
        //获取mRecyclerView对象
        mRecyclerView = pullLoadMoreRecyclerView.getRecyclerView();
        //代码设置scrollbar无效？未解决！
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //设置下拉刷新是否可见
        pullLoadMoreRecyclerView.setRefreshing(true);
        //设置是否可以下拉刷新
        pullLoadMoreRecyclerView.setPullRefreshEnable(true);
        //设置是否可以上拉刷新
        pullLoadMoreRecyclerView.setPushRefreshEnable(false);
        //显示下拉刷新
        pullLoadMoreRecyclerView.setRefreshing(true);
        //设置上拉刷新文字
        pullLoadMoreRecyclerView.setFooterViewText("loading");
        //设置上拉刷新文字颜色
        //pullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //pullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);
        pullLoadMoreRecyclerView.setLinearLayout();

        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
//        mRecyclerViewAdapter = new UpcomAdapter(getActivity());
        shenqingAdapter_qgl = new ShenqingAdapter_qgl(getActivity());
        pullLoadMoreRecyclerView.setAdapter(shenqingAdapter_qgl);
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
        getData();
    }

    private void setRefresh() {
        if (shenqingAdapter_qgl!=null){
            shenqingAdapter_qgl.notifyDataSetChanged();
        }
        shenqingAdapter_qgl.clearData();
    }


    private void getData(){
        memberList = new ArrayList<Daiban_xin_qgl1.DataBean>();
        for (int i = 0; i <= 3; i++) {
            Daiban_xin_qgl1.DataBean newslistBean = new Daiban_xin_qgl1.DataBean();
            newslistBean.setId(i+"");
            memberList.add(newslistBean);
        }
        shenqingAdapter_qgl.addAllData(memberList);
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }
}
