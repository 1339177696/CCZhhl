package com.hulian.oa.agency.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hulian.oa.R;
import com.hulian.oa.agency.fragment.UpcomFragment;
import com.hulian.oa.bean.AgencyNew;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.work.activity.expense.ExpenseExamineActivity;
import com.hulian.oa.work.activity.instruct.InstructBackActivity;
import com.hulian.oa.work.activity.instruct.InstructReceiverActivity;
import com.hulian.oa.work.activity.leave.LeaveExamineActivity;
import com.hulian.oa.work.activity.meeting.MeetingSigninActivity;
import com.hulian.oa.work.activity.task.l_details_activity.TaskUndoneDetailsActivity;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

//待办
public class UpcomAdapter extends RecyclerView.Adapter <UpcomAdapter.ViewHolder>{
    private Context mContext;
    private List<AgencyNew> dataList = new ArrayList<>();

    public void addAllData(List<AgencyNew>   dataList) {
       this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
       this.dataList.clear();
    }

    public UpcomAdapter(Context context) {
        mContext = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_time;
        public TextView tv_content;
        public TextView tv_des;
        public TextView tv_type;
        public TextView bt_1,bt_2;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_des= (TextView) itemView.findViewById(R.id.tv_des);
            tv_type=(TextView) itemView.findViewById(R.id.tv_type);
            bt_1=itemView.findViewById(R.id.bt_1);
            bt_2=itemView.findViewById(R.id.bt_2);
        }
    }
    @Override
    public UpcomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agency_d, parent, false);
        return new UpcomAdapter.ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(UpcomAdapter.ViewHolder holder, final int position) {
        holder.tv_title.setText(dataList.get(position).getTitile());
        holder.tv_time.setText(dataList.get(position).getTime());
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
                            params.put("userId", SPUtils.get(mContext,"userId","").toString());
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
                                            EventBus.getDefault().post(new UpcomFragment());
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
                                    Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                });
                break;
            case "1":
                holder.tv_type.setText("文");
                holder.bt_1.setVisibility(View.VISIBLE);
                holder.bt_2.setVisibility(View.VISIBLE);
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
                                        EventBus.getDefault().post(new UpcomFragment());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onFailure(OkHttpException failuer) {
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
                                        EventBus.getDefault().post(new UpcomFragment());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(OkHttpException failuer) {
                                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            case "2":
                holder.tv_type.setText("令");
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
                                            EventBus.getDefault().post(new UpcomFragment());
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
                                            EventBus.getDefault().post(new UpcomFragment());
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
            case "3":
                holder.tv_type.setText("会");
                holder.bt_1.setVisibility(View.GONE);
                holder.bt_2.setVisibility(View.VISIBLE);
                holder.bt_2.setText("签到");
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
                break;
            case "4":
                holder.tv_type.setText("报");
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
                                        EventBus.getDefault().post(new UpcomFragment());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onFailure(OkHttpException failuer) {
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
                                        EventBus.getDefault().post(new UpcomFragment());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onFailure(OkHttpException failuer) {
                                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            case "5":
                holder.tv_type.setText("假");
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
                            HttpRequest.get_Work_edit(params, new ResponseCallback() {
                                @Override
                                public void onSuccess(Object responseObj) {
                                    //需要转化为实体对象
                                    try {
                                        JSONObject result = new JSONObject(responseObj.toString());
                                        ToastHelper.showToast(mContext, result.getString("msg"));
                                        if( result.getString("code").equals("0")){
                                            EventBus.getDefault().post(new UpcomFragment());
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                @Override
                                public void onFailure(OkHttpException failuer) {
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
                        HttpRequest.get_Work_edit(params, new ResponseCallback() {
                            @Override
                            public void onSuccess(Object responseObj) {
                                //需要转化为实体对象
                                try {
                                    JSONObject result = new JSONObject(responseObj.toString());
                                    ToastHelper.showToast(mContext, result.getString("msg"));
                                    if( result.getString("code").equals("0")){
                                        EventBus.getDefault().post(new UpcomFragment());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onFailure(OkHttpException failuer) {
                                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                switch (dataList.get(position).getType()) {
                    case "0":
                        intent.setClass(mContext, TaskUndoneDetailsActivity.class);
                        intent.putExtra("PORID",dataList.get(position).getId());
                        intent.putExtra("ID",dataList.get(position).getId());
                        break;
                    case "1":
                        break;
                    case "2":
                        if(dataList.get(position).getStatus().equals("1")){
                            intent.setClass(mContext, InstructBackActivity.class);
                        }
                        else {
                            intent.setClass(mContext, InstructReceiverActivity.class);
                        }
                        intent.putExtra("id",dataList.get(position).getId());
                        intent.putExtra("content",dataList.get(position).getContent());
                        break;
                    case "3":
                        intent.setClass(mContext, MeetingSigninActivity.class);
                        intent.putExtra("id",dataList.get(position).getId());
                        break;
                    case "4":
                        intent.setClass(mContext, ExpenseExamineActivity.class);
                        intent.putExtra("id",dataList.get(position).getId());
                        intent.putExtra("createByid",dataList.get(position).getCreateBy());
                        break;
                    case "5":
                        intent.setClass(mContext, LeaveExamineActivity.class);
                        intent.putExtra("id",dataList.get(position).getId());
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
