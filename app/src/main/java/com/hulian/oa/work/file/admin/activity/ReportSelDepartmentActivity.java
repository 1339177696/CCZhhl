package com.hulian.oa.work.file.admin.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.work.file.admin.activity.meeting.l_adapter.ExpandableAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
/**
 * Created by 陈泽宇 on 2020/3/18.
 * Describe:汇报筛选 选择部门，人员
 */

public class ReportSelDepartmentActivity extends BaseActivity {
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    //创建显示列表的listView
    private ListView listView;
    private TextView tv_select_people, tv_all_count;
    private Button bt_commit;
    String name = "";
    String aid = "";
    List<People> memberList = new ArrayList<>();
    List<Department> departmentList = new ArrayList<>();
    //创建适配器对象
    private DepartmentAdapter adapter;
    private RelativeLayout iv_back;

    // 筛选
    private ExpandableListView expandableListView;
    //最外面一层 分组名
    private List<Department> groupArray;
    //最外面一层 分组下面的详情
    private List<List<People>> childArray;
    private List<List<People>> childArray_sel;
    private Context context = this;

    ExpandableAdapter expandableAdapter;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.activity_sel_department_people_report);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        //初始化页面对象
        init();
        if (getIntent().getStringExtra("hasTop") != null && getIntent().getStringExtra("hasTop").equals("0")) {
            llTop.setVisibility(View.GONE);
        }
    }

    public void onEventMainThread(People_x event) {
        finish();
    }

    public void onEventMainThread(List<List<People>> event) {
        String username = "";
        name = "";
        aid = "";
        boolean allcheckede = false;
        if (event.size() > 0) {

            for (int i = 0; i < event.size(); i++) {
                for (int j = 0; j < event.get(i).size(); j++) {
                    if (event.get(i).get(j).isIscheck()) {
                        name += event.get(i).get(j).getUserName() + ";";
                        aid += event.get(i).get(j).getUserId() + ";";
                    }
                }
            }
            if (!name.equals("")) {
                username = name.substring(0, name.length() - 1);
                tv_all_count.setText("(" + username.split(";").length + ")");
            } else
                tv_all_count.setText("(0)");
        }
        tv_select_people.setText(username);


    }

    public void init() {
        tv_select_people = findViewById(R.id.tv_select_people);
        tv_all_count = findViewById(R.id.tv_all_count);
        bt_commit = findViewById(R.id.bt_commit);
        bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.equals("")) {
                    Intent mIntent = new Intent();
                    List<People> mList = new ArrayList<People>();
                    for (int i = 0; i < childArray.size(); i++) {
                        for (int j = 0; j < childArray.get(i).size(); j++) {
                            if (childArray.get(i).get(j).isIscheck()) {
                                mList.add(childArray.get(i).get(j));
                            }
                        }
                    }
                    mIntent.putExtra("mList", (Serializable) mList);
                    setResult(1, mIntent);
                    finish();
                } else
                    ToastHelper.showToast(mContext, "请选择人员");
            }
        });

        expandableListView = findViewById(R.id.exlistview);
        expandableListView.setGroupIndicator(null);

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        groupArray = new ArrayList<>();
        childArray = new ArrayList<>();
        //创建适配器
        expandableAdapter = new ExpandableAdapter(this, groupArray, R.layout.group_layout_ceshi, childArray, R.layout.group_item_listview_ceshi);
        expandableListView.setAdapter(expandableAdapter);
        getDepartMent();

    }

    private void getDepartMent() {
        if (SPUtils.get(this, "roleKey", "").toString().contains("synthesizeLead")) {
            RequestParams params = new RequestParams();
            HttpRequest.postDepartmentListApi(params, new ResponseCallback() {
                @Override
                public void onSuccess(Object responseObj) {
                    //需要转化为实体对象
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    try {
                        departmentList.clear();
                        JSONObject result = new JSONObject(responseObj.toString());
                        departmentList = gson.fromJson(result.getJSONArray("data").toString(),
                                new TypeToken<List<Department>>() {
                                }.getType());

                        groupArray.addAll(departmentList);
                        for (int i = 0; i < departmentList.size(); i++) {
                            List<People> temPeople = new ArrayList<>();
                            childArray.add(temPeople);
                            initPeopleData(departmentList.get(i).getDeptId(), i);
                        }


                        expandableAdapter.notifyDataSetChanged();
                        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                            @Override
                            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long id) {
                                //如果分组被打开 直接关闭
//                            if ( expandableListView.isGroupExpanded(groupPosition) ) {
//                                expandableListView.collapseGroup(groupPosition);
//                            }
//                            else {
//                             //    initPeopleData(groupArray.get(groupPosition).getDeptId(),groupPosition);
//                            }
                                //返回false表示系统自己处理展开和关闭事件 返回true表示调用者自己处理展开和关闭事件
                                return false;
                            }

                        });
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
        }else if (SPUtils.get(this, "roleKey", "").toString().contains("eachLead")) {
            Department department = new Department();
            department.setDeptId(SPUtils.get(this,"deptId","").toString());
            department.setDeptName(SPUtils.get(this,"deptname","").toString());
            groupArray.add(department);
            List<People> temPeople = new ArrayList<>();
            childArray.add(temPeople);
            initPeopleData(department.getDeptId(), 0);
            expandableAdapter.notifyDataSetChanged();
        }else {
            Department department = new Department();
            department.setDeptId("");
            department.setDeptName("智慧互联");
            groupArray.add(department);
            List<People> temPeople = new ArrayList<>();
            childArray.add(temPeople);
            getLeaderList();
            expandableAdapter.notifyDataSetChanged();
        }




    }

    private void initPeopleData(String partId, int position) {
        RequestParams params = new RequestParams();
        params.put("deptId", partId);
        HttpRequest.postUserInfoByDeptId(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    memberList.clear();
                    memberList.removeAll(memberList);
                    childArray.get(position).clear();
                    JSONObject result = new JSONObject(responseObj.toString());
                    memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<People>>() {
                            }.getType());
                    List<People> childModels = childArray.get(position);
                    childModels.addAll(memberList);
                    //  expandableListView.expandGroup(position, true);
                    expandableListView.collapseGroup(position);
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

    private void getLeaderList(){
        RequestParams params = new RequestParams();
        params.put("deptId",SPUtils.get(mContext, "deptId", "").toString());
        params.put("userId", SPUtils.get(mContext, "userId", "").toString());

        HttpRequest.getLeadershipList(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    memberList.clear();
                    memberList.removeAll(memberList);
                    childArray.get(0).clear();
                    JSONObject result = new JSONObject(responseObj.toString());
                    memberList = gson.fromJson(result.getJSONObject("data").getString("userList"),
                            new TypeToken<List<People>>() {
                            }.getType());
                    List<People> childModels = childArray.get(0);
                    for (People people : memberList){
                        people.setIsLead("0");
                    }
                    childModels.addAll(memberList);
                    //  expandableListView.expandGroup(position, true);
                    expandableListView.collapseGroup(0);
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


}
