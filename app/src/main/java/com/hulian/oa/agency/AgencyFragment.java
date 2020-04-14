package com.hulian.oa.agency;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.agency.fragment.HascomFragment;
import com.hulian.oa.agency.fragment.UpcomFragment;
import com.hulian.oa.bean.AgencyCount;
import com.hulian.oa.bean.AgencyCountFinish;
import com.hulian.oa.bean.Fab;
import com.hulian.oa.bean.Fab2;
import com.hulian.oa.bean.StringBean1;
import com.hulian.oa.bean.StringBean2;
import com.hulian.oa.work.activity.expense.ExpenseExamineActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

public class AgencyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    Unbinder unbinder;
    @BindView(R.id.tv_mengban)
    TextView tvMengban;
    @BindView(R.id.zx_qgl_img1)
    ImageView zxQglImg1;
    @BindView(R.id.lr_qgl_btn1)
    LinearLayout lrQglBtn1;
    @BindView(R.id.zx_qgl_img2)
    ImageView zxQglImg2;
    @BindView(R.id.lr_qgl_btn2)
    LinearLayout lrQglBtn2;
    @BindView(R.id.zx_qgl_txt1)
    TextView zxQglTxt1;
    @BindView(R.id.daiban_number)
    TextView daibanNumber;
    @BindView(R.id.zx_qgl_txt2)
    TextView zxQglTxt2;
    @BindView(R.id.yiban_number)
    TextView yibanNumber;
    private int pos = 0;

    private UpcomFragment l_upcomFragment;
    private HascomFragment l_hascomFragment;
    private FragmentManager fManager;

    public AgencyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Main1_home.
     */
    // TODO: Rename and change types and number of parameters
    public static AgencyFragment newInstance(String requestJson) {
        AgencyFragment fragment = new AgencyFragment();
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
        View view = inflater.inflate(R.layout.fra_agency, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        fManager = getFragmentManager();
        lrQglBtn1.performClick();//模拟一次点击，既进去后选择第一项
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    //接受点击事件,发送给fragment
    public void onEventMainThread(String event) {
        if (pos == 0) {
            Log.e("已办------->", event);
            StringBean1 stringBean1 = new StringBean1();
            stringBean1.setDaiban(event);
            EventBus.getDefault().post(stringBean1);
        } else {
            Log.e("待办------->", event);
            StringBean2 stringBean2 = new StringBean2();
            stringBean2.setDaiban(event);
            EventBus.getDefault().post(stringBean2);
        }
    }

    public void onEventMainThread(Fab event) {
        if (event.getTag().equals("0")) {
            tvMengban.setVisibility(View.GONE);
        } else {
            tvMengban.setVisibility(View.VISIBLE);
        }
    }


    public void onEventMainThread(AgencyCount event) {
        if (!"".equals(event.getAgencyCount())) {
            daibanNumber.setText(event.getAgencyCount());
        }
    }

    public void onEventMainThread(AgencyCountFinish event) {
        if (!"".equals(event.getAgencyCountFinish())) {
            yibanNumber.setText(event.getAgencyCountFinish());
        }
    }
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), ExpenseExamineActivity.class);
        intent.putExtra("type", "agency");
        startActivity(intent);
    }


    @OnClick(R.id.tv_mengban)
    public void onViewClicked2() {
        Fab2 fab2 = new Fab2();
        fab2.setTag("0");
        EventBus.getDefault().post(fab2);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (l_upcomFragment != null) fragmentTransaction.hide(l_upcomFragment);
        if (l_hascomFragment != null) fragmentTransaction.hide(l_hascomFragment);

    }

    @OnClick({R.id.lr_qgl_btn1, R.id.lr_qgl_btn2})
    public void onViewClicked(View view) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (view.getId()) {
            case R.id.lr_qgl_btn1:
                pos = 0;
                zxQglTxt1.setTextColor(Color.parseColor("#FFFFFF"));
                zxQglImg1.setImageResource(R.mipmap.done);
                zxQglTxt2.setTextColor(Color.parseColor("#ccccd5"));
                zxQglImg2.setImageResource(R.mipmap.done_default);
                if (l_upcomFragment == null) {
                    l_upcomFragment = new UpcomFragment();
                    fTransaction.add(R.id.qgl_fragment_daiban, l_upcomFragment);
                } else {
                    fTransaction.show(l_upcomFragment);
                }
                break;
            case R.id.lr_qgl_btn2:
                pos = 1;
                zxQglTxt1.setTextColor(Color.parseColor("#ccccd5"));
                zxQglImg1.setImageResource(R.mipmap.done_default);
                zxQglTxt2.setTextColor(Color.parseColor("#FFFFFF"));
                zxQglImg2.setImageResource(R.mipmap.done);
                if (l_hascomFragment == null) {
                    l_hascomFragment = new HascomFragment();
                    fTransaction.add(R.id.qgl_fragment_daiban, l_hascomFragment);
                } else {
                    fTransaction.show(l_hascomFragment);
                }
                break;
        }
        fTransaction.commit();
    }

}
