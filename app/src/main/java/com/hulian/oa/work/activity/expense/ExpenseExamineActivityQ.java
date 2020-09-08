package com.hulian.oa.work.activity.expense;

import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.agency.fragment.UpcomFragment;
import com.hulian.oa.bean.ExpenBean;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.HttpsUtils;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.socket.activity.NoticeWorkActivity;
import com.hulian.oa.socket.adapter.NoticeWorkAdaoter;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.views.MyDialog;
import com.hulian.oa.views.flowview.MyLinearLayoutManager;
import com.hulian.oa.work.activity.expense.l_adapter.ExpenseExamineAdapterSS;
import com.hulian.oa.work.activity.expense.l_adapter.ExpenseSecondAdapterSS;
import com.hulian.oa.work.activity.expense.l_fragment.ExpenseApprovedFragment;
import com.hulian.oa.work.activity.expense.l_fragment.ExpenseCopymeFragment;
import com.hulian.oa.work.activity.expense.l_fragment.ExpenseLaunchFragment;
import com.hulian.oa.work.activity.expense.l_fragment.ExpensePendFragment;
import com.hulian.oa.work.activity.leave.l_fragment.LeaveApprovedFragment;
import com.hulian.oa.work.activity.leave.l_fragment.LeavePendFragment;
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
import de.greenrobot.event.EventBus;

/**
 * 待审批报销审批详情改版内容
 */
public class ExpenseExamineActivityQ extends BaseActivity {
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
    @BindView(R.id.tv_disagree)
    RadioButton tv_disagree;//报销金额
    @BindView(R.id.tv_agree)
    RadioButton tv_agree;//报销金额
    //    @BindView(R.id.mSwipeRefreshLayout)
//    SwipeRefreshLayout mSwipeRefreshLayout;
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
//    @BindView(R.id.recycleview_expense)
//    RecyclerView recycleviewExpense;
    @BindView(R.id.liucheng_img_my)
    ImageView liuchengImgMy;
    @BindView(R.id.tv_shenqing_time_qgl)
    TextView tvShenqingTimeQgl;
    @BindView(R.id.tv_shenqingren_qgl)
    TextView tvShenqingrenQgl;
    private int mNextRequestPage = 1;
    private static final int PAGE_SIZE = 6;
    ExpenseExamineAdapterSS adapter;
    private List<ExpenBean> mData = new ArrayList<>();

    private String sp_name = "";
    private String createBy = "";
    @BindView(R.id.tv_expen_cop)
    TextView tv_expen_cop;//报销抄从人
    @BindView(R.id.lv_m1)
    LinearLayout lv_m1;
    private String approveOpinion = ""; // 驳回意见
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.work_expense_result_q);
        ButterKnife.bind(this);
//        initAdapter();
        initRefreshLayout();
        initList();
        if (SPUtils.get(this, "roleKey", "").toString().contains("boss") || SPUtils.get(this, "roleKey", "").toString().contains("synthesizeLead")) {
            tv_expen_lishi.setVisibility(View.VISIBLE);
        } else {
            tv_expen_lishi.setVisibility(View.GONE);
        }
//        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void initList() {
        adapter = new ExpenseExamineAdapterSS(mContext,onAddPicClickListener);
        MyLinearLayoutManager layoutManager = new MyLinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(false);
        recycleview_expense.setLayoutManager(layoutManager);
        recycleview_expense.setAdapter(adapter);
        adapter.setList(mData);
        recycleview_expense.setAdapter(adapter);


//        swipeRefreshLayout.setOnRefreshListener(this);
//        adapter = new ExpenseExamineAdapterS(mData);
//        adapter.openLoadAnimation();
//        adapter.setEnableLoadMore(true);
        // adapter.setOnLoadMoreListener(this,recycleview_expense);
//        adapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.list_empty, null));
//        recycleview_expense.setLayoutManager(new LinearLayoutManager(this));
//        recycleview_expense.setAdapter(adapter);
        getExpen_Deata();

    }

    private String[] images = new String[]{};
    private List<LocalMedia> selectList = new ArrayList<>();

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
//    private void initAdapter() {
////        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
//        recycleview_expense.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new ExpenseExamineAdapterS(mData);
//        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                loadMore();
//            }
//        }, recycleview_expense);
//    }

    //加载更多
    private void loadMore() {

    }

    //初始化刷新
    private void initRefreshLayout() {
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refresh();
//            }
//        });
    }

    private void refresh() {
        mNextRequestPage = 1;
//        adapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
    }

    private void setData(boolean isRefresh, List<ExpenBean> data) {
//        mNextRequestPage++;
//        final int size = data == null ? 0 : data.size();
//        if (isRefresh) {
//            adapter.setNewData(data);
//        } else {
//            if (size > 0) {
//                adapter.addData(data);
//            }
//        }
//        if (size < 1) {
//            //第一页如果不够一页就不显示没有更多数据布局
//            adapter.loadMoreEnd(isRefresh);
//            Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
//        } else {
//            adapter.loadMoreComplete();
//        }
    }

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
                    sp_name = result.getJSONObject("data").getString("createName") + "的报销申请";
                    tv_department.setText(result.getJSONObject("data").getString("deptName"));
                    tv_bill_count.setText(result.getJSONObject("data").getString("receiptSum") + "张");
                    tv_expense_money.setText(result.getJSONObject("data").getString("money") + "元");
                    if (result.getJSONObject("data").getString("copierName") != null && !result.getJSONObject("data").getString("copierName").equals("null")){
                        lv_m1.setVisibility(View.VISIBLE);
                        tv_expen_cop.setText(result.getJSONObject("data").getString("copierName").substring(0,result.getJSONObject("data").getString("copierName").length()-1));
                    }else {
                        lv_m1.setVisibility(View.GONE);
                    }
                    tv_expense_time.setText(result.getJSONObject("data").getString("createTime").substring(0,result.getJSONObject("data").getString("createTime").length()-3));
                    List<ExpenBean> memberList = gson.fromJson(result.getJSONObject("data").getJSONArray("expenseReportLines").toString(),
                            new TypeToken<List<ExpenBean>>() {
                            }.getType());
                    mData.addAll(memberList);
