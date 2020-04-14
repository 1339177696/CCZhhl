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
import com.hulian.oa.bean.Daiban_xin_qgl1;
import com.hulian.oa.bean.SecondMail_bean_x;
import com.hulian.oa.iac.activity.BacklogActivity;
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
import com.hulian.oa.work.file.admin.activity.leave.LeaveExamineActivity;
import com.hulian.oa.work.file.admin.activity.mail.MailParticularsActivity;
import com.hulian.oa.work.file.admin.activity.meeting.MeetingSigninActivity;
import com.hulian.oa.work.file.admin.activity.task.l_details_activity.TaskUndoneDetailsActivity;
import com.hulian.oa.xcpsactivity.XCDetelisActivity;
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
public class ShenqingAdapter_qgl extends RecyclerView.Adapter <ShenqingAdapter_qgl.ViewHolder>{
    private Context mContext;
    private List<Daiban_xin_qgl1.DataBean> dataList = new ArrayList<>();

    public void addAllData(List<Daiban_xin_qgl1.DataBean>   dataList) {
       this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
       this.dataList.clear();
    }

    public ShenqingAdapter_qgl(Context context) {
        mContext = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_content;
        public ImageView im_src;
        public TextView tv_des;
        public TextView bt_1,bt_2;
        // qgl
        public TextView tv_time_qgl;
        public TextView tv_time_qgl2;
        public LinearLayout lv_jinji;
        public ImageView lv_img;
        public TextView lv_txt;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_des= (TextView) itemView.findViewById(R.id.tv_des);
            im_src= (ImageView) itemView.findViewById(R.id.im_src);
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
    public ShenqingAdapter_qgl.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shenqing_d, parent, false);
        return new ShenqingAdapter_qgl.ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ShenqingAdapter_qgl.ViewHolder holder, final int position) {
        if (dataList.get(position).getId().equals("0")){
            holder.tv_time_qgl.setText("2020/03");
            holder.tv_time_qgl2.setText("11");
            holder.tv_title.setText("特设设备生产许可（锅炉制造）");
            holder.tv_content.setText("申请单位：长春吉大正元信息安全技术有限公司");
            holder.tv_des.setText("申请时间：2020-03-11 16:39:42 ");
            holder.im_src.setImageResource(R.mipmap.xianchangpingshen_item);
        }else if (dataList.get(position).getId().equals("1")){
            holder.tv_time_qgl.setText("2020/03");
            holder.tv_time_qgl2.setText("12");
            holder.tv_title.setText("特设设备生产许可（压力容器）");
            holder.tv_content.setText("申请单位：长春吉大正元信息安全技术有限公司");
            holder.tv_des.setText("申请时间：2020-03-12 10:27:11 ");
            holder.im_src.setImageResource(R.mipmap.chushen_item);
        }else if (dataList.get(position).getId().equals("2")){
            holder.tv_time_qgl.setText("2020/03");
            holder.tv_time_qgl2.setText("13");
            holder.tv_title.setText("特设设备生产许可（安全附件）");
            holder.tv_content.setText("申请单位：长春吉大正元信息安全技术有限公司");
            holder.tv_des.setText("申请时间：2020-03-13 15:33:23 ");
            holder.im_src.setImageResource(R.mipmap.fushen_item);
        }else {
            holder.tv_time_qgl.setText("2020/03");
            holder.tv_time_qgl2.setText("14");
            holder.tv_title.setText("特设设备生产许可（起重机制造）");
            holder.tv_content.setText("申请单位：长春吉大正元信息安全技术有限公司");
            holder.tv_des.setText("申请时间：2020-03-14 11:42:13 ");
            holder.im_src.setImageResource(R.mipmap.xukequeren_item);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position==0){
                    Intent intent=new Intent();
                    intent.setClass(mContext, BacklogActivity.class);
                    mContext.startActivity(intent);
                }else{
                Intent intent=new Intent();
                intent.setClass(mContext, XCDetelisActivity.class);
                intent.putExtra("title",position);
                mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
