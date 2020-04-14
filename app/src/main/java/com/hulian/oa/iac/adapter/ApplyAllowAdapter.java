package com.hulian.oa.iac.adapter;

import android.support.annotation.NonNull;

import com.hulian.oa.R;
import com.hulian.oa.iac.bean.ApplyAllowBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ApplyAllowAdapter extends BaseQuickAdapter<ApplyAllowBean.ObjBean.ResBean, BaseViewHolder> {

    public ApplyAllowAdapter(List<ApplyAllowBean.ObjBean.ResBean> data) {
        super(R.layout.item_applyallow, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ApplyAllowBean.ObjBean.ResBean item) {
        helper.setText(R.id.tv_title,"许可项目:"+item.getN_FIRST_ID());
    }
}
