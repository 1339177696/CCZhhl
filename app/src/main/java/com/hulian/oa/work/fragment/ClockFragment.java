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
import android.support.v4.app.ActivityCompat;
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
import android.widget.TextClock;
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
import com.hulian.oa.bean.Res;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.NullStringToEmptyAdapterFactory;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.TimeUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.views.LoadingDialog;
import com.hulian.oa.views.MyDialog;
import com.hulian.oa.work.file.admin.activity.ScreenReportActivity;
import com.hulian.oa.work.file.admin.activity.ScreenReportListActivity;
import com.hulian.oa.work.file.admin.activity.attendance.AttendrulesActivity;
import com.hulian.oa.work.file.admin.activity.attendance.AttendrulesmodifyActivity;
import com.netease.nim.avchatkit.common.util.TimeUtil;
import com.othershe.calendarview.utils.CalendarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

import static com.hulian.oa.utils.TimeUtils.getMway;

/**
 * Created by qgl on 2020/3/17 16:55.
 */
public class ClockFragment extends Fragment implements AMapLocationListener {
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
    private String b_jingdu = "";  // 自己精读
    private String b_weigdu = "";  // 维度
    private String f_jingdu = "";  // 服务器经度
    private String f_weigdu = "";  // 服务器维度
    private String dk_type = "";  // 打卡状态，0 正常，1 外勤
    private String dk_time = "";  // 上班打卡时间，0 正常，1 迟到
    private String xb_dk_time = "";  // 上班打卡时间，0 正常，1 迟到
    private String createTime = "";  // 服务器返回年月日
    private String registerUpAddress = "";  // 打卡地点
    private String registerUpCoordinate = "";  // 打卡坐标
    private String registerUpRemark = "";  // 外勤备注
    private String dk_id = ""; // 服务器打卡ID
    private boolean mRunning = true;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            sbTime.setText((String) msg.obj);
            xbTime.setText((String) msg.obj);
        }
    };

    protected LoadingDialog loadDialog;//加载等待弹窗

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
        loadDialog.show();
        postRule();
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
                loadDialog.show();
                //上班打卡按钮
                type = true;
                //开始定位
                post_permission();
                break;
            case R.id.re_no_btn:
                Log.d("点击了", "点击了下班");
                loadDialog.show();
                //下班打卡时间
                type = false;
                //开始定位
                post_permission();
                break;
            case R.id.tv_update:
                Log.d("点击了", "点击了更新打卡");
                loadDialog.show();
                //下班打卡时间
                type = false;
                //开始定位
                post_permission();
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
                    ToastHelper.showToast(getActivity(), "请联系管理员");
                }
                break;
        }
    }

    // 上班弹框
    private void showDialog() {
        //是正常
        View view = LayoutInflater.from(mcontext).inflate(R.layout.dialog_daka, null);
        TextView textView = view.findViewById(R.id.tv_text);
        TextView tv_m1 = view.findViewById(R.id.tv_m1);
        if (type) {
            tv_m1.setText("上班打卡成功");
        } else {
            tv_m1.setText("下班打卡成功");
        }
        textView.setText(TimeUtils.getNowhousr());
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
//                  Toast.makeText(getActivity(), "外勤打卡成功", Toast.LENGTH_LONG).show();
                registerUpRemark = et_content.getText().toString().trim();
                if (type) {
                    postData();
                } else {
                    postData1();
                }
                dialog.dismiss();
            }
        });
    }

    //发送上班数据
    public void postData() {
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
                loadDialog.dismiss();
                try {

                    JSONObject result = new JSONObject(responseObj.toString());
                    if (result.getString("code").equals("500")) {
                        showToast("打卡失败");
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
                loadDialog.dismiss();

                showToast("服务器异常");
            }
        });


    }

    // 发送下班数据
    public void postData1() {
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
                loadDialog.dismiss();

                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (result.getString("code").equals("500")) {
                        showToast("打卡失败");
                    } else {
                        showDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                loadDialog.dismiss();

                showToast("服务器异常");
            }
        });
    }

    /**
     * 请求打卡规则
     */
    public void postRule() {
        RequestParams params = new RequestParams();
        HttpRequest.PostClock_rules(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                loadDialog.dismiss();
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
                        fw_time = TimeUtils.timeStamp2Date(result.getJSONObject("data").getString("remark"), "HH:mm:ss");
                        createTime = TimeUtils.timeStamp2Date(result.getJSONObject("data").getString("remark"), "yyyy-MM-dd");
                        // 请求打卡信息
                        ClockType();
//                        // 判断上班打卡是否在规定时间内
                        if (TimeUtils.compareTwoTime(gz_sb_time, fw_time) > 0) {
                            reOnBtn.setBackgroundResource(R.drawable.clock_rela_bg_no);
                            dk_time = "1";
                        } else {
                            reOnBtn.setBackgroundResource(R.drawable.clock_rela_bg_yes);
                            dk_time = "0";
                        }
                        // 判断下班打卡是否在规定时间外
                        if (TimeUtils.compareTwoTime(fw_time, gz_xb_time) > 0) {
                            reNoBtn.setBackgroundResource(R.drawable.clock_rela_bg_no);
                            xb_dk_time = "1";
                        } else {
                            reNoBtn.setBackgroundResource(R.drawable.clock_rela_bg_yes);
                            xb_dk_time = "0";
                        }
                        //放置时间，开始计数
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (mRunning) {
                                    handler.sendMessage(handler.obtainMessage(100, TimeUtils.timeStamp2Date(fw_time, "HH:mm")));
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
                // ToastHelper.showToast(getActivity(), "服务器请求失败");
                loadDialog.dismiss();
            }
        });
    }

    // 请求打卡信息
    public void ClockType() {
        RequestParams params = new RequestParams();
        params.put("createBy", SPUtils.get(getActivity(), "userId", "").toString());
        params.put("createTime", createTime);
        HttpRequest.OnClock_Type(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
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

            }
        });

    }

    //权限判断
    public void permissions() {
        if (SPUtils.get(getActivity(), "roleKey", "").toString().contains("synthesizeLead")) {
            permi = true;
        } else if (SPUtils.get(getActivity(), "roleKey", "").toString().contains("eachLead")) {
            permi = true;
        } else {
            permi = false;
        }
    }

    @Override
    public void onDestroy() {
        mRunning = false;
        destroyLocation();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        mRunning = false;
        super.onDestroyView();
        unbinder.unbind();
    }

    // 刷新
    public void onEventMainThread(ClockFragment event) {
        postRule();
    }

    /********************高德定位************/
    private static final int MY_PERMISSIONS_REQUEST_CALL_LOCATION = 1;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //"权限已申请"
                //showLocation();
            } else {
                showToast("权限已拒绝,不能定位");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // TODO:
    private void showLocation() {
        try {
            mlocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();
            mlocationClient.setLocationListener(this);
            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(5000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            //启动定位
            mlocationClient.startLocation();
        } catch (Exception e) {

        }
    }

    /**
     * 地图回调
     *
     * @param amapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        try {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    //获取当前定位结果来源，如网络定位结果，详见定位类型表
                    Log.i("定位类型", amapLocation.getLocationType() + "");
                    Log.i("获取纬度", amapLocation.getLatitude() + "");
                    Log.i("获取经度", amapLocation.getLongitude() + "");
                    Log.i("获取精度信息", amapLocation.getAccuracy() + "");
                    //如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    Log.i("地址", amapLocation.getAddress());
                    Log.i("国家信息", amapLocation.getCountry());
                    Log.i("省信息", amapLocation.getProvince());
                    Log.i("城市信息", amapLocation.getCity());
                    Log.i("城区信息", amapLocation.getDistrict());
                    Log.i("街道信息", amapLocation.getStreet());
                    Log.i("街道门牌号信息", amapLocation.getStreetNum());
                    Log.i("城市编码", amapLocation.getCityCode());
                    Log.i("地区编码", amapLocation.getAdCode());
                    Log.i("获取当前定位点的AOI信息", amapLocation.getAoiName());
                    Log.i("获取当前室内定位的建筑物Id", amapLocation.getBuildingId());
                    Log.i("获取当前室内定位的楼层", amapLocation.getFloor());
                    Log.i("获取GPS的当前状态", amapLocation.getGpsAccuracyStatus() + "");

                    //获取定位时间
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());

                    Log.i("获取定位时间", df.format(date));
                    Log.i("poi", amapLocation.getPoiName());
                    convertToDouble(f_jingdu, 0);
                    convertToDouble(f_weigdu, 0);
                    // 停止定位
                    mlocationClient.stopLocation();
                    registerUpAddress = amapLocation.getAddress();
                    registerUpCoordinate = amapLocation.getLongitude() + "," + amapLocation.getLatitude();
                    // 判断范围，是否外勤
                    double juli = CoordinateConverter.calculateLineDistance(new DPoint(amapLocation.getLatitude(), amapLocation.getLongitude()), new DPoint(convertToDouble(f_weigdu, 0), convertToDouble(f_jingdu, 0)));
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


                } else {
                    loadDialog.dismiss();
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                    Toast.makeText(getActivity(), "定位失败，请手动打开定位权限", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * 销毁定位
     */
    private void destroyLocation() {
        if (null != mlocationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            mlocationClient.onDestroy();
            mlocationClient = null;
        }
    }

    private void showToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
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

    // 开启定位权限
    public void post_permission() {
        //检查版本是否大于M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_CALL_LOCATION);
            } else {
                //"权限已申请";
                showLocation();
            }
        }
    }

}
