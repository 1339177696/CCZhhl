package com.hulian.oa.work;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.me.MeActivity;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.work.file.admin.activity.task.l_fragment.CompletedTaskFragment;
import com.hulian.oa.work.fragment.WorkFragemt_9;
import com.hulian.oa.work.fragment.WorkFragemt_list;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

public class WorkFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
//    @BindView(R.id.iv_mine)
//    ImageView ivMine;
//    @BindView(R.id.iv_news)
//    ImageView ivNews;
    @BindView(R.id.fg_content)
    LinearLayout fgContent;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    WorkFragemt_9 workFragemt_9;
    WorkFragemt_list workFragemt_list;
    @BindView(R.id.tv_type)
    TextView tv_type;
    public WorkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Main1_home.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkFragment newInstance(String requestJson) {
        WorkFragment fragment = new WorkFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_work, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        tv_type.setText(SPUtils.get(getActivity(), "nickname", "").toString().substring(SPUtils.get(getActivity(), "nickname", "").toString().length()-2,SPUtils.get(getActivity(), "nickname", "").toString().length()));
        workFragemt_9=new WorkFragemt_9();
        workFragemt_list=new WorkFragemt_list();
        init9fragment();
        return view;
    }

    private void init9fragment() {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fg_content,workFragemt_9);
        fragmentTransaction.commit();
    }
    private void initListfragment() {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fg_content,workFragemt_list);
        fragmentTransaction.commit();
    }

    public void onEventMainThread(WorkFragemt_9 ev) {
        initListfragment();
    }
    public void onEventMainThread(WorkFragemt_list ev) {
        init9fragment();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.tv_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_type:
                startActivity(new Intent(getActivity(), MeActivity.class));
                break;
            default:
                break;
        }
    }


//
//    @OnClick(R.id.iv_news)
//    public void onViewClicked() {
//        startActivity(new Intent(getActivity(), NewsActivityInfo.class));
//    }


}
