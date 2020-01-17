package com.hulian.oa.work.file.admin.activity.meeting;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.Meeting;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.ToastHelper;
import com.netease.nim.uikit.business.contact.core.viewholder.TextHolder;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 会议签到
 */


public class MeetingSigninActivity extends BaseActivity {

    @BindView(R.id.et_title)
    TextView et_title;
    @BindView(R.id.tv_part_time)
    TextView tv_part_time;
    @BindView(R.id.tv_part_count)
    TextView tv_part_count;
    @BindView(R.id.tv_part_person)
    TextView tv_part_person;
    @BindView(R.id.tv_back_instruct)
    TextView tvBackInstruct;
    @BindView(R.id.tv_meet_room_num)
    TextView tvMeetRoomNum;
    @BindView(R.id.tv_meet_count)
    TextView tvMeetCount;
    @BindView(R.id.et_signType)
    TextView etSignType;
    @BindView(R.id.tv_part_lianx)
    TextView tvPartLianx;
    @BindView(R.id.tv_part_lianx_phone)
    TextView tvPartLianxPhone;
    @BindView(R.id.tv_part_time2)
    TextView tvPartTime2;
    @BindView(R.id.et_content)
    TextView et_content;
    private int REQUEST_CODE_SCAN = 991;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_meeting_signin);
        ButterKnife.bind(this);
        getData();

    }


    @OnClick({R.id.tv_back_instruct, R.id.iv_back, R.id.rl_title,R.id.tv_part_lianx_phone})
    public void onViewClicked(View view) {
//        Intent intent=new Intent(MeetingSigninActivity.this, MainActivity.class);
//        intent.putExtra("isgowork", true);
//        startActivity(intent);
//        finish();

        switch (view.getId()) {
            case R.id.tv_back_instruct:
                if (TextUtils.equals(tvBackInstruct.getText(), "签到")){
                // sendData();
                Intent intent = new Intent(MeetingSigninActivity.this, CaptureActivity.class);
                /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
                 * 也可以不传这个参数
                 * 不传的话  默认都为默认不震动  其他都为true
                 * */

                ZxingConfig config = new ZxingConfig();
                config.setShowbottomLayout(false);//底部布局（包括闪光灯和相册）
                //config.setPlayBeep(true);//是否播放提示音
                //config.setShake(true);//是否震动
                //config.setShowAlbum(true);//是否显示相册
                //config.setShowFlashLight(true);//是否显示闪光灯
                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }else {
                    Toast.makeText(MeetingSigninActivity.this,"已经签到了",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_title:
                finish();
                break;
            case R.id.tv_part_lianx_phone:
                if (!tvPartLianxPhone.getText().toString().equals("")){
                    callPhone(tvPartLianxPhone.getText().toString());
                }
                break;
        }
    }

    private void getData() {

        RequestParams params = new RequestParams();
        params.put("meetingId", getIntent().getStringExtra("id"));
        HttpRequest.postMeeting(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    Meeting meeting = gson.fromJson(result.getJSONObject("data").toString(),
                            new TypeToken<Meeting>() {
                            }.getType());
                    if ("0".equals(meeting.getSignType())) {
                        tvBackInstruct.setVisibility(View.VISIBLE);
                        etSignType.setText("签到");
                    } else {
                        etSignType.setText("非签到");
                        tvBackInstruct.setText("已签到");
                        tvBackInstruct.setBackgroundResource(R.drawable.edit_background_order1);
                        tvBackInstruct.setVisibility(View.GONE);
                    }

                    et_title.setText(meeting.getMeetingTheme());
//                    tv_part_time.setText(TimeUtils.getDateToString(meeting.getMeetingTimeBegin()));
//                    tvPartTime2.setText(TimeUtils.getDateToString(meeting.getMeetingTimeEnd()));

                    tv_part_time.setText(meeting.getMeetingTimeBegin());
                    tvPartTime2.setText(meeting.getMeetingTimeEnd());



                    tvPartLianx.setText(result.getJSONObject("data").getString("meetingContacts"));
                    tvPartLianxPhone.setText(result.getJSONObject("data").getString("meetingContactsPhone"));
                    et_content.setText(result.getJSONObject("data").getString("meetingContent"));
                    tv_part_count.setText(meeting.getParticipants().size() + "人");
                    String nameListStr = "";
                    for (int i = 0; i < meeting.getParticipants().size(); i++) {
                        if (i == 0) {
                            nameListStr = meeting.getParticipants().get(i).getParticipantName();
                        } else {
                            nameListStr = nameListStr + "," + meeting.getParticipants().get(i).getParticipantName();
                        }
                        if (meeting.getParticipants().get(i).getParticipantId().equals(SPUtils.get(mContext, "userId", "-1").toString())) {
                            if (meeting.getParticipants().get(i).getSignStatus().equals("1")) {
//                                tvBackInstruct.setVisibility(View.GONE);
                                tvBackInstruct.setText("已签到");
                                tvBackInstruct.setBackgroundResource(R.drawable.edit_background_order1);
                            }
                        }
                    }
                    tvMeetRoomNum.setText(result.getJSONObject("data").getString("meetingRoomLocation"));
                    tvMeetCount.setText("容纳人数：" + result.getJSONObject("data").getString("galleryful"));
                    tv_part_person.setText(nameListStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });

    }

    private void sendData(String meetingId) {
        String userid = SPUtils.get(this, "userId", "-1").toString();
        RequestParams params = new RequestParams();
        params.put("participantId", userid);
        params.put("meetingId", getIntent().getStringExtra("id"));
        params.put("meetingRoomId", meetingId);
        HttpRequest.postMeetingsignIn(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (TextUtils.equals(result.getString("code"), "0")) {
                        finish();

                    }else {
                        Toast.makeText(MeetingSigninActivity.this,"签到失败",Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                try {
                    JSONObject result = new JSONObject(content);
                    Log.e("context",result.getString("id"));
                    sendData(result.getString("id"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //  sendData();
            }
        }
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

}
