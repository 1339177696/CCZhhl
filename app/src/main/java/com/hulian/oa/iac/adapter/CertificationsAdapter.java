package com.hulian.oa.iac.adapter;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.iac.bean.Backlog;
//import com.zhhl.marketauthority.R;
//import com.zhhl.marketauthority.bean.Backlog;

import java.util.List;

public class CertificationsAdapter extends BaseQuickAdapter<Backlog, BaseViewHolder> {
    public CertificationsAdapter(List<Backlog> data) {
        super(R.layout.item_backlog_work_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Backlog item) {

    }
}
