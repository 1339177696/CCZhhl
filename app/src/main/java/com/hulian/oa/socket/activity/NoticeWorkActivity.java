package com.hulian.oa.socket.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.agency.fragment.UpcomFragment;
import com.hulian.oa.bean.Report;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.push.bean.MeBean;
import com.hulian.oa.socket.adapter.NoticeWorkAdaoter;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.views.MyDialog;
import com.hulian.oa.work.activity.leave.LeaveApplyResultActivity;
import com.hulian.oa.work.activity.leave.LeaveExamineActivity;
import com.hulian.oa.work.activity.leave.l_fragment.LeaveLaunchFragment;
import com.hulian.oa.work.activity.task.l_details_activity.TaskLaunchDetailsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 作者：qgl 时间： 2020/4/27 10:34
 * Describe: 工作通知
 */
public class NoticeWorkActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.listview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.notice_tv_title)
    TextView notice_tv_title;
    private NoticeWorkAdaoter mAdapter;
    private List<MeBean> mData = new ArrayList<>();
    List<MeBean>meBeans = new ArrayList<>();
    List<MeBean> memberList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticeworkactivity);
        ButterKnife.bind(this);
        initList();
    }

    private void initList() {
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new NoticeWorkAdaoter(mData);
        mAdapter.openLoadAnimation();
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.list_empty, null));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                if (mData.get(position).getType().equals("3")){
//                    if (mData.get(position).getCompletion().equals("待审批")){
//                        //审批
//                        Intent intent=new Intent();
//                        intent.putExtra("id",mData.get(position).getRelationId());
//                        intent.putExtra("createByid", SPUtils.get(mContext, "userId", "").toString());
//                        intent.setClass(mContext, LeaveExamineActivity.class);
//                        mContext.startActivity(intent);
//                    }else {
//                        Intent intent=new Intent();
//                        intent.putExtra("id",mData.get(position).getRelationId());
//                        intent.setClass(mContext, LeaveApplyResultActivity.class);
//                        mContext.startActivity(intent);
//                    }
//                }else if (mData.get(position).getType().equals("7")){
//                    Intent intent = new Intent();
//                    intent.putExtra("PORID","1");
//                    intent.putExtra("ID",mData.get(position).getRelationId());
//                    intent.setClass(mContext, TaskLaunchDetailsActivity.class);
//                    mContext.startActivity(intent);
//
//                }else if (mData.get(position).getType().equals("8")){
//
//                }
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
        mAdapter.notifyDataSetChanged();
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("userId", SPUtils.get(this, "userId", "").toString());
        params.put("type", getIntent().getStringExtra("type"));
        HttpRequest.get_NotList(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                swipeRefreshLayout.setRefreshing(false);
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    memberList = gson.fromJson(result.getJSONArray("data").toString(),
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
                Toast.makeText(NoticeWorkActivity.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onLoadMoreRequested() {
        getData();
    }

    @OnClick({R.id.iv_back,R.id.notice_im_screen})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.notice_im_screen:
                shouDialog();
                break;
        }
    }

    public void shouDialog(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_notice_work, null);
        TextView tv_leave = view.findViewById(R.id.dialog_not_tv_qj);
        TextView tv_ReiBurs = view.findViewById(R.id.dialog_not_tv_bx);
        TextView tv_task = view.findViewById(R.id.dialog_not_tv_rw);
        Dialog dialog = new MyDialog(NoticeWorkActivity.this, true, true, (float) 0.7).setNewView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        tv_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                notice_tv_title.setText("请假推送");
                meBeans.clear();
                mData.clear();
                for (int i = 0;i<memberList.size();i++){
                    if (memberList.get(i).getType().equals("3")){
                        meBeans.add(memberList.get(i));
                    }
                }
                mData.addAll(meBeans);
                mAdapter.loadMoreEnd();
                mAdapter.notifyDataSetChanged();
            }
        });
        tv_ReiBurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                notice_tv_title.setText("报销推送");
                meBeans.clear();
                mData.clear();
                for (int i = 0;i<memberList.size();i++){
                    if (memberList.get(i).getType().equals("8")){
                        meBeans.add(memberList.get(i));
                    }
                }
                mData.addAll(meBeans);
                mAdapter.loadMoreEnd();
                mAdapter.notifyDataSetChanged();
            }
        });
        tv_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                notice_tv_title.setText("任务推送");
                meBeans.clear();
                mData.clear();
                for (int i = 0;i<memberList.size();i++){
                    if (memberList.get(i).getType().equals("7")){
                        meBeans.add(memberList.get(i));
                    }
                }
                mData.addAll(meBeans);
                mAdapter.loadMoreEnd();
                mAdapter.notifyDataSetChanged();
            }
        });

    }


    public void onEventMainThread(NoticeWorkActivity event) {
        onRefresh();
    }
}
