package com.hulian.oa.me.l_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.bean.Bean_x;
import com.hulian.oa.bean.News;
import com.hulian.oa.me.l_fragment.L_CollectionNewsFragment;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.net.Urls;
import com.hulian.oa.news.activity.NewsActivityInfo;
import com.hulian.oa.news.activity.NewsActivityInfo_x;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.views.AlertDialog;
import com.hulian.oa.work.file.admin.activity.notice.NoticeParticularsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class L_CollectionNewsAdapter extends RecyclerView.Adapter <L_CollectionNewsAdapter.ViewHolder> {
    private Context mContext;
    private List<News> dataList = new ArrayList<>();
    private String image_url = "https://abc.cczhhl.cn/";
    private String collectTypeId;
    AlertDialog myDialog;
    public void addAllData(List<News> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public L_CollectionNewsAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView tv_content,tv_time,tv_dianzanCount;
        public SimpleDraweeView iv_head;
        public RelativeLayout l_item_collection_news_delet;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_name);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_head = (SimpleDraweeView) itemView.findViewById(R.id.iv_head);
            tv_dianzanCount=(TextView) itemView.findViewById(R.id.tv_dianzanCount);
            l_item_collection_news_delet = (RelativeLayout) itemView.findViewById(R.id.l_item_collection_news_delet);
        }
    }

    @Override
    public L_CollectionNewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection_1, parent, false);

        return new L_CollectionNewsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(L_CollectionNewsAdapter.ViewHolder holder, final int position) {
        myDialog = new AlertDialog(mContext).builder();
//        holder.tv_title.setText(dataList.get(position).getCollectTypeTitle());
//        holder.tv_time.setText(dataList.get(position).getCreateTime());
//        holder.tv_from.setText(dataList.get(position).getCollectTypeUserName());
        String collectId = dataList.get(position).getJournalismId();
     //   collectTypeId = String.valueOf(dataList.get(position).getCollectTypeId());
////        Log.d("这是图片地址1",dataList.get(position).getCollectTypeImage());
//        String a = dataList.get(position).getCollectTypeImage();
//        String b = a.replaceAll("D:/","");
////        Log.d("这是图片地址2",b);
//
//        Glide.with(mContext).load(a).into(holder.iv_pic_news);
        holder.title.setText(dataList.get(position).getJournalismTitle());
        holder.tv_content.setText(Html.fromHtml(dataList.get(position).getJournalismContent()));
        holder.tv_time.setText(TimeUtils.getDateToString(dataList.get(position).getCreateTime()));
        holder.iv_head.setImageURI(dataList.get(position).getJournalismImage());


        holder.l_item_collection_news_delet.setOnClickListener(new View.OnClickListener() {
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
            //    Log.d("collectTypeId",collectTypeId);
//                Intent intent= new Intent(mContext, NewsActivityInfo_x.class);
//                intent.putExtra("journalism",dataList.get(position));
//                mContext.startActivity(intent);
                Intent intent= new Intent(mContext, NewsActivityInfo.class);
                intent.putExtra("journalism",dataList.get(position));
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
                            Toast.makeText(mContext,"接口没修改",Toast.LENGTH_SHORT).show();
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
