package com.hulian.oa.work.activity.attendancestatistics.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.bean.ClockDepartBean;
import com.hulian.oa.bean.Department;
import com.hulian.oa.bean.People;
import com.hulian.oa.bean.UnitLisFragmentBean;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.work.activity.attendancestatistics.activity.ClockDetailsActivity;
import com.hulian.oa.work.activity.meeting.l_adapter.ExpandableSingleAdapter;
import com.hulian.oa.work.adapter.UnitListAdapter;
import com.nostra13.universalimageloader.utils.L;

import org.json.JSONArray;
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
    private List<List<ClockDepartBean>> childArray;
    //最外面一层 分组名
    private List<UnitLisFragmentBean> groupArray;
    List<ClockDepartBean> memberList = new ArrayList<>();
    List<UnitLisFragmentBean> departmentList = new ArrayList<>();
    private int cd = 7; // 0正常，1迟到，2早退，3加班，4请假，5缺勤，6外勤,7全部
    private static String timer = "";
    private int mCount = 1;

    // TODO: Rename and change types and number of parameters
    public  static UnitListFragment newInstance(String code,String time) {
        UnitListFragment fragment = new UnitListFragment();
        Bundle args = new Bundle();
        args.putString("param", code);
        args.putString("time", time);
        timer = time;
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.unitlistfragment, container, false);
        String mParam1 = getArguments().getString("param");
        cd = Integer.parseInt(mParam1);
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
        exlistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), ClockDetailsActivity.class);
                intent.putExtra("username",childArray.get(groupPosition).get(childPosition).getUserName());
                intent.putExtra("deptname",childArray.get(groupPosition).get(childPosition).getDeptName());
                intent.putExtra("userid",childArray.get(groupPosition).get(childPosition).getCreateBy());
                intent.putExtra("time",timer);
                startActivity(intent);
                return false;
            }
        });

        getDepartMent();

    }

    private void getDepartMent(){
        RequestParams params = new RequestParams();
        params.put("type",String.valueOf(cd));
        params.put("createTime",timer);
        HttpRequest.getDwkqtj(params, new ResponseCallback() {
                @Override
                public void onSuccess(Object responseObj) {
                    //需要转化为实体对象
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    try {
                        departmentList.clear();
                        groupArray.clear();
                        JSONObject result = new JSONObject(responseObj.toString());
                        departmentList = gson.fromJson(result.getJSONObject("data").getJSONArray("kaoqin").getJSONObject(0).getJSONArray("dept").toString(),
                                new TypeToken<List<UnitLisFragmentBean>>() {
                                }.getType());
                        groupArray.addAll(departmentList);
                        for (int i = 0; i < departmentList.size(); i++) {
                            List<ClockDepartBean> temPeople = new ArrayList<>();
                            childArray.add(temPeople);
                            initPeopleData(departmentList.get(i).getDeptId(), i);
                        }
                        int size = Integer.parseInt(result.getJSONObject("data").getJSONArray("kaoqin").getJSONObject(0).getString("size"));
                        int pos = 0;
                        if (cd == 0){
                            pos = 1;
                        }else if (cd == 1){
                            pos = 2;
                        }else if (cd == 2){
                            pos = 3;
                        }else if (cd == 3){
                            pos = 4;
                        }else if (cd == 4){
                            pos = 5;
                        }else if (cd == 5){
                            pos = 6;
                        }else if (cd == 6){
                            pos = 7;
                        }else if (cd == 7){
                            pos = 0;
                        }
                        ((UnitAttendanceFragment) (UnitListFragment.this.getParentFragment())).setListSize(size,pos);
                        ((UnitAttendanceFragment) (UnitListFragment.this.getParentFragment())).setUnit_text(departmentList.size());

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
        params.put("createTime", timer);
        params.put("type", String.valueOf(cd));
        params.put("iden", "1");
        HttpRequest.getAttece_List(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.e("单位考勤","yes");
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    memberList.clear();
                    memberList.removeAll(memberList);
                    childArray.get(position).clear();
                    JSONObject result = new JSONObject(responseObj.toString());
                    memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<ClockDepartBean>>() {
                            }.getType());
                    List<ClockDepartBean> childModels = childArray.get(position);
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
