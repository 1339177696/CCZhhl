package com.hulian.oa.work.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.ClockBean;
import com.hulian.oa.bean.Report;
import com.hulian.oa.utils.TimeUtils;

import java.util.List;

/**
 * Created by qgl
 * Describe: 考勤列表
 */
public class AttenListAdapter extends BaseQuickAdapter<ClockBean, BaseViewHolder> {
    public AttenListAdapter(List<ClockBean> mData){
        super(R.layout.item_atten_list,mData);
    }
    @Override
    protected void convert(BaseViewHolder holder, ClockBean report) {
        switch (report.getType()){
            // 0正常，1迟到，2早退，3加班，4请假，5缺勤，6外勤
            case "0":
            case "1":
            case "2":
            case "3":
            case "6":
                holder.setText(R.id.att_tv_date,report.getCreateTime())
                        .setText(R.id.att_clock_time,"上班" + report.getRegisterUpTime() + "下班" + report.getRegisterDownTime());
                break;
            case "4":
                holder.setText(R.id.att_tv_date,report.getStartTime()+" --- "+report.getEndTime())
                        .setText(R.id.att_clock_time,"共"+report.getDuration()+"天");
                break;
            case "5":
                holder.setText(R.id.att_tv_date,report.getCreateTime());
                if (("2").equals(report.getRegisterUpState())|| TimeUtils.compareTwoTime((String) report.getRegisterUpTime(), "17:30") <= 0)
                {
                    holder.setText(R.id.att_clock_time,"全天");
                }else if (TimeUtils.compareTwoTime((String) report.getRegisterDownTime(), "11:30") >= 0){
                    holder.setText(R.id.att_clock_time,"下午");
                }else if (TimeUtils.compareTwoTime((String) report.getRegisterUpTime(), "13:30") <= 0){
                    holder.setText(R.id.att_clock_time,"上午");
                }


                break;



        }


    }


}
