package com.hulian.oa.work.file.admin.activity.expense.l_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Expense;
import com.hulian.oa.work.file.admin.activity.expense.ExpenseExamineActivity;
import com.hulian.oa.work.file.admin.activity.leave.LeaveExamineActivity;

import java.util.ArrayList;
import java.util.List;

public class L_ExpensePendAdapter extends RecyclerView.Adapter <L_ExpensePendAdapter.ViewHolder>{
    private Context mContext;
    private List<Expense> dataList = new ArrayList<>();


    public void addAllData(List<Expense> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public L_ExpensePendAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //请假标题
        public TextView tv_title;
        //申请时间
        public TextView tv_time;
        //请假事由
        public TextView tv_expense_monkey;
//        public TextView tv_pend;
        public ImageView tv_pend;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_expense_monkey = (TextView) itemView.findViewById(R.id.tv_expense_monkey);
//            tv_pend = (TextView) itemView.findViewById(R.id.tv_pend);
            tv_pend = (ImageView) itemView.findViewById(R.id.tv_pend);
        }
    }

    @Override
    public L_ExpensePendAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_item_pend_expense, parent, false);

        return new L_ExpensePendAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(L_ExpensePendAdapter.ViewHolder holder, final int position) {
        holder.tv_title.setText(dataList.get(position).getRemark());
        holder.tv_time.setText(dataList.get(position).getCreateTime().split(" ")[0]);
        holder.tv_expense_monkey.setText(dataList.get(position).getMoney()+"元");
        if(dataList.get(position).getState().equals("0")){
//            holder.tv_pend.setBackgroundColor(mContext.getResources().getColor(R.color.tab_color_true));
//            holder.tv_pend.setText("待审批");
            holder.tv_pend.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qj_daishenpi_icon_qgl));

        }
        else if (dataList.get(position).getState().equals("1")){
//            holder.tv_pend.setText("已审批");
//            holder.tv_pend.setBackgroundColor(mContext.getResources().getColor(R.color.color_a_green));
            holder.tv_pend.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qj_shenpi_tongguo_icon_qgl));

        }
        else {
//            holder.tv_pend.setText("驳回");
//            holder.tv_pend.setBackgroundColor(mContext.getResources().getColor(R.color.colorText));
            holder.tv_pend.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qj_bohui_icon_qgl));

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("id",dataList.get(position).getId());
                intent.putExtra("createByid",dataList.get(position).getCreateBy());
                intent.setClass(mContext, ExpenseExamineActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
