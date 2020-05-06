package com.hulian.oa.work.activity.attendancestatistics.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.bean.Department;
import com.hulian.oa.bean.People;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.work.activity.meeting.l_adapter.ExpandableSingleAdapter;
import com.hulian.oa.work.adapter.UnitListAdapter;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：qgl 时间： 2020/4/22 10:47
 * Describe: 单位考勤列表
 */
public class UnitListFragment extends Fragment {
    ExpandableListView exlistview;
    UnitListAdapter expandableAdapter;
    private View view;
    //最外面一层 分组下面的详情
    private List<List<People>> childArray;
    //最外面一层 分组名
    private List<Department> groupArray;
    List<People> memberList = new ArrayList<>();
    List<Department> departmentList = new ArrayList<>();

    // TODO: Rename and change types and number of parameters
    public  static UnitListFragment newInstance(String code) {
        UnitListFragment fragment = new UnitListFragment();
        Bundle args = new Bundle();
        args.putString("param", code);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.unitlistfragment, container, false);
        //初始化页面对象
        init();
        return view;
    }

    public void init() {
        exlistview = view.findViewById(R.id.exlistview);
        exlistview.setGroupIndicator(null);
        groupArray = new ArrayList<>();
        childArray = new ArrayList<>();
        //创建适配器
        expandableAdapter = new UnitListAdapter(getActivity(), groupArray, R.layout.group_layout_unitlist_single, childArray, R.layout.group_item_listview_unitlist);
        exlistview.setAdapter(expandableAdapter);
        getDepartMent();
    }

    private void getDepartMent(){
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
                    exlistview.collapseGroup(position);
                    expandableAdapter.notifyDataSetChanged();

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
    public void onDestroyView() {
        super.onDestroyView();
    }
}
