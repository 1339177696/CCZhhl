package com.hulian.oa.work.activity.statistical.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.Expen_Statis_Person_Bean;
import com.hulian.oa.bean.ExpenseStaBean;

import java.util.List;

/**
 * 作者：qgl 时间： 2020/4/22 11:20
 * Describe:
 */
public class ExpenseListAdapter extends BaseExpandableListAdapter {
    //视图加载器
    private LayoutInflater mInflater;
    private Context mContext;
    private int mExpandedGroupLayout;
    private int mChildLayout;
    private List<Expen_Statis_Person_Bean> mGroupArray;
    private List<List<ExpenseStaBean>> mChildArray;
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
    public ExpenseListAdapter(Context context, List<Expen_Statis_Person_Bean> groupData, int expandedGroupLayout,
                              List<List<ExpenseStaBean>> childData, int childLayout) {
        mContext = context;
        mExpandedGroupLayout = expandedGroupLayout;
        mChildLayout = childLayout;
        mGroupArray = groupData;
        mChildArray = childData;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ExpenseListAdapter(Context context, List<Expen_Statis_Person_Bean> groupData, int expandedGroupLayout,
                              List<List<ExpenseStaBean>> childData, int childLayout, boolean isVideo) {
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

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
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
    private void bindGroupView(View view, Expen_Statis_Person_Bean data, boolean isExpanded, int groupPosition) {
        // 绑定组视图的数据 当然这些都是模拟的
        TextView tv_title = view.findViewById(R.id.group_title);
        tv_title.setText(data.getUserName());
        TextView tv_count = view.findViewById(R.id.tv_count);
        tv_count.setText("￥" + data.getMoney());
        ImageView group_state = view.findViewById(R.id.group_state);
        //如果是展开状态，
        if (isExpanded) {
            group_state.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.return_down));
        } else {
            group_state.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.return_up));
        }
    }

    /**
     * 绑定子数据
     *
     * @param view
     * @param data
     * @param peopleList
     */
    private void bindChildView(View view, ExpenseStaBean data, List<ExpenseStaBean> peopleList, Expen_Statis_Person_Bean mDapart) {
        // 绑定组视图的数据 当然这些都是模拟的
        TextView expens_name = (TextView) view.findViewById(R.id.expens_name);
        TextView expens_num2 = (TextView) view.findViewById(R.id.expens_num2);
        ImageView iv_remind = (ImageView) view.findViewById(R.id.iv_remind);
        TextView expens_num1 = (TextView) view.findViewById(R.id.expens_num1);
        expens_num1.setVisibility(View.GONE);
        expens_num2.setText("￥" + data.getMoney());
        switch (data.getApproveType()) {
            case "1":
                expens_name.setText("￥" + "采购费");
                iv_remind.setImageResource(R.drawable.expense_img_yuan0);
                break;
            case "2":
                expens_name.setText("￥" + "加班餐费");
                iv_remind.setImageResource(R.drawable.expense_img_yuan1);
                break;
            case "3":
                expens_name.setText("￥" + "加班打车费");
                iv_remind.setImageResource(R.drawable.expense_img_yuan2);
                break;
            case "4":
                expens_name.setText("￥" + "外出打车费");
                iv_remind.setImageResource(R.drawable.expense_img_yuan3);
                break;
            case "5":
                expens_name.setText("￥" + "办公用品");
                iv_remind.setImageResource(R.drawable.expense_img_yuan4);
                break;
            case "6":
                expens_name.setText("￥" + "差旅费");
                iv_remind.setImageResource(R.drawable.expense_img_yuan5);
                break;
            case "7":
                expens_name.setText("￥" + "员工福利");
                iv_remind.setImageResource(R.drawable.expense_img_yuan6);
                break;
            case "8":
                expens_name.setText("￥" + "招待费");
                iv_remind.setImageResource(R.drawable.expense_img_yuan7);
                break;
            case "9":
                expens_name.setText("￥" + "其他");
                iv_remind.setImageResource(R.drawable.expense_img_yuan8);
                break;
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

