package com.hulian.oa.work.file.admin.activity.expense;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.FullyGridLayoutManager;
import com.hulian.oa.work.file.admin.activity.leave.LeaveHistoryActivity;
import com.hulian.oa.work.file.admin.activity.leave.l_adapter.LeaveResultAdapter;
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

/**
 * 报销申请结果详情
 */
public class ExpenseApplyResultActivity extends BaseActivity {
    //审批人
    @BindView(R.id.tv_approved_person)
    TextView tv_approved_person;
    //审批状态
    @BindView(R.id.tv_approved_state)
    TextView tv_approved_state;
    //审批状态
    @BindView(R.id.tv_approved_time)
    TextView tv_approved_time;
    //报销事由
    @BindView(R.id.tv_expense_reason)
    TextView tv_expense_reason;
    //标题
    @BindView(R.id.tv_expense_title)
    TextView tv_expense_title;
    //报销金额
    @BindView(R.id.tv_expense_money)
    TextView tv_expense_money;
    //报销时间
//    @BindView(R.id.tv_expense_time)
//    TextView tv_expense_time;
    //报销时间
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //查看他的历史记录
    @BindView(R.id.tv_check_history)
    TextView tv_check_history;
    private LeaveResultAdapter adapter;
    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
    //图片放大预览测试
    private String[] images = {
    };

    @BindView(R.id.tv_start)
    TextView tv_start;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.work_expense_result);
        ButterKnife.bind(this);
        //领导
        if (SPUtils.get(mContext, "isLead", "").equals("0")) {
            tv_check_history.setVisibility(View.VISIBLE);
        }
        //员工
        else {
            tv_check_history.setVisibility(View.GONE);
        }
        mContext = this;
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
                    tv_approved_person.setText(result.getJSONObject("data").getString("approver"));
                    String status = result.getJSONObject("data").getString("state");
                    if (status.equals("1")) {
                        tv_approved_state.setText("已审批");
                    } else if (status.equals("2")) {
                        tv_approved_state.setText("驳回");
                    } else {
                        tv_approved_state.setText("待审批");
                    }

                    if (result.getJSONObject("data").getString("createTime") != "null") {
                        tv_start.setText(result.getJSONObject("data").getString("createTime").split(" ")[0]);
                    } else {
                        tv_start.setText("暂无");

                    }

                    if (result.getJSONObject("data").getString("approvalTime") != "null") {
                        tv_approved_time.setText(result.getJSONObject("data").getString("approvalTime").split(" ")[0]);
                    } else {
                        tv_approved_time.setText("暂无");
                    }


                    tv_expense_title.setText(result.getJSONObject("data").getString("remark"));
                    tv_expense_reason.setText(result.getJSONObject("data").getString("cause"));
                    tv_expense_money.setText(result.getJSONObject("data").getString("money"));
                    if (!result.getJSONObject("data").getString("picture").equals("null")) {
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

    @OnClick({R.id.tv_check_history, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_check_history:
                Intent itent = new Intent();
                itent.putExtra("id", getIntent().getStringExtra("createByid"));
                startActivity(new Intent(mContext, ExpenseHistoryActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
        }

    }

    //初始化图片信息
    private void init(String[] images) {
        for (int i = 0; i < images.length; i++) {
            LocalMedia localMedia = new LocalMedia();
            localMedia.setPath(images[i]);
            selectList.add(localMedia);
        }
        //图片信息适配
        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new LeaveResultAdapter(mContext);
        adapter.setList(selectList);
        recyclerView.setAdapter(adapter);

        //图片信息大图预览
        adapter.setOnItemClickListener(new LeaveResultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                Intent intent = new Intent(mContext, PicturePreviewActivity.class);
                intent.putExtra(PictureConfig.EXTRA_PREVIEW_SELECT_LIST, (Serializable) selectList);
                intent.putExtra(PictureConfig.EXTRA_POSITION, position);
                mContext.startActivity(intent);
            }
        });

    }
}
