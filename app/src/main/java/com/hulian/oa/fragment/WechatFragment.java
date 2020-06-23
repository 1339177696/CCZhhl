package com.hulian.oa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.bean.Report;
import com.hulian.oa.me.activity.MeActivity;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.socket.activity.NoticeMeetingActivity;
import com.hulian.oa.socket.adapter.NoticeActivityAdapter;
import com.hulian.oa.utils.SPUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者：qgl 时间： 2020/6/17 10:30
 * Describe: 消息推送
 */
public class WechatFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private NoticeActivityAdapter noticeActivityAdapter;
    private List<Report> mData = new ArrayList<>();
    private int mCount = 1;
    private Unbinder unbinder;
    @BindView(R.id.tv_type)
    TextView tv_type;

    public static WechatFragment newInstance(String requestJson) {
        WechatFragment fragment = new WechatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.notticactivity, container, false);
        unbinder = ButterKnife.bind(this, view);
        tv_type.setText(SPUtils.get(getActivity(), "nickname", "").toString().substring(SPUtils.get(getActivity(), "nickname", "").toString().length()-2,SPUtils.get(getActivity(), "nickname", "").toString().length()));
        initList();
        return view;
    }

    private void initList() {
        swipeRefreshLayout.setOnRefreshListener(this);
        noticeActivityAdapter = new NoticeActivityAdapter(mData);
        noticeActivityAdapter.openLoadAnimation();
        noticeActivityAdapter.setEnableLoadMore(true);
        noticeActivityAdapter.setOnLoadMoreListener(this, listview);
        noticeActivityAdapter.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.list_empty, null));
        listview.setLayoutManager(new LinearLayoutManager(getActivity()));
        listview.setAdapter(noticeActivityAdapter);
        noticeActivityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), NoticeMeetingActivity.class));
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
        mCount = 1;
    }
    @Override
    public void onLoadMoreRequested() {
        mCount = mCount + 1;
        getData();
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("pageStart", mCount * 10 - 10 + "");
        params.put("pageEnd", mCount * 10 + "");
        params.put("createBy", SPUtils.get(getActivity(), "userId", "").toString());
        params.put("receivePerson", SPUtils.get(getActivity(), "userId", "").toString());
        HttpRequest.getGetWorkReportList(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                swipeRefreshLayout.setRefreshing(false);
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<Report> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<Report>>() {}.getType());
                    mData.addAll(memberList);
                    if (memberList.size() < 10) {
                        noticeActivityAdapter.loadMoreEnd();
                    } else {
                        noticeActivityAdapter.loadMoreComplete();
                    }
                    noticeActivityAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(getActivity(), "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_type:
                startActivity(new Intent(getActivity(), MeActivity.class));
                break;
            default:
                break;
        }
    }
}
