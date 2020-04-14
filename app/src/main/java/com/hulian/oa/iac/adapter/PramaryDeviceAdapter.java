package com.hulian.oa.iac.adapter;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.iac.bean.PramaryDeviceBean;

import java.util.List;

public class PramaryDeviceAdapter extends BaseQuickAdapter<PramaryDeviceBean.ObjBean.ResBean, BaseViewHolder> {

    public PramaryDeviceAdapter(List<PramaryDeviceBean.ObjBean.ResBean> data) {
        super(R.layout.item_pramarydevice, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PramaryDeviceBean.ObjBean.ResBean item) {
        helper.setText(R.id.tv_title,"仪器设备名称："+item.getV_E_NAME());
    }
}
