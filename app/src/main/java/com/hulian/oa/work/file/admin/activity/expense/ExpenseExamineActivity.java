package com.hulian.oa.work.file.admin.activity.expense;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.views.AlertDialog;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.FullyGridLayoutManager;
import com.hulian.oa.work.file.admin.activity.expense.l_adapter.ExpenseExamineAdapter;
import com.hulian.oa.work.file.admin.activity.expense.l_fragment.ExpenseApprovedFragment;
import com.hulian.oa.work.file.admin.activity.expense.l_fragment.ExpenseLaunchFragment;
import com.hulian.oa.work.file.admin.activity.expense.l_fragment.ExpensePendFragment;
import com.hulian.oa.work.file.admin.activity.leave.LeaveExamineActivity;
import com.hulian.oa.work.file.admin.activity.leave.LeaveHistoryActivity;
import com.hulian.oa.work.file.admin.activity.leave.SelDepartmentActivity_Leave;
import com.hulian.oa.work.file.admin.activity.leave.l_adapter.LeaveExamineAdapter;
import com.hulian.oa.work.file.admin.activity.leave.l_fragment.LeaveApprovedFragment;
import com.hulian.oa.work.file.admin.activity.leave.l_fragment.LeavePendFragment;
import com.hulian.oa.work.file.admin.activity.meeting.MeetingAmendActivity;
import com.hulian.oa.work.file.admin.activity.meeting.MeetingSponsorActivity;
import com.hulian.oa.work.file.admin.activity.mypreview.PicturePreviewActivity;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 报销审批详情
 */
public class ExpenseExamineActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    //报销事由
    @BindView(R.id.tv_expense_reason)
    TextView tv_expense_reason;
    //报销钱数
    @BindView(R.id.tv_moeny)
    TextView tv_moeny;
    //报销时间
    @BindView(R.id.tv_expense_time)
    TextView tv_expense_time;
    //发票信息
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //查看他的历史记录
    @BindView(R.id.tv_check_history)
    TextView tv_check_history;
    private ExpenseExamineAdapter adapter;
    AlertDialog myDialog;
    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
    //图片放大预览测试
    private String[] images = {
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_expense_examine);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mContext = this;
        myDialog = new AlertDialog(this).builder();
        getData();
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("id", getIntent().getStringExtra("id"));
        HttpRequest.get_WorkExpense(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    title.setText(result.getJSONObject("data").getString("remark"));
                    tv_expense_reason.setText(result.getJSONObject("data").getString("cause"));
                    tv_moeny.setText(result.getJSONObject("data").getString("money"));
                    tv_expense_time.setText(result.getJSONObject("data").getString("createTime").split(" ")[0]);
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
    @OnClick({R.id.tv_disagree,R.id.tv_agree,R.id.tv_transfer,R.id.tv_check_history,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_check_history://查看历史记录
                Intent itent=new Intent();
                itent.putExtra("id",getIntent().getStringExtra("createByid"));
                startActivity(new Intent(mContext, ExpenseHistoryActivity.class));
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
            case R.id.tv_transfer://转交
                startActivity(new Intent(mContext, SelDepartmentActivity_Leave.class));
                break;
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
        HttpRequest.get_WorkExpense_edit(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    ToastHelper.showToast(mContext, result.getString("msg"));
                    if( result.getString("code").equals("0")){
                        EventBus.getDefault().post(new ExpensePendFragment());
                        EventBus.getDefault().post(new ExpenseApprovedFragment());
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
        FullyGridLayoutManager manager = new FullyGridLayoutManager(ExpenseExamineActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new ExpenseExamineAdapter(mContext);
        adapter.setList(selectList);
        recyclerView.setAdapter(adapter);

        //图片信息大图预览
        adapter.setOnItemClickListener(new ExpenseExamineAdapter.OnItemClickListener() {
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
