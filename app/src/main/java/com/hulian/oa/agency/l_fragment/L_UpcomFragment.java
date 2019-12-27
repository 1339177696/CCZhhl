package com.hulian.oa.agency.l_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.agency.l_adapter.UpcomAdapter;
import com.hulian.oa.agency.l_adapter.UpcomAdapter_qgl;
import com.hulian.oa.bean.Agency;
import com.hulian.oa.bean.AgencyCount;
import com.hulian.oa.bean.AgencyNew;
import com.hulian.oa.bean.Daiban_xin_qgl1;
import com.hulian.oa.bean.Fab;
import com.hulian.oa.bean.Fab2;
import com.hulian.oa.bean.InstructionsList;
import com.hulian.oa.bean.Leave;
import com.hulian.oa.bean.MeetingList;
import com.hulian.oa.bean.OfficialDocumentList;
import com.hulian.oa.bean.WorkCoordinationReleaseList;
import com.hulian.oa.bean.WorkLeaveList;
import com.hulian.oa.bean.WorkReimbursementList;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.qglactivity.qglbean.StringBean1;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.work.file.admin.activity.meeting.MeetingSponsorActivity;
import com.hulian.oa.work.file.admin.activity.task.TaskLauncherActivity;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

//待办-》待办
public class L_UpcomFragment extends Fragment implements PullLoadMoreRecyclerView.PullLoadMoreListener {
    @BindView(R.id.lv_notice)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    @BindView(R.id.emptyBg)
    RelativeLayout emptyBg;
    private int mCount = 1;
    private RecyclerView mRecyclerView;
//    UpcomAdapter mRecyclerViewAdapter;
    UpcomAdapter_qgl mRecyclerViewAdapter;
    Unbinder unbinder;

    @BindView(R.id.tv_mengban)
    TextView tvMengban;

   private List<Daiban_xin_qgl1.DataBean> memberList = new ArrayList<>();

