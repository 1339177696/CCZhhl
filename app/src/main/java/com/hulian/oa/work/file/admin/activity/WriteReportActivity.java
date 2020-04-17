package com.hulian.oa.work.file.admin.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.views.MyDialog;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.FullyGridLayoutManager;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.L_GridImageAdapter;
import com.hulian.oa.work.fragment.ReadReportFragment;
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
 * Created by 陈泽宇 on 2020/3/11.
 * Describe:写日报页面
 */
public class WriteReportActivity extends BaseActivity {


    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.finished_work)
    EditText finishedWork;
    @BindView(R.id.unfinished_work)
    EditText unfinishedWork;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.plan_work)
    EditText planWork;
    @BindView(R.id.coordinate_work)
    EditText coordinateWork;//协调的工作
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.recipient)
    TextView recipient;//接收人
//    @BindView(R.id.work_leave_list_liner)
//    LinearLayout workLeaveListLiner;


    private int maxSelectNum = 9;
    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
    private L_GridImageAdapter adapter_grid;
    private String dialogText;
    private String type;
    private String recipientIds;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.activity_write_report);
        ButterKnife.bind(this);
        init();
        type = getIntent().getStringExtra("type");
        setTitleText(type);
        getRecipient();//获取接收人
    }

    @OnClick({R.id.iv_back, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.submit:
                sendData();
                break;
        }
    }

    private void setTitleText(String type) {

        switch (type) {
            case "1":
                titleText.setText("写日报");
                tv1.setText("今日完成工作");
                tv3.setText("明日工作计划");
                finishedWork.setHint("请输入今日完成的工作内容");
                planWork.setHint("请输入明日工作计划内容");
                dialogText = "提交日报成功";
                break;

            case "2":
                titleText.setText("写周报");
                tv1.setText("本周完成工作");
                tv3.setText("下周工作计划");
                finishedWork.setHint("请输入本周完成的工作内容");
                planWork.setHint("请输入下周工作计划内容");
                dialogText = "提交周报成功";
                break;

            case "3":
                titleText.setText("写月报");
                tv1.setText("本月完成工作");
                tv3.setText("下月工作计划");
                finishedWork.setHint("请输入本月完成的工作内容");
                planWork.setHint("请输入下月工作计划内容");
                dialogText = "提交月报成功";
                break;
        }
    }

    private void showDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_submit, null);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(dialogText);
        TextView submit = view.findViewById(R.id.confirm);

        Dialog dialog = new MyDialog(WriteReportActivity.this, true, true, (float) 0.7)
                .setNewView(view);
        dialog.show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交
                dialog.dismiss();
                finish();
            }
        });
    }

    //获取接收人
    private void getRecipient() {
        RequestParams params = new RequestParams();
        params.put("deptId", SPUtils.get(mContext, "deptId", "").toString());
        params.put("userId", SPUtils.get(mContext, "userId", "").toString());

        HttpRequest.getRecipient(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject jsonObject = new JSONObject(responseObj.toString());
                    if (TextUtils.equals("0", jsonObject.getString("code"))) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        switch (data.getString("type")) {
                            case "1"://综合部门领导
                                recipient.setText(data.getJSONObject("bossUser").getString("userName"));
                                recipientIds = data.getJSONObject("bossUser").getString("userId");
                                break;

                            case "2"://部门领导
                                recipient.setText(data.getJSONObject("bossUser").getString("userName") + "," + data.getJSONObject("synthesizeLeadUser").getString("userName"));
                                recipientIds = data.getJSONObject("bossUser").getString("userId")+ "," +data.getJSONObject("synthesizeLeadUser").getString("userId");
                                break;

                            case "3"://部门职员
                                recipient.setText(data.getJSONObject("synthesizeLeadUser").getString("userName") + "," + data.getJSONObject("deptLeadUser").getString("userName"));
                                recipientIds = data.getJSONObject("synthesizeLeadUser").getString("userId") + "," + data.getJSONObject("deptLeadUser").getString("userId");
                                break;
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }

    private void sendData() {
        if (TextUtils.isEmpty(finishedWork.getText())){
            ToastHelper.showToast(WriteReportActivity.this,"请输入完成的工作内容");
            return;
        }
//        if (TextUtils.isEmpty(planWork.getText())){
//            ToastHelper.showToast(WriteReportActivity.this,"请输入工作计划内容");
//            return;
//        }
        loadDialog.show();
        RequestParams params = new RequestParams();
        params.put("reportType", type);
        params.put("finishWork", finishedWork.getText() + "");
        params.put("unfinishedWork", unfinishedWork.getText() + "");
        params.put("tomorrowWorkPlan", planWork.getText() + "");
        params.put("coordinateWork", coordinateWork.getText() + "");
        params.put("receivePerson", recipientIds);
        params.put("receivePersonName", recipient.getText() + "");
        params.put("createBy",SPUtils.get(mContext, "userId", "").toString() );
        params.put("createByName",SPUtils.get(mContext, "nickname", "").toString() );


        //qgl新加的多图片上传
        List<File> fils = new ArrayList<>();
        for (LocalMedia imgurl : selectList) {
            fils.add(new File(imgurl.getPath()));
        }
        HttpRequest.postReport(params, fils, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                loadDialog.dismiss();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    showDialog();
                    EventBus.getDefault().post(new ReadReportFragment());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                loadDialog.dismiss();
                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //照片
    private void init() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(WriteReportActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        adapter_grid = new L_GridImageAdapter(WriteReportActivity.this, onAddPicClickListener);
        adapter_grid.setList(selectList);
        adapter_grid.setSelectMax(maxSelectNum);
        recycler.setAdapter(adapter_grid);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


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
                    adapter_grid.setList(selectList);
                    adapter_grid.notifyDataSetChanged();
                }
                break;
        }

    }

    private L_GridImageAdapter.onAddPicClickListener onAddPicClickListener = new L_GridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick() {
            initSelectImage();
        }
    };

    private void initSelectImage() {
        PictureSelector.create(WriteReportActivity.this)
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


}
