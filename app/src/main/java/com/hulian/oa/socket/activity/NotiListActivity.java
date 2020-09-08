package com.hulian.oa.socket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.me.activity.ScheduleActivity;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.push.bean.MeBean;
import com.hulian.oa.socket.adapter.NoticeTonggaoAdaoter;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.work.activity.notice.NoticeParticularsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：qgl 时间： 2020/7/7 08:54
 * Describe: 通知列表
 */
public class NotiListActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.notice_tv_title)
    TextView noticeTvTitle;
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private String title;
    private String type;
    private NoticeTonggaoAdaoter mAdapter;
    private List<MeBean> mData = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notlistactivity);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        noticeTvTitle.setText(title);
        initList();

    }

    private void initList() {
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new NoticeTonggaoAdaoter(mData);
        mAdapter.openLoadAnimation();
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, listview);
        mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.list_empty, null));
        listview.setLayoutManager(new LinearLayoutManager(this));
        listview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mData.get(position).getType().equals("5")){
                    // 通告
                    Intent intent=new Intent(mContext, NoticeParticularsActivity.class);
                    intent.putExtra("noticeId",mData.get(position).getRelationId());
                    intent.putExtra("isCollect",mData.get(position).getCollectionStatus());
                    mContext.startActivity(intent);
                }else if (mData.get(position).getType().equals("1")){
                    // 邮件
                }else if (mData.get(position).getType().equals("6")){
                    startActivity(new Intent(mContext, ScheduleActivity.class));
                    //日程没有详情
                }
            }
        });
        getData();
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
        params.put("type", type);
        HttpRequest.get_NotList(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                swipeRefreshLayout.setRefreshing(false);
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<MeBean> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<MeBean>>() {
                            }.getType());
                    mData.addAll(memberList);
                    mAdapter.loadMoreEnd();
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(NotiListActivity.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
