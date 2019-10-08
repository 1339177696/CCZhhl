package com.hulian.oa.work.file.admin.activity.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.Department;
import com.hulian.oa.bean.MessageEvent_x;
import com.hulian.oa.bean.People;
import com.hulian.oa.bean.People_x;
import com.hulian.oa.me.AddScheduleActivity;
import com.hulian.oa.me.SelDepartmentActivity;
import com.hulian.oa.me.SelDepartmentActivity_x;
import com.hulian.oa.me.l_adapter.DepartmentAdapter;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.views.AlertDialog;
import com.hulian.oa.views.NonScrollGridView;
import com.hulian.oa.work.file.admin.activity.document.LauncherDocumentActivity;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.FullyGridLayoutManager;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.L_GridImageAdapter;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.L_GridRoamAdapter;
import com.hulian.oa.work.file.admin.activity.document.l_adapter.L_GridRoamAdapter_qgl;
import com.hulian.oa.work.file.admin.activity.mail.MailWriteActivity;
import com.hulian.oa.work.file.admin.activity.meeting.MeetingSponsorActivity;
import com.hulian.oa.work.file.admin.activity.meeting.SelDepartmentActivity_meet_zb;
import com.hulian.oa.work.file.admin.activity.meeting.SelDepartmentActivity_meet_zb_single;
import com.hulian.oa.work.file.admin.activity.meeting.l_adapter.MeetGridViewAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.rxbus2.Subscribe;
import com.luck.picture.lib.rxbus2.ThreadMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

//发起任务
public class TaskLauncherActivity extends BaseActivity {
    //任务标题
    @BindView(R.id.et_title)
    TextView et_title;
    //任务详情
    @BindView(R.id.et_task_details)
    TextView et_task_details;
    //执行人
    @BindView(R.id.rl_opreator)
    RelativeLayout rl_opreator;
    //截止时间
    @BindView(R.id.rl_deadline)
    RelativeLayout rl_deadline;
    // 截止时间name
    @BindView(R.id.tv_deadline)
    TextView tv_deadline;

    //抄送人
    @BindView(R.id.rl_copyperson)
    RelativeLayout rl_copyperson;
    //提醒
    @BindView(R.id.rl_remind)
    RelativeLayout rl_remind;
    // 提醒name
    @BindView(R.id.tv_remind)
    TextView tv_remind;

    // 執行人nmae
    @BindView(R.id.tv_opreator)
    TextView tv_opreator;

    //发起按钮
    @BindView(R.id.tv_back_instruct)
    TextView tv_back_instruct;

    // 抄送人name
    @BindView(R.id.tv_copyperson)
    TextView tv_copyperson;
    @BindView(R.id.iv_back)
    RelativeLayout iv_back;
    private Context mContext;

    private List<People> selectList2 = new ArrayList<>();
    private List<People_x> selectList2_x = new ArrayList<>();
    // 标题、任务详情、执行人、截止时间、抄送人、提醒
    private String title;
    private String context;
    private String executor;
    private String deadline;
    private String copier = "";
    private String remind = "不提醒";

//    @BindView(R.id.recycler2)
//    RecyclerView recyclerView2;
    private L_GridRoamAdapter_qgl adapter2;
    private int maxSelectNum = 9;

    @BindView(R.id.iv)
    ImageView iv;

    @BindView(R.id.fl_content)
    FrameLayout fl_content;

    @BindView(R.id.iimmgg)
    ImageView iimmgg;

    MeetGridViewAdapter adapter;
    @BindView(R.id.gv_test)
    NonScrollGridView gvTest;
    AlertDialog myDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_task_launcher);
        myDialog = new AlertDialog(this).builder();
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mContext = this;
        init();
    }

    @OnClick({R.id.rl_remind,R.id.rl_deadline,R.id.rl_opreator,R.id.iimmgg,R.id.tv_back_instruct,R.id.iv_back,R.id.iv})
    public void onViewClicked(View view) {
        title = et_title.getText().toString().trim();
        context = et_task_details.getText().toString().trim();
        deadline = tv_deadline.getText().toString().trim();
        remind = tv_remind.getText().toString().trim();
        switch (view.getId()){
            case R.id.rl_remind:
                startActivity(new Intent(mContext, L_TaskRemindActivity.class));
                break;
            case R.id.rl_deadline:
                selectTime(tv_deadline);
                break;
            case R.id.rl_opreator:
//                Intent intent = new Intent(TaskLauncherActivity.this, SelDepartmentActivity_task.class);
//                startActivityForResult(new Intent(TaskLauncherActivity.this, SelDepartmentActivity_meet_zb.class),0);
                startActivityForResult(new Intent(TaskLauncherActivity.this, SelDepartmentActivity_meet_zb.class).putExtra("hasTop","0"), 0);

//                startActivity(intent);
                break;
            case R.id.iimmgg:
//                Intent intent1 = new Intent(TaskLauncherActivity.this, SelDepartmentActivity_x.class);
//                startActivity(intent1);

                Intent intent = new Intent(TaskLauncherActivity.this, SelDepartmentActivity_meet_zb_single.class);
                startActivityForResult(intent,110);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_back_instruct:
                if (title.isEmpty())
                {
                  Toast.makeText(TaskLauncherActivity.this,"请输入标题",Toast.LENGTH_SHORT).show();
                  return;
                }
                else if (context.isEmpty())
                {
                    Toast.makeText(TaskLauncherActivity.this,"请输入任务详情",Toast.LENGTH_SHORT).show();
                    return;

                }
                else if (executor==null||executor.isEmpty())
                {
                    Toast.makeText(TaskLauncherActivity.this,"请选择执行人",Toast.LENGTH_SHORT).show();
                    return;

                }
                else if (executor==null||deadline.isEmpty())
                {

                    Toast.makeText(TaskLauncherActivity.this,"请选择截止时间",Toast.LENGTH_SHORT).show();
                    return;

                }
                else if (executor==null||copier.isEmpty())
                {
                    Toast.makeText(TaskLauncherActivity.this,"请选择抄送人",Toast.LENGTH_SHORT).show();
                    return;

                }
                else
                {
                    getData();
                }
                break;
            case R.id.iv:
                fl_content.setVisibility(View.GONE);
                break;
        }

    }

    private void selectTime(TextView textView) {

        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //设置时间
                textView.setText(getTime(date));
            }
        }).setType(new boolean[]{true,true,true,true,true,false})
                .setLabel("年","月","日","时","分","秒")
                .build();
        pvTime.show();

    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    // 执行人
