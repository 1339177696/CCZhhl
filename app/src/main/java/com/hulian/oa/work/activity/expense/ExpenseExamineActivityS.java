package com.hulian.oa.work.activity.expense;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.bean.ExpenBean;
import com.hulian.oa.bean.ExpenseBean;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.views.flowview.MyLinearLayoutManager;
import com.hulian.oa.work.activity.expense.l_adapter.ExpenseExamineAdapterSS;
import com.hulian.oa.work.activity.expense.l_adapter.ExpenseSecondAdapterSS;
import com.hulian.oa.work.activity.mypreview.PicturePreviewActivity;
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

/**
 * 报销审批详情改版内容
 */
public class ExpenseExamineActivityS extends BaseActivity {
    @BindView(R.id.tv_applicant)
    TextView tv_applicant;//申请人
    @BindView(R.id.tv_state)
    TextView tv_state;//申请状态
    @BindView(R.id.tv_reject_reson)
    TextView tv_reject_reson;//驳回原因
    @BindView(R.id.tv_reject_time)
    TextView tv_reject_time;//驳回原因
    @BindView(R.id.tv_expense_title)
    TextView tv_expense_title;//报销申请标题
    @BindView(R.id.tv_expense_time)
    TextView tv_expense_time;//报销申请时间
    @BindView(R.id.tv_department)
    TextView tv_department;//申请人所在部门
    @BindView(R.id.tv_bill_count)
    TextView tv_bill_count;//发票张数
    @BindView(R.id.tv_expense_money)
    TextView tv_expense_money;//报销金额
    @BindView(R.id.expen_r1)
    RelativeLayout expen_r1;//报销金额
    @BindView(R.id.expen_r2)
    RelativeLayout expen_r2;//报销金额
    @BindView(R.id.iv_state)
    ImageView iv_state;//报销金额
    @BindView(R.id.tv_reject_time_title)
    TextView tv_reject_time_title;//报销金额
    @BindView(R.id.tv_expen_lishi)
    TextView tv_expen_lishi;//报销金额
    @BindView(R.id.tv_shenqing_time_qgl)
    TextView tv_shenqing_time_qgl;//报销金额
    @BindView(R.id.tv_expen_cop)
    TextView tv_expen_cop;//报销金额
    @BindView(R.id.recycleview_expense)
    RecyclerView recycleview_expense;
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.tv_reject_reson_title)
    TextView tvRejectResonTitle;
    @BindView(R.id.tv_department_title)
    TextView tvDepartmentTitle;
    @BindView(R.id.tv_bill_count_title)
    TextView tvBillCountTitle;
    @BindView(R.id.tv_expense_amount_title)
    TextView tvExpenseAmountTitle;
    @BindView(R.id.liucheng_img_my)
    ImageView liuchengImgMy;
    @BindView(R.id.tv_shenqingren_qgl)
    TextView tvShenqingrenQgl;
    ExpenseExamineAdapterSS adapter;
    private List<ExpenBean> mData = new ArrayList<>();
    //新加的
    private String[] images = new String[]{};
    private List<LocalMedia> selectList = new ArrayList<>();
    private String createBy = "";
    @BindView(R.id.lv_m1)
    LinearLayout lv_m1;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.work_expense_result_s);
        ButterKnife.bind(this);
        initList();
        if (SPUtils.get(this, "roleKey", "").toString().contains("boss") || SPUtils.get(this, "roleKey", "").toString().contains("synthesizeLead")) {
            tv_expen_lishi.setVisibility(View.VISIBLE);
        } else {
            tv_expen_lishi.setVisibility(View.GONE);
        }
    }


    private void initList() {
        adapter = new ExpenseExamineAdapterSS(this,onAddPicClickListener);
        MyLinearLayoutManager layoutManager = new MyLinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(false);
        recycleview_expense.setLayoutManager(layoutManager);
        recycleview_expense.setAdapter(adapter);
        adapter.setList(mData);
        recycleview_expense.setAdapter(adapter);
        getExpen_Deata();
    }

    //添加事项--添加图片回调
    public ExpenseSecondAdapterSS.onAddPicClickListener onAddPicClickListener = new ExpenseSecondAdapterSS.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int position,int positionChaild, View view) {//position为外层数据
            Log.e("添加图片事件:","外层位置："+position);
            Log.e("添加图片事件:","图片放大："+position);
            selectList.clear();
            images = mData.get(position).getFiles().split(",");
            for (int i=0;i<images.length;i++){
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(images[i]);
                selectList.add(localMedia);
            }
            Intent intent = new Intent(mContext, PicturePreviewActivity.class);
            intent.putExtra(PictureConfig.EXTRA_PREVIEW_SELECT_LIST, (Serializable) selectList);
            intent.putExtra(PictureConfig.EXTRA_POSITION, positionChaild);
            mContext.startActivity(intent);
        }
    };

    public void getExpen_Deata() {
        RequestParams params = new RequestParams();
        params.put("id", getIntent().getStringExtra("id"));
        HttpRequest.get_WorkExpense1(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    createBy = result.getJSONObject("data").getString("createBy");
                    tv_applicant.setText(result.getJSONObject("data").getString("createName"));
                    tv_expense_title.setText(result.getJSONObject("data").getString("createName") + "的报销申请");
                    tv_department.setText(result.getJSONObject("data").getString("deptName"));
                    tv_bill_count.setText(result.getJSONObject("data").getString("receiptSum") + "张");
                    tv_expense_money.setText(result.getJSONObject("data").getString("money") + "元");
                    if (result.getJSONObject("data").getString("copierName") != null && !result.getJSONObject("data").getString("copierName").equals("null")){
                        lv_m1.setVisibility(View.VISIBLE);
                        tv_expen_cop.setText(result.getJSONObject("data").getString("copierName").substring(0,result.getJSONObject("data").getString("copierName").length()-1));
                    }else {
                        lv_m1.setVisibility(View.INVISIBLE);
                    }
                    tv_expense_time.setText(result.getJSONObject("data").getString("createTime").substring(0,result.getJSONObject("data").getString("createTime").length()-3));
                    List<ExpenBean> memberList = gson.fromJson(result.getJSONObject("data").getJSONArray("expenseReportLines").toString(),
                            new TypeToken<List<ExpenBean>>() {
                            }.getType());
                    mData.addAll(memberList);
//                    adapter.loadMoreEnd();
                    adapter.notifyDataSetChanged();

                    if (result.getJSONObject("data").getString("state").equals("0")) {
                        tv_shenqing_time_qgl.setText(result.getJSONObject("data").getString("approverName"));
                        tv_state.setText("待审批");
                        expen_r1.setVisibility(View.GONE);
                        expen_r2.setVisibility(View.GONE);
                        iv_state.setVisibility(View.GONE);
                    } else if (result.getJSONObject("data").getString("state").equals("1")) {
                        expen_r1.setVisibility(View.GONE);
                        expen_r2.setVisibility(View.VISIBLE);
                        iv_state.setVisibility(View.VISIBLE);
                        iv_state.setImageResource(R.mipmap.passed);
                        tv_shenqing_time_qgl.setText(result.getJSONObject("data").getString("approverName"));
                        tv_state.setText("审批通过");
                        tv_reject_time_title.setText("审批时间");
                        tv_reject_time.setText(result.getJSONObject("data").getString("approveTime").substring(0,result.getJSONObject("data").getString("approveTime").length()-3));
                    } else if (result.getJSONObject("data").getString("state").equals("2")){
                        iv_state.setImageResource(R.mipmap.dismissed);
                        iv_state.setVisibility(View.VISIBLE);
                        expen_r1.setVisibility(View.VISIBLE);
                        expen_r2.setVisibility(View.VISIBLE);
                        tv_shenqing_time_qgl.setText(result.getJSONObject("data").getString("approverName"));
                        tv_reject_time_title.setText("驳回时间");
                        tv_state.setText("审批驳回");
                        tv_reject_reson.setText(result.getJSONObject("data").getString("approveOpinion"));
                        tv_reject_time.setText(result.getJSONObject("data").getString("approveTime").substring(0,result.getJSONObject("data").getString("approveTime").length()-3));
                    }else if (result.getJSONObject("data").getString("state").equals("3")){
                        tv_shenqing_time_qgl.setText(result.getJSONObject("data").getString("approverName"));
                        tv_state.setText("审批中");
                        expen_r1.setVisibility(View.GONE);
                        expen_r2.setVisibility(View.GONE);
                        iv_state.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }

    @OnClick({R.id.iv_back,R.id.tv_expen_lishi})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_expen_lishi:
                Intent intent = new Intent(ExpenseExamineActivityS.this,ExpenseHistoryActivityS.class);
                intent.putExtra("ID",createBy);
                startActivity(intent);
                break;
        }
    }
}
