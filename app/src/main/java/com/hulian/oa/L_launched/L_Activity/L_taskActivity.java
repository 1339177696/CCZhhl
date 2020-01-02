package com.hulian.oa.L_launched.L_Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.Notice_x;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.work.file.admin.activity.task.l_adapter.L_LaunchTaskAdapter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qgl on 2020/1/2 17:10.
 */
public class L_taskActivity extends BaseActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.lm_task)
    PullLoadMoreRecyclerView lmTask;
    @BindView(R.id.emptyBg1)
    ImageView emptyBg1;
    @BindView(R.id.message_list_empty_hint)
    TextView messageListEmptyHint;
    @BindView(R.id.emptyBg)
    RelativeLayout emptyBg;
    private RecyclerView mRecyclerView;
    L_LaunchTaskAdapter mRecyclerViewAdapter;
    private int mCount = 1;
    private String typee = "0"; // 0发起的任务；1执行的任务；2抄送的任务

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_taskactivity);
        ButterKnife.bind(this);
        initList();
    }
    private void initList() {
        //获取mRecyclerView对象
        mRecyclerView = lmTask.getRecyclerView();
        //代码设置scrollbar无效？未解决！
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //设置是否可以下拉刷新
        lmTask.setPullRefreshEnable(true);
        //显示下拉刷新
        lmTask.setRefreshing(true);
        //设置上拉刷新文字
        lmTask.setFooterViewText("loading");
        //设置上拉刷新文字颜色
        //lmTask.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //lmTask.setFooterViewBackgroundColor(R.color.colorBackground);
        lmTask.setLinearLayout();

        lmTask.setOnPullLoadMoreListener(this);
        mRecyclerViewAdapter = new L_LaunchTaskAdapter(L_taskActivity.this);
        lmTask.setAdapter(mRecyclerViewAdapter);
        getData();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
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
        if (mRecyclerViewAdapter!=null){
            mRecyclerViewAdapter.notifyDataSetChanged();
        }
        mRecyclerViewAdapter.clearData();
        mCount = 1;
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("type", typee);
        params.put("loger", SPUtils.get(L_taskActivity.this, "userId", "").toString());
        params.put("pageStart", mCount * 10 - 9 + "");
        params.put("pageEnd", mCount * 10 + "");
        HttpRequest.post_CoordinationRelease_list(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<Notice_x> memberList = gson.fromJson(result.getJSONArray("data").toString(), new TypeToken<List<Notice_x>>() {
                    }.getType());
                    mRecyclerViewAdapter.addAllData(memberList);
                    lmTask.setPullLoadMoreCompleted();
                    if (mCount == 1 && memberList.size() == 0) {
                        emptyBg.setVisibility(View.VISIBLE);
                        lmTask.setVisibility(View.GONE);
                    }
                    else {
                        emptyBg.setVisibility(View.GONE);
                        lmTask.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                Toast.makeText(L_taskActivity.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
