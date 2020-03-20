package com.hulian.oa.me;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadFailedListener;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadingDialogListener;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.Version.Version_qgl;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.net.Urls;
import com.hulian.oa.utils.StatusBarUtil;
import com.hulian.oa.views.BaseDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



/**
 * 创建：  qgl
 * 时间： 2020/03/10
 * 描述： 版本升级页面
 */
public class LBanbenActivity extends BaseActivity {
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.app_version)
    TextView appVersion;
    @BindView(R.id.rela_r1)
    RelativeLayout relaR1;
    private  int ver;
    private int versioncodel;  // 版本号
    private String versionname;  // 版本名称
    private String Version_context = "";
    private String Version_title = "工作桌面";
    private String Version_url;
    private DownloadBuilder builder;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.lbanbenactivity);
        ButterKnife.bind(this);
        versioncodel = Version_qgl.getLocalVersion(this);
        versionname = Version_qgl.getLocalVersionName(this);

    }
    // 后台检测版本
    public void postVer(int v) {
        RequestParams params = new RequestParams();
        params.put("apkVersion", v + "");
        HttpRequest.posVerson(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    String code = result.getJSONObject("data").getString("versionNum");
                    if (Integer.valueOf(code)>v){
                        Toast.makeText(LBanbenActivity.this,"温馨提示，为节省您的流量请在WiFi情况下载",Toast.LENGTH_LONG).show();
                        Version_context = "1.功能优化。";
                        Version_url = result.getJSONObject("data").getString("phoneUri");
                        sendRequest();
                        // 开启下载
                    }else {
                        Toast.makeText(LBanbenActivity.this, "当前为最新版本", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    @OnClick({R.id.rl_title, R.id.rela_r1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_title:
                finish();
                break;
            case R.id.rela_r1:
                postVer(versioncodel);
                break;
        }
    }

    private void sendRequest() {
        builder = AllenVersionChecker
                .getInstance()
                .requestVersion()
                .setRequestUrl(Urls.commUrls + "system/apkVersion/android/apkInfo")
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(String result) {
                        return crateUIData();
                    }
                    @Override
                    public void onRequestVersionFailure(String message) {
                        Toast.makeText(LBanbenActivity.this, "request failed", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.setShowDownloadingDialog(false);
        builder.setShowNotification(true);
        builder.executeMission(this);
    }

    /**
     * @return
     * @important 使用请求版本功能，可以在这里设置downloadUrl
     * 这里可以构造UI需要显示的数据
     * UIData 内部是一个Bundle
     */
    private UIData crateUIData() {
        UIData uiData = UIData.create();
        uiData.setTitle(Version_title+versionname);
        uiData.setDownloadUrl(Version_url);
        uiData.setContent(Version_context);
        return uiData;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //合适的地方关闭
//        AllenVersionChecker.getInstance().cancelAllMission(LBanbenActivity.this);
    }
}
