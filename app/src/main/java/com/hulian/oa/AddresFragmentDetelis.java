package com.hulian.oa;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hulian.oa.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 作者：qgl 时间： 2020/5/18 17:17
 * Describe: 通讯录详情
 */
public class AddresFragmentDetelis extends BaseActivity {
    private static final String TAG = AddresFragmentDetelis.class.getSimpleName();
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.iv_image)
    FrameLayout ivImage;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_account)
    TextView userAccount;
    @BindView(R.id.user_nick)
    TextView userNick;
    @BindView(R.id.begin_phone)
    TextView beginPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.begin_chat)
    Button beginChat;
    @BindView(R.id.begin_message)
    Button beginMessage;
    @BindView(R.id.toggle_layout)
    LinearLayout toggleLayout;
    private String account;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addresfragmentdetelis);
        ButterKnife.bind(this);
        account = getIntent().getStringExtra("loginName");
        if (TextUtils.isEmpty(account)) {
//            ToastHelper.showToast(AddresFragmentDetelis.this, "传入的帐号为空");
            finish();
            return;
        }
        tvType.setText(getIntent().getStringExtra("userName").substring(getIntent().getStringExtra("userName").length() - 2));
        userName.setText(getIntent().getStringExtra("userName"));
        beginPhone.setText(getIntent().getStringExtra("phone"));
        tvEmail.setText(getIntent().getStringExtra("email"));
    }
    @OnClick({R.id.iv_back, R.id.begin_chat, R.id.begin_message, R.id.begin_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.begin_chat:
                onChat();
                break;
            case R.id.begin_message:
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(Uri.parse("smsto:" + account));
                sendIntent.putExtra("sms_body", "");
                startActivity(sendIntent);
                break;
            case R.id.begin_phone:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + account);
                intent.setData(data);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
    private void onChat() {
        Log.i(TAG, "onChat");
    }
}
