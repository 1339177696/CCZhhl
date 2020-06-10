package com.hulian.oa.work.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.ClockBean;
import com.hulian.oa.bean.ClockDetaTypeBean;
import com.hulian.oa.utils.TimeUtils;

import java.util.List;

/**
 * 作者：qgl 时间： 2020/4/22 11:20
 * Describe:
 */
public class ClockDetaAdapter extends BaseExpandableListAdapter {
    //视图加载器
    private LayoutInflater mInflater;
    private Context mContext;
    private int mExpandedGroupLayout;
    private int mChildLayout;
    private List<ClockDetaTypeBean> mGroupArray;
    private List<List<ClockBean>> mChildArray;
    TextView tv_count;
    private boolean isVideo = false;
    /**
     * 构造函数
     *
     * @param context
     * @param groupData
     * @param expandedGroupLayout 分组视图布局
     * @param childData
     * @param childLayout         详情视图布局
     */
    public ClockDetaAdapter(Context context, List<ClockDetaTypeBean> groupData, int expandedGroupLayout,
                            List<List<ClockBean>> childData, int childLayout) {
        mContext = context;
        mExpandedGroupLayout = expandedGroupLayout;
        mChildLayout = childLayout;
        mGroupArray = groupData;
        mChildArray = childData;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ClockDetaAdapter(Context context, List<ClockDetaTypeBean> groupData, int expandedGroupLayout,
                            List<List<ClockBean>> childData, int childLayout, boolean isVideo) {
        mContext = context;
        mExpandedGroupLayout = expandedGroupLayout;
        mChildLayout = childLayout;
        mGroupArray = groupData;
        mChildArray = childData;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.isVideo = isVideo;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return mChildArray.get(groupPosition).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        // 取得显示给定分组给定子位置的数据用的视图。
        View v;
        if (convertView == null) {
            v = newChildView(parent);
        } else {
            v = convertView;
        }
        bindChildView(v, mChildArray.get(groupPosition).get(childPosition), mChildArray.get(groupPosition), mGroupArray.get(groupPosition));
        return v;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // 取得指定分组的子元素数。
        return mChildArray.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        // 取得与给定分组关联的数据。
        return mGroupArray.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        // 取得分组数
        return mGroupArray.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        // 取得指定分组的ID。该组ID必须在组中是唯一的。组合的ID （参见getCombinedGroupId(long)）
        // 必须不同于其他所有ID（分组及子项目的ID）。
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        // 取得用于显示给定分组的视图。 这个方法仅返回分组的视图对象， 要想获取子元素的视图对象，
        // 就需要调用 getChildView(int, int, boolean, View, ViewGroup)。
        View v;
        v = newGroupView(parent);
        bindGroupView(v, mGroupArray.get(groupPosition), isExpanded, groupPosition);
        return v;
    }

    /**
     * 绑定组数据
     *
     * @param view
     * @param data
     * @param isExpanded
     * @param groupPosition
     */
    private void bindGroupView(View view, ClockDetaTypeBean data, boolean isExpanded, int groupPosition) {
        // 绑定组视图的数据 当然这些都是模拟的
        TextView tv_title = (TextView) view.findViewById(R.id.group_title);
        tv_title.setText(data.getTypeName());
        TextView tv_count = view.findViewById(R.id.tv_count);
        if (data.getType().equals("4")||data.getType().equals("5")){
            tv_count.setText(data.getSize()+ "天");
        } else {
            tv_count.setText(getChildrenCount(groupPosition)+ "天");
        }
        ImageView group_state = view.findViewById(R.id.group_state);
        //如果是展开状态，
        if (isExpanded){
            group_state.setImageDrawable(ContextCompat.getDrawable(mContext,R.mipmap.return_down));
        }else{
            group_state.setImageDrawable(ContextCompat.getDrawable(mContext,R.mipmap.return_up));
        }
    }

    /**
     * 绑定子数据
     *
     * @param view
     * @param data
     * @param peopleList
     */
    private void bindChildView(View view, ClockBean data, List<ClockBean> peopleList, ClockDetaTypeBean mDapart) {
        // 绑定组视图的数据 当然这些都是模拟的
        TextView att_tv_date = (TextView) view.findViewById(R.id.att_tv_date);
        TextView att_clock_time = (TextView) view.findViewById(R.id.att_clock_time);
        if (data.getType().equals("4")){
            att_tv_date.setText(data.getStartTime()+"---"+data.getEndTime());
            att_clock_time.setText("共"+data.getDuration()+"天");
        }else if (data.getType().equals("5")){
            att_tv_date.setText(data.getCreateTime());
            if (("2").equals(data.getRegisterUpState())|| TimeUtils.compareTwoTime((String) data.getRegisterUpTime(), "17:30") <= 0)
            {
                att_clock_time.setText("全天");
            }else if (TimeUtils.compareTwoTime((String) data.getRegisterDownTime(), "11:30") >= 0){
                att_clock_time.setText("下午");
            }else if (TimeUtils.compareTwoTime((String) data.getRegisterUpTime(), "13:30") <= 0){
                att_clock_time.setText("上午");
            }
        }else {
            att_tv_date.setText(data.getCreateTime());
            att_clock_time.setText("上班"+data.getRegisterUpTime()+"下班"+data.getRegisterDownTime());
        }
    }

    /**
     * 创建新的组视图
     *
     * @param parent
     * @return
     */
    public View newGroupView(ViewGroup parent) {
        return mInflater.inflate(mExpandedGroupLayout, parent, false);
    }

    /**
     * 创建新的子视图
     *
     * @param parent
     * @return
     */
    public View newChildView(ViewGroup parent) {
        return mInflater.inflate(mChildLayout, parent, false);
    }

    public boolean hasStableIds() {
        // 是否指定分组视图及其子视图的ID对应的后台数据改变也会保持该ID。
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // 指定位置的子视图是否可选择。
        return true;
    }
}

