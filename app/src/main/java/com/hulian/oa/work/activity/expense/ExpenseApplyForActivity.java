package com.hulian.oa.work.activity.expense;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.print.PageRange;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.bean.AppersonBean;
import com.hulian.oa.bean.ExpenseBean;
import com.hulian.oa.bean.People;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.views.AlertDialog;
import com.hulian.oa.views.MyDialog;
import com.hulian.oa.views.MyGridView;
import com.hulian.oa.views.flowview.MyLayoutManager;
import com.hulian.oa.views.flowview.MyLinearLayoutManager;
import com.hulian.oa.views.flowview.NoScrollRecyclerView;
import com.hulian.oa.R;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.activity.expense.l_adapter.ExpenseApproverAdapter;
import com.hulian.oa.work.activity.expense.l_adapter.ExpenseCopyerAdapter;
import com.hulian.oa.work.activity.expense.l_adapter.ExpenseImageAdapter;
import com.hulian.oa.work.activity.expense.l_adapter.ExpenseSecondAdapter;
import com.hulian.oa.work.activity.expense.l_fragment.ExpenseApprovedFragment;
import com.hulian.oa.work.activity.expense.l_fragment.ExpenseCopymeFragment;
import com.hulian.oa.work.activity.expense.l_fragment.ExpenseLaunchFragment;
import com.hulian.oa.work.activity.expense.l_fragment.ExpensePendFragment;
import com.hulian.oa.work.activity.meeting.SelDepartmentActivity_meet_video;
import com.hulian.oa.work.activity.meeting.SelDepartmentActivity_meet_zb;
import com.hulian.oa.work.activity.meeting.l_adapter.MeetGridViewAdapter;
import com.hulian.oa.work.activity.video.activity.VideoConferenceActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.netease.nim.avchatkit.common.util.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

// 报销申请
public class ExpenseApplyForActivity extends BaseActivity {
    @BindView(R.id.tv_addItem)
    TextView tv_addItem;
    @BindView(R.id.tv_sum)
    TextView tv_sum;
    @BindView(R.id.tv_time_content)
    TextView tv_time_content;
    @BindView(R.id.recycler_item)
    NoScrollRecyclerView recycler_item;//发票说明列表
//    @BindView(R.id.recycler_approver)
//    RecyclerView recycler_approver;//审批人
//    @BindView(R.id.recycler_copyer)
//    RecyclerView recycler_copyer;//抄送人
    @BindView(R.id.iv_back)
    RelativeLayout iv_back;
    @BindView(R.id.et_sub_money)
    EditText et_sub_money;
    @BindView(R.id.tv_sqr)
    TextView tv_sqr;
    @BindView(R.id.tv_sqbm)
    TextView tv_sqbm;
    @BindView(R.id.tv_bill_count)
    EditText tv_bill_count;

    @BindView(R.id.rl_approver)
    RelativeLayout rl_approver; // 审批人按钮
    @BindView(R.id.rl_copyer)
    RelativeLayout rl_copyer; // 抄送人人按钮


    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
    List<String> reasonlist = new ArrayList<>();//报销事由
    private OptionsPickerView reasonPicker;
    String reason = "";
    //审批人
    private List<String> approverList = new ArrayList<>();
    //抄送人
    private List<People> copyerList = new ArrayList<>();
    private int count1 = 0;
    public  int count2 = 0;
    List<ExpenseBean> list = new ArrayList<>();
    private ExpenseImageAdapter adapter;
    private ExpenseApproverAdapter approverAdapter;
    //照片选择最大值
    private int maxSelectNum = 9;
    public int positionMain;
    public int positionChildMain;
    //抄送人
    @BindView(R.id.gv_test)
    MyGridView gvTest;
    List<People> mList = new ArrayList<People>();
    MeetGridViewAdapter adapter1;
    AlertDialog myDialog;
    @BindView(R.id.titleView)
    ImageView titleView;
    int a ;
    int picCount = 0;
    String copname = "";
    String copid = "";
    private String appName = "";
    private String appId = "";
    private List<AppersonBean> memberList ;
    // 审批人
    private OptionsPickerView reasonPicker1;
    private OptionsPickerView reasonPicker2;
    private String mony = "";
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_name1)
    TextView tv_name1;
    @BindView(R.id.iv_image)
    ImageView iv_image;
    private  Dialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.work_expense_applyfor_s);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        init();
        initData();
        addItem();
    }
    private void initData() {
        //申请人姓名
        //nickname是姓名  username是账号
        tv_sqr.setText(SPUtils.get(ExpenseApplyForActivity.this, "nickname", "").toString());
        tv_sqbm.setText(SPUtils.get(ExpenseApplyForActivity.this, "deptname", "").toString());
        tv_bill_count.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        et_sub_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (null != editable) {
                        if (0 != editable.length()) {//没有输入则清空搜索记录
                            if (Double.parseDouble(editable.toString().trim())>0)
                                mony = editable.toString().trim();
                        }
                }
            }
        });
    }
    @OnClick({R.id.tv_addItem,R.id.tv_sum,R.id.iv_back,R.id.rl_copyer,R.id.rl_approver})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.tv_addItem:
                //新增-添加一个数据为空的新对象
                addItem();
                break;
            case R.id.tv_sum:
                Rule_form();
