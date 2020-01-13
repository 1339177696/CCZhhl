package com.hulian.oa.work.file.admin.activity.mail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.duke.dfileselector.activity.DefaultSelectorActivity;
import com.duke.dfileselector.util.FileSelectorUtils;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.People;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.URLImageParser;
import com.hulian.oa.views.AlertDialog;
import com.hulian.oa.views.MyGridView;
import com.hulian.oa.work.file.admin.activity.meeting.SelDepartmentActivity_meet_x;
import com.hulian.oa.work.file.admin.activity.meeting.SelDepartmentActivity_meet_zb;
import com.hulian.oa.work.file.admin.activity.meeting.l_adapter.MeetGridViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 写邮件
 */
public class MailWriteActivity extends BaseActivity {
    AlertDialog myDialog;
    @BindView(R.id.gv_test)
    MyGridView gvTest;

    @BindView(R.id.gv_test2)
    MyGridView gvTest2;

    //收件人
    @BindView(R.id.work_mail_shoujianren)
    TextView work_mail_shoujianren;
    //主题
    @BindView(R.id.tv_theme)
    EditText tv_theme;
    //内容
    @BindView(R.id.et_content)
    EditText et_content;
    //发送
    @BindView(R.id.tv_send)
    TextView tv_send;
    //选择文件
    @BindView(R.id.et_annex)
    EditText et_annex;
    @BindView(R.id.tv_user)
    TextView tvUser;

    private String id;
    private String aid;
    private String zhuti = "";
    private String neirong = "";
    private String youxiang;
    private String username = "";
    private List<String> selectList = new ArrayList<>();

