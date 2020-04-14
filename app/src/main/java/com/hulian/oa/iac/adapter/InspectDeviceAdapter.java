package com.hulian.oa.iac.adapter;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.iac.bean.InspectDeviceBean;

import java.util.List;

public class InspectDeviceAdapter extends BaseQuickAdapter<InspectDeviceBean.ObjBean.ResBean, BaseViewHolder> {

    public InspectDeviceAdapter(List<InspectDeviceBean.ObjBean.ResBean> data) {
        super(R.layout.item_inspectdevice, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, InspectDeviceBean.ObjBean.ResBean item) {
        helper.setText(R.id.tv_title,"仪器设备名称："+item.getV_T_NAME());
    }
}
