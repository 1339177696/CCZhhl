package com.hulian.oa.iac.activity;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hulian.oa.R;
import com.hulian.oa.iac.base.BaseActivity;
import com.hulian.oa.iac.bean.ApproveBean;
import com.hulian.oa.iac.util.UntilsTime;
//import com.yanzhenjie.nohttp.NoHttp;
//import com.yanzhenjie.nohttp.RequestMethod;
//import com.yanzhenjie.nohttp.rest.Request;
//import com.yanzhenjie.nohttp.rest.Response;
//import com.zhhl.marketauthority.R;
//import com.zhhl.marketauthority.activity.BaseActivity;
//import com.zhhl.marketauthority.bean.ApproveBean;
//import com.zhhl.marketauthority.config.UrlConfig;
//import com.zhhl.marketauthority.nohttp.listener.HttpListener;
//import com.zhhl.marketauthority.util.ToastUtils;
//import com.zhhl.marketauthority.util.UntilsTime;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

//相关认证
public class ApproveActivity extends BaseActivity {
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.et_approve_item)
    EditText et_approve_item;//认证项目
    @BindView(R.id.et_approve_agency)
    EditText et_approve_agency;//认证机构
    @BindView(R.id.et_approve_date)
    TextView et_approve_date;//认证日期
    @BindView(R.id.et_approve_exp_date)
    EditText et_approve_exp_date;//认证有效期
    ImageView back;
    private ImageView change;
    ApproveBean.ObjBean.ResBean resBean;
    @Override
    protected int setContentView() {
        return R.layout.activity_approve;
    }

    @Override
    protected void onCreate() {
        addBack();
        setTitleText("申请许可类别");
        change = addChange();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSate();
//                mAdapter.setToChange(true);
            }
        });
        getData();
    }
    private void getData() {

        resBean = (ApproveBean.ObjBean.ResBean) getIntent().getExtras().get("data");
        setData(resBean);

    }
//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response<String> response) {
//            if (what==1){
//                ToastUtils.show(mContext,"修改成功");
//            }
//        }
//
//        @Override
//        public void onFailed(int what, Response<String> response) {
//            ToastUtils.show(mContext,"请求失败");
//        }
//    };
    @OnClick({R.id.et_approve_date,R.id.et_approve_exp_date,R.id.submit})
    public void onViewClick(View view){
        switch (view.getId()) {
            case R.id.et_approve_date:
                //时间选择器
//                TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
//                    @Override
//                    public void onTimeSelect(Date date, View v) {
//                        String time = UntilsTime.getTime(date);
//                        et_approve_date.setText(time);
//
//                    }
//                }).setType(new boolean[]{true, true, true, true, true, true}).build();
//                pvTime.show();
                break;
            case R.id.et_approve_exp_date:
                  //时间选择器
//                TimePickerView pvTime1 = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
//                    @Override
//                    public void onTimeSelect(Date date, View v) {
//                        String time = UntilsTime.getTime(date);
//                        et_approve_exp_date.setText(time);
//
//                    }
//                }).setType(new boolean[]{true, true, true, true, true, true}).build();
//                pvTime1.show();
                break;
            case R.id.submit:
                uploadData();
                break;
        }

    }

    private void uploadData() {
//        submit.setVisibility(View.INVISIBLE);
//        change.setVisibility(View.VISIBLE);
//        Request<String> request = NoHttp.createStringRequest(UrlConfig.PATH_UPLOAD_DATA, RequestMethod.POST);
//        Map<String,Object> map = new HashMap<>();
//        map.put("id",resBean.getN_CC_ID());//主键表ID
//        map.put("tjlx","3");//提交类型
//        map.put("v_cc_item",et_approve_item.getText());//认证项目
//        map.put("v_cc_organization",et_approve_agency.getText());//代表作品
//        map.put("v_year",et_approve_agency.getText());//代表作品
//        request.add(map);
//        request(1,request,httpListener,true,true);
    }

    private void changeSate() {
        change.setVisibility(View.INVISIBLE);
        submit.setVisibility(View.VISIBLE);
        et_approve_item.setEnabled(true);
        et_approve_agency.setEnabled(true);
//        et_approve_date.setEnabled(true);
        et_approve_exp_date.setEnabled(true);
        et_approve_item.setBackground(ContextCompat.getDrawable(mContext,R.drawable.background_arc_3));
        et_approve_agency.setBackground(ContextCompat.getDrawable(mContext,R.drawable.background_arc_3));
        et_approve_exp_date.setBackground(ContextCompat.getDrawable(mContext,R.drawable.background_arc_3));
    }

    private void setData(ApproveBean.ObjBean.ResBean resBean) {
        et_approve_item.setText(resBean.getV_CC_ITEM());//认证项目
        et_approve_agency.setText(resBean.getV_CC_ORGANIZATION());//认证机构
        et_approve_date.setText(resBean.getD_CC_BEGIN_DATE());//认证日期
        et_approve_exp_date.setText(resBean.getV_YEAR());//认证有效期

    }

    protected void addBack() {
        back = findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
