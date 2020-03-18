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
import com.hulian.oa.bean.Leave;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.work.file.admin.activity.leave.l_adapter.L_LeaveApplyLaunchAdapter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qgl on 2020/1/2 17:17.
 */
public class L_leaveActivity extends BaseActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener{
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.lm_leave)
    PullLoadMoreRecyclerView lmLeave;
    @BindView(R.id.emptyBg1)
    ImageView emptyBg1;
    @BindView(R.id.message_list_empty_hint)
    TextView messageListEmptyHint;
    @BindView(R.id.emptyBg)
    RelativeLayout emptyBg;

    private int mCount = 1;
    private RecyclerView mRecyclerView;
    L_LeaveApplyLaunchAdapter mRecyclerViewAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_leaveactivity);
        ButterKnife.bind(this);
        initList();
    }
    private void initList() {

        //获取mRecyclerView对象
        mRecyclerView = lmLeave.getRecyclerView();
        //代码设置scrollbar无效？未解决！
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //设置下拉刷新是否可见
        //lmLeave.setRefreshing(true);
        //设置是否可以下拉刷新
        //lmLeave.setPullRefreshEnable(true);
        //设置是否可以上拉刷新
        //lmLeave.setPushRefreshEnable(false);
        //显示下拉刷新
        lmLeave.setRefreshing(true);
        //设置上拉刷新文字
        lmLeave.setFooterViewText("loading");
        //设置上拉刷新文字颜色
        //lmLeave.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //lmLeave.setFooterViewBackgroundColor(R.color.colorBackground);
        lmLeave.setLinearLayout();

        lmLeave.setOnPullLoadMoreListener(this);
        mRecyclerViewAdapter = new L_LeaveApplyLaunchAdapter(L_leaveActivity.this);
        lmLeave.setAdapter(mRecyclerViewAdapter);
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
        params.put("pageStart", mCount*10-9 + "");
        params.put("pageEnd", mCount * 10 + "");
        params.put("createBy", SPUtils.get(L_leaveActivity.this, "userId", "").toString());
        HttpRequest.get_listWorkLeave(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<Leave> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<Leave>>() {
                            }.getType());
                    mRecyclerViewAdapter.addAllData(memberList);
                    if (mCount == 1 && memberList.size() == 0) {
                        emptyBg.setVisibility(View.VISIBLE);
                        lmLeave.setVisibility(View.GONE);
                    } else {
                        emptyBg.setVisibility(View.GONE);
                        lmLeave.setVisibility(View.VISIBLE);
                    }
                    lmLeave.setPullLoadMoreCompleted();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                Toast.makeText(L_leaveActivity.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
