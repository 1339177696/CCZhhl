package com.hulian.oa.iac.media;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.hulian.oa.R;
import com.hulian.oa.iac.base.BaseActivity;

/**
 * @描述: 视频播放类
 */
public class VideoPreviewActivity extends BaseActivity implements
        MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener, View.OnClickListener {
    private String video_path;
    private ImageView picture_left_back;
    private MediaController mMediaController;
    private VideoView mVideoView;
    private TextView tvConfirm;
    private ImageView iv_play;
    private int mPositionWhenPaused = -1;

    @Override
    public boolean isImmersive() {
        return false;
    }

//    @Override
//    public boolean isRequestedOrientation() {
//        return false;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContentView() {
        return R.layout.picture_activity_video_play;
    }

    @Override
    protected void onCreate() {
        initWidgets();
    }


    protected void initWidgets() {
//        super.initWidgets();
//        video_path = getIntent().getStringExtra("video");
//        System.out.println("视频路径"+video_path);
        http://124.234.143.68:7034/FH_O/apply_data/download.do
        video_path = "/storage/emulated/0/JCamera/video_1579160545710.mp4";
        picture_left_back = findViewById(R.id.picture_left_back);
        mVideoView = findViewById(R.id.video_view);
        tvConfirm = findViewById(R.id.tv_confirm);
        mVideoView.setBackgroundColor(Color.BLACK);
        iv_play = findViewById(R.id.iv_play);
        mMediaController = new MediaController(this);
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setMediaController(mMediaController);
        picture_left_back.setOnClickListener(this);
        iv_play.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
//        tvConfirm.setVisibility(config.selectionMode
//                == PictureConfig.SINGLE
//                && config.enPreviewVideo && !isExternalPreview ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onStart() {
        // Play Video
        mVideoView.setVideoPath(TextUtils.isEmpty(video_path) ? "" : video_path);
        Uri uri = Uri.parse("http://192.168.0.101:8080/apply_data/download.do?mogondbId=84ddf39bcd6f473ca91052c662b2b9e4&mediatype=1");
        mVideoView.setVideoURI(uri);
        mVideoView.start();
        super.onStart();
    }

    @Override
    public void onPause() {
        // Stop video when the activity is pause.
        mPositionWhenPaused = mVideoView.getCurrentPosition();
        mVideoView.stopPlayback();

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMediaController = null;
        mVideoView = null;
        iv_play = null;
        super.onDestroy();
    }

    @Override
    public void onResume() {
        // Resume video player
        if (mPositionWhenPaused >= 0) {
            mVideoView.seekTo(mPositionWhenPaused);
            mPositionWhenPaused = -1;
        }

        super.onResume();
    }

    @Override
    public boolean onError(MediaPlayer player, int arg1, int arg2) {
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (null != iv_play) {
            iv_play.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.picture_left_back) {
//            onBackPressed();
//        } else if (id == R.id.iv_play) {
//            mVideoView.start();
//            iv_play.setVisibility(View.INVISIBLE);
//        } else if (id == R.id.tv_confirm) {
//            List<LocalMedia> result = new ArrayList<>();
//            result.add(getIntent().getParcelableExtra(PictureConfig.EXTRA_MEDIA_KEY));
//            Bundle bundle = new Bundle();
//            bundle.putParcelableArrayList(PictureConfig.EXTRA_SELECT_IMAGES_KEY,
//                    (ArrayList<? extends Parcelable>) result);
//            BroadcastManager.getInstance(this)
//                    .action(BroadcastAction.ACTION_PREVIEW_COMPRESSION)
//                    .extras(bundle)
//                    .broadcast();
//            onBackPressed();
//        }
    }

//    @Override
//    public void onBackPressed() {
//        if (config.windowAnimationStyle != null
//                && config.windowAnimationStyle.activityPreviewExitAnimation != 0) {
//            finish();
//            overridePendingTransition(0, config.windowAnimationStyle != null
//                    && config.windowAnimationStyle.activityPreviewExitAnimation != 0 ?
//                    config.windowAnimationStyle.activityPreviewExitAnimation : R.anim.picture_anim_exit);
//        } else {
//            closeActivity();
//        }
//    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ContextWrapper(newBase) {
            @Override
            public Object getSystemService(String name) {
                if (Context.AUDIO_SERVICE.equals(name)) {
                    return getApplicationContext().getSystemService(name);
                }
                return super.getSystemService(name);
            }
        });
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.setOnInfoListener((mp1, what, extra) -> {
            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                // video started
                mVideoView.setBackgroundColor(Color.TRANSPARENT);
                return true;
            }
            return false;
        });
    }
}