   private List<Daiban_xin_qgl1.DataBean> dataBean  = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.l_fra_collection_notice, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initList();
        return view;
    }


    public void onEventMainThread(Fab event) {
        if (event.getTag().equals("0")) {
            tvMengban.setVisibility(View.GONE);
        } else {
            tvMengban.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.tv_mengban)
    public void onViewClicked2() {
        Fab2 fab2 = new Fab2();
        fab2.setTag("0");
        EventBus.getDefault().post(fab2);
    }

    private void initList() {
        //获取mRecyclerView对象
        mRecyclerView = mPullLoadMoreRecyclerView.getRecyclerView();
        //代码设置scrollbar无效？未解决！
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //设置下拉刷新是否可见
        mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置是否可以下拉刷新
        mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
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
//        mRecyclerViewAdapter = new UpcomAdapter(getActivity());
        mRecyclerViewAdapter = new UpcomAdapter_qgl(getActivity());
        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
        getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(L_UpcomFragment event) {
        onRefresh();
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
        params.put("createBy", SPUtils.get(getActivity(), "userId", "").toString());
//        params.put("type", SPUtils.get(getActivity(), "isLead", "").toString());
        HttpRequest.postAgencyListApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    memberList = gson.fromJson(result.getJSONArray("data").toString(), new TypeToken<List<Daiban_xin_qgl1.DataBean>>() {}.getType());
//                    List<AgencyNew> agencyNewslist = new ArrayList<>();
//                    for (int i = 0;i<memberList.size();i++){
//                        if (memberList.get(i).getType().equals("1")){
//                            //公文
//
//
//                        }else if (memberList.get(i).getType().equals("2")){
//                            //请假
//
//
//                        }else if (memberList.get(i).getType().equals("3")){
//                            //会议
//
//
//                        }else {
//                            //任务
//                        }
//                    }
                    mRecyclerViewAdapter.addAllData(memberList);
                    mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                    AgencyCount mAgencyCount = new AgencyCount();
                    mAgencyCount.setAgencyCount(memberList.size() + "");
                    EventBus.getDefault().post(mAgencyCount);



//                    Agency agency = gson.fromJson(result.getJSONObject("data").toString(), Agency.class);
//                    mRecyclerViewAdapter.addAllData(newData(agency));
//                    mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();




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

    private List<AgencyNew> newData(Agency agency) {
        List<AgencyNew> agencyNewslist = new ArrayList<>();
        if (agency.getWorkCoordinationReleaseList() != null) {
            for (WorkCoordinationReleaseList mWorkCoordinationReleaseList : agency.getWorkCoordinationReleaseList()) {
                AgencyNew mAgencyNew = new AgencyNew();
                mAgencyNew.setType("0");
                mAgencyNew.setTime(mWorkCoordinationReleaseList.getTime());
                mAgencyNew.setTitile(mWorkCoordinationReleaseList.getTitle());
                mAgencyNew.setContent("发起人 " + mWorkCoordinationReleaseList.getCreateBy());
                mAgencyNew.setDes("协同人 " + mWorkCoordinationReleaseList.getPersonSum());
                mAgencyNew.setId(mWorkCoordinationReleaseList.getId());
                agencyNewslist.add(mAgencyNew);
            }
        }
        if (agency.getOfficialDocumentList() != null) {
            for (OfficialDocumentList mOfficialDocumentList : agency.getOfficialDocumentList()) {
                AgencyNew mAgencyNew = new AgencyNew();
                mAgencyNew.setType("1");
                mAgencyNew.setTime(mOfficialDocumentList.getTime());
                mAgencyNew.setTitile(mOfficialDocumentList.getTitle());
                mAgencyNew.setContent("发起人 " + mOfficialDocumentList.getReleasePerson());
                mAgencyNew.setDes("审批人 " + mOfficialDocumentList.getApproveNames());
                mAgencyNew.setId(mOfficialDocumentList.getId());
                agencyNewslist.add(mAgencyNew);
            }
        }
        if (agency.getInstructionsList() != null) {
            for (InstructionsList mInstructionsList : agency.getInstructionsList()) {
                AgencyNew mAgencyNew = new AgencyNew();
                mAgencyNew.setType("2");
                mAgencyNew.setTime(mInstructionsList.getTime());
                mAgencyNew.setTitile(mInstructionsList.getTitle());
                mAgencyNew.setStatus(mInstructionsList.getReceiveStatus());
                mAgencyNew.setContent("");
                mAgencyNew.setDes("");
                mAgencyNew.setId(mInstructionsList.getId());
                agencyNewslist.add(mAgencyNew);
            }
        }
        if (agency.getMeetingList() != null) {
            for (MeetingList mMeetingList : agency.getMeetingList()) {
                AgencyNew mAgencyNew = new AgencyNew();
                mAgencyNew.setType("3");
                mAgencyNew.setTime(mMeetingList.getTime());
                mAgencyNew.setTitile(mMeetingList.getTitle());
                mAgencyNew.setContent("地点 " + mMeetingList.getMeetingAddress());
                mAgencyNew.setDes("时间 " + mMeetingList.getMeetingTime());
                mAgencyNew.setId(mMeetingList.getId());
                agencyNewslist.add(mAgencyNew);
            }
        }
        if (agency.getWorkReimbursementList() != null) {
            for (WorkReimbursementList mWorkReimbursementList : agency.getWorkReimbursementList()) {
                AgencyNew mAgencyNew = new AgencyNew();
                mAgencyNew.setType("4");
                mAgencyNew.setTime(mWorkReimbursementList.getTime());
                mAgencyNew.setCreateBy(mWorkReimbursementList.getCreateBy());
                mAgencyNew.setTitile(mWorkReimbursementList.getTitle());
                mAgencyNew.setContent("事由 " + mWorkReimbursementList.getCause());
                mAgencyNew.setDes("金额 " + mWorkReimbursementList.getMoney());
                mAgencyNew.setId(mWorkReimbursementList.getId());
                agencyNewslist.add(mAgencyNew);
            }
        }
        if (agency.getWorkLeaveList() != null) {
            for (WorkLeaveList mWorkLeaveList : agency.getWorkLeaveList()) {
                AgencyNew mAgencyNew = new AgencyNew();
                mAgencyNew.setType("5");
                mAgencyNew.setCreateBy(mWorkLeaveList.getCreateBy());
                mAgencyNew.setTime(mWorkLeaveList.getTime());
                mAgencyNew.setTitile(mWorkLeaveList.getTitle());
                mAgencyNew.setContent("事由 " + mWorkLeaveList.getCause());
                mAgencyNew.setDes("时长 " + mWorkLeaveList.getDuration() + "天");
                mAgencyNew.setId(mWorkLeaveList.getId());
                agencyNewslist.add(mAgencyNew);
            }
        }
        AgencyCount mAgencyCount = new AgencyCount();
        mAgencyCount.setAgencyCount(agencyNewslist.size() + "");
        EventBus.getDefault().post(mAgencyCount);
        if(agencyNewslist.size()==0){
            emptyBg.setVisibility(View.VISIBLE);
//            qgl改的
//            mPullLoadMoreRecyclerView.setVisibility(View.GONE);
        }
        else {
            //            qgl改的
            emptyBg.setVisibility(View.GONE);
//            mPullLoadMoreRecyclerView.setVisibility(View.VISIBLE);
        }
        return agencyNewslist;
    }



    //接受点击事件
//    public void onEventMainThread(StringBean1 event) {
//        Log.e("待办点击的",event.getDaiban());
//        if (event.getDaiban().equals("公文审批")){
//            dataBean.clear();
//            for (int i = 0;i<memberList.size();i++) {
//
//                if (memberList.get(i).getType().equals("1")) {
//                    //公文
//                    dataBean.add(memberList.get(i));
//                }
//            }
//            mRecyclerViewAdapter.clearData();
//            mRecyclerViewAdapter.addAllData(dataBean);
//            AgencyCount mAgencyCount = new AgencyCount();
//            mAgencyCount.setAgencyCount(dataBean.size() + "");
//            EventBus.getDefault().post(mAgencyCount);
//
//        }else if (event.getDaiban().equals("会议安排")){
//            dataBean.clear();
//            for (int i = 0;i<memberList.size();i++) {
//                if (memberList.get(i).getType().equals("3")) {
//                    //公文
//                    dataBean.add(memberList.get(i));
//                }
//            }
//            mRecyclerViewAdapter.clearData();
//            mRecyclerViewAdapter.addAllData(dataBean);
//            AgencyCount mAgencyCount = new AgencyCount();
//            mAgencyCount.setAgencyCount(dataBean.size() + "");
//            EventBus.getDefault().post(mAgencyCount);
//
//        }else if (event.getDaiban().equals("任务协同")){
//            dataBean.clear();
//            for (int i = 0;i<memberList.size();i++) {
//                if (memberList.get(i).getType().equals("4")) {
//                    //公文
//                    dataBean.add(memberList.get(i));
//                }
//            }
//            mRecyclerViewAdapter.clearData();
//            mRecyclerViewAdapter.addAllData(dataBean);
//            AgencyCount mAgencyCount = new AgencyCount();
//            mAgencyCount.setAgencyCount(dataBean.size() + "");
//            EventBus.getDefault().post(mAgencyCount);
//
//        }else if (event.getDaiban().equals("请假审批")){
//            dataBean.clear();
//            for (int i = 0;i<memberList.size();i++) {
//
//                if (memberList.get(i).getType().equals("2")) {
//                    //公文
//                    dataBean.add(memberList.get(i));
//                }
//            }
//            mRecyclerViewAdapter.clearData();
//            mRecyclerViewAdapter.addAllData(dataBean);
//            AgencyCount mAgencyCount = new AgencyCount();
//            mAgencyCount.setAgencyCount(dataBean.size() + "");
//            EventBus.getDefault().post(mAgencyCount);
//        }
//    }


}
