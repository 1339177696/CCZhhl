package com.hulian.oa.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Fab;
import com.hulian.oa.bean.Fab2;
import com.hulian.oa.me.activity.MeActivity;
import com.hulian.oa.news.adapter.MyViewPageAdapter;
import com.hulian.oa.utils.Identity;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.work.activity.notice.NoticeIssueActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

public class NewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Unbinder unbinder;
    @BindView(R.id.tv_mengban)
    TextView tvMengban;
    @BindView(R.id.my_tablayout)
    TabLayout myTablayout;
    @BindView(R.id.my_viewpager)
    ViewPager myViewpager;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_send)
    TextView tvSend;
    private int role = 0;
    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Main1_home.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String requestJson) {
        NewsFragment fragment = new NewsFragment();
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
        View view = inflater.inflate(R.layout.fra_news, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        iniTview();
        ArrayList<String> titleDatas = new ArrayList<>();
        titleDatas.add("新闻");
        titleDatas.add("通告");
        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new News_1_Fragment());
        fragmentList.add(new News_2_Fragment());
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getChildFragmentManager(), titleDatas, fragmentList);
        myViewpager.setAdapter(myViewPageAdapter);
        myTablayout.setupWithViewPager(myViewpager);
        role = Identity.aa(SPUtils.get(getActivity(),"roleKey","").toString());
        myTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tvSend.setVisibility(View.GONE);
                        break;
                    case 1:
                        if (role == 3 || role == 4){
                            tvSend.setVisibility(View.VISIBLE);
                        }else {
                            tvSend.setVisibility(View.GONE);
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        myTablayout.setTabsFromPagerAdapter(myViewPageAdapter);
        return view;
    }

    public void iniTview() {
        tv_type.setText(SPUtils.get(getActivity(), "nickname", "").toString().substring(SPUtils.get(getActivity(), "nickname", "").toString().length() - 2, SPUtils.get(getActivity(), "nickname", "").toString().length()));
    }

    public void onEventMainThread(NewsFragment event) {
        iniTview();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.tv_mengban)
    public void onViewClicked2() {
        Fab2 fab2 = new Fab2();
        fab2.setTag("0");
        EventBus.getDefault().post(fab2);
    }

    public void onEventMainThread(Fab event) {
        if (event.getTag().equals("0")) {
            tvMengban.setVisibility(View.GONE);
        } else {
            tvMengban.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.tv_type, R.id.tv_send})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_type:
                startActivity(new Intent(getActivity(), MeActivity.class));
                break;
            case R.id.tv_send:
                Intent intent = new Intent(getActivity(), NoticeIssueActivity.class);
                startActivityForResult(intent, 1);
                break;
        }

    }

}
