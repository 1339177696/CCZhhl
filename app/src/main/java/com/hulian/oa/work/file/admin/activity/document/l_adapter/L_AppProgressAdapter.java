package com.hulian.oa.work.file.admin.activity.document.l_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Approve;
import com.hulian.oa.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class L_AppProgressAdapter extends BaseAdapter {

    private Context mContext;
    private String time;
    private String app_id;
    private String status;
    private List<Approve> dataList = new ArrayList<>();
    ViewHolder viewHolder=new ViewHolder ();//必须new，防止空指针
    public L_AppProgressAdapter(Context context,List<Approve> dataList,String time,String app_id,String status){
        this.mContext = context;
        this.time=time;
        this.dataList = dataList;
        this.app_id=app_id;
        this.status=status;
    }
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.l_item_app_progress, null);
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
            viewHolder.tv_state = convertView.findViewById(R.id.tv_state);
            viewHolder.tv_approval = convertView.findViewById(R.id.tv_approval);
            viewHolder.tv_time = convertView.findViewById(R.id.tv_time);
            viewHolder.bottom_line = convertView.findViewById(R.id.bottom_line);

            viewHolder.img_icon = convertView.findViewById(R.id.img_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String status = dataList.get(position).getApproveState();

        viewHolder.img_icon.setText(position+1+"");
        if (status.equals("0")) {
            if (dataList.get(position).getUserId().equals(app_id)) {
                viewHolder.tv_state.setText("审批中");
                viewHolder.tv_approval.setText("正在审核");
            } else {
                viewHolder.tv_state.setText("待审批");
                viewHolder.tv_approval.setText("");
            }
        } else if (status.equals("1")) {

            viewHolder.tv_state.setText("通过");
            viewHolder.tv_approval.setText("已通过");
        } else {
            viewHolder.tv_state.setText("拒绝");
            viewHolder.tv_approval.setText("已拒绝");
        }
        if (dataList.get(position).getUserId().equals(SPUtils.get(mContext, "userId", "").toString())) {
            viewHolder.tv_name.setText("我");
        } else {
            viewHolder.tv_name.setText(dataList.get(position).getUserName());
        }
        if (position == 0) {
            viewHolder.tv_state.setText(" 发起的申请");
            viewHolder.tv_approval.setText("发起申请");
        } else {
            String statusCurrent = dataList.get(position).getApproveState();
            if (statusCurrent.equals("0")) {
                if (dataList.get(position).getUserId().equals(app_id)) {
                    viewHolder.tv_state.setText("审批中");
                    viewHolder.tv_approval.setText("正在审核");
                } else {
                    viewHolder.tv_state.setText("待审批");
                    viewHolder.tv_approval.setText("");
                }
            } else if (statusCurrent.equals("1")) {
                viewHolder.tv_state.setText("通过");
                viewHolder.tv_approval.setText("已通过");
            } else {
                viewHolder.tv_state.setText("拒绝");
                viewHolder.tv_approval.setText("已拒绝");
            }
        }
        if (dataList.size() - 1 == position) {
            viewHolder.bottom_line.setVisibility(View.GONE);
        } else {
            viewHolder.bottom_line.setVisibility(View.VISIBLE);
        }
        if(position==0){
            viewHolder.tv_time.setText( this.time);
        }else
        viewHolder.tv_time.setText(dataList.get(position).getApptime());

        return convertView;
    }
    static class ViewHolder {
        public TextView tv_name;
        public TextView tv_state;
        public TextView tv_approval;
        public TextView tv_time;
        public TextView img_icon;
        public View bottom_line;

    }

}

