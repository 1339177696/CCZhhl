package com.hulian.oa.work.activity.attendancestatistics.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.bean.Department;
import com.hulian.oa.bean.People;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.work.adapter.ClockDetaAdapter;
import com.hulian.oa.work.adapter.UnitListAdapter;
import com.othershe.calendarview.utils.CalendarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：qgl 时间： 2020/4/22 14:29
 * Describe:考勤统计详情
 */
public class ClockDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.clock_name)
    TextView clockName;
    @BindView(R.id.clock_department)
    TextView clockDepartment;
    @BindView(R.id.current_time_liner)
    LinearLayout currentTimeLiner;
    @BindView(R.id.exlistview)
    ExpandableListView exlistview;
    @BindView(R.id.current_time)
    TextView currentTime;
    private String startTimeStr = "";
    private boolean btn = true;
    private int[] cDate = CalendarUtil.getCurrentDate();

    private View view;
    //最外面一层 分组下面的详情
    private List<List<People>> childArray;
    //最外面一层 分组名
    private List<Department> groupArray;
    List<People> memberList = new ArrayList<>();
    List<Department> departmentList = new ArrayList<>();
    private ClockDetaAdapter clockDetaAdapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.clockdetailsactivity);
        ButterKnife.bind(this);
        currentTime.setText("" + cDate[0] + "-" + cDate[1]);
        init();
    }


    @OnClick({R.id.iv_back, R.id.current_time_liner})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.current_time_liner:
                selectStartTime();
                break;
        }
    }

    public void init() {
        exlistview = findViewById(R.id.exlistview);
        exlistview.setGroupIndicator(null);
        groupArray = new ArrayList<>();
        childArray = new ArrayList<>();
        //创建适配器
        clockDetaAdapter = new ClockDetaAdapter(this, groupArray, R.layout.group_layout_clockdeta_single, childArray, R.layout.group_item_listview_clockdeta);
        exlistview.setAdapter(clockDetaAdapter);
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
                Toast.makeText(ClockDetailsActivity.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
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
                    clockDetaAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(ClockDetailsActivity.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectStartTime() {
        TimePickerView pvTime = new TimePickerBuilder(ClockDetailsActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                currentTime.setText(getTime(date));
                startTimeStr = currentTime.getText().toString();
                btn = true;
            }
        }).setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }
}
