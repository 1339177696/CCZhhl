package com.hulian.oa.iac.activity_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.library.banner.BannerLayout;
import com.hulian.oa.R;
import com.hulian.oa.iac.activity.SubcontractActivity;
import com.hulian.oa.iac.adapter.SubcontractAdapter;
import com.hulian.oa.iac.base.BaseActivity;
import com.hulian.oa.iac.bean.SubcontractBean;
//import com.hulian.oa.iac.config.UrlConfig;
//import com.yanzhenjie.nohttp.NoHttp;
//import com.yanzhenjie.nohttp.RequestMethod;
//import com.yanzhenjie.nohttp.rest.Request;
//import com.yanzhenjie.nohttp.rest.Response;
//import com.zhhl.marketauthority.R;
//import com.zhhl.marketauthority.activity.BaseActivity;
//import com.zhhl.marketauthority.activity.backlog.SubcontractActivity;
//import com.zhhl.marketauthority.adapter.SubcontractAdapter;
//import com.zhhl.marketauthority.bean.SubcontractBean;
//import com.zhhl.marketauthority.config.UrlConfig;
//import com.zhhl.marketauthority.nohttp.listener.HttpListener;
//import com.zhhl.marketauthority.util.GsonUtil;
//import com.zhhl.marketauthority.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//分包、外协情况-列表
public class SubcontractListActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ImageView back;
    SubcontractAdapter mAdapter;
    private Context mContext = SubcontractListActivity.this;
    private List<SubcontractBean.ObjBean.ResBean> mData = new ArrayList<>();
    private String N_L_ID;
    private String N_B_ID;
    @Override
    protected int setContentView() {
        return R.layout.activity_subcontract_list;
    }

    @Override
    protected void onCreate() {
        addBack();
        setTitleText("分包、外协情况");
        init();
        getData();
        //模拟数据
        setData();
    }

    private void setData() {
        SubcontractBean.ObjBean.ResBean bean = new SubcontractBean.ObjBean.ResBean();
        bean.setV_S_COMPANY("智慧互联科技公司");
        bean.setV_S_CREDITCODE("KJ9842133");
        bean.setV_S_ITEM("办公管理");
        bean.setV_S_TYPE("无");
        bean.setPSZT("0");
        mData.add(bean);

    }

    private void init() {
        mAdapter = new SubcontractAdapter(mData);
        mAdapter.openLoadAnimation();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, SubcontractActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("data",mData.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    private void getData() {
        Intent intent = getIntent();
        N_L_ID =  intent.getStringExtra("N_L_ID");
        N_B_ID =  intent.getStringExtra("N_B_ID");
//        Request<String> request = NoHttp.createStringRequest(UrlConfig.PATH_COMMON, RequestMethod.POST);
//        request.add("N_L_ID",N_L_ID);
//        request.add("N_B_ID",N_B_ID);
//        request.add("N_TYPE","5");
//        request(0,request,httpListener,true,true);

    }
//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response<String> response) {
//            System.out.println("分包外协返回结果==="+response.get());
//            SubcontractBean result = GsonUtil.GsonToBean(response.get(), SubcontractBean.class);
//            List<SubcontractBean.ObjBean.ResBean> resBeans = result.getObj().getRes();
//            if (result!=null){
//                for (int i=0;i<resBeans.size();i++){
//                    mData.add(resBeans.get(i));
//                }
//                mAdapter.notifyDataSetChanged();
//            }
//        }
//
//        @Override
//        public void onFailed(int what, Response<String> response) {
//            ToastUtils.show(mContext,"请求失败");
//        }
//    };
    protected void addBack() {
        back = findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
