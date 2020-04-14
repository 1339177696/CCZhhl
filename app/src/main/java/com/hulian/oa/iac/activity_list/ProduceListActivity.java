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
import com.hulian.oa.iac.activity.ProduceActivity;
import com.hulian.oa.iac.adapter.ProduceAdapter;
import com.hulian.oa.iac.base.BaseActivity;
import com.hulian.oa.iac.bean.ProduceBean;
//import com.yanzhenjie.nohttp.NoHttp;
//import com.yanzhenjie.nohttp.RequestMethod;
//import com.yanzhenjie.nohttp.rest.Request;
//import com.yanzhenjie.nohttp.rest.Response;
//import com.zhhl.marketauthority.R;
//import com.zhhl.marketauthority.activity.BaseActivity;
//import com.zhhl.marketauthority.activity.backlog.ProduceActivity;
//import com.zhhl.marketauthority.adapter.ProduceAdapter;
//import com.zhhl.marketauthority.bean.ProduceBean;
//import com.zhhl.marketauthority.config.UrlConfig;
//import com.zhhl.marketauthority.nohttp.listener.HttpListener;
//import com.zhhl.marketauthority.util.GsonUtil;
//import com.zhhl.marketauthority.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//试生产(制造)情况-列表
public class ProduceListActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ImageView back;
    ProduceAdapter mAdapter;
    private String N_L_ID;
    private String N_B_ID;
    private List<ProduceBean.ObjBean.ResBean> mData = new ArrayList<>();
    @Override
    protected int setContentView() {
        return R.layout.activity_produce_list;
    }

    @Override
    protected void onCreate() {
        addBack();
        setTitleText("试生产(制造)情况-数据");
        init();
        getData();
        setData();
    }

    private void setData() {
        ProduceBean.ObjBean.ResBean bean = new ProduceBean.ObjBean.ResBean();
        bean.setV_GC_TYPE("锅炉");
        bean.setV_SHB_TYPE("锅炉制造");
        bean.setV_LEVEL("一级");
        bean.setV_SBXH("V30214");
        bean.setPSZT("1");
        mData.add(bean);
    }

    private void init() {
        mAdapter = new ProduceAdapter(mData);
        mAdapter.openLoadAnimation();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, ProduceActivity.class);
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
//        request.add("N_TYPE","4");
//        request(0,request,httpListener,true,true);

    }
//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response<String> response) {
//            ProduceBean produce = GsonUtil.GsonToBean(response.get(), ProduceBean.class);
//            List<ProduceBean.ObjBean.ResBean> resBeans = produce.getObj().getRes();
//            if (produce!=null && resBeans!=null){
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
