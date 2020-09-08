package com.hulian.oa.work.activity.statistical.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.ExpenseStatisDeteBean;
import com.hulian.oa.bean.Report;

import java.util.List;

/**
 * Created by
 * Describe:
 */
public class ExpenStatisDetelisAdapter extends BaseQuickAdapter<ExpenseStatisDeteBean, BaseViewHolder> {
    public ExpenStatisDetelisAdapter(List<ExpenseStatisDeteBean> mData){
        super(R.layout.expenstatisdetelislayout,mData);
    }
    @Override
    protected void convert(BaseViewHolder holder, ExpenseStatisDeteBean report) {
        holder.setText(R.id.ex_tv_time,report.getCreateTime());
        if (report.getCause().equals("null")){
            holder.setText(R.id.ex_tv_money,"￥" + report.getMoney()+"   ()");
        }else {
            holder.setText(R.id.ex_tv_money,"￥" + report.getMoney()+"   (" + report.getCause() + ")");
        }
    }


}
