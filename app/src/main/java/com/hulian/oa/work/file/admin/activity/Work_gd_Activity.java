package com.hulian.oa.work.file.admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.DemoCache;
import com.hulian.oa.R;
import com.hulian.oa.bean.People;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.ToastHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hulian.oa.work.file.admin.activity.meeting.SelDepartmentActivity_meet_zb;
import com.netease.nim.avchatkit.AVChatKit;
import com.netease.nim.avchatkit.TeamAVChatProfile;
import com.netease.nim.uikit.business.team.helper.TeamHelper;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.model.AVChatChannelInfo;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.CustomNotificationConfig;

import java.util.ArrayList;
import java.util.List;

public class Work_gd_Activity extends BaseActivity {
    @BindView(R.id.bt_mail)
    ImageView btMail;
    @BindView(R.id.bt_qingjia)
    ImageView bt_qingjia;
    @BindView(R.id.bt_coop)
    ImageView btCoop;
    @BindView(R.id.bt_notice)
    ImageView btNotice;

    @BindView(R.id.bt_meeting)

    ImageView btMeeting;
    @BindView(R.id.bt_instruct)
    ImageView btInstruct;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_gd_activity);
        ButterKnife.bind(this);
    }



    @OnClick({R.id.bt_mail,R.id.bt_qingjia, R.id.bt_coop, R.id.bt_notice, R.id.bt_meeting, R.id.bt_instruct,R.id.bt_k1,R.id.bt_k2,R.id.bt_k3,R.id.bt_baoxiao,R.id.bt_gongwen,R.id.bt_shipin,R.id.bt_yuyin,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_mail:
       /*         if(BuildConfig.IsPad)//业务驾驶舱
                {
                    //tartActivity(new Intent(getActivity(), SecondMailActivity.class));
                    init(images);
                    Intent intent = new Intent(getActivity(), PicturePreviewActivity2.class);
                    intent.putExtra(PictureConfig.EXTRA_PREVIEW_SELECT_LIST, (Serializable) selectList);
                    intent.putExtra(PictureConfig.EXTRA_POSITION, 0);
                    getActivity().startActivity(intent);
                }
                else*/
                startActivity(new Intent(Work_gd_Activity.this, SecondMailActivity.class));
                break;
            case R.id.bt_qingjia:
                //跳转到请假人申请列表
                startActivity(new Intent(Work_gd_Activity.this, SecondLeaveActivity.class));
                break;
            case R.id.bt_gongwen:
                startActivity(new Intent(Work_gd_Activity.this, SecondDocumentActivity.class));
                break;
            case R.id.bt_coop:
                startActivity(new Intent(Work_gd_Activity.this, SecondTaskCoopActivity.class));
                break;
            case R.id.bt_notice:
                startActivity(new Intent(Work_gd_Activity.this, SecondNoticeActivity.class));
                break;
            case R.id.bt_meeting:
                startActivity(new Intent(Work_gd_Activity.this, SecondMeetingActivity.class));
                break;
            case R.id.bt_instruct:
                startActivity(new Intent(Work_gd_Activity.this, SecondInstructActivity.class));
                break;
            case R.id.bt_baoxiao:
                ToastHelper.showToast(Work_gd_Activity.this, "功能暂未开放");
                break;
            case R.id.bt_k3:
                ToastHelper.showToast(Work_gd_Activity.this, "功能暂未开放");
                break;
            case R.id.bt_k1:
                startActivity(new Intent(Work_gd_Activity.this, SecondDocumentActivity.class));
                break;
            case R.id.bt_k2:
            //跳转到请假人申请列表
            startActivity(new Intent(Work_gd_Activity.this, SecondLeaveActivity.class));
            break;
            case R.id.bt_shipin:
                startActivityForResult(new Intent(Work_gd_Activity.this, SelDepartmentActivity_meet_zb.class), 0);
                break;
                case R.id.bt_yuyin:
                ToastHelper.showToast(Work_gd_Activity.this, "功能暂未开放");
                break;


//            case R.id.bt_work_space:
//                //20191129
////                startActivity(new Intent(getActivity(), WorkSpaceActivity.class));
//                //startActivity(new Intent(getActivity(), MessagenotificationActivity.class));
//                ToastHelper.showToast(getActivity(), "功能暂未开放");
//
//                break;
//            case R.id.bt_expense:
////                qgl修改
//
////                    startActivity(new Intent(getActivity(), SecondExpenseActivity.class));
//                ToastHelper.showToast(getActivity(), "功能暂未开放");
//                break;
//            case R.id.bt_work_attendance:
//                ToastHelper.showToast(getActivity(), "功能暂未开放");
//                break;
//            case R.id.bt_list:
//                EventBus.getDefault().post(new WorkFragemt_9());
//                break;
//            case R.id.bt_9:
//                break;
            case R.id.iv_back:
                finish();
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && requestCode == 0&&data!=null) {
            List<People> peopleList = (List<People>) data.getSerializableExtra("mList");
            ArrayList<String> accounts = new ArrayList<>();
            String roomName = TimeUtils.getNowTime();
            for (People people : peopleList) {
                accounts.add(people.getLoginName());
            }

            creatRoom(roomName, accounts);
        }
    }

    private void creatRoom(String roomName, ArrayList<String> accounts) {

        AVChatManager.getInstance().createRoom(roomName, null, new AVChatCallback<AVChatChannelInfo>() {
            @Override
            public void onSuccess(AVChatChannelInfo avChatChannelInfo) {
                LogUtil.ui("create room " + roomName + " success !");
                onCreateRoomSuccess(roomName, accounts);


                TeamAVChatProfile.sharedInstance().setTeamAVChatting(true);
                AVChatKit.outgoingTeamCall(Work_gd_Activity.this, false, "", roomName, accounts, roomName);
            }

            @Override
            public void onFailed(int code) {
            }

            @Override
            public void onException(Throwable exception) {
            }
        });
    }

    private void onCreateRoomSuccess(String roomName, List<String> accounts) {
        String teamID = roomName;
        // 在群里发送tip消息
//        IMMessage message = MessageBuilder.createTipMessage(teamID, SessionTypeEnum.Team);
//        CustomMessageConfig tipConfig = new CustomMessageConfig();
//        tipConfig.enableHistory = false;
//        tipConfig.enableRoaming = false;
//        tipConfig.enablePush = false;
        String teamNick = TeamHelper.getDisplayNameWithoutMe(teamID, DemoCache.getAccount());
//        message.setContent(teamNick + getActivity().getString(R.string.t_avchat_start));
//        message.setConfig(tipConfig);
//        sendMessage(message);
        // 对各个成员发送点对点自定义通知
        String teamName = TeamHelper.getTeamName(teamID);
        String content = TeamAVChatProfile.sharedInstance().buildContent(roomName, teamID, accounts, teamName);
        CustomNotificationConfig config = new CustomNotificationConfig();
        config.enablePush = true;
        config.enablePushNick = false;
        config.enableUnreadCount = true;

        for (String account : accounts) {
            CustomNotification command = new CustomNotification();
            command.setSessionId(account);
            command.setSessionType(SessionTypeEnum.P2P);
            command.setConfig(config);
            command.setContent(content);
            command.setApnsText(teamNick + Work_gd_Activity.this.getString(R.string.t_avchat_push_content));

            command.setSendToOnlineUserOnly(false);
            NIMClient.getService(MsgService.class).sendCustomNotification(command);
        }
    }

}
