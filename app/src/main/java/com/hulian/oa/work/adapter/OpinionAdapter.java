package com.hulian.oa.work.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.Opinion;

import java.util.List;

/**
 * Created by 陈泽宇 on 2020/3/16.
 * Describe:
 */
public class OpinionAdapter extends BaseQuickAdapter<Opinion, BaseViewHolder> {

    public OpinionAdapter(List<Opinion> mData) {
        super(R.layout.item_reporting_opinions, mData);
    }

    @Override
    protected void convert(BaseViewHolder helper, Opinion item) {
        helper.setText(R.id.opinion_people, item.getName())
                .setText(R.id.opinion_time,item.getTime())
                .setText(R.id.opinion_content,item.getContent());
    }
}
