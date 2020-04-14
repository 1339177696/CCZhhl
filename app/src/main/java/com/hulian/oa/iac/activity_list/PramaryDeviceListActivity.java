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
import com.hulian.oa.iac.activity.PramaryDeviceActivity;
import com.hulian.oa.iac.adapter.PramaryDeviceAdapter;
import com.hulian.oa.iac.base.BaseActivity;
import com.hulian.oa.iac.bean.PramaryDeviceBean;
import com.hulian.oa.iac.bean.SelfCheckDeviceBean;
//import com.yanzhenjie.nohttp.NoHttp;
//import com.yanzhenjie.nohttp.RequestMethod;
//import com.yanzhenjie.nohttp.rest.Request;
//import com.yanzhenjie.nohttp.rest.Response;
//import com.zhhl.marketauthority.R;
//import com.zhhl.marketauthority.activity.BaseActivity;
//import com.zhhl.marketauthority.activity.backlog.PramaryDeviceActivity;
//import com.zhhl.marketauthority.adapter.PramaryDeviceAdapter;
//import com.zhhl.marketauthority.bean.PramaryDeviceBean;
//import com.zhhl.marketauthority.config.UrlConfig;
//import com.zhhl.marketauthority.nohttp.listener.HttpListener;
//import com.zhhl.marketauthority.util.GsonUtil;
//import com.zhhl.marketauthority.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//主要生产设备情况7
public class PramaryDeviceListActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ImageView back;
    PramaryDeviceAdapter mAdapter;
    private List<PramaryDeviceBean.ObjBean.ResBean> mData = new ArrayList<>();
    @Override
    protected int setContentView() {
        return R.layout.activity_selfcheckdevice_list;
    }
    private String N_L_ID;
    private String N_B_ID;
    @Override
    protected void onCreate() {
        addBack();
        setTitleText("主要生产设备情况-列表");
        init();
        getData();
        //模拟数据
        setData();
    }

    private void setData() {
        PramaryDeviceBean.ObjBean.ResBean bean = new PramaryDeviceBean.ObjBean.ResBean();
        bean.setV_E_NAME("仪器设备名称");
        bean.setV_E_PERFOR("仪器设备能力");
        bean.setV_E_NUM("3");
        bean.setV_E_TYPE("2");
        bean.setD_E_BEGIN("2020-03-09");
        bean.setPSZT("0");
        mData.add(bean);
    }

    private void init() {
        mAdapter = new PramaryDeviceAdapter(mData);
        mAdapter.openLoadAnimation();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, PramaryDeviceActivity.class);
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
//        request.add("N_TYPE","7");
//        request(0,request,httpListener,true,true);

    }
//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response<String> response) {
//            System.out.println("主要生产设备状况："+response.get());
//            PramaryDeviceBean produce = GsonUtil.GsonToBean(response.get(), PramaryDeviceBean.class);
//            List<PramaryDeviceBean.ObjBean.ResBean> resBeans = produce.getObj().getRes();
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
