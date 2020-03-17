package com.hulian.oa.work.file.admin.activity.document;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.duke.dfileselector.activity.DefaultSelectorActivity;
import com.duke.dfileselector.util.FileSelectorUtils;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.People;
import com.hulian.oa.me.SelDepartmentActivity;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.net.Urls;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.views.l_flowview.FlowLayoutAdapter;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.FullyGridLayoutManager;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.L_GridImageAdapter;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.L_GridRoamAdapter;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.L_GridRoamAdapter_qgl;
import com.hulian.oa.work.file.admin.activity.document.l_fragment.L_ApprovedFragment;
import com.hulian.oa.work.file.admin.activity.document.l_fragment.L_ChaosongmeFragment_qgl;
import com.hulian.oa.work.file.admin.activity.document.l_fragment.L_PendFragment;
import com.hulian.oa.work.file.admin.activity.document.l_fragment.QGLWofaqiFragment;
import com.hulian.oa.work.file.admin.activity.meeting.SelDepartmentActivity_meet_zb_single;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//公文发起
public class LauncherDocumentActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.tv_launcher)
    TextView tvLauncher;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.tv_top)
    TextView tvTop;
    private FlowLayoutAdapter<String> flowLayoutAdapter;
    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<People> selectList2 = new ArrayList<>();
    private L_GridImageAdapter adapter;
    private L_GridRoamAdapter adapter2;
    //照片选择最大值
    private int maxSelectNum = 9;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.recycler2)
    RecyclerView recyclerView2;
    private int count = 0;
    String offid;
    // 审批人选择最大值
    private int maxSelectNum2 = 5;
    // 新加的抄送人qgl
    @BindView(R.id.recycler3)
    RecyclerView recyclerView3;
    private int maxSelectNum3 = 5;
    private L_GridRoamAdapter_qgl adapter3;
    private List<People> selectList3 = new ArrayList<>();
    // 选择文件
    @BindView(R.id.file_btn)
    TextView filebtn;
    private List<String> fileList = new ArrayList<>();
    @BindView(R.id.tv_reaseon)
    TextView tv_reaseon;
    List<String> reasonlist = new ArrayList<>();
    private OptionsPickerView reasonPicker;//类型;
    private String gwtype = "1";
    @BindView(R.id.et_number)
    EditText etnumber;
