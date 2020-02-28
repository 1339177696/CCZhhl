package com.hulian.oa.work.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hulian.oa.BuildConfig;
import com.hulian.oa.DemoCache;
import com.hulian.oa.R;
import com.hulian.oa.bean.People;
import com.hulian.oa.qglactivity.MessagenotificationActivity;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.work.file.admin.activity.SecondDocumentActivity;
import com.hulian.oa.work.file.admin.activity.SecondExpenseActivity;
import com.hulian.oa.work.file.admin.activity.SecondInstructActivity;
import com.hulian.oa.work.file.admin.activity.SecondLeaveActivity;
import com.hulian.oa.work.file.admin.activity.SecondMailActivity;
import com.hulian.oa.work.file.admin.activity.SecondMeetingActivity;
import com.hulian.oa.work.file.admin.activity.SecondNoticeActivity;
import com.hulian.oa.work.file.admin.activity.SecondTaskCoopActivity;
import com.hulian.oa.work.file.admin.activity.WorkSpaceActivity;
import com.hulian.oa.work.file.admin.activity.meeting.SelDepartmentActivity_meet_zb;
import com.luck.picture.lib.entity.LocalMedia;
import com.netease.nim.avchatkit.AVChatKit;
import com.netease.nim.avchatkit.TeamAVChatProfile;
import com.netease.nim.avchatkit.common.util.TimeUtil;
import com.netease.nim.uikit.business.team.helper.TeamHelper;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.model.AVChatChannelInfo;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.CustomNotificationConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class WorkFragemt_9 extends Fragment {
    @BindView(R.id.bt_mail)
    ImageView btMail;
    @BindView(R.id.bt_leave)
    ImageView btLeave;
    @BindView(R.id.bt_document)
    ImageView btDocument;
    @BindView(R.id.bt_coop)
    ImageView btCoop;
    @BindView(R.id.bt_notice)
    ImageView btNotice;
    @BindView(R.id.bt_meeting)
    ImageView btMeeting;
    @BindView(R.id.bt_instruct)
    ImageView btInstruct;
    @BindView(R.id.bt_expense)
    ImageView btExpense;
    int[] images = {R.mipmap.demo, R.mipmap.demo1, R.mipmap.demo2, R.mipmap.demo3, R.mipmap.demo4};
    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
//    @BindView(R.id.bt_list)
//    Button btList;
//    @BindView(R.id.bt_9)
//    Button bt9;

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        if (BuildConfig.IsPad) {
            if (SPUtils.get(getActivity(), "isLead", "").equals("0")) {
                view = inflater.inflate(R.layout.fragment_work_fragemt_9_pad_lead, container, false);
            } else {
                view = inflater.inflate(R.layout.fragment_work_fragemt_9_pad, container, false);
            }

        } else {
            if (SPUtils.get(getActivity(), "isLead", "").equals("0")) {
                view = inflater.inflate(R.layout.fragment_work_fragemt_9_lead, container, false);
            } else {
                view = inflater.inflate(R.layout.fragment_work_fragemt_9, container, false);
            }
        }
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //    , R.id.bt_list, R.id.bt_9
    @OnClick({R.id.bt_mail, R.id.bt_leave, R.id.bt_document, R.id.bt_coop, R.id.bt_notice, R.id.bt_meeting, R.id.bt_instruct, R.id.bt_expense, R.id.bt_work_space})
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
                startActivity(new Intent(getActivity(), SecondMailActivity.class));
                break;
            case R.id.bt_leave:
                //跳转到请假人申请列表
                startActivity(new Intent(getActivity(), SecondLeaveActivity.class));
                break;
            case R.id.bt_document:
                startActivity(new Intent(getActivity(), SecondDocumentActivity.class));
                break;
            case R.id.bt_coop:
                startActivity(new Intent(getActivity(), SecondTaskCoopActivity.class));
                break;
            case R.id.bt_notice:
                startActivity(new Intent(getActivity(), SecondNoticeActivity.class));
                break;
            case R.id.bt_meeting:
                startActivity(new Intent(getActivity(), SecondMeetingActivity.class));
                break;
            case R.id.bt_instruct:
                startActivity(new Intent(getActivity(), SecondInstructActivity.class));
                break;
            case R.id.bt_work_space:
                //20191129
//                startActivity(new Intent(getActivity(), WorkSpaceActivity.class));
                startActivity(new Intent(getActivity(), MessagenotificationActivity.class));

                break;
            case R.id.bt_expense:
//                qgl修改

//                    startActivity(new Intent(getActivity(), SecondExpenseActivity.class));
//                ToastHelper.showToast(getActivity(), "功能暂未开放");
                startActivityForResult(new Intent(getActivity(), SelDepartmentActivity_meet_zb.class), 0);
                break;
//            case R.id.bt_list:
//                EventBus.getDefault().post(new WorkFragemt_9());
//                break;
//            case R.id.bt_9:
//                break;
        }
    }

    //初始化图片信息
    private void init(int[] images) {
        for (int i = 0; i < images.length; i++) {
            LocalMedia localMedia = new LocalMedia();
            localMedia.setPath(images[i] + "");
            selectList.add(localMedia);
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
                AVChatKit.outgoingTeamCall(getActivity(), false, "", roomName, accounts, roomName);
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
            command.setApnsText(teamNick + getActivity().getString(R.string.t_avchat_push_content));

            command.setSendToOnlineUserOnly(false);
            NIMClient.getService(MsgService.class).sendCustomNotification(command);
        }
    }


}
