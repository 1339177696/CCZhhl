package com.hulian.oa.news.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.Bean_x;
import com.hulian.oa.bean.JournalismComments;
import com.hulian.oa.bean.News;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.news.adapter.NewsCommentdapter;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.utils.URLImageParser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsActivityInfo_x extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_dis)
    TextView tvDis;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.listview)
    RecyclerView mRecyclerView;
    @BindView(R.id.bt_comment)
    Button btComment;
    @BindView(R.id.bt_top)
    ImageView btTop;
    @BindView(R.id.bt_store)
    ImageView btStore;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.scrollable)
    NestedScrollView scrollable;

    private int mCount = 1;
    NewsCommentdapter mRecyclerViewAdapter;
    private Bean_x newsA;
    List<JournalismComments> memberList=new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);
        ButterKnife.bind(this);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        newsA = (Bean_x) getIntent().getSerializableExtra("journalism");
        tvTitle.setText(newsA.getCollectTypeTitle());  // 修改了
        tvDis.setText(newsA.getCollectTypeUserName() + "  " + newsA.getCreateTime()); // 修改了
        getXinwenCentent(newsA.getCollectTypeId(),newsA.getCollectUserId());
        tvAuthor.setText("责任编辑：" + newsA.getCollectTypeUserName());  //修改了
        initList();
    }

    private void initList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                //解决ScrollView里存在多个RecyclerView时滑动卡顿的问题
                //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
                return false;
            }
        });
//解决数据加载不完的问题
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
//解决数据加载完成后, 没有停留在顶部的问题
        mRecyclerView.setFocusable(false);


        //代码设置scrollbar无效？未解决！
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //设置下拉刷新是否可见
        //mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置是否可以下拉刷新
        //mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
        //设置是否可以上拉刷新
        //mPullLoadMoreRecyclerView.setPushRefreshEnable(false);


        getData();

    }

    private void getData() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//               runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mRecyclerViewAdapter.addAllData(setList());
//                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
//                    }
//                });
//
//            }
//        }, 1000);
        memberList.removeAll(memberList);
        RequestParams params = new RequestParams();
        params.put("journalismId", String.valueOf(newsA.getCollectTypeId()));  // 修改的
        //    params.put("commentUserId", "1");
        try {
            params.put("commentUserId", SPUtils.get(mContext, "userId", "").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpRequest.postNesInfoApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    memberList = gson.fromJson(result.getJSONObject("data").getJSONArray("journalismComments").toString(),
                            new TypeToken<List<JournalismComments>>() {
                            }.getType());
                    mRecyclerViewAdapter = new NewsCommentdapter(mContext);
                    mRecyclerView.setAdapter(mRecyclerViewAdapter);
                    mRecyclerViewAdapter.addAllData(memberList);
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

    @OnClick({R.id.bt_comment, R.id.bt_top, R.id.bt_store,R.id.iv_back,R.id.rl_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_title:
                finish();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_comment:
                if (etContent.getText().toString().equals("") || etContent.getText().toString().trim().length() <= 0) {
                    ToastHelper.showToast(mContext, "请填写评论内容");
                    return;
                }
                RequestParams params = new RequestParams();
                params.put("journalismId", String.valueOf(newsA.getCollectTypeId()));  // 修改的
                params.put("journalismTitle", newsA.getCollectTypeTitle()); // 修改的
                params.put("commentName", SPUtils.get(mContext, "username", "").toString());
                params.put("commentContent", etContent.getText().toString());
                HttpRequest.postNesSenCommApi(params, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        try {
                            JSONObject result = new JSONObject(responseObj.toString());
                            ToastHelper.showToast(mContext, result.getString("msg"));
                            if( result.getString("code").equals("0")){
                                etContent.setText("");
                                getData();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(OkHttpException failuer) {
                    }
                });
                break;
            case R.id.bt_top:
                scrollable.scrollTo(0,0);
                break;
            case R.id.bt_store:
                RequestParams params1 = new RequestParams();
                params1.put("collectTypeId", String.valueOf(newsA.getCollectTypeId()));  // 修改的
                params1.put("collectType","0");
                params1.put("collectUserId", SPUtils.get(mContext, "userId", "").toString());
                HttpRequest.postStoreSenCommApi(params1, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        try {
                            JSONObject result = new JSONObject(responseObj.toString());
                            ToastHelper.showToast(mContext, result.getString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(OkHttpException failuer) {
                    }
                });
                break;
        }
    }


    /**
     * 请求收藏新闻详情
     * @param journalismId
     * @param commentUserId
     */
    private void getXinwenCentent(int journalismId,int commentUserId)
    {
        RequestParams params = new RequestParams();
        params.put("journalismId",String.valueOf(journalismId));
        params.put("commentUserId",String.valueOf(commentUserId));
        HttpRequest.postNesInfoApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj)
            {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONObject a = result.getJSONObject("data");
                    JSONObject data = a.getJSONObject("journalismDetails");
                    String c = data.getString("journalismContent");
                    Log.d("这是内容",c);
                    URLImageParser imageGetter = new URLImageParser(tvContent);
                    tvContent.setText(Html.fromHtml(c, imageGetter, null));
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



}
