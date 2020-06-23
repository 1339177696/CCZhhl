package com.hulian.oa.socket.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.Report;
import java.util.List;

/**
 * 作者：qgl 时间： 2020/5/20 11:04
 * Describe:
 */
public class NoticeActivityAdapter extends BaseQuickAdapter<Report, BaseViewHolder> {
    public NoticeActivityAdapter(List<Report> mData) {
        super(R.layout.notticactivity_recy_item, mData);
    }

    @Override
    protected void convert(BaseViewHolder helper, Report item) {
        switch (item.getType()) {
            case "1":
                break;

        }
    }
}
