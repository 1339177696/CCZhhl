package com.hulian.oa.work.activity.expense.l_adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.People;

public class ExpenseExamineAdapterS extends BaseQuickAdapter<People,BaseViewHolder> {

    public ExpenseExamineAdapterS(){
        super(R.layout.item_work_expense_result_s,null);
    }
    @Override
    protected void convert(BaseViewHolder helper, People item) {
        ((TextView)helper.getView(R.id.tv_expense_reason)).setText("");//报销事由
        ((TextView)helper.getView(R.id.tv_detail)).setText("");//报销明细
        ((TextView)helper.getView(R.id.tv_amount_sub)).setText("");//报销金额
        ((TextView)helper.getView(R.id.tv_invoice_date)).setText("");//开票日期
        ((TextView)helper.getView(R.id.tv_fee_description)).setText("");//费用说明
        RecyclerView recycler_bill_pic = helper.getView(R.id.recycler_bill_pic);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.getView(R.id.tv_detail);

    }

}
