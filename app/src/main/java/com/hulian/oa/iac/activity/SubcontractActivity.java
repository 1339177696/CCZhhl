package com.hulian.oa.iac.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.hulian.oa.iac.bean.MyMediaType;
import com.hulian.oa.iac.bean.SubcontractBean;
import com.hulian.oa.iac.util.ScreenUtils;
import com.hulian.oa.iac.util.UntilsTime;
import com.hulian.oa.iac.view.FullyGridLayoutManager;
import com.hulian.oa.iac.view.GridSpacingItemNotBothDecoration;
//import com.yanzhenjie.nohttp.BasicBinary;
//import com.yanzhenjie.nohttp.FileBinary;
//import com.yanzhenjie.nohttp.NoHttp;
//import com.yanzhenjie.nohttp.RequestMethod;
//import com.yanzhenjie.nohttp.rest.Request;
//import com.yanzhenjie.nohttp.rest.Response;
//import com.zhhl.marketauthority.R;
//import com.zhhl.marketauthority.activity.BaseActivity;
//import com.zhhl.marketauthority.adapter.GridImageSecAdapter;
//import com.zhhl.marketauthority.bean.ApplyCompanyBean;
//import com.zhhl.marketauthority.bean.MyMediaType;
//import com.zhhl.marketauthority.bean.SubcontractBean;
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

//分包，外协情况
public class

 SubcontractActivity extends BaseActivity {

    @BindView(R.id.et_code)
    EditText et_code;//统一社会信用代码/注册号
    @BindView(R.id.et_sub_unit)
    EditText et_sub_unit;//分包、外协单位名称
    @BindView(R.id.et_type)
    EditText et_type;//类型
    @BindView(R.id.et_sub_program)
    EditText et_sub_program;//分包、外协项目
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
    @BindView(R.id.submit)
    TextView submit;//提交
    private ImageView back;
    private ImageView change;
    private Boolean markBool = true;
    List<MyMediaType> selectList = new ArrayList<MyMediaType>();
    FullyGridLayoutManager manager;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    GridImageSecAdapter adapter;
    private static final int REQUESTCODE = 100;
    SubcontractBean.ObjBean.ResBean resBean;
    MyMediaType myMediaType;
    private String result = "0";
    @Override
    protected int setContentView() {
        return R.layout.activity_subcontract;
    }

    @Override
    protected void onCreate() {
        addBack();
        setTitleText("分包、外协情况");
        change = addChange();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSate(markBool);
//                mAdapter.setToChange(true);
            }
        });
        init();
        getData();
    }
    //回显数据
    private void getData() {

        resBean = (SubcontractBean.ObjBean.ResBean) getIntent().getExtras().get("data");
        //设置回显数据
        setData(resBean);

    }
