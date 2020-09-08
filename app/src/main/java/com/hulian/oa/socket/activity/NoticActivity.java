package com.hulian.oa.socket.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.bean.Messagebean;
import com.hulian.oa.bean.Report;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.push.activity.MessagenotificationActivity;
import com.hulian.oa.push.bean.MeBean;
import com.hulian.oa.socket.adapter.NoticeActivityAdapter;
import com.hulian.oa.socket.adapter.NoticeMeetingAdaoter;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：qgl 时间： 2020/5/20 08:58
 * Describe: 消息通知
 */
public class NoticActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private NoticeActivityAdapter noticeActivityAdapter;
    private List<MeBean> mData = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.notticactivity);
        ButterKnife.bind(this);
        initList();
    }

    private void initList() {
        swipeRefreshLayout.setOnRefreshListener(this);
        noticeActivityAdapter = new NoticeActivityAdapter(mData);
        noticeActivityAdapter.openLoadAnimation();
        noticeActivityAdapter.setEnableLoadMore(true);
        noticeActivityAdapter.setOnLoadMoreListener(this, listview);
        noticeActivityAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.list_empty, null));
        listview.setLayoutManager(new LinearLayoutManager(this));
        listview.setAdapter(noticeActivityAdapter);
        noticeActivityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (mData.get(position).getType()){
                    case "1":
                        //邮件详情
                        Intent intent = new Intent(NoticActivity.this,NotiListActivity.class);
                        intent.putExtra("title","邮件通知");
                        intent.putExtra("type","1");
                        startActivityForResult(intent,1);
                        break;
                    case "3":
                    case "7":
                    case "8":
                        // 工作通知
                        Intent intent1 = new Intent(NoticActivity.this,NoticeWorkActivity.class);
                        intent1.putExtra("title","工作通知");
                        intent1.putExtra("type","9");
                        startActivityForResult(intent1,9);
                        break;
                    case "4":
                        //会议通知
                        Intent intent4 = new Intent(NoticActivity.this,NoticeMeetingActivity.class);
                        intent4.putExtra("title","会议通知");
                        intent4.putExtra("type","4");
                        startActivityForResult(intent4,4);
                        break;
                    case "5":
                        // 通告
                        Intent intent2 = new Intent(NoticActivity.this,NotiListActivity.class);
                        intent2.putExtra("title","通告通知");
                        intent2.putExtra("type","5");
                        startActivityForResult(intent2,5);
                        break;
                    case "6":
                        Intent intent3 = new Intent(NoticActivity.this,NotiListActivity.class);
                        intent3.putExtra("title","日程通知");
                        intent3.putExtra("type","6");
                        startActivityForResult(intent3,6);
                        break;
                }
            }
        });
        getData();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        setRefresh();
        getData();
    }

    private void setRefresh() {
        mData.clear();

    }
    @Override
    public void onLoadMoreRequested() {
        getData();
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("userId", SPUtils.get(this, "userId", "").toString());
        HttpRequest.postNotice(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                swipeRefreshLayout.setRefreshing(false);
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<MeBean> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<MeBean>>() {}.getType());
                    mData.addAll(memberList);
                    noticeActivityAdapter.loadMoreEnd();
                    noticeActivityAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 回到前台刷洗一下
         onRefresh();
    }
}
