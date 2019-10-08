package com.hulian.oa.work.file.admin.activity.meeting;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.Department;
import com.hulian.oa.bean.People;
import com.hulian.oa.bean.People_x;
import com.hulian.oa.me.l_adapter.DepartmentAdapter;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.work.file.admin.activity.meeting.l_adapter.MyPeopleAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

//选择部门，人员
public class SelDepartmentActivity_meet extends BaseActivity  {
    //创建显示列表的listView
    private ListView listView;
    List<Department> memberList=new ArrayList<>();
    //创建适配器对象
    private DepartmentAdapter adapter;
    private ImageView iv_back;

    // 筛选
    private CheckBox mMainCkb,ckb_leader,ckb_worker;
    private ExpandableListView expandableListView;
    private List<Map<String, String>> parentList = new ArrayList<Map<String, String>>();
    private List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
    private Context context = this;
    private MyAdapter adapter11;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_info_meet_people);
        EventBus.getDefault().register(this);
        //初始化页面对象
        init();
        //将数据显示在页面上
//        initDatas();

//        初始化数据
        initData();
    }
    public void onEventMainThread(People_x event) {
            finish();
    }
    public void onEventMainThread(List<People> resultmemberList ) {
        finish();
    }
    public void init() {
        mMainCkb = findViewById(R.id.ckb_main);
        ckb_leader = findViewById(R.id.ckb_leader);
        ckb_worker = findViewById(R.id.ckb_worker);

        expandableListView = findViewById(R.id.exlistview);
        expandableListView.setGroupIndicator(null);
        Drawable drawableWeiHui1 = getResources().getDrawable(R.drawable.zz_img_qgl1);
        drawableWeiHui1.setBounds(0, 0, 80, 80);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        mMainCkb.setCompoundDrawables(null, drawableWeiHui1, null, null);//只放上面
        Drawable drawableWeiHui2 = getResources().getDrawable(R.drawable.zz_img_qgl2);
        drawableWeiHui2.setBounds(0, 0, 80, 80);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        ckb_leader.setCompoundDrawables(null, drawableWeiHui2, null, null);//只放上面
        Drawable drawableWeiHui3 = getResources().getDrawable(R.drawable.zz_img_qgl2);
        drawableWeiHui3.setBounds(0, 0, 80, 80);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        ckb_worker.setCompoundDrawables(null, drawableWeiHui3, null, null);//只放上面




//        listView = (ListView) findViewById(R.id.lv_text_view);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent=new  Intent(SelDepartmentActivity_meet.this,SelPeopleActivity_meet_people.class);
//                intent.putExtra("partId",memberList.get(i).getDeptId());
//                startActivity(intent);
//            }
//        });
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initDatas() {
        RequestParams params = new RequestParams();
        HttpRequest.postDepartmentListApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<Department>>() {
                            }.getType());
                    adapter = new DepartmentAdapter(memberList, mContext);
                    listView.setAdapter(adapter);

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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }





    private void initData()
    {
        for (int i = 0; i < 3; i++)
        {
            Map<String, String> groupMap = new HashMap<String, String>();
            groupMap.put("groupText", "item" + i);
            groupMap.put("isGroupCheckd", "No");
            parentList.add(groupMap);
        }
        for (int i = 0; i < 3; i++)
        {
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            for (int j = 0; j < 4; j++)
            {
                Map<String, String> map = new HashMap<String, String>();
                map.put("childItem", "childItem" + j);
                map.put("isChecked", "No");
                list.add(map);
            }
            childData.add(list);
        }
        adapter11 = new MyAdapter();
        expandableListView.setAdapter(adapter11);
//        hashSet = new HashSet<String>();
    }

    /**
     * 适配adapter
     */
    private class MyAdapter extends BaseExpandableListAdapter {
        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            // TODO Auto-generated method stub
            return childData.get(groupPosition).get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            // TODO Auto-generated method stub
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
        {

            ViewHolder holder = null;
            if (convertView == null)
            {
                holder = new ViewHolder();
                convertView = View.inflate(context, R.layout.group_item_listview_ceshi, null);
                holder.childText = (TextView) convertView.findViewById(R.id.tv_name);
                holder.childBox = (CheckBox) convertView.findViewById(R.id.aa);

                convertView.setTag(holder);
            } else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.childText.setText(childData.get(groupPosition).get(childPosition).get("childItem"));
            String isChecked = childData.get(groupPosition).get(childPosition).get("isChecked");
            if ("No".equals(isChecked))
            {
                holder.childBox.setChecked(false);
            } else
            {
                holder.childBox.setChecked(true);
            }
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            // TODO Auto-generated method stub
            return childData.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition)
        {
            return parentList.get(groupPosition);
        }

        @Override
        public int getGroupCount()
        {
            // TODO Auto-generated method stub
            return parentList.size();
        }

        @Override
        public long getGroupId(int groupPosition)
        {
            // TODO Auto-generated method stub
            return groupPosition;
        }

        @Override
        public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent)
        {
            ViewHolder holder = null;
            if (convertView == null)
            {
                holder = new ViewHolder();
                convertView = View.inflate(context, R.layout.group_layout_ceshi, null);
                holder.groupText = (TextView) convertView.findViewById(R.id.group_title);
                holder.groupBox = (CheckBox) convertView.findViewById(R.id.bb);
                convertView.setTag(holder);
            } else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.groupText.setText(parentList.get(groupPosition).get("groupText"));
            final String isGroupCheckd = parentList.get(groupPosition).get("isGroupCheckd");

            if ("No".equals(isGroupCheckd))
            {
                holder.groupBox.setChecked(false);
            } else
            {
                holder.groupBox.setChecked(true);
            }

            /*
             * groupListView的点击事件
             */
            holder.groupBox.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    CheckBox groupBox = (CheckBox) v.findViewById(R.id.bb);
                    if (!isExpanded)
                    {
                        //展开某个group view
                        expandableListView.expandGroup(groupPosition);
                    } else
                    {
                        //关闭某个group view
                        expandableListView.collapseGroup(groupPosition);
                    }

                    if ("No".equals(isGroupCheckd))
                    {
                        expandableListView.expandGroup(groupPosition);
                        groupBox.setChecked(true);
                        parentList.get(groupPosition).put("isGroupCheckd", "Yes");
                        List<Map<String, String>> list = childData
                                .get(groupPosition);
                        for (Map<String, String> map : list)
                        {
                            map.put("isChecked", "Yes");
                        }
                    } else
                    {
                        groupBox.setChecked(false);
                        parentList.get(groupPosition).put("isGroupCheckd", "No");
                        List<Map<String, String>> list = childData.get(groupPosition);
                        for (Map<String, String> map : list)
                        {
                            map.put("isChecked", "No");
                        }
                    }
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        @Override
        public boolean hasStableIds()
        {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition)
        {
            return true;
        }

    }

    private class ViewHolder {
        TextView groupText, childText;
        CheckBox groupBox, childBox;
    }



}
