package com.hulian.oa.work.activity.expense;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.bean.Department;
import com.hulian.oa.bean.People;
import com.hulian.oa.bean.People_x;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.work.activity.expense.l_adapter.ExpandableAdapter_expenseapply;
import com.hulian.oa.work.activity.meeting.l_adapter.ExpandableAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

//选择部门，人员
public class SelDepartmentActivity_meet_expenseApply extends BaseActivity {
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.title_rl)
    RelativeLayout titleRl;
    //创建显示列表的listView
    private ListView listView;
    private TextView tv_select_people, tv_all_count;
    private Button bt_commit;
    String name = "";
    String aid = "";
    List<Department> departmentList = new ArrayList<>();
    List<People> memberList = new ArrayList<>();
    //创建适配器对象
    private RelativeLayout iv_back;
    // 筛选
    private ExpandableListView expandableListView;
    //最外面一层 分组名
    private List<Department> groupArray;
    //最外面一层 分组下面的详情
    private List<List<People>> childArray;
    ExpandableAdapter_expenseapply expandableAdapter;

    //前台传过来的审批人ID
    private String appId = "";
    private String[] phoneNos = {};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_info_meet_people);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        appId = getIntent().getStringExtra("appId");
        phoneNos = appId.replace(" ", "").split(",");
        //初始化页面对象
        init();
        llTop.setVisibility(View.GONE);
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
        titleRl.setVisibility(View.GONE);
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
                    ToastHelper.showToast(mContext, "请选择参会人");
            }
        });

        expandableListView = findViewById(R.id.exlistview);
        expandableListView.setGroupIndicator(null);
//        Drawable drawableWeiHui1 = getResources().getDrawable(R.drawable.zz_img_qgl1);
//        drawableWeiHui1.setBounds(0, 0, 80, 80);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
//        mMainCkb.setCompoundDrawables(null, drawableWeiHui1, null, null);//只放上面
//        Drawable drawableWeiHui2 = getResources().getDrawable(R.drawable.zz_img_qgl2);
//        drawableWeiHui2.setBounds(0, 0, 80, 80);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
//        ckb_leader.setCompoundDrawables(null, drawableWeiHui2, null, null);//只放上面
//        Drawable drawableWeiHui3 = getResources().getDrawable(R.drawable.zz_img_qgl2);
//        drawableWeiHui3.setBounds(0, 0, 80, 80);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
//        ckb_worker.setCompoundDrawables(null, drawableWeiHui3, null, null);//只放上面

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
        expandableAdapter = new ExpandableAdapter_expenseapply(this, groupArray, R.layout.group_layout_ceshi, childArray, R.layout.group_item_listview_ceshi,true);
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
                    memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<People>>() {
                            }.getType());
//                    String nkName = SPUtils.get(mContext, "nickname", "").toString();
//                     for (int i = 0;i < memberList.size();i++){
//                         if (memberList.get(i).getUserName().equals(nkName)){
//                             memberList.remove(i);
//                         }
//                     }
                    for (int i = 0;i<phoneNos.length;i++){
                        for (int j = 0;j<memberList.size();j++){
                            if (phoneNos[i].equals(memberList.get(j).getUserId())){
                                memberList.remove(j);
                            }
                        }
                    }

                    List<People> childModels = childArray.get(position);
                    childModels.addAll(memberList);
                    expandableListView.collapseGroup(position);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}