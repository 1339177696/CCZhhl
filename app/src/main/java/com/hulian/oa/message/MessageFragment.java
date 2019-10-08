package com.hulian.oa.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.message.activity.MeaaageAdressActivity;
import com.hulian.oa.message.activity.MessageMeActivity;
import com.hulian.oa.message.activity.MessageRemindActivity;
import com.hulian.oa.work.file.admin.activity.SecondMailActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MessageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_gomine)
    ImageView ivGomine;
    @BindView(R.id.iv_message_info)
    ImageView ivMessageInfo;
    Unbinder unbinder;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Main1_home.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String requestJson) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
//        args.putString("requestJson", requestJson);
//        args.putString("gid", gid);
//        args.putString("idno", idno);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            gid = getArguments().getString("gid");
//            idno=getArguments().getString("idno");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_address, R.id.iv_gomine, R.id.iv_message_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                startActivity(new Intent(getActivity(), MeaaageAdressActivity.class));
                break;
            case R.id.iv_gomine:
                startActivity(new Intent(getActivity(), MessageMeActivity.class));
                break;
            case R.id.iv_message_info:
                startActivity(new Intent(getActivity(), MessageRemindActivity.class));
                break;
            default:
                break;
        }
    }
}
