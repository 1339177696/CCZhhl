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
import com.hulian.oa.iac.activity.ApplyAllowActivity;
import com.hulian.oa.iac.adapter.ApplyAllowAdapter;
import com.hulian.oa.iac.base.BaseActivity;
import com.hulian.oa.iac.bean.ApplyAllowBean;
//import com.yanzhenjie.nohttp.NoHttp;
//import com.yanzhenjie.nohttp.RequestMethod;
//import com.yanzhenjie.nohttp.rest.Request;
//import com.yanzhenjie.nohttp.rest.Response;
//import com.zhhl.marketauthority.R;
//import com.zhhl.marketauthority.activity.BaseActivity;
//import com.zhhl.marketauthority.activity.backlog.ApplyAllowActivity;
//import com.zhhl.marketauthority.adapter.ApplyAllowAdapter;
//import com.zhhl.marketauthority.bean.ApplyAllowBean;
//import com.zhhl.marketauthority.config.UrlConfig;
//import com.zhhl.marketauthority.nohttp.listener.HttpListener;
//import com.zhhl.marketauthority.util.GsonUtil;
//import com.zhhl.marketauthority.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//申请许可类别-列表
public class ApplyAllowListActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ImageView back;
    ApplyAllowAdapter mAdapter;
    private List<ApplyAllowBean.ObjBean.ResBean> mData = new ArrayList<>();
    @Override
    protected int setContentView() {
        return R.layout.activity_applyallow_list;
    }

    @Override
    protected void onCreate() {
        addBack();
        setTitleText("申请许可类别-列表");
        init();
        getData();
        //模拟数据
        setData();
    }

    private void setData() {
        ApplyAllowBean.ObjBean.ResBean bean = new ApplyAllowBean.ObjBean.ResBean();
        bean.setN_FIRST_ID("锅炉制造");
        bean.setN_SECOND_ID("锅炉");
        bean.setN_THIRD_ID("锅炉B");
        bean.setV_PARA_ID("额定出口压力小于等于2.5MPa的蒸汽和热水锅炉；有机热载体锅炉");
        bean.setV_UNIT("形式试验单位");
        bean.setV_PRODUCTION("代表产品（限制范围、典型产品）");
        mData.add(bean);
    }

    private void init() {
        mAdapter = new ApplyAllowAdapter(mData);
        mAdapter.openLoadAnimation();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, ApplyAllowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("data",mData.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    private void getData() {
        Intent intent = getIntent();
        String N_L_ID = intent.getStringExtra("N_L_ID");
        String N_B_ID = intent.getStringExtra("N_B_ID");
//        Request<String> request = NoHttp.createStringRequest(UrlConfig.PATH_COMMON, RequestMethod.POST);
//        request.add("N_L_ID",N_L_ID);
//        request.add("N_B_ID",N_B_ID);
//        request.add("N_TYPE","2");
//        request(0,request,httpListener,true,true);

    }
//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response<String> response) {
//            ApplyAllowBean produce = GsonUtil.GsonToBean(response.get(), ApplyAllowBean.class);
//            if (produce!=null){
//                List<ApplyAllowBean.ObjBean.ResBean> resBeans = produce.getObj().getRes();
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
