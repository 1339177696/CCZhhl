package com.hulian.oa.push.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.push.adpter.PersonAdapter;
import com.hulian.oa.push.bean.PersonBean;
import com.hulian.oa.utils.SPUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qgl on 2019/12/9 9:49.
 */
public class PersonqglActivity extends BaseActivity {
    @BindView(R.id.person_listview)
    ListView person_listview;
    @BindView(R.id.iv_back)
    RelativeLayout iv_back;
    private PersonAdapter adapter;
    List <PersonBean> list = new ArrayList<>();
    PersonBean personBean;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personqglactivity);
        ButterKnife.bind(this);
        getPerson();
    }
    // 请求审批人,抄送人
    public void getPerson(){
        RequestParams params = new RequestParams();
        params.put("deptId",SPUtils.get(mContext, "deptId", "").toString());
        params.put("userId", SPUtils.get(mContext, "userId", "").toString());
        params.put("num","0");
        HttpRequest.getPerson(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONObject data = result.getJSONObject("data");
                    JSONArray deptUser = data.getJSONArray("deptUser");
                    list = new ArrayList<>();
                    personBean = new PersonBean();
                    for (int i=0;i<deptUser.length();i++){
                        personBean = new PersonBean();
                        JSONObject value = deptUser.getJSONObject(i);
                        Log.e("userName",value.getString("userName"));
                        personBean.setUserId(value.getString("userId"));
                        personBean.setUserName(value.getString("userName"));
                        list.add(personBean);
                    }
                    adapter = new PersonAdapter(PersonqglActivity.this, list);
                    person_listview.setAdapter(adapter);
                    person_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            Intent mIntent = new Intent();
                            mIntent.putExtra("userName",list.get(position).getUserName());
                            mIntent.putExtra("userId",list.get(position).getUserId());
                            setResult(RESULT_OK, mIntent);
                            finish();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });

    }
    @OnClick(R.id.iv_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }

    }

}
