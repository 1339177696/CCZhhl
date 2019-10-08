package com.hulian.oa.work.file.admin.activity.task.l_details_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hulian.oa.R;
import com.hulian.oa.bean.Hufu_bean;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.views.CircleImagview;
import com.hulian.oa.work.file.admin.activity.mypreview.PicturePreviewActivity;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * item_undone_top_task  item_undone_down_task  两个布局文件是4个详情adapter公用的
 */

public class L_DetailsCopymeTaskAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    public static final int ITEMONE = 1;
    public static final int ITEMTWO = 2;
    private Context mContext;
    private List<Hufu_bean> dataList = new ArrayList<>();


    public void addAllData(List<Hufu_bean> dataList,Hufu_bean hufu_bean) {
        this.dataList.add(hufu_bean);
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }
    public void addOneData(List<Hufu_bean> dataList) {
        this.dataList.addAll(0,dataList);
        notifyItemInserted(1);

    }
    public void clearData() {
        this.dataList.clear();
    }

    public L_DetailsCopymeTaskAdapter(Context context) {
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
//                viewHolderTop.tv_title.setText(dataList.get(position).getTitle());
                viewHolderTop.tv_launch_person.setText(dataList.get(position).getCreateBy()+"发起");
//                viewHolderTop.tv_deadline.setText(dataList.get(position).getEndTime()+"截止");
//                viewHolderTop.tv_launch_time.setText(dataList.get(position).getStartTime());
                viewHolderTop.tv_operator_person.setText(dataList.get(position).getExecutor().substring(0,dataList.get(position).getExecutor().length()-1));

                viewHolderTop.tv_chaosong.setText(SPUtils.get(mContext, "nickname", "").toString());

                viewHolderTop.tv_completed_count.setText(dataList.get(position).getSum()+"完成");
                viewHolderTop.tv_task_details.setText("    "+dataList.get(position).getDetails());
//                viewHolderTop.tv_undone_top_completed.setVisibility(View.GONE);
            }else if (holder instanceof ViewHolder_List){
                ViewHolder_List viewHolderList = ((ViewHolder_List) holder);
                viewHolderList.tv_name.setText(dataList.get(position).getRespondent());
                String a = dataList.get(position).getCreateTime();
                String b =  getDataaa(a);
                viewHolderList.tv_reply_time.setText(b);
//                String a = dataList.get(position).getCreateTime().substring(0,dataList.get(position).getCreateTime().length()-3);
//                viewHolderList.tv_reply_time.setText(a);
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
                    Glide.with(mContext).load(dataList.get(position).getPicture()).into(((L_DetailsCopymeTaskAdapter.ViewHolder_List) holder).iv_show);
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
//        TextView tv_title;
//        TextView tv_deadline;
        TextView tv_launch_person;
//        TextView tv_launch_time;
        TextView tv_operator_person,tv_chaosong;
        TextView tv_completed_count;
        TextView tv_task_details;
//        TextView tv_undone_top_completed;
        public ViewHolder_Top(View itemView) {
            super(itemView);
//            tv_title = ((TextView) itemView.findViewById(R.id.tv_title_x));
//            tv_deadline = ((TextView) itemView.findViewById(R.id.tv_deadline_x));
            tv_launch_person = ((TextView) itemView.findViewById(R.id.tv_launch_person_x));
//            tv_launch_time = ((TextView) itemView.findViewById(R.id.tv_launch_time_x));
            tv_operator_person = ((TextView) itemView.findViewById(R.id.tv_operator_person_x));

            tv_chaosong = ((TextView) itemView.findViewById(R.id.tv_chaosong));

            tv_completed_count = ((TextView) itemView.findViewById(R.id.tv_completed_count_x));
            tv_task_details = ((TextView) itemView.findViewById(R.id.tv_task_details_x));
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
    public int getItemViewType(int position) {
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
