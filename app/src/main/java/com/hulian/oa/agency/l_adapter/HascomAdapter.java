package com.hulian.oa.agency.l_adapter;

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
import com.hulian.oa.bean.AgencyNew;
import com.hulian.oa.work.file.admin.activity.document.DocumentLotusActivity;
import com.hulian.oa.work.file.admin.activity.document.DocumentLotusInfoActivity;
import com.hulian.oa.work.file.admin.activity.expense.ExpenseApplyResultActivity;
import com.hulian.oa.work.file.admin.activity.expense.ExpenseExamineActivity;
import com.hulian.oa.work.file.admin.activity.instruct.InstructBackActivity;
import com.hulian.oa.work.file.admin.activity.instruct.InstructDetailsActivity;
import com.hulian.oa.work.file.admin.activity.leave.LeaveApplyResultActivity;
import com.hulian.oa.work.file.admin.activity.leave.LeaveExamineActivity;
import com.hulian.oa.work.file.admin.activity.meeting.MeetingSigninActivity;
import com.hulian.oa.work.file.admin.activity.task.l_details_activity.TaskCompletedDetailsActivity;
import com.hulian.oa.work.file.admin.activity.task.l_details_activity.TaskUndoneDetailsActivity;

import java.util.ArrayList;
import java.util.List;
//已办
public class HascomAdapter extends RecyclerView.Adapter <HascomAdapter.ViewHolder>{
    private Context mContext;
    private List<AgencyNew> dataList = new ArrayList<>();

    public void addAllData(List<AgencyNew>   dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public HascomAdapter(Context context) {
        mContext = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_time;
        public TextView tv_content;
        public TextView tv_des;
        public TextView tv_type;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_des= (TextView) itemView.findViewById(R.id.tv_des);
            tv_type=(TextView) itemView.findViewById(R.id.tv_type);
        }
    }
    @Override
    public HascomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agency_d, parent, false);
        return new HascomAdapter.ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(HascomAdapter.ViewHolder holder, final int position) {
        holder.tv_title.setText(dataList.get(position).getTitile());
        holder.tv_content.setText(dataList.get(position).getContent());
        holder.tv_time.setText(dataList.get(position).getTime());
        holder.tv_des.setText(dataList.get(position).getDes());
        if (dataList.get(position).getContent().equals("")) {
            holder.tv_content.setText("");
        } else {
            String text_content = dataList.get(position).getContent().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getContent().split(" ")[1] + "</font>";

            holder.tv_content.setText(Html.fromHtml(text_content));
        }
        if (dataList.get(position).getContent().equals("")) {
            holder.tv_content.setText("");
        } else {
            String text_content = dataList.get(position).getContent().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getContent().split(" ")[1] + "</font>";
            holder.tv_content.setText(Html.fromHtml(text_content));
        }
        if (dataList.get(position).getDes().equals("")) {
            holder.tv_des.setText("");
        } else {
            String text_content = dataList.get(position).getDes().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getDes().split(" ")[1] + "</font>";
            holder.tv_des.setText(Html.fromHtml(text_content));
        }
        //0任务协同，1：公文流转2:指令安排3:会议安排 4 报销 5 请假
        switch (dataList.get(position).getType()) {
            case "0":
                holder.tv_type.setText("协");
                break;
            case "1":
                holder.tv_type.setText("文");
                break;
            case "2":
                holder.tv_type.setText("令");
                break;
            case "3":
                holder.tv_type.setText("会");
                break;
            case "4":
                holder.tv_type.setText("报");
                break;
            case "5":
                holder.tv_type.setText("假");
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                switch (dataList.get(position).getType()) {
                    case "0":
                        intent.setClass(mContext, TaskCompletedDetailsActivity.class);
                        intent.putExtra("PORID",dataList.get(position).getId());
                        intent.putExtra("source","0");
                        intent.putExtra("ID",dataList.get(position).getId());
                        break;
                    case "1":
                        intent.setClass(mContext, DocumentLotusInfoActivity.class);
                        intent.putExtra("source","0");
                        intent.putExtra("offId",dataList.get(position).getId());
                        break;
                    case "2":
                        intent.setClass(mContext, InstructDetailsActivity.class);
                        intent.putExtra("source","0");
                        intent.putExtra("id",dataList.get(position).getId());
                        break;
                    case "3":
                        intent.setClass(mContext, MeetingSigninActivity.class);
                        intent.putExtra("source","0");
                        intent.putExtra("id",dataList.get(position).getId());
                        break;
                    case "4":
                        intent.setClass(mContext, ExpenseApplyResultActivity.class);
                        intent.putExtra("id",dataList.get(position).getId());
                        intent.putExtra("source","0");
                        intent.putExtra("createByid",dataList.get(position).getCreateBy());
                        break;
                    case "5":
                        intent.setClass(mContext, LeaveApplyResultActivity.class);
                        intent.putExtra("id",dataList.get(position).getId());
                        intent.putExtra("source","0");
                        intent.putExtra("createByid",dataList.get(position).getCreateBy());
                        break;
                }
                    mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

