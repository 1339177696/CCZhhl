package com.hulian.oa.address.pad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.address.adapter.ExpandablePadAdapter;
import com.hulian.oa.bean.Department;
import com.hulian.oa.bean.People;
import com.hulian.oa.me.adapter.DepartmentAdapter;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//选择部门，人员
public class Address_Pad_Fragment extends Fragment {
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    //创建显示列表的listView
    private ListView listView;
    private TextView tv_select_people, tv_all_count;
    private Button bt_commit;
    String name = "";
    String aid = "";
    List<Department> departmentList = new ArrayList<>();
    List<People> memberList = new ArrayList<>();
    //创建适配器对象
    private DepartmentAdapter adapter;
    private ImageView iv_back;

    // 筛选
    private CheckBox mMainCkb, ckb_leader, ckb_worker;
    private ExpandableListView expandableListView;
    //最外面一层 分组名
    private List<Department> groupArray;
    //最外面一层 分组下面的详情
    private List<List<People>> childArray;
    private List<List<People>> childArray_sel;

    ExpandablePadAdapter expandableAdapter;

    public static Address_Pad_Fragment newInstance(String requestJson) {
        Address_Pad_Fragment fragment = new Address_Pad_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_address_pad, container, false);
        ButterKnife.bind(this, view);
        //初始化页面对象
        init(view);
        return view;
    }

    public void init(View view) {
        tv_select_people = view.findViewById(R.id.tv_select_people);
        tv_all_count = view.findViewById(R.id.tv_all_count);
        mMainCkb = view.findViewById(R.id.ckb_main);
        ckb_leader = view.findViewById(R.id.ckb_leader);
        ckb_worker = view.findViewById(R.id.ckb_worker);

        expandableListView = view.findViewById(R.id.exlistview);
        expandableListView.setGroupIndicator(null);

        groupArray = new ArrayList<>();
        childArray = new ArrayList<>();
        //创建适配器
        expandableAdapter = new ExpandablePadAdapter(getActivity(), groupArray, R.layout.group_layout_ceshi_single, childArray, R.layout.group_item_listview_ceshi);
        expandableListView.setAdapter(expandableAdapter);
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
                Toast.makeText(getActivity(), "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
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
                    memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<People>>() {
                            }.getType());
                    List<People> childModels = childArray.get(position);
                    childModels.addAll(memberList);
                    expandableListView.collapseGroup(position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(getActivity(), "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
