package com.hulian.oa.iac.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hulian.oa.R;
import com.hulian.oa.iac.adapter.GridImageSecAdapter;
import com.hulian.oa.iac.base.BaseActivity;
import com.hulian.oa.iac.bean.ManageStaffBean;
import com.hulian.oa.iac.util.ScreenUtils;
import com.hulian.oa.iac.util.UntilsTime;
import com.hulian.oa.iac.view.FullyGridLayoutManager;
import com.hulian.oa.iac.view.GridSpacingItemNotBothDecoration;
//import com.yanzhenjie.nohttp.NoHttp;
//import com.yanzhenjie.nohttp.RequestMethod;
//import com.yanzhenjie.nohttp.rest.Request;
//import com.yanzhenjie.nohttp.rest.Response;
//import com.zhhl.marketauthority.R;
//import com.zhhl.marketauthority.activity.BaseActivity;
//import com.zhhl.marketauthority.adapter.GridImageSecAdapter;
//import com.zhhl.marketauthority.bean.ManageStaffBean;
//import com.zhhl.marketauthority.config.UrlConfig;
//import com.zhhl.marketauthority.nohttp.listener.HttpListener;
//import com.zhhl.marketauthority.util.ScreenUtils;
//import com.zhhl.marketauthority.util.ToastUtils;
//import com.zhhl.marketauthority.util.UntilsTime;
//import com.zhhl.marketauthority.view.FullyGridLayoutManager;
//import com.zhhl.marketauthority.view.GridSpacingItemNotBothDecoration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//管理专业作业人员情况
public class ManageStaffActivity extends BaseActivity {
    @BindView(R.id.et_homework)
    EditText et_homework;//作业项目
    @BindView(R.id.et_username)
    EditText et_username;//姓名
    @BindView(R.id.et_age)
    EditText et_age;//年龄
    @BindView(R.id.et_education)
    EditText et_education;//学历
    @BindView(R.id.et_profession)
    EditText et_profession;//专业
    @BindView(R.id.et_job_name)
    EditText et_job_name;//职称
    @BindView(R.id.et_work_years)
    EditText et_work_years;//从事专业工作年限
    @BindView(R.id.et_profession_card)
    EditText et_profession_card;//持专业证
    @BindView(R.id.et_remark)
    EditText et_remark;//备注(注明从事专业)
    @BindView(R.id.et_updatetime)
    TextView et_updatetime;//评审时间
    @BindView(R.id.et_idea)
    EditText et_idea;//评审意见
    @BindView(R.id.radio_result)
    RadioGroup radio_result;//评审结果
    @BindView(R.id.regular)
    RadioButton regular;//合格
    @BindView(R.id.unregular)
    RadioButton unregular;//不合格
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.submit)
    TextView submit;
    private ImageView back;
    private ImageView change;
    private Boolean markBool = true;
    FullyGridLayoutManager manager;
    GridImageSecAdapter adapter;
    private String result = "0";
    List<String> selectList = new ArrayList<String>();
    private static final int REQUESTCODE = 100;
    ManageStaffBean.ObjBean.ResBean resBean;
    @Override
    protected int setContentView() {
        return R.layout.activity_managestaff;
    }

    @Override
    protected void onCreate() {
        addBack();
        setTitleText("管理、专业、作业人员情况");
        change = addChange();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSate(markBool);
            }
        });
        init();
        getData();
    }
    private void getData() {

         resBean = (ManageStaffBean.ObjBean.ResBean) getIntent().getExtras().get("data");
        setData(resBean);

    }
    @OnClick({R.id.submit,R.id.et_updatetime,})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.submit:
                uploadData();
                break;
            case R.id.et_updatetime:
                TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String time = UntilsTime.getTime(date);
                        et_updatetime.setText(time);

                    }
                }).setType(new boolean[]{true, true, true, true, true, true}).isDialog(false).build();
                pvTime.show();
                break;
        }
    }

    private void uploadData() {
         String homework = et_homework.getText().toString();//作业项目
        String username = et_username.getText().toString();//姓名
        String age = et_age.getText().toString();//年龄
        String education = et_education.getText().toString();//学历
        String profession = et_profession.getText().toString();//专业
        String remark = et_remark.getText().toString();//备注
        String job_name = et_job_name.getText().toString();//职称
        String work_years = et_work_years.getText().toString();//从事专业年限
        String profession_card = et_profession_card.getText().toString();//持专业证
        String updatetime = et_updatetime.getText().toString();//评审时间
        String idea = et_idea.getText().toString();//评审意见
        submit.setVisibility(View.INVISIBLE);
        change.setVisibility(View.VISIBLE);
//        Request<String> request = NoHttp.createStringRequest(UrlConfig.PATH_UPLOAD_DATA, RequestMethod.POST);
//        Map<String,Object> map = new HashMap<>();
//        map.put("id",resBean.getN_M_ID());//主键表ID
//        map.put("tjlx","9");
//        map.put("v_m_item",homework);
//        map.put("v_m_name",username);
//        map.put("v_m_age",age);
//        map.put("v_m_education",education);
//        map.put("v_m_specialty",profession);
//        map.put("v_m_title",job_name);
//        map.put("v_m_year",work_years);
//        map.put("v_m_credentials",profession_card);
//        map.put("v_m_remark",remark);
//        map.put("psyj",idea);
//        map.put("pssj",updatetime);
//        map.put("pszt",result);
//        request.add(map);
//        request(1,request,httpListener,true,true);
    }

