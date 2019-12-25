package com.hulian.oa.qglactivity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.qglactivity.qgladpter.QglTuisongadapter;
import com.hulian.oa.qglactivity.qglbean.MeBean;
import com.hulian.oa.utils.SPUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

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
    private QglTuisongadapter qglTuisongadapter;
    private int mCount = 1;
    private String ty = "";
    @BindView(R.id.emptyBg)
    RelativeLayout im_empty;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagenotificationdeteilsactivity);
        ButterKnife.bind(this);
        Type = getIntent().getStringExtra("type");
        ty = getIntent().getStringExtra("ty");
        initview();
        initList();
    }

    private void initview() {
        message_title.setText(Type);
        if (ty.equals("6")){
            getrcStatus();
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
        mPullLoadMoreRecyclerView.setRefreshing(false);
        //设置上拉刷新文字
        mPullLoadMoreRecyclerView.setFooterViewText("loading");
        //设置上拉刷新文字颜色
        //mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);
        mPullLoadMoreRecyclerView.setLinearLayout();
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
        qglTuisongadapter = new QglTuisongadapter(MessagenotificationDeteilsActivity.this);
        mPullLoadMoreRecyclerView.setAdapter(qglTuisongadapter);
        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

        getDatalist();
    }


    @Override
    public void onRefresh() {
        Log.e("wxl", "onRefresh");
        setRefresh();
    }

    @Override
    public void onLoadMore() {
        mCount = mCount + 1;
    }

    private void setRefresh() {
        if (qglTuisongadapter!=null){
            qglTuisongadapter.notifyDataSetChanged();
        }
        qglTuisongadapter.clearData();
        mCount = 1;
        getDatalist();
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

                    //判断是否有数据
                    if (mCount == 1 && memberList.size() == 0) {
                        im_empty.setVisibility(View.VISIBLE);
                    } else {
                        im_empty.setVisibility(View.GONE);
                    }
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

    //修改已读状态
    private void getrcStatus(){
        RequestParams params = new RequestParams();
        params.put("type",ty);
        params.put("userId", SPUtils.get(mContext, "userId", "").toString());
        HttpRequest.getrcYiDu(params, new ResponseCallback(){
            @Override
            public void onSuccess(Object responseObj) {
                Log.e("成功",responseObj.toString());
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }
}
