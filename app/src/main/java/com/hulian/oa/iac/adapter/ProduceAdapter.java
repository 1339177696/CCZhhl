package com.hulian.oa.iac.adapter;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.iac.bean.ProduceBean;

import java.util.List;

public class ProduceAdapter extends BaseQuickAdapter<ProduceBean.ObjBean.ResBean, BaseViewHolder> {

    public ProduceAdapter(List<ProduceBean.ObjBean.ResBean> data) {
        super(R.layout.item_produce, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ProduceBean.ObjBean.ResBean item) {
        helper.setText(R.id.tv_title,"产品名称："+item.getV_GC_TYPE());
    }
}
