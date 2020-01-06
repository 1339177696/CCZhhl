package com.hulian.oa.agency.l_adapter;

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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.BuildConfig;
import com.hulian.oa.R;
import com.hulian.oa.agency.l_fragment.L_UpcomFragment;
import com.hulian.oa.bean.AgencyNew;
import com.hulian.oa.bean.Daiban_xin_qgl1;
import com.hulian.oa.bean.SecondMail_bean_x;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.pad.PAD_gongwen_SP;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.work.file.admin.activity.document.DocumentLotusActivity;
import com.hulian.oa.work.file.admin.activity.expense.ExpenseExamineActivity;
import com.hulian.oa.work.file.admin.activity.instruct.InstructBackActivity;
import com.hulian.oa.work.file.admin.activity.instruct.InstructReceiverActivity;
import com.hulian.oa.work.file.admin.activity.leave.LeaveExamineActivity;
import com.hulian.oa.work.file.admin.activity.mail.MailParticularsActivity;
import com.hulian.oa.work.file.admin.activity.meeting.MeetingSigninActivity;
import com.hulian.oa.work.file.admin.activity.task.l_details_activity.TaskUndoneDetailsActivity;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

//待办
public class UpcomAdapter_qgl extends RecyclerView.Adapter <UpcomAdapter_qgl.ViewHolder>{
    private Context mContext;
    private List<Daiban_xin_qgl1.DataBean> dataList = new ArrayList<>();

    public void addAllData(List<Daiban_xin_qgl1.DataBean>   dataList) {
       this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
       this.dataList.clear();
    }

