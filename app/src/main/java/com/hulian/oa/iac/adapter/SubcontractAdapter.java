package com.hulian.oa.iac.adapter;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.iac.bean.SubcontractBean;

import java.util.List;

public class SubcontractAdapter extends BaseQuickAdapter<SubcontractBean.ObjBean.ResBean, BaseViewHolder> {

    public SubcontractAdapter(List<SubcontractBean.ObjBean.ResBean> data) {
        super(R.layout.item_subcontrat, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SubcontractBean.ObjBean.ResBean item) {
        helper.setText(R.id.tv_title,"分包外协项目："+item.getV_S_COMPANY());
    }
}
