package com.hulian.oa.work.activity.video.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.bean.Report;
import com.hulian.oa.bean.VideoMeeting;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.work.activity.video.activity.VideoConferenceActivity;
import com.hulian.oa.work.activity.video.adapter.MeetingInMyAdapter;
import com.hulian.oa.work.adapter.WriteReportAdapter;
import com.hulian.oa.work.fragment.ReadReportFragment;
import com.netease.nim.avchatkit.AVChatKit;
import com.netease.nim.avchatkit.teamavchat.activity.TeamAVChatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * Created by 陈泽宇 on 2020/4/20
 * Describe:视频会议列表 (我参与的)
 */
public class MeetingListInMyFragment extends Fragment implements  BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.listview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private int mCount = 1;
    Unbinder unbinder;
    private MeetingInMyAdapter mAdapter;
    private List<VideoMeeting> mData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_read_report, container, false);
        unbinder = ButterKnife.bind(this, view);
        initList();
        return view;
    }

    private void initList() {
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new MeetingInMyAdapter(mData);
        mAdapter.openLoadAnimation();
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this,mRecyclerView);
        mAdapter.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.list_empty, null));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        ArrayList<String> accounts = new ArrayList<>() ;
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("这是年",mData.get(position).getYear());
                Collections.addAll(accounts,mData.get(position).getParticipant().split("\\s*,\\s*"));
                TeamAVChatActivity.startActivity(AVChatKit.getContext(), false, mData.get(position).getYear(), mData.get(position).getYear(), accounts, mData.get(position).getYear());
            }
        });
        getData();

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("pageStart", mCount*10-10 + "");
        params.put("pageEnd", mCount * 10 + "");
        params.put("initiator", SPUtils.get(getActivity(), "username", "").toString());
        HttpRequest.getVideoRoomList(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                swipeRefreshLayout.setRefreshing(false);
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();

                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<VideoMeeting> memberList = gson.fromJson(result.getJSONArray("rows").toString(),
                            new TypeToken<List<VideoMeeting>>() {
                            }.getType());
                    mData.addAll(memberList);
                    if (memberList.size()<10){
                        mAdapter.loadMoreEnd();
                    }else {
                        mAdapter.loadMoreComplete();
                    }
                    mAdapter.notifyDataSetChanged();

                    ((VideoConferenceActivity)getActivity()).setListSize(mData.size(),0);


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
    public void onLoadMoreRequested() {

        mCount = mCount + 1;
        getData();
    }
}
