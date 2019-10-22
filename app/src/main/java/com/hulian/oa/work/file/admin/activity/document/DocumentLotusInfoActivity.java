package com.hulian.oa.work.file.admin.activity.document;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.Approve;
import com.hulian.oa.bean.DocumentImage;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.views.CircleRelativeLayout;
import com.hulian.oa.views.MyListView;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.L_AppProgressAdapter;
import com.hulian.oa.work.file.admin.activity.document.l_gallery_adapter.CustomGalleryAdapter;
import com.hulian.oa.work.file.admin.activity.document.l_gallery_adapter.CustomPagerSnapHelper;
import com.hulian.oa.work.file.admin.activity.document.l_gallery_adapter.GalleryOnScrollListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//公文详情-员工端
public class DocumentLotusInfoActivity extends BaseActivity {
//    @BindView(R.id.tv_status)
//    TextView tvStatus;
//    @BindView(R.id.tv_content)
//    TextView tvContent;
//    @BindView(R.id.tv_updata)
//    TextView tvUpdata;
//    @BindView(R.id.tv_state)
//    TextView tvState;
//    @BindView(R.id.tv_approval)
//    TextView tvApproval;
    private String offId;
//    @BindView(R.id.iv_back)
//    RelativeLayout ivBack;
//    @BindView(R.id.lv_progress)
//    MyListView lv_progress;
//    L_AppProgressAdapter adapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
//    @BindView(R.id.bt_store)
//    ImageView bt_store;
//    @BindView(R.id.cr_collection)
//    CircleRelativeLayout cr_collection;
    public CustomGalleryAdapter customAdapter;
    List<DocumentImage> memberList = new ArrayList<>();
    List<Object> list = new ArrayList<>();
    List<Approve> approveContentList = new ArrayList<>();
    private List<String> networkImages;
    private String create_Time;
    private String app_id;
//
//    @BindView(R.id.la_un_start_time)
//    TextView la_un_start_time;
//    @BindView(R.id.la_un_stop_time)
//    TextView la_un_stop_time;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);

        setContentView(R.layout.document_lotus_infor);
        ButterKnife.bind(this);
        offId = getIntent().getStringExtra("offId");
//        ivBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        getNetData();
    }

    private void init(List<DocumentImage> memberList) {
//        cr_collection.setColor(getResources().getColor(R.color.color_a_yellow));
//        cr_collection.setAlhpa(200);
        recyclerView = findViewById(R.id.recyclerView);
        //设置横滑
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        //填充数据
        customAdapter = new CustomGalleryAdapter(memberList, this);
        recyclerView.setAdapter(customAdapter);
        //分页滑动效果
        recyclerView.setOnFlingListener(null);
        new CustomPagerSnapHelper().attachToRecyclerView(recyclerView);
        //滑动动画
        recyclerView.addOnScrollListener(new GalleryOnScrollListener());


    }

    private void getNetData() {
        RequestParams params = new RequestParams();
        params.put("offId", offId);
        params.put("uId", SPUtils.get(mContext, "userId", "").toString());
        HttpRequest.postDocumentInfoApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    create_Time = result.getJSONObject("data").getString("create_Time");

//                    la_un_start_time.setText(create_Time);
                    app_id = result.getJSONObject("data").getString("approveringId");
                    memberList = gson.fromJson(result.getJSONObject("data").getJSONArray("fileList").toString(),
                            new TypeToken<List<DocumentImage>>() {
                            }.getType());
                    init(memberList);
                    approveContentList = gson.fromJson(result.getJSONObject("data").getJSONArray("approveContentList").toString(),
                            new TypeToken<List<Approve>>() {
                            }.getType());
                    String status = approveContentList.get(approveContentList.size() - 1).getApproveState();
                    if(result.getJSONObject("data").getString("officialDocumentState").equals("2")){
//                        tvUpdata.setVisibility(View.VISIBLE);
                    }
                    else {
//                        tvUpdata.setVisibility(View.GONE);
                    }


//                    if (status.equals("1"))
//                    {
//                        tvStatus.setText("已通过");
//                        la_un_stop_time.setText(approveContentList.get(approveContentList.size() - 1).getApptime());
//                    } else if (status.equals("2")) {
//                        tvStatus.setText("已拒绝");
//                        la_un_stop_time.setText(approveContentList.get(approveContentList.size() - 1).getApptime());
//                    } else {
//                        tvStatus.setText("进行中");
//                    }
//                    if (approveContentList.get(approveContentList.size() - 1).getApproveContent() == null) {
//                        tvContent.setText("暂无");
//                    } else {
//                        tvContent.setText(approveContentList.get(approveContentList.size() - 1).getApproveContent() + "");
//                    }
//                    initTimeLine(approveContentList);

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

//    void initTimeLine(List<Approve> approveContentList) {
//        //审批进度
//        adapter = new L_AppProgressAdapter(DocumentLotusInfoActivity.this, approveContentList, create_Time, app_id,tvStatus.getText().toString());
//        lv_progress.setAdapter(adapter);
////        lv_progress.setOnTouchListener(new View.OnTouchListener() {
////
////            public boolean onTouch(View v, MotionEvent event) {
////                lv_progress.getParent().requestDisallowInterceptTouchEvent(true);
////                return false;
////            }
////        });
//
//
//        lv_progress.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//                lv_progress.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
//    }
//
//    @OnClick({R.id.cr_collection})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.cr_collection:
//                Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();
//                break;
//        }
////        startActivity(new Intent(DocumentLotusInfoActivity.this,DocumentLotusActivity.class));
//
//    }
//
//    @OnClick(R.id.tv_updata)
//    public void onViewClicked() {
//      //  startActivity(new Intent(DocumentLotusInfoActivity.this,LauncherDocumentActivity.class));
//        Intent intent=new Intent(DocumentLotusInfoActivity.this,LauncherDocumentActivity.class);
//        intent.putExtra("offid",offId);
//        startActivity(intent);
//
//    }
}