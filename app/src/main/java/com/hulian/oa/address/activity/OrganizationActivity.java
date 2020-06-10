package com.hulian.oa.address.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.AddresFragmentDetelis;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.address.adapter.OrganizationAdapter;
import com.hulian.oa.bean.Department;
import com.hulian.oa.bean.People;
import com.hulian.oa.bean.SortModel;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.work.activity.attendancestatistics.activity.ClockDetailsActivity;
import com.hulian.oa.work.activity.meeting.l_adapter.ExpandableSingleAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：qgl 时间： 2020/5/28 16:38
 * Describe: 组织机构
 */
public class OrganizationActivity extends BaseActivity {
    @BindView(R.id.exlistview)
    ExpandableListView exlistview;
    OrganizationAdapter expandableAdapter;
    //最外面一层 分组下面的详情
    private List<List<People>> childArray;
    //最外面一层 分组名
    private List<Department> groupArray;
    List<People> memberList = new ArrayList<>();
    List<Department> departmentList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizationactivity);
        ButterKnife.bind(this);
        init();
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public void init() {
        exlistview.setGroupIndicator(null);
        groupArray = new ArrayList<>();
        childArray = new ArrayList<>();
        //创建适配器
        expandableAdapter = new OrganizationAdapter(this, groupArray, R.layout.group_layout_organization, childArray, R.layout.group_layout_organization_item);
        exlistview.setAdapter(expandableAdapter);
        exlistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(OrganizationActivity.this, AddresFragmentDetelis.class);
                intent.putExtra("userName",childArray.get(groupPosition).get(childPosition).getUserName());
                intent.putExtra("email",childArray.get(groupPosition).get(childPosition).getEmail());
                intent.putExtra("loginName",childArray.get(groupPosition).get(childPosition).getLoginName());
                intent.putExtra("phone",childArray.get(groupPosition).get(childPosition).getPhonenumber());
                startActivity(intent);
                return false;
            }
        });

        getDepartMent();
    }
    private void getDepartMent() {
            RequestParams params = new RequestParams();
            HttpRequest.postDepartmentListApi(params, new ResponseCallback() {
                @Override
                public void onSuccess(Object responseObj) {
                    //需要转化为实体对象
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    try {
                        departmentList.clear();
                        JSONObject result = new JSONObject(responseObj.toString());
                        departmentList = gson.fromJson(result.getJSONArray("data").toString(), new TypeToken<List<Department>>() {}.getType());
                        groupArray.addAll(departmentList);
                        for (int i = 0; i < departmentList.size(); i++) {
                            List<People> temPeople = new ArrayList<>();
                            childArray.add(temPeople);
                            initPeopleData(departmentList.get(i).getDeptId(), i);
                        }
                        exlistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                            @Override
                            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long id) {
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
                    memberList = gson.fromJson(result.getJSONArray("data").toString(), new TypeToken<List<People>>() {}.getType());
                    List<People> childModels = childArray.get(position);
                    childModels.addAll(memberList);
                    exlistview.collapseGroup(position);
                    expandableAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
