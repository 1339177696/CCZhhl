/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hulian.oa.iac.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hulian.oa.R;

public class WaitDialog extends Dialog {
private Context context;
    public WaitDialog(@NonNull Context context) {
        super(context);
    }

    public WaitDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected WaitDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        View llBg = (View) findViewById(R.id.dialog_ll_bg);
        ProgressBar pbBg = (ProgressBar) findViewById(R.id.pb_bg);
        TextView tvMsg = (TextView) findViewById(R.id.dialog_tv_msg);
        pbBg.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.dialog_rotate_img,null));
        llBg.setBackgroundResource(R.drawable.dialog_shape_gray);
        tvMsg.setTextColor(Color.WHITE);
        tvMsg.setText("加载中...");
    }

    //    public WaitDialog(Context context) {
//        super(context);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setCanceledOnTouchOutside(false);
//        setProgressStyle(STYLE_SPINNER);
////        setMessage(context.getText(R.string.wait_dialog_title));
//        setMessage("正在请求，请稍后…");
//    }
}
