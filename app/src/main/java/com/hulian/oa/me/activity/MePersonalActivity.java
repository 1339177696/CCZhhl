package com.hulian.oa.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hulian.oa.R;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.address.AddressFragment2;
import com.hulian.oa.fragment.WechatFragment;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.news.fragment.NewsFragment;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.work.fragment.WorkFragment;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 作者：qgl 时间： 2020/6/4 09:26
 * Describe: 个人信息
 */
public class MePersonalActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_me_person_login_name)
    TextView tvMePersonLoginName;
    @BindView(R.id.tv_me_person_user_name)
    EditText tvMePersonUserName;
    @BindView(R.id.tv_me_person_phone)
    EditText tvMePersonPhone;
    @BindView(R.id.reboutn_one)
    RadioButton reboutnOne;
    @BindView(R.id.reboutn_two)
    RadioButton reboutnTwo;
    @BindView(R.id.tv_me_person_email)
    TextView tvMePersonEmail;
    @BindView(R.id.re_change_password)
    RelativeLayout reChangePassword;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.rd_group)
    RadioGroup rdGroup;
    private String loginName = "";
    private String userName = "";
    private String phone = "";
    private String sex ;
    private String email = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mepersonalactivity);
        ButterKnife.bind(this);
        initView();
        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radiobutton = findViewById(group.getCheckedRadioButtonId());
                sex = radiobutton.getTag().toString();
            }
        });
    }

    public void initView(){
        tvType.setText(SPUtils.get(mContext, "nickname", "").toString().substring(SPUtils.get(mContext, "nickname", "").toString().length() - 2, SPUtils.get(mContext, "nickname", "").toString().length()));
        tvMePersonLoginName.setText(SPUtils.get(mContext, "loginName", "").toString());
        tvMePersonUserName.setText(SPUtils.get(mContext, "nickname", "").toString());
        tvMePersonPhone.setText(SPUtils.get(mContext, "phonenumber", "").toString());
        tvMePersonEmail.setText(SPUtils.get(mContext, "email", "").toString());
        sex = SPUtils.get(mContext, "sex", "").toString();
        // 性别
        if (sex.equals("0")){
            reboutnOne.setChecked(true);
        }else {
            reboutnTwo.setChecked(true);
        }
    }

    @OnClick({R.id.iv_back, R.id.btn_save, R.id.re_change_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_save:
                postData();
                break;
            case R.id.re_change_password:
                startActivityForResult(new Intent(MePersonalActivity.this,MeSavePersonalActivity.class),2);
                break;
        }
    }

    // 提交数据
    public void postData() {
        if (TextUtils.isEmpty(tvMePersonLoginName.getText().toString().trim())) {
            ToastHelper.showToast(mContext, "未获取登录名");
            return;
        }
        if (TextUtils.isEmpty(tvMePersonUserName.getText().toString().trim())) {
            ToastHelper.showToast(mContext, "请输入名称");
            return;
        }
        if (TextUtils.isEmpty(tvMePersonPhone.getText().toString().trim())) {
            ToastHelper.showToast(mContext, "请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(sex)) {
            ToastHelper.showToast(mContext, "未获取性别");
            return;
        }
        if (TextUtils.isEmpty(tvMePersonEmail.getText().toString().trim())) {
            ToastHelper.showToast(mContext, "未获取邮箱");
            return;
        }
        loadDialog.show();
        RequestParams params = new RequestParams();
        params.put("userId", SPUtils.get(mContext, "userId", "").toString());
        params.put("loginName",tvMePersonLoginName.getText().toString().trim());
        params.put("userName",tvMePersonUserName.getText().toString().trim());
        params.put("phonenumber",tvMePersonPhone.getText().toString().trim());
        params.put("sex",sex);
        params.put("email",tvMePersonEmail.getText().toString().trim());
        HttpRequest.getPerson_information(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                loadDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(responseObj.toString());
                    if (object.getString("code").equals("0")){
                        Toast.makeText(mContext,"保存成功",Toast.LENGTH_LONG).show();
                        SPUtils.put(mContext,"nickname",tvMePersonUserName.getText().toString().trim());
                        SPUtils.put(mContext,"phonenumber",tvMePersonPhone.getText().toString().trim());
                        SPUtils.put(mContext,"sex",sex);
                        EventBus.getDefault().post(new WechatFragment());
                        EventBus.getDefault().post(new WorkFragment());
                        EventBus.getDefault().post(new AddressFragment2());
                        EventBus.getDefault().post(new NewsFragment());
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(OkHttpException failuer) {
                loadDialog.dismiss();
                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 2:
                initView();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