//                submit1();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_copyer:
                if (appId.equals("")){
                    Toast.makeText(this,"请先选择审批人",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent1 = new Intent(this, SelDepartmentActivity_meet_expenseApply.class);
                intent1.putExtra("appId",appId + "," + SPUtils.get(mContext, "userId", "").toString());
                startActivityForResult(intent1,10);
                break;
            case R.id.rl_approver:
                if (mony == ""){
                    Toast.makeText(mContext,"金额不能为空",Toast.LENGTH_LONG).show();
                }else {
                    if (SPUtils.get(this, "roleKey", "").toString().contains("boss")|| SPUtils.get(this, "roleKey", "").toString().contains("synthesizeLead")) {
                        getAppror(mony,"1");
                    }else {
                        if (Double.parseDouble(mony)>200){
                            getAppror(mony,"2");
                        }else {
                            getAppror(mony,"1");
                        }
                    }

                }
                break;

        }
    }
    private void submint() {
        RequestParams params = new RequestParams();
        //创建人id
        params.put("createBy", SPUtils.get(mContext, "userId", "").toString());
        //创建人名称
        params.put("createName",SPUtils.get(ExpenseApplyForActivity.this, "nickname", "").toString());
        //部门id
        params.put("deptId", SPUtils.get(ExpenseApplyForActivity.this, "deptId", "").toString());
        //部门名称
        params.put("deptName", SPUtils.get(ExpenseApplyForActivity.this, "deptname", "").toString());
        //创建时间
        params.put("createTime",tv_time_content.getText().toString().trim());
        //	单据张数
        params.put("receiptSum", tv_bill_count.getText().toString().trim());
        //	总金额
        params.put("money",et_sub_money.getText().toString().trim());
        params.put("approver","185");
        params.put("approverName","曹宇");
        HttpRequest.post_sendExpense2(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.e("报销申请","请求成功");
                String id = "";
                showDialog();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Log.e("报销申请","请求失败");
            }
        });
//
//

//
//        //创建人id
//        params.put("createBy", SPUtils.get(mContext, "userId", "").toString());
//        //创建人名称
//        params.put("createName",SPUtils.get(ExpenseApplyForActivity.this, "nickname", "").toString());
//        //部门id
//        params.put("deptId", SPUtils.get(ExpenseApplyForActivity.this, "deptId", "").toString());
//        //部门名称
//        params.put("deptName", SPUtils.get(ExpenseApplyForActivity.this, "deptname", "").toString());
//        //创建时间
//        params.put("createTime",tv_time_content.getText().toString().trim());
//        //	单据张数
//        params.put("receiptSum", tv_bill_count.getText().toString().trim());
//        //	总金额
//        params.put("money",et_sub_money.getText().toString().trim());
        //审批人id
//        params.put("approver", approverList.get(0));
        //审批人名称
//        params.put("approverName", approverList.get(0));
        //抄送人id
//        params.put("copier", copyerList.get(0).getUserId());
        //抄送人名称
//        params.put("copierName", copyerList.get(0).getUserName());
        List<File> list = new ArrayList<>();
        for (LocalMedia imgurl : selectList) {
            list.add(new File(imgurl.getPath()));
        }
