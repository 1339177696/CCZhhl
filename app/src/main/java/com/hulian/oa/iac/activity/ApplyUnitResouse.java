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
import com.hulian.oa.iac.bean.ApplyUnitResBean;
import com.hulian.oa.iac.util.ScreenUtils;
import com.hulian.oa.iac.view.FullyGridLayoutManager;
import com.hulian.oa.iac.view.GridSpacingItemNotBothDecoration;
//import com.yanzhenjie.nohttp.NoHttp;
//import com.yanzhenjie.nohttp.RequestMethod;
//import com.yanzhenjie.nohttp.rest.Request;
//import com.yanzhenjie.nohttp.rest.Response;
//import com.zhhl.marketauthority.R;
//import com.zhhl.marketauthority.activity.BaseActivity;
//import com.zhhl.marketauthority.adapter.GridImageSecAdapter;
//import com.zhhl.marketauthority.bean.ApplyUnitResBean;
//import com.zhhl.marketauthority.config.UrlConfig;
//import com.zhhl.marketauthority.nohttp.listener.HttpListener;
//import com.zhhl.marketauthority.util.GsonUtil;
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

//申请单位资源
public class ApplyUnitResouse extends BaseActivity {

    private ImageView back;
    private ImageView change;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.et_area_all)
    EditText et_area_all;//占地面积
    @BindView(R.id.et_area_office)
    EditText et_area_office;//办公场所面积
    @BindView(R.id.et_count_total)
    EditText et_count_total;//单位人员总数
    @BindView(R.id.et_count_exam)
    EditText et_count_exam;//质量检验人员数
    @BindView(R.id.et_count_hold)
    EditText et_count_hold;//持证焊接人员数
    @BindView(R.id.et_count_other)
    EditText et_count_other;//其他专业人员数

    @BindView(R.id.et_area_workshop)
    EditText et_area_workshop;//厂房面积
    @BindView(R.id.et_area_depot)
    EditText et_area_depot;//仓库面积
    @BindView(R.id.et_count_tech)
    EditText et_count_tech;//技术人员数
    @BindView(R.id.et_count_lossless)
    EditText et_count_lossless;//无损检验人数
    @BindView(R.id.et_count_operators)
    EditText et_count_operators;//作业人员数
    @BindView(R.id.et_output)
    EditText et_output;//年产值(万元)
    @BindView(R.id.et_updatetime)
    TextView et_updatetime;//评审时间
    @BindView(R.id.et_idea)
    EditText et_idea;//评审意见
    @BindView(R.id.radio_result)
    RadioGroup radio_result;//评审结果
    @BindView(R.id.regular)
    RadioButton regular;
    @BindView(R.id.unregular)
    RadioButton unregular;
    private boolean markBool;
    List<String> selectList = new ArrayList<String>();
    FullyGridLayoutManager manager;
    GridImageSecAdapter adapter;
    private static final int REQUESTCODE = 100;
    private String result = "0";
    ApplyUnitResBean.ObjBean.ResBean  resBean;
    @Override
    protected int setContentView() {
        return R.layout.activity_applyunit_res;
    }

    @Override
    protected void onCreate() {
        addBack();
        setTitleText("申请单位资源");
        change = addChange();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (markBool){
                    markBool = false;
                }else{
                    markBool = true;
                }
                changeSate(markBool);
//                mAdapter.setToChange(true);
            }
        });
        init();
        getData();
        //模拟数据
        setData(null);
    }
    private void getData() {

//        Intent intent = getIntent();
//        String N_L_ID = intent.getStringExtra("N_L_ID");
//        String N_B_ID = intent.getStringExtra("N_B_ID");
//        Request<String> request = NoHttp.createStringRequest(UrlConfig.PATH_COMMON, RequestMethod.POST);
//        request.add("N_L_ID",N_L_ID);
//        request.add("N_B_ID",N_B_ID);
//        request.add("N_TYPE","11");
//        request(0,request,httpListener,true,true);

    }
