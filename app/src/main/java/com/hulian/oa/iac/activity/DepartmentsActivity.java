package com.hulian.oa.iac.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hulian.oa.R;
import com.hulian.oa.iac.adapter.GridImageSecAdapter;
import com.hulian.oa.iac.base.BaseActivity;
import com.hulian.oa.iac.bean.DepartmentsBean;
import com.hulian.oa.iac.util.DensityUtils;
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
//import com.zhhl.marketauthority.bean.DepartmentsBean;
//import com.zhhl.marketauthority.config.UrlConfig;
//import com.zhhl.marketauthority.nohttp.listener.HttpListener;
//import com.zhhl.marketauthority.util.DensityUtils;
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

//各部门人员组成
public class DepartmentsActivity extends BaseActivity {
    @BindView(R.id.et_updatetime)
    TextView et_updatetime;//评审时间
    @BindView(R.id.et_idea)
    EditText et_idea;//评审意见
    @BindView(R.id.radio_result)
    RadioGroup radio_result;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.regular)
    RadioButton regular;
    @BindView(R.id.unregular)
    RadioButton unregular;
    @BindView(R.id.table_depart)
    TableLayout table_depart;
    private ImageView back;
    private ImageView change;
    private Boolean markBool = true;
    List<String> selectList = new ArrayList<String>();
    private List<DepartmentsBean.ObjBean.ResBean> mData = new ArrayList<>();
    FullyGridLayoutManager manager;
    GridImageSecAdapter adapter;
    DepartmentsBean.ObjBean.ResBean resBean;
    private String result = "0";
    @Override
    protected int setContentView() {

        return R.layout.activity_departments;

    }

    @Override
    protected void onCreate() {
        addBack();
        setTitleText("各部门人员组成");
        change = addChange();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSate(markBool);
            }
        });
        change.setVisibility(View.INVISIBLE);
        init();
        getData();
        //模拟数据
        setTableData();

    }

    private void setTableData() {
        List<DepartmentsBean.ObjBean.ResBean> list = new ArrayList<>();
        DepartmentsBean.ObjBean.ResBean bean = new DepartmentsBean.ObjBean.ResBean();
        bean.setV_D_DEPARTMENT("技术部");
        bean.setV_D_PERSON("黄文龙");
        bean.setV_D_NUM("20");

        DepartmentsBean.ObjBean.ResBean bean1 = new DepartmentsBean.ObjBean.ResBean();
        bean1.setV_D_DEPARTMENT("人事部");
        bean1.setV_D_PERSON("曹宇");
        bean1.setV_D_NUM("4");

        DepartmentsBean.ObjBean.ResBean bean2 = new DepartmentsBean.ObjBean.ResBean();
        bean2.setV_D_DEPARTMENT("产品部");
        bean2.setV_D_PERSON("王俊杰");
        bean2.setV_D_NUM("6");

        list.add(bean);
        list.add(bean1);
        list.add(bean2);
        addTow(list);

    }

    //动态创建表格，填入数据
    private void addTow(List<DepartmentsBean.ObjBean.ResBean> mData) {
        for (int i=0;i<mData.size();i++){
            //TableRow的布局设置
            TableRow tableRow = new TableRow(mContext);
            tableRow.setDividerDrawable(ContextCompat.getDrawable(mContext,R.drawable.background_table));
            tableRow.setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING|LinearLayout.SHOW_DIVIDER_MIDDLE|LinearLayout.SHOW_DIVIDER_END);
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            for (int j=0;j<3;j++){
                //TableRow中的TextView布局设置
                TextView textView = new TextView(mContext);
                int len = DensityUtils.dip2px(mContext,  8.0f);
                textView.setPadding(len,len,len,len);
                textView.setLayoutParams(new TableRow.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
                textView.setTextColor(ContextCompat.getColor(mContext,R.color.title_text));
                if (j==0){//设置部门
                    textView.setText(mData.get(i).getV_D_DEPARTMENT());
                }else if(j==1){//设置负责人
                    textView.setText(mData.get(i).getV_D_PERSON());
                }else if(j==2){//设置人员数量
                    textView.setText(mData.get(i).getV_D_NUM());
                }
                tableRow.addView(textView);
            }
            table_depart.addView(tableRow);
        }
    }

    private void getData() {
//        Intent intent = getIntent();
//        String N_L_ID =  intent.getStringExtra("N_L_ID");
//        String N_B_ID =  intent.getStringExtra("N_B_ID");
//        Request<String> request = NoHttp.createStringRequest(UrlConfig.PATH_COMMON, RequestMethod.POST);
//        request.add("N_L_ID",N_L_ID);
//        request.add("N_B_ID",N_B_ID);
//        request.add("N_TYPE","10");
//        request(0,request,httpListener,true,true);

    }
//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response<String> response) {
//            if (what==0){
//                System.out.println("各部门数据："+response.get());
//                DepartmentsBean result = GsonUtil.GsonToBean(response.get(), DepartmentsBean.class);
//                List<DepartmentsBean.ObjBean.ResBean> resBeans = result.getObj().getRes();
//                resBean = resBeans.get(0);
//                addTow(resBeans);
//                setData(resBean);
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

    private void setData(DepartmentsBean.ObjBean.ResBean resBean) {
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

    @OnClick({R.id.et_updatetime,R.id.submit})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.et_updatetime:
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(DepartmentsActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String time = UntilsTime.getTime(date);
                        et_updatetime.setText(time);

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
//        String update_time =  et_updatetime.getText().toString();//评审时间
//        String idea = et_idea.getText().toString();//评审意见
//        Request<String> request = NoHttp.createStringRequest(UrlConfig.PATH_UPLOAD_DATA, RequestMethod.POST);
//        Map<String,Object> map = new HashMap<>();
//        map.put("id",resBean.getN_D_ID());
//        map.put("tjlx","10");
//        map.put("pssj",update_time);
//        map.put("psyj",idea);
//        map.put("pszt",result);
//        request.add(map);
//        request(1,request,httpListener,true,true);
    }

    private void changeSate(Boolean bool) {
        et_updatetime.setEnabled(bool);
        et_idea.setEnabled(bool);
        regular.setEnabled(bool);
        unregular.setEnabled(bool);
        et_updatetime.setBackground(ContextCompat.getDrawable(DepartmentsActivity.this,R.drawable.background_arc_3));
        et_idea.setBackground(ContextCompat.getDrawable(DepartmentsActivity.this,R.drawable.background_arc_3));

    }

    private void init() {
        manager = new FullyGridLayoutManager(this,
                5, GridLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(new GridSpacingItemNotBothDecoration(5,
                ScreenUtils.dip2px(DepartmentsActivity.this, 4), true, false));
        adapter = new GridImageSecAdapter(DepartmentsActivity.this, onAddPicClickListener,onDelete);
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

        }
    };
    private GridImageSecAdapter.OnDelete onDelete = new GridImageSecAdapter.OnDelete() {
        @Override
        public void onItemDelete(int mark) {
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 101) {
            Log.i("CJT", "picture");
            String path = data.getStringExtra("path");
//            photo.setImageBitmap(BitmapFactory.decodeFile(path));
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
