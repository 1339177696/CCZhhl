package com.hulian.oa.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.JournalismComments;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.news.activity.NewsActivityInfo;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.ToastHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsCommentdapter extends RecyclerView.Adapter <NewsCommentdapter.ViewHolder>{
    private Context mContext;
    private List<JournalismComments> dataList = new ArrayList<>();
    public void addAllData(List<JournalismComments> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public NewsCommentdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView tv_content;
        public TextView time;
        public  TextView tv_dianzanCount;
        public ImageView iv_dianzan;
        public  TextView iv_head;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_name);
            tv_content=(TextView) itemView.findViewById(R.id.tv_content);
            time = (TextView) itemView.findViewById(R.id.time);
            tv_dianzanCount=(TextView) itemView.findViewById(R.id.tv_dianzanCount);
            iv_dianzan=itemView.findViewById(R.id.iv_dianzan);
            iv_head=itemView.findViewById(R.id.iv_head);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(dataList.get(position).getCommentName());
        holder.tv_content.setText(dataList.get(position).getCommentContent());
        holder.time.setText(dataList.get(position).getCreateTime1());
        holder.tv_dianzanCount.setText(dataList.get(position).getCount());
        holder.iv_head.setText(dataList.get(position).getCommentName().substring(0,2)+"");

        holder.iv_dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params1 = new RequestParams();
                params1.put("commentId",dataList.get(position).getCommentId());
                params1.put("userId", SPUtils.get(mContext, "userId", "").toString());
                HttpRequest.postDianzanCommApi(params1, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        try {
                            JSONObject result = new JSONObject(responseObj.toString());
                            ToastHelper.showToast(mContext, result.getString("msg"));
                            if("0".equals(result.getString("code"))){
                                holder.iv_dianzan.setBackgroundResource(R.mipmap.ic_dianzan_check);
                                holder.iv_dianzan.setClickable(false);
                                holder.tv_dianzanCount.setText(Integer.parseInt(dataList.get(position).getCount())+1+"");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(OkHttpException failuer) {
                    }
                });
            }
        });
        if("0".equals(dataList.get(position).getIsPraise())){
            holder.iv_dianzan.setBackgroundResource(R.mipmap.ic_dianzan);
            holder.iv_dianzan.setClickable(true);
        }else {
            holder.iv_dianzan.setBackgroundResource(R.mipmap.ic_dianzan_check);
            holder.iv_dianzan.setClickable(false);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
