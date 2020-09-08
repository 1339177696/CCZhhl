package com.hulian.oa.work.activity.expense.l_adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hulian.oa.R;
import com.hulian.oa.bean.ExpenBean;
import com.hulian.oa.utils.FullyGridLayoutManager;
import com.luck.picture.lib.entity.LocalMedia;
import java.util.ArrayList;
import java.util.List;

public class ExpenseExamineAdapterSS extends RecyclerView.Adapter<ExpenseExamineAdapterSS.ViewHolder> {
    private LayoutInflater mInflater;
    private List<ExpenBean> list = new ArrayList<>();
    private Context mContext;
    private String[] images = new String[]{};

    //点击
    private ExpenseSecondAdapterSS.onAddPicClickListener mOnAddPicClickListener;

    public ExpenseExamineAdapterSS(Context mContext, ExpenseSecondAdapterSS.onAddPicClickListener mOnAddPicClickListener) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.mOnAddPicClickListener = mOnAddPicClickListener;

    }
    public void setList(List<ExpenBean> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recycler_bill_pic;
        TextView tv_expense_reason;
        TextView tv_ietm_mony;
        TextView tv_fee_description;
        private ExpenseSecondAdapterSS expenseAdapter;
        private List<LocalMedia> listMedia = new ArrayList<>();
        private List<LocalMedia> selectList = new ArrayList<>();

        public ViewHolder(View view) {
            super(view);
            tv_expense_reason =  view.findViewById(R.id.tv_expense_reason);
            tv_ietm_mony = view.findViewById(R.id.tv_ietm_mony);
            recycler_bill_pic =  view.findViewById(R.id.recycler_bill_pic);
            tv_fee_description = view.findViewById(R.id.tv_fee_description);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_work_expense_result_s, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.listMedia.clear();
        holder.selectList.clear();
        images = new String[]{};
        if (list.size()>0){
            ExpenBean expenseBean = list.get(position);
            if (expenseBean!=null){
                images = expenseBean.getFiles().split(",");
                for (int i=0;i<images.length;i++){
                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setPath(images[i]);
                    holder.selectList.add(localMedia);
                }
                List<LocalMedia> localMediaList =  holder.selectList;
                if (localMediaList!=null && localMediaList.size()>0){
                    holder.listMedia.addAll(holder.selectList);
                }
            }
        }
        if (holder.expenseAdapter == null) {
            holder.expenseAdapter = new ExpenseSecondAdapterSS(mContext,mOnAddPicClickListener,position);
            holder.expenseAdapter.setList(holder.listMedia);//设置数据
            FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
            holder.recycler_bill_pic.setLayoutManager(manager);
            holder.recycler_bill_pic.setAdapter(holder.expenseAdapter);
        } else {
            holder.expenseAdapter.setPosition(position);
            holder.expenseAdapter.notifyDataSetChanged();
        }
        Log.e("ExpenseImageAdapter",list.get(position).getLineMoney());
        //设置数据
        holder.tv_ietm_mony.setText(list.get(position).getLineMoney()+"元");
        holder.tv_fee_description.setText(list.get(position).getCause());
        switch (list.get(position).getApproveType()){
            case "1":
                holder.tv_expense_reason.setText("采购费");
                break;
            case "2":
                holder.tv_expense_reason.setText("加班餐费");
                break;
            case "3":
                holder.tv_expense_reason.setText("加班打车费");
                break;
            case "4":
                holder.tv_expense_reason.setText("外出打车费");
                break;
            case "5":
                holder.tv_expense_reason.setText("办公用品费");
                break;
            case "6":
                holder.tv_expense_reason.setText("差旅费");
                break;
            case "7":
                holder.tv_expense_reason.setText("员工福利");
                break;
            case "8":
                holder.tv_expense_reason.setText("招待费");
                break;
            case "9":
                holder.tv_expense_reason.setText("其他费用");
                break;
        }
    }

}