//    紧急类型
    @BindView(R.id.jinji_leibie)
    TextView jinji_leibie;
    private OptionsPickerView jinjiPicker;//类型;
    private String gwjinji = "1";
    List<String> Jinji = new ArrayList<>();

    @BindView(R.id.approver_tv)
    TextView approver_tv;

    private String qj_id = "";
    private String qj_name = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_launcher);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        offid = getIntent().getStringExtra("offid");
        if (offid != null) {
            tvTop.setText("公文重发");
        } else {
            tvTop.setText("公文发起");
        }
        init();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initReason();
        initJinji();
        tv_reaseon.setText("签批");
        jinji_leibie.setText("紧急且重要");
        getWenHao();


    }

    private void initSelectImage() {
        PictureSelector.create(LauncherDocumentActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(0)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .selectionMedia(selectList)// 是否传入已选图片
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    //流转人初始化
    private void init() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(LauncherDocumentActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new L_GridImageAdapter(LauncherDocumentActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);
        FullyGridLayoutManager manager2 = new FullyGridLayoutManager(LauncherDocumentActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(manager2);
        adapter2 = new L_GridRoamAdapter(LauncherDocumentActivity.this, onAddPicClickListener2);
        adapter2.setList(selectList2);
        adapter2.setSelectMax(maxSelectNum2);
        recyclerView2.setAdapter(adapter2);
        adapter.setOnItemClickListener(new L_GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                PictureSelector.create(LauncherDocumentActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList);
            }
        });
        // qgl
        FullyGridLayoutManager manager3 = new FullyGridLayoutManager(LauncherDocumentActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView3.setLayoutManager(manager3);
        adapter3 = new L_GridRoamAdapter_qgl(LauncherDocumentActivity.this,onAddPicClickListener3);
        adapter3.setList(selectList3);
        adapter3.setSelectMax(maxSelectNum3);
        recyclerView3.setAdapter(adapter3);

    }


    private L_GridImageAdapter.onAddPicClickListener onAddPicClickListener = new L_GridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick() {
            initSelectImage();
        }
    };

    private L_GridRoamAdapter.onAddPicClickListener onAddPicClickListener2 = new L_GridRoamAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick() {
//            selectList2.hb_add("流转人"+count);
//            adapter2.setList(selectList2);
//            adapter2.notifyDataSetChanged();
//            count++;
            Intent intent = new Intent(LauncherDocumentActivity.this, SelDepartmentActivity_meet_zb_single.class);
            startActivityForResult(intent,110);
        }
    };
    // qgl
    private L_GridRoamAdapter_qgl.onAddPicClickListener onAddPicClickListener3 = new L_GridRoamAdapter_qgl.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            Intent intent = new Intent(LauncherDocumentActivity.this, SelDepartmentActivity_meet_zb_single.class);
            startActivityForResult(intent,120);
        }
    };

    public void onEventMainThread(People event) {
        selectList2.add(event);
        selectList3.add(event);
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", new File(media.getPath()).length() + "");
                        Log.i("压缩图片-----》", new File(media.getCompressPath()).length() + "");

//                        Bitmap bitmap = BitmapFactory.decodeFile(media.getCompressPath());
//                        iv_document.setImageBitmap(bitmap);
                        adapter.setList(selectList);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case 110:{
                    List<People> mList = (List<People>) data.getSerializableExtra("mList");
                    selectList2.add(mList.get(0));
                    adapter2.notifyDataSetChanged();
                    break;

                }
                case 120:
                    List<People> mList = (List<People>) data.getSerializableExtra("mList");
                    selectList3.add(mList.get(0));
                    adapter3.notifyDataSetChanged();
                    break;
                case DefaultSelectorActivity.FILE_SELECT_REQUEST_CODE:
                    printData(DefaultSelectorActivity.getDataFromIntent(data));
                    break;

            }
        }
    }

    /**
     * 自定义压缩存储地址
     *
     * @return
     */
    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
    private void deleteCache() {
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(LauncherDocumentActivity.this);
                } else {
                    Toast.makeText(LauncherDocumentActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.tv_launcher,R.id.file_btn,R.id.tv_reaseon,R.id.jinji_leibie})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.tv_launcher:
                if (TextUtils.isEmpty(etnumber.getText().toString().trim())) {
                    ToastHelper.showToast(mContext, "公文文号为空");
                    return;
                }

                if (TextUtils.isEmpty(etTitle.getText().toString().trim())) {
                    ToastHelper.showToast(mContext, "标题不能为空");
                    return;
                }

                if (qj_id.equals("")){
                    if (selectList2.size() <= 0) {
                        ToastHelper.showToast(mContext, "审批人人不能为空");
                        return;
                    }
                }
                // 新加的qgl
                if (selectList3.size() <= 0){
                    ToastHelper.showToast(mContext, "抄送人不能为空");
                    return;
                }
                if (selectList.size() <= 0) {
                    ToastHelper.showToast(mContext, "公文照片不能为空");
                    return;
                }
                String uids = "";
                for (People params1 : selectList2) {
                    uids += params1.getUserId() + ",";
                }
                String uidname = "";
                for (People params1 : selectList2) {
                    uidname += params1.getUserName() + "-";
                }
                //qgl
                String csids = "";
                for (People params : selectList3){
                    csids += params.getUserId() + ",";
                }
                String csidname = "";
                for (People params : selectList3){
                    csidname += params.getUserName() + "-";
                }
                loadDialog.show();
                RequestParams params = new RequestParams();
                params.put("spare4", "0");
                params.put("initiationType", gwtype);
                params.put("symbol", etnumber.getText().toString().trim());
                params.put("title", etTitle.getText().toString());
                if (qj_id.equals("")&&qj_name.equals("")){
                    params.put("approverId", uids.substring(0, uids.length() - 1));
                    params.put("approverName", uidname.substring(0, uidname.length() - 1));
                }else {
                    params.put("approverId", qj_id);
                    params.put("approverName", qj_name);
                }
                params.put("createName",SPUtils.get(mContext, "nickname", "").toString());
                params.put("copierId",csids.substring(0, csids.length() - 1));
                params.put("copierName",csidname.substring(0, csidname.length() - 1));
                params.put("createBy", SPUtils.get(mContext, "userId", "").toString());
                params.put("status", gwjinji);
                List<File> fils = new ArrayList<>();
                for (LocalMedia imgurl : selectList) {
                    fils.add(new File(imgurl.getPath()));
                }
                for (String fileurl : fileList) {
                    fils.add(new File(fileurl));

                }

                showDialogLoading();
                HttpRequest.postDocumentSendApi(params, fils, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        dismissDialogLoading();
                        loadDialog.dismiss();
                        try {
                            JSONObject result = new JSONObject(responseObj.toString());
                            ToastHelper.showToast(mContext, result.getString("msg"));
                            if (result.getString("code").equals("0")) {
                                //领导
                                if (SPUtils.get(mContext, "isLead", "").equals("0")){
                                    EventBus.getDefault().post(new L_PendFragment());
                                    EventBus.getDefault().post(new L_ApprovedFragment());
                                }else {
                                    //员工
                                    EventBus.getDefault().post(new QGLWofaqiFragment());
                                    EventBus.getDefault().post(new L_ChaosongmeFragment_qgl());
                                }
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(OkHttpException failuer) {
                        //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                        loadDialog.dismiss();
                        dismissDialogLoading();
                        Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.file_btn:
                filebtn.setCursorVisible(false);
                //文件选择器  过滤条件在 DefaultSelectorActivity.onPermissionSuccess 中添加
                DefaultSelectorActivity.startActivityForResult(this, false, true, 3);
                break;
            case R.id.tv_reaseon:
                reasonPicker.show();
                break;
            case R.id.jinji_leibie:
                jinjiPicker.show();
                break;
        }

    }

    private void initJinji() {
        Jinji.add("紧急且重要");
        Jinji.add("重要不紧急");
        Jinji.add("紧急不重要");
        Jinji.add("不紧急且不重要");
        jinjiPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                jinji_leibie.setText(Jinji.get(options1));
                if (jinji_leibie.getText().toString().trim().equals("紧急且重要")) {
                    gwjinji = "1" ;
                } else if(jinji_leibie.getText().toString().trim().equals("重要不紧急")){
                    gwjinji = "2" ;
                }else if (jinji_leibie.getText().toString().trim().equals("紧急不重要")){
                    gwjinji = "3" ;
                }else {
                    gwjinji = "4" ;
                }

            }
        }).setTitleText("公文类型").setContentTextSize(14).setTitleSize(14).setSubCalSize(14).build();
        jinjiPicker.setPicker(Jinji);
    }

    private void printData(ArrayList<String> list) {
        if (FileSelectorUtils.isEmpty(list)) {
            return;
        }
        int size = list.size();
        Log.v("EMAIL", "获取到数据-开始 size = " + size);
        StringBuffer stringBuffer = new StringBuffer(" ");
        for (int i = 0; i < size; i++) {
            int a = list.get(i).length();
            String ccc = list.get(i).substring(list.get(i).lastIndexOf('/') + 1, a);
            stringBuffer.append(ccc);
            stringBuffer.append(",");
        }
        String file_name = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
        filebtn.setText(file_name);
        fileList = list;
        Log.v("EMAIL", "获取到数据-结束");
    }

    private void initReason() {
        reasonlist.add("签批");
        reasonlist.add("会签");
        reasonPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tv_reaseon.setText(reasonlist.get(options1));
                if (tv_reaseon.getText().toString().trim().equals("会签"))
                {
                    gwtype = "1" ;
                    Log.e("会签","审批人后台给");
                    getApproval();
                    approver_tv.setVisibility(View.VISIBLE);
                    recyclerView2.setVisibility(View.GONE);


                }
                else {
                    gwtype = "0" ;
                    Log.e("签批","空");
                    approver_tv.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.VISIBLE);
                    qj_id = "";
                    qj_name = "";
                }

            }
        }).setTitleText("公文类型").setContentTextSize(22).setTitleSize(22).setSubCalSize(21).build();
        reasonPicker.setPicker(reasonlist);
    }

    //qgl注释的