    public UpcomAdapter_qgl(Context context) {
        mContext = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
      //  public ImageView iv_image;
        public TextView tv_title;
//        public TextView tv_time;
        public TextView tv_content;
        public TextView tv_des;

//        public TextView tv_type;
        public TextView bt_1,bt_2;
        // qgl
        public TextView tv_time_qgl;
        public TextView tv_time_qgl2;
        public LinearLayout lv_jinji;
        public ImageView lv_img;
        public TextView lv_txt;
        public ViewHolder(View itemView) {
            super(itemView);
      //      iv_image=itemView.findViewById(R.id.iv_image);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
//            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_des= (TextView) itemView.findViewById(R.id.tv_des);
//            tv_type=(TextView) itemView.findViewById(R.id.tv_type);
            bt_1=itemView.findViewById(R.id.bt_1);
            bt_2=itemView.findViewById(R.id.bt_2);
            //qgl
            tv_time_qgl=itemView.findViewById(R.id.tv_time_qgl);
            tv_time_qgl2=itemView.findViewById(R.id.tv_time_qgl2);
            lv_jinji=itemView.findViewById(R.id.lv_jinji);
            lv_img=itemView.findViewById(R.id.lv_img);
            lv_txt=itemView.findViewById(R.id.lv_txt);
        }
    }
    @Override
    public UpcomAdapter_qgl.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agency_d, parent, false);
        return new UpcomAdapter_qgl.ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(UpcomAdapter_qgl.ViewHolder holder, final int position) {
        holder.tv_title.setText(dataList.get(position).getTitle());
        holder.tv_time_qgl.setText(TimeUtils.getDateToString3(dataList.get(position).getCreateTime()));
        holder.tv_time_qgl2.setText(TimeUtils.getDateToString4(dataList.get(position).getCreateTime()));
//        if (dataList.get(position).getCreateBy().equals("null")&&dataList.get(position).getCreateBy().equals(null)) {
//            holder.tv_content.setText("");
//        } else {
////            String text_content = dataList.get(position).getContent().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getContent().split(" ")[1] + "</font>";
//            holder.tv_content.setText(Html.fromHtml("发起人:"+dataList.get(position).getCreateBy()));
//        }
//        if (dataList.get(position).getField1() == null &&dataList.get(position).getField1() == "null"&&dataList.get(position).getField1() == "") {
//            holder.tv_des.setText("");
//        } else {
////            String text_content = dataList.get(position).getField1().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getField1().split(" ")[1] + "</font>";
//            holder.tv_des.setText(Html.fromHtml("审批人："+dataList.get(position).getField1()));
//        }

        //0任务协同，1：公文流转2:请假3:会议安排 4 报销 5 指令安排
        switch (dataList.get(position).getType()) {
            case "0":
                holder.lv_jinji.setVisibility(View.GONE);
//                holder.tv_type.setText("报");
                holder.bt_1.setVisibility(View.VISIBLE);
                holder.bt_2.setVisibility(View.VISIBLE);
                holder.bt_1.setText("同意");
                holder.bt_1.setBackgroundResource(R.drawable.bt_8background);
                holder.bt_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestParams params = new RequestParams();
                        params.put("id", dataList.get(position).getId());
                        params.put("state", "1");
                        HttpRequest.get_WorkExpense_edit(params, new ResponseCallback() {
                            @Override
                            public void onSuccess(Object responseObj) {
                                //需要转化为实体对象
                                try {
                                    JSONObject result = new JSONObject(responseObj.toString());
                                    ToastHelper.showToast(mContext, result.getString("msg"));
                                    if( result.getString("code").equals("0")){
                                        EventBus.getDefault().post(new L_UpcomFragment());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onFailure(OkHttpException failuer) {
                                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                holder.bt_2.setText("驳回");
                holder.bt_2.setBackgroundResource(R.drawable.bt_7background);
                holder.bt_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestParams params = new RequestParams();
                        params.put("id", dataList.get(position).getId());
                        params.put("state", "2");
                        HttpRequest.get_WorkExpense_edit(params, new ResponseCallback() {
                            @Override
                            public void onSuccess(Object responseObj) {
                                //需要转化为实体对象
                                try {
                                    JSONObject result = new JSONObject(responseObj.toString());
                                    ToastHelper.showToast(mContext, result.getString("msg"));
                                    if( result.getString("code").equals("0")){
                                        EventBus.getDefault().post(new L_UpcomFragment());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onFailure(OkHttpException failuer) {
                                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            case "1":

                if (dataList.get(position).getName().equals("null")&&dataList.get(position).getName().equals(null)) {
                    holder.tv_content.setText("");
                } else {
//            String text_content = dataList.get(position).getContent().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getContent().split(" ")[1] + "</font>";
                    holder.tv_content.setText(Html.fromHtml("发起人:"+dataList.get(position).getName()));
                }
                if (dataList.get(position).getField1() == null &&dataList.get(position).getField1() == "null"&&dataList.get(position).getField1() == "") {
                    holder.tv_des.setText("");
                } else {
//               String text_content = dataList.get(position).getField1().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getField1().split(" ")[1] + "</font>";
                    holder.tv_des.setText(Html.fromHtml("审批人："+dataList.get(position).getField1()));
                }
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

//                holder.tv_type.setText("文");
                holder.bt_1.setVisibility(View.GONE);
                holder.bt_2.setVisibility(View.GONE);
                holder.bt_1.setText("同意");
                holder.bt_1.setBackgroundResource(R.drawable.bt_8background);
                holder.bt_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String content = "";
                        RequestParams params = new RequestParams();
                        params.put("proid", dataList.get(position).getId());
                        params.put("aaproverId", SPUtils.get(mContext, "userId", "").toString());
                        params.put("approverOpinion", content);
                        params.put("state", "1");
                        HttpRequest.postDocumentApproveApi(params, new ResponseCallback() {
                            @Override
                            public void onSuccess(Object responseObj) {
                                try {
                                    JSONObject result = new JSONObject(responseObj.toString());
                                    ToastHelper.showToast(mContext, result.getString("msg"));
                                    if (result.getString("code").equals("0")) {
                                        EventBus.getDefault().post(new L_UpcomFragment());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onFailure(OkHttpException failuer) {
                                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                holder.bt_2.setText("驳回");
                holder.bt_2.setBackgroundResource(R.drawable.bt_7background);
                holder.bt_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String content2 = "";
                        RequestParams params1 = new RequestParams();
                        params1.put("proid", dataList.get(position).getId());
                        params1.put("aaproverId", SPUtils.get(mContext, "userId", "").toString());
                        params1.put("approverOpinion", content2);
                        params1.put("state", "2");
                        HttpRequest.postDocumentApproveApi(params1, new ResponseCallback() {
                            @Override
                            public void onSuccess(Object responseObj) {
                                try {
                                    JSONObject result = new JSONObject(responseObj.toString());
                                    ToastHelper.showToast(mContext, result.getString("msg"));
                                    if (result.getString("code").equals("0")) {
                                        EventBus.getDefault().post(new L_UpcomFragment());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(OkHttpException failuer) {
                                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            case "2":
                if (dataList.get(position).getName().equals("null")&&dataList.get(position).getName().equals(null)) {
                    holder.tv_content.setText("");
                } else {
//            String text_content = dataList.get(position).getContent().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getContent().split(" ")[1] + "</font>";
                    holder.tv_content.setText(Html.fromHtml("发起人:"+dataList.get(position).getName()));
                }
                if (dataList.get(position).getField1() == null &&dataList.get(position).getField1() == "null"&&dataList.get(position).getField1() == "") {
                    holder.tv_des.setText("");
                } else {
//            String text_content = dataList.get(position).getField1().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getField1().split(" ")[1] + "</font>";
                    holder.tv_des.setText(Html.fromHtml("审批人："+dataList.get(position).getField1()));
                }
                holder.lv_jinji.setVisibility(View.GONE);
//                holder.tv_type.setText("假");
                holder.bt_1.setVisibility(View.VISIBLE);
                holder.bt_2.setVisibility(View.VISIBLE);
                holder.bt_1.setText("同意");
                holder.bt_1.setBackgroundResource(R.drawable.bt_8background);
                holder.bt_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestParams params = new RequestParams();
                        params.put("id", dataList.get(position).getId());
                        params.put("state", "1");
                        params.put("ids", dataList.get(position).getId());
                        params.put("nowApprove", SPUtils.get(mContext,"userId","").toString());
                        params.put("nowApproveName", SPUtils.get(mContext,"nickname","").toString());
                        HttpRequest.get_Work_edit(params, new ResponseCallback() {
                            @Override
                            public void onSuccess(Object responseObj) {
                                //需要转化为实体对象
                                try {
                                    JSONObject result = new JSONObject(responseObj.toString());
                                    ToastHelper.showToast(mContext, result.getString("msg"));
                                    if( result.getString("code").equals("0")){
                                        EventBus.getDefault().post(new L_UpcomFragment());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onFailure(OkHttpException failuer) {
                                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                holder.bt_2.setText("驳回");
                holder.bt_2.setBackgroundResource(R.drawable.bt_7background);
                holder.bt_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestParams params = new RequestParams();
                        params.put("id", dataList.get(position).getId());
                        params.put("state", "2");
                        params.put("ids", dataList.get(position).getId());
                        params.put("nowApprove", SPUtils.get(mContext,"userId","").toString());
                        params.put("nowApproveName", SPUtils.get(mContext,"nickname","").toString());
                        HttpRequest.get_Work_edit(params, new ResponseCallback() {
                            @Override
                            public void onSuccess(Object responseObj) {
                                //需要转化为实体对象
                                try {
                                    JSONObject result = new JSONObject(responseObj.toString());
                                    ToastHelper.showToast(mContext, result.getString("msg"));
                                    if( result.getString("code").equals("0")){
                                        EventBus.getDefault().post(new L_UpcomFragment());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onFailure(OkHttpException failuer) {
                                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            case "3":
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

            holder.lv_jinji.setVisibility(View.GONE);

                //              holder.tv_type.setText("会");
                holder.bt_1.setVisibility(View.GONE);
                holder.bt_2.setVisibility(View.VISIBLE);
                holder.bt_2.setText(dataList.get(position).getField5());
                holder.bt_2.setBackgroundResource(R.drawable.bt_6background);
                holder.bt_1.setBackgroundResource(0);
                holder.bt_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, CaptureActivity.class);
                        /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
                         * 也可以不传这个参数
                         * 不传的话  默认都为默认不震动  其他都为true
                         * */

                        ZxingConfig config = new ZxingConfig();
                        config.setShowbottomLayout(false);//底部布局（包括闪光灯和相册）
                        //config.setPlayBeep(true);//是否播放提示音
                        //config.setShake(true);//是否震动
                        //config.setShowAlbum(true);//是否显示相册
                        //config.setShowFlashLight(true);//是否显示闪光灯
                        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                        // mContext.startActivityForResult(intent, REQUEST_CODE_SCAN);????
                        mContext.startActivity(intent);
                    }
                });
//
                break;
            case "4":
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
                    holder.tv_des.setText(Html.fromHtml("截止时间："+dataList.get(position).getField4()));
                }
 //             holder.tv_type.setText("协");
                holder.lv_jinji.setVisibility(View.GONE);
                holder.bt_1.setVisibility(View.GONE);
                holder.bt_2.setVisibility(View.VISIBLE);
                holder.bt_2.setText("完成");
                holder.bt_2.setBackgroundResource(R.drawable.bt_8background);
                holder.bt_1.setBackgroundResource(0);
                holder.bt_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //发送完成接口
                        RequestParams params = new RequestParams();
                        params.put("id",dataList.get(position).getId());
                        //加一个UserID
                        params.put("userId",SPUtils.get(mContext,"userId","").toString());
                        params.put("completion","1");
                        HttpRequest.post_CoordinationRelease_deit(params, new ResponseCallback() {
                            @Override
                            public void onSuccess(Object responseObj) {
                                //需要转化为实体对象
                                try {
                                    JSONObject result = new JSONObject(responseObj.toString());
                                    JSONObject obj = new JSONObject(result.toString());
                                    String code = obj.getString("code");
                                    String msg = obj.getString("msg");
                                    if (code.equals("0"))
                                    {
                                        EventBus.getDefault().post(new L_UpcomFragment());
                                        //   EventBus.getDefault().post(new L_HascomFragment());
                                        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(OkHttpException failuer) {
                                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                break;
            case "5":
                if (dataList.get(position).getName().equals("null")&&dataList.get(position).getName().equals(null)) {
                    holder.tv_content.setText("");
                } else {
//            String text_content = dataList.get(position).getContent().split(" ")[0] + "  <font color='#2B8CFA'>" + dataList.get(position).getContent().split(" ")[1] + "</font>";
                    holder.tv_content.setText(Html.fromHtml("发起人:"+dataList.get(position).getName()));
                }
                holder.tv_des.setVisibility(View.VISIBLE);
                holder.tv_des.setText("收件人："+SPUtils.get(mContext, "nickname", "").toString());
                holder.lv_jinji.setVisibility(View.GONE);
                break;
            case "6":

                //                holder.tv_type.setText("令");
                holder.bt_1.setVisibility(View.GONE);
                holder.bt_2.setVisibility(View.VISIBLE);
                holder.bt_2.setBackgroundResource(R.drawable.bt_8background);
                holder.bt_1.setBackgroundResource(0);
                if(dataList.get(position).getStatus().equals("0")) {
                    holder.bt_2.setText("接收");
                    holder.bt_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RequestParams params = new RequestParams();
                            params.put("type", "0");
                            params.put("id", dataList.get(position).getId());
                            HttpRequest.postInstructOperation(params, new ResponseCallback() {
                                @Override
                                public void onSuccess(Object responseObj) {
                                    Gson gson = new GsonBuilder().serializeNulls().create();
                                    try {
                                        JSONObject result = new JSONObject(responseObj.toString());
                                        String code = result.getString("code");
                                        if (TextUtils.equals(code, "0")) {
                                            EventBus.getDefault().post(new L_UpcomFragment());
                                        }
                                        ToastHelper.showToast(mContext, result.getString("msg"));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(OkHttpException failuer) {

                                }
                            });
                        }
                    });
                }
                else {
                    holder.bt_2.setText("反馈");
                    holder.bt_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RequestParams params = new RequestParams();
                            params.put("type","1");
                            params.put("id",dataList.get(position).getId());
                            params.put("feedback","");
                            HttpRequest.postInstructOperation(params, new ResponseCallback() {
                                @Override
                                public void onSuccess(Object responseObj) {
                                    try {
                                        JSONObject result = new JSONObject(responseObj.toString());
                                        String code = result.getString("code");
                                        if (TextUtils.equals(code,"0")){
                                            EventBus.getDefault().post(new L_UpcomFragment());
                                        }
                                        ToastHelper.showToast(mContext,result.getString("msg"));
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                                @Override
                                public void onFailure(OkHttpException failuer) {
                                }
                            });
                        }
                    });
                }
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                switch (dataList.get(position).getType()) {
                    case "0":
                        intent.setClass(mContext, ExpenseExamineActivity.class);
                        intent.putExtra("id",dataList.get(position).getId());
                        intent.putExtra("createByid",dataList.get(position).getCreateBy());
                        mContext.startActivity(intent);
                        break;
                    case "1":
                        if(BuildConfig.IsPad) {
                            intent.setClass(mContext, PAD_gongwen_SP.class);
                        }
                        else {
                            intent.setClass(mContext,DocumentLotusActivity.class);
                        }
                        intent.putExtra("offId",dataList.get(position).getId());
                        mContext.startActivity(intent);
                        break;
                    case "2":
                        intent.setClass(mContext, LeaveExamineActivity.class);
                        intent.putExtra("id",dataList.get(position).getId());
                        intent.putExtra("createByid",dataList.get(position).getCreateBy());
                        mContext.startActivity(intent);
                        break;
                    case "3":
                        intent.setClass(mContext, MeetingSigninActivity.class);
                        intent.putExtra("id",dataList.get(position).getId());
                        mContext.startActivity(intent);
                        break;
                    case "4":
                        intent.setClass(mContext, TaskUndoneDetailsActivity.class);
                        intent.putExtra("PORID",dataList.get(position).getId());
                        intent.putExtra("ID",dataList.get(position).getId());
                        mContext.startActivity(intent);
                        break;
                    case "5":
                        MailPart(dataList.get(position).getId(),position);
                        break;
//                    case "6":
//                        if(dataList.get(position).getStatus().equals("1")){
//                            intent.setClass(mContext, InstructBackActivity.class);
//                        }
//                        else {
//                            intent.setClass(mContext, InstructReceiverActivity.class);
//                        }
////                        intent.putExtra("id",dataList.get(position).getId());
////                        intent.putExtra("content",dataList.get(position).getContent());
//                        break;
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
