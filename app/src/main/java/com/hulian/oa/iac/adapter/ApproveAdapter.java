package com.hulian.oa.iac.adapter;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.iac.bean.ApproveBean;

import java.util.List;

public class ApproveAdapter extends BaseQuickAdapter<ApproveBean.ObjBean.ResBean, BaseViewHolder> {

    public ApproveAdapter(List<ApproveBean.ObjBean.ResBean> data) {
        super(R.layout.item_approve, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ApproveBean.ObjBean.ResBean item) {
        helper.setText(R.id.tv_title,"认证项目:"+item.getV_CC_ITEM());
    }
}
