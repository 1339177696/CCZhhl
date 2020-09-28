package com.duke.dfileselector.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.duke.dfileselector.R;
import com.duke.dfileselector.helper.FileSelector;
import com.duke.dfileselector.widget.FileSelectorLayout;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class DefaultSelectorActivity extends BasePermissionActivity implements View.OnClickListener {
    //startActivityForResult request code
    public static final int FILE_SELECT_REQUEST_CODE = 255;
    //send broadcast when get file
    public static final String FILE_SELECT_ACTION = "com.duke.dfileselector.select.file.action";
    //返回数据为路径字符串数组
    private static final String FILE_SELECT_STRING_ARRAY_PARAM = "file_select_string_array_param";
    private String[] mTypes = new String[]{".txt",".doc"};
    private RelativeLayout imageViewBack;
    private FileSelectorLayout fileSelectorLayout;
    //接收到的参数
    private boolean isForResult;//是否是 startActivityForResult 方式启动的
    private boolean isNeedBroadCast;//是否需要发送广播传递选中的数据
    private boolean isMultiMode;//是否是多选模式
    private int maxFileCount;//多选模式文件的最大数量

    public static void startActivity(Context context) {
        startActivity(context, true);
    }

    public static void startActivity(Context context, boolean isNeedBroadCast) {
        startActivity(context, isNeedBroadCast, false, 0);
    }

    public static void startActivity(Context context, boolean isNeedBroadCast, boolean isMultiMode, int maxFileCount) {
        start(context, false, isNeedBroadCast, isMultiMode, maxFileCount);
    }

    public static void startActivityForResult(Activity activity) {
        startActivityForResult(activity, false);
    }

    public static void startActivityForResult(Activity activity, boolean isNeedBroadCast) {
        startActivityForResult(activity, isNeedBroadCast, false, 0);
    }

    public static void startActivityForResult(Activity activity, boolean isNeedBroadCast, boolean isMultiMode, int maxFileCount) {
        start(activity, true, isNeedBroadCast, isMultiMode, maxFileCount);
    }

    private static void start(Context context, boolean isForResult, boolean isNeedBroadCast, boolean isMultiMode, int maxFileCount) {
        if (context == null || (isForResult && !(context instanceof Activity))) {
            return;
        }
        Intent intent = new Intent(context, DefaultSelectorActivity.class);
        intent.putExtra("isForResult", isForResult);
        intent.putExtra("isNeedBroadCast", isNeedBroadCast);
        intent.putExtra("isMultiMode", isMultiMode);
        intent.putExtra("maxFileCount", maxFileCount);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, FILE_SELECT_REQUEST_CODE);
        } else {
            context.startActivity(intent);
        }
    }

    private void parseIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        isForResult = intent.getBooleanExtra("isForResult", false);
        isNeedBroadCast = intent.getBooleanExtra("isNeedBroadCast", false);
        isMultiMode = intent.getBooleanExtra("isMultiMode", false);
        maxFileCount = intent.getIntExtra("maxFileCount", 0);
    }

    public static ArrayList<String> getDataFromIntent(Intent intent) {
        if (intent == null) {
            return null;
        }
        return intent.getStringArrayListExtra(FILE_SELECT_STRING_ARRAY_PARAM);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent(getIntent());
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//清除半透明模式
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);//设置绘画模式
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_FFFFFF));
        }
        setContentView(R.layout.dfileselector_activity_file_selector);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        fileSelectorLayout = findViewById(R.id.activity_file_select_layout);

        imageViewBack = findViewById(R.id.rl_title);
        imageViewBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }
        int id = v.getId();
        if (id == R.id.rl_title) {
            finish();
        }
    }

    @Override
    protected void onPermissionSuccess() {
        FileSelector.with(fileSelectorLayout)
                .listen(onFileSelectListener)
                .setMultiSelectionModel(isMultiMode)
                .setMultiModelMaxSize(maxFileCount)
                .setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        if (pathname.isDirectory()) {
                            return true;
                        }
                        if (mTypes != null && mTypes.length > 0) {
                            for (int i = 0; i < mTypes.length; i++) {
                                if (pathname.getName().endsWith(mTypes[i].toLowerCase()) || pathname.getName().endsWith(mTypes[i].toUpperCase())) {
                                    return true;
                                }
                            }
                        }else {
                            return true;
                        }
                        return false;
                    }
                })
                .setup();
    }

    private FileSelector.OnFileSelectListener onFileSelectListener = new FileSelector.OnFileSelectListener() {
        @Override
        public void onSelected(ArrayList<String> list) {
            Intent intent = new Intent();
            //保存数据到intent中
            intent.putStringArrayListExtra(FILE_SELECT_STRING_ARRAY_PARAM, list);
            if (isNeedBroadCast) {
                //发送广播
                intent.setAction(FILE_SELECT_ACTION);
                DefaultSelectorActivity.this.sendBroadcast(intent);
            }
            if (isForResult) {
                setResult(Activity.RESULT_OK, intent);
            }
            DefaultSelectorActivity.this.finish();
        }
    };
}
