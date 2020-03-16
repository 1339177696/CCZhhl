package com.hulian.oa.work.file.admin.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.Report;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.views.MyDialog;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.FullyGridLayoutManager;
import com.hulian.oa.work.file.admin.activity.leave.l_adapter.LeaveResultAdapter;
import com.hulian.oa.work.file.admin.activity.mypreview.PicturePreviewActivity;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 陈泽宇 on 2020/3/11.
 * Describe:日报页面详情
 */
public class ReadReportActivity extends BaseActivity {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.time)
    TextView time;

    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.finished_work)
    EditText finishedWork;
    @BindView(R.id.unfinished_work)
    EditText unfinishedWork;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.plan_work)
    EditText planWork;
    @BindView(R.id.coordinate_work)
    EditText coordinateWork;//协调的工作
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.recipient)
    TextView recipient;//接收人
    @BindView(R.id.opinion_people)
    TextView opinionPeople;//意见人
    @BindView(R.id.opinion_time)
    TextView opinionTime;//意见时间
    @BindView(R.id.opinion_content)
    TextView opinionContent;//意见内容
    @BindView(R.id.report_read)
    ConstraintLayout reportRead;//汇报意见查看（查看自己）
    @BindView(R.id.reporting_opinions)
    EditText reportingOpinions;//汇报意见输入（查看非自己）
    @BindView(R.id.submit)
    TextView submit;//提交

    private String dialogText;
    private LeaveResultAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private Report report;
    private boolean isMyReport;

    //图片放大预览测试
    private String[] images = {
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.activity_read_report);
        ButterKnife.bind(this);

        report = (Report) getIntent().getSerializableExtra("report");
        String type = report.getType();
        setTitleText(type);

        if (TextUtils.equals(report.getCreateBy(),SPUtils.get(ReadReportActivity.this, "userId", "").toString())){
            isMyReport = true;
        }else {
            isMyReport = false;
        }


        finishedWork.setFocusable(false);
        unfinishedWork.setFocusable(false);
        planWork.setFocusable(false);
        coordinateWork.setFocusable(false);

        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        adapter = new LeaveResultAdapter(mContext);
        recycler.setAdapter(adapter);

        //图片信息大图预览
        adapter.setOnItemClickListener(new LeaveResultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(mContext, PicturePreviewActivity.class);
                intent.putExtra(PictureConfig.EXTRA_PREVIEW_SELECT_LIST, (Serializable) selectList);
                intent.putExtra(PictureConfig.EXTRA_POSITION, position);
                mContext.startActivity(intent);
            }
        });

        getData();
    }

    @OnClick({R.id.iv_back, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.submit:
                showDialog();
                break;
        }
    }

    private void setTitleText(String type) {

        switch (type) {
            case "1":
                titleText.setText("日报");
                tv1.setText("今日完成工作");
                tv3.setText("明日工作计划");
                break;

            case "2":
                titleText.setText("周报");
                tv1.setText("本周完成工作");
                tv3.setText("下周工作计划");
                break;

            case "3":
                titleText.setText("月报");
                tv1.setText("本月完成工作");
                tv3.setText("下月工作计划");
                break;
        }
    }

    private void showDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_submit, null);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText("汇报意见发送成功");
        TextView submit = view.findViewById(R.id.confirm);

        Dialog dialog = new MyDialog(ReadReportActivity.this, true, true, (float) 0.7)
                .setNewView(view);
        dialog.show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交
                dialog.dismiss();
            }
        });
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("id", report.getId());
        HttpRequest.getGetWorkReport(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (TextUtils.equals("0",result.getString("code"))) {
                        JSONObject data = result.getJSONObject("data");
                        String content = data.getJSONObject("logs").getString("opinionContent");
                        if (!isMyReport&&TextUtils.isEmpty(content)){
                            reportingOpinions.setVisibility(View.VISIBLE);
                            submit.setVisibility(View.VISIBLE);
                        }else {
                            reportRead.setVisibility(View.VISIBLE);
                            opinionPeople.setText(data.getJSONObject("logs").getString("opinionReplePName"));
                            opinionTime.setText(data.getJSONObject("logs").getString("createTime"));
                            opinionContent.setText(data.getJSONObject("logs").getString("opinionContent"));

                        }
                        name.setText(data.getJSONObject("info").getString("createByName"));
                        time.setText(data.getJSONObject("info").getString("createTime"));

                        finishedWork.setText(data.getJSONObject("info").getString("finishWork"));
                        unfinishedWork.setText(data.getJSONObject("info").getString("unfinishedWork"));
                        planWork.setText(data.getJSONObject("info").getString("tomorrowWorkPlan"));



                        showImage(data.getJSONObject("info").getString("img"));
                    }

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

    //照片
    private void showImage(String imgList) {

        if (imgList != null && imgList != "") {
            List<String> c = Arrays.asList(imgList.split(","));
            for (int i = 0; i <= c.size() - 1; i++) {
                // 初始化list
//                if (getMIMEType(c.get(i)).equals("image/jpeg")||getMIMEType(c.get(i)).equals("image/png")||getMIMEType(c.get(i)).equals("image/gif")){
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(c.get(i));
                selectList.add(localMedia);


//                }

            }

            adapter.setList(selectList);
        }
    }


}