//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response<String> response) {
//            if (what==0){//单张上传图片
//                ApplyCompanyBean backlogBean = GsonUtil.GsonToBean(response.get(), ApplyCompanyBean.class);
//                if (backlogBean!=null){
//                    selectList.add(myMediaType);
//                    adapter.notifyDataSetChanged();
//                }
//            }else if(what==1){//上传提交数据与修改数据
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

    @OnClick({R.id.et_updatetime,R.id.submit})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.et_updatetime://获取时间
                TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String time = UntilsTime.getTime(date);
                        et_updatetime.setText(time);

                    }
                }).setType(new boolean[]{true, true, true, true, true, true}).isDialog(false).build();
                pvTime.show();
                break;
            case R.id.submit:
                uploadData();
                break;
        }
    }

    private void uploadData() {
        String code = et_code.getText().toString();
        String sub_unit = et_sub_unit.getText().toString();
        String type = et_type.getText().toString();
        String sub_program = et_sub_program.getText().toString();
        String update_time =  et_updatetime.getText().toString();//评审时间
        String idea = et_idea.getText().toString();//评审意见
//        Request<String> request = NoHttp.createStringRequest(UrlConfig.PATH_UPLOAD_DATA, RequestMethod.POST);
//        Map<String,Object> map = new HashMap<>();
//        map.put("id",resBean.getN_S_ID());
//        map.put("tjlx","5");
//        map.put("v_s_type",type);
//        map.put("v_s_item",sub_program);
//        map.put("v_s_company",sub_unit);
//        map.put("v_s_creditcode",code);
//        map.put("pssj",update_time);
//        map.put("psyj",idea);
//        map.put("pszt",result);
//        request.add(map);
//        request(1,request,httpListener,true,true);
    }

    private void changeSate(Boolean bool) {
        et_code.setEnabled(bool);
        et_code.setBackground(ContextCompat.getDrawable(SubcontractActivity.this,R.drawable.background_arc_3));
        et_sub_unit.setEnabled(bool);
        et_sub_unit.setBackground(ContextCompat.getDrawable(SubcontractActivity.this,R.drawable.background_arc_3));
        et_type.setEnabled(bool);
        et_type.setBackground(ContextCompat.getDrawable(SubcontractActivity.this,R.drawable.background_arc_3));
        et_sub_program.setEnabled(bool);
        et_sub_program.setBackground(ContextCompat.getDrawable(SubcontractActivity.this,R.drawable.background_arc_3));


    }

    private void init() {
        manager = new FullyGridLayoutManager(this,
                5, GridLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(new GridSpacingItemNotBothDecoration(5,
                ScreenUtils.dip2px(SubcontractActivity.this, 4), true, false));
        adapter = new GridImageSecAdapter(SubcontractActivity.this, onAddPicClickListener,onDelete);
        adapter.setList(selectList);//设置数据
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

        }
    };
    private void setData(SubcontractBean.ObjBean.ResBean resBean) {
        if (resBean!=null){
         et_code.setText(resBean.getV_S_CREDITCODE());//统一社会信用代码/注册号
         et_sub_unit.setText(resBean.getV_S_COMPANY());//分包、外协单位名称
         et_type.setText(resBean.getV_S_TYPE());//类型
         et_sub_program.setText(resBean.getV_S_ITEM());//分包、外协项目
         et_updatetime.setText(resBean.getPSSJ());//评审时间
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
            //回显数据的ID
//            String imgsData = resBean.getPSTPIDS();
//            int len = -1;
//            if (imgsData!=null&&!imgsData.equals(0)&& imgsData.length()>1){
//                String[] imgs= resBean.getPSTPIDS().split(",");
//                if (imgs.length>=5){
//                    len = 5;
//                }else{
//                    len = imgs.length;
//                }
//                for (int i=0;i<len;i++){
//                    MyMediaType myMediaType = new MyMediaType();
//                    myMediaType.setId(imgs[0]);
//                    myMediaType.setType("0");
//                    selectList.add(myMediaType);
//                }
//                adapter.notifyDataSetChanged();
//            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        myMediaType = new MyMediaType();
        if (resultCode == 101) {
            String path = data.getStringExtra("path");
//            photo.setImageBitmap(BitmapFactory.decodeFile(path));
            myMediaType.setType("0");
            myMediaType.setPath(path);
            uploadMedia(path,"0");
        }
        if (resultCode == 102) {
            String path = data.getStringExtra("path");
            myMediaType.setType("1");
            myMediaType.setPath(path);
            uploadMedia(path,"1");
        }
        if (resultCode == 103) {
            Toast.makeText(this, "请检查相机权限~", Toast.LENGTH_SHORT).show();
        }
    }

    //上传图片，视频
    private void uploadMedia(String path,String mediatype) {
//        Request<String> request = NoHttp.createStringRequest(UrlConfig.PATH_UPLOAD_IMAGE, RequestMethod.POST);
//        BasicBinary binary = new FileBinary(new File(path));
//        request.add("keyid", resBean.getN_S_ID());//表主键ID
//        request.add("mediatype", mediatype);//媒体类型  0图片 1视频
//        request.add("type", "5");//类型-试生产制造
//        request.add("tmpfile", binary);//文件流
//        request(0,request,httpListener,true,true);
    }
}
