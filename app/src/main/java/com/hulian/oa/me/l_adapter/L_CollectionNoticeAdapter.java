package com.hulian.oa.me.l_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hulian.oa.R;
import com.hulian.oa.bean.Bean_x;
import com.hulian.oa.bean.Notice;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.news.activity.NewsActivityInfo_x;
import com.hulian.oa.views.AlertDialog;
import com.hulian.oa.work.file.admin.activity.notice.NoticeParticularsActivity;
import com.hulian.oa.work.file.admin.activity.notice.adapter.NoticeParticularsActivity_x;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class L_CollectionNoticeAdapter extends RecyclerView.Adapter <L_CollectionNoticeAdapter.ViewHolder> {
    private Context mContext;
    private List<Bean_x> dataList = new ArrayList<>();
    AlertDialog myDialog;

    public void addAllData(List<Bean_x> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public L_CollectionNoticeAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_time;
        public TextView tv_show_person;
        public TextView tv_delete;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_show_person = (TextView) itemView.findViewById(R.id.tv_show_person);

            tv_delete = (TextView)itemView.findViewById(R.id.tv_delete);
        }
    }

    @Override
    public L_CollectionNoticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_item_collection_notice, parent, false);

        return new L_CollectionNoticeAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(L_CollectionNoticeAdapter.ViewHolder holder, final int position) {
        myDialog = new AlertDialog(mContext).builder();
        holder.tv_title.setText(dataList.get(position).getCollectTypeTitle());
        holder.tv_time.setText(dataList.get(position).getCreateTime());
        holder.tv_show_person.setText("发布人："+dataList.get(position).getCollectTypeUserName());
        String collectId = dataList.get(position).getCollectId();
        //删除
        holder.tv_delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                myDialog.setGone().setTitle("提示").setMsg("确定删除么").setNegativeButton("取消",null).setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDelete(collectId,position);
                    }
                }).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   Intent intent=new Intent(mContext, NoticeParticularsActivity_x.class);
                   String typeid = String.valueOf(dataList.get(position).getCollectTypeId());
                    Log.d("noticeId",typeid);
                    intent.putExtra("noticeId",typeid);
                    mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    private void getDelete(String collectId,int pstion) {
        RequestParams params = new RequestParams();
        params.put("collectId",collectId);
        HttpRequest.potNews_romoveCollectInfo(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject obj = new JSONObject(responseObj.toString());
                    String code = obj.getString("code");
                    String msg = obj.getString("msg");
                    Log.d("这是code",code);
                    if (code.equals("0"))
                    {
                        dataList.remove(pstion);
                        notifyDataSetChanged();
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

}
