package com.hulian.oa.work.activity.meeting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.MeetingRoom;
import com.hulian.oa.bean.People;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.views.AlertDialog;
import com.hulian.oa.views.MyGridView;
import com.hulian.oa.views.MyListView;
import com.hulian.oa.work.activity.meeting.l_adapter.MeetGridViewAdapter;
import com.hulian.oa.work.activity.meeting.l_adapter.MeetRoomAdapter;
import com.hulian.oa.work.activity.meeting.l_fragment.MeetLaunchFragment;
import com.hulian.oa.work.activity.meeting.l_fragment.MeetReceiverFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 会议发起
 */
public class MeetingSponsorActivity extends BaseActivity {
    List<People> mList = new ArrayList<People>();
    @BindView(R.id.gv_test)
    MyGridView gvTest;
    @BindView(R.id.lv_meet)
    MyListView lv_meet;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.tv_part_time)
    TextView tvPartTime;
    @BindView(R.id.tv_part_count)
    TextView tvPartCount;
    @BindView(R.id.tv_back_instruct)
    TextView tvBackInstruct;
    @BindView(R.id.rd_group)
    RadioGroup rdGroup;
    @BindView(R.id.tv_part_time2)
    TextView tvPartTime2;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_opreator_lianx)
    TextView tvLianxPerson;
    @BindView(R.id.et_title9)
    TextView etTitle9;
    private MeetRoomAdapter meetRoomAdapter;
    private String participantId;
    private String participantName;
    private String meetingTime = "";
    private String meetingTimeEnd = "";
    private String roomid = "";
    private String numberOfPeople = "";
    private String signType = "1";
    MeetGridViewAdapter adapter;
    AlertDialog myDialog;
    private String meetingContacts = "";
    private String meetingContactsPhone = "";
    // 会议名称
    private String meetingRoomName = "";
    // 会议地点
    private String meetingRoomLocation = "";
    @BindView(R.id.sw_refres)
    SwipeRefreshLayout sw_refres;
    private String cd = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_meeting_sponsor);
        myDialog = new AlertDialog(this).builder();
        ButterKnife.bind(this);
        //默认会议联系人，会议电话
        tvLianxPerson.setText(SPUtils.get(MeetingSponsorActivity.this,"nickname","").toString());
        etTitle9.setText( SPUtils.get(MeetingSponsorActivity.this,"username","").toString());
        meetingContacts = SPUtils.get(MeetingSponsorActivity.this,"nickname","").toString();
        meetingContactsPhone = SPUtils.get(MeetingSponsorActivity.this,"username","").toString();
        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radiobutton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                signType = radiobutton.getTag().toString();

            }
        });
        init();


    }

    private void init() {
        tvPartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime();
            }
        });
        // 刷新
        sw_refres.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh() {
                if (!cd.equals("")){
                    getData(cd);
                }else {
                    sw_refres.setRefreshing(false);
                }
            }
        });
    }

    public void onViewClicked() {
        startActivity(new Intent(MeetingSponsorActivity.this, MeetingAmendActivity.class));
    }

    @OnClick({R.id.iv_back,R.id.rl_title, R.id.ci_approved_pic, R.id.tv_part_count_title, R.id.tv_back_instruct})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_title:
                finish();
                break;
            case R.id.ci_approved_pic:
                startActivityForResult(new Intent(MeetingSponsorActivity.this, SelDepartmentActivity_meet_zb.class), 0);
                break;
            case R.id.tv_back_instruct:
                if (TextUtils.isEmpty(etTitle.getText().toString())) {
                    ToastHelper.showToast(mContext, "请输入会议标题");
                    return;
                }
                if (TextUtils.isEmpty(meetingTime)) {

                    ToastHelper.showToast(mContext, "请选择会议开始时间");
                    return;
                }
                if (TextUtils.isEmpty(meetingTimeEnd)) {
                    ToastHelper.showToast(mContext, "请选择会议结束时间");
                    return;
                }
                if (TextUtils.isEmpty(meetingContacts)) {
                    ToastHelper.showToast(mContext, "请选择会议联系人");
                    return;
                }

                if (TextUtils.isEmpty(numberOfPeople)) {
                    ToastHelper.showToast(mContext, "请添加参会人");
                    return;
                }
                if (TextUtils.isEmpty(roomid)) {
                    ToastHelper.showToast(mContext, "请选择会议室");
                    return;
                }
                if(TextUtils.isEmpty( etContent.getText().toString())){
                    ToastHelper.showToast(mContext, "请填写会议内容");
                    return;
                }
                loadDialog.show();
                RequestParams params = new RequestParams();
                params.put("signType", signType);
                params.put("meetingTheme", etTitle.getText().toString());
                params.put("meetingTimeBegin", meetingTime);
                params.put("meetingTimeEnd", meetingTimeEnd);
                params.put("meetingContent", etContent.getText().toString());
                params.put("numberOfPeople", numberOfPeople);
                params.put("meetingRoomId", roomid);
                params.put("participantId", participantId);
                params.put("createBy", SPUtils.get(mContext, "userId", "").toString());
                params.put("meetingContacts", meetingContacts);
                params.put("meetingContactsPhone", meetingContactsPhone);
                params.put("meetingLocation", meetingRoomLocation+meetingRoomName);
                HttpRequest.postMeetLauncherApi(params, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        loadDialog.dismiss();
                        try {
                            JSONObject result = new JSONObject(responseObj.toString());
                            ToastHelper.showToast(mContext, result.getString("msg"));
                            if (result.getString("code").equals("0")) {
                                EventBus.getDefault().post(new MeetLaunchFragment());
                                EventBus.getDefault().post(new MeetReceiverFragment());
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(OkHttpException failuer) {
                        loadDialog.dismiss();
                        Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    private void getData(String galleryful) {
        RequestParams params = new RequestParams();
        params.put("galleryful", galleryful);
        HttpRequest.postMeetRoomeApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                sw_refres.setRefreshing(false);
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<MeetingRoom> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<MeetingRoom>>() {
                            }.getType());
                    for (MeetingRoom room : memberList) {
                        room.setIsCheck("0");
                    }
                    meetRoomAdapter = new MeetRoomAdapter(mContext, memberList);
                    lv_meet.setAdapter(meetRoomAdapter);
                    lv_meet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if (memberList.get(i).getMeetingContacts()!=null&&memberList.get(i).getMeetingContacts()!=""){
                                // 拨打电话
                                callPhone(memberList.get(i).getMeetingContactsPhone());
                            }else {
                                roomid = memberList.get(i).getId();
                                meetingRoomLocation = memberList.get(i).getMeetingRoomLocation();
                                meetingRoomName = memberList.get(i).getMeetingRoomName();
                                for (int j = 0; j < memberList.size(); j++) {
                                    if (i == j) {
                                        memberList.get(j).setIsCheck("1");
                                    } else
                                        memberList.get(j).setIsCheck("0");
                                }
                                meetRoomAdapter.notifyDataSetChanged();
                            }

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void selectTime() {
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //设置时间
                if(!meetingTimeEnd.equals(""))
                {
                    if(TimeUtils.differentDaysByMillisecond(getTime(date),meetingTimeEnd)>0){
                        ToastHelper.showToast(mContext, "请选择不小于开始时间的结束时间");
                        return;
                    }
                }
//                判断选择开始时间是否大于当前时间
                if(TimeUtils.timeCompare(TimeUtils.getNowTime1(),getTime(date))==1){
                    ToastHelper.showToast(mContext, "请选择当前时间之后");
                }else {
                    tvPartTime.setText(getTime(date));
                    meetingTime = tvPartTime.getText().toString();
                }

            }
        }).setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    private void selectTime2() {
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if(!meetingTime.equals(""))
                {
                    if(TimeUtils.differentDaysByMillisecond(meetingTime,getTime(date))<0){
                        ToastHelper.showToast(mContext, "请选择不小于开始时间的结束时间");
                        return;
                    }
                }
                //设置时间
                tvPartTime2.setText(getTime(date));
                meetingTimeEnd = tvPartTime2.getText().toString();

            }
        }).setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && requestCode == 0&&data!=null) {

            List<People> mList1 = (List<People>) data.getSerializableExtra("mList");
            mList.addAll(mList1);
            mList = TimeUtils.removeDuplicateWithOrder(mList);
            tvPartCount.setText(mList.size() + " 人");

            numberOfPeople = mList.size() + "";
            if (mList.size() > 0) {

                // 替换了
                cd = mList.size() + "";
                getData(cd);
                String name = "";
                participantId = "";
                for (People params1 : mList) {
                    participantId += params1.getUserId() + ",";
                    name += params1.getUserName() + ",";
                }
                if (!participantId.equals(""))
                    participantId = participantId.substring(0, participantId.length() - 1);
                adapter = new MeetGridViewAdapter(MeetingSponsorActivity.this, mList);
                gvTest.setAdapter(adapter);
                List<People> finalMList = mList;
                gvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        myDialog.setGone().setTitle("提示").setMsg("确定删除么").setNegativeButton("取消", null).setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finalMList.remove(position);
                                tvPartCount.setText(finalMList.size() + "");
                                adapter.notifyDataSetChanged();
                            }
                        }).show();
                    }
                });
            }
        } else if (requestCode == 110&&data!=null) {
            List<People> mList = (List<People>) data.getSerializableExtra("mList");
            meetingContacts = mList.get(0).getUserName();
            tvLianxPerson.setText(meetingContacts);
            meetingContactsPhone = mList.get(0).getLoginName();
            etTitle9.setText(meetingContactsPhone);
        }
    }

    @OnClick(R.id.tv_part_time2)
    public void onViewClicked2() {
        selectTime2();
    }

    @OnClick(R.id.rl_meet_lianx)
    public void onViewClicked3() {
        startActivityForResult(new Intent(MeetingSponsorActivity.this, SelDepartmentActivity_meet_zb_single.class), 110);
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
