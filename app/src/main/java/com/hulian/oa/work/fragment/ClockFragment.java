package com.hulian.oa.work.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hulian.oa.R;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.views.LoadingDialog;
import com.hulian.oa.views.MyDialog;
import com.hulian.oa.work.file.admin.activity.attendance.AttendrulesActivity;
import com.hulian.oa.work.file.admin.activity.attendance.AttendrulesmodifyActivity;
import com.othershe.calendarview.utils.CalendarUtil;
import org.json.JSONException;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import static com.hulian.oa.utils.TimeUtils.getMway;

/**
 * Created by qgl on 2020/3/17 16:55.
 */
public class ClockFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.clock_name)
    TextView clockName;
    @BindView(R.id.clock_department)
    TextView clockDepartment;
    @BindView(R.id.current_time)
    TextView currentTime;
    @BindView(R.id.re_on_btn)
    RelativeLayout reOnBtn;
    @BindView(R.id.re_no_btn)
    RelativeLayout reNoBtn;
    //点击上班打卡,下班打卡
    @BindView(R.id.sb_rela_btn)
    RelativeLayout sbRelabtn;
    @BindView(R.id.xb_rela_btn)
    RelativeLayout xbRelabtn;
    @BindView(R.id.tv_update)
    TextView tvUpdate;
    @BindView(R.id.permissions_no)
    RelativeLayout permissionsNo;
    @BindView(R.id.clock_fg_img)
    ImageView clockFgimg;
    @BindView(R.id.clock_fg_tv)
    TextView clockFg_tv;
    @BindView(R.id.permissions_yes)
    LinearLayout permissionsYes;
    @BindView(R.id.sb_time)
    TextView sbTime;
    @BindView(R.id.xb_time)
    TextView xbTime;
    @BindView(R.id.sb_dk_time)
    TextView sbDktime;
    @BindView(R.id.sb_dk_waiqin)
    TextView sbDkwaiqin;
    @BindView(R.id.sb_dk_chidao)
    TextView sbDkchidao;
    @BindView(R.id.sb_dk_adress)
    TextView sbDkadress;
    @BindView(R.id.work_time)
    TextView workTime;
    @BindView(R.id.under_time)
    TextView underTime;
    @BindView(R.id.xb_dk_time)
    TextView xbDktime;
    @BindView(R.id.xb_dk_waiqin)
    TextView xbDkwaiqin;
    @BindView(R.id.xb_dk_chidao)
    TextView xbDkchidao;
    @BindView(R.id.xb_dk_adress)
    TextView xbDkadress;

    private int[] cDate = CalendarUtil.getCurrentDate();
    private Context mcontext;
    private boolean type;  // 上班，下班
    private boolean permi; //权限
    private String gz_sb_time = "";  // 规则上班时间
    private String gz_xb_time = "";  // 规则下班时间
    private String jingweidu = "";  // 规则经纬度
    private String distance = "";  // 规则距离
    private String fw_time = "";  // 服务器时间
    private String f_jingdu = "";  // 服务器经度
    private String f_weigdu = "";  // 服务器维度
    private String dk_type = "";  // 打卡状态，0 正常，1 外勤
    private String dk_time = "";  // 上班打卡时间，0 正常，1 迟到
    private String xb_dk_time = "";  // 下班打卡时间，0 正常，1 迟到
    private String createTime = "";  // 服务器返回年月日
    private String registerUpAddress = "";  // 打卡地点
    private String registerUpCoordinate = "";  // 打卡坐标
    private String registerUpRemark = "";  // 外勤备注
    private String dk_id = ""; // 服务器打卡ID
    private boolean mRunning = true;
    protected LoadingDialog loadDialog;//加载等待弹窗'
    private AMapLocationListener mAMapLocationListener;
    private AMapLocationClient locationClient = null;
    private AMapLocation location;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS};

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 上班时间大于则时间
            if (TimeUtils.compareTwoTime((String) msg.obj, gz_sb_time) >= 0) {
                reOnBtn.setBackgroundResource(R.drawable.clock_rela_bg_yes);
            } else {
                reOnBtn.setBackgroundResource(R.drawable.clock_rela_bg_no);
            }
            // 下班时间大于则时间
            if (TimeUtils.compareTwoTime((String) msg.obj, gz_xb_time) > 0) {
                reNoBtn.setBackgroundResource(R.drawable.clock_rela_bg_no);
            } else {
                reNoBtn.setBackgroundResource(R.drawable.clock_rela_bg_yes);
            }
            sbTime.setText((String) msg.obj);
            xbTime.setText((String) msg.obj);
        }
    };
    private Long timer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.clockfragment, container, false);
        loadDialog = new LoadingDialog(getActivity());
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        mcontext = getActivity();
        //个人信息赋值
        tvType.setText(SPUtils.get(getActivity(), "nickname", "").toString().substring(SPUtils.get(getActivity(), "nickname", "").toString().length() - 2, SPUtils.get(getActivity(), "nickname", "").toString().length()));
        clockName.setText(SPUtils.get(getActivity(), "nickname", "").toString());
        clockDepartment.setText(SPUtils.get(getActivity(), "deptname", "").toString() + "   考勤(查看规则)");
        currentTime.setText("" + cDate[0] + "-" + cDate[1] + "-" + cDate[2] + "   星期" + getMway());
        //权限判断
        permissions();
        // 规则制定查询
        postRule();
        initListener();
        return view;
    }

    @OnClick({R.id.clock_department, R.id.rela_on, R.id.re_on_btn, R.id.re_no_btn, R.id.tv_update, R.id.permissions_no})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clock_department:
                // 考勤规则
                startActivity(new Intent(getActivity(), AttendrulesActivity.class));
                break;
            case R.id.re_on_btn:
                Log.d("点击了", "点击了上班");
                if (!loadDialog.isShowing())
                    loadDialog.show();
                //上班打卡按钮
                type = true;
                initPermission();
                break;
            case R.id.re_no_btn:
                if (!loadDialog.isShowing())
                    loadDialog.show();
                Log.d("点击了", "点击了下班");
                //下班打卡时间
                type = false;
                initPermission();
                break;
            case R.id.tv_update:
                if (!loadDialog.isShowing())
                    loadDialog.show();
                Log.d("点击了", "点击了更新打卡");
                //下班打卡时间
                type = false;
                initPermission();
                break;
            case R.id.permissions_no:
                if (permi) {
                    // 跳转修改规则，值为空(领导)
                    Intent intent = new Intent(getActivity(), AttendrulesmodifyActivity.class);
                    intent.putExtra("id", "");
                    intent.putExtra("registerContent", "");
                    intent.putExtra("upTime", "");
                    intent.putExtra("downTime", "");
                    intent.putExtra("registerAddress", "");
                    intent.putExtra("distance", "");
                    intent.putExtra("rxhRule", "");
                    intent.putExtra("jingwei", "");
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请联系管理员", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    // 上班弹框
    private void showDialog() {
        if (loadDialog.isShowing())
            loadDialog.dismiss();
        //是正常
        View view = LayoutInflater.from(mcontext).inflate(R.layout.dialog_daka, null);
        TextView textView = view.findViewById(R.id.tv_text);
        TextView tv_m1 = view.findViewById(R.id.tv_m1);
        if (type) {
            tv_m1.setText("上班打卡成功");
            textView.setText(sbTime.getText().toString());
        } else {
            tv_m1.setText("下班打卡成功");
            textView.setText(xbTime.getText().toString());
        }
        TextView submit = view.findViewById(R.id.confirm);
        Dialog dialog = new MyDialog(getActivity(), true, true, (float) 0.7).setNewView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交
                dialog.dismiss();
                EventBus.getDefault().post(new CalendarFragment());
                if (type) {
                    reOnBtn.setVisibility(View.GONE);
                    reNoBtn.setVisibility(View.VISIBLE);
                    sbRelabtn.setVisibility(View.VISIBLE);
                    // 上班打卡成功，开始赋值
                    sb_iniData();
                } else {
                    reNoBtn.setVisibility(View.GONE);
                    xbRelabtn.setVisibility(View.VISIBLE);
                    xb_iniData();
                }

            }
        });
    }

    // 外勤弹框
    private void showDialog1(String adress) {
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        View view = LayoutInflater.from(mcontext).inflate(R.layout.dialog_waiqin, null);
        TextView tv_text3 = view.findViewById(R.id.tv_adress_wq);
        TextView tv_text5 = view.findViewById(R.id.tv_text5);
        EditText et_content = view.findViewById(R.id.et_content);
        ImageView im_diss = view.findViewById(R.id.im_diss);
        Dialog dialog = new MyDialog(getActivity(), true, true, (float) 0.8).setNewView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        tv_text3.setText(adress);
        im_diss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        tv_text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//              Toast.makeText(getActivity(), "外勤打卡成功", Toast.LENGTH_LONG).show();
                registerUpRemark = et_content.getText().toString().trim();
                if (type) {
                    postData();
                } else {
                    postData1();
                }
            }
        });
    }

    //发送上班数据
    public void postData() {
        if (!loadDialog.isShowing())
            loadDialog.show();
        if (TimeUtils.compareTwoTime(sbTime.getText().toString().trim(), gz_sb_time) >= 0) {
            dk_time = "0";
        } else {
            dk_time = "1";
        }
        RequestParams params = new RequestParams();
        params.put("createBy", SPUtils.get(getActivity(), "userId", "").toString());
        params.put("deptId", SPUtils.get(getActivity(), "deptId", "").toString());
        params.put("createTime", createTime);
        params.put("registerUpTime", sbTime.getText().toString());
        params.put("registerUpAddress", registerUpAddress);
        params.put("registerUpCoordinate", registerUpCoordinate);
        params.put("registerUpState", dk_time);
        params.put("registerUpRemark", registerUpRemark);
        params.put("regisgerUpType", dk_type);
        HttpRequest.OnClock(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                if (loadDialog.isShowing())
                    loadDialog.dismiss();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (result.getString("code").equals("500")) {
                        Toast.makeText(getActivity(), "打卡失败", Toast.LENGTH_LONG).show();
                    } else {
                        // 这个是上班打卡之后出现的打卡ID
                        dk_id = result.getString("msg");
                        showDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                if (loadDialog.isShowing())
                    loadDialog.dismiss();
                Toast.makeText(getActivity(), "服务器异常", Toast.LENGTH_LONG).show();
            }
        });


    }

    // 发送下班数据
    public void postData1() {
        if (!loadDialog.isShowing())
            loadDialog.show();
        if (TimeUtils.compareTwoTime(xbTime.getText().toString().trim(), gz_xb_time) > 0) {
            xb_dk_time = "1";
        } else {
            xb_dk_time = "0";
        }
        RequestParams params = new RequestParams();
        params.put("id", dk_id);
        params.put("registerDownTime", xbTime.getText().toString());
        params.put("registerDownAddress", registerUpAddress);
        params.put("registerDownCoordinate", registerUpCoordinate);
        params.put("registerDownState", xb_dk_time);
        params.put("registerDownRemark", registerUpRemark);
        params.put("regisgerDownType", dk_type);
        HttpRequest.OnClock(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                if (loadDialog.isShowing())
                    loadDialog.dismiss();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (result.getString("code").equals("500")) {
                        Toast.makeText(getActivity(), "打卡失败", Toast.LENGTH_LONG).show();
                    } else {
                        showDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                if (loadDialog.isShowing())
                    loadDialog.dismiss();
                Toast.makeText(getActivity(), "服务器异常", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 请求打卡规则
     */
    public void postRule() {
        loadDialog.show();
        RequestParams params = new RequestParams();
        HttpRequest.PostClock_rules(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //loadDialog不用听继续转，请求个人打卡
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (result.optString("data") == "") {
                        if (permi) {
                            clockFgimg.setImageResource(R.mipmap.kq_rule);
                            clockFg_tv.setText("请设置打卡规则");
                        } else {
                            clockFgimg.setImageResource(R.mipmap.kq_administrator);
                            clockFg_tv.setText("未设置打卡规则,请联系管理员");
                        }
                        permissionsNo.setVisibility(View.VISIBLE);
                        permissionsYes.setVisibility(View.GONE);
                    } else {
                        permissionsYes.setVisibility(View.VISIBLE);
                        permissionsNo.setVisibility(View.GONE);
                        gz_sb_time = result.getJSONObject("data").getString("upTime");
                        gz_xb_time = result.getJSONObject("data").getString("downTime");
                        jingweidu = result.getJSONObject("data").getString("registerCoordinate");
                        workTime.setText("上班时间  " + gz_sb_time);
                        underTime.setText("下班时间  " + gz_xb_time);
                        //按逗号获取经纬度
                        String[] sourceStrArray = jingweidu.split(",");
                        f_jingdu = sourceStrArray[0];
                        f_weigdu = sourceStrArray[1];
                        distance = result.getJSONObject("data").getString("distance");
                        Log.d("时间转换", TimeUtils.time_getDateToString(Long.parseLong(result.getJSONObject("data").getString("remark")), "HH:mm"));
                        fw_time = TimeUtils.time_getDateToString(Long.parseLong(result.getJSONObject("data").getString("remark")), "HH:mm:ss");
                        timer = Long.parseLong(result.getJSONObject("data").getString("remark"));
                        createTime = TimeUtils.time_getDateToString(Long.parseLong(result.getJSONObject("data").getString("remark")), "yyyy-MM-dd");
                        // 请求打卡信息
                        ClockType();
//                        // 判断上班打卡是否在规定时间内
                        if (TimeUtils.compareTwoTime(gz_sb_time, fw_time) > 0) {
                            reOnBtn.setBackgroundResource(R.drawable.clock_rela_bg_no);
                            //dk_time = "1";
                        } else {
                            reOnBtn.setBackgroundResource(R.drawable.clock_rela_bg_yes);
                           // dk_time = "0";
                        }
                        // 判断下班打卡是否在规定时间外
                        if (TimeUtils.compareTwoTime(fw_time, gz_xb_time) > 0) {
                            reNoBtn.setBackgroundResource(R.drawable.clock_rela_bg_no);
                           // xb_dk_time = "1";
                        } else {
                            reNoBtn.setBackgroundResource(R.drawable.clock_rela_bg_yes);
                            //xb_dk_time = "0";
                        }

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (mRunning) {
                                    timer = timer + 1000;
                                    handler.sendMessage(handler.obtainMessage(100, TimeUtils.time_getDateToString(timer, "HH:mm")));
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(OkHttpException failuer) {
                loadDialog.dismiss();
                Toast.makeText(getActivity(), "服务器返回失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    // 请求打卡信息
    public void ClockType() {
        if (!loadDialog.isShowing())
            loadDialog.show();
        RequestParams params = new RequestParams();
        params.put("createBy", SPUtils.get(getActivity(), "userId", "").toString());
        params.put("createTime", createTime);
        HttpRequest.OnClock_Type(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                loadDialog.dismiss();
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (result.optString("data") != "") {
                        // 有打卡记录，隐藏上班打卡按钮
                        reOnBtn.setVisibility(View.GONE);
                        sbRelabtn.setVisibility(View.VISIBLE);
                        sbDktime.setText("打卡时间" + result.getJSONObject("data").getString("registerUpTime"));
                        sbDkadress.setText(result.getJSONObject("data").getString("registerUpAddress"));
                        dk_id = result.getJSONObject("data").getString("id");
                        if (result.getJSONObject("data").getString("registerUpState").equals("0")) {
                            sbDkchidao.setVisibility(View.GONE);
                            if (result.getJSONObject("data").getString("regisgerUpType").equals("0")) {
                                sbDkwaiqin.setVisibility(View.VISIBLE);
                                sbDkwaiqin.setBackgroundResource(R.drawable.kqrl_tv_bg_blue);
                                sbDkwaiqin.setText("正常");
                            } else {
                                sbDkwaiqin.setVisibility(View.VISIBLE);
                            }
                        } else {
                            sbDkchidao.setVisibility(View.VISIBLE);
                            if (result.getJSONObject("data").getString("regisgerUpType").equals("0")) {
                                sbDkwaiqin.setVisibility(View.GONE);
                            } else {
                                sbDkwaiqin.setVisibility(View.VISIBLE);
                            }
                        }
                        // 如果有下班打卡时间，隐藏打卡按钮
                        if (!TextUtils.equals(result.getJSONObject("data").getString("registerDownTime"), "null")) {
                            reNoBtn.setVisibility(View.GONE);
                            xbRelabtn.setVisibility(View.VISIBLE);
                            xbDktime.setText("打卡时间" + result.getJSONObject("data").getString("registerDownTime"));
                            xbDkadress.setText(result.getJSONObject("data").getString("registerDownAddress"));
                            if (result.getJSONObject("data").getString("registerDownState").equals("0")) {
                                xbDkchidao.setVisibility(View.GONE);
                                if (result.getJSONObject("data").getString("regisgerDownType").equals("0")) {
//                                    xbDkwaiqin.setVisibility(View.VISIBLE);
                                    xbDkwaiqin.setBackgroundResource(R.drawable.kqrl_tv_bg_blue);
                                    xbDkwaiqin.setText("正常");
                                } else {
                                    xbDkwaiqin.setVisibility(View.VISIBLE);
                                }
                            } else {
                                xbDkchidao.setVisibility(View.VISIBLE);
                                xbDkchidao.setText("早退");
                                if (result.getJSONObject("data").getString("regisgerDownType").equals("0")) {
                                    xbDkwaiqin.setVisibility(View.GONE);
                                } else {
                                    xbDkwaiqin.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            reNoBtn.setVisibility(View.VISIBLE);
                        }

                    } else {

                        // 未打卡
                        reOnBtn.setVisibility(View.VISIBLE);
                        sbRelabtn.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(OkHttpException failuer) {
                loadDialog.dismiss();
            }
        });

    }

    //权限判断
    public void permissions() {
        if (SPUtils.get(getActivity(), "roleKey", "").toString().contains("synthesizeLead")
                || SPUtils.get(getActivity(), "roleKey", "").toString().contains("eachLead")
                || SPUtils.get(getActivity(), "roleKey", "").toString().contains("boss")) {
            permi = true;
        } else {
            permi = false;
        }
    }

    @Override
    public void onDestroy() {
        mRunning = false;
        stopLocation();
        if (null != locationClient) {
            locationClient.onDestroy();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        mRunning = false;
        super.onDestroyView();
        unbinder.unbind();
    }

    // Event接受刷新指令
    public void onEventMainThread(ClockFragment event) {
        postRule();
    }

    //把String转化为double
    public static double convertToDouble(String number, double defaultValue) {
        if (TextUtils.isEmpty(number)) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(number);
        } catch (Exception e) {
            return defaultValue;
        }

    }

    // 上班回显数据
    public void sb_iniData() {
        sbDktime.setText("打卡时间" + sbTime.getText().toString());
        if (dk_type.equals("0")) {
            sbDkwaiqin.setVisibility(View.GONE);
            if (dk_time.equals("0")) {
                sbDkchidao.setVisibility(View.GONE);
                sbDkwaiqin.setVisibility(View.VISIBLE);
                sbDkwaiqin.setText("正常");
                sbDkwaiqin.setBackgroundResource(R.drawable.kqrl_tv_bg_blue);
            } else {
                sbDkchidao.setVisibility(View.VISIBLE);
            }
        } else {
            sbDkwaiqin.setVisibility(View.VISIBLE);
            if (dk_time.equals("0")) {
                sbDkchidao.setVisibility(View.GONE);
            } else {
                sbDkchidao.setVisibility(View.VISIBLE);
            }
        }
        sbDkadress.setText(registerUpAddress);

    }

    //下班回显数据
    public void xb_iniData() {
        xbDktime.setText("打卡时间" + xbTime.getText().toString());
        if (dk_type.equals("0")) {
            xbDkwaiqin.setVisibility(View.GONE);
            if (xb_dk_time.equals("0")) {
                xbDkchidao.setVisibility(View.GONE);
                xbDkwaiqin.setVisibility(View.VISIBLE);
                xbDkwaiqin.setText("正常");
                xbDkwaiqin.setBackgroundResource(R.drawable.kqrl_tv_bg_blue);
            } else {
                xbDkchidao.setVisibility(View.VISIBLE);
            }
        } else {
            xbDkwaiqin.setVisibility(View.VISIBLE);
            if (xb_dk_time.equals("0")) {
                xbDkchidao.setVisibility(View.GONE);
            } else {
                xbDkchidao.setVisibility(View.VISIBLE);
            }
        }
        xbDkadress.setText(registerUpAddress);
    }
    /********************高德定位************/
    /**
     * 初始化定位
     */
    private void initLocation() {
        if (null == locationClient) {
            //初始化client
            locationClient = new AMapLocationClient(getActivity().getApplicationContext());
            //设置定位参数
            locationClient.setLocationOption(getDefaultOption());
            // 设置定位监听
            locationClient.setLocationListener(mAMapLocationListener);
        }
    }

    /**
     * 默认的定位参数
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setMockEnable(true);//如果您希望位置被模拟，请通过setMockEnable(true);方法开启允许位置模拟
        return mOption;
    }

    /**
     * 开始定位
     */
    public void startLocation() {
        initLocation();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 定位回调
     */
    private void initListener() {
        //gps定位监听器
        mAMapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation loc) {
                try {
                    if (null != loc) {
                        stopLocation();
                        if (loc.getErrorCode() == 0) {
                            //可在其中解析amapLocation获取相应内容。
                            location = loc;
                            doWhenLocationSucess();
                        } else {
                            if (loadDialog.isShowing())
                                loadDialog.dismiss();
                            //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                            showToastWithErrorInfo(loc.getErrorCode());
                            Log.e("AmapError", "location Error, ErrCode:" + loc.getErrorCode() + ", errInfo:" + loc.getErrorInfo());
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    /**
     * 停止定位
     */
    private void stopLocation() {
        if (null != locationClient) {
            locationClient.stopLocation();
        }
    }

    /**
     * 判断手机版本获取手机权限
     */
    private void initPermission() {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(getActivity(), permissions[0]);
            int l = ContextCompat.checkSelfPermission(getActivity(), permissions[1]);
            int m = ContextCompat.checkSelfPermission(getActivity(), permissions[2]);
            int n = ContextCompat.checkSelfPermission(getActivity(), permissions[3]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED || l != PackageManager.PERMISSION_GRANTED || m != PackageManager.PERMISSION_GRANTED ||
                    n != PackageManager.PERMISSION_GRANTED) {

                // 如果没有授予该权限，就去提示用户请求
                requestPermissions(permissions, 321);
            } else {
                startLocation();
            }
        } else {
            startLocation();
        }
    }

    /**
     * 当定位成功需要做的事情
     */
    private void doWhenLocationSucess() {
        //获取当前定位结果来源，如网络定位结果，详见定位类型表
        Log.i("定位类型", location.getLocationType() + "");
        Log.i("获取纬度", location.getLatitude() + "");
        Log.i("获取经度", location.getLongitude() + "");
        Log.i("获取精度信息", location.getAccuracy() + "");
        //如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
        Log.i("地址", location.getAddress());
        Log.i("国家信息", location.getCountry());
        Log.i("省信息", location.getProvince());
        Log.i("城市信息", location.getCity());
        Log.i("城区信息", location.getDistrict());
        Log.i("街道信息", location.getStreet());
        Log.i("街道门牌号信息", location.getStreetNum());
        Log.i("城市编码", location.getCityCode());
        Log.i("地区编码", location.getAdCode());
        Log.i("获取当前定位点的AOI信息", location.getAoiName());
        Log.i("获取当前室内定位的建筑物Id", location.getBuildingId());
        Log.i("获取当前室内定位的楼层", location.getFloor());
        Log.i("获取GPS的当前状态", location.getGpsAccuracyStatus() + "");
        Log.i("poi", location.getPoiName());
//        convertToDouble(f_jingdu, 0);
//        convertToDouble(f_weigdu, 0);
        // 模拟器存在，获取经纬度没有地址的情况
        if (TextUtils.equals(location.getAddress(), "")) {
            if (loadDialog.isShowing())
                loadDialog.dismiss();
            Toast.makeText(getActivity(), "获取地址失败", Toast.LENGTH_LONG).show();
        } else {
            registerUpAddress = location.getAddress();
            registerUpCoordinate = location.getLongitude() + "," + location.getLatitude();
            // 判断范围，是否外勤
            double juli = CoordinateConverter.calculateLineDistance(new DPoint(location.getLatitude(), location.getLongitude()), new DPoint(convertToDouble(f_weigdu, 0), convertToDouble(f_jingdu, 0)));
            if (type) {
                // 上班
                if (juli <= convertToDouble(distance, 0)) {
                    dk_type = "0";
                    postData();
                } else {
                    dk_type = "1";
                    showDialog1(registerUpAddress);
                }
            } else {
                //下班
                if (juli <= convertToDouble(distance, 0)) {
                    dk_type = "0";
                    postData1();
                } else {
                    dk_type = "1";
                    showDialog1(registerUpAddress);
                }
            }
        }

    }

    /**
     * 用户权限 申请 的回调方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //如果没有获取权限，那么可以提示用户去设置界面--->应用权限开启权限
                    Toast toast = Toast.makeText(getActivity(), "请开启权限", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    //获取权限成功
                    startLocation();
                }
            }
        }
    }

    /**
     * Toast
     *
     * @param error
     */
    private void showToastWithErrorInfo(int error) {
        String tips = "定位错误码：" + error;
        switch (error) {
            case 4:
                tips = "请检查设备网络是否通畅，检查通过接口设置的网络访问超时时间，建议采用默认的30秒。";
                break;
            case 7:
                tips = "请仔细检查key绑定的sha1值与apk签名sha1值是否对应。";
                break;
            case 12:
                tips = "请在设备的设置中开启app的定位权限。";
                break;
            case 18:
                tips = "建议手机关闭飞行模式，并打开WIFI开关";
                break;
            case 19:
                tips = "建议手机插上sim卡，打开WIFI开关";
                break;
        }
        Toast.makeText(getActivity(), tips, Toast.LENGTH_LONG).show();
    }

}
