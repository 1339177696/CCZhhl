package com.hulian.oa.work.activity.leave;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.People;
import com.hulian.oa.bean.People_x;
import com.hulian.oa.bean.Userqglbean;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.push.activity.PersonqglActivity;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.utils.FullyGridLayoutManager;
import com.hulian.oa.adpter.GridImageAdapter;
import com.hulian.oa.work.activity.leave.l_fragment.LeaveLaunchFragment;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 请假申请
 */
public class LeaveApplyforActivity extends BaseActivity {

    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.fl_content)
    FrameLayout fl_content;
    @BindView(R.id.tv_opreator)
    TextView tvOpreator;
    String tvOpreatorCode = "";
    //抄送人
    @BindView(R.id.title11)
    ImageView title11;
    @BindView(R.id.fl_content1)
    FrameLayout fl_content1;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.copier)
    TextView copier;
    String copierCode = "";
    String approverName = "";
    private List<People> selectList2 = new ArrayList<>();
    private List<People_x> selectList2_x = new ArrayList<>();
    private OptionsPickerView reasonPicker;//时间;
    List<String> reasonlist = new ArrayList<>();
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.iv_reason_more)
    TextView ivReasonMore;
    @BindView(R.id.tv_reaseon)
    TextView tvReaseon;
    @BindView(R.id.rl_leave_reason)
    RelativeLayout rlLeaveReason;
    @BindView(R.id.iv_start_more)
    ImageView ivStartMore;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.rl_start_time)
    RelativeLayout rlStartTime;
    @BindView(R.id.iv_end_more)
    ImageView ivEndMore;
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;
    @BindView(R.id.tv_day)
    EditText tvDay;
    @BindView(R.id.rl_duration)
    RelativeLayout rlDuration;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rl_leave_details)
    LinearLayout rlLeaveDetails;
    @BindView(R.id.tv_document_image)
    TextView tvDocumentImage;
    @BindView(R.id.tv_approved_person)
    TextView tvApprovedPerson;
    @BindView(R.id.ci_approved_pic)
    RelativeLayout ciApprovedPic;
    @BindView(R.id.tv_copy_person_title)
    TextView tvCopyPersonTitle;
    @BindView(R.id.ci_copy_pic)
    RelativeLayout ciCopyPic;
    @BindView(R.id.tv_back_instruct)
    TextView tvBackInstruct;
    private GridImageAdapter adapter;
    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
    //照片选择最大值
    private int maxSelectNum = 1;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    //新加的交接人
    @BindView(R.id.ci_jiaojie_pic)
    RelativeLayout ci_jiaojie_pic;
    @BindView(R.id.jiaojie_person)
    TextView jiaojie_person;
    private String jiaojiecode = "";
    @BindView(R.id.fl_content2)
    FrameLayout fl_content2;
    private List<Userqglbean>userqgllist = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_leave_applyfor);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mContext = this;
        init();
        initReason();
        tvReaseon.setText("事假");
    }

    private void initReason() {
        reasonlist.add("事假");
        reasonlist.add("病假");
        reasonlist.add("年假");
        reasonlist.add("调休");
        reasonlist.add("婚假");
        reasonlist.add("产假");
        reasonlist.add("路途假");
        reasonlist.add("其它");
        reasonPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvReaseon.setText(reasonlist.get(options1));
            }
        }).setTitleText("请假类别").setContentTextSize(22).setTitleSize(22).setSubCalSize(21).build();
        reasonPicker.setPicker(reasonlist);
    }

    private void init() {

        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(mContext, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                PictureSelector.create(LeaveApplyforActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList);
            }
        });

    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick() {
            initSelectImage();
        }
    };

    private void initSelectImage() {
        PictureSelector.create(LeaveApplyforActivity.this)
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
                        adapter.setList(selectList);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case 110:
                    List<People> mList = (List<People>) data.getSerializableExtra("mList");
                    fl_content.setVisibility(View.VISIBLE);
                    tvOpreator.setText(mList.get(0).getUserName());
                    tvOpreatorCode = mList.get(0).getUserId();
                    approverName = mList.get(0).getUserName();
                    break;
                case 120:
                    List<People> mList1 = (List<People>) data.getSerializableExtra("mList");
                    fl_content1.setVisibility(View.VISIBLE);
                    copier.setText(mList1.get(0).getUserName());
                    copierCode = mList1.get(0).getUserId();
                    break;
                case 520:
                    fl_content2.setVisibility(View.VISIBLE);
                    jiaojie_person.setText(data.getStringExtra("userName"));
                    jiaojiecode = data.getStringExtra("userId");

                    break;
            }
        }
    }
    @OnClick({R.id.iv_back, R.id.rl_leave_reason, R.id.rl_start_time, R.id.rl_end_time, R.id.tv_back_instruct, R.id.ci_jiaojie_pic,R.id.iv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_leave_reason:
                reasonPicker.show();
                break;
            case R.id.rl_start_time:
                selectTime(tvTimeStart);
                break;
            case R.id.rl_end_time:
                if (TextUtils.isEmpty(tvTimeStart.getText().toString().trim())) {
                    ToastHelper.showToast(mContext, "请先选择开始时间");
                    return;
                }
                selectTime(tvTimeEnd);
                break;
                // 交接人
            case R.id.ci_jiaojie_pic:
                Intent intent = new Intent(LeaveApplyforActivity.this, PersonqglActivity.class);
                startActivityForResult(intent, 520);
                break;
            case R.id.tv_back_instruct:
                postData();
                break;
            case R.id.iv2:
                fl_content2.setVisibility(View.GONE);
                jiaojiecode = "";
                break;

        }

    }

    private void postData() {
        if ("请选择请假类别".equals(tvReaseon.getText().toString().trim())) {
            ToastHelper.showToast(mContext, "请选择请假类别");
            return;
        }
        if ("请选择开始时间".equals(tvTimeStart.getText().toString().trim())) {
            ToastHelper.showToast(mContext, "请选择开始时间");
            return;
        }
        if ("请选择结束时间".equals(tvTimeStart.getText().toString().trim())) {
            ToastHelper.showToast(mContext, "请选择结束时间");
            return;
        }
        if (TextUtils.isEmpty(etContent.getText().toString().trim())) {
            ToastHelper.showToast(mContext, "请填写请假事由");
            return;
        }
        if (TextUtils.isEmpty(tvOpreatorCode)) {
            ToastHelper.showToast(mContext, "请选择审批人");
            return;
        }
        if (TextUtils.isEmpty(copierCode)) {
            ToastHelper.showToast(mContext, "请选择抄送人");
            return;
        }
        showDialogLoading();
        loadDialog.show();
        RequestParams params = new RequestParams();
        params.put("createBy", SPUtils.get(mContext, "userId", "").toString());
        params.put("copier", copierCode);
        params.put("approver", tvOpreatorCode);
        params.put("approverName", approverName);
        params.put("describe", etContent.getText().toString());
        params.put("duration", tvDay.getText().toString());
        params.put("startTime", tvTimeStart.getText().toString());
        params.put("endTime", tvTimeEnd.getText().toString());
        params.put("cause", tvReaseon.getText().toString());
        params.put("jobHandover", jiaojiecode);
        List<File> fils = new ArrayList<>();
        for (LocalMedia imgurl : selectList) {
            fils.add(new File(imgurl.getPath()));
        }
        HttpRequest.post_sendLeave(params, fils, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissDialogLoading();
                loadDialog.dismiss();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    ToastHelper.showToast(mContext, result.getString("msg"));
                    if (result.getString("code").equals("0")) {
                        EventBus.getDefault().post(new LeaveLaunchFragment());
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                dismissDialogLoading();
                loadDialog.dismiss();
                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectTime(TextView textView) {
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //判断选择开始时间是否大于当前时间
                if(TimeUtils.timeCompare(TimeUtils.getNowTime1(),getTime(date))==1){
                    ToastHelper.showToast(mContext, "请选择当前时间之后");
                }else {
                    //设置时间
                    textView.setText(getTime(date));
                }
                if (!"请选择开始时间".equals(tvTimeStart.getText().toString().trim()) && !"请选择结束时间".equals(tvTimeEnd.getText().toString().trim())) {
                    if (TimeUtils.differentDaysByMillisecond(tvTimeStart.getText().toString().trim(), tvTimeEnd.getText().toString().trim()) < 0) {
                        ToastHelper.showToast(mContext, "请选择不小于开始时间的结束时间");
                        tvDay.setText("");
                    } else
                        tvDay.setText((1 + TimeUtils.differentDaysByMillisecond(tvTimeStart.getText().toString().trim(), tvTimeEnd.getText().toString().trim())) + "");
                        // 天数换算成功之后请求接口
                        getPerson((1 + TimeUtils.differentDaysByMillisecond(tvTimeStart.getText().toString().trim(), tvTimeEnd.getText().toString().trim())) + "",SPUtils.get(mContext, "userId", "").toString(),SPUtils.get(mContext, "deptId", "").toString());
                }
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
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
    }

    // 抄送人
    public void onEventMainThread(People_x event_x) {
        selectList2_x.clear();
        selectList2_x.add(event_x);
    }


    // 请求审批人,抄送人
    public void getPerson(String day,String id,String bid){
        RequestParams params = new RequestParams();
        params.put("deptId",bid);
        params.put("userId",id);
        params.put("num",day);
        HttpRequest.getPerson(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                String username = "";
                String userid = "";
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONObject data = result.getJSONObject("data");
                    JSONArray jsonArray = data.getJSONArray("synthesizeEmpUser");
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject value = jsonArray.getJSONObject(i);
                        username += value.getString("userName") + ",";
                        userid += value.getString("userId") + ",";
                    }
                    tvOpreator.setText(data.getString("leaveApproveName"));
                    copier.setText(username.substring(0, username.length() - 1));
                    approverName = data.getString("leaveApproveName");
                    tvOpreatorCode = data.getString("leaveApproveId");
                    copierCode =userid.substring(0, userid.length() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });

    }

}
