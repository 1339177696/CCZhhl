package com.hulian.oa.work.activity.expense.l_adapter;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.ExpenBean;
import com.hulian.oa.utils.FullyGridLayoutManager;
import com.hulian.oa.work.activity.mypreview.PicturePreviewActivity;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExpenseExamineAdapterS extends BaseQuickAdapter<ExpenBean, BaseViewHolder> {
    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
    private ExpenseExamineAdapter adapter;
    RecyclerView recycler_bill_pic;
    private String[] images = new String[]{};

    public ExpenseExamineAdapterS(List<ExpenBean> mData) {
        super(R.layout.item_work_expense_result_s, mData);


    }



    @Override
    protected void convert(BaseViewHolder helper, ExpenBean item) {
        if (item.getApproveType().equals("1")) {
            ((TextView) helper.getView(R.id.tv_expense_reason)).setText("采购费");//报销事由
        } else if (item.getApproveType().equals("2")) {
            ((TextView) helper.getView(R.id.tv_expense_reason)).setText("加班餐费");//报销事由
        } else if (item.getApproveType().equals("3")) {
            ((TextView) helper.getView(R.id.tv_expense_reason)).setText("加班打车费");//报销事由
        } else if (item.getApproveType().equals("4")) {
            ((TextView) helper.getView(R.id.tv_expense_reason)).setText("外出打车费");//报销事由
        } else if (item.getApproveType().equals("5")) {
            ((TextView) helper.getView(R.id.tv_expense_reason)).setText("办公用品费");//报销事由
        } else if (item.getApproveType().equals("6")) {
            ((TextView) helper.getView(R.id.tv_expense_reason)).setText("差旅费");//报销事由
        } else if (item.getApproveType().equals("7")) {
            ((TextView) helper.getView(R.id.tv_expense_reason)).setText("员工福利");//报销事由
        } else if (item.getApproveType().equals("8")) {
            ((TextView) helper.getView(R.id.tv_expense_reason)).setText("招待费");//报销事由
        } else if (item.getApproveType().equals("9")) {
            ((TextView) helper.getView(R.id.tv_expense_reason)).setText("其他费用");//报销事由
        }
        ((TextView) helper.getView(R.id.tv_ietm_mony)).setText(item.getLineMoney() + "元");//报销金额
        ((TextView) helper.getView(R.id.tv_fee_description)).setText(item.getCause());// 说明
        recycler_bill_pic = helper.getView(R.id.recycler_bill_pic);
        selectList.clear();
        if (item.getFiles() != null && item.getFiles() != "null") {
            images = item.getFiles().split(",");
            LocalMedia localMedia = new LocalMedia();
            for (int i=0;i<images.length;i++){
                localMedia.setPath(images[i]);
                selectList.add(localMedia);
            }

            adapter.notifyDataSetChanged();
        }
    }

    //初始化图片信息
    private void init(String[] images) {
        for (int i=0;i<images.length;i++){
            LocalMedia localMedia = new LocalMedia();
            localMedia.setPath(images[i]);
            selectList.add(localMedia);
        }
        //图片信息适配
        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
        recycler_bill_pic.setLayoutManager(manager);
        adapter = new ExpenseExamineAdapter(mContext);
        adapter.setList(selectList);
        recycler_bill_pic.setAdapter(adapter);
        //图片信息大图预览
        adapter.setOnItemClickListener(new ExpenseExamineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(mContext, PicturePreviewActivity.class);
                intent.putExtra(PictureConfig.EXTRA_PREVIEW_SELECT_LIST, (Serializable) selectList);
                intent.putExtra(PictureConfig.EXTRA_POSITION, position);
                mContext.startActivity(intent);
            }
        }) ;
    }



}