//    RequestParams params = new RequestParams();
//                if (offid != null) {
//        params.put("offid", offid);
//    }
//                params.put("pid", SPUtils.get(mContext, "userId", "").toString());
//                params.put("offTitle", etTitle.getText().toString());
//                params.put("uids", uids.substring(0, uids.length() - 1));
//
//    List<File> fils = new ArrayList<>();
//                for (LocalMedia imgurl : selectList) {
//        fils.hb_add(new File(imgurl.getPath()));
//    }
//    showDialogLoading();
//                HttpRequest.postDocumentSendApi(params, fils, new ResponseCallback() {
//        @Override
//        public void onSuccess(Object responseObj) {
//            dismissDialogLoading();
//            try {
//                JSONObject result = new JSONObject(responseObj.toString());
//                ToastHelper.showToast(mContext, result.getString("msg"));
//                if (result.getString("code").equals("0")) {
//                    EventBus.getDefault().post(new L_PendFragment());
//                    finish();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(OkHttpException failuer) {
//            //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
//            dismissDialogLoading();
//            Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
//        }
//    });

    public void getWenHao()
    {
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .build();
        Request.Builder builder=new Request.Builder();
        Request  request=builder.url(Urls.commUrls+"system/lotus/findOffNum").post(body).build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String number = response.body().string();
                new Thread(){
                    @Override
                    public void run()
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                etnumber.setText(number);
                            }
                        });
                        super.run();
                    }
                }.start();

            }
        });
    }

    //请求审批人
    private void getApproval(){
        RequestParams params = new RequestParams();
        HttpRequest.post_findLotusSignApprover(params, new ResponseCallback(){
            @Override
            public void onSuccess(Object responseObj) {
                JSONObject obj = null;
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    obj = new JSONObject(result.toString());
                    approver_tv.setText(obj.getJSONObject("data").getString("approveNames"));
                    qj_id = obj.getJSONObject("data").getString("approveIds");
                    qj_name = obj.getJSONObject("data").getString("approveNames");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }
}