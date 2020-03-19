package com.hulian.oa.me;

import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.Version.VersionInfo_qgl;
import com.hulian.oa.Version.Version_qgl;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.util.LogUtils;
import sskj.lee.appupdatelibrary.BaseUpdateDialogFragment;
import sskj.lee.appupdatelibrary.BaseVersion;
import sskj.lee.appupdatelibrary.SimpleUpdateFragment;


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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.lbanbenactivity);
        ButterKnife.bind(this);
//        ver = getLocalVersion(this);
//        appVersion.setText("Version"+getLocalVersionName(LBanbenActivity.this));

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
                    if (TextUtils.equals("",result.getJSONObject("data").getString("apkPath"))) {
                        Toast.makeText(LBanbenActivity.this, "当前为最新版本", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LBanbenActivity.this,"温馨提示，为节省您的流量请在WiFi情况下载",Toast.LENGTH_LONG).show();
                        Version_context = "1.功能优化。";
                        Version_url = result.getJSONObject("data").getString("apkPath");
                        qgl();
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

    /**
     * 弹出对话框
     */
    public void qgl()
    {
        SimpleUpdateFragment updateFragment = new SimpleUpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BaseUpdateDialogFragment.INTENT_KEY, initData(BaseVersion.NOTIFYCATION_STYLE));
        updateFragment.setArguments(bundle);
        FragmentManager transition = getFragmentManager();
        updateFragment.show(transition, "tag");
    }

    /**
     * 对话框样式
     * @param dialogStyle
     * @return
     */
    private VersionInfo_qgl initData(int dialogStyle)
    {
        VersionInfo_qgl versionInfo = new VersionInfo_qgl();
        versionInfo.setContent(Version_context);
        versionInfo.setTitle(Version_title);
        versionInfo.setMustup(false);
        versionInfo.setUrl(Version_url);
        versionInfo.setViewStyle(dialogStyle);
        return versionInfo;
    }

}
