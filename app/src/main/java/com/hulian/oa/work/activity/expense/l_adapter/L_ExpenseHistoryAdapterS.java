package com.hulian.oa.work.activity.expense.l_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.ExpenisBean;
import com.hulian.oa.bean.Expense;
import com.hulian.oa.bean.ExpenseBean;
import com.hulian.oa.bean.People;

import java.util.ArrayList;
import java.util.List;

public class L_ExpenseHistoryAdapterS extends BaseQuickAdapter<ExpenisBean,BaseViewHolder> {
    public L_ExpenseHistoryAdapterS(List<ExpenisBean>mData) {
        super(R.layout.item_expense_history, mData);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpenisBean item) {
        ((TextView) helper.getView(R.id.tv_title)).setText(item.getCreateName()+"申请的报销");//申请标题
        ((TextView) helper.getView(R.id.tv_department)).setText(item.getDeptName());//申请部门
        ((TextView) helper.getView(R.id.tv_expense_monkey)).setText(item.getMoney());//报销金额
        ((TextView) helper.getView(R.id.tv_time)).setText(item.getCreateTime().substring(0,item.getCreateTime().length()-3));//报销时间
        TextView tv_state =  helper.getView(R.id.tv_state);//报销状态
        if(item.getState().equals("0")){
            tv_state.setText("待审批");
            tv_state.setTextColor(ContextCompat.getColor(mContext,R.color.bg_yellow_a));
            tv_state.setBackground(ContextCompat.getDrawable(mContext,R.drawable.baoxiao_state_yellow));
        }
        else if (item.getState().equals("1")){
            tv_state.setText("已审批");
            tv_state.setTextColor(ContextCompat.getColor(mContext,R.color.bg_blue_a));
            tv_state.setBackground(ContextCompat.getDrawable(mContext,R.drawable.baoxiao_state_blue));
        }
        else {
            tv_state.setText("已驳回");
            tv_state.setTextColor(ContextCompat.getColor(mContext,R.color.bg_red_a));
            tv_state.setBackground(ContextCompat.getDrawable(mContext,R.drawable.baoxiao_state_red));
        }
    }

}
