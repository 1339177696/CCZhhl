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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.Meeting;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.work.file.admin.activity.meeting.l_adapter.MeetLaunchAdapter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qgl on 2020/1/2 15:08.
 */
public class L_meetingActivity extends BaseActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener{
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.lm_meeting)
    PullLoadMoreRecyclerView lmMeeting;
    @BindView(R.id.emptyBg1)
    ImageView emptyBg1;
    @BindView(R.id.message_list_empty_hint)
    TextView messageListEmptyHint;
    @BindView(R.id.emptyBg)
    RelativeLayout emptyBg;
    private RecyclerView mRecyclerView;
    MeetLaunchAdapter mRecyclerViewAdapter;
    private int mCount = 1;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_meetingactivity);
        ButterKnife.bind(this);
        initList();
    }

    private void initList() {
        //获取mRecyclerView对象
        mRecyclerView = lmMeeting.getRecyclerView();
        //代码设置scrollbar无效？未解决！
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //设置下拉刷新是否可见
        //mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置是否可以下拉刷新
        //mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
        //设置是否可以上拉刷新
        //mPullLoadMoreRecyclerView.setPushRefreshEnable(false);
        //显示下拉刷新
        lmMeeting.setRefreshing(true);
        //设置上拉刷新文字
        lmMeeting.setFooterViewText("loading");
        //设置上拉刷新文字颜色
        //mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);
        lmMeeting.setLinearLayout();

        lmMeeting.setOnPullLoadMoreListener(this);
        mRecyclerViewAdapter = new MeetLaunchAdapter(L_meetingActivity.this);
        lmMeeting.setAdapter(mRecyclerViewAdapter);
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
        String userid = SPUtils.get(L_meetingActivity.this, "userId", "-1").toString();
        RequestParams params = new RequestParams();
        params.put("createBy", userid);
        params.put("page1", mCount * 10 - 9 + "");
        params.put("page2", mCount * 10 + "");
        HttpRequest.postMyCreateMeeting(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<Meeting> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<Meeting>>() {
                            }.getType());
                    if (mCount == 1 && memberList.size() == 0) {
                        emptyBg.setVisibility(View.VISIBLE);
                        lmMeeting.setVisibility(View.GONE);
                    } else {
                        emptyBg.setVisibility(View.GONE);
                        lmMeeting.setVisibility(View.VISIBLE);
                    }
                    mRecyclerViewAdapter.addAllData(memberList);
                    lmMeeting.setPullLoadMoreCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }

}
