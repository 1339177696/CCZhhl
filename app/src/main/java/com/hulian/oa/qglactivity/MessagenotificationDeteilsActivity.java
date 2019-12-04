package com.hulian.oa.qglactivity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.Leave;
import com.hulian.oa.bean.SecondMail_bean_x;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.qglactivity.qgladpter.qglTuisongadapter;
import com.hulian.oa.qglactivity.qglbean.MeBean;
import com.hulian.oa.utils.SPUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qgl on 2019/11/29 11:14.
 */
public class MessagenotificationDeteilsActivity extends BaseActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {
    private String Type;
    @BindView(R.id.message_title)
    TextView message_title;
    @BindView(R.id.listview)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private RecyclerView mRecyclerView;
    private qglTuisongadapter qglTuisongadapter;
    private int mCount = 1;
    private String ty = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagenotificationdeteilsactivity);
        ButterKnife.bind(this);
        Type = getIntent().getStringExtra("type");
        ty = getIntent().getStringExtra("ty");
        initview();
        initList();
        getDatalist();
    }

    private void initview() {
        message_title.setText(Type);
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
        mPullLoadMoreRecyclerView.setRefreshing(false);
        //设置上拉刷新文字
        mPullLoadMoreRecyclerView.setFooterViewText("loading");
        //设置上拉刷新文字颜色
        //mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);
        mPullLoadMoreRecyclerView.setLinearLayout();
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
        qglTuisongadapter = new qglTuisongadapter(MessagenotificationDeteilsActivity.this);
        mPullLoadMoreRecyclerView.setAdapter(qglTuisongadapter);
        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

    }


    @Override
    public void onRefresh() {
        Log.e("wxl", "onRefresh");
        setRefresh();
        getDatalist();
    }

    @Override
    public void onLoadMore() {
        mCount = mCount + 1;
    }

    private void setRefresh() {
        qglTuisongadapter.clearData();
        mCount = 1;
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    //获取消息详情
    private void getDatalist(){
        RequestParams params = new RequestParams();
        params.put("userId", SPUtils.get(MessagenotificationDeteilsActivity.this, "userId", "").toString());
        params.put("type", ty);
        HttpRequest.postNotice_List(params, new ResponseCallback(){
            @Override
            public void onSuccess(Object responseObj) {
                Log.e("详情",responseObj.toString());
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<MeBean> memberList = gson.fromJson(result.getJSONArray("data").toString(), new TypeToken<List<MeBean>>() {}.getType());
                    qglTuisongadapter.addAllData(memberList);
                    mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }
}
