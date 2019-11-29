package com.hulian.oa.qglactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qgl on 2019/11/29 10:38.
 */
public class MessagenotificationActivity extends BaseActivity {
    @BindView(R.id.swipe_ly)
    SwipeRefreshLayout swipe_ly;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagenotificationactivity);
        ButterKnife.bind(this);
        swipe_ly.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Log.e("刷新了","刷新了");
                swipe_ly.setRefreshing(false);
            }
        });
    }
    @OnClick({R.id.iv_back,R.id.yj_rela,R.id.tg_rela,R.id.rc_rela,R.id.hy_rela,R.id.qj_rela,R.id.bx_rela,R.id.rw_rela,R.id.gw_rela})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.yj_rela:
                Intent intent1 = new Intent();
                intent1.putExtra("type","邮件通知");
                intent1.setClass(MessagenotificationActivity.this,MessagenotificationDeteilsActivity.class);
                startActivity(intent1);
                break;
            case R.id.tg_rela:
                Intent intent2 = new Intent();
                intent2.putExtra("type","通知通告");
                intent2.setClass(MessagenotificationActivity.this,MessagenotificationDeteilsActivity.class);
                startActivity(intent2);
                break;
            case R.id.rc_rela:
                Intent intent3 = new Intent();
                intent3.putExtra("type","日程通知");
                intent3.setClass(MessagenotificationActivity.this,MessagenotificationDeteilsActivity.class);
                startActivity(intent3);
                break;
            case R.id.hy_rela:
                Intent intent4 = new Intent();
                intent4.putExtra("type","会议通知");
                intent4.setClass(MessagenotificationActivity.this,MessagenotificationDeteilsActivity.class);
                startActivity(intent4);
                break;
            case R.id.qj_rela:
                Intent intent5 = new Intent();
                intent5.putExtra("type","请假通知");
                intent5.setClass(MessagenotificationActivity.this,MessagenotificationDeteilsActivity.class);
                startActivity(intent5);
                break;
            case R.id.bx_rela:
                Intent intent6 = new Intent();
                intent6.putExtra("type","报销通知");
                intent6.setClass(MessagenotificationActivity.this,MessagenotificationDeteilsActivity.class);
                startActivity(intent6);
                break;
            case R.id.rw_rela:
                Intent intent7 = new Intent();
                intent7.putExtra("type","任务通知");
                intent7.setClass(MessagenotificationActivity.this,MessagenotificationDeteilsActivity.class);
                startActivity(intent7);
                break;
            case R.id.gw_rela:
                Intent intent8 = new Intent();
                intent8.putExtra("type","公文通知");
                intent8.setClass(MessagenotificationActivity.this,MessagenotificationDeteilsActivity.class);
                startActivity(intent8);
                break;
        }
    }

    //网络请求
    private void getData(){

    }
}