//        HttpRequest.post_sendExpense2(params, list, new ResponseCallback() {
//            @Override
//            public void onSuccess(Object responseObj) {
//                Log.e("报销申请","请求成功");
//                showDialog();
//            }
//
//            @Override
//            public void onFailure(OkHttpException failuer) {
//                Log.e("报销申请","请求失败");
//            }
//        });
    }

    /**
     * 判断发票是否上传了
     */
    private void Rule_form(){
        boolean lose = true;
        for (int i = 0 ;i < list.size();i++){
            if (!TextUtils.isEmpty(list.get(i).getExpense_money())){
                if (list.get(i).getList_invoice().size()<1){
                    lose = false;
                }else {
                    lose = true;
                }
            }else {
                lose = false;
            }
        }
        if (lose){
            if (TextUtils.isEmpty(tv_name.getText().toString().trim())){
                Toast.makeText(mContext,"请选择审批人",Toast.LENGTH_LONG).show();
            }else {
                submit1();
            }
        }else {
            Toast.makeText(mContext,"请查看是否有金额或单据未上传！",Toast.LENGTH_LONG).show();
        }
    }
    public void submit1(){
        loadDialog.show();
        RequestParams params = new RequestParams();
        //创建人id
        params.put("createBy", SPUtils.get(mContext, "userId", "").toString());
        //创建人名称
        params.put("createName",SPUtils.get(ExpenseApplyForActivity.this, "nickname", "").toString());
        //部门id
        params.put("deptId", SPUtils.get(ExpenseApplyForActivity.this, "deptId", "").toString());
        //部门名称
        params.put("deptName", SPUtils.get(ExpenseApplyForActivity.this, "deptname", "").toString());
        //创建时间
        params.put("createTime",tv_time_content.getText().toString().trim());
        //	单据张数
        params.put("receiptSum", tv_bill_count.getText().toString().trim());
        //	总金额
        params.put("money",et_sub_money.getText().toString().trim());
        //抄送人ID
        params.put("copier",copid);
        //抄送人名字
        params.put("copierName",copname);
        // 审批人id
        params.put("approver",appId);
        // 审批人名字
        params.put("approverName",appName);
        HttpRequest.post_sendExpense2(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.e("报销申请","请求成功");
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONObject obj = new JSONObject(result.toString());
                    String id = obj.getString("msg");
                    if (!id.equals("")){
                        for (int i = 0;i<list.size();i++){
                            getList(id,list.get(i).getExpense_money(),list.get(i).getReason_num(),list.get(i).getExpense_legend(),list.get(i).getList_invoice());
                        }
                    }else {
                        Toast.makeText(ExpenseApplyForActivity.this,"传输中断，没有ID",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Log.e("报销申请","请求失败");
            }
        });
    }
    private void getList(String id,String expense_money,String expense_num,String expense_legend,List<LocalMedia> localMedia){
        if (expense_num == null){
            expense_num = "9";
        }
        // 请求操作
        RequestParams params = new RequestParams();
        params.put("proId", id);
        params.put("lineMoney", expense_money);
        params.put("approveType", expense_num);
        params.put("cause", expense_legend);
        List<File>files = new ArrayList<>();
        for (LocalMedia imgurl : localMedia) {
            files.add(new File(imgurl.getPath()));
        }
        HttpRequest.post_sendExpense3(params, files, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                    loadDialog.dismiss();
                    showDialog();
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });

    }
    private void addItem() {
        reasonlist.clear();
        ExpenseBean expense = new ExpenseBean();
        List<LocalMedia> localMediaList = new ArrayList<>();
        expense.setIndex(list.size()+"");
        expense.setList_invoice(localMediaList);
        list.add(expense);
        adapter.notifyItemInserted(list.size());
    }
    //添加事项--点击事件(删除，报销事由点击)
    public ExpenseImageAdapter.OnItemClickListener onItemClickListener = new ExpenseImageAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position, View v) {
            switch (v.getId()){
                case R.id.iv_delete:
                    Log.e("删除条目:","外层位置："+position);
                    adapter.removeItem(position);
                    Double money = 0.0;
                    for (int i=0;i<list.size();i++){
                        if (!TextUtils.isEmpty(list.get(i).getExpense_money())){
                            money +=Double.parseDouble(list.get(i).getExpense_money());
                        }
                    }
                    et_sub_money.setText(money+"");


                    a = 0;
                    Log.e("Item数量:",list.size()+"");
                    for (int i = 0;i<list.size();i++){
                        int size = list.get(i).getList_invoice().size();
                        Log.e("图片数量:",size+"");
                        a =a+ size;
                    }
                    Log.e("picCount数量:",picCount+"");
//                    a = 0;
//                    Log.e("Item数量:",list.size()+"");
//                    for (int i = 0;i<list.size();i++){
//                        int size = list.get(i).getList_invoice().size();
//                        Log.e("图片数量:",size+"");
//                        a =a+ size;
//                    }
//                    Log.e("picCount数量:",picCount+"");
                    tv_bill_count.setText(a + "");
                    break;
                case R.id.tv_expense_reason:
                    Log.e("报销事由:","外层位置："+position);
                    approverList.clear();
                    reasonlist.clear();
                    initReason();
                    reasonPicker.show();
                    positionMain = position;
                    break;
            }
        }
    };
    //添加事项--编辑金额
    public ExpenseImageAdapter.OnItemEditListener onItemEditListener = new ExpenseImageAdapter.OnItemEditListener() {
        @Override
        public void onEditItem(int position, CharSequence charSequence) {//position为外层位置
            list.get(position).setExpense_money(charSequence+"");
            Double money = 0.0;
            for (int i=0;i<list.size();i++){
                if (!TextUtils.isEmpty(list.get(i).getExpense_money())){
                    money +=Double.parseDouble(list.get(i).getExpense_money());
                }
            }
            et_sub_money.setText(money+"");
            Log.e("编辑金额:","外层位置："+position+"输出的结果:"+charSequence);
        }
    };
    //编辑费用说明
    public ExpenseImageAdapter.OnItemEditListener2 onItemEditListener2 = new ExpenseImageAdapter.OnItemEditListener2(){

        @Override
        public void onEditItem(int position, CharSequence charSequence) {
            list.get(position).setExpense_legend(charSequence+"");
            Log.e("费用说明:","外层位置："+position+"输出的结果:"+charSequence);
        }
    };
    //添加事项--添加图片回调
    public ExpenseSecondAdapter.onAddPicClickListener onAddPicClickListener = new ExpenseSecondAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int position,int positionChaild, View view) {//position为外层数据
            Log.e("添加图片事件:","外层位置："+position);
            positionMain = position;
            positionChildMain = positionChaild;
            initSelectImage();
        }
    };
    //添加事项--删除子条目中的图片事件回调
    public ExpenseSecondAdapter.OnItemDeleteListener onItemDeleteListener = new ExpenseSecondAdapter.OnItemDeleteListener() {
        @Override
        public void onItemDelete(int position, int positionChild, View v) {
            Log.e("删除子条目:","外层位置："+position+"内层位置"+positionChild);
            list.get(position).getList_invoice().remove(positionChild);
            adapter.getData(position,positionChild);
            for (int i=0;i<list.size();i++){
                Log.e("第一层数据",list.get(i).getIndex());
                List<LocalMedia> localMediaList = list.get(i).getList_invoice();
                for (int j=0;j<localMediaList.size();j++){
                    Log.e("第二层数据",localMediaList.get(j).getCompressPath());
                }
            }
            a = a - 1;
            tv_bill_count.setText(a+"");

        }
    };
    //添加审批人
    public ExpenseApproverAdapter.onAddPicClickListener onAddExpenseClickListener = new ExpenseApproverAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int position, View view) {
//            startActivity(new Intent(mContext, SubordpersonActivity.class));
            startActivity(new Intent(mContext, SelDepartmentActivity.class));
            count1++;
            approverList.add("大老板"+count1);
            approverAdapter.notifyItemInserted(approverList.size());

        }
    };
    //删除审批人
    public ExpenseApproverAdapter.OnItemDeleteListener onItemCopyerDeleteListener = new ExpenseApproverAdapter.OnItemDeleteListener() {
        @Override
        public void onItemDelete(int position, View v) {

        }
    };
    //添加抄送人
    public ExpenseCopyerAdapter.onAddPicClickListener onAddCopyer = new ExpenseCopyerAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int position, View view) {
//            count2++;
            startActivityForResult(new Intent(mContext, SelDepartmentActivity_meet_zb.class), 0);

        }
    };
    //删除抄送人
    public ExpenseCopyerAdapter.OnItemDeleteListener onDeleteCopyer = new ExpenseCopyerAdapter.OnItemDeleteListener() {
        @Override
        public void onItemDelete(int position, View v) {

        }
    };
    public void init(){
        //添加事项初始化
        myDialog = new AlertDialog(this).builder();
        adapter = new ExpenseImageAdapter(mContext,onItemClickListener,onItemEditListener,onItemEditListener2,onAddPicClickListener,onItemDeleteListener);
        MyLinearLayoutManager layoutManager = new MyLinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(false);
        recycler_item.setLayoutManager(layoutManager);
//            holder.recycler_bill.addItemDecoration();
        recycler_item.setAdapter(adapter);
        //添加事件
        adapter.setSelectMax(maxSelectNum);
        adapter.setList(list);
        recycler_item.setAdapter(adapter);

        //审批人初始化
        approverAdapter = new ExpenseApproverAdapter(mContext,onAddExpenseClickListener,onItemCopyerDeleteListener);
//        FlowLayoutManager flowLayoutManager = new FlowLayoutManager(mContext,true);
        MyLayoutManager flowLayoutManager = new MyLayoutManager();
//        recycler_approver.addItemDecoration(new ItemDecoration(DisplayUtils.dip2px(mContext,6)));
        //必须，防止recyclerview高度为wrap时测量item高度0
        flowLayoutManager.setAutoMeasureEnabled(true);
        approverAdapter.setList(approverList);
//        recycler_approver.setLayoutManager(flowLayoutManager);
//        recycler_approver.setAdapter(approverAdapter);
//        copyerAdapter = new ExpenseCopyerAdapter(mContext,onAddCopyer,onDeleteCopyer);
//        MyLayoutManager flowManager = new MyLayoutManager();
//        recycler_copyer.addItemDecoration(new ItemDecoration(DisplayUtils.dip2px(mContext,6)));
//        //必须，防止recyclerview高度为wrap时测量item高度0
//        flowManager.setAutoMeasureEnabled(true);
//        copyerAdapter.setList(copyerList);
//        recycler_copyer.setLayoutManager(flowManager);
//        recycler_copyer.setAdapter(copyerAdapter);
        //抄送人初始化
        tv_time_content.setText(TimeUtil.getNowDatetime1());
    }
    private void initSelectImage() {
        PictureSelector.create(ExpenseApplyForActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(5)// 最大图片选择数量
                .minSelectNum(0)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .selectionMedia(selectListPic)// 是否传入已选图片
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
//                    a += selectList.size();
                    Log.e("这是多少",a+"");
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    List<LocalMedia> listMedia = new ArrayList<>();
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", new File(media.getPath()).length() + "");
                        Log.i("压缩图片-----》", new File(media.getCompressPath()).length() + "");
                        Bitmap bitmap = BitmapFactory.decodeFile(media.getCompressPath());
                        listMedia.add(media);
                    }
                    ExpenseBean expenseBean = list.get(positionMain);
                    expenseBean.setList_invoice(listMedia);
                    list.set(positionMain,expenseBean);
                    adapter.notifyDataSetChanged();//刷新数据
                    a = 0;
                    Log.e("Item数量:",list.size()+"");
                    for (int i = 0;i<list.size();i++){
                        int size = list.get(i).getList_invoice().size();
                        Log.e("图片数量:",size+"");
                        a =a+ size;
                    }
                    Log.e("picCount数量:",picCount+"");
                    tv_bill_count.setText(a + "");
                    break;
            }
        }
        if (requestCode == 10){
            if (data!=null){
                copname = "";
                copid = "";
                mList = new ArrayList<>();
                List<People> mList1 = (List<People>) data.getSerializableExtra("mList");
                mList.addAll(mList1);
                mList = TimeUtils.removeDuplicateWithOrder(mList);
                if (mList.size() > 0) {
//                // 替换了
                    for (People params1 : mList) {
                        copname += params1.getUserName() + ",";
                        copid += params1.getUserId() +",";
                    }
                    adapter1 = new MeetGridViewAdapter(ExpenseApplyForActivity.this, mList);
                    gvTest.setAdapter(adapter1);
//                //如果超过5个隐藏按钮
                    if (mList.size()>4){
                        Toast.makeText(this,"抄从人最多5人",Toast.LENGTH_LONG).show();
                        titleView.setVisibility(View.GONE);
                    }else {
                        titleView.setVisibility(View.VISIBLE);
                    }
                    List<People> finalMList = mList;
                    gvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            myDialog.setGone().setTitle("提示").setMsg("确定删除么").setNegativeButton("取消", null).setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finalMList.remove(position);
                                    if (mList.size()>4){
                                        titleView.setVisibility(View.GONE);
                                    }else {
                                        titleView.setVisibility(View.VISIBLE);
                                    }
                                    adapter1.notifyDataSetChanged();
                                }
                            }).show();
                        }
                    });
                }
            }

        }

    }
    private void initReason() {
        reasonlist.add("采购费");
        reasonlist.add("加班餐费");
        reasonlist.add("加班打车费");
        reasonlist.add("外出打车费");
        reasonlist.add("办公用品费");
        reasonlist.add("差旅费");
        reasonlist.add("员工福利费");
        reasonlist.add("招待费");
        reasonlist.add("其他费用");
        reasonPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                reason = reasonlist.get(options1);
                list.get(positionMain).setExpense_reason(reason);
                list.get(positionMain).setReason_num(options1+1+"");
                adapter.notifyDataSetChanged();
            }
        }).setTitleText("报销类别").setContentTextSize(22).setTitleSize(22).setSubCalSize(21).build();
        reasonPicker.setPicker(reasonlist);
    }
    public void onEventMainThread(People event) {
        showToast(event.getUserName());
    }
    @Override
    protected void onDestroy() {
        if(dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    private void showDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_submit, null);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText("报销申请成功");
        TextView submit = view.findViewById(R.id.confirm);
        dialog = new MyDialog(ExpenseApplyForActivity.this, true, true, (float) 0.7).setNewView(view);
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

    /**
     * 200元一下
     * 获取审批人
     */
    public void getAppror(String money,String type){
        memberList = new ArrayList<>();
        appName = "";
        appId = "";
        RequestParams params = new RequestParams();
        //创建人id
        params.put("userId", SPUtils.get(mContext, "userId", "").toString());
        params.put("money1", "1");
        HttpRequest.get_Expense_aper(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
//                    memberList.clear();
//                    approverList.clear();
//                    appId = "";
//                    appName = "";
                    JSONObject result = new JSONObject(responseObj.toString());
                    memberList = gson.fromJson(result.getJSONArray("data").toString(), new TypeToken<List<AppersonBean>>() {}.getType());
//                    for (int i = 0;i < memberList.size();i++){
//                        appName += memberList.get(i).getUserName() + ",";
//                        appId += memberList.get(i).getUserId() + ",";
//                        approverList.add(memberList.get(i).getUserName());
//                    }
//                    approverAdapter.notifyItemInserted(approverList.size());
//                    Log.e("ID+名字",appName+"---->"+appId);

                    hideInput();
                    initreason1(memberList,type);
                    reasonPicker1.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }

    /**
     * 选择审批人
     */
    public void initreason1(List<AppersonBean>appersonBeans,String type){
        appId = "";
        appName = "";
        List<String> oa = new ArrayList<>();
        for (int i = 0;i<appersonBeans.size();i++){
            oa.add(appersonBeans.get(i).getUserName());
        }
        reasonPicker1 = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                reason = oa.get(options1);
                appId = appersonBeans.get(options1).getUserId();
                appName = reason;
//                list.get(positionMain).setExpense_reason(reason);
//                list.get(positionMain).setReason_num(options1+1+"");
//                adapter.notifyDataSetChanged();
//                approverList.add(reason);
//                approverAdapter.notifyItemInserted(approverList.size());
                tv_name.setText(reason);
                iv_image.setVisibility(View.GONE);
                tv_name1.setVisibility(View.GONE);
                if (type.equals("2")){
                    getAppror1(mony);
                }else {

                }
            }
        }).setTitleText("综合审批人").setContentTextSize(22).setTitleSize(22).setSubCalSize(21).build();
        reasonPicker1.setPicker(oa);

    }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }


    /**
     * 200元以上
     * 获取审批人
     */
    public void getAppror1(String money){
        RequestParams params = new RequestParams();
        //创建人id
        params.put("userId", SPUtils.get(mContext, "userId", "").toString());
        params.put("money1", "2");
        HttpRequest.get_Expense_aper(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    memberList = gson.fromJson(result.getJSONArray("data").toString(), new TypeToken<List<AppersonBean>>() {}.getType());
                    initreason2(memberList);
                    reasonPicker2.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }

    public void initreason2(List<AppersonBean>appersonBeans){
        List<String> oa = new ArrayList<>();
        for (int i = 0;i<appersonBeans.size();i++){
            oa.add(appersonBeans.get(i).getUserName());
        }
        reasonPicker2 = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                reason = oa.get(options1);
                appId += "," + appersonBeans.get(options1).getUserId();
                appName += ","+reason;
//                adapter.notifyDataSetChanged();
//                approverList.add(reason);
//                approverAdapter.notifyItemInserted(approverList.size());
                iv_image.setVisibility(View.VISIBLE);
                tv_name1.setVisibility(View.VISIBLE);
                tv_name1.setText(reason);
            }
        }).setTitleText("高级审批人").setContentTextSize(22).setTitleSize(22).setSubCalSize(21).build();
        reasonPicker2.setPicker(oa);
    }

}
