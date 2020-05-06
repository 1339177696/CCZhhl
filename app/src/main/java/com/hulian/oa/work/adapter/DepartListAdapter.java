package com.hulian.oa.work.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.Report;

import java.util.List;

/**
 * 作者：qgl 时间： 2020/4/22 10:02
 * Describe:
 */
public class DepartListAdapter extends BaseQuickAdapter<Report, BaseViewHolder> {
    public DepartListAdapter(List<Report> mData) {
        super(R.layout.item_depart_list, mData);
    }

    @Override
    protected void convert(BaseViewHolder holder, Report report) {
        holder.setText(R.id.depart_clock_name, "王俊杰");
    }
}
