package com.hulian.oa.work.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.hulian.oa.R;
import com.hulian.oa.bean.People;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.work.activity.SecondInstructActivity;
import com.hulian.oa.work.activity.SecondLeaveActivity;
import com.hulian.oa.work.activity.SecondMailActivity;
import com.hulian.oa.work.activity.SecondMeetingActivity;
import com.hulian.oa.work.activity.SecondNoticeActivity;
import com.hulian.oa.work.activity.SecondTaskCoopActivity;
import com.hulian.oa.work.activity.WorkReportActivity;
import com.hulian.oa.work.activity.attendance.ClockActivity;
import com.hulian.oa.work.activity.attendancestatistics.activity.AnaestheticsActivity;
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
    @BindView(R.id.bt_coop)
    ImageView btCoop;
    @BindView(R.id.bt_notice)
    ImageView btNotice;
    @BindView(R.id.bt_meeting)
    ImageView btMeeting;
    @BindView(R.id.bt_instruct)
    ImageView btInstruct;
    @BindView(R.id.bt_Work_notfit)
    ImageView btWorkNotfit;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        if (SPUtils.get(getActivity(), "isLead", "").equals("0")) {
            view = inflater.inflate(R.layout.fragment_work_fragment_xin, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_work_fragment_xin, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_shipin, R.id.bt_yuyin, R.id.bt_mail, R.id.bt_coop, R.id.bt_meeting, R.id.bt_notice, R.id.bt_time, R.id.bt_instruct, R.id.bt_leave, R.id.bt_baoxiao, R.id.bt_Work_report, R.id.bt_Work_statistical, R.id.bt_Work_account,R.id.bt_Work_notfit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //视频会议
            case R.id.bt_shipin:
//                startActivity(new Intent(getActivity(), VideoConferenceActivity.class));
                ToastHelper.showToast(getActivity(), "功能暂未开放");
                break;
            //语音会议
            case R.id.bt_yuyin:
                ToastHelper.showToast(getActivity(), "功能暂未开放");
                break;
            //邮件收发
            case R.id.bt_mail:
                startActivity(new Intent(getActivity(), SecondMailActivity.class));
                break;
//                任务协同
            case R.id.bt_coop:
                startActivity(new Intent(getActivity(), SecondTaskCoopActivity.class));
                break;
            //会议安排
            case R.id.bt_meeting:
                startActivity(new Intent(getActivity(), SecondMeetingActivity.class));
                break;
            // 通告通知
            case R.id.bt_notice:
                startActivity(new Intent(getActivity(), SecondNoticeActivity.class));
                break;
            //考勤打卡
            case R.id.bt_time:
                startActivity(new Intent(getActivity(), ClockActivity.class));
                // 此处修改部分手机闪屏上一个界面问题
                getActivity().overridePendingTransition(0, 0);
                break;
            //指令安排
            case R.id.bt_instruct:
                startActivity(new Intent(getActivity(), SecondInstructActivity.class));
                break;
            case R.id.bt_leave:
                //跳转到请假人申请列表
                startActivity(new Intent(getActivity(), SecondLeaveActivity.class));
                break;
            //报销
            case R.id.bt_baoxiao:
//                startActivity(new Intent(getActivity(),SecondExpenseActivity.class));
                ToastHelper.showToast(getActivity(), "功能暂未开放");
                break;
            case R.id.bt_expense:
                ToastHelper.showToast(getActivity(), "功能暂未开放");
                break;
            //工作汇报
            case R.id.bt_Work_report:
                startActivity(new Intent(getActivity(), WorkReportActivity.class));
                break;
            //考勤统计
            case R.id.bt_Work_statistical:
                startActivity(new Intent(getActivity(), AnaestheticsActivity.class));
                break;
            case R.id.bt_Work_account:
//                startActivity(new Intent(getActivity(), ExpenseStatisticalActivity.class));
                ToastHelper.showToast(getActivity(), "功能暂未开放");
                break;
            case R.id.bt_Work_notfit:
//                startActivity(new Intent(getActivity(), NoticActivity.class));
                ToastHelper.showToast(getActivity(), "功能暂未开放");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && requestCode == 0 && data != null) {
            List<People> peopleList = (List<People>) data.getSerializableExtra("mList");
            ArrayList<String> accounts = new ArrayList<>();
            String userName = SPUtils.get(getActivity(), "username", "").toString();
            String roomName = userName + TimeUtils.getNowTime();
            for (People people : peopleList) {
                accounts.add(people.getLoginName());
            }
        }
    }





}
