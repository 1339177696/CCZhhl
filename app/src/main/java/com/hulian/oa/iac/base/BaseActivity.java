package com.hulian.oa.iac.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SupportActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hulian.oa.R;
import com.hulian.oa.iac.view.CameraActivity;
import butterknife.ButterKnife;

/**
 * Created by czy on 2019/8/19 9:50.
 */
@SuppressLint("RestrictedApi")
public abstract class BaseActivity extends SupportActivity {

    private TextView title;
    protected Context mContext;
    private ImageView back;
    private ImageView change;
    private ProgressDialog progressDialog;
    private final int GET_PERMISSION_REQUEST = 100; //权限申请自定义码
    /**
     * 用来标记取消。
     */
    private Object object = new Object();

    /**
     * 请求队列。
     */
//    private RequestQueue mQueue;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentView());
        ButterKnife.bind(this);
        // 初始化请求队列，传入的参数是请求并发值。
//        mQueue = NoHttp.newRequestQueue(1);
        title = findViewById(R.id.title_text);
        mContext = this;
        setStatusBar();
        onCreate();

    }

    protected abstract int setContentView();

    protected abstract void onCreate();


    protected void showToast(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }

    }


    protected void setTitleText(String titleStr) {
        title.setText(titleStr);
    }

    protected void addBack() {
        back = findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected ImageView addChange(){
        change = findViewById(R.id.change);
        change.setVisibility(View.VISIBLE);
        return change;
    }


    /**
     * 加载框
     */
    public void buildDialog(String msg) {
        if (TextUtils.isEmpty(msg)) {
            msg = "加载中...";
        }
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    /**
     * @Description: TODO 取消加载框
     * @author Sunday
     * @date 2015年12月25日
     */
    public void cancelDialog() {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
// TODO: handle exception
                } finally {
                    if (progressDialog != null)
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                }
            }
        }.start();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            View v = getCurrentFocus();

            //如果不是落在EditText区域，则需要关闭输入法
            if (HideKeyboard(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    private boolean HideKeyboard(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {

            int[] location = {0, 0};
            view.getLocationInWindow(location);

            //获取现在拥有焦点的控件view的位置，即EditText
            int left = location[0], top = location[1], bottom = top + view.getHeight(), right = left + view.getWidth();
            //判断我们手指点击的区域是否落在EditText上面，如果不是，则返回true，否则返回false
            boolean isInEt = (event.getX() > left && event.getX() < right && event.getY() > top
                    && event.getY() < bottom);
            return !isInEt;
        }
        return false;
    }


    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.b_text_color_true,null));//设置状态栏颜色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |               View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        }
    }

    /**
     * 获取权限
     */
    public void getPermissions(int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager
                    .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager
                            .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager
                            .PERMISSION_GRANTED) {
                startActivityForResult(new Intent(BaseActivity.this, CameraActivity.class), requestCode);
            } else {
                //不具有获取权限，需要进行权限申请
                ActivityCompat.requestPermissions(BaseActivity.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA}, GET_PERMISSION_REQUEST);
            }
        } else {
            startActivityForResult(new Intent(BaseActivity.this, CameraActivity.class), requestCode);
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GET_PERMISSION_REQUEST) {
            int size = 0;
            if (grantResults.length >= 1) {
                int writeResult = grantResults[0];
                //读写内存权限
                boolean writeGranted = writeResult == PackageManager.PERMISSION_GRANTED;//读写内存权限
                if (!writeGranted) {
                    size++;
                }
                //录音权限
                int recordPermissionResult = grantResults[1];
                boolean recordPermissionGranted = recordPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!recordPermissionGranted) {
                    size++;
                }
                //相机权限
                int cameraPermissionResult = grantResults[2];
                boolean cameraPermissionGranted = cameraPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!cameraPermissionGranted) {
                    size++;
                }
                if (size == 0) {
                    startActivityForResult(new Intent(BaseActivity.this, CameraActivity.class), 100);
                } else {
                    Toast.makeText(this, "请到设置-权限管理中开启", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 发起请求。
     *
     * @param what      what.
     * @param request   请求对象。
     * @param callback  回调函数。
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     * @param <T>       想请求到的数据类型。
     */
//    public <T> void request(int what, Request<T> request, HttpListener<T> callback,
//                            boolean canCancel, boolean isLoading) {
//        request.setCancelSign(object);
//        mQueue.add(what, request, new HttpResponseListener<>(this, request, callback, canCancel, isLoading));
//    }
}
