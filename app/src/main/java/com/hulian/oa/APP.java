package com.hulian.oa;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;

public class APP extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
//        图片框架
        Fresco.initialize(this);
        // ... your codes
        context = getApplicationContext();


    }

    private String loginType = "";
    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }



}