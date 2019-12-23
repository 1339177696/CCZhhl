package com.hulian.oa.agency.l_adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.AgencyNew;
import com.hulian.oa.bean.Daiban_xin_qgl1;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.work.file.admin.activity.document.DocumentLotusInfoActivity;
import com.hulian.oa.work.file.admin.activity.expense.ExpenseApplyResultActivity;
import com.hulian.oa.work.file.admin.activity.instruct.InstructDetailsActivity;
import com.hulian.oa.work.file.admin.activity.leave.LeaveApplyResultActivity;
import com.hulian.oa.work.file.admin.activity.meeting.MeetingSigninActivity;
import com.hulian.oa.work.file.admin.activity.task.l_details_activity.TaskCompletedDetailsActivity;

import java.util.ArrayList;
import java.util.List;

//已办
public class HascomAdapter_qgl extends RecyclerView.Adapter <HascomAdapter_qgl.ViewHolder>{
    private Context mContext;
    private List<Daiban_xin_qgl1.DataBean> dataList = new ArrayList<>();

    public void addAllData(List<Daiban_xin_qgl1.DataBean>   dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public HascomAdapter_qgl(Context context) {
        mContext = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_time;
        public TextView tv_time2;
        public TextView tv_title;
        public TextView tv_content;
        public TextView tv_des;
        public TextView tv_type;

        public LinearLayout lv_jinji;
        public ImageView lv_img;
        public TextView lv_txt;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_time= (TextView) itemView.findViewById(R.id.tv_time_qgl);
            tv_time2= (TextView) itemView.findViewById(R.id.tv_time_qgl2);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_des= (TextView) itemView.findViewById(R.id.tv_des);
            tv_type=(TextView) itemView.findViewById(R.id.tv_type);
            lv_jinji=itemView.findViewById(R.id.lv_jinji);
            lv_img=itemView.findViewById(R.id.lv_img);
            lv_txt=itemView.findViewById(R.id.lv_txt);


        }
    }
    @Override
    public HascomAdapter_qgl.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agency_d, parent, false);
        return new HascomAdapter_qgl.ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(HascomAdapter_qgl.ViewHolder holder, final int position) {
        holder.tv_title.setText(dataList.get(position).getTitle());
        if(dataList.get(position).getCreateTime()!=null) {
            holder.tv_time.setText(TimeUtils.getDateToString3(dataList.get(position).getCreateTime()));
            holder.tv_time2.setText(TimeUtils.getDateToString4(dataList.get(position).getCreateTime()));
        }

//        holder.tv_content.setText(dataList.get(position).getContent());
//        holder.tv_time.setText(dataList.get(position).getTime());
//        holder.tv_des.setText(dataList.get(position).getDes());
//        if (dataList.get(position).getContent().equals("")) {
//            holder.tv_content.setText("");
//        } else {
//            String text_content = dataList.get(position).getContent().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getContent().split(" ")[1] + "</font>";
//
//            holder.tv_content.setText(Html.fromHtml(text_content));
//        }
//        if (dataList.get(position).getContent().equals("")) {
//            holder.tv_content.setText("");
//        } else {
//            String text_content = dataList.get(position).getContent().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getContent().split(" ")[1] + "</font>";
//            holder.tv_content.setText(Html.fromHtml(text_content));
//        }
//        if (dataList.get(position).getDes().equals("")) {
//            holder.tv_des.setText("");
//        } else {
//            String text_content = dataList.get(position).getDes().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getDes().split(" ")[1] + "</font>";
//            holder.tv_des.setText(Html.fromHtml(text_content));
//        }

        //0任务协同，1：公文流转2:指令安排3:会议安排 4 报销 5 请假
        switch (dataList.get(position).getType()) {
            case "0":
//                holder.tv_type.setText("协");
                break;
            case "1":
//                holder.tv_type.setText("文");
                holder.lv_jinji.setVisibility(View.VISIBLE);
                if (dataList.get(position).getStatus().equals("1"))
                {
                    holder.lv_img.setImageResource(R.mipmap.renwu_jinji_icon);
                    holder.lv_txt.setText("重要且紧急");
                    holder.lv_txt.setTextColor(Color.parseColor("#D23D3D"));
                }else if (dataList.get(position).getStatus().equals("2")){
                    holder.lv_img.setImageResource(R.mipmap.renwu_bujinji_icon);
                    holder.lv_txt.setText("重要不紧急");
                    holder.lv_txt.setTextColor(Color.parseColor("#FE874C"));
                }else if (dataList.get(position).getStatus().equals("3")){
                    holder.lv_img.setImageResource(R.mipmap.renwu_buzhongyao);
                    holder.lv_txt.setText("紧急不重要");
                    holder.lv_txt.setTextColor(Color.parseColor("#6E8AFF"));
                }else {
                    holder.lv_img.setImageResource(R.mipmap.renwu_buzhongyao);
                    holder.lv_txt.setText("不紧急不重要");
                    holder.lv_txt.setTextColor(Color.parseColor("#6E8AFF"));
                }
                if (dataList.get(position).getName().equals("null")&&dataList.get(position).getName().equals(null)) {
                    holder.tv_content.setText("");
                } else {
//            String text_content = dataList.get(position).getContent().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getContent().split(" ")[1] + "</font>";
                    holder.tv_content.setText(Html.fromHtml("发起人:"+dataList.get(position).getName()));
                }
                if (dataList.get(position).getField5() == null &&dataList.get(position).getField5() == "null"&&dataList.get(position).getField5() == "") {
                    holder.tv_des.setText("");
                } else {
//            String text_content = dataList.get(position).getField5ield1().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getField5ield1().split(" ")[1] + "</font>";
                    holder.tv_des.setText(Html.fromHtml("审批状态："+dataList.get(position).getField5()));
                }
                break;
            case "2":
                holder.lv_jinji.setVisibility(View.GONE);

//                holder.tv_type.setText("令");
                if (dataList.get(position).getName().equals("null")&&dataList.get(position).getName().equals(null)) {
                    holder.tv_content.setText("");
                } else {
//            String text_content = dataList.get(position).getContent().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getContent().split(" ")[1] + "</font>";
                    holder.tv_content.setText(Html.fromHtml("发起人:"+dataList.get(position).getName()));
                }
                if (dataList.get(position).getField5() == null &&dataList.get(position).getField5() == "null"&&dataList.get(position).getField5() == "") {
                    holder.tv_des.setText("");
                } else {
//            String text_content = dataList.get(position).getField1().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getField1().split(" ")[1] + "</font>";
                    holder.tv_des.setText(Html.fromHtml("审批状态："+dataList.get(position).getField5()));
                }
                break;
            case "3":
                holder.lv_jinji.setVisibility(View.GONE);

//                holder.tv_type.setText("会");
                if (dataList.get(position).getName().equals("null")&&dataList.get(position).getName().equals(null)) {
                    holder.tv_content.setText("");
                } else {
//            String text_content = dataList.get(position).getContent().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getContent().split(" ")[1] + "</font>";
                    holder.tv_content.setText(Html.fromHtml("发起人:"+dataList.get(position).getName()));
                }
                if (dataList.get(position).getField4() == null &&dataList.get(position).getField4() == "null"&&dataList.get(position).getField4() == "") {
                    holder.tv_des.setText("");
                } else {
//            String text_content = dataList.get(position).getField1().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getField1().split(" ")[1] + "</font>";
                    holder.tv_des.setText(Html.fromHtml("会议地点："+dataList.get(position).getField4()));
                }
                break;
            case "4":
                holder.lv_jinji.setVisibility(View.GONE);

//                holder.tv_type.setText("报");
                if (dataList.get(position).getName()==null&&dataList.get(position).getName()=="null"&&dataList.get(position).getName()=="") {
                    holder.tv_content.setText("");
                } else {
//            String text_content = dataList.get(position).getContent().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getContent().split(" ")[1] + "</font>";
                    holder.tv_content.setText(Html.fromHtml("发起人:"+dataList.get(position).getName()));
                }
                if (dataList.get(position).getField4() == null &&dataList.get(position).getField4() == "null"&&dataList.get(position).getField4() == "") {
                    holder.tv_des.setText("");
                } else {
//            String text_content = dataList.get(position).getField1().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getField1().split(" ")[1] + "</font>";
                    holder.tv_des.setText(Html.fromHtml("截止时间："+dataList.get(position).getField4()));
                }
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
                        intent.setClass(mContext, LeaveApplyResultActivity.class);
                        intent.putExtra("id",dataList.get(position).getId());
                        intent.putExtra("source","0");
                        intent.putExtra("createByid",dataList.get(position).getCreateBy());

//                        intent.setClass(mContext, InstructDetailsActivity.class);
//                        intent.putExtra("source","0");
//                        intent.putExtra("id",dataList.get(position).getId());
                        break;
                    case "3":
                        intent.setClass(mContext, MeetingSigninActivity.class);
                        intent.putExtra("source","0");
                        intent.putExtra("id",dataList.get(position).getId());
                        break;
                    case "4":
//                        intent.setClass(mContext, ExpenseApplyResultActivity.class);
//                        intent.putExtra("id",dataList.get(position).getId());
//                        intent.putExtra("source","0");
//                        intent.putExtra("createByid",dataList.get(position).getCreateBy());

                        intent.setClass(mContext, TaskCompletedDetailsActivity.class);
                        intent.putExtra("PORID",dataList.get(position).getId());
                        intent.putExtra("source","0");
                        intent.putExtra("ID",dataList.get(position).getId());
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

