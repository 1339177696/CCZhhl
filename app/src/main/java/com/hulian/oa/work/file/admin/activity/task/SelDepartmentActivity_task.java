package com.hulian.oa.work.file.admin.activity.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.Department;
import com.hulian.oa.bean.People;
import com.hulian.oa.me.l_adapter.DepartmentAdapter;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

//选择部门，人员
public class SelDepartmentActivity_task extends BaseActivity {
    //创建显示列表的listView
    private ListView listView;
    List<Department> memberList=new ArrayList<>();
    //创建适配器对象
    private DepartmentAdapter adapter;
    private ImageView iv_back;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_info);
        EventBus.getDefault().register(this);
        //初始化页面对象
        init();
        //将数据显示在页面上
        initDatas();
    }
    public void onEventMainThread(  List<People>  event) {
            finish();
    }
    public void init() {
        listView = (ListView) findViewById(R.id.lv_text_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new  Intent(SelDepartmentActivity_task.this,SelPeopleActivity_task_people.class);
                intent.putExtra("partId",memberList.get(i).getDeptId());
                startActivity(intent);
            }
        });
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
}
