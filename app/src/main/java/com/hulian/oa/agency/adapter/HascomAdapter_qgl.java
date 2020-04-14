package com.hulian.oa.agency.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.bean.Daiban_xin_qgl1;
import com.hulian.oa.bean.SecondMail_bean_x;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.work.activity.leave.LeaveApplyResultActivity;
import com.hulian.oa.work.activity.mail.MailParticularsActivity;
import com.hulian.oa.work.activity.meeting.MeetingSigninActivity;
import com.hulian.oa.work.activity.task.l_details_activity.TaskCompletedDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agency_q, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_title.setText(dataList.get(position).getTitle());
        if(dataList.get(position).getCreateTime()!=null) {
            holder.tv_time.setText(TimeUtils.getDateToString3(dataList.get(position).getCreateTime()));
            holder.tv_time2.setText(TimeUtils.getDateToString4(dataList.get(position).getCreateTime()));
        }

        //0任务协同，1：公文流转2:指令安排3:会议安排 4 报销 5 请假
        switch (dataList.get(position).getType()) {
            case "0":
                break;
            case "1":
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
                    holder.tv_content.setText(Html.fromHtml("发起人:"+dataList.get(position).getName()));
                }
                if (dataList.get(position).getField5() == null &&dataList.get(position).getField5() == "null"&&dataList.get(position).getField5() == "") {
                    holder.tv_des.setText("");
                } else {
                    holder.tv_des.setText(Html.fromHtml("审批状态："+dataList.get(position).getField5()));
                }
                break;
            case "2":
                holder.lv_jinji.setVisibility(View.GONE);
                if (dataList.get(position).getName().equals("null")&&dataList.get(position).getName().equals(null)) {
                    holder.tv_content.setText("");
                } else {
                    holder.tv_content.setText(Html.fromHtml("发起人:"+dataList.get(position).getName()));
                }
                if (dataList.get(position).getField5() == null &&dataList.get(position).getField5() == "null"&&dataList.get(position).getField5() == "") {
                    holder.tv_des.setText("");
                } else {
                    holder.tv_des.setText(Html.fromHtml("审批状态："+dataList.get(position).getField5()));
                }
                break;
            case "3":
                holder.lv_jinji.setVisibility(View.GONE);
                if (dataList.get(position).getName().equals("null")&&dataList.get(position).getName().equals(null)) {
                    holder.tv_content.setText("");
                } else {
                    holder.tv_content.setText(Html.fromHtml("发起人:"+dataList.get(position).getName()));
                }
                if (dataList.get(position).getField4() == null &&dataList.get(position).getField4() == "null"&&dataList.get(position).getField4() == "") {
                    holder.tv_des.setText("");
                } else {
                    holder.tv_des.setText(Html.fromHtml("会议地点："+dataList.get(position).getField4()));
                }
                break;
            case "4":
                holder.lv_jinji.setVisibility(View.GONE);
                if (dataList.get(position).getName()==null&&dataList.get(position).getName()=="null"&&dataList.get(position).getName()=="") {
                    holder.tv_content.setText("");
                } else {
                    holder.tv_content.setText(Html.fromHtml("发起人:"+dataList.get(position).getName()));
                }
                if (dataList.get(position).getField4() == null &&dataList.get(position).getField4() == "null"&&dataList.get(position).getField4() == "") {
                    holder.tv_des.setText("");
                } else {
                    holder.tv_des.setText(Html.fromHtml("截止时间："+dataList.get(position).getField4()));
                }
                break;
            case "5":
                if (TextUtils.equals("null",dataList.get(position).getName())
                       || TextUtils.isEmpty(dataList.get(position).getName())) {
                    holder.tv_content.setText("发起人:");
                } else {
                    holder.tv_content.setText(Html.fromHtml("发起人:"+dataList.get(position).getName()));
                }
                holder.tv_des.setVisibility(View.VISIBLE);
                holder.tv_des.setText("收件人："+SPUtils.get(mContext, "nickname", "").toString());
                holder.lv_jinji.setVisibility(View.GONE);
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
                        mContext.startActivity(intent);

                        break;
                    case "1":
                        break;
                    case "2":
                        intent.setClass(mContext, LeaveApplyResultActivity.class);
                        intent.putExtra("id",dataList.get(position).getId());
                        intent.putExtra("source","0");
                        intent.putExtra("createByid",dataList.get(position).getCreateBy());
                        mContext.startActivity(intent);
                        break;
                    case "3":
                        intent.setClass(mContext, MeetingSigninActivity.class);
                        intent.putExtra("source","0");
                        intent.putExtra("id",dataList.get(position).getId());
                        mContext.startActivity(intent);

                        break;
                    case "4":
                        intent.setClass(mContext, TaskCompletedDetailsActivity.class);
                        intent.putExtra("PORID",dataList.get(position).getId());
                        intent.putExtra("source","0");
                        intent.putExtra("ID",dataList.get(position).getId());
                        mContext.startActivity(intent);
                        break;
                    case "5":
                        MailPart(dataList.get(position).getId(),position);

                        break;
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // 邮件详情数据
    private SecondMail_bean_x mind_setup_jvabean;
    private void MailPart(String id,int p){
        RequestParams params = new RequestParams();
        params.put("id",id);
        params.put("createBy", SPUtils.get(mContext, "userId", "").toString());
        params.put("email", SPUtils.get(mContext, "email", "").toString());
        HttpRequest.youjian_detelis(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    mind_setup_jvabean = new SecondMail_bean_x();
                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONObject jsonObject = result.getJSONObject("data").getJSONObject("mailInfo");
                    mind_setup_jvabean.setTitle(jsonObject.getString("title"));
                    mind_setup_jvabean.setId(jsonObject.getString("messageId"));
                    mind_setup_jvabean.setSender(jsonObject.getString("sendMailPerson")+"<"+jsonObject.getString("sendMail")+">");
                    mind_setup_jvabean.setSendDate(jsonObject.getString("createTime"));
                    mind_setup_jvabean.setContent(jsonObject.getString("content").equals("null")?"":jsonObject.getString("content"));
                    if (jsonObject.getString("receiverMail") != "null"&&!jsonObject.getString("receiverMail").equals(""))
                    {
                        String [] email = jsonObject.getString("receiverMail").split(",");
                        String [] emailname = jsonObject.getString("receiverMailPerson").split(",");
                        StringBuffer stringBuffer = new StringBuffer();
                        for (int i = 0;i<emailname.length;i++){
                            stringBuffer.append(emailname[i]+"<"+email[i]+">");
                            if (i != emailname.length-1){
                                stringBuffer.append(",");
                            }
                        }
                        mind_setup_jvabean.setRecipients(stringBuffer.toString());
                    }else {
                        mind_setup_jvabean.setRecipients("");
                    }
                    if (jsonObject.getString("ccMail") != "null"&&!jsonObject.getString("ccMail").equals("")){
                        String [] ccmail = jsonObject.getString("ccMail").split(",");
                        String [] ccmailname = jsonObject.getString("ccMailPerson").split(",");
                        StringBuffer stringBuffer = new StringBuffer();
                        for (int j = 0;j<ccmailname.length;j++){
                            stringBuffer.append(ccmailname[j]+"<"+ccmail[j]+">");
                            if (j != ccmail.length-1){
                                stringBuffer.append(",");
                            }
                        }
                        mind_setup_jvabean.setCCP(stringBuffer.toString());
                    }else {
                        mind_setup_jvabean.setCCP("");

                    }
                    if (jsonObject.getString("attachment")!=null&&!jsonObject.getString("attachment").equals("null")){
                        String jo = jsonObject.getString("attachment");
                        jsonObject.put("attachment",jo);
                        JSONArray j = new JSONArray(jo);
                        List<SecondMail_bean_x.AttachBean> memberList = gson.fromJson(j.toString(), new TypeToken<List<SecondMail_bean_x.AttachBean>>() {}.getType());
                        mind_setup_jvabean.setAttach(memberList);
                        Intent intent=new Intent(mContext, MailParticularsActivity.class);
                        intent.putExtra("key",mind_setup_jvabean);
                        mContext.startActivity(intent);
                    }else {
                        mind_setup_jvabean.setAttach(null);
                        Intent intent=new Intent(mContext, MailParticularsActivity.class);
                        intent.putExtra("key",mind_setup_jvabean);
                        mContext.startActivity(intent);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Log.e("TAG", "请求失败=" + failuer.getEmsg());
            }
        });
    }
}

