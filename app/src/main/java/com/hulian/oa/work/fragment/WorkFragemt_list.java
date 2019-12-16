package com.hulian.oa.work.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hulian.oa.R;
import com.hulian.oa.work.file.admin.activity.SecondDocumentActivity;
import com.hulian.oa.work.file.admin.activity.SecondExpenseActivity;
import com.hulian.oa.work.file.admin.activity.SecondInstructActivity;
import com.hulian.oa.work.file.admin.activity.SecondLeaveActivity;
import com.hulian.oa.work.file.admin.activity.SecondMailActivity;
import com.hulian.oa.work.file.admin.activity.SecondMeetingActivity;
import com.hulian.oa.work.file.admin.activity.SecondNoticeActivity;
import com.hulian.oa.work.file.admin.activity.SecondTaskCoopActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class WorkFragemt_list extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.rela_gongwen)
    RelativeLayout relaGongwen;
    @BindView(R.id.rela_youjian)
    RelativeLayout relaYoujian;
    @BindView(R.id.rela_huiyi)
    RelativeLayout relaHuiyi;
    @BindView(R.id.rela_renwu)
    RelativeLayout relaRenwu;
    @BindView(R.id.rela_qingjia)
    RelativeLayout relaQingjia;
    @BindView(R.id.rela_baoxiao)
    RelativeLayout relaBaoxiao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_fragment_list_qgl, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    原来的
//    @OnClick({R.id.bt_mail, R.id.bt_leave, R.id.bt_document, R.id.bt_coop, R.id.bt_notice, R.id.bt_meeting, R.id.bt_instruct, R.id.bt_expense, R.id.bt_list, R.id.bt_9})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
////            2
//            case R.id.bt_mail:
//                startActivity(new Intent(getActivity(), SecondMailActivity.class));
//                break;
//            case R.id.bt_leave:
//                //跳转到请假人申请列表
//                startActivity(new Intent(getActivity(), SecondLeaveActivity.class));
//                break;
////                1
//            case R.id.bt_document:
//                startActivity(new Intent(getActivity(), SecondDocumentActivity.class));
//                break;
////                4
//            case R.id.bt_coop:
//                startActivity(new Intent(getActivity(), SecondTaskCoopActivity.class));
//                break;
//            case R.id.bt_notice:
//                startActivity(new Intent(getActivity(), SecondNoticeActivity.class));
//                break;
////                3
//            case R.id.bt_meeting:
//
//                startActivity(new Intent(getActivity(), SecondMeetingActivity.class));
//                break;
//            case R.id.bt_instruct:
//                startActivity(new Intent(getActivity(), SecondInstructActivity.class));
//                break;
//            case R.id.bt_expense:
//                //跳转报销人申请列表
//                startActivity(new Intent(getActivity(), SecondExpenseActivity.class));
//                break;
//            case R.id.bt_list:
//                break;
//            case R.id.bt_9:
//                EventBus.getDefault().post(new WorkFragemt_list());
//                break;
//        }
//    }

    @OnClick({R.id.rela_gongwen, R.id.rela_youjian, R.id.rela_huiyi, R.id.rela_renwu, R.id.rela_qingjia, R.id.rela_baoxiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rela_gongwen:
                startActivity(new Intent(getActivity(), SecondDocumentActivity.class));
                break;
            case R.id.rela_youjian:
                startActivity(new Intent(getActivity(), SecondMailActivity.class));
                break;
            case R.id.rela_huiyi:
                startActivity(new Intent(getActivity(), SecondMeetingActivity.class));
                break;
            case R.id.rela_renwu:
                startActivity(new Intent(getActivity(), SecondTaskCoopActivity.class));
                break;
            case R.id.rela_qingjia:
                startActivity(new Intent(getActivity(), SecondLeaveActivity.class));
                break;
            case R.id.rela_baoxiao:
                startActivity(new Intent(getActivity(), SecondExpenseActivity.class));
                break;
        }
    }
}