//    public void onEventMainThread(  List<People>  event) {
//        selectList2.clear();
//        selectList2.addAll(event);
//        String uids="";
//        String uname="";
//        for(People params1:selectList2)
//        {
//            uids+=params1.getUserId()+",";
//            uname+=params1.getUserName()+",";
//        }
//        tv_opreator.setText(uname.substring(0,uname.length()-1));
//        executor = uids.substring(0,uids.length()-1);
//
//   //     Toast.makeText(this, uids.substring(0,uids.length()-1), Toast.LENGTH_SHORT).show();
//    }

    // 执行人
    public void onEventMainThread(  List<People>  event) {
        selectList2 .addAll(event);
        adapter2.notifyDataSetChanged();
        String uids="";
        String uname="";
        for(People params1:selectList2)
        {
            uids+=params1.getUserId()+",";
            uname+=params1.getUserName()+",";
        }
//        tv_opreator.setText(uname.substring(0,uname.length()-1));
        executor = uids.substring(0,uids.length()-1);
    }
    // 抄送人
//    public void onEventMainThread(People_x event_x) {
//        selectList2_x.clear();
//        selectList2_x.add(event_x);
//        String uids="";
//        String uname="";
//        for(People_x params_x1:selectList2_x)
//        {
//            uids+=params_x1.getUserId();
//            uname+=params_x1.getUserName();
//        }
//        fl_content.setVisibility(View.VISIBLE);
//        tv_copyperson.setText(uname);
//        copier = uids;
//    }

    //　提醒
    public void onEvent(MessageEvent_x event){
        tv_remind.setText(event.message);

//        Toast.makeText(TaskLauncherActivity.this, event.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void getData()
    {
        RequestParams params = new RequestParams();
        params.put("title",title);
        params.put("details",context);
        params.put("executor",executor);
        params.put("endtime",deadline);
        params.put("taskreminder",remind);
        params.put("strCopier",copier);
        params.put("createby", SPUtils.get(TaskLauncherActivity.this, "userId", "").toString());
        HttpRequest.post_CoordinationRelease_add(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONObject obj = new JSONObject(result.toString());
                    String code = obj.getString("code");
                    String msg = obj.getString("msg");
                    if (code.equals("0"))
                    {
                        Toast.makeText(TaskLauncherActivity.this,msg,Toast.LENGTH_SHORT).show();
                        setResult(1);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(TaskLauncherActivity.this,msg,Toast.LENGTH_SHORT).show();
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

    //流转人初始化
    private void init() {
        FullyGridLayoutManager manager2 = new FullyGridLayoutManager(TaskLauncherActivity.this, 4, GridLayoutManager.VERTICAL, false);
//        recyclerView2.setLayoutManager(manager2);
//        adapter2 = new L_GridRoamAdapter_qgl(TaskLauncherActivity.this);
//        adapter2.setList(selectList2);
//        adapter2.setSelectMax(maxSelectNum);
//        recyclerView2.setAdapter(adapter2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1 && requestCode == 0&&data!=null) {

            List<People> mList = (List<People>) data.getSerializableExtra("mList");
            //   mList= TimeUtils.removeDuplicate(mList);

            if (mList.size() > 0) {
                String name = "";
                executor = "";
                for (People params1 : mList) {
                    executor += params1.getUserId() + ",";
                    name += params1.getUserName() + ",";
                }
                //     tvPartPerson.setText(name.substring(0, name.length() - 1));
                if(!executor.equals("")) executor = executor.substring(0, executor.length() - 1);

                adapter = new MeetGridViewAdapter(TaskLauncherActivity.this, mList);
                gvTest.setAdapter(adapter);
                List<People> finalMList = mList;
                gvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        myDialog.setGone().setTitle("提示").setMsg("确定删除么").setNegativeButton("取消", null).setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finalMList.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        }).show();
                    }
                });
            }
        }
        if (requestCode == 110&&data!=null)
        {
            List<People> mList = (List<People>) data.getSerializableExtra("mList");
            fl_content.setVisibility(View.VISIBLE);
            tv_copyperson.setText(mList.get(0).getUserName());
            copier = mList.get(0).getUserId();
            Log.e("ID",copier);
        }

    }



    //                else if (remind.isEmpty())
//                {
//
//                    Toast.makeText(TaskLauncherActivity.this,"提醒",Toast.LENGTH_SHORT).show();
//                }
}