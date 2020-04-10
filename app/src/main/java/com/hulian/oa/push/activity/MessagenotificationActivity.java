package com.hulian.oa.push.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qgl on 2019/11/29 10:38.
 */
public class MessagenotificationActivity extends BaseActivity {
    @BindView(R.id.swipe_ly)
    SwipeRefreshLayout swipe_ly;
    //红点
    @BindView(R.id.yj_qgl_number)
    TextView yj_qgl_number;
    @BindView(R.id.tg_qgl_number)
    TextView tg_qgl_number;
    @BindView(R.id.rc_qgl_number)
    TextView rc_qgl_number;
    @BindView(R.id.hy_qgl_number)
    TextView hy_qgl_number;
    @BindView(R.id.qj_qgl_number)
    TextView qj_qgl_number;
    @BindView(R.id.bx_qgl_number)
    TextView bx_qgl_number;
    @BindView(R.id.rw_qgl_number)
    TextView rw_qgl_number;
    @BindView(R.id.gw_qgl_number)
    TextView gw_qgl_number;
    //标题
    @BindView(R.id.yj_qgl_title)
    TextView yj_qgl_title;
    @BindView(R.id.tz_qgl_title)
    TextView tz_qgl_title;
    @BindView(R.id.rc_qgl_title)
    TextView rc_qgl_title;
    @BindView(R.id.hy_qgl_title)
    TextView hy_qgl_title;
    @BindView(R.id.qj_qgl_title)
    TextView qj_qgl_title;
    @BindView(R.id.bx_qgl_title)
    TextView bx_qgl_title;
    @BindView(R.id.rw_qgl_title)
    TextView rw_qgl_title;
    @BindView(R.id.gw_qgl_title)
    TextView gw_qgl_title;
    //时间
    @BindView(R.id.yj_qgl_time)
    TextView yj_qgl_time;
    @BindView(R.id.tz_qgl_time)
    TextView tz_qgl_time;
    @BindView(R.id.rc_qgl_time)
    TextView rc_qgl_time;
    @BindView(R.id.hy_qgl_time)
    TextView hy_qgl_time;
    @BindView(R.id.qj_qgl_time)
    TextView qj_qgl_time;
    @BindView(R.id.bx_qgl_time)
    TextView bx_qgl_time;
    @BindView(R.id.rw_qgl_time)
    TextView rw_qgl_time;
    @BindView(R.id.gw_qgl_time)
    TextView gw_qgl_time;
    //内容
    @BindView(R.id.yj_qgl_content)
    TextView yj_qgl_content;
    @BindView(R.id.tz_qgl_content)
    TextView tz_qgl_content;
    @BindView(R.id.rc_qgl_content)
    TextView rc_qgl_content;
    @BindView(R.id.hy_qgl_content)
    TextView hy_qgl_content;
    @BindView(R.id.qj_qgl_content)
    TextView qj_qgl_content;
    @BindView(R.id.bx_qgl_content)
    TextView bx_qgl_content;
    @BindView(R.id.rw_qgl_content)
    TextView rw_qgl_content;
    @BindView(R.id.gw_qgl_content)
    TextView gw_qgl_content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagenotificationactivity);
        ButterKnife.bind(this);
        swipe_ly.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.yj_rela, R.id.tg_rela, R.id.rc_rela, R.id.hy_rela, R.id.qj_rela, R.id.bx_rela, R.id.rw_rela, R.id.gw_rela})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.yj_rela:
                Intent intent1 = new Intent();
                intent1.putExtra("type", "邮件通知");
                intent1.putExtra("ty", "1");
                intent1.setClass(MessagenotificationActivity.this, MessagenotificationDeteilsActivity.class);
                startActivity(intent1);
                break;
            case R.id.tg_rela:
                Intent intent2 = new Intent();
                intent2.putExtra("type", "通知通告");
                intent2.putExtra("ty", "5");
                intent2.setClass(MessagenotificationActivity.this, MessagenotificationDeteilsActivity.class);
                startActivity(intent2);
                break;
            case R.id.rc_rela:
                Intent intent3 = new Intent();
                intent3.putExtra("type", "日程通知");
                intent3.putExtra("ty", "6");
                intent3.setClass(MessagenotificationActivity.this, MessagenotificationDeteilsActivity.class);
                startActivity(intent3);
                break;
            case R.id.hy_rela:
                Intent intent4 = new Intent();
                intent4.putExtra("type", "会议通知");
                intent4.putExtra("ty", "4");
                intent4.setClass(MessagenotificationActivity.this, MessagenotificationDeteilsActivity.class);
                startActivity(intent4);
                break;
            case R.id.qj_rela:
                Intent intent5 = new Intent();
                intent5.putExtra("type", "请假通知");
                intent5.putExtra("ty", "3");
                intent5.setClass(MessagenotificationActivity.this, MessagenotificationDeteilsActivity.class);
                startActivity(intent5);
                break;
            case R.id.bx_rela:
                Intent intent6 = new Intent();
                intent6.putExtra("type", "报销通知");
                intent6.putExtra("ty", "8");
                intent6.setClass(MessagenotificationActivity.this, MessagenotificationDeteilsActivity.class);
                startActivity(intent6);
                break;
            case R.id.rw_rela:
                Intent intent7 = new Intent();
                intent7.putExtra("type", "任务通知");
                intent7.putExtra("ty", "7");
                intent7.setClass(MessagenotificationActivity.this, MessagenotificationDeteilsActivity.class);
                startActivity(intent7);
                break;
            case R.id.gw_rela:
                Intent intent8 = new Intent();
                intent8.putExtra("type", "公文通知");
                intent8.putExtra("ty", "2");
                intent8.setClass(MessagenotificationActivity.this, MessagenotificationDeteilsActivity.class);
                startActivity(intent8);
                break;
        }
    }

    //网络请求
    private void getData() {
        RequestParams params = new RequestParams();
        params.put("userId", SPUtils.get(MessagenotificationActivity.this, "userId", "").toString());
        HttpRequest.postNotice(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                swipe_ly.setRefreshing(false);
                //需要转化为实体对象
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONArray data = result.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject value = data.getJSONObject(i);
                        JSONObject msg = data.getJSONObject(i).getJSONObject("msg");
                        //获取到title值
                        String type = value.getString("type");
                        String createTime = "";
                        String content = "";
                        if (msg.has("content")){
                             content = msg.getString("content")=="null"?"":msg.getString("content");
                        }
                        if (msg.has("createTime")){
                            createTime = msg.getString("createTime")=="null"?"":msg.getString("createTime");
                        }

                        Log.e("通知返回值", type);
                        /** 1:邮件 2：公文 3：请假 4会议 5：公告 6：日程 7:任务 8：报销*/
                        if (type.equals("1")) {
                            String count = value.getString("count");
                            if (Integer.parseInt(count) < 1) {
                                yj_qgl_number.setVisibility(View.GONE);
                            } else {
                                yj_qgl_number.setVisibility(View.VISIBLE);
                            }
                            yj_qgl_time.setText(createTime);
                            yj_qgl_content.setText(content);
                        } else if (type.equals("5")) {
                            String count = value.getString("count");
                            if (Integer.parseInt(count) < 1) {
                                tg_qgl_number.setVisibility(View.GONE);
                            } else {
                                tg_qgl_number.setVisibility(View.VISIBLE);
                            }
                            tz_qgl_time.setText(createTime);
                            tz_qgl_content.setText(content);
                        } else if (type.equals("6")) {
                            String count = value.getString("count");
                            if (Integer.parseInt(count) < 1) {
                                rc_qgl_number.setVisibility(View.GONE);
                            } else {
                                rc_qgl_number.setVisibility(View.VISIBLE);
                            }
                            rc_qgl_time.setText(createTime);
                            rc_qgl_content.setText(content);
                        } else if (type.equals("4")) {
                            String count = value.getString("count");
                            if (Integer.parseInt(count) < 1) {
                                hy_qgl_number.setVisibility(View.GONE);
                            } else {
                                hy_qgl_number.setVisibility(View.VISIBLE);
                            }
                            hy_qgl_time.setText(createTime);
                            hy_qgl_content.setText(content);
                        } else if (type.equals("3")) {
                            String count = value.getString("count");
                            if (Integer.parseInt(count) < 1) {
                                qj_qgl_number.setVisibility(View.GONE);
                            } else {
                                qj_qgl_number.setVisibility(View.VISIBLE);
                            }
                            qj_qgl_time.setText(createTime);
                            qj_qgl_content.setText(content);
                        } else if (type.equals("8")) {
                            String count = value.getString("count");
                            if (Integer.parseInt(count) < 1) {
                                bx_qgl_number.setVisibility(View.GONE);
                            } else {
                                bx_qgl_number.setVisibility(View.VISIBLE);
                            }
                            bx_qgl_time.setText(createTime);
                            bx_qgl_content.setText(content);
                        } else if (type.equals("7")) {
                            String count = value.getString("count");
                            if (Integer.parseInt(count) < 1) {
                                rw_qgl_number.setVisibility(View.GONE);
                            } else {
                                rw_qgl_number.setVisibility(View.VISIBLE);
                            }
                            rw_qgl_time.setText(createTime);
                            rw_qgl_content.setText(content);
                        } else if (type.equals("2")) {
                            String count = value.getString("count");
                            if (Integer.parseInt(count) < 1) {
                                gw_qgl_number.setVisibility(View.GONE);
                            } else {
                                gw_qgl_number.setVisibility(View.VISIBLE);
                            }
                            gw_qgl_time.setText(createTime);
                            gw_qgl_content.setText(content);
                        }
                    }
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

    //Activity创建或者从被覆盖、后台重新回到前台时被调用
    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }
}