//                    adapter.loadMoreEnd();


                    adapter.notifyDataSetChanged();

                    if (result.getJSONObject("data").getString("state").equals("0")) {
                        tvShenqingTimeQgl.setText(result.getJSONObject("data").getString("approverName"));
                        tv_state.setText("待审批");
                        expen_r1.setVisibility(View.GONE);
                        expen_r2.setVisibility(View.GONE);
                        iv_state.setVisibility(View.GONE);
                    } else if (result.getJSONObject("data").getString("state").equals("1")) {
                        tvShenqingTimeQgl.setText(result.getJSONObject("data").getString("approverName"));
                        expen_r1.setVisibility(View.GONE);
                        expen_r2.setVisibility(View.VISIBLE);
                        iv_state.setVisibility(View.VISIBLE);
                        iv_state.setImageResource(R.mipmap.passed);
                        tv_state.setText("审批通过");
                        tv_reject_time_title.setText("审批时间");
                        tv_reject_time.setText(result.getJSONObject("data").getString("approveTime").substring(0,result.getJSONObject("data").getString("approveTime").length()-3));
                    } else if (result.getJSONObject("data").getString("state").equals("2")){
                        tvShenqingTimeQgl.setText(result.getJSONObject("data").getString("approverName"));
                        iv_state.setImageResource(R.mipmap.dismissed);
                        iv_state.setVisibility(View.VISIBLE);
                        expen_r1.setVisibility(View.VISIBLE);
                        expen_r2.setVisibility(View.VISIBLE);
                        tv_reject_time_title.setText("驳回时间");
                        tv_state.setText("审批驳回");
                        tv_reject_reson.setText(result.getJSONObject("data").getString("approveOpinion"));
                        tv_reject_time.setText(result.getJSONObject("data").getString("approveTime").substring(0,result.getJSONObject("data").getString("approveTime").length()-3));
                    }else if (result.getJSONObject("data").getString("state").equals("3")){
                        tvShenqingTimeQgl.setText(result.getJSONObject("data").getString("approverName"));
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

    @OnClick({R.id.iv_back,R.id.tv_disagree,R.id.tv_agree,R.id.tv_expen_lishi})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_agree:
                showDialog1("驳回");
                break;
            case R.id.tv_disagree:
                post_data("1","");
                break;
            case R.id.tv_expen_lishi:
                Intent intent = new Intent(ExpenseExamineActivityQ.this,ExpenseHistoryActivityS.class);
                intent.putExtra("ID",createBy);
                startActivity(intent);
                break;
        }
    }

    //审批 1,同意 2，驳回
    public void post_data(String state,String approveOpinion){
        RequestParams params = new RequestParams();
        params.put("id", getIntent().getStringExtra("id"));
        params.put("approveOpinion", approveOpinion);
        params.put("state", state);
        params.put("approver", SPUtils.get(this, "userId", "").toString());
        HttpRequest.get_WorkExpense_edit1(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if( result.getString("code").equals("0")) {
                        if (state.equals("1")){
                            showDialog("同意"+ sp_name);
                        }else {
                            showDialog2("驳回"+sp_name);
                        }

                    }else {
                        Toast.makeText(ExpenseExamineActivityQ.this,"服务器错误",Toast.LENGTH_LONG).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showDialog(String context) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_submit, null);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(context);
        TextView submit = view.findViewById(R.id.confirm);
        Dialog dialog = new MyDialog(ExpenseExamineActivityQ.this, true, true, (float) 0.7).setNewView(view);
        dialog.show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                EventBus.getDefault().post(new ExpenseLaunchFragment());
                EventBus.getDefault().post(new ExpensePendFragment());
                EventBus.getDefault().post(new ExpenseApprovedFragment());
                EventBus.getDefault().post(new ExpenseCopymeFragment());
                finish();
            }
        });
    }

    private void showDialog1(String adress) {
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_reject, null);
        EditText et_content = view.findViewById(R.id.et_content);
        TextView tv_text1 = view.findViewById(R.id.tv_state);
        ImageView im_diss = view.findViewById(R.id.im_diss);
        Dialog dialog = new MyDialog(mContext, true, true, (float) 0.8).setNewView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        im_diss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_content.getText().toString().trim())) {
                    Toast.makeText(mContext,"请输入驳回原因",Toast.LENGTH_LONG).show();
                    return;
                }
                dialog.dismiss();
                approveOpinion = et_content.getText().toString().trim();
                post_data("2",approveOpinion);
            }
        });
    }

    private void showDialog2(String context){
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_rejected, null);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(context);
        TextView submit = view.findViewById(R.id.confirm);
        Dialog dialog = new MyDialog(ExpenseExamineActivityQ.this, true, true, (float) 0.7).setNewView(view);
        dialog.show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                EventBus.getDefault().post(new ExpenseLaunchFragment());
                EventBus.getDefault().post(new ExpensePendFragment());
                EventBus.getDefault().post(new ExpenseApprovedFragment());
                EventBus.getDefault().post(new ExpenseCopymeFragment());
                EventBus.getDefault().post(new NoticeWorkActivity());
                finish();
            }
        });
    }


}
