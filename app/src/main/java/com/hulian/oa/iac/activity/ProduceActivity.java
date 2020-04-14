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
import com.hulian.oa.iac.bean.MyMediaType;
import com.hulian.oa.iac.bean.ProduceBean;
import com.hulian.oa.iac.media.PicturePreviewActivity;
import com.hulian.oa.iac.media.VideoPreviewActivity;
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
//import com.zhhl.marketauthority.bean.MyMediaType;
//import com.zhhl.marketauthority.bean.ProduceBean;
//import com.zhhl.marketauthority.config.UrlConfig;
//import com.zhhl.marketauthority.media.PicturePreviewActivity;
//import com.zhhl.marketauthority.media.VideoPreviewActivity;
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

//试生产(制造)情况
public class ProduceActivity  extends BaseActivity {
    @BindView(R.id.et_name_product)
    EditText et_name_product;//产品名称
    @BindView(R.id.et_device_type)
    EditText et_device_type;//设备品种/类别
    @BindView(R.id.et_param_level)
    EditText et_param_level;//参数级别
    @BindView(R.id.et_device_model)
    EditText et_device_model;//设备型号
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
    List<MyMediaType> selectList = new ArrayList<MyMediaType>();
    private static final int REQUESTCODE = 100;
    ProduceBean.ObjBean.ResBean resBean;
    MyMediaType myMediaType;
    private String result = "0";
    @Override
    protected int setContentView() {
        return R.layout.activity_produce;
    }

    @Override
    protected void onCreate() {
        addBack();
        setTitleText("试生产(制造)情况");
        init();
        getData();
    }

