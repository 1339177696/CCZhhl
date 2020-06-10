package com.hulian.oa.work.activity.statistical.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.ExpenseStaBean;
import com.hulian.oa.bean.VideoMeeting;

import java.util.List;

/**
 * Created by 陈泽宇 on 2020/4/21
 * Describe:
 */
public class ExpenseStatisAdapter extends BaseQuickAdapter<ExpenseStaBean, BaseViewHolder> {

    public ExpenseStatisAdapter(List<ExpenseStaBean> mData) {
        super(R.layout.item_expense_statis, mData);
    }

    @Override
    protected void convert(BaseViewHolder holder, ExpenseStaBean videoMeeting) {
        holder.setText(R.id.expens_num1,"("+videoMeeting.getNum_per()+")");
        holder.setText(R.id.expens_num2,"￥"+videoMeeting.getNum());
        switch (videoMeeting.getState()){
            case "0":
                holder.setBackgroundRes(R.id.iv_remind,R.drawable.expense_img_yuan0);
                break;
            case "1":
                holder.setBackgroundRes(R.id.iv_remind,R.drawable.expense_img_yuan1);
                break;
             case "2":
                 holder.setBackgroundRes(R.id.iv_remind,R.drawable.expense_img_yuan2);
                 break;
            case "3":
                holder.setBackgroundRes(R.id.iv_remind,R.drawable.expense_img_yuan3);
                break;
            case "4":
                holder.setBackgroundRes(R.id.iv_remind,R.drawable.expense_img_yuan4);
                break;
            case "5":
                holder.setBackgroundRes(R.id.iv_remind,R.drawable.expense_img_yuan5);
                break;
            case "6":
                holder.setBackgroundRes(R.id.iv_remind,R.drawable.expense_img_yuan6);
                break;
            case "7":
                holder.setBackgroundRes(R.id.iv_remind,R.drawable.expense_img_yuan7);
                break;
        }


    }
}
