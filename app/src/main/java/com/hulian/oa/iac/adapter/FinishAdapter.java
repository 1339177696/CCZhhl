package com.hulian.oa.iac.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.iac.bean.FinishFraBean;

import java.util.List;

/**
 * Created by 陈泽宇 on 2019/12/5.
 * Describe:
 */
public class FinishAdapter extends BaseQuickAdapter<FinishFraBean.ObjBean.VarListBean, BaseViewHolder> {

    public FinishAdapter(List<FinishFraBean.ObjBean.VarListBean> data) {
        super(R.layout.item_finish_work_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FinishFraBean.ObjBean.VarListBean item) {
        helper.addOnClickListener(R.id.tv_preview);
        String date = item.getD_DATE();
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8,10);
        helper.setText(R.id.year_month, year + "/" + month);
        helper.setText(R.id.day,day);
        helper.setText(R.id.title, item.getN_FIRST_ID());
        helper.setText(R.id.entity_name, "申请单位:" + item.getV_C_NAME());
        helper.setText(R.id.opinion,"评审意见:" + item.getXCPSZT());
        if (TextUtils.equals(item.getXCPSZT(), "-1")) {
            helper.setText(R.id.state,"未评审");
        } else if(TextUtils.equals(item.getXCPSZT(), "0")){
            helper.setText(R.id.state,"通过");
        }else if(TextUtils.equals(item.getXCPSZT(), "1")){
            helper.setText(R.id.state,"未通过");
        }else if(TextUtils.equals(item.getXCPSZT(), "2")){
            helper.setText(R.id.state,"整改");
        }else{
            helper.setText(R.id.state,"");
            helper.setVisible(R.id.state,false);
        }
    }


}
