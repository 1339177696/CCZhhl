package com.hulian.oa.work.file.admin.activity.task.l_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.bean.Notice;
import com.hulian.oa.bean.Notice_x;
import com.hulian.oa.me.SelDepartmentActivity;
import com.hulian.oa.me.SelDepartmentActivity_xx;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.work.file.admin.activity.task.TaskLauncherActivity;
import com.hulian.oa.work.file.admin.activity.task.l_details_activity.TaskUndoneDetailsActivity;
import com.hulian.oa.work.file.admin.activity.task.l_fragment.CompletedTaskFragment;
import com.hulian.oa.work.file.admin.activity.task.l_fragment.UndoneTaskFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;

public class L_UndoneTaskAdapter extends RecyclerView.Adapter <L_UndoneTaskAdapter.ViewHolder>{
    private Context mContext;
    private List<Notice_x> dataList = new ArrayList<>();
    //接口
    private ClickInterface clickInterface;

    public void setOnclick(ClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    //回调接口
    public interface ClickInterface {
        void onButtonClick(View view, int position);
    }



    public void addAllData(List<Notice_x> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public L_UndoneTaskAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_time;
        public TextView tv_deadline_time;
        public TextView tv_deadline_week;
        public TextView tv_launch_task_person;
//        public Button tv_transfer_task;
        public Button tv_completed;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_deadline_time = (TextView) itemView.findViewById(R.id.tv_deadline_time);
            tv_launch_task_person = (TextView) itemView.findViewById(R.id.tv_launch_task_person);
            tv_completed = (Button) itemView.findViewById(R.id.tv_completed);
//            tv_transfer_task = (Button) itemView.findViewById(R.id.tv_undone_transfer_task);
        }
    }

    @Override
    public L_UndoneTaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_item_undone_task, parent, false);

        return new L_UndoneTaskAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(L_UndoneTaskAdapter.ViewHolder holder, final int position) {
        holder.tv_title.setText(dataList.get(position).getTitle());

//        开始时间
        String b = dataList.get(position).getStartTime();
        holder.tv_time.setText(b.substring(0,b.length()-5));

//        截止时间
        String a = dataList.get(position).getEndTime();
        holder.tv_deadline_time.setText(a);


        holder.tv_launch_task_person.setText(dataList.get(position).getCreateBy());
        Log.e("i弟弟弟弟",dataList.get(position).getId());

//        holder.tv_transfer_task.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent();
//                intent.putExtra("ID",dataList.get(position).getId());
//                intent.setClass(mContext, SelDepartmentActivity_xx.class);
//                mContext.startActivity(intent);
//            }
//        });

        holder.tv_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String id = dataList.get(position).getId();

                getData(v,id,position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.putExtra("PORID",dataList.get(position).getProid());
                intent.putExtra("ID",dataList.get(position).getId());

                intent.setClass(mContext,TaskUndoneDetailsActivity.class);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private void getData(View v,String id,final int position)
    {
        RequestParams params = new RequestParams();
        params.put("id",id);
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
                        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new CompletedTaskFragment());
                        clickInterface.onButtonClick(v, position);
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
