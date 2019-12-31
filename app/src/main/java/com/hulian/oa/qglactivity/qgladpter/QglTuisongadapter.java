package com.hulian.oa.qglactivity.qgladpter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.BuildConfig;
import com.hulian.oa.R;
import com.hulian.oa.bean.SecondMail_bean_x;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.pad.PAD_gongwen_SP;
import com.hulian.oa.qglactivity.qglbean.MeBean;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.work.file.admin.activity.document.DocumentLotusActivity;
import com.hulian.oa.work.file.admin.activity.document.DocumentLotusInfoActivity;
import com.hulian.oa.work.file.admin.activity.leave.LeaveApplyResultActivity;
import com.hulian.oa.work.file.admin.activity.leave.LeaveExamineActivity;
import com.hulian.oa.work.file.admin.activity.mail.MailParticularsActivity;
import com.hulian.oa.work.file.admin.activity.meeting.MeetingSigninActivity;
import com.hulian.oa.work.file.admin.activity.notice.NoticeParticularsActivity;
import com.hulian.oa.work.file.admin.activity.task.l_details_activity.TaskLaunchDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QglTuisongadapter extends RecyclerView.Adapter <QglTuisongadapter.ViewHolder>{
    private Context mContext;
    private List<MeBean> dataList = new ArrayList<>();


    public void addAllData(List<MeBean> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public QglTuisongadapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_remind;
        private TextView tv_title;
        private TextView tv_time;
        private TextView tv_context;
        private TextView tv_qgl_zhuangtai;
        private TextView tv_qgl_bumen;
        private TextView tv_yuedu;
        private ImageView im_yuedu;
        private TextView tv_qgl_faqiren;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_remind = itemView.findViewById(R.id.iv_remind);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_context = itemView.findViewById(R.id.tv_context);
            tv_qgl_zhuangtai = itemView.findViewById(R.id.tv_qgl_zhuangtai);
            tv_qgl_bumen = itemView.findViewById(R.id.tv_qgl_bumen);
            tv_yuedu = itemView.findViewById(R.id.tv_yuedu);
            im_yuedu = itemView.findViewById(R.id.im_yuedu);
            tv_qgl_faqiren = itemView.findViewById(R.id.tv_qgl_faqiren);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.qgltuisongadapter_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (dataList.get(position).getStatus().equals("0")){
            holder.iv_remind.setVisibility(View.VISIBLE);
        }else {
            holder.iv_remind.setVisibility(View.GONE);
        }
        holder.tv_time.setText(dataList.get(position).getCreateTime());
        if (dataList.get(position).getType().equals("1")){
//            邮件
            holder.tv_title.setText("发件人:"+"   "+dataList.get(position).getSendPerson());
            holder.tv_context.setText("邮件主题:"+"   "+dataList.get(position).getTitle());
            holder.tv_qgl_zhuangtai.setVisibility(View.GONE);
            holder.tv_qgl_bumen.setVisibility(View.GONE);
            holder.tv_qgl_faqiren.setVisibility(View.GONE);
            holder.tv_yuedu.setVisibility(View.VISIBLE);
            holder.im_yuedu.setVisibility(View.VISIBLE);
        }else if (dataList.get(position).getType().equals("2")){
//            公文
            if (dataList.get(position).getMsgType() == 0){
                holder.tv_qgl_zhuangtai.setVisibility(View.GONE);
                holder.tv_qgl_bumen.setText("发起人:"+"   "+dataList.get(position).getCreatePerson());
            }else {
                holder.tv_qgl_zhuangtai.setVisibility(View.VISIBLE);
                holder.tv_qgl_bumen.setText("审批人:"+"   "+dataList.get(position).getApprovalPerson());
                holder.tv_qgl_zhuangtai.setText("状态:"+"   "+dataList.get(position).getApprovalStatus());
            }
            holder.tv_title.setText("公文标题:"+"   "+dataList.get(position).getTitle());
            holder.tv_context.setText("公文类型:"+"   "+dataList.get(position).getDocumentType());
            holder.tv_qgl_bumen.setVisibility(View.VISIBLE);
            holder.tv_yuedu.setVisibility(View.VISIBLE);
            holder.im_yuedu.setVisibility(View.VISIBLE);
            holder.tv_qgl_faqiren.setVisibility(View.GONE);
        }else if (dataList.get(position).getType().equals("3")){
//            请假
            if (dataList.get(position).getMsgType() == 0){
                holder.tv_qgl_zhuangtai.setVisibility(View.GONE);
                holder.tv_qgl_faqiren.setText("发起人:"+"   "+dataList.get(position).getCreatePerson());
            }else {
                holder.tv_qgl_zhuangtai.setVisibility(View.VISIBLE);
                holder.tv_qgl_faqiren.setText("审批人:"+"   "+dataList.get(position).getApprovalPerson());
                holder.tv_qgl_zhuangtai.setText("状态:"+"   "+dataList.get(position).getApprovalStatus());
            }

            holder.tv_title.setText("请假事由:"+"   "+dataList.get(position).getCause());
            holder.tv_context.setText("开始时间:"+"   "+dataList.get(position).getStartDate());
            holder.tv_qgl_bumen.setText("结束时间:"+"   "+dataList.get(position).getEndDate());
            holder.tv_qgl_bumen.setVisibility(View.VISIBLE);
            holder.tv_yuedu.setVisibility(View.VISIBLE);
            holder.im_yuedu.setVisibility(View.VISIBLE);
            holder.tv_qgl_faqiren.setVisibility(View.VISIBLE);
        }else if (dataList.get(position).getType().equals("4")){
//            会议
            holder.tv_title.setText("会议标题:"+"   "+dataList.get(position).getTitle());
            holder.tv_context.setText("会议地点:"+"   "+dataList.get(position).getMeetingLocation());
            holder.tv_qgl_bumen.setText("会议开始时间:"+"   "+dataList.get(position).getStartDate());
            holder.tv_qgl_zhuangtai.setVisibility(View.GONE);
            holder.tv_qgl_faqiren.setVisibility(View.GONE);
            holder.tv_qgl_bumen.setVisibility(View.VISIBLE);
            holder.tv_yuedu.setVisibility(View.VISIBLE);
            holder.im_yuedu.setVisibility(View.VISIBLE);
        }else if (dataList.get(position).getType().equals("5")){
//            公告
            holder.tv_title.setText("公告标题:"+"   "+dataList.get(position).getTitle());
            holder.tv_context.setText("公告内容:"+"   "+dataList.get(position).getContent());
            holder.tv_qgl_bumen.setText("发布部门:"+"   "+dataList.get(position).getReleaseDept());
            holder.tv_qgl_zhuangtai.setVisibility(View.GONE);
            holder.tv_qgl_faqiren.setVisibility(View.GONE);
            holder.tv_qgl_bumen.setVisibility(View.VISIBLE);
            holder.tv_yuedu.setVisibility(View.VISIBLE);
            holder.im_yuedu.setVisibility(View.VISIBLE);
        }else if (dataList.get(position).getType().equals("6")){
//            日程
            holder.tv_title.setText("日程内容:"+"   "+dataList.get(position).getContent());
            holder.tv_context.setText("开始时间:"+"   "+dataList.get(position).getStartDate());
            holder.tv_qgl_bumen.setText("结束时间:"+"   "+dataList.get(position).getEndDate());
            holder.tv_qgl_zhuangtai.setVisibility(View.GONE);
            holder.tv_qgl_faqiren.setVisibility(View.GONE);
            holder.tv_qgl_bumen.setVisibility(View.VISIBLE);
            holder.tv_yuedu.setVisibility(View.GONE);
            holder.im_yuedu.setVisibility(View.GONE);
            holder.iv_remind.setVisibility(View.GONE);
        }else if (dataList.get(position).getType().equals("7")){
//            任务
            if (dataList.get(position).getMsgType() == 0){
                if(dataList.get(position).getApprovalStatus()!=null){
                    holder.tv_qgl_zhuangtai.setVisibility(View.VISIBLE);
                    holder.tv_qgl_zhuangtai.setText("状态:"+"   "+dataList.get(position).getApprovalStatus());
                }else {
                    holder.tv_qgl_zhuangtai.setVisibility(View.GONE);
                }
                holder.tv_qgl_bumen.setText("发起人:"+"   "+dataList.get(position).getCreatePerson());
            }else {
                holder.tv_qgl_bumen.setText("执行人:"+"   "+dataList.get(position).getApprovalPerson());
                holder.tv_qgl_zhuangtai.setVisibility(View.VISIBLE);
                holder.tv_qgl_zhuangtai.setText("状态:"+"   "+dataList.get(position).getApprovalStatus());
            }
            holder.tv_title.setText("任务标题:"+"   "+dataList.get(position).getTitle());
            holder.tv_context.setText("截止时间:"+"   "+dataList.get(position).getEndDate());
            holder.tv_qgl_bumen.setVisibility(View.VISIBLE);
            holder.tv_yuedu.setVisibility(View.VISIBLE);
            holder.im_yuedu.setVisibility(View.VISIBLE);
            holder.tv_qgl_faqiren.setVisibility(View.GONE);
        }else if (dataList.get(position).getType().equals("8")){
            holder.tv_qgl_zhuangtai.setVisibility(View.VISIBLE);
            holder.tv_qgl_bumen.setVisibility(View.VISIBLE);
            holder.tv_yuedu.setVisibility(View.VISIBLE);
            holder.im_yuedu.setVisibility(View.VISIBLE);
            holder.tv_qgl_faqiren.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dataList.get(position).getType().equals("6")){
                    getStatus(dataList.get(position).getId());
                }
                if (dataList.get(position).getType().equals("1")){
//                    邮件
                    MailPart(dataList.get(position).getRelationId(),position);
                }else if (dataList.get(position).getType().equals("2")){
//                    公文
                    if (dataList.get(position).getMsgType() == 0){
                        Intent intent = new Intent();
                        intent.putExtra("offId", dataList.get(position).getRelationId());
                        if (SPUtils.get(mContext, "isLead", "").equals("0")) {
                            if (BuildConfig.IsPad) {
                                intent.setClass(mContext, PAD_gongwen_SP.class);
                            } else {
                                intent.setClass(mContext, DocumentLotusActivity.class);
                            }
                        } else {
                            intent.setClass(mContext, DocumentLotusInfoActivity.class);
                        }
                        mContext.startActivity(intent);
                    }else {
                        Intent intent=new Intent();
                        intent.putExtra("offId",dataList.get(position).getRelationId());
                        intent.setClass(mContext, DocumentLotusInfoActivity.class);
                        mContext.startActivity(intent);
                    }

                }else if (dataList.get(position).getType().equals("3")){
//                    请假
                        if (dataList.get(position).getMsgType() == 0){
                            //审批
                            Intent intent=new Intent();
                            intent.putExtra("id",dataList.get(position).getRelationId());
                            intent.putExtra("createByid", SPUtils.get(mContext, "userId", "").toString());
                            intent.setClass(mContext, LeaveExamineActivity.class);
                            mContext.startActivity(intent);
                        }else {
                            Intent intent=new Intent();
                            intent.putExtra("id",dataList.get(position).getRelationId());
                            intent.setClass(mContext, LeaveApplyResultActivity.class);
                            mContext.startActivity(intent);
                            //查看
                        }
                }else if (dataList.get(position).getType().equals("4")){
//                    会议
                    Intent intent = new Intent(mContext, MeetingSigninActivity.class);
                    intent.putExtra("id",dataList.get(position).getRelationId());
                    mContext.startActivity(intent);
                }else if (dataList.get(position).getType().equals("5")){
//                    公告
                    Intent intent=new Intent(mContext, NoticeParticularsActivity.class);
                    intent.putExtra("noticeId",dataList.get(position).getRelationId());
                    intent.putExtra("isCollect",dataList.get(position).getCollectionStatus());
                    mContext.startActivity(intent);
                }else if (dataList.get(position).getType().equals("6")){
                //日程没有详情

                }else if (dataList.get(position).getType().equals("7")){
//                    任务
                    Intent intent = new Intent();
                    intent.putExtra("PORID","1");
                    intent.putExtra("ID",dataList.get(position).getRelationId());
                    intent.setClass(mContext, TaskLaunchDetailsActivity.class);
                    mContext.startActivity(intent);

                }else if (dataList.get(position).getType().equals("8")){
//                    报销
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    //修改已读状态
    private void getStatus(String id){
        RequestParams params = new RequestParams();
        params.put("id",id);
        params.put("userId", SPUtils.get(mContext, "userId", "").toString());
        HttpRequest.postNotice_staus(params, new ResponseCallback(){
            @Override
            public void onSuccess(Object responseObj) {
                Log.e("成功",responseObj.toString());
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }

    private SecondMail_bean_x mind_setup_jvabean;
    // 邮件详情数据
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
