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

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.People;
import com.hulian.oa.me.SelDepartmentActivity;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.views.l_flowview.FlowLayoutAdapter;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.FullyGridLayoutManager;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.L_GridImageAdapter;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.L_GridRoamAdapter;
import com.hulian.oa.work.file.admin.activity.document.l_fragment.L_PendFragment;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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
        adapter2.setSelectMax(maxSelectNum);
        recyclerView2.setAdapter(adapter2);
        adapter.setOnItemClickListener(new L_GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                PictureSelector.create(LauncherDocumentActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList);
            }
        });
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
//            selectList2.add("流转人"+count);
//            adapter2.setList(selectList2);
//            adapter2.notifyDataSetChanged();
//            count++;
            Intent intent = new Intent(LauncherDocumentActivity.this, SelDepartmentActivity_meet_zb_single.class);
            startActivityForResult(intent,110);
        }
    };

    public void onEventMainThread(People event) {
        selectList2.add(event);
        adapter2.notifyDataSetChanged();
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

    @OnClick(R.id.tv_launcher)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etTitle.getText().toString().trim())) {
            ToastHelper.showToast(mContext, "标题不能为空");
            return;
        }
        if (selectList2.size() <= 0) {
            ToastHelper.showToast(mContext, "流转人不能为空");
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
        RequestParams params = new RequestParams();
        if (offid != null) {
            params.put("offid", offid);
        }
        params.put("pid", SPUtils.get(mContext, "userId", "").toString());
        params.put("offTitle", etTitle.getText().toString());
        params.put("uids", uids.substring(0, uids.length() - 1));
        List<File> fils = new ArrayList<>();
        for (LocalMedia imgurl : selectList) {
            fils.add(new File(imgurl.getPath()));
        }
        showDialogLoading();
        HttpRequest.postDocumentSendApi(params, fils, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                    dismissDialogLoading();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    ToastHelper.showToast(mContext, result.getString("msg"));
                    if (result.getString("code").equals("0")) {
                        EventBus.getDefault().post(new L_PendFragment());
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                dismissDialogLoading();
                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}