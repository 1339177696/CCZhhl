package com.hulian.oa.work.file.admin.activity.document.l_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Approve;
import com.hulian.oa.bean.Approve_qgl;
import com.hulian.oa.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class L_AppProgressAdapter_qgl extends BaseAdapter {

    private Context mContext;
    private String initiationType1;
    private List<Approve_qgl> dataList = new ArrayList<>();
    ViewHolder viewHolder=new ViewHolder ();//必须new，防止空指针
    public L_AppProgressAdapter_qgl(Context context, List<Approve_qgl> dataList,String initiationType){
        this.mContext = context;
        this.initiationType1 = initiationType;
        this.dataList = dataList;

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.l_item_app_progress_qgl, null);
            viewHolder.tv_qgl_item_name = convertView.findViewById(R.id.tv_qgl_item_name);
            viewHolder.tv_qgl_item_stata = convertView.findViewById(R.id.tv_qgl_item_stata);
            viewHolder.tv_qgl_item_time = convertView.findViewById(R.id.tv_qgl_item_time);
            viewHolder.tv_qgl_item_yijian = convertView.findViewById(R.id.tv_qgl_item_yijian);
            viewHolder.gw_img1 = convertView.findViewById(R.id.gw_img1);
            viewHolder.v_rela = convertView.findViewById(R.id.v_rela);
            viewHolder.v_xian = convertView.findViewById(R.id.v_xian);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        会签没有审批意见，签批有审批意见
        if (initiationType1.equals("1")){
            viewHolder.v_rela.setVisibility(View.GONE);
            viewHolder.v_xian.setVisibility(View.GONE);
        }
        else if (initiationType1.equals("0")){
//            viewHolder.v_rela.setVisibility(View.VISIBLE);
//            viewHolder.v_xian.setVisibility(View.VISIBLE);
            viewHolder.v_rela.setVisibility(View.GONE);
            viewHolder.v_xian.setVisibility(View.GONE);
        }
        viewHolder.tv_qgl_item_name.setText(dataList.get(position).getApproverName());
        viewHolder.gw_img1.setText(dataList.get(position).getApproverName().substring(0,1));
        if (dataList.get(position).getApproverTime() == null){
            viewHolder.tv_qgl_item_time.setText("");
        }else {
            viewHolder.tv_qgl_item_time.setText(dataList.get(position).getApproverTime().substring(0,dataList.get(position).getApproverTime().length()-3));
        }
        viewHolder.tv_qgl_item_yijian.setText(dataList.get(position).getApproverOpinion());
        if ("0".equals(dataList.get(position).getState())){
            viewHolder.tv_qgl_item_stata.setText("待审批");
        }else if ("1".equals(dataList.get(position).getState())){
            viewHolder.tv_qgl_item_stata.setText("已审批");
        }
        else {
            viewHolder.tv_qgl_item_stata.setText("已驳回");
        }

        return convertView;
    }
    static class ViewHolder {
        public TextView tv_qgl_item_name;
        public TextView tv_qgl_item_stata;
        public TextView tv_qgl_item_time;
        public TextView tv_qgl_item_yijian;
        public TextView gw_img1;
        public TextView v_xian;
        public RelativeLayout v_rela;


    }

}

