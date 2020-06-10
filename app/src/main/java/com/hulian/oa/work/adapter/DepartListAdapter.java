package com.hulian.oa.work.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.ClockDepartBean;
import com.hulian.oa.bean.Report;
import com.hulian.oa.utils.SPUtils;

import java.util.List;

/**
 * 作者：qgl 时间： 2020/4/22 10:02
 * Describe:
 */
public class DepartListAdapter extends BaseQuickAdapter<ClockDepartBean, BaseViewHolder> {
    public DepartListAdapter(List<ClockDepartBean> mData) {
        super(R.layout.item_depart_list, mData);
    }

    @Override
    protected void convert(BaseViewHolder holder, ClockDepartBean report) {
        holder.setText(R.id.depart_clock_name, report.getUserName())
                .setText(R.id.depart_clock_dept,report.getDeptName())
                .setText(R.id.tv_type, report.getUserName().substring
                        (report.getUserName().toString().length()-2,report.getUserName().toString().length()))
                .setText(R.id.depart_click_day,report.getSeveralDay()+"天");

    }
}
