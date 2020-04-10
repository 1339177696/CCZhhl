package com.hulian.oa.work.activity.notice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.Department;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.views.AlertDialog;
import com.hulian.oa.views.MyGridView;
import com.hulian.oa.utils.FullyGridLayoutManager;
import com.hulian.oa.adpter.GridImageAdapter;
import com.hulian.oa.work.activity.notice.adapter.GridViewAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布通知
 */
public class NoticeIssueActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.gv_test)
      MyGridView gvTest;
    @BindView(R.id.bt_comment)
    Button btComment;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    AlertDialog myDialog;
    private GridImageAdapter adapterimage;
    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
    //照片选择最大值
    private int maxSelectNum = 9;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    private List<Department> mDatas = new ArrayList<>();
    ;
    private List<Department> mDatasSel = new ArrayList<>();
    ;
    private GridViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_notice_issue);
        ButterKnife.bind(this);
        init();
        initDatas();
         myDialog = new AlertDialog(this).builder();
        adapter = new GridViewAdapter(NoticeIssueActivity.this, mDatasSel);
        gvTest.setAdapter(adapter);
        gvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                    myDialog.setGone().setTitle("提示").setMsg("确定删除么").setNegativeButton("取消",null).setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDatasSel.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    }).show();
            }
        });

    }

    private void initDatas() {
        RequestParams params = new RequestParams();
        HttpRequest.postDepartmentListApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<Department> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<Department>>() {
                            }.getType());
                    mDatas.addAll(memberList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            mDatasSel = (List<Department>) data.getSerializableExtra("partment");
            adapter = new GridViewAdapter(NoticeIssueActivity.this, mDatasSel);
            gvTest.setAdapter(adapter);
        }
        else
            if (requestCode== PictureConfig.CHOOSE_REQUEST) {
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
                        adapterimage.setList(selectList);
                        adapterimage.notifyDataSetChanged();
                    }
        }
    }

    @OnClick({R.id.rl_title, R.id.bt_comment,R.id.ci_approved_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ci_approved_pic:
                Intent intent = new Intent(NoticeIssueActivity.this, NoticeSelPartActivity.class);
                intent.putExtra("partment", (Serializable) mDatas);
                startActivityForResult(intent, 1);
                break;
            case R.id.rl_title:
                finish();
                break;
            case R.id.bt_comment:
                if(TextUtils.isEmpty(etTitle.getText().toString().trim())){
                    ToastHelper.showToast(mContext,"请输入通知标题");
                    return;
                }
                if(TextUtils.isEmpty(etContent.getText().toString().trim())){
                    ToastHelper.showToast(mContext,"请填写通知内容");
                    return;
                }
                if(mDatasSel.size()<=0){
                    ToastHelper.showToast(mContext,"请添加通知部门");
                    return;
                }
                String targetDeptId="";
                String targetDeptName="";
                for(Department params1:mDatasSel){
                    targetDeptId+=params1.getDeptId()+",";
                    targetDeptName+=params1.getDeptName()+",";
                }
                loadDialog.show();
                RequestParams params1 = new RequestParams();
                params1.put("noticeTitle", etTitle.getText().toString().trim());
                params1.put("noticeContent", etContent.getText().toString().trim());
                params1.put("createBy", SPUtils.get(mContext, "nickname", "").toString());
                params1.put("targetDeptId", targetDeptId.substring(0,targetDeptId.length()-1));
                params1.put("targetDeptName",targetDeptName.substring(0,targetDeptName.length()-1));
                HttpRequest.postNoticeSendApi(params1, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        loadDialog.dismiss();
                        try {
                            JSONObject result = new JSONObject(responseObj.toString());
                            ToastHelper.showToast(mContext, result.getString("msg"));
                            setResult(1);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(OkHttpException failuer) {
                        loadDialog.dismiss();
                    }
                });
                break;
        }
    }
    private void init() {

        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapterimage = new GridImageAdapter(mContext, onAddPicClickListener);
        adapterimage.setList(selectList);
        adapterimage.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapterimage);

        adapterimage.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                PictureSelector.create(NoticeIssueActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList);
            }
        });

    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick() {
            initSelectImage();
        }
    };

    private void initSelectImage() {
        PictureSelector.create(NoticeIssueActivity.this)
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
}
