package com.hulian.oa.work.file.admin.activity.task.l_details_activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
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
import com.hulian.oa.bean.Hufu_bean;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.file.admin.activity.task.l_details_adapter.L_DetailsCompletedTaskAdapter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//已完成详情
public class TaskCompletedDetailsActivity extends BaseActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    @BindView(R.id.listview)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    @BindView(R.id.iv_back)
    RelativeLayout iv_back;
    @BindView(R.id.la_un_start_time)
    TextView laUnStartTime;
    @BindView(R.id.la_un_stop_time)
    TextView laUnStopTime;
    private int mCount = 1;
    private RecyclerView mRecyclerView;
    L_DetailsCompletedTaskAdapter mRecyclerViewAdapter;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    private String porid;
    private String id;
    private Hufu_bean huifu_bean_x;
    private List<Hufu_bean> list = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.l_activity_completed_details_task);
        ButterKnife.bind(this);
        porid = getIntent().getStringExtra("PORID");
        id = getIntent().getStringExtra("ID");
        mContext = this;
        initList();
    }

    private void initList() {

        //获取mRecyclerView对象
        mRecyclerView = mPullLoadMoreRecyclerView.getRecyclerView();
//        mRecyclerView.setNestedScrollingEnabled(false);
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
        mRecyclerViewAdapter = new L_DetailsCompletedTaskAdapter(this);
        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
        getData();

    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
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
//    private void getData() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mRecyclerViewAdapter.addAllData(setList());
//                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
//                    }
//                });
//
//            }
//        }, 1000);
//
//    }

//  private List<String> setList() {
//        List<String> dataList = new ArrayList<>();
//        int start = 20 * (mCount - 1);
//        for (int i = start; i < 20 * mCount; i++) {
//            dataList.add("Frist" + i);
//        }
//        return dataList;
//
//    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("type", "1");
        HttpRequest.post_workCoordinationRelease_details(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                //需要转化为实体对象
                try {

                    JSONObject result = new JSONObject(responseObj.toString());
                    list = gson.fromJson(result.getJSONObject("data").getString("list"), new TypeToken<List<Hufu_bean>>() {
                    }.getType());
                    huifu_bean_x = gson.fromJson(result.getJSONObject("data").getString("object"), new TypeToken<Hufu_bean>() {
                    }.getType());
                    String sum = result.getJSONObject("data").getString("sum");
                    huifu_bean_x.setSum(sum);

                    JSONObject jsonObject = new JSONObject(result.getJSONObject("data").getString("object"));
                    String a = jsonObject.getString("startTime");
                    laUnStartTime.setText(a.replace("-","/"));
                    String b = jsonObject.getString("endTime");
                    laUnStopTime.setText(b.replace("-","/"));

                    mRecyclerViewAdapter.addAllData(list, huifu_bean_x);
                    mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