    private String receiveMailCcName="";
    private String receiveMailCc="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_mail_write);
        ButterKnife.bind(this);
        myDialog = new AlertDialog(this).builder();
        EventBus.getDefault().register(this);
        tvUser.setText(SPUtils.get(MailWriteActivity.this, "email", "").toString());
        if (getIntent().getStringExtra("type").equals("1")){
            tv_theme.setText("转发："+getIntent().getStringExtra("zhuti"));
            URLImageParser imageGetter = new URLImageParser( et_content);
            et_content.setText(Html.fromHtml(getIntent().getStringExtra("neirong"), imageGetter, null));
        }
    }

    @OnClick({R.id.tv_send, R.id.et_annex, R.id.work_mail_shoujianren, R.id.my_img1, R.id.my_img2, R.id.rl_title})
    public void onViewClicked(View view) {
        zhuti = tv_theme.getText().toString().trim();
        neirong = et_content.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_send:
                if (username.equals("")) {
                    Toast.makeText(MailWriteActivity.this, "请选择收件人", Toast.LENGTH_SHORT).show();
                } else if (zhuti.equals("")) {
                    Toast.makeText(MailWriteActivity.this, "请输入邮件主题", Toast.LENGTH_SHORT).show();
                } else if (neirong.equals("")) {
                    Toast.makeText(MailWriteActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                } else {
                    getData();
                }
                break;
            case R.id.et_annex:
                et_annex.setCursorVisible(false);
                //文件选择器  过滤条件在 DefaultSelectorActivity.onPermissionSuccess 中添加
                DefaultSelectorActivity.startActivityForResult(this, false, true, 3);
                break;
            case R.id.work_mail_shoujianren:
                Intent intent = new Intent(MailWriteActivity.this, SelDepartmentActivity_meet_x.class);
                startActivity(intent);
                break;
            case R.id.my_img1:
                startActivityForResult(new Intent(MailWriteActivity.this, SelDepartmentActivity_meet_zb.class).putExtra("hasTop","0"), 0);
                break;
            case R.id.my_img2:
                startActivityForResult(new Intent(MailWriteActivity.this, SelDepartmentActivity_meet_zb.class).putExtra("hasTop","0"), 1);
                break;
            case R.id.rl_title:
                finish();
        }

    }

    private void printData(ArrayList<String> list) {
        if (FileSelectorUtils.isEmpty(list)) {
            return;
        }
        int size = list.size();
        Log.v("EMAIL", "获取到数据-开始 size = " + size);
        StringBuffer stringBuffer = new StringBuffer("选中的文件：");
        for (int i = 0; i < size; i++) {
            int a = list.get(i).length();
            String ccc = list.get(i).substring(list.get(i).lastIndexOf('/') + 1, a);
            stringBuffer.append(ccc);
            stringBuffer.append(",");
        }
        String file_name = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
        et_annex.setText(file_name);
        selectList = list;
        Log.v("EMAIL", "获取到数据-结束");
    }

    // 发送邮件
    private void getData() {
        loadDialog.show();
        RequestParams params = new RequestParams();
        params.put("title", zhuti);
        params.put("content", neirong);
        params.put("sendMailName", SPUtils.get(MailWriteActivity.this, "nickname", "").toString());
        params.put("sendMail", SPUtils.get(MailWriteActivity.this, "email", "").toString());
        params.put("sendMailPsd", "123456");
        params.put("receiveMailTo", youxiang);
        params.put("receiveMailToName", username);
        if(!receiveMailCc.equals("")&&!receiveMailCcName.equals("")) {
            params.put("receiveMailCc", receiveMailCc);
            params.put("receiveMailCcName", receiveMailCcName);
        }
        List<File> fils = new ArrayList<>();
        for (String imgurl : selectList) {
            fils.add(new File(imgurl));
        }
        HttpRequest.post_sendMail(params, fils, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                loadDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(responseObj.toString());
                    String msg = obj.getString("msg");
                    Toast.makeText(MailWriteActivity.this, msg, Toast.LENGTH_SHORT).show();
                    setResult(1);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                loadDialog.dismiss();
                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                Toast.makeText(MailWriteActivity.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onEventMainThread(List<People> resultmemberLis) {
//        if (resultmemberLis.size() > 0) {
//            String name = "";
//            String email = "";
//            aid = "";
//            for (People params1 : resultmemberLis) {
//                aid += params1.getUserId() + ",";
//                name += params1.getUserName() + ",";
//                email += params1.getEmail() + ",";
//            }
//            work_mail_shoujianren.setText(name.substring(0, name.length() - 1));
//            id = aid.substring(0, aid.length() - 1);
//            youxiang = email.substring(0, email.length() - 1);
//            username = name.substring(0, name.length() - 1);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == DefaultSelectorActivity.FILE_SELECT_REQUEST_CODE) {
            printData(DefaultSelectorActivity.getDataFromIntent(data));
        } else if (resultCode == 1 && requestCode == 0) {

            List<People> mList = (List<People>) data.getSerializableExtra("mList");

            if (mList.size() > 0) {
                String name = "";
                String email = "";
                aid = "";
                for (People params1 : mList) {
                    aid += params1.getUserId() + ",";
                    name += params1.getUserName() + ",";
                    email += params1.getEmail() + ",";
                }
                work_mail_shoujianren.setText(name.substring(0, name.length() - 1));
                id = aid.substring(0, aid.length() - 1);
                youxiang = email.substring(0, email.length() - 1);
                username = name.substring(0, name.length() - 1);
            }
            MeetGridViewAdapter adapter = new MeetGridViewAdapter(MailWriteActivity.this, mList);
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
        } else if (resultCode == 1 && requestCode == 1) {

            List<People> mList = (List<People>) data.getSerializableExtra("mList");

            if (mList.size() > 0) {
                String name = "";
                String email = "";
                aid="";
                for (People params1 : mList) {
                    aid += params1.getUserId() + ",";
                    name += params1.getUserName() + ",";
                    email += params1.getEmail() + ",";
                }
                work_mail_shoujianren.setText(name.substring(0, name.length() - 1));
                id = aid.substring(0, aid.length() - 1);
                receiveMailCc = email.substring(0, email.length() - 1);
                receiveMailCcName = name.substring(0, name.length() - 1);
            }
                MeetGridViewAdapter adapter2 = new MeetGridViewAdapter(MailWriteActivity.this, mList);
                gvTest2.setAdapter(adapter2);
                List<People> finalMList = mList;
                gvTest2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        myDialog.setGone().setTitle("提示").setMsg("确定删除么").setNegativeButton("取消", null).setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finalMList.remove(position);
                                adapter2.notifyDataSetChanged();
                            }
                        }).show();
                    }
                });
            }
    }
}
