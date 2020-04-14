package com.hulian.oa.iac.activity_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hulian.oa.R;
import com.hulian.oa.iac.activity.InspectDeviceActivity;
import com.hulian.oa.iac.adapter.InspectDeviceAdapter;
import com.hulian.oa.iac.base.BaseActivity;
import com.hulian.oa.iac.bean.InspectDeviceBean;
import com.hulian.oa.iac.bean.PramaryDeviceBean;
//import com.yanzhenjie.nohttp.NoHttp;
//import com.yanzhenjie.nohttp.RequestMethod;
//import com.yanzhenjie.nohttp.rest.Request;
//import com.yanzhenjie.nohttp.rest.Response;
//import com.zhhl.marketauthority.R;
//import com.zhhl.marketauthority.activity.BaseActivity;
//import com.zhhl.marketauthority.activity.backlog.InspectDeviceActivity;
//import com.zhhl.marketauthority.adapter.InspectDeviceAdapter;
//import com.zhhl.marketauthority.bean.InspectDeviceBean;
//import com.zhhl.marketauthority.config.UrlConfig;
//import com.zhhl.marketauthority.nohttp.listener.HttpListener;
//import com.zhhl.marketauthority.util.GsonUtil;
//import com.zhhl.marketauthority.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//主要检验与试验仪器设备状况-列表
public class InspectDeviceListActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ImageView back;
    InspectDeviceAdapter mAdapter;
    private List<InspectDeviceBean.ObjBean.ResBean> mData = new ArrayList<>();
    @Override
    protected int setContentView() {
        return R.layout.activity_selfcheckdevice_list;
    }
    private String N_L_ID;
    private String N_B_ID;
    @Override
    protected void onCreate() {
        addBack();
        setTitleText("自行校验仪器设备能力-列表");
        init();
        getData();
        //模拟数据
        setData();
    }

    private void setData() {
        InspectDeviceBean.ObjBean.ResBean bean = new InspectDeviceBean.ObjBean.ResBean();
        bean.setV_T_NAME("仪器设备名称");
        bean.setV_T_PERFOR("仪器设备能力");
        bean.setV_T_NUM("3");
        bean.setV_T_TYPE("2");
        bean.setD_T_BEGIN("2020-03-09");
        bean.setPSZT("0");
        mData.add(bean);
    }

    private void init() {
        mAdapter = new InspectDeviceAdapter(mData);
        mAdapter.openLoadAnimation();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, InspectDeviceActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("data",mData.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    private void getData() {
//        Intent intent = getIntent();
//        N_L_ID =  intent.getStringExtra("N_L_ID");
//        N_B_ID =  intent.getStringExtra("N_B_ID");
//        Request<String> request = NoHttp.createStringRequest(UrlConfig.PATH_COMMON, RequestMethod.POST);
//        request.add("N_L_ID",N_L_ID);
//        request.add("N_B_ID",N_B_ID);
//        request.add("N_TYPE","8");
//        request(0,request,httpListener,true,true);

    }
//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response<String> response) {
//            System.out.println("主要检验=====："+response.get());
//            InspectDeviceBean produce = GsonUtil.GsonToBean(response.get(), InspectDeviceBean.class);
//            List<InspectDeviceBean.ObjBean.ResBean> resBeans = produce.getObj().getRes();
//            if (produce!=null){
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
