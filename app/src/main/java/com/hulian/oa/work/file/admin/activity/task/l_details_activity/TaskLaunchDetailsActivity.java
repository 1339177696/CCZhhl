package com.hulian.oa.work.file.admin.activity.task.l_details_activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.agency.l_fragment.L_UpcomFragment;
import com.hulian.oa.bean.Hufu_bean;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.work.file.admin.activity.task.l_details_adapter.L_DetailsLaunchTaskAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.netease.nim.avchatkit.common.util.TimeUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

//我发起的详情22 带回复
public class TaskLaunchDetailsActivity extends BaseActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {
    @BindView(R.id.listview)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    @BindView(R.id.launch_minput)
    EditText launch_minput;
    @BindView(R.id.tv_launch_send)
    ImageView tv_launch_send;
    @BindView(R.id.iv_btn)
    ImageView iv_btn;
    @BindView(R.id.la_un_start_time)
    TextView laUnStartTime;
    @BindView(R.id.la_un_stop_time)
    TextView laUnStopTime;

    private int mCount = 1;
    private RecyclerView mRecyclerView;
    L_DetailsLaunchTaskAdapter mRecyclerViewAdapter;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    private String porid;
    private String id;
    private Hufu_bean huifu_bean_x;
    private List<Hufu_bean> list = new ArrayList<>();
    private String context = "";
    //照片选择最大值
    private int maxSelectNum = 1;
    private List<LocalMedia> selectList = new ArrayList<>();
    @BindView(R.id.launch_rela_huifu)
    RelativeLayout launch_rela_huifu;
    @BindView(R.id.iv_back)
    FrameLayout iv_back;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.l_activity_launch_details_task);
        ButterKnife.bind(this);
        porid = getIntent().getStringExtra("PORID");
        id = getIntent().getStringExtra("ID");
        mContext = this;
        initList();
    }

    private void initList() {

        //获取mRecyclerView对象
        mRecyclerView = mPullLoadMoreRecyclerView.getRecyclerView();
//        mRecyclerView.setNestedScrollingEnabled(false);
        //代码设置scrollbar无效？未解决！
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //设置下拉刷新是否可见
        //mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置是否可以下拉刷新
        //mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
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
        mRecyclerViewAdapter = new L_DetailsLaunchTaskAdapter(this);
        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
        getData();

        launch_minput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //发送
                    launch_minput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (!v.getText().toString().trim().isEmpty()) {
                                //执行的操作
                                context = v.getText().toString().trim();
                                gethuifuData();
                                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(TaskLaunchDetailsActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                launch_minput.setText("");
                            }
                            return true;
                        }
                    });
                }
                return false;
            }
        });

    }

    @OnClick({R.id.tv_launch_send, R.id.iv_back, R.id.iv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_launch_send:
                initSelectImage();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_btn:
                Dialog_qgl();
                break;
        }
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
        mRecyclerViewAdapter.clearData();
        mCount = 1;
    }


    private void getData() {
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("type", "0");
        HttpRequest.post_workCoordinationRelease_details(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                //需要转化为实体对象
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    list = gson.fromJson(result.getJSONObject("data").getString("list"), new TypeToken<List<Hufu_bean>>() {
                    }.getType());
                    huifu_bean_x = gson.fromJson(result.getJSONObject("data").getString("object"), new TypeToken<Hufu_bean>() {
                    }.getType());
                    JSONObject jsonObject = new JSONObject(result.getJSONObject("data").getString("object"));
                    String a = jsonObject.getString("startTime");
                    laUnStartTime.setText(a.substring(0,a.length()-3).replace("-","/"));
                    String b = jsonObject.getString("endTime");
                    laUnStopTime.setText(b.replace("-","/"));
                    String sum = result.getJSONObject("data").getString("sum");
                    huifu_bean_x.setSum(sum);
                    if (huifu_bean_x.getCompletion().equals("1")) {
                        launch_rela_huifu.setVisibility(View.GONE);
                        iv_btn.setVisibility(View.GONE);
                    } else {
                        launch_rela_huifu.setVisibility(View.VISIBLE);
                        iv_btn.setVisibility(View.GONE);
                    }

                    mRecyclerViewAdapter.addAllData(list, huifu_bean_x);
                    mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
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

    private void gethuifuData() {
        RequestParams params = new RequestParams();
        params.put("proid", id);
        params.put("content", context);
        params.put("respondent", SPUtils.get(mContext, "userId", "").toString());
        List<File> fils = new ArrayList<>();
        for (LocalMedia imgurl : selectList) {
            fils.add(new File(imgurl.getPath()));
        }
        HttpRequest.post_workCoordinationReply_add(params, fils, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {

                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    ToastHelper.showToast(mContext, result.getString("msg"));
                    if (result.getString("code").equals("0")) {
                        onRefresh();
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

    private void initSelectImage() {
        PictureSelector.create(TaskLaunchDetailsActivity.this)
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
            //    .selectionMedia(selectList)// 是否传入已选图片
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
                        Log.i("图片地址-----》", media.getCompressPath());
                        Log.i("图片-----》", new File(media.getPath()).length() + "");
                        Log.i("压缩图片-----》", new File(media.getCompressPath()).length() + "");
                        context = "";
                        gethuifuData();


                    }
                    break;
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private View inflate;
    private Dialog dialog;

    public void Dialog_qgl() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_qgl, null);
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;
        dialogWindow.setAttributes(lp);
        dialog.show();
        dialog.getWindow().findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.getWindow().findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_com_Data(id);
                dialog.dismiss();

            }
        });

        dialog.getWindow().findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    //发送完成接口
    private void get_com_Data(String id) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        HttpRequest.post_CoordinationRelease_deit(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONObject obj = new JSONObject(result.toString());
                    String code = obj.getString("code");
                    String msg = obj.getString("msg");
                    if (code.equals("0")) {
                        EventBus.getDefault().post(new L_UpcomFragment());
                        //   EventBus.getDefault().post(new L_HascomFragment());
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                        launch_rela_huifu.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
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

}