//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response<String> response) {
//            if (what==0){
//                ApplyUnitResBean applyUnitResBean = GsonUtil.GsonToBean(response.get(), ApplyUnitResBean.class);
//                resBean = applyUnitResBean.getObj().getRes();
//                if (resBean!=null){
//                    setData(resBean);
//                }
//            }else if(what==1){
//                ToastUtils.show(mContext,"修改成功");
//            }
//
//        }
//
//        @Override
//        public void onFailed(int what, Response<String> response) {
//            ToastUtils.show(mContext,"请求失败");
//        }
//    };
    @OnClick({R.id.et_updatetime,R.id.submit,})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.et_updatetime:
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(ApplyUnitResouse.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
//                        String time = UntilsTime.getTime(date);
//                        et_updatetime.setText(time);

                    }
                }).setType(new boolean[]{true, true, true, true, true, true}).build();
                pvTime.show();
                break;
            case R.id.submit:
                uploadData();
                break;
        }
    }

    private void uploadData() {
        String area_all = et_area_all.getText().toString();//占地面积
        String area_office = et_area_office.getText().toString();//办公场所面积
        String count_total = et_count_total.getText().toString();//单位人员总数
        String count_exam = et_count_exam.getText().toString();//质量检验人员数
        String count_hold = et_count_hold.getText().toString();//持证焊接人员数
        String count_other = et_count_other.getText().toString();//其他专业人员数
        String area_workshop = et_area_workshop.getText().toString();//厂房面积
        String area_depot = et_area_depot.getText().toString();//仓库面积
        String count_tech = et_count_tech.getText().toString();//技术人员数
        String count_lossless = et_count_lossless.getText().toString();//无损检验人数
        String count_operators = et_count_operators.getText().toString();//作业人员数
        String output = et_output.getText().toString();//年产值(万元)
        String updatetime = et_updatetime.getText().toString();
        String idea = et_idea.getText().toString();
//        Request<String> request = NoHttp.createStringRequest(UrlConfig.PATH_UPLOAD_DATA, RequestMethod.POST);
//        Map<String,Object> map = new HashMap<>();
//        map.put("id",resBean.getN_R_ID());
//        map.put("tjlx","11");
//        map.put("v_area_sum",area_all);
//        map.put("v_area_plant",area_workshop);
//        map.put("v_area_office",area_office);
//        map.put("v_area_warehouse",area_depot);
//        map.put("v_staff_sum",count_total);
//        map.put("v_technology_num",count_tech);
//        map.put("v_quality_num",count_exam);
//        map.put("v_breakdown_num",count_lossless);
//        map.put("v_soldering_num",count_hold);
//        map.put("v_work_num",count_operators);
//        map.put("v_other_num",count_other);
//        map.put("v_output_value",output);
//        map.put("pssj",updatetime);
//        map.put("psyj",idea);
//        map.put("pszt",result);
//        request.add(map);
//        request(1,request,httpListener,true,true);



    }

    private void init() {
        manager = new FullyGridLayoutManager(this,
                1, GridLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(new GridSpacingItemNotBothDecoration(5,
                ScreenUtils.dip2px(ApplyUnitResouse.this, 4), true, false));
        adapter = new GridImageSecAdapter(ApplyUnitResouse.this, onAddPicClickListener,onDelete);
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

            getPermissions(REQUESTCODE);

        }
    };
    private GridImageSecAdapter.OnDelete onDelete = new GridImageSecAdapter.OnDelete() {
        @Override
        public void onItemDelete(int mark) {
            if (selectList.size()<5){
                manager = new FullyGridLayoutManager(mContext,
                        mark+1, GridLayoutManager.VERTICAL, false);
                recycler.setLayoutManager(manager);
            }else{
                manager = new FullyGridLayoutManager(mContext,
                        5, GridLayoutManager.VERTICAL, false);
                recycler.setLayoutManager(manager);
            }
        }
    };
    private void setData(ApplyUnitResBean.ObjBean.ResBean resBean) {

        resBean = new ApplyUnitResBean.ObjBean.ResBean();

        resBean.setV_AREA_SUM("3000");//占地面积
        resBean.setV_AREA_OFFICE("1500");//办公场所面积
        resBean.setV_STAFF_SUM("300");//单位人员总数
        resBean.setV_QUALITY_NUM("20");//质量检验人员数
        resBean.setV_SOLDERING_NUM("10");//持证焊接人员数
        resBean.setV_OTHER_NUM("58");//其他专业人员数
        resBean.setV_AREA_PLANT("100");//厂房面积
        resBean.setV_AREA_WAREHOUSE("1400");//仓库面积
        resBean.setV_TECHNOLOGY_NUM("90");//技术人员数
        resBean.setV_BREAKDOWN_NUM("100");
        resBean.setV_WORK_NUM("50");
        resBean.setV_OUTPUT_VALUE("3000");
        resBean.setPSZT("0");

        et_area_all.setText(resBean.getV_AREA_SUM());//占地面积
        et_area_office.setText(resBean.getV_AREA_OFFICE());//办公场所面积
        et_count_total.setText(resBean.getV_STAFF_SUM());//单位人员总数
        et_count_exam.setText(resBean.getV_QUALITY_NUM());//质量检验人员数
        et_count_hold.setText(resBean.getV_SOLDERING_NUM());//持证焊接人员数
        et_count_other.setText(resBean.getV_OTHER_NUM());//其他专业人员数
        et_area_workshop.setText(resBean.getV_AREA_PLANT());//厂房面积
        et_area_depot.setText(resBean.getV_AREA_WAREHOUSE());//仓库面积
        et_count_tech.setText(resBean.getV_TECHNOLOGY_NUM());//技术人员数
        et_count_lossless.setText(resBean.getV_BREAKDOWN_NUM());//无损检验人数
        et_count_operators.setText(resBean.getV_WORK_NUM());//作业人员数
        et_output.setText(resBean.getV_OUTPUT_VALUE());//年产值(万元)
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 101) {
            Log.i("CJT", "picture");
            String path = data.getStringExtra("path");
//            photo.setImageBitmap(BitmapFactory.decodeFile(path));
            selectList.add(path);
            if (selectList.size()<5){
                manager = new FullyGridLayoutManager(this,
                        selectList.size()+1, GridLayoutManager.VERTICAL, false);
                recycler.setLayoutManager(manager);
//                recycler.addItemDecoration(new GridSpacingItemNotBothDecoration(selectList.size(),
//                                ScreenUtils.dip2px(ApplyUnitResouse.this, 4), true, false));
            }
            adapter.notifyDataSetChanged();
        }
        if (resultCode == 102) {
            Log.i("CJT", "video");
            String path = data.getStringExtra("path");
        }
        if (resultCode == 103) {
            Toast.makeText(this, "请检查相机权限~", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeSate(boolean bool) {

        et_area_all.setEnabled(bool);
        et_area_office.setEnabled(bool);
        et_count_total.setEnabled(bool);
        et_count_exam.setEnabled(bool);
        et_count_hold.setEnabled(bool);
        et_count_other.setEnabled(bool);
        et_area_workshop.setEnabled(bool);
        et_area_depot.setEnabled(bool);
        et_count_tech.setEnabled(bool);
        et_count_lossless.setEnabled(bool);
        et_count_operators.setEnabled(bool);
        et_output.setEnabled(bool);
        //设置评审结果的显示
        if (bool) {
            et_area_all.setBackground(ContextCompat.getDrawable(ApplyUnitResouse.this, R.drawable.background_arc_3));
            et_area_office.setBackground(ContextCompat.getDrawable(ApplyUnitResouse.this, R.drawable.background_arc_3));
            et_count_total.setBackground(ContextCompat.getDrawable(ApplyUnitResouse.this, R.drawable.background_arc_3));
            et_count_exam.setBackground(ContextCompat.getDrawable(ApplyUnitResouse.this, R.drawable.background_arc_3));
            et_count_hold.setBackground(ContextCompat.getDrawable(ApplyUnitResouse.this, R.drawable.background_arc_3));
            et_count_other.setBackground(ContextCompat.getDrawable(ApplyUnitResouse.this, R.drawable.background_arc_3));
            et_area_workshop.setBackground(ContextCompat.getDrawable(ApplyUnitResouse.this, R.drawable.background_arc_3));
            et_area_depot.setBackground(ContextCompat.getDrawable(ApplyUnitResouse.this, R.drawable.background_arc_3));
            et_count_tech.setBackground(ContextCompat.getDrawable(ApplyUnitResouse.this, R.drawable.background_arc_3));
            et_count_lossless.setBackground(ContextCompat.getDrawable(ApplyUnitResouse.this, R.drawable.background_arc_3));
            et_count_operators.setBackground(ContextCompat.getDrawable(ApplyUnitResouse.this, R.drawable.background_arc_3));
            et_output.setBackground(ContextCompat.getDrawable(ApplyUnitResouse.this, R.drawable.background_arc_3));
        } else {//修改完，保存后的效果设置
            et_area_all.setBackground(null);
            et_area_office.setBackground(null);
            et_count_total.setBackground(null);
            et_count_exam.setBackground(null);
            et_count_hold.setBackground(null);
            et_count_other.setBackground(null);
            et_area_workshop.setBackground(null);
            et_area_depot.setBackground(null);
            et_count_tech.setBackground(null);
            et_count_lossless.setBackground(null);
            et_count_operators.setBackground(null);
            et_output.setBackground(null);
        }
    }


}
