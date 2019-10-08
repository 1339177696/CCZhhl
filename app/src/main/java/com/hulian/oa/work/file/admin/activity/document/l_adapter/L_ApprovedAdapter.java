package com.hulian.oa.work.file.admin.activity.document.l_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Document;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.work.file.admin.activity.document.DocumentLotusActivity;
import com.hulian.oa.work.file.admin.activity.document.DocumentLotusInfoActivity;

import java.util.ArrayList;
import java.util.List;

public class L_ApprovedAdapter extends RecyclerView.Adapter <L_ApprovedAdapter.ViewHolder> {
    private Context mContext;
    private List<Document> dataList = new ArrayList<>();
    private String type;

    public void addAllData(List<Document> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public L_ApprovedAdapter(Context context,String type) {
        mContext = context;
        this.type=type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_time;
        public TextView tv_roam_person;
        private ImageView tv_status;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_roam_person = (TextView) itemView.findViewById(R.id.tv_roam_person);
            tv_status=(ImageView) itemView.findViewById(R.id.tv_status);
        }
    }

    @Override
    public L_ApprovedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_item_approved, parent, false);
        return new L_ApprovedAdapter.ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(L_ApprovedAdapter.ViewHolder holder, final int position) {
        holder.tv_title.setText(dataList.get(position).getOfficialDocumentTitle());
     //   holder.tv_time.setText(TimeUtils.getDateToString(dataList.get(position).getCreate_Time()));
        holder.tv_time.setText(dataList.get(position).getCreate_Time());
        holder.tv_roam_person.setText(dataList.get(position).getApproverIdsNames());

        if(!SPUtils.get(mContext,"isLead","").equals("0")){
            holder.tv_status.setVisibility(View.VISIBLE);
            if("0".equals(dataList.get(position).getOfficialDocumentState()))
            {
//                holder.tv_status.setText("审批中");
//                holder.tv_status.setTextColor(R.color.bg_yellow);
                holder.tv_status.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qj_daishenpi_icon_qgl));

            }
            else if("1".equals(dataList.get(position).getOfficialDocumentState())) {
//                holder.tv_status.setText("审批通过");
//                holder.tv_status.setTextColor(R.color.colorCircle);
                holder.tv_status.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qj_shenpi_tongguo_icon_qgl));

            }
            else if("2".equals(dataList.get(position).getOfficialDocumentState())) {
//                holder.tv_status.setText("审批不通过");
//                holder.tv_status.setTextColor(R.color.colorAccent);
                holder.tv_status.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qj_bohui_icon_qgl));
            }
            else
            {
                holder.tv_status.setVisibility(View.GONE);
            }
        }
        else {
            holder.tv_status.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("offId",dataList.get(position).getOfficialDocumentId());
//                if(SPUtils.get(mContext,"isLead","").equals("0")){
//                    if(!"0".equals(dataList.get(position).getOfficialDocumentState())){
//                        intent.setClass(mContext, DocumentLotusInfoActivity.class);
//                    }
//                        else{
//                        intent.setClass(mContext, DocumentLotusActivity.class);
//                    }
//
//                }
//                else {
//                    intent.setClass(mContext, DocumentLotusInfoActivity.class);
//                }
                intent.setClass(mContext, DocumentLotusInfoActivity.class);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
