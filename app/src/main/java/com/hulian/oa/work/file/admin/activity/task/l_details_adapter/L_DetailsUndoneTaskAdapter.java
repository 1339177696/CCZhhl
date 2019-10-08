package com.hulian.oa.work.file.admin.activity.task.l_details_adapter;

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

import com.bumptech.glide.Glide;
import com.hulian.oa.R;
import com.hulian.oa.bean.Hufu_bean;
import com.hulian.oa.bean.Huifu_bean_x;
import com.hulian.oa.bean.Notice_x;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.net.Urls;
import com.hulian.oa.views.CircleImagview;
import com.hulian.oa.work.file.admin.activity.mypreview.PicturePreviewActivity;
import com.hulian.oa.work.file.admin.activity.task.l_adapter.L_UndoneTaskAdapter;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class L_DetailsUndoneTaskAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    public static final int ITEMONE = 1;
    public static final int ITEMTWO = 2;
    private Context mContext;
    private List<Hufu_bean> dataList = new ArrayList<>();

    public void addAllData(List<Hufu_bean> dataList,Hufu_bean bean) {
        this.dataList.add(bean);
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    //接口
    private aClickInterface clickInterface;

    public void setOnclick(aClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    //回调接口
    public interface aClickInterface {
        void onButtonClick(View view, int position);
    }

    public void clearData() {
        this.dataList.clear();
    }

    public L_DetailsUndoneTaskAdapter(Context context) {
        mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = null;
        RecyclerView.ViewHolder viewHolder = null;

        //根据i返回不同布局
        switch (viewType) {
            case ITEMONE:
                inflate = LayoutInflater.from(mContext).inflate(R.layout.item_undone_top_task, parent, false);
                viewHolder = new ViewHolder_Top(inflate);
                break;
            case ITEMTWO:
                inflate = LayoutInflater.from(mContext).inflate(R.layout.item_undone_down_task, parent, false);
                viewHolder = new ViewHolder_List(inflate);
                break;
        }
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof ViewHolder_Top){//设置数据 事件
                ViewHolder_Top viewHolderTop = ((ViewHolder_Top) holder);

//                viewHolderTop.tv_title_x.setText(dataList.get(position).getTitle());
                viewHolderTop.tv_launch_person_x.setText(dataList.get(position).getCreateBy()+"发起");
//                viewHolderTop.tv_deadline_x.setText(dataList.get(position).getEndTime()+"截止");
//                viewHolderTop.tv_launch_time_x.setText(dataList.get(position).getStartTime());
                viewHolderTop.tv_operator_person_x.setText(dataList.get(position).getExecutor().substring(0,dataList.get(position).getExecutor().length()-1));
                viewHolderTop.tv_completed_count_x.setText(dataList.get(position).getSum()+"完成");
                viewHolderTop.tv_task_details_x.setText("    "+dataList.get(position).getDetails());
                Log.e("大哥哥哥哥",dataList.get(position).getId());
//                viewHolderTop.tv_undone_top_completed.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        clickInterface.onButtonClick(v, position);
//
////                        getData(v,dataList.get(position).getId(),1);
//                    }
//                });
            }else if (holder instanceof ViewHolder_List)
            {
                ViewHolder_List viewHolderList = ((ViewHolder_List) holder);
                viewHolderList.tv_name.setText(dataList.get(position).getRespondent());


                String a = dataList.get(position).getCreateTime();
                String b =  getDataaa(a);
                viewHolderList.tv_reply_time.setText(b);


                if (dataList.get(position).getContent()!=null)
                {
                    viewHolderList.tv_content.setVisibility(View.VISIBLE);
                    viewHolderList.iv_show.setVisibility(View.GONE);
                    viewHolderList.tv_content.setText(dataList.get(position).getContent());
                }
                else
                {
                    viewHolderList.tv_content.setVisibility(View.GONE);
                    viewHolderList.iv_show.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(dataList.get(position).getPicture()).into(((ViewHolder_List) holder).iv_show);
                    viewHolderList.iv_show.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                             List<LocalMedia> selectList = new ArrayList<>();
                            LocalMedia aa=new LocalMedia();
                            aa.setPath(dataList.get(position).getPicture());
                            selectList.add(aa);
                            Intent intent = new Intent(mContext, PicturePreviewActivity.class);
                            intent.putExtra(PictureConfig.EXTRA_PREVIEW_SELECT_LIST, (Serializable) selectList);
                            intent.putExtra(PictureConfig.EXTRA_POSITION, position);
                            mContext.startActivity(intent);
                        }
                    });
                }


            }

    }
    public static class ViewHolder_Top extends RecyclerView.ViewHolder {
//        TextView tv_title_x;
//        TextView tv_deadline_x;
        TextView tv_launch_person_x;
//        TextView tv_launch_time_x;
        TextView tv_operator_person_x;
        TextView tv_completed_count_x;
        TextView tv_task_details_x;

        TextView tv_undone_top_completed;
        public ViewHolder_Top(View itemView) {
            super(itemView);
//            tv_title_x = ((TextView) itemView.findViewById(R.id.tv_title_x));
//            tv_deadline_x = ((TextView) itemView.findViewById(R.id.tv_deadline_x));
            tv_launch_person_x = ((TextView) itemView.findViewById(R.id.tv_launch_person_x));
//            tv_launch_time_x = ((TextView) itemView.findViewById(R.id.tv_launch_time_x));
            tv_operator_person_x = ((TextView) itemView.findViewById(R.id.tv_operator_person_x));
            tv_completed_count_x = ((TextView) itemView.findViewById(R.id.tv_completed_count_x));
            tv_task_details_x = ((TextView) itemView.findViewById(R.id.tv_task_details_x));

//            tv_undone_top_completed = ((TextView) itemView.findViewById(R.id.tv_undone_top_completed));
        }
    }
    public static class ViewHolder_List extends RecyclerView.ViewHolder {
        CircleImagview ci_pic;
        TextView tv_name;
        TextView tv_reply_time;
        TextView tv_content;
        ImageView iv_show;
        public ViewHolder_List(View itemView) {
            super(itemView);
            ci_pic = ((CircleImagview) itemView.findViewById(R.id.ci_pic));
            tv_name = ((TextView) itemView.findViewById(R.id.tv_name));
            tv_reply_time = ((TextView) itemView.findViewById(R.id.tv_reply_time));
            tv_content = ((TextView) itemView.findViewById(R.id.tv_content));
            iv_show = ((ImageView) itemView.findViewById(R.id.iv_show));
        }
    }
    @Override
    public int getItemViewType(int position)
    {
        if (position==0){
            return ITEMONE;
        }else{
            return ITEMTWO;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

//    时间差
    private String getDataaa(String fromDate) {
    String strTime = null;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
    String toDate = df.format(curDate);
    try {
        //前的时间
        Date fd = df.parse(fromDate);
        //后的时间
        Date td = df.parse(toDate);
        //两时间差,精确到毫秒
        long diff = td.getTime() - fd.getTime();
        long day = diff / 86400000;                         //以天数为单位取整
        long hour = diff % 86400000 / 3600000;               //以小时为单位取整
        long min = diff % 86400000 % 3600000 / 60000;       //以分钟为单位取整
        long seconds = diff % 86400000 % 3600000 % 60000 / 1000;   //以秒为单位取整

        if (day<=0)
        {
            strTime = fromDate.substring(10,fromDate.length()-3);
        }
        else
        {
            System.out.println("两时间差---> " + day + "天" + hour + "小时" + min + "分" + seconds + "秒");
            strTime = fromDate.substring(0,fromDate.length()-8);

        }
        return strTime;
    } catch (ParseException e)
    {
        e.printStackTrace();
    }
    return null;
}

}
