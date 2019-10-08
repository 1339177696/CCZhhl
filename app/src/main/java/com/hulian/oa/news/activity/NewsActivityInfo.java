package com.hulian.oa.news.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.hulian.oa.bean.Fab;
import com.hulian.oa.bean.Fab2;
import com.hulian.oa.bean.JournalismComments;
import com.hulian.oa.bean.News;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.news.adapter.NewsCommentdapter;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.utils.URLImageParser;
import com.hulian.oa.work.file.admin.activity.task.l_details_activity.TaskUndoneDetailsActivity;
import com.netease.nim.avchatkit.common.util.TimeUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class NewsActivityInfo extends BaseActivity {
    @BindView(R.id.tv_mengban)
    TextView tvMengban;

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
    private News newsA;
    List<JournalismComments> memberList=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        newsA = (News) getIntent().getSerializableExtra("journalism");
        tvTitle.setText(newsA.getJournalismTitle());
        tvDis.setText("来源："+newsA.getCreateBy() + "    " + TimeUtils.getDateToString(newsA.getCreateTime()) );
        URLImageParser imageGetter = new URLImageParser(tvContent);
        tvContent.setText(Html.fromHtml(newsA.getJournalismContent(), imageGetter, null));
        tvAuthor.setText("责任编辑：" + newsA.getCreateBy());
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

        etContent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //发送
                    etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (!v.getText().toString().trim().isEmpty()) {
                                RequestParams params = new RequestParams();
                                params.put("journalismId", newsA.getJournalismId());
                                params.put("journalismTitle", newsA.getJournalismTitle());
                                params.put("commentName", SPUtils.get(mContext, "nickname", "").toString());
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
                                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(NewsActivityInfo.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                etContent.setText("");
                            }
                            return true;
                        }
                    });
                }
                return false;
            }
        });
        getData();


    }

    private void getData() {
        memberList.removeAll(memberList);
        RequestParams params = new RequestParams();
        params.put("journalismId", newsA.getJournalismId());
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
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_title:
                finish();
                break;
            case R.id.bt_comment:
                if (etContent.getText().toString().equals("") || etContent.getText().toString().trim().length() <= 0) {
                    ToastHelper.showToast(mContext, "请填写评论内容");
                    return;
                }
                RequestParams params = new RequestParams();
                params.put("journalismId", newsA.getJournalismId());
                params.put("journalismTitle", newsA.getJournalismTitle());
                params.put("commentName", SPUtils.get(mContext, "nickname", "").toString());
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
                params1.put("collectTypeId", newsA.getJournalismId());
                params1.put("collectType","0");
                params1.put("collectUserId", SPUtils.get(mContext, "userId", "").toString());
                HttpRequest.postStoreSenCommApi(params1, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        try {
                            JSONObject result = new JSONObject(responseObj.toString());
                            ToastHelper.showToast(mContext, result.getString("msg"));
                            btStore.setBackgroundResource(R.mipmap.ic_store_sel);
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
    public void onEventMainThread(Fab event) {
        if (event.getTag().equals("0")) {
            tvMengban.setVisibility(View.GONE);
        } else {
            tvMengban.setVisibility(View.VISIBLE);
        }
    }
    @OnClick(R.id.tv_mengban)
    public void onViewClicked2() {
        Fab2 fab2 = new Fab2();
        fab2.setTag("0");
        EventBus.getDefault().post(fab2);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
