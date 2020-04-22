package com.hulian.oa.work.activity.expense;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.bean.ExpenseBean;
import com.hulian.oa.utils.gallery.DisplayUtils;
import com.hulian.oa.views.flowview.ItemDecoration;
import com.hulian.oa.views.flowview.MyLayoutManager;
import com.hulian.oa.views.flowview.MyLinearLayoutManager;
import com.hulian.oa.views.flowview.NoScrollRecyclerView;
import com.hulian.oa.R;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.activity.attendance.ClockActivity;
import com.hulian.oa.work.activity.attendance.SubordpersonActivity;
import com.hulian.oa.work.activity.expense.l_adapter.ExpenseApproverAdapter;
import com.hulian.oa.work.activity.expense.l_adapter.ExpenseCopyerAdapter;
import com.hulian.oa.work.activity.expense.l_adapter.ExpenseImageAdapter;
import com.hulian.oa.work.activity.expense.l_adapter.ExpenseSecondAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.netease.nim.avchatkit.common.util.TimeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpenseApplyForActivity extends BaseActivity {
    @BindView(R.id.tv_addItem)
    TextView tv_addItem;
    @BindView(R.id.tv_sum)
    TextView tv_sum;
    @BindView(R.id.tv_time_content)
    TextView tv_time_content;
    @BindView(R.id.recycler_item)
    NoScrollRecyclerView recycler_item;//发票说明列表
    @BindView(R.id.recycler_approver)
    RecyclerView recycler_approver;//审批人
    @BindView(R.id.recycler_copyer)
    RecyclerView recycler_copyer;//抄送人
    @BindView(R.id.iv_back)
    RelativeLayout iv_back;
    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
    List<String> reasonlist = new ArrayList<>();//报销事由
    private OptionsPickerView reasonPicker;
    String reason = "";
    //审批人
    private List<String> approverList = new ArrayList<>();
    //抄送人
    private List<String> copyerList = new ArrayList<>();
    private int count1 = 0;
    public  int count2 = 0;
    List<ExpenseBean> list = new ArrayList<>();
    private ExpenseImageAdapter adapter;
    private ExpenseApproverAdapter approverAdapter;
    private ExpenseCopyerAdapter copyerAdapter;
    //照片选择最大值
    private int maxSelectNum = 9;
    public int positionMain;
    public int positionChildMain;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.work_expense_applyfor_s);
        ButterKnife.bind(this);
        init();
        addItem();
    }


    @OnClick({R.id.tv_addItem,R.id.tv_sum,R.id.iv_back})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.tv_addItem:
                //新增-添加一个数据为空的新对象
                addItem();
                break;
            case R.id.tv_sum:
                break;
            case R.id.iv_back:
                this.finish();
                break;
        }
    }

    private void addItem() {
        ExpenseBean expense = new ExpenseBean();
        List<LocalMedia> localMediaList = new ArrayList<>();
        expense.setIndex(list.size()+"");
        expense.setList_invoice(localMediaList);
        list.add(expense);
        adapter.notifyItemInserted(list.size());
    }

    ;
    //添加事项--点击事件(删除，报销事由点击)
    public ExpenseImageAdapter.OnItemClickListener onItemClickListener = new ExpenseImageAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position, View v) {
            switch (v.getId()){
                case R.id.iv_delete:
                    Log.e("删除条目:","外层位置："+position);
                    adapter.removeItem(position);
                    break;
                case R.id.tv_expense_reason:
                    Log.e("报销事由:","外层位置："+position);
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
        }
    };
    //添加审批人
    public ExpenseApproverAdapter.onAddPicClickListener onAddExpenseClickListener = new ExpenseApproverAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int position, View view) {
            startActivity(new Intent(mContext, SubordpersonActivity.class));
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
            count2++;
            copyerList.add("老板"+count2);
            copyerAdapter.notifyItemInserted(copyerList.size());

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
        recycler_approver.addItemDecoration(new ItemDecoration(DisplayUtils.dip2px(mContext,6)));
        //必须，防止recyclerview高度为wrap时测量item高度0
        flowLayoutManager.setAutoMeasureEnabled(true);
        approverAdapter.setList(approverList);
        recycler_approver.setLayoutManager(flowLayoutManager);
        recycler_approver.setAdapter(approverAdapter);

        copyerAdapter = new ExpenseCopyerAdapter(mContext,onAddCopyer,onDeleteCopyer);
//        FlowLayoutManager flowLayoutManager = new FlowLayoutManager(mContext,true);
        MyLayoutManager flowManager = new MyLayoutManager();
        recycler_copyer.addItemDecoration(new ItemDecoration(DisplayUtils.dip2px(mContext,6)));
        //必须，防止recyclerview高度为wrap时测量item高度0
        flowManager.setAutoMeasureEnabled(true);
        copyerAdapter.setList(copyerList);
        recycler_copyer.setLayoutManager(flowManager);
        recycler_copyer.setAdapter(copyerAdapter);
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
                    break;
            }
        }
    }

    private void initReason() {
        reasonlist.add("办公费");
        reasonlist.add("差旅费");
        reasonlist.add("通讯网络");
        reasonlist.add("会务费用");
        reasonlist.add("业务招待费");
        reasonlist.add("市内交通费");
        reasonlist.add("车辆使用费");
        reasonlist.add("其他费用");
        reasonPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                reason = reasonlist.get(options1);
                list.get(positionMain).setExpense_reason(reason);
                adapter.notifyDataSetChanged();
            }
        }).setTitleText("报销类别").setContentTextSize(22).setTitleSize(22).setSubCalSize(21).build();
        reasonPicker.setPicker(reasonlist);
    }
}
