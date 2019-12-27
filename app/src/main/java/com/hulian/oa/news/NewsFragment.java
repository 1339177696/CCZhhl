package com.hulian.oa.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Fab;
import com.hulian.oa.bean.Fab2;
import com.hulian.oa.news.adapter.MyViewPageAdapter;
import com.hulian.oa.news.fragment.News_1_Fragment;
import com.hulian.oa.news.fragment.News_2_Fragment;
import com.hulian.oa.news.fragment.News_3_Fragment;

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
//    @BindView(R.id.my_tablayout)
//    TabLayout myTablayout;
//    @BindView(R.id.my_viewpager)
//    ViewPager myViewpager;
//    @BindView(R.id.my_viewpager)
//    FrameLayout myViewpager;
    @BindView(R.id.zx_img1)
    ImageView zxImg1;
    @BindView(R.id.zx_txt1)
    TextView zxTxt1;
    @BindView(R.id.btn1)
    LinearLayout btn1;
    @BindView(R.id.zx_img2)
    ImageView zxImg2;
    @BindView(R.id.zx_txt2)
    TextView zxTxt2;
    @BindView(R.id.btn2)
    LinearLayout btn2;
    @BindView(R.id.zx_img3)
    ImageView zxImg3;
    @BindView(R.id.zx_txt3)
    TextView zxTxt3;
    @BindView(R.id.btn3)
    LinearLayout btn3;

    private News_1_Fragment news_1_fragment;
    private News_2_Fragment news_2_fragment;
    //文件柜
    private News_3_Fragment news_3_fragment;
    private FragmentManager fManager;
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
        fManager = getFragmentManager();
        btn1.performClick();//模拟一次点击，既进去后选择第一项
        return view;
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

    @OnClick({R.id.btn1, R.id.btn2,R.id.btn3})
    public void onViewClicked(View view) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (view.getId()) {
            case R.id.btn1:
                zxTxt1.setTextColor(Color.parseColor("#FFFFFF"));
                zxImg1.setImageResource(R.mipmap.zx_xinwen_yes);
                zxTxt2.setTextColor(Color.parseColor("#ccccd5"));
                zxImg2.setImageResource(R.mipmap.zx_tongzhi_no);
                zxTxt3.setTextColor(Color.parseColor("#ccccd5"));
                zxImg3.setImageResource(R.mipmap.zx_file_icon_no);
                if(news_1_fragment == null){
                    news_1_fragment = new News_1_Fragment();
                    fTransaction.add(R.id.qgl_fragment,news_1_fragment);
                }else{
                    fTransaction.show(news_1_fragment);
                }
                break;
            case R.id.btn2:
                zxTxt1.setTextColor(Color.parseColor("#ccccd5"));
                zxImg1.setImageResource(R.mipmap.zx_xinwen_no);
                zxTxt2.setTextColor(Color.parseColor("#FFFFFF"));
                zxImg2.setImageResource(R.mipmap.zx_tongzhi_yes);
                zxTxt3.setTextColor(Color.parseColor("#ccccd5"));
                zxImg3.setImageResource(R.mipmap.zx_file_icon_no);
                if(news_2_fragment == null){
                    news_2_fragment = new News_2_Fragment();
                    fTransaction.add(R.id.qgl_fragment,news_2_fragment);
                }else{
                    fTransaction.show(news_2_fragment);
                }
                break;
            case R.id.btn3:
                zxTxt1.setTextColor(Color.parseColor("#ccccd5"));
                zxImg1.setImageResource(R.mipmap.zx_xinwen_no);
                zxTxt2.setTextColor(Color.parseColor("#ccccd5"));
                zxImg2.setImageResource(R.mipmap.zx_tongzhi_no);
                zxTxt3.setTextColor(Color.parseColor("#FFFFFF"));
                zxImg3.setImageResource(R.mipmap.zx_file_icon_yes);
                if(news_3_fragment == null){
                    news_3_fragment = new News_3_Fragment();
                    fTransaction.add(R.id.qgl_fragment,news_3_fragment);
                }else{
                    fTransaction.show(news_3_fragment);
                }
                break;
        }
        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(news_1_fragment != null)fragmentTransaction.hide(news_1_fragment);
        if(news_2_fragment != null)fragmentTransaction.hide(news_2_fragment);
        if(news_3_fragment != null)fragmentTransaction.hide(news_3_fragment);

    }


}
