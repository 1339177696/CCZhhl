package com.hulian.oa.iac.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.hulian.oa.R;
import com.hulian.oa.iac.adapter.GridImageSecAdapter;
import com.hulian.oa.iac.base.BaseActivity;
import com.hulian.oa.iac.bean.MyMediaType;
import com.hulian.oa.iac.media.PicturePreviewActivity;
import com.hulian.oa.iac.media.VideoPreviewActivity;
import com.hulian.oa.iac.util.ScreenUtils;
import com.hulian.oa.iac.util.ToastUtils;
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
//import com.zhhl.marketauthority.adapter.GridImageSecAdapter;
//import com.zhhl.marketauthority.bean.MyMediaType;
//import com.zhhl.marketauthority.bean.UploadImage;
//import com.zhhl.marketauthority.config.UrlConfig;
//import com.zhhl.marketauthority.media.PicturePreviewActivity;
//import com.zhhl.marketauthority.media.VideoPreviewActivity;
//import com.zhhl.marketauthority.nohttp.listener.HttpListener;
//import com.zhhl.marketauthority.util.ScreenUtils;
//import com.zhhl.marketauthority.util.ToastUtils;
//import com.zhhl.marketauthority.util.UntilsTime;
//import com.zhhl.marketauthority.view.FullyGridLayoutManager;
//import com.zhhl.marketauthority.view.GridSpacingItemNotBothDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ReviewActvity extends BaseActivity {

    @BindView(R.id.et_updatetime)
    TextView et_updatetime;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.et_idea)
    EditText et_idea;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    FullyGridLayoutManager manager;
    GridImageSecAdapter adapter;
    List<MyMediaType> selectList = new ArrayList<MyMediaType>();
    private static final int REQUESTCODE = 100;
    MyMediaType myMediaType;
    private String ps_state;
    private String N_L_ID;
    private String N_B_ID;
    private StringBuffer sbf;
    @Override
    protected int setContentView() {
        return R.layout.activity_review;
    }

    @Override
    protected void onCreate() {
        setTitleText("现场评审");
        ImageView back = findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        ImageView change; change = findViewById(R.id.change);
        change.setVisibility(View.INVISIBLE);
        init();
    }

    private void init() {
        N_L_ID = getIntent().getStringExtra("N_L_ID");
        N_B_ID = getIntent().getStringExtra("N_B_ID");
        sbf = new StringBuffer();
        manager = new FullyGridLayoutManager(this,
                5, GridLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(new GridSpacingItemNotBothDecoration(5,
                ScreenUtils.dip2px(this, 8), true, false));
        adapter = new GridImageSecAdapter(this, onAddPicClickListener,onDelete);
        adapter.setList(selectList);//设置数据
        recycler.setAdapter(adapter);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.all:
                        ps_state = "0";
                        break;
                    case R.id.type_1:
                        ps_state = "1";
                        break;
                    case R.id.type_2:
                        ps_state = "2";
                        break;
                    case R.id.type_3:
                        ps_state = "3";
                        break;
                }
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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(ps_state)){
                    String psyj = et_idea.getText().toString();
                    String time = et_updatetime.getText().toString();
//                    Request<String> request = NoHttp.createStringRequest(UrlConfig.PAHT_DB_PJ, RequestMethod.POST);
//                    request.add("xklx","1");
//                    request.add("L_TYPE","4");
//                    request.add("N_L_ID",N_L_ID);
//                    request.add("audit",ps_state);
//                    request.add("PSR","评审人姓名");
//                    request.add("opinion","psyj");
//                    request.add("N_B_ID",N_B_ID);
//                    request.add("mogonDbId",sbf.toString());
//                    System.out.println("图片ID参数："+sbf.toString());
//                    request(1,request,httpListener,true,true);
                }else{
                    ToastUtils.show(mContext,"请选择评审状态");
                }
            }
        });
    }
    @OnClick({R.id.et_updatetime})
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
        }
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
//        request.add("keyid",N_L_ID );//表主键ID
//        request.add("mediatype", mediatype);//媒体类型  0图片 1视频
////        request.add("type", "5");//类型-试生产制造
//        request.add("tmpfile", binary);//文件流
//        request(0,request,httpListener,true,true);
    }
    //上传图片结果
//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response<String> response) {
//            if (what ==0){//上传视频或图片
//                Gson gson = new Gson();
//                UploadImage uploadImage = gson.fromJson(response.get(), UploadImage.class);
//                if (uploadImage!=null && uploadImage.isFlag()){
//                    selectList.add(myMediaType);
//                    adapter.setList(selectList);
//                    adapter.notifyDataSetChanged();
//                    sbf.append(uploadImage.getZmwjFileId()+",");
//                }
//            }else if(what ==1){//评审意见提交
//                ToastUtils.show(mContext,"评审完成");
//                ReviewActvity.this.finish();
//                System.out.println("评审结果："+response.get());
//            }
//        }
//
//        @Override
//        public void onFailed(int what, Response<String> response) {
//            ToastUtils.show(mContext,"请求失败");
//        }
//    };
}
