package com.hulian.oa.xcpsactivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.views.DragFloatActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qgl on 2020/3/12 10:15.
 */
public class XCDetelisActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.li_1)
    LinearLayout li1;
    @BindView(R.id.li_2)
    LinearLayout li2;
    @BindView(R.id.li_3)
    LinearLayout li3;
    @BindView(R.id.li_4)
    LinearLayout li4;
    @BindView(R.id.li_5)
    LinearLayout li5;
    @BindView(R.id.li_6)
    LinearLayout li6;
    @BindView(R.id.li_7)
    LinearLayout li7;
    @BindView(R.id.li_8)
    LinearLayout li8;
    @BindView(R.id.li_9)
    LinearLayout li9;
    @BindView(R.id.li_10)
    LinearLayout li10;
    @BindView(R.id.li_11)
    LinearLayout li11;
    @BindView(R.id.li_12)
    LinearLayout li12;
    @BindView(R.id.floatbutton)
    DragFloatActionButton floatbutton;
    @BindView(R.id.xcd_img)
    ImageView xcd_img;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xcdetelisactivity);
        ButterKnife.bind(this);
        Intent intnet = getIntent();
        int title_value = intnet.getIntExtra("title",-1);
        if (title_value==1){
          tv_title.setText("初审");
        }else if(title_value==2){
           tv_title.setText("复审");
        }else if (title_value==3){
            tv_title.setText("许可确认");
        }
    }


    @OnClick({R.id.rl_title, R.id.li_1, R.id.li_2, R.id.li_3, R.id.li_4, R.id.li_5, R.id.li_6, R.id.li_7, R.id.li_8, R.id.li_9, R.id.li_10, R.id.li_11, R.id.li_12,R.id.floatbutton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_title:
                finish();
                break;
            case R.id.li_1:
                li1.setBackgroundColor(Color.parseColor("#E6F3F8"));
                li2.setBackgroundColor(Color.parseColor("#ffffff"));
                li3.setBackgroundColor(Color.parseColor("#ffffff"));
                li4.setBackgroundColor(Color.parseColor("#ffffff"));
                li5.setBackgroundColor(Color.parseColor("#ffffff"));
                li6.setBackgroundColor(Color.parseColor("#ffffff"));
                li7.setBackgroundColor(Color.parseColor("#ffffff"));
                li8.setBackgroundColor(Color.parseColor("#ffffff"));
                li9.setBackgroundColor(Color.parseColor("#ffffff"));
                li10.setBackgroundColor(Color.parseColor("#ffffff"));
                li11.setBackgroundColor(Color.parseColor("#ffffff"));
                li12.setBackgroundColor(Color.parseColor("#ffffff"));
                xcd_img.setImageResource(R.mipmap.xiangqing);
                break;
            case R.id.li_2:
                li1.setBackgroundColor(Color.parseColor("#ffffff"));
                li2.setBackgroundColor(Color.parseColor("#E6F3F8"));
                li3.setBackgroundColor(Color.parseColor("#ffffff"));
                li4.setBackgroundColor(Color.parseColor("#ffffff"));
                li5.setBackgroundColor(Color.parseColor("#ffffff"));
                li6.setBackgroundColor(Color.parseColor("#ffffff"));
                li7.setBackgroundColor(Color.parseColor("#ffffff"));
                li8.setBackgroundColor(Color.parseColor("#ffffff"));
                li9.setBackgroundColor(Color.parseColor("#ffffff"));
                li10.setBackgroundColor(Color.parseColor("#ffffff"));
                li11.setBackgroundColor(Color.parseColor("#ffffff"));
                li12.setBackgroundColor(Color.parseColor("#ffffff"));
                xcd_img.setImageResource(R.mipmap.xukeleibie_img);
                break;
            case R.id.li_3:
                li1.setBackgroundColor(Color.parseColor("#ffffff"));
                li2.setBackgroundColor(Color.parseColor("#ffffff"));
                li3.setBackgroundColor(Color.parseColor("#E6F3F8"));
                li4.setBackgroundColor(Color.parseColor("#ffffff"));
                li5.setBackgroundColor(Color.parseColor("#ffffff"));
                li6.setBackgroundColor(Color.parseColor("#ffffff"));
                li7.setBackgroundColor(Color.parseColor("#ffffff"));
                li8.setBackgroundColor(Color.parseColor("#ffffff"));
                li9.setBackgroundColor(Color.parseColor("#ffffff"));
                li10.setBackgroundColor(Color.parseColor("#ffffff"));
                li11.setBackgroundColor(Color.parseColor("#ffffff"));
                li12.setBackgroundColor(Color.parseColor("#ffffff"));
                xcd_img.setImageResource(R.mipmap.xiangguanrenzheng_img);
                break;
            case R.id.li_4:
                li1.setBackgroundColor(Color.parseColor("#ffffff"));
                li2.setBackgroundColor(Color.parseColor("#ffffff"));
                li3.setBackgroundColor(Color.parseColor("#ffffff"));
                li4.setBackgroundColor(Color.parseColor("#E6F3F8"));
                li5.setBackgroundColor(Color.parseColor("#ffffff"));
                li6.setBackgroundColor(Color.parseColor("#ffffff"));
                li7.setBackgroundColor(Color.parseColor("#ffffff"));
                li8.setBackgroundColor(Color.parseColor("#ffffff"));
                li9.setBackgroundColor(Color.parseColor("#ffffff"));
                li10.setBackgroundColor(Color.parseColor("#ffffff"));
                li11.setBackgroundColor(Color.parseColor("#ffffff"));
                li12.setBackgroundColor(Color.parseColor("#ffffff"));
                xcd_img.setImageResource(R.mipmap.danweiziyuan_img);
                break;
            case R.id.li_5:
                li1.setBackgroundColor(Color.parseColor("#ffffff"));
                li2.setBackgroundColor(Color.parseColor("#ffffff"));
                li3.setBackgroundColor(Color.parseColor("#ffffff"));
                li4.setBackgroundColor(Color.parseColor("#ffffff"));
                li5.setBackgroundColor(Color.parseColor("#E6F3F8"));
                li6.setBackgroundColor(Color.parseColor("#ffffff"));
                li7.setBackgroundColor(Color.parseColor("#ffffff"));
                li8.setBackgroundColor(Color.parseColor("#ffffff"));
                li9.setBackgroundColor(Color.parseColor("#ffffff"));
                li10.setBackgroundColor(Color.parseColor("#ffffff"));
                li11.setBackgroundColor(Color.parseColor("#ffffff"));
                li12.setBackgroundColor(Color.parseColor("#ffffff"));
                xcd_img.setImageResource(R.mipmap.renyuanzucheng_img);
                break;
            case R.id.li_6:
                li1.setBackgroundColor(Color.parseColor("#ffffff"));
                li2.setBackgroundColor(Color.parseColor("#ffffff"));
                li3.setBackgroundColor(Color.parseColor("#ffffff"));
                li4.setBackgroundColor(Color.parseColor("#ffffff"));
                li5.setBackgroundColor(Color.parseColor("#ffffff"));
                li6.setBackgroundColor(Color.parseColor("#E6F3F8"));
                li7.setBackgroundColor(Color.parseColor("#ffffff"));
                li8.setBackgroundColor(Color.parseColor("#ffffff"));
                li9.setBackgroundColor(Color.parseColor("#ffffff"));
                li10.setBackgroundColor(Color.parseColor("#ffffff"));
                li11.setBackgroundColor(Color.parseColor("#ffffff"));
                li12.setBackgroundColor(Color.parseColor("#ffffff"));
                xcd_img.setImageResource(R.mipmap.renyuanqingkuang_img);
                break;
            case R.id.li_7:
                li1.setBackgroundColor(Color.parseColor("#ffffff"));
                li2.setBackgroundColor(Color.parseColor("#ffffff"));
                li3.setBackgroundColor(Color.parseColor("#ffffff"));
                li4.setBackgroundColor(Color.parseColor("#ffffff"));
                li5.setBackgroundColor(Color.parseColor("#ffffff"));
                li6.setBackgroundColor(Color.parseColor("#ffffff"));
                li7.setBackgroundColor(Color.parseColor("#E6F3F8"));
                li8.setBackgroundColor(Color.parseColor("#ffffff"));
                li9.setBackgroundColor(Color.parseColor("#ffffff"));
                li10.setBackgroundColor(Color.parseColor("#ffffff"));
                li11.setBackgroundColor(Color.parseColor("#ffffff"));
                li12.setBackgroundColor(Color.parseColor("#ffffff"));
                xcd_img.setImageResource(R.mipmap.shebeizhuangkuang_img);
                break;
            case R.id.li_8:
                li1.setBackgroundColor(Color.parseColor("#ffffff"));
                li2.setBackgroundColor(Color.parseColor("#ffffff"));
                li3.setBackgroundColor(Color.parseColor("#ffffff"));
                li4.setBackgroundColor(Color.parseColor("#ffffff"));
                li5.setBackgroundColor(Color.parseColor("#ffffff"));
                li6.setBackgroundColor(Color.parseColor("#ffffff"));
                li7.setBackgroundColor(Color.parseColor("#ffffff"));
                li8.setBackgroundColor(Color.parseColor("#E6F3F8"));
                li9.setBackgroundColor(Color.parseColor("#ffffff"));
                li10.setBackgroundColor(Color.parseColor("#ffffff"));
                li11.setBackgroundColor(Color.parseColor("#ffffff"));
                li12.setBackgroundColor(Color.parseColor("#ffffff"));
                xcd_img.setImageResource(R.mipmap.shiyanyiqi_img);
                break;
            case R.id.li_9:
                li1.setBackgroundColor(Color.parseColor("#ffffff"));
                li2.setBackgroundColor(Color.parseColor("#ffffff"));
                li3.setBackgroundColor(Color.parseColor("#ffffff"));
                li4.setBackgroundColor(Color.parseColor("#ffffff"));
                li5.setBackgroundColor(Color.parseColor("#ffffff"));
                li6.setBackgroundColor(Color.parseColor("#ffffff"));
                li7.setBackgroundColor(Color.parseColor("#ffffff"));
                li8.setBackgroundColor(Color.parseColor("#ffffff"));
                li9.setBackgroundColor(Color.parseColor("#E6F3F8"));
                li10.setBackgroundColor(Color.parseColor("#ffffff"));
                li11.setBackgroundColor(Color.parseColor("#ffffff"));
                li12.setBackgroundColor(Color.parseColor("#ffffff"));
                xcd_img.setImageResource(R.mipmap.shebeinengli_img);
                break;
            case R.id.li_10:
                li1.setBackgroundColor(Color.parseColor("#ffffff"));
                li2.setBackgroundColor(Color.parseColor("#ffffff"));
                li3.setBackgroundColor(Color.parseColor("#ffffff"));
                li4.setBackgroundColor(Color.parseColor("#ffffff"));
                li5.setBackgroundColor(Color.parseColor("#ffffff"));
                li6.setBackgroundColor(Color.parseColor("#ffffff"));
                li7.setBackgroundColor(Color.parseColor("#ffffff"));
                li8.setBackgroundColor(Color.parseColor("#ffffff"));
                li9.setBackgroundColor(Color.parseColor("#ffffff"));
                li10.setBackgroundColor(Color.parseColor("#E6F3F8"));
                li11.setBackgroundColor(Color.parseColor("#ffffff"));
                li12.setBackgroundColor(Color.parseColor("#ffffff"));
                xcd_img.setImageResource(R.mipmap.fenbaowaiqin_img);
                break;
            case R.id.li_11:
                li1.setBackgroundColor(Color.parseColor("#ffffff"));
                li2.setBackgroundColor(Color.parseColor("#ffffff"));
                li3.setBackgroundColor(Color.parseColor("#ffffff"));
                li4.setBackgroundColor(Color.parseColor("#ffffff"));
                li5.setBackgroundColor(Color.parseColor("#ffffff"));
                li6.setBackgroundColor(Color.parseColor("#ffffff"));
                li7.setBackgroundColor(Color.parseColor("#ffffff"));
                li8.setBackgroundColor(Color.parseColor("#ffffff"));
                li9.setBackgroundColor(Color.parseColor("#ffffff"));
                li10.setBackgroundColor(Color.parseColor("#ffffff"));
                li11.setBackgroundColor(Color.parseColor("#E6F3F8"));
                li12.setBackgroundColor(Color.parseColor("#ffffff"));
                xcd_img.setImageResource(R.mipmap.zhizaoqingkuang_img);
                break;
            case R.id.li_12:
                li1.setBackgroundColor(Color.parseColor("#ffffff"));
                li2.setBackgroundColor(Color.parseColor("#ffffff"));
                li3.setBackgroundColor(Color.parseColor("#ffffff"));
                li4.setBackgroundColor(Color.parseColor("#ffffff"));
                li5.setBackgroundColor(Color.parseColor("#ffffff"));
                li6.setBackgroundColor(Color.parseColor("#ffffff"));
                li7.setBackgroundColor(Color.parseColor("#ffffff"));
                li8.setBackgroundColor(Color.parseColor("#ffffff"));
                li9.setBackgroundColor(Color.parseColor("#ffffff"));
                li10.setBackgroundColor(Color.parseColor("#ffffff"));
                li11.setBackgroundColor(Color.parseColor("#ffffff"));
                li12.setBackgroundColor(Color.parseColor("#E6F3F8"));
                xcd_img.setImageResource(R.mipmap.shenqingcailiao_img);
                break;
            case R.id.floatbutton:
                final View inflate = LayoutInflater.from(this).inflate(R.layout.banlixinxi_dialog, null);
                final RadioButton yes = (RadioButton)inflate.findViewById(R.id.tv_disagree);
                final RadioButton no = (RadioButton)inflate.findViewById(R.id.tv_agree);

                final Dialog dialog = new AlertDialog
                        .Builder(this)
                        .setView(inflate)
                        .show();
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                // 将对话框的大小按屏幕大小的百分比设置
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                lp.gravity= Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                lp.width = (int)(display.getWidth() * 1); //设置宽度
                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                break;
        }
    }
}
