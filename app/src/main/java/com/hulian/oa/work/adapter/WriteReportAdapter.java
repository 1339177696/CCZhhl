package com.hulian.oa.work.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hulian.oa.R;
import com.hulian.oa.bean.Report;

import java.util.List;

/**
 * Created by 陈泽宇 on 2020/3/11.
 * Describe:
 */
public class WriteReportAdapter extends BaseQuickAdapter<Report, BaseViewHolder> {
    public WriteReportAdapter(List<Report> mData){
        super(R.layout.item_read_report,mData);
    }
    @Override
    protected void convert(BaseViewHolder holder, Report report) {
        switch (report.getType()){
            case "1":
                holder.setImageResource(R.id.iv,R.mipmap.day);
                holder.setText(R.id.title_name,report.getName() +"的日报");
                break;

            case "2":
                holder.setImageResource(R.id.iv,R.mipmap.week);
                holder.setText(R.id.title_name,report.getName() +"的周报");
                break;

            case "3":
                holder.setImageResource(R.id.iv,R.mipmap.month);
                holder.setText(R.id.title_name,report.getName() +"的月报");
                break;
        }

        holder.setText(R.id.time,report.getTime())
                .setText(R.id.finished_work_tv,report.getFinishWork())
                .setText(R.id.unfinished_work_tv,report.getUnFinishWork());

    }


}
