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
import com.hulian.oa.R;
import com.hulian.oa.iac.activity.ManageStaffActivity;
import com.hulian.oa.iac.adapter.ManageStaffAdapter;
import com.hulian.oa.iac.base.BaseActivity;
import com.hulian.oa.iac.bean.ManageStaffBean;
//import com.yanzhenjie.nohttp.NoHttp;
//import com.yanzhenjie.nohttp.RequestMethod;
//import com.yanzhenjie.nohttp.rest.Request;
//import com.yanzhenjie.nohttp.rest.Response;
//import com.zhhl.marketauthority.R;
//import com.zhhl.marketauthority.activity.BaseActivity;
//import com.zhhl.marketauthority.activity.backlog.ManageStaffActivity;
//import com.zhhl.marketauthority.adapter.ManageStaffAdapter;
//import com.zhhl.marketauthority.bean.ManageStaffBean;
//import com.zhhl.marketauthority.config.UrlConfig;
//import com.zhhl.marketauthority.nohttp.listener.HttpListener;
//import com.zhhl.marketauthority.util.GsonUtil;
//import com.zhhl.marketauthority.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ManageStaffListActivity  extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ImageView back;
    ManageStaffAdapter mAdapter;
    private Context mContext = ManageStaffListActivity.this;
    private List<ManageStaffBean.ObjBean.ResBean> mData = new ArrayList<>();
    private String N_L_ID;
    private String N_B_ID;
    @Override
    protected int setContentView() {
        return R.layout.activity_managestaff_list;
    }

    @Override
    protected void onCreate() {
        addBack();
        setTitleText("管理、专业、作业人员情况");
        init();
        getData();
        //模拟数据
        setData();
    }

    private void setData() {

        ManageStaffBean.ObjBean.ResBean bean = new ManageStaffBean.ObjBean.ResBean();
        bean.setV_M_ITEM("管理系统开发");
        bean.setV_M_NAME("王俊杰");
        bean.setV_M_AGE("26");
        bean.setV_M_EDUCATION("本科");
        bean.setV_M_SPECIALTY("计算机软件");
        bean.setV_M_TITLE("职称");
        bean.setV_M_YEAR("7年");
        bean.setV_M_CREDENTIALS("持专业证");
        bean.setV_M_REMARK("备注说明");
        bean.setPSZT("0");
        mData.add(bean);
    }

    private void init() {
        mAdapter = new ManageStaffAdapter(mData);
        mAdapter.openLoadAnimation();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, ManageStaffActivity.class);
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
//        request.add("N_TYPE","9");
//        request(0,request,httpListener,true,true);

    }
//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response<String> response) {
//            System.out.println("管理专业人员"+response.get());
//            ManageStaffBean result = GsonUtil.GsonToBean(response.get(), ManageStaffBean.class);
//            List<ManageStaffBean.ObjBean.ResBean> resBeans = result.getObj().getRes();
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
