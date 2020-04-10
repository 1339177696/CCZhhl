package com.hulian.oa.me.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hulian.oa.R;
import com.hulian.oa.bean.News_qgl;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.news.activity.NewsActivityInfo;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.views.AlertDialog;
import com.hulian.oa.work.activity.notice.NoticeParticularsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class L_CollectionNewsAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private Context mContext;
    private List<News_qgl> dataList = new ArrayList<>();
    AlertDialog myDialog;
    public static final int ITEMONE = 1;
    public static final int ITEMTWO = 2;
    public void addAllData(List<News_qgl> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public L_CollectionNewsAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder_Top extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView tv_content,tv_time,tv_dianzanCount;
        public SimpleDraweeView iv_head;
        public RelativeLayout l_item_collection_news_delet;
        public ViewHolder_Top(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_name);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_head = (SimpleDraweeView) itemView.findViewById(R.id.iv_head);
            tv_dianzanCount=(TextView) itemView.findViewById(R.id.tv_dianzanCount);
            l_item_collection_news_delet = (RelativeLayout) itemView.findViewById(R.id.l_item_collection_news_delet);
        }
    }
    public class ViewHolder_List extends RecyclerView.ViewHolder{
        public TextView list_tv_time;
        public TextView list_tv_time2;
        public TextView list_tv_name;
        public TextView list_tv_author;
        public TextView list_tv_content;
        public RelativeLayout l_item_collection_news_delet;

        public ViewHolder_List(View itemView){
            super(itemView);
            list_tv_time = itemView.findViewById(R.id.tv_time);
            list_tv_time2 = itemView.findViewById(R.id.tv_time2);
            list_tv_name = itemView.findViewById(R.id.tv_name);
            list_tv_author = itemView.findViewById(R.id.tv_author);
            list_tv_content = itemView.findViewById(R.id.tv_content);
            l_item_collection_news_delet = itemView.findViewById(R.id.l_item_collection_news_delet);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = null;
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType){
            case ITEMONE:
                inflate = LayoutInflater.from(mContext).inflate(R.layout.item_collection_1, parent, false);
                viewHolder = new L_CollectionNewsAdapter.ViewHolder_Top(inflate);
                break;
            case ITEMTWO:
                inflate = LayoutInflater.from(mContext).inflate(R.layout.item_collection_2, parent, false);
                viewHolder = new L_CollectionNewsAdapter.ViewHolder_List(inflate);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        myDialog = new AlertDialog(mContext).builder();
        if (holder instanceof ViewHolder_Top) {
            //设置新闻
            ViewHolder_Top viewHolderTop = ((ViewHolder_Top) holder);
            String collectId = dataList.get(position).getCollectId();
            viewHolderTop.title.setText(dataList.get(position).getCollectTypeTitle());
            viewHolderTop.tv_content.setText(Html.fromHtml(dataList.get(position).getCollectTypeDetails()));
            viewHolderTop.tv_time.setText(TimeUtils.getDateToString(dataList.get(position).getCreateTime()));
            viewHolderTop.iv_head.setImageURI(dataList.get(position).getCollectTypeImage());
            viewHolderTop.l_item_collection_news_delet.setOnClickListener(new View.OnClickListener() {
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
        }else if (holder instanceof ViewHolder_List){
            if(dataList.get(position).getCreateTime()!=null) {
                ViewHolder_List viewHolderlist = ((ViewHolder_List) holder);
                String collectId = dataList.get(position).getCollectId();
                viewHolderlist.list_tv_name.setText(dataList.get(position).getCollectTypeTitle());
                viewHolderlist.list_tv_author.setText(dataList.get(position).getCreateBy());
                if (dataList.get(position).getCollectTypeDetails()!=null){
                    viewHolderlist.list_tv_content.setText(Html.fromHtml(dataList.get(position).getCollectTypeDetails()));
                }
                if(dataList.get(position).getCreateTime()!=null){
                    viewHolderlist.list_tv_time.setText(TimeUtils.getDateToString3(dataList.get(position).getCreateTime()));
                    viewHolderlist.list_tv_time2.setText(TimeUtils.getDateToString4(dataList.get(position).getCreateTime()));
                }
                viewHolderlist.l_item_collection_news_delet.setOnClickListener(new View.OnClickListener() {
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
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                dataList.get(position).setIsCollect("1");

                if (dataList.get(position).getCollectType().equals("0")){
                    intent.putExtra("getIsCollect",dataList.get(position).getIsCollect());
                    intent.putExtra("getJournalismId",dataList.get(position).getCollectTypeId());
                    intent.setClass(mContext,NewsActivityInfo.class);
                }else if (dataList.get(position).getCollectType().equals("1")){
                    intent.putExtra("noticeId",dataList.get(position).getCollectTypeId());
                    //新改的传收藏状态qgl
                    intent.putExtra("isCollect",dataList.get(position).getIsCollect());
                    intent.setClass(mContext,NoticeParticularsActivity.class);


                }
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

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position).getCollectType().equals("0")){
            return ITEMONE;
        }else {
            return ITEMTWO;
        }
    }
}