//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response<String> response) {
//            if(what==1){
//                ToastUtils.show(mContext,"修改成功");
//            }
//        }
//
//        @Override
//        public void onFailed(int what, Response<String> response) {
//            ToastUtils.show(mContext,"请求失败");
//        }
//    };
    private void setData(ManageStaffBean.ObjBean.ResBean resBean) {
         et_homework.setText(resBean.getV_M_ITEM());//作业项目
         et_username.setText(resBean.getV_M_NAME());//姓名
         et_age.setText(resBean.getV_M_AGE());//年龄
         et_education.setText(resBean.getV_M_EDUCATION());//学历
         et_profession.setText(resBean.getV_M_SPECIALTY());//专业
         et_job_name.setText(resBean.getV_M_TITLE());//职称
         et_work_years.setText(resBean.getV_M_YEAR());//从事专业工作年限
         et_profession_card.setText(resBean.getV_M_CREDENTIALS());//持专业证
         et_remark.setText(resBean.getV_M_REMARK());//备注(注明从事专业)
        et_updatetime.setText(resBean.getPSSJ());
        et_idea.setText(resBean.getPSYJ());
        if (resBean.getPSZT().equals("0")){
            regular.setChecked(false);
            unregular.setChecked(false);
        }else if(resBean.getPSZT().equals("1")){
            regular.setChecked(true);
            unregular.setChecked(false);
        }else if(resBean.getPSZT().equals("2")){
            regular.setChecked(false);
            unregular.setChecked(true);
        }

    }

    private void init() {
        manager = new FullyGridLayoutManager(this,
                1, GridLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(new GridSpacingItemNotBothDecoration(5,
                ScreenUtils.dip2px(ManageStaffActivity.this, 4), true, false));
        adapter = new GridImageSecAdapter(ManageStaffActivity.this, onAddPicClickListener,onDelete);
//        adapter.setList(selectList);//设置数据
        recycler.setAdapter(adapter);
        radio_result.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.regular:
                        result = "1";
                        break;
                    case R.id.unregular:
                        result = "2";
                        break;
                }
            }
        });
    }

    private GridImageSecAdapter.onAddPicClickListener onAddPicClickListener = new GridImageSecAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick() {

            manager = new FullyGridLayoutManager(ManageStaffActivity.this,
                    selectList.size()+1, GridLayoutManager.VERTICAL, false);
            recycler.setLayoutManager(manager);
            getPermissions(REQUESTCODE);

        }
    };
    private GridImageSecAdapter.OnDelete onDelete = new GridImageSecAdapter.OnDelete() {
        @Override
        public void onItemDelete(int mark) {

        }
    };
    private void changeSate(Boolean bool) {
        et_homework.setEnabled(bool);
        et_username.setEnabled(bool);
        et_age.setEnabled(bool);
        et_education.setEnabled(bool);
        et_profession.setEnabled(bool);
        et_job_name.setEnabled(bool);
        et_work_years.setEnabled(bool);
        et_profession_card.setEnabled(bool);
        et_remark.setEnabled(bool);
        et_updatetime.setEnabled(bool);
        et_idea.setEnabled(bool);
        et_homework.setBackground(ContextCompat.getDrawable(ManageStaffActivity.this,R.drawable.background_arc_3));
        et_username.setBackground(ContextCompat.getDrawable(ManageStaffActivity.this,R.drawable.background_arc_3));
        et_age.setBackground(ContextCompat.getDrawable(ManageStaffActivity.this,R.drawable.background_arc_3));
        et_education.setBackground(ContextCompat.getDrawable(ManageStaffActivity.this,R.drawable.background_arc_3));
        et_profession.setBackground(ContextCompat.getDrawable(ManageStaffActivity.this,R.drawable.background_arc_3));
        et_job_name.setBackground(ContextCompat.getDrawable(ManageStaffActivity.this,R.drawable.background_arc_3));
        et_work_years.setBackground(ContextCompat.getDrawable(ManageStaffActivity.this,R.drawable.background_arc_3));
        et_profession_card.setBackground(ContextCompat.getDrawable(ManageStaffActivity.this,R.drawable.background_arc_3));
        et_remark.setBackground(ContextCompat.getDrawable(ManageStaffActivity.this,R.drawable.background_arc_3));
        et_updatetime.setBackground(ContextCompat.getDrawable(ManageStaffActivity.this,R.drawable.background_arc_3));
        et_idea.setBackground(ContextCompat.getDrawable(ManageStaffActivity.this,R.drawable.background_arc_3));
        if (bool){
            markBool = false;
        }else {
            markBool = true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 101) {
            Log.i("CJT", "picture");
            String path = data.getStringExtra("path");
//            photo.setImageBitmap(BitmapFactory.decodeFile(path));
            selectList.add(path);
        }
        if (resultCode == 102) {
            Log.i("CJT", "video");
            String path = data.getStringExtra("path");
        }
        if (resultCode == 103) {
            Toast.makeText(this, "请检查相机权限~", Toast.LENGTH_SHORT).show();
        }
    }
}
