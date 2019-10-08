package com.hulian.oa.work.file.admin.activity.expense;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.People;
import com.hulian.oa.bean.People_x;
import com.hulian.oa.me.SelDepartmentActivity;
import com.hulian.oa.me.SelDepartmentActivity_x;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.FullyGridLayoutManager;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.L_GridImageAdapter;
import com.hulian.oa.work.file.admin.activity.expense.l_fragment.ExpenseCopymeFragment;
import com.hulian.oa.work.file.admin.activity.expense.l_fragment.ExpenseLaunchFragment;
import com.hulian.oa.work.file.admin.activity.leave.l_fragment.LeaveLaunchFragment;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 报销申请 填写申请数据
 */
public class ExpenseApplyForActivity extends BaseActivity {
    @BindView(R.id.tv_opreator)
    TextView tvOpreator;
    String  tvOpreatorCode="";
    @BindView(R.id.copier)
    TextView copier;
    String  copierCode="";
    private List<People> selectList2 = new ArrayList<>();
    private List<People_x> selectList2_x = new ArrayList<>();
    //报销事由
    @BindView(R.id.rl_expense_reason)
    RelativeLayout rl_expense_reason;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_expense_content)
    EditText tvExpenseContent;
    @BindView(R.id.et_expense_monkey)
    EditText etExpenseMonkey;
    @BindView(R.id.ci_approved_pic)
    RelativeLayout ciApprovedPic;
    @BindView(R.id.ci_copy_pic)
    RelativeLayout ciCopyPic;
    @BindView(R.id.tv_expense_submit)
    TextView tvExpenseSubmit;

    private L_GridImageAdapter adapter;
    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
    //照片选择最大值
    private int maxSelectNum = 9;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_expense_applyfor);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new L_GridImageAdapter(mContext, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new L_GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                PictureSelector.create(ExpenseApplyForActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList);
            }
        });

    }

    private L_GridImageAdapter.onAddPicClickListener onAddPicClickListener = new L_GridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick() {
            initSelectImage();
        }
    };

    private void initSelectImage() {
        PictureSelector.create(ExpenseApplyForActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
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
                .selectionMedia(selectList)// 是否传入已选图片
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
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", new File(media.getPath()).length() + "");
                        Log.i("压缩图片-----》", new File(media.getCompressPath()).length() + "");

//                        Bitmap bitmap = BitmapFactory.decodeFile(media.getCompressPath());
//                        iv_document.setImageBitmap(bitmap);
                        adapter.setList(selectList);
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_expense_submit, R.id.ci_approved_pic, R.id.ci_copy_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_expense_submit:
                postData();
                break;
            case R.id.ci_approved_pic:
                startActivity(new Intent(mContext, SelDepartmentActivity.class));
                break;
            case R.id.ci_copy_pic:
                startActivity(new Intent(mContext, SelDepartmentActivity_x.class));
                break;
        }
    }

    private void postData() {

        if(TextUtils.isEmpty(tvExpenseContent.getText().toString().trim())){
            ToastHelper.showToast(mContext,"请选择请假事由");
            return;
        }
        if(TextUtils.isEmpty(etExpenseMonkey.getText().toString().trim())){
            ToastHelper.showToast(mContext,"请添加报销金额");
            return;
        }
        if(selectList.size()<=0){
            ToastHelper.showToast(mContext,"请填写报销发票");
            return;
        }
        if(TextUtils.isEmpty(tvOpreatorCode)){
            ToastHelper.showToast(mContext,"请选择审批人");
            return;
        }
        if(TextUtils.isEmpty(copierCode)){
            ToastHelper.showToast(mContext,"请选择抄送人");
            return;
        }

        RequestParams params = new RequestParams();
        params.put("createBy", SPUtils.get(mContext, "userId", "").toString());
        params.put("copier", copierCode);
        params.put("approver", tvOpreatorCode);
        params.put("money", etExpenseMonkey.getText().toString());
        params.put("cause",tvExpenseContent.getText().toString());
        List<File> fils = new ArrayList<>();
        for (LocalMedia imgurl : selectList) {
            fils.add(new File(imgurl.getPath()));
        }
        HttpRequest.post_sendExpense(params, fils, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {

                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    ToastHelper.showToast(mContext, result.getString("msg"));
                    if (result.getString("code").equals("0")) {
                        EventBus.getDefault().post(new ExpenseLaunchFragment());
                        EventBus.getDefault().post(new ExpenseCopymeFragment());
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    // 执行人
    public void onEventMainThread(People event) {
        selectList2.clear();
        selectList2.add(event);
        String uids = "";
        String uname = "";
        for (People params1 : selectList2) {
            uids += params1.getUserId() + ",";
            uname += params1.getUserName() + ",";
        }
        tvOpreator.setText(uname.substring(0, uname.length() - 1));
        tvOpreatorCode = uids.substring(0, uids.length() - 1);
        //     Toast.makeText(this, uids.substring(0,uids.length()-1), Toast.LENGTH_SHORT).show();
    }

    // 抄送人
    public void onEventMainThread(People_x event_x) {
        selectList2_x.clear();
        selectList2_x.add(event_x);
        String uids = "";
        String uname = "";
        for (People_x params_x1 : selectList2_x) {
            uids += params_x1.getUserId();
            uname += params_x1.getUserName();
        }
        copier.setText(uname);
        copierCode = uids;
        //    Toast.makeText(this, uids.substring(0,uids.length()-1), Toast.LENGTH_SHORT).show();
    }
}
