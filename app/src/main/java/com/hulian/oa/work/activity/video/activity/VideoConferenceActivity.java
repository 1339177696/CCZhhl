package com.hulian.oa.work.activity.video.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.DemoCache;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.bean.People;
import com.hulian.oa.listener.ListSizeListener;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.news.adapter.MyViewPageAdapter;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.work.activity.ScreenReportActivity;
import com.hulian.oa.work.activity.WorkReportActivity;
import com.hulian.oa.work.activity.meeting.SelDepartmentActivity_meet_video;
import com.hulian.oa.work.activity.video.fragment.MeetingListInMyFragment;
import com.hulian.oa.work.activity.video.fragment.MeetingListOngoingFragment;
import com.hulian.oa.work.fragment.ReadReportFragment;
import com.hulian.oa.work.fragment.WriteReportFragment;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 陈泽宇 on 2020/4/20
 * Describe:视频会议
 */
public class VideoConferenceActivity extends BaseActivity {


    @BindView(R.id.my_tablayout)
    TabLayout myTablayout;
    @BindView(R.id.my_viewpager)
    ViewPager myViewpager;
    private Context mContext;

    private ArrayList<String> titleDatas = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private ArrayList<Integer> imgList = new ArrayList<>();
    private List<TextView> numberList = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.activity_work_report);
        ButterKnife.bind(this);
        mContext = this;
        titleDatas.add("我参与会议");
        titleDatas.add("进行中会议");
        fragmentList.add(new MeetingListInMyFragment());
        fragmentList.add(new MeetingListOngoingFragment());

        imgList.add(R.drawable.vc_pic_1);
        imgList.add(R.drawable.vc_pic_2);
        init();
    }

    private void init() {
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        myTablayout.setSelectedTabIndicator(0);
        myViewpager.setAdapter(myViewPageAdapter);
        myTablayout.setupWithViewPager(myViewpager);
        //替换tab中原有的样式
        for (int i = 0; i < titleDatas.size(); i++) {
            myTablayout.getTabAt(i).setCustomView(R.layout.item_sphy_tab);
            TextView title = myTablayout.getTabAt(i).getCustomView().findViewById(R.id.tv_title);
            ImageView imageView = myTablayout.getTabAt(i).getCustomView().findViewById(R.id.iv_pic);
            TextView number = myTablayout.getTabAt(i).getCustomView().findViewById(R.id.number);
            //存储后方便赋值
            numberList.add(number);
            title.setText(titleDatas.get(i));
            //标题左边选中和未选中的图片效果
            imageView.setBackground(ContextCompat.getDrawable(this, imgList.get(i)));

        }


//        myTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                tab.getCustomView().findViewById(R.id.tv_title).setSelected(true);
//                myViewpager.setCurrentItem(tab.getPosition());
//                switch (tab.getPosition()) {

//                    case 0:
//                        tv_apply.setVisibility(View.INVISIBLE);
//                        break;
////
//                    case 1:
//                        tv_apply.setVisibility(View.VISIBLE);
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                tab.getCustomView().findViewById(R.id.tv_title).setSelected(false);
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

    }

    @OnClick({R.id.tv_launch, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_launch://发起视频会议
                startActivityForResult(new Intent(VideoConferenceActivity.this, SelDepartmentActivity_meet_video.class), 0);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && requestCode == 0 && data != null) {
            List<People> peopleList = (List<People>) data.getSerializableExtra("mList");
            ArrayList<String> accounts = new ArrayList<>();
            String userName = SPUtils.get(mContext, "nickname", "").toString();
            String roomName = userName + TimeUtils.getNowTime();
            for (People people : peopleList) {
                accounts.add(people.getLoginName());
            }

            createRoom(roomName, accounts);
        }
    }

    private void createRoom(String roomName, ArrayList<String> accounts) {

        AVChatManager.getInstance().createRoom(roomName, null, new AVChatCallback<AVChatChannelInfo>() {
            @Override
            public void onSuccess(AVChatChannelInfo avChatChannelInfo) {
                LogUtil.ui("create room " + roomName + " success !");
                onCreateRoomSuccess(roomName, accounts);
                //调创建接口 传入创建人 参与人
                createVideoRoom(roomName,getParticipantId(accounts));

                TeamAVChatProfile.sharedInstance().setTeamAVChatting(true);
                AVChatKit.outgoingTeamCall(VideoConferenceActivity.this, false, "", roomName, accounts, roomName);
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
        String teamNick = TeamHelper.getDisplayNameWithoutMe(teamID, DemoCache.getAccount());
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
            command.setApnsText(teamNick + getString(R.string.t_avchat_push_content));

            command.setSendToOnlineUserOnly(false);
            NIMClient.getService(MsgService.class).sendCustomNotification(command);
        }
    }


    public void setListSize(int size, int position) {
        numberList.get(position).setText(size + "");
    }


    private void createVideoRoom(String roomName,String participant){

        RequestParams params = new RequestParams();
        params.put("initiator",SPUtils.get(mContext, "username", "").toString());
        params.put("participant",participant);
        params.put("meetinglName",roomName);
        HttpRequest.createVideoRoom(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.e("创建房间",responseObj.toString());
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }

    private String getParticipantId(List<String> mList) {
        String participantId = "";
        for (String params1 : mList) {
            participantId += params1 + ";";
        }
        if (!participantId.equals(""))
            participantId = participantId.substring(0, participantId.length() - 1);
        return participantId;
    }

}
