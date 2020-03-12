package com.hulian.oa.me;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.lbanbenactivity);
        ButterKnife.bind(this);
        ver = getLocalVersion(this);
        appVersion.setText("Version"+getLocalVersionName(LBanbenActivity.this));
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
                    String apkurl = result.getJSONObject("data").getString("apkPath");
                    String versionNum = result.getJSONObject("data").getString("versionNum");
                    if (apkurl.equals("")) {
                        Toast.makeText(LBanbenActivity.this, "当前为最新版本", Toast.LENGTH_LONG).show();

                    } else {
                        updateApk(LBanbenActivity.this, "", versionNum, true, true, 10000000, apkurl, LBanbenActivity.this.getResources().getString(R.string.app_name));
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
                postVer(ver);
                break;
        }
    }
}