    private void getData() {
        resBean = (ProduceBean.ObjBean.ResBean) getIntent().getExtras().get("data");
        if (resBean!=null){
            setData(resBean);
        }
    }
    //上传图片结果
//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response<String> response) {
//            if (what ==0){//上传视频或图片
//                Gson gson = new Gson();
//                selectList.add(myMediaType);
//                adapter.setList(selectList);
//                adapter.notifyDataSetChanged();
//                System.out.println("试生产制造，上传数据："+response.get());
//            }else if(what ==1){
//                ToastUtils.show(mContext,"修改成功");
//            }
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
            case R.id.submit:
                uploadData();
                break;
        }
    }

    private void   uploadData() {
        String name = et_name_product.getText().toString();//产品名称
        String device_type =  et_device_type.getText().toString();//设备品种/类别
        String param_level =  et_param_level.getText().toString();//参数级别
        String device_num = et_device_model.getText().toString();//设备型号
        String update_time =  et_updatetime.getText().toString();//评审时间
        String idea = et_idea.getText().toString();//评审意见
        submit.setVisibility(View.INVISIBLE);
        change.setVisibility(View.VISIBLE);
//        Request<String> request = NoHttp.createStringRequest(UrlConfig.PATH_UPLOAD_DATA, RequestMethod.POST);
//        Map<String,Object> map = new HashMap<>();
//        map.put("id",resBean.getN_P_ID());//主键表ID
//        map.put("tjlx","4");
//        map.put("v_gc_type",name);
//        map.put("v_shb_type",device_type);
//        map.put("v_level",param_level);
//        map.put("v_sbxh",device_num);
//        map.put("pssj",update_time);
//        map.put("psyj",idea);
//        map.put("pszt",result);
//        request.add(map);
//        System.out.println("评审状态："+result);
//        request(1,request,httpListener,true,true);
    }

    public void setData(ProduceBean.ObjBean.ResBean produce){
        et_name_product.setText(resBean.getV_GC_TYPE());//产品名称
        et_device_type.setText(resBean.getV_SHB_TYPE());//设备品种/类别
        et_param_level.setText(resBean.getV_LEVEL());//参数级别
        et_device_model.setText(resBean.getV_SBXH());//设备型号
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
        System.out.println("返回结果："+resBean.getPSZT());
        //回显数据的ID
//        String imgsData = produce.getPSTPIDS();
//        int len = -1;
//        if (imgsData!=null&&!imgsData.equals(0)&& imgsData.length()>1){
//            String[] imgs= produce.getPSTPIDS().split(",");
//            if (imgs.length>=5){
//                len = 5;
//            }else{
//                len = imgs.length;
//            }
//            for (int i=0;i<len;i++){
//                MyMediaType myMediaType = new MyMediaType();
//                myMediaType.setId(imgs[0]);
//                myMediaType.setType("0");
//                selectList.add(myMediaType);
//            }
//            adapter.notifyDataSetChanged();
//        }
    };
    private void changeSate(Boolean bool) {
        et_name_product.setEnabled(bool);
        et_device_type.setEnabled(bool);
        et_param_level.setEnabled(bool);
        et_device_model.setEnabled(bool);
        et_updatetime.setEnabled(bool);
        et_idea.setEnabled(bool);

        et_name_product.setBackground(ContextCompat.getDrawable(ProduceActivity.this,R.drawable.background_arc_3));
        et_device_type.setBackground(ContextCompat.getDrawable(ProduceActivity.this,R.drawable.background_arc_3));
        et_param_level.setBackground(ContextCompat.getDrawable(ProduceActivity.this,R.drawable.background_arc_3));
        et_device_model.setBackground(ContextCompat.getDrawable(ProduceActivity.this,R.drawable.background_arc_3));
        et_updatetime.setBackground(ContextCompat.getDrawable(ProduceActivity.this,R.drawable.background_arc_3));
        et_idea.setBackground(ContextCompat.getDrawable(ProduceActivity.this,R.drawable.background_arc_3));

    }

    private void init() {
        manager = new FullyGridLayoutManager(this,
                5, GridLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(new GridSpacingItemNotBothDecoration(5,
                ScreenUtils.dip2px(this, 8), true, false));
        adapter = new GridImageSecAdapter(this, onAddPicClickListener,onDelete);
        adapter.setList(selectList);//设置数据
        recycler.setAdapter(adapter);
        change = addChange();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSate(markBool);
//                mAdapter.setToChange(true);
            }
        });
        adapter.setOnItemClickListener(new GridImageSecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                MyMediaType myMediaType = selectList.get(position);
                if (myMediaType.getType().equals("0")){
                    Intent intent = new Intent(mContext, PicturePreviewActivity.class);
                    intent.putExtra("position",(position+1)+"");
                    intent.putExtra("sum",selectList.size()+"");
                    intent.putExtra("path",selectList.get(position).getPath());
                    intent.putExtra("id",selectList.get(position).getId());
                    intent.putExtra("mediatype","0");
                    startActivity(intent);
                }else if(myMediaType.getType().equals("1")){
                    Intent intent = new Intent(mContext, VideoPreviewActivity.class);
                    intent.putExtra("position",position);
                    intent.putExtra("sum",selectList.size());
                    intent.putExtra("path",selectList.get(position).getPath());
                    intent.putExtra("id",selectList.get(position).getId());
                    startActivity(intent);
                }

            }
        });
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
    //点击+号按钮进行拍照
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

    //拍照数据添加到recyclerview并上传数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         myMediaType = new MyMediaType();
        if (resultCode == 101) {
            Log.i("CJT", "picture");
            String path = data.getStringExtra("path");
//            photo.setImageBitmap(BitmapFactory.decodeFile(path));
            myMediaType.setType("0");
            myMediaType.setPath(path);
            uploadMedia(path,"0");

        }
        if (resultCode == 102) {
            Log.i("CJT", "video");
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
//        request.add("keyid", resBean.getN_P_ID());//表主键ID
//        request.add("mediatype", mediatype);//媒体类型  0图片 1视频
//        request.add("type", "5");//类型-试生产制造
//        request.add("tmpfile", binary);//文件流
//        request(0,request,httpListener,true,true);
    }
}
