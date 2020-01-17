package com.hulian.oa;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.views.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2018/10/10.
 */

public class BaseActivity extends FragmentActivity  {
    public FragmentManager fm;
    public FragmentTransaction ft;
    public static List<Activity> activitys;
    /**加载对话框*/
    private Dialog dialogLoading;
    protected LoadingDialog loadDialog;//加载等待弹窗
 

    protected Context mContext;
    /**加载对话框上显示的文字*/
    private TextView txt_msg;
    public Gson gson;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.statusBarLightMode(this);
        if (activitys == null) {
            activitys = new ArrayList<Activity>();
        }
        activitys.add(this);
        fm = getSupportFragmentManager();
        gson=new Gson();
        mContext = this;
        loadDialog = new LoadingDialog(this);

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // super.onSaveInstanceState(outState);
    }
    /**
     * 加载对话框不可编辑内容
     */
    public void showDialogLoading(){
//        GrobleProgress.createLoadingDialog(this);
    }
    /**
     * 关闭加载对话框
     */
    public void dismissDialogLoading(){
      //  GrobleProgress.progressDismiss();
    }

    /** 点击空白隐藏软键盘 */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }
    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private final void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * 退出应用
     *
     * @param context
     */
    public void exitApp(Context context) {// 循环结束当前所有Activity
        for (Activity ac : activitys) {
            if (ac != null) {
                ac.finish();
            }
        }
        startActivity(new Intent(this,LoginActivity.class));
    }
    public  void showToast(String text){
        Toast.makeText(BaseActivity.this,text+"", Toast.LENGTH_SHORT).show();
    }

}