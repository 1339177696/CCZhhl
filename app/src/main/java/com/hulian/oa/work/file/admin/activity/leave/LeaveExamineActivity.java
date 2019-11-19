package com.hulian.oa.work.file.admin.activity.leave;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.agency.l_fragment.L_UpcomFragment;
import com.hulian.oa.bean.People;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.views.AlertDialog;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.FullyGridLayoutManager;
import com.hulian.oa.work.file.admin.activity.leave.l_adapter.LeaveExamineAdapter;
import com.hulian.oa.work.file.admin.activity.leave.l_fragment.LeaveApprovedFragment;
import com.hulian.oa.work.file.admin.activity.leave.l_fragment.LeavePendFragment;
import com.hulian.oa.work.file.admin.activity.meeting.l_fragment.MeetLaunchFragment;
import com.hulian.oa.work.file.admin.activity.mypreview.PicturePreviewActivity;
import com.hulian.oa.work.fragment.WorkFragemt_9;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 请假审批详情
 */
public class LeaveExamineActivity extends BaseActivity {
    @BindView(R.id.tv_chaosong_person_qgl)
    TextView tv_chaosong_person_qgl;
    @BindView(R.id.recycler2)
    RecyclerView recyclerView;
    //请假标题
//    @BindView(R.id.tv_leave_title)
//    TextView tv_leave_title;
    //请假事由
    @BindView(R.id.tv_leave_reason)
    TextView tv_leave_reason;
    //请假时长
    @BindView(R.id.tv_duration)
    TextView tv_duration;
    //开始时间
    @BindView(R.id.tv_start)
    TextView tv_start;
    //结束时间
    @BindView(R.id.tv_end)
    TextView tv_end;
    //请假事由详细信息
//    @BindView(R.id.tv_leave_reason_content)
//    TextView tv_leave_reason_content;
    //查看他的历史记录
    @BindView(R.id.tv_check_history)
    TextView tv_check_history;
    //驳回
    @BindView(R.id.tv_disagree)
    RadioButton tv_disagree;
    //同意
    @BindView(R.id.tv_agree)
    RadioButton tv_agree;
    //转交
//    @BindView(R.id.tv_transfer)
//    TextView tv_transfer;
    AlertDialog myDialog;
    private LeaveExamineAdapter adapter;
    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
    //图片放大预览测试
    private String[] images = {};
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.work_leave_examine);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        myDialog = new AlertDialog(this).builder();
        getData();

    }
    private void getData() {
        RequestParams params = new RequestParams();
        params.put("id", getIntent().getStringExtra("id"));
        HttpRequest.get_WorkLeave(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
//                    tv_leave_title.setText(result.getJSONObject("data").getString("remark"));
                    tv_leave_reason.setText(result.getJSONObject("data").getString("createBy"));
                    tv_duration.setText(result.getJSONObject("data").getString("duration"));
                    tv_start.setText(result.getJSONObject("data").getString("startTime"));
                    tv_end.setText(result.getJSONObject("data").getString("endTime"));
                    tv_chaosong_person_qgl.setText(result.getJSONObject("data").getString("describe"));
                    if(!result.getJSONObject("data").getString("picture").equals("null")) {
                        images = result.getJSONObject("data").getString("picture").split(",");
                        init(images);
                    }
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
//    R.id.tv_transfer,
    @OnClick({R.id.tv_disagree,R.id.tv_agree,R.id.tv_check_history,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_check_history://查看历史记录
                Intent itent=new Intent(LeaveExamineActivity.this,LeaveHistoryActivity.class);
                itent.putExtra("id",getIntent().getStringExtra("createByid"));
                startActivity(itent);
                break;
            case R.id.tv_disagree://驳回
                postData("2","");
                break;
            case R.id.tv_agree://同意
                postData("1","");
                break;
            case R.id.iv_back://返回
                finish();
                break;
//            case R.id.tv_transfer://转交
//                startActivity(new Intent(mContext,SelDepartmentActivity_Leave.class));
//                break;
        }
    }

    private void postData(String state,String approver) {
        RequestParams params = new RequestParams();
        params.put("id", getIntent().getStringExtra("id"));
        if(!state.equals("")){
            params.put("state", state);
        }
        if(!"".equals(approver)){
            params.put("approver", approver);
        }
        HttpRequest.get_Work_edit(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    ToastHelper.showToast(mContext, result.getString("msg"));
                    if( result.getString("code").equals("0")){
                        EventBus.getDefault().post(new LeavePendFragment());
                        EventBus.getDefault().post(new LeaveApprovedFragment());
                        EventBus.getDefault().post(new L_UpcomFragment());
                        finish();
                    }
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

    //初始化图片信息
    private void init(String[] images) {
        for (int i=0;i<images.length;i++){
            LocalMedia localMedia = new LocalMedia();
            localMedia.setPath(images[i]);
            selectList.add(localMedia);
        }
        //图片信息适配
        FullyGridLayoutManager manager = new FullyGridLayoutManager(LeaveExamineActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new LeaveExamineAdapter(mContext);
        adapter.setList(selectList);
        recyclerView.setAdapter(adapter);

        //图片信息大图预览
        adapter.setOnItemClickListener(new LeaveExamineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                Intent intent = new Intent(mContext, PicturePreviewActivity.class);
                intent.putExtra(PictureConfig.EXTRA_PREVIEW_SELECT_LIST, (Serializable) selectList);
                intent.putExtra(PictureConfig.EXTRA_POSITION, position);
                mContext.startActivity(intent);
            }
        }) ;
    }
    public void onEventMainThread(People ev) {
        myDialog.setGone().setTitle("提示").setMsg("确定转交给 "+ev.getUserName()+" 吗?").setNegativeButton("取消",null).setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData("",ev.getUserId());
            }
        }).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
