package com.hulian.oa.work.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.Report;

import java.util.List;

/**
 * Created by qgl
 * Describe: 考勤列表
 */
public class AttenListAdapter extends BaseQuickAdapter<Report, BaseViewHolder> {
    public AttenListAdapter(List<Report> mData){
        super(R.layout.item_atten_list,mData);
    }
    @Override
    protected void convert(BaseViewHolder holder, Report report) {
        holder.setText(R.id.att_tv_date,report.getTime())
                .setText(R.id.att_clock_time,"上班 08:01   下班 17:48");

    }


}
