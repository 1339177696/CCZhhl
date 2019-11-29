package com.hulian.oa.qglactivity.qgladpter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Notice;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.URLImageParser;
import com.hulian.oa.work.file.admin.activity.notice.NoticeParticularsActivity;

import java.util.ArrayList;
import java.util.List;

public class qglTuisongadapter extends RecyclerView.Adapter <qglTuisongadapter.ViewHolder>{
    private Context mContext;
    private List<Notice> dataList = new ArrayList<>();


    public void addAllData(List<Notice> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public qglTuisongadapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_remind;
        private TextView tv_title;
        private TextView tv_time;
        private TextView tv_context;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_remind = itemView.findViewById(R.id.iv_remind);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_context = itemView.findViewById(R.id.tv_context);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.qgltuisongadapter_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, NoticeParticularsActivity.class);
                intent.putExtra("noticeId",dataList.get(position).getNoticeId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
