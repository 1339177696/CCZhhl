package com.hulian.oa.work.activity.expense.l_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.Expense;
import com.hulian.oa.bean.People;

import java.util.ArrayList;
import java.util.List;

public class L_ExpenseHistoryAdapterS extends BaseQuickAdapter<String,BaseViewHolder> {
    public L_ExpenseHistoryAdapterS() {
        super(R.layout.item_expense_history, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ((TextView) helper.getView(R.id.tv_title)).setText("");//申请标题
        ((TextView) helper.getView(R.id.tv_department)).setText("");//申请部门
        ((TextView) helper.getView(R.id.tv_expense_monkey)).setText("");//报销金额
        ((TextView) helper.getView(R.id.tv_time)).setText("");//报销时间
        TextView tv_state =  helper.getView(R.id.tv_state);//报销状态
        if(item.equals("0")){
            tv_state.setBackgroundColor(mContext.getResources().getColor(R.color.tab_color_true));
            tv_state.setText("待审批");
        }
        else if (item.equals("1")){
            tv_state.setText("已审批");
            tv_state.setBackgroundColor(mContext.getResources().getColor(R.color.color_a_green));
        }
        else {
            tv_state.setText("驳回");
            tv_state.setBackgroundColor(mContext.getResources().getColor(R.color.colorText));
        }
    }

}
