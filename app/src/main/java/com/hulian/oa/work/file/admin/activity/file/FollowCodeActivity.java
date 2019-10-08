package com.hulian.oa.work.file.admin.activity.file;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.utils.ZXingUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FollowCodeActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_code)
    ImageView ivCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_code);
        ButterKnife.bind(this);
        Bitmap bitmap = ZXingUtils.createQRImage(getIntent().getStringExtra("content"), 800,  800);
        ivCode.setImageBitmap(bitmap);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
