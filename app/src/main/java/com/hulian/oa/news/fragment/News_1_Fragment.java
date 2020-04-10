package com.hulian.oa.news.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.library.banner.layoutmanager.CenterScrollListener;
import com.example.library.banner.layoutmanager.OverFlyingLayoutManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.bean.News;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.news.adapter.LocalDataAdapter;
import com.hulian.oa.news.adapter.NewsViewAdapter;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.views.LoadingDialog;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

public class News_1_Fragment extends Fragment implements OnBannerListener, PullLoadMoreRecyclerView.PullLoadMoreListener {
    OverFlyingLayoutManager mOverFlyingLayoutManager;
    Handler mHandler;
    Runnable mRunnable;
    int currentPosition = 0;
    @BindView(R.id.listview)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private int mCount = 1;
    private RecyclerView mRecyclerView;
    NewsViewAdapter mRecyclerViewAdapter;
    private ArrayList<String> list_path = new ArrayList<>();
    private ArrayList<String> list_title;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recycler_banner)
    RecyclerView recycler_banner;
    Unbinder unbinder;
    View rootView;
    protected LoadingDialog loadDialog;//加载等待弹窗

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_news_1, null);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        loadDialog = new LoadingDialog(getActivity());
        initList();
        return rootView;
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
        mRecyclerViewAdapter = new NewsViewAdapter(getActivity());
        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);

        getData();

    }

    private void initView() {
        mOverFlyingLayoutManager = new OverFlyingLayoutManager(0.75f, 385, OverFlyingLayoutManager.HORIZONTAL);
        recycler_banner.setAdapter(new LocalDataAdapter(getActivity(), list_path));
        recycler_banner.setLayoutManager(mOverFlyingLayoutManager);
        recycler_banner.addOnScrollListener(new CenterScrollListener());
        mOverFlyingLayoutManager.setOnPageChangeListener(new OverFlyingLayoutManager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                currentPosition++;
                Log.d("recyclerBanner", currentPosition + " ");
                mOverFlyingLayoutManager.scrollToPosition(currentPosition);
                //  recyclerView.smoothScrollToPosition(currentPosition);
                mHandler.postDelayed(this, 3000);
            }
        };
        mHandler.postDelayed(mRunnable, 3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);

    }

    @OnClick(R.id.banner)
    public void onViewClicked() {
    }

    @Override
    public void OnBannerClick(int position) {
        //   Toast.makeText(getActivity(), "你点了第" + (position + 1) + "张轮播图", Toast.LENGTH_SHORT).show();
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

    //新加的用户IDqgl
    private void getData() {
        loadDialog.setCancelable(false);
        loadDialog.show();
        RequestParams params = new RequestParams();
        params.put("pageState", mCount * 10 - 9 + "");
        params.put("pageEnd", mCount * 10 + "");
        params.put("createBy", SPUtils.get(getActivity(), "userId", "").toString());
        HttpRequest.postNesListApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                loadDialog.dismiss();
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<News> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<News>>() {
                            }.getType());
                    mRecyclerViewAdapter.addAllData(memberList);
                    mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                    if (mCount == 1) {
                        list_path.removeAll(list_path);
                        list_path.add(memberList.get(0).getJournalismImage());
                        list_path.add(memberList.get(1).getJournalismImage());
                        list_path.add(memberList.get(2).getJournalismImage());
                        initView();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                loadDialog.dismiss();

                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                Toast.makeText(getActivity(), "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onEventMainThread(News_1_Fragment event) {
        onRefresh();
    }

}
