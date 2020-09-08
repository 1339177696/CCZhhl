package com.hulian.oa.work.activity.expense.l_adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.bean.ExpenseBean;
import com.hulian.oa.R;
import com.hulian.oa.utils.FullyGridLayoutManager;
import com.hulian.oa.views.DecimalDigitsInputFilter;
import com.luck.picture.lib.entity.LocalMedia;
import java.util.ArrayList;
import java.util.List;

public class ExpenseImageAdapter extends RecyclerView.Adapter<ExpenseImageAdapter.ViewHolder> {
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private List<ExpenseBean> list = new ArrayList<>();
    private int selectMax = 9;
    private Context mContext;
    /**
     * 点击添加图片跳转
     */
    private ExpenseSecondAdapter.onAddPicClickListener mOnAddPicClickListener;
    private ExpenseSecondAdapter.OnItemDeleteListener onItemDeleteListener;

    //点击
    protected OnItemClickListener mItemClickListener;
    //EditText编辑
    protected OnItemEditListener mItemEditListener;
    protected OnItemEditListener2 mItemEditListener2;

    public ExpenseImageAdapter(Context mContext, OnItemClickListener mItemClickListener, OnItemEditListener mItemEditListener
                                , OnItemEditListener2 mItemEditListener2, ExpenseSecondAdapter.onAddPicClickListener mOnAddPicClickListener,
                               ExpenseSecondAdapter.OnItemDeleteListener onItemDeleteListener) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.mItemClickListener = mItemClickListener;
        this.mItemEditListener = mItemEditListener;
        this.mItemEditListener2 = mItemEditListener2;
        this.mOnAddPicClickListener = mOnAddPicClickListener;
        this.onItemDeleteListener = onItemDeleteListener;
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public void setList(List<ExpenseBean> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_expense_reason;
        EditText ed_money;
        EditText et_expense_directions;
        ImageView iv_delete;
        RecyclerView recycler_bill;
        private ExpenseSecondAdapter expenseAdapter;
        private List<LocalMedia> listMedia = new ArrayList<>();
        public ViewHolder(View view) {
            super(view);
            tv_expense_reason =  view.findViewById(R.id.tv_expense_reason);
            ed_money = view.findViewById(R.id.ed_money);
            et_expense_directions =  view.findViewById(R.id.et_expense_directions);
            recycler_bill = view.findViewById(R.id.recycler_bill);
            iv_delete = view.findViewById(R.id.iv_delete);
            ed_money.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)}); // 限制小数点后位数
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

    public void removeItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,list.size());
    }
    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_baoxiao, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.listMedia.clear();
        Log.e("ExpenseImageAdapter",position+"");
        if (list.size()>0){
            ExpenseBean expenseBean = list.get(position);
            if (expenseBean!=null){
                List<LocalMedia> localMediaList =  expenseBean.getList_invoice();
                if (localMediaList!=null && localMediaList.size()>0){
                    holder.listMedia.addAll(list.get(position).getList_invoice());
                }
            }
        }
        if (position==0){
            holder.iv_delete.setVisibility(View.GONE);
        }
        if (holder.expenseAdapter == null) {
            holder.expenseAdapter = new ExpenseSecondAdapter(mContext, mOnAddPicClickListener,onItemDeleteListener,position);
            holder.expenseAdapter.setList(holder.listMedia);//设置数据
            FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
            holder.recycler_bill.setLayoutManager(manager);
            holder.recycler_bill.setAdapter(holder.expenseAdapter);
        } else {
            holder.expenseAdapter.setPosition(position);
            holder.expenseAdapter.notifyDataSetChanged();
        }
        //设置数据
        holder.ed_money.setText(list.get(position).getExpense_money());
        holder.tv_expense_reason.setText(list.get(position).getExpense_reason());
        //设置监听回调
        //删除回调
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(position,view);
            }
        });
        //报销事由回调
        holder.tv_expense_reason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(position,view);
            }
        });
        //报销金额改变回调
        holder.ed_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mItemEditListener.onEditItem(position,charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //费用说明回调
        holder.et_expense_directions.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mItemEditListener2.onEditItem(position,charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void getData(int position,int positionChild){
        for (int i=0;i<list.get(position).getList_invoice().size();i++){
            for (int j=0;j<list.get(position).getList_invoice().size();j++){
                Log.e("第一层Adapter中输出数据",list.get(position).getList_invoice().get(j).getCompressPath());
            }
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface OnItemEditListener {
        void onEditItem(int position,CharSequence charSequence);
    }

    public void setOnItemEditListener(OnItemEditListener listener) {
        this.mItemEditListener = listener;
    }

    public interface OnItemEditListener2 {
        void onEditItem(int position,CharSequence charSequence);
    }

    public void setOnItemEditListener2(OnItemEditListener2 listener) {
        this.mItemEditListener2 = listener;
    }
}
