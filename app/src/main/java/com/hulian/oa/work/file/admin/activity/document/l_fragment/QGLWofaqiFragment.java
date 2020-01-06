package com.hulian.oa.work.file.admin.activity.document.l_fragment;

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
import com.hulian.oa.bean.Document;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.L_ChaosongAdapter;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.L_WoFaqiAdapter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * Created by qgl on 2019/10/16 9:17.
 */
public class QGLWofaqiFragment extends Fragment implements PullLoadMoreRecyclerView.PullLoadMoreListener
{
    @BindView(R.id.lv_notice)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    @BindView(R.id.emptyBg)
    RelativeLayout emptyBg;
    private int mCount = 1;
    private RecyclerView mRecyclerView;
    private L_WoFaqiAdapter l_woFaqiAdapter;
    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.qglwofaqifragment, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initList();
        return view;
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
        mPullLoadMoreRecyclerView.setPushRefreshEnable(false);
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
        l_woFaqiAdapter = new L_WoFaqiAdapter(getActivity(),"1");
        mPullLoadMoreRecyclerView.setAdapter(l_woFaqiAdapter);
        getData();
    }

    @Override
    public void onRefresh() {
        Log.e("wxl", "onRefresh");
        setRefresh();
        getData();
    }
    private void setRefresh() {
        l_woFaqiAdapter.clearData();
        mCount = 1;
    }
    @Override
    public void onLoadMore() {

    }

//    @Override
//    public void onDestroy()
//    {
//        unbinder.unbind();
//        super.onDestroy();
//    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    // 返回刷新
    public void onEventMainThread(QGLWofaqiFragment event) {
        onRefresh();
    }
    private void getData() {
        RequestParams params = new RequestParams();
        params.put("createBy", SPUtils.get(getActivity(), "userId", "").toString());
        HttpRequest.postWoFaqi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<Document> memberList = gson.fromJson(result.getJSONArray("rows").toString(),
                            new TypeToken<List<Document>>() {
                            }.getType());
                    l_woFaqiAdapter.addAllData(memberList);
                    mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                    if(memberList.size()==0&&mCount==1){
                        emptyBg.setVisibility(View.VISIBLE);
//                        mPullLoadMoreRecyclerView.setVisibility(View.GONE);
                    }
                    else {
                        emptyBg.setVisibility(View.GONE);
//                        mPullLoadMoreRecyclerView.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                Toast.makeText(getActivity(), "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
